package pl.jw.currencyexchange;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.text.MessageFormat;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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

}
