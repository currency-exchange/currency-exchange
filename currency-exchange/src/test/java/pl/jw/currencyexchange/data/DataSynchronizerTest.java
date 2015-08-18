package pl.jw.currencyexchange.data;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import pl.jw.currency.exchange.api.CurrencyData;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ContextConfiguration(locations = "classpath:application-context.xml")
public class DataSynchronizerTest extends AbstractJUnit4SpringContextTests {

	private pl.jw.currencyexchange.data.IDataSynchronizer dataSynchronizer;

	@Autowired
	public void setDataSynchronizer(pl.jw.currencyexchange.data.IDataSynchronizer dataSynchronizer) {
		this.dataSynchronizer = dataSynchronizer;
	}

	@Test
	public void synchronize() {
		Set<CurrencyData> set = dataSynchronizer.synchronize(new HashSet<CurrencyData>());
		Assert.assertTrue("No currency expected - input list is empty, none should be synchronized.", set.isEmpty());

		CurrencyData currencyData = pl.jw.currencyexchange.data.CurrencyDAOMock.getMockedCurrency();
		currencyData.setBuyPrice(BigDecimal.valueOf(-12.33));
		currencyData.setSellPrice(BigDecimal.valueOf(-64.2));

		set = dataSynchronizer.synchronize(new HashSet<CurrencyData>(Arrays.asList(currencyData)));
		Assert.assertEquals("One currency from DAO mock expected.", 1, set.size());
		Assert.assertEquals("Synchronization of prices is required.", pl.jw.currencyexchange.data.CurrencyDAOMock.getMockedCurrency().getSellPrice(), set.iterator().next().getSellPrice());
		Assert.assertEquals("Synchronization of prices is required.", pl.jw.currencyexchange.data.CurrencyDAOMock.getMockedCurrency().getBuyPrice(), set.iterator().next().getBuyPrice());

	}
}
