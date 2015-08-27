package pl.jw.currencyexchange.model.mongo.data;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "location")
public class Location {

	@Id
	private String id;

	private CashBox cashBoxState;

	// embedded
	private List<Currency> currencyState;

	@DBRef
	private List<TransactionsSummaryDaily> transactions;

	public CashBox getCashBoxState() {
		return cashBoxState;
	}

	public void setCashBoxState(CashBox cashBoxState) {
		this.cashBoxState = cashBoxState;
	}

	public List<Currency> getCurrencyState() {
		return currencyState;
	}

	public void setCurrencyState(List<Currency> currencyState) {
		this.currencyState = currencyState;
	}

	public List<TransactionsSummaryDaily> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionsSummaryDaily> transactions) {
		this.transactions = transactions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cashBoxState == null) ? 0 : cashBoxState.hashCode());
		result = prime * result + ((currencyState == null) ? 0 : currencyState.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((transactions == null) ? 0 : transactions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (cashBoxState == null) {
			if (other.cashBoxState != null)
				return false;
		} else if (!cashBoxState.equals(other.cashBoxState))
			return false;
		if (currencyState == null) {
			if (other.currencyState != null)
				return false;
		} else if (!currencyState.equals(other.currencyState))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (transactions == null) {
			if (other.transactions != null)
				return false;
		} else if (!transactions.equals(other.transactions))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + "]";
	}

}
