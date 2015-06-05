package pl.jw.vabank.agent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SynchronizationTask {

	private static final Logger log = LogManager.getLogger(SynchronizationTask.class);
	
	@Autowired
	private KantorDao dao;
	
	@Autowired
	private ChangesExporter exporter;
	
	@Autowired
	private  DataStateComparator stateComparator;
	
	@Scheduled(fixedDelay=60000)
	//TODO: dok³adneij  - cron="*/5 * * * * MON-FRI"
	public void execute() {
		log.info("JOB - execution");
	}
}
