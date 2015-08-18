package pl.jw.currencyexchange.agent.export;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import pl.jw.currency.exchange.dao.api.CurrencyState;
import pl.jw.currency.exchange.dao.api.Transaction;
import pl.jw.currencyexchange.agent.export.data.CashBox;
import pl.jw.currencyexchange.agent.export.data.Currency;
import pl.jw.currencyexchange.agent.export.data.DailyCurrencyTransaction;
import pl.jw.currencyexchange.agent.synchronization.IChangesExporter;

@Repository
@PropertySource(value = "classpath:application-location.properties")
public class ChangesExporter implements IChangesExporter {

	private static final Logger log = LogManager.getLogger(ChangesExporter.class);
	@Value(value = "location")
	private String location;
	@Autowired
	private ICurrencyRepository currencyRepository;
	@Autowired
	private ICashBoxRepository cashBoxRepository;
	@Autowired
	private ITransactionRepository transactionRepository;

	private List<Currency> conversion(List<CurrencyState> list) {
		return list.stream().map(d -> {
			Currency currencyState = new Currency();
			currencyState.setId(d.getSymbol());
			currencyState.setOrdinal(d.getOrdinal());
			currencyState.setState(d.getState());
			currencyState.setName(d.getName());
			currencyState.setLocation(location);
			return currencyState;
		}).collect(Collectors.toList());
	}

	private CashBox conversion(BigDecimal cashboxState) {
		CashBox cashBox = new CashBox();
		cashBox.setState(cashboxState);
		cashBox.setLocation(location);
		return cashBox;
	}

	/**
	 * Converts transactions by grouping (quantity summing) it according to it's direction and currency.
	 * @param transactions
	 * @return
	 */
	List<DailyCurrencyTransaction> conversionTransaction(List<Transaction> transactions) {

		Map<TransactionKey, BigDecimal> mapByTransactionDirection = mapByTransactionDirection(transactions);





		List<DailyCurrencyTransaction> list = new ArrayList<>();
		//		collect.forEach((type, quantity) -> {
		//
		//			DailyCurrencyTransaction t = new DailyCurrencyTransaction();
		//			t.setCurrencySymbol(type.getCurrency());
		//			if (type.isBuy()) {
		//				t.setBought(quantity);
		//			} else {
		//				t.setSold(quantity);
		//			}
		//			t.setDate(LocalDate.now());
		//
		//			t.setLocation(location);
		//
		//			list.add(t);
		//		});

		// list.stream().
		// TODO:merge tych samych symboli

		return list;
	}

	Map<TransactionKey, BigDecimal> mapByTransactionDirection(List<Transaction> transactions) {
		Map<TransactionKey, BigDecimal> mapByTransactionDirection = Optional.ofNullable(transactions).orElse(new ArrayList<>()).stream()
				.collect(
						Collectors.groupingBy(
								TransactionKey::getInstance,
								Collectors.mapping(Transaction::getQuantity,

										Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

		return mapByTransactionDirection;
	}

	@Override
	public void synchronizeCurrencyState(List<CurrencyState> set) {
		log.info("Synchronization - currency - before: {0}", currencyRepository.findAll());

		List<Currency> listMongo = conversion(set);

		log.info("Synchronization - currency - after: {0}", listMongo);

		currencyRepository.save(listMongo);
	}

	@Override
	public void synchronizeCashboxState(BigDecimal cashboxState) {
		log.info("Synchronization - cashbox");

		CashBox cashbox = conversion(cashboxState);
		cashBoxRepository.save(cashbox);
	}

	@Override
	public void synchronizeTransactions(List<Transaction> transactions) {
		log.info("Synchronization - transactions");

		transactionRepository.save(conversionTransaction(transactions));
	}

	static class TransactionKey {
		private final String currency;
		private final boolean plus;

		public TransactionKey(String currency, boolean buy) {
			this.currency = currency;
			this.plus = buy;
		}

		public static TransactionKey getInstance(Transaction t) {
			return new TransactionKey(t.getCurrencySymbol(), t.getType().isPlus());
		}

		public String getCurrency() {
			return currency;
		}

		public boolean isPlus() {
			return plus;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (plus ? 1231 : 1237);
			result = prime * result + ((currency == null) ? 0 : currency.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TransactionKey other = (TransactionKey) obj;
			if (plus != other.plus)
				return false;
			if (currency == null) {
				if (other.currency != null)
					return false;
			} else if (!currency.equals(other.currency))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "[currency=" + currency + ", plus=" + plus + "]";
		}
	}

}
