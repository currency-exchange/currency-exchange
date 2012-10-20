package pl.jw.currencyexchange;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import pl.jw.currency.exchange.api.CurrencyData;

import com.google.common.base.Strings;

public class Util {

	public static void setComponentSize(Component jComponent, int width, int height) {

		jComponent.setMinimumSize(new Dimension(width, height));
		jComponent.setMaximumSize(new Dimension(width, height));
		jComponent.setPreferredSize(new Dimension(width, height));
	}

	public static void exceptionSupport(Container container, Exception ex) {
		JOptionPane
				.showMessageDialog(SwingUtilities.windowForComponent(container), MessageFormat.format("Wyst¹pi³ b³¹d:\n\"{0}\"", ex.getMessage()), "B³¹d", JOptionPane.OK_OPTION);
		ex.printStackTrace();
	}

	/**
	 * Wyznacza kurs na podstawie ceny waluty dla podanej iloœci.
	 * <p>
	 * Wymaga utrzymania wysokiej precyzji obliczeñ.
	 * 
	 * @param currencyData
	 * @param price
	 * @return
	 */
	public static String getCourse(CurrencyData currencyData, BigDecimal price) {
		BigDecimal curse = price.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : price.divide(BigDecimal.valueOf(currencyData.getCount()), 5, RoundingMode.HALF_UP);

		return priceToString(curse);
	}

	/**
	 * Konwersja z zachowaniem ustalonej precyzji.
	 * 
	 * @param curse
	 * @return
	 */
	public static String priceToString(BigDecimal curse) {
		String str = curse == null ? Constants.PRICE_DEFAULT.toPlainString() : curse.setScale(Constants.SCALE, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();

		int indexOfDot = str.indexOf('.');
		if (indexOfDot < 0) {
			str = str + '.';
			str = Strings.padEnd(str, str.length() + Constants.SCALE_MIN, '0');
		} else {

			int placesAfterDot = str.length() - 1 - indexOfDot;
			if (placesAfterDot < Constants.SCALE_MIN) {
				str = Strings.padEnd(str, str.length() + Constants.SCALE_MIN - placesAfterDot, '0');

			}
		}

		return str;
	}
}
