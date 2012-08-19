package pl.jw.currencyexchange.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.jw.currency.exchange.api.CurrencyData;
import pl.jw.currency.exchange.api.ICurrencyDAO;

public class CurrencyDAOMock implements ICurrencyDAO {

	@Override
	public List<CurrencyData> get() {
		CurrencyData currencyData = getMockedCurrency();

		return new ArrayList<>(Arrays.asList(currencyData));
	}

	static CurrencyData getMockedCurrency() {
		CurrencyData currencyData = new CurrencyData("Polska", "PLN", BigDecimal.TEN, BigDecimal.ONE);
		return currencyData;
	}

}
