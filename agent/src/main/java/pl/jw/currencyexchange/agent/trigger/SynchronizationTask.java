package pl.jw.currencyexchange.agent.trigger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import pl.jw.currency.exchange.dao.api.ICurrencyDAO;
import pl.jw.currencyexchange.agent.data.DataStateComparator;
import pl.jw.currencyexchange.agent.export.ChangesExporter;

@Service
public class SynchronizationTask {

	private static final Logger log = LogManager
			.getLogger(SynchronizationTask.class);

	@Autowired
	private ICurrencyDAO dao;

	@Autowired
	private ChangesExporter exporter;

	@Autowired
	private DataStateComparator stateComparator;

	@Scheduled(fixedDelay = 60000)
	// TODO: dok�adneij - cron="*/5 * * * * MON-FRI"
	public void execute() {
		log.info("JOB - execution");

		dao.get();
	}
}
