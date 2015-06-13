package pl.jw.currencyexchange.agent.export;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.jw.currency.exchange.dao.api.CurrencyState;
import pl.jw.currency.exchange.dao.api.Transaction;
import pl.jw.currencyexchange.agent.synchronization.IChangesExporter;

@Repository
public class ChangesExporter implements IChangesExporter {

	private static final Logger log = LogManager.getLogger(ChangesExporter.class);

	@Autowired
	private ICurrencyRepository currencyRepository;

	@Override
	public void synchronizeCurrencyState(List<CurrencyState> set) {
		log.info("Synchronization - currency - before: {0}", currencyRepository.findAll());

		List<Currency> listMongo = set.stream().map(d -> {
			Currency currencyState = new Currency();
			currencyState.setId(d.getSymbol());
			currencyState.setOrdinal(d.getOrdinal());
			currencyState.setState(d.getState());

			return currencyState;
		}).collect(Collectors.toList());

		log.info("Synchronization - currency - after: {0}", listMongo);

		currencyRepository.save(listMongo);
	}

	@Override
	public void synchronizeCashboxState(BigDecimal cashboxState) {
		log.info("Synchronization - cashbox");

	}

	@Override
	public void synchronizeTransactions(List<Transaction> transactions) {
		log.info("Synchronization - transactions");

	}

}
