package pl.jw.currencyexchange.agent.data;

import java.math.BigDecimal;
import java.util.List;

import pl.jw.currency.exchange.dao.api.CurrencyState;
import pl.jw.currency.exchange.dao.api.Transaction;

public class SynchronizedDataState {

	private List<Transaction> listTransactions;

	private List<CurrencyState> listCurrencyState;

	private BigDecimal cashboxState;

	public SynchronizedDataState() {
	}

	public SynchronizedDataState(List<Transaction> listTransactions, BigDecimal cashboxState,
			List<CurrencyState> listCurrencyState) {
		this.listTransactions = listTransactions;
		this.cashboxState = cashboxState;
		this.listCurrencyState = listCurrencyState;
	}

	public List<CurrencyState> getCurrencyState() {
		return listCurrencyState;
	}

	public int getTransactionsCount() {
		return listTransactions.size();
	}

	public BigDecimal getCashboxState() {
		return cashboxState;
	}

	public List<Transaction> getTransactions() {
		return listTransactions;
	}

	@Override
	public String toString() {
		return "SynchronizedDataState [listTransactions=" + listTransactions + ", setCurrencyState="
				+ listCurrencyState + ", cashboxState=" + cashboxState + "]";
	}

}
