package pl.jw.currencyexchange.agent.synchronization;

import java.math.BigDecimal;
import java.util.List;

import pl.jw.currency.exchange.dao.api.CurrencyState;
import pl.jw.currency.exchange.dao.api.Transaction;

public interface IChangesExporter {

	public abstract void synchronizeCurrencyState(List<CurrencyState> set);

	public abstract void synchronizeCashboxState(BigDecimal cashboxState);

	public abstract void synchronizeTransactions(List<Transaction> transactions);

}