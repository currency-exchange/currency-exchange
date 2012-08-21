package pl.jw.currencyexchange;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;

public class UtilJTest {

	@Test
	public void priceToString_Float() {
		String str = Util.priceToString(BigDecimal.valueOf(1.1));
		Assert.assertEquals("1.10", str);

		str = Util.priceToString(BigDecimal.valueOf(1.22));
		Assert.assertEquals("1.22", str);

		str = Util.priceToString(BigDecimal.valueOf(1.333));
		Assert.assertEquals("1.333", str);

		str = Util.priceToString(BigDecimal.valueOf(1.4444));
		Assert.assertEquals("1.4444", str);

		str = Util.priceToString(BigDecimal.valueOf(1.55555));
		Assert.assertEquals("1.5556", str);

		str = Util.priceToString(BigDecimal.valueOf(1.55554));
		Assert.assertEquals("1.5555", str);

	}

	@Test
	public void priceToString_Integer() {

		String str = Util.priceToString(null);
		Assert.assertNotNull(str);
		assertZero(str);

		str = Util.priceToString(BigDecimal.ZERO);
		Assert.assertEquals("0.0000", str);

		str = Util.priceToString(BigDecimal.TEN.setScale(0));
		assertTen(str);

		str = Util.priceToString(BigDecimal.TEN.setScale(1));
		assertTen(str);

		str = Util.priceToString(BigDecimal.TEN.setScale(2));
		assertTen(str);

		str = Util.priceToString(BigDecimal.TEN.setScale(3));
		assertTen(str);

		str = Util.priceToString(BigDecimal.TEN.setScale(4));
		assertTen(str);

		str = Util.priceToString(BigDecimal.TEN.setScale(5));
		assertTen(str);
	}

	private void assertTen(String str) {
		Assert.assertTrue(str.matches("10\\.[0]{" + Constants.SCALE_MIN + "}"));
	}

	private void assertZero(String str) {
		Assert.assertTrue(str.matches("0\\.[0]{" + Constants.SCALE_MIN + "}"));
	}

}
