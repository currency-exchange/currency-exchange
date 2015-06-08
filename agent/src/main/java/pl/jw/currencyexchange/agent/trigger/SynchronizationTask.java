package pl.jw.currencyexchange.agent.trigger;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import pl.jw.currency.exchange.dao.api.CurrencyState;
import pl.jw.currency.exchange.dao.api.ICurrencyDAO;
import pl.jw.currencyexchange.agent.data.DataStateComparator;
import pl.jw.currencyexchange.agent.export.ChangesExporter;
import pl.jw.currencyexchange.agent.export.CurrencyState;
import pl.jw.currencyexchange.agent.export.CurrencyStateRepository;

@Service
public class SynchronizationTask {

	private static final Logger log = LogManager
			.getLogger(SynchronizationTask.class);

	@Autowired
	private ICurrencyDAO dao;

	@Autowired
	private CurrencyStateRepository stateRepository;

	@Autowired
	private ChangesExporter exporter;

	@Autowired
	private DataStateComparator stateComparator;

	@Scheduled(fixedDelay = 60000)
	// TODO: dok�adneij - cron="*/5 * * * * MON-FRI"
	public void execute() {
		log.info("JOB - execution");

		log.info("stored: " + stateRepository.findAll());

		List<CurrencyState> list = dao.get();
		List<CurrencyState> listMongo = list.stream().map(d -> {
			CurrencyState currencyState = new CurrencyState();
			currencyState.setLp(d.getOrdinal());
			currencyState.setState(d.getState());
			currencyState.setSymbol(d.getSymbol());

			return currencyState;
		}).collect(Collectors.toList());
		stateRepository.save(listMongo);
	}
}
