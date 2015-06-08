package pl.jw.currency.exchange.dao.kantor2008;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import pl.jw.currency.exchange.dao.ConfigurationDaoPlugin;
import pl.jw.currency.exchange.dao.api.CurrencyState;
import pl.jw.currency.exchange.dao.api.ICurrencyDAO;
import pl.jw.currency.exchange.dao.api.Transaction;

@ActiveProfiles("test")
@ContextConfiguration(classes = ConfigurationDaoPlugin.class)
public class CurrencyDAOTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ICurrencyDAO currencyDAO;

	@Test
	public void get() {
		List<CurrencyState> list = currencyDAO.get();
		Assert.assertEquals("Content of whole table expected.", 11, list.size());
	}

	@Test
	public void getCashboxState() throws Exception {
		BigDecimal cashboxState = currencyDAO.getCashboxState();

		assertThat(
				"Sum of transactions values (plus or minus, depending on transaction direction) + in/out transactions expected.",
				cashboxState, comparesEqualTo(BigDecimal.valueOf(11022.80)));
	}

	@Test
	public void getTransactions() throws Exception {
		List<Transaction> data = currencyDAO.getTransactions(LocalDate.now()
				.withYear(2015).withMonth(6).withDayOfMonth(8));

		assertThat(data, hasSize(8));
		assertThat(
				"Descending order (by number) expected.",
				data,
				contains(hasProperty("number", equalTo("11/2015")),
						hasProperty("number", equalTo("10/2015")),
						hasProperty("number", equalTo("10/2015")),
						hasProperty("number", equalTo("9/2015")),
						hasProperty("number", equalTo("8/2015")),
						hasProperty("number", equalTo("7/2015")),
						hasProperty("number", equalTo("6/2015")),
						hasProperty("number", equalTo("5/2015"))));
		//
		// hasItems(hasProperty("number", equalTo("11/2015")),
		// hasProperty("number", equalTo("")),
		// hasProperty("number", equalTo("")),
		// hasProperty("number", equalTo("")),
		// hasProperty("number", equalTo("")),
		// hasProperty("number", equalTo("")),
		// hasProperty("number", equalTo("")),
		// hasProperty("number", equalTo("")),
		// hasProperty("number", equalTo("")),
		// hasProperty("number", equalTo("")),
		// hasProperty("number", equalTo("")),
		// ));

	}
}
