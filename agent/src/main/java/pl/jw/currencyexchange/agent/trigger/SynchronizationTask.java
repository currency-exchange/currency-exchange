package pl.jw.currencyexchange.agent.trigger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import pl.jw.currencyexchange.agent.data.DataStateComparators;
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
	// FIXME: initial state should be pulled form repo
	private SynchronizedDataState state = new SynchronizedDataState();

	@Scheduled(fixedDelay = 60000)
	// TODO: dok�adneij - cron="*/5 * * * * MON-FRI"
	public void execute() {
		log.info("Synchronization - START");

		SynchronizedDataState actualState = importer.importCurrentState();

		if (DataStateComparators.isChanged(state, actualState)) {
			log.info("Synchronization - CHANGES:\n{0}\n{1}", state, actualState);

			if (DataStateComparators.changedCurrencyState(state, actualState)) {
				exporter.synchronizeCurrencyState(actualState.getCurrencyState());
			}

			if (DataStateComparators.changedCashboxState(state, actualState)) {
				exporter.synchronizeCashboxState(actualState.getCashboxState());
			}

			if (DataStateComparators.changedTransactions(state, actualState)) {
				exporter.synchronizeTransactions(actualState.getTransactions());
			}

			state = actualState;
		} else {
			log.info("Synchronization - NO CHANGES, state:\n{0}", state);
		}
	}

}
