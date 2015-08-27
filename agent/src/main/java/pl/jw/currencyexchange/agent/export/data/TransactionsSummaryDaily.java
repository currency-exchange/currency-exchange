package pl.jw.currencyexchange.agent.export.data;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transactionsSummaryDaily")
@CompoundIndexes({ @CompoundIndex(name = "index-location-date", def = "{'location': 1, 'date': 1}") })
public class TransactionsSummaryDaily implements Cloneable {

	@Id
	private String id;

	@Indexed
	private String location;

	@Indexed
	private LocalDate date;

	@Indexed
	private String currencySymbol;

	private BigDecimal bought;
	private BigDecimal sold;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public BigDecimal getBought() {
		return bought;
	}

	public void setBought(BigDecimal bought) {
		this.bought = bought;
	}

	public BigDecimal getSold() {
		return sold;
	}

	public void setSold(BigDecimal sold) {
		this.sold = sold;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bought == null) ? 0 : bought.hashCode());
		result = prime * result + ((currencySymbol == null) ? 0 : currencySymbol.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((sold == null) ? 0 : sold.hashCode());
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
		TransactionsSummaryDaily other = (TransactionsSummaryDaily) obj;
		if (bought == null) {
			if (other.bought != null)
				return false;
		} else if (!bought.equals(other.bought))
			return false;
		if (currencySymbol == null) {
			if (other.currencySymbol != null)
				return false;
		} else if (!currencySymbol.equals(other.currencySymbol))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (sold == null) {
			if (other.sold != null)
				return false;
		} else if (!sold.equals(other.sold))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TransactionsSummaryDaily [id=" + id + ", date=" + date + ", currencySymbol=" + currencySymbol
				+ ", location=" + location + ", bought=" + bought + ", sold=" + sold + "]";
	}

	@Override
	protected Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
