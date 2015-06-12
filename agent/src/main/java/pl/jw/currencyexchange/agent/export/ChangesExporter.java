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

@Repository
public class ChangesExporter {

	private static final Logger log = LogManager.getLogger(ChangesExporter.class);

	@Autowired
	private CurrencyStateRepository repository;

	public void synchronizeCurrencyState(List<CurrencyState> set) {
		log.info("Mongo update - stored: {0}", repository.findAll());

		List<CurrencyState> listMongo = set.stream().map(d -> {
			CurrencyState currencyState = new CurrencyState();
			currencyState.setOrdinal(d.getOrdinal());
			currencyState.setState(d.getState());
			currencyState.setSymbol(d.getSymbol());

			return currencyState;
		}).collect(Collectors.toList());
		// stateRepository.save(listMongo);
	}

	public void synchronizeCashboxState(BigDecimal cashboxState) {
		// TODO Auto-generated method stub

	}

	public void synchronizeTransactions(List<Transaction> transactions) {
		// TODO Auto-generated method stub

	}

}
