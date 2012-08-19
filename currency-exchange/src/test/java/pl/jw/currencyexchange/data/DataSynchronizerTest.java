package pl.jw.currencyexchange.data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import pl.jw.currency.exchange.api.CurrencyData;

@ContextConfiguration(locations = "classpath:application-context.xml")
public class DataSynchronizerTest extends AbstractJUnit4SpringContextTests {

	private IDataSynchronizer dataSynchronizer;

	@Autowired
	public void setDataSynchronizer(IDataSynchronizer dataSynchronizer) {
		this.dataSynchronizer = dataSynchronizer;
	}

	@Test
	public void synchronize() {
		Set<CurrencyData> set = dataSynchronizer.synchronize(new HashSet<CurrencyData>());
		Assert.assertTrue("No currency expected - input list is empty, none should be synchronized.", set.isEmpty());

		CurrencyData currencyData = CurrencyDAOMock.getMockedCurrency();
		currencyData.setBuyPrice(BigDecimal.valueOf(-12.33));
		currencyData.setSellPrice(BigDecimal.valueOf(-64.2));

		set = dataSynchronizer.synchronize(new HashSet<CurrencyData>(Arrays.asList(currencyData)));
		Assert.assertEquals("One currency from DAO mock expected.", 1, set.size());
		Assert.assertEquals("Synchronization of prices is required.", CurrencyDAOMock.getMockedCurrency().getSellPrice(), set.iterator().next().getSellPrice());
		Assert.assertEquals("Synchronization of prices is required.", CurrencyDAOMock.getMockedCurrency().getBuyPrice(), set.iterator().next().getBuyPrice());

	}
}
