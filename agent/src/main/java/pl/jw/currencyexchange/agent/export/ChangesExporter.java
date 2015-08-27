package pl.jw.currencyexchange.agent.export;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import pl.jw.currencyexchange.agent.export.data.Location;
import pl.jw.currencyexchange.agent.export.data.TransactionsSummaryDaily;
import pl.jw.currencyexchange.agent.synchronization.IChangesExporter;

@Repository
@PropertySource(value = "classpath:application-location.properties")
public class ChangesExporter implements IChangesExporter {

	private static final Logger log = LogManager.getLogger(ChangesExporter.class);

	@Value(value = "${location}")
	private String location;

	@Autowired
	private ILocationRepository locationRepository;
	@Autowired
	private ITransactionRepository transactionRepository;

	private List<Currency> conversion(List<CurrencyState> list) {
		return list.stream().map(d -> {
			Currency currencyState = new Currency();
			currencyState.setSymbol(d.getSymbol());
			currencyState.setOrdinal(d.getOrdinal());
			currencyState.setState(d.getState());
			currencyState.setName(d.getName());
			return currencyState;
		}).collect(Collectors.toList());
	}

	private CashBox conversion(BigDecimal cashboxState) {
		CashBox cashBox = new CashBox();
		cashBox.setState(cashboxState);
		return cashBox;
	}

	/**
	 * Converts transactions by grouping (quantity summing) it according to it's
	 * direction and currency.
	 *
	 * @param transactions
	 * @return
	 */
	List<TransactionsSummaryDaily> conversionTransaction(List<Transaction> transactions) {

		Map<TransactionKey, BigDecimal> mapByTransactionDirection = mapByTransactionDirection(transactions);

		// conversion
		List<TransactionsSummaryDaily> list = mapByTransactionDirection.entrySet().stream().map((entry) -> {

			TransactionsSummaryDaily t = new TransactionsSummaryDaily();
			t.setCurrencySymbol(entry.getKey().getCurrency());
			if (entry.getKey().isPlus()) {
				t.setBought(entry.getValue());
			} else {
				t.setSold(entry.getValue());
			}
			t.setDate(LocalDate.now());

			t.setLocation(location);

			return t;
		}).collect(Collectors.toList());

		return mergeCurrenciesBoughtSold(list);
	}

	private BigDecimal notNull(BigDecimal b) {
		return b == null ? BigDecimal.ZERO : b;
	}

	private BigDecimal sum(BigDecimal a, BigDecimal b) {
		return a == null && b == null ? null : notNull(a).add(notNull(b));
	}

	List<TransactionsSummaryDaily> mergeCurrenciesBoughtSold(List<TransactionsSummaryDaily> list) {
		// merge of values for the same symbols
		Map<Object, TransactionsSummaryDaily> m = Optional.ofNullable(list).orElse(new ArrayList<>()).stream()
				.collect(Collectors.groupingBy(t -> t.getCurrencySymbol(),
						Collectors.reducing(new TransactionsSummaryDaily(), (a, b) -> {

							b.setBought(sum(a.getBought(), b.getBought()));
							b.setSold(sum(a.getSold(), b.getSold()));
							return b;
						})));

		return new ArrayList<>(m.values());
	}

	Map<TransactionKey, BigDecimal> mapByTransactionDirection(List<Transaction> transactions) {
		Map<TransactionKey, BigDecimal> mapByTransactionDirection = Optional.ofNullable(transactions)
				.orElse(new ArrayList<>()).stream()
				// filter canceled or unknown
				.filter(t -> !t.getType().isZero())
				.collect(Collectors.groupingBy(TransactionKey::getInstance, Collectors.mapping(Transaction::getQuantity,

						Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

		return mapByTransactionDirection;
	}

	public List<TransactionsSummaryDaily> synchronizeTransactions(List<Transaction> transactions) {
		log.info("Synchronization - transactions");

		return transactionRepository.save(conversionTransaction(transactions));
	}

	@Override
	public void synchronize(List<Transaction> transactions, BigDecimal cashboxState,
			List<CurrencyState> currenciesState) {

		log.info("Synchronization - before: {0}", locationRepository.findAll());
		LocalDate date = LocalDate.now();

		// clean transactions from today - might have changed/been deleted or
		// simply added
		transactionRepository.deleteByLocationAndDate(location, date);
		// add current state of transactions
		List<TransactionsSummaryDaily> listSynchronizedTransactions = synchronizeTransactions(transactions);

		// update data
		Location data = Optional.ofNullable(locationRepository.findOne(location)).orElse(new Location());

		prepareLocationData(data, cashboxState, currenciesState, listSynchronizedTransactions);

		data = locationRepository.save(data);

		log.info("Synchronization - currency - after: {0}", data);
	}

	private Location prepareLocationData(Location data, BigDecimal cashboxState, List<CurrencyState> currenciesState,
			List<TransactionsSummaryDaily> listSynchronizedTransactions) {
		data.setId(location);
		data.setCashBoxState(conversion(cashboxState));
		data.setCurrencyState(conversion(currenciesState));
		// stores all transactions so daily transactions are just added
		List<TransactionsSummaryDaily> list = Optional.ofNullable(data.getTransactions()).orElse(new ArrayList<>());
		list.addAll(listSynchronizedTransactions);
		data.setTransactions(list);
		return data;
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
