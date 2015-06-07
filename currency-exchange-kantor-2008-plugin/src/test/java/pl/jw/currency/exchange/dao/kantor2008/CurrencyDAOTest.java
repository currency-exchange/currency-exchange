package pl.jw.currency.exchange.dao.kantor2008;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import pl.jw.currency.exchange.dao.ConfigurationDaoPlugin;
import pl.jw.currency.exchange.dao.api.CurrencyData;
import pl.jw.currency.exchange.dao.api.ICurrencyDAO;


@ActiveProfiles("test")
@ContextConfiguration(classes = ConfigurationDaoPlugin.class)
public class CurrencyDAOTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ICurrencyDAO currencyDAO;

	public void setCurrencyDAO(ICurrencyDAO currencyDAO) {
		this.currencyDAO = currencyDAO;
	}

	@Test
	public void get() {
		List<CurrencyData> list = currencyDAO.get();
		Assert.assertEquals("", 11, list.size());
	}

}
