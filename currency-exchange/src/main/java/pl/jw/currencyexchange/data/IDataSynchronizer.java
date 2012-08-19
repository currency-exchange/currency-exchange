package pl.jw.currencyexchange.data;

import java.util.Set;

import pl.jw.currency.exchange.api.CurrencyData;

public interface IDataSynchronizer {

	public abstract Set<CurrencyData> synchronize(Set<CurrencyData> listAcctualData);

}
