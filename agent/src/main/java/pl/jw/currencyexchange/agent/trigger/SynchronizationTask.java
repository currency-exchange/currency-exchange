package pl.jw.currencyexchange.agent.trigger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import pl.jw.currencyexchange.agent.data.SynchronizedDataState;
import pl.jw.currencyexchange.agent.synchronization.IChangesExporter;
import pl.jw.currencyexchange.agent.synchronization.IChangesImporter;

@Service
public class SynchronizationTask {

	private static final Logger log = LogManager.getLogger(SynchronizationTask.class);

	@Autowired
	private IChangesImporter importer;

	@Autowired
	private IChangesExporter exporter;

	@Scheduled(fixedDelay = 60000)
	// TODO: dok�adneij - cron="*/5 * * * * MON-FRI"
	public void execute() {
		log.info("Synchronization - START");

		// TODO: log4j2 logging method with params
		// FIXME: initial state should be pulled form mongo repo
		SynchronizedDataState actualState = importer.importCurrentState();

		exporter.synchronize(actualState.getTransactions(), actualState.getCashboxState(),
				actualState.getCurrencyState());

	}

}
