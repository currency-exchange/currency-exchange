package pl.jw.currencyexchange.agent.export.data;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class DailyCurrencyTransaction implements ILocation, Cloneable {

	@Id
	private LocalDate date;

	private String location;

	private String currencySymbol;
	private BigDecimal bought;
	private BigDecimal sold;

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public void setLocation(String location) {
		this.location = location;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bought == null) ? 0 : bought.hashCode());
		result = prime * result + ((currencySymbol == null) ? 0 : currencySymbol.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		DailyCurrencyTransaction other = (DailyCurrencyTransaction) obj;
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
		return "DailyCurrencyTransaction [date=" + date + ", location=" + location + ", currencySymbol="
				+ currencySymbol + ", bought=" + bought + ", sold=" + sold + "]";
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
