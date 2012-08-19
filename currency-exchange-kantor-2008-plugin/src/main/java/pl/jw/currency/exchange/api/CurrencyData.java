package pl.jw.currency.exchange.api;

import java.awt.Image;
import java.math.BigDecimal;

public class CurrencyData {

	private String name;
	private String symbol;
	private BigDecimal sellPrice;
	private BigDecimal buyPrice;
	private int count;
	private int ordinal;
	private BigDecimal state;
	private BigDecimal changeableCourse;
	private BigDecimal forks;
	private Image flag;

	public CurrencyData() {
	}

	public CurrencyData(String name, String symbol, BigDecimal sellPrice, BigDecimal buyPrice) {
		super();
		this.name = name;
		this.symbol = symbol;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal value) {
		this.sellPrice = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public BigDecimal getState() {
		return state;
	}

	public void setState(BigDecimal state) {
		this.state = state;
	}

	public BigDecimal getChangeableCourse() {
		return changeableCourse;
	}

	public void setChangeableCourse(BigDecimal changeableCourse) {
		this.changeableCourse = changeableCourse;
	}

	public BigDecimal getForks() {
		return forks;
	}

	public void setForks(BigDecimal forks) {
		this.forks = forks;
	}

	public Image getFlag() {
		return flag;
	}

	public void setFlag(Image flag) {
		this.flag = flag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (buyPrice == null ? 0 : buyPrice.hashCode());
		result = prime * result + (changeableCourse == null ? 0 : changeableCourse.hashCode());
		result = prime * result + count;
		result = prime * result + (forks == null ? 0 : forks.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + ordinal;
		result = prime * result + (sellPrice == null ? 0 : sellPrice.hashCode());
		result = prime * result + (state == null ? 0 : state.hashCode());
		result = prime * result + (symbol == null ? 0 : symbol.hashCode());
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
		CurrencyData other = (CurrencyData) obj;
		if (buyPrice == null) {
			if (other.buyPrice != null)
				return false;
		} else if (!buyPrice.equals(other.buyPrice))
			return false;
		if (changeableCourse == null) {
			if (other.changeableCourse != null)
				return false;
		} else if (!changeableCourse.equals(other.changeableCourse))
			return false;
		if (count != other.count)
			return false;
		if (forks == null) {
			if (other.forks != null)
				return false;
		} else if (!forks.equals(other.forks))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ordinal != other.ordinal)
			return false;
		if (sellPrice == null) {
			if (other.sellPrice != null)
				return false;
		} else if (!sellPrice.equals(other.sellPrice))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

}
