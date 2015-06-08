package pl.jw.currencyexchange.agent.export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChangesExporter {

	@Autowired
	private CurrencyStateRepository repository;

	public boolean synchronize() {

		repository.findAll();

		return false;
	}

}
