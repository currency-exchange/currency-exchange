package pl.jw.currencyexchange.agent.synchronization;

import java.math.BigDecimal;
import java.util.List;

import pl.jw.currency.exchange.dao.api.CurrencyState;
import pl.jw.currency.exchange.dao.api.Transaction;

public interface IChangesExporter {

	void synchronize(List<Transaction> transactions, BigDecimal cashboxState, List<CurrencyState> currenciesState);

}