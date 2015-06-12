package pl.jw.currencyexchange.agent.importt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import pl.jw.currency.exchange.dao.api.CurrencyState;
import pl.jw.currency.exchange.dao.api.ICurrencyDAO;
import pl.jw.currency.exchange.dao.api.Transaction;
import pl.jw.currencyexchange.agent.data.SynchronizedDataState;

public class ChangesImporter {

	private static final Logger log = LogManager.getLogger(ChangesImporter.class);

	@Autowired
	private ICurrencyDAO dao;

	public SynchronizedDataState importCurrentState() {

		log.info("Data import");

		List<Transaction> listTransactions = dao.getTransactions(LocalDate.now());
		List<CurrencyState> listCurrencyState = dao.get();
		BigDecimal cashboxState = dao.getCashboxState();
		SynchronizedDataState actualState = new SynchronizedDataState(listTransactions, cashboxState, listCurrencyState);

		log.info("Data import - done {0}", actualState);

		return actualState;
	}

}
