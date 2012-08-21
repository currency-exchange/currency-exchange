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

	public static String getCourse(CurrencyData currencyData, BigDecimal price) {
		BigDecimal curse = price.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : price.divide(BigDecimal.valueOf(currencyData.getCount()), RoundingMode.HALF_UP);

		return priceToString(curse);
	}

	public static String priceToString(BigDecimal curse) {
		return curse.setScale(Constants.SCALE).stripTrailingZeros().toPlainString();
	}

}
