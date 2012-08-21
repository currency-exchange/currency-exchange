package pl.jw.currencyexchange;

import java.math.BigDecimal;

public class Constants {

	public static final int SCALE = 4;
	public static final int SCALE_MIN = 2;

	public static final String REG_EXP_COURSE = "[0-9]{0,3}[\\.]?[0-9]{0," + SCALE + "}";
	public static final int FIELD_OFFSET_SELL = 130;
	public static final int FIELD_OFFSET_BUY = 515;
	public static final int FIELDS_COUNT = 11;
	public static final String IMAGE_BACKGROUND = "background.jpg";
	static final String APPLICATION_CONTEXT_XML = "application-context.xml";
	public final static BigDecimal PRICE_DEFAULT = BigDecimal.ZERO;

}
