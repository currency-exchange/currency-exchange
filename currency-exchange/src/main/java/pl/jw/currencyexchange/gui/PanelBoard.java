package pl.jw.currencyexchange.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import pl.jw.currency.exchange.api.CurrencyData;
import pl.jw.currencyexchange.Constants;
import pl.jw.currencyexchange.Util;
import pl.jw.currencyexchange.data.IDataSynchronizer;

class PanelBoard extends JPanel {
	private final class ListenerTextField extends InputVerifier {
		@Override
		public boolean verify(JComponent input) {
			if (input instanceof JTextField) {

				// FIXME: currencies set should be synchronized after change

				log.info("\n");
				for (int i = 0; i < Constants.FIELDS_COUNT; i++) {

					log.info(MessageFormat.format("{0}: {1} {2}", i, listBuyPrice.get(i).getText(), listSellPrice.get(i).getText()));
				}
			}
			return true;
		}
	}

	private final class ListenerSynchronization implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				synchronize();
			} catch (Exception ex) {
				Util.exceptionSupport(PanelBoard.this, ex);
			}
		}
	}

	private static Logger log = Logger.getLogger(PanelBoard.class);

	private final Set<CurrencyData> setCurrencies = new LinkedHashSet<>();
	{
		setCurrencies.add(new CurrencyData("EURO", "EUR", Constants.PRICE_DEFAULT, Constants.PRICE_DEFAULT));
		setCurrencies.add(new CurrencyData("USA", "USD", Constants.PRICE_DEFAULT, Constants.PRICE_DEFAULT));
		setCurrencies.add(new CurrencyData("W. BRYTANIA", "GBP", Constants.PRICE_DEFAULT, Constants.PRICE_DEFAULT));
		setCurrencies.add(new CurrencyData("KANADA", "CAD", Constants.PRICE_DEFAULT, Constants.PRICE_DEFAULT));
		setCurrencies.add(new CurrencyData("AUSTRALIA", "AUD", Constants.PRICE_DEFAULT, Constants.PRICE_DEFAULT));
		setCurrencies.add(new CurrencyData("NORWEGIA", "NOK", Constants.PRICE_DEFAULT, Constants.PRICE_DEFAULT));
		setCurrencies.add(new CurrencyData("SZWAJCARIA", "CHF", Constants.PRICE_DEFAULT, Constants.PRICE_DEFAULT));
		setCurrencies.add(new CurrencyData("CZECHY", "CZK", Constants.PRICE_DEFAULT, Constants.PRICE_DEFAULT));
		setCurrencies.add(new CurrencyData("DANIA", "DKK", Constants.PRICE_DEFAULT, Constants.PRICE_DEFAULT));
		setCurrencies.add(new CurrencyData("SZWECJA", "SEK", Constants.PRICE_DEFAULT, Constants.PRICE_DEFAULT));
		setCurrencies.add(new CurrencyData("EURO - BILON", "EURb", Constants.PRICE_DEFAULT, Constants.PRICE_DEFAULT));
	}

	private final JButton jButtonSynchronize = new JButton();
	private final List<JTextField> listSellPrice = new ArrayList<>();
	private final List<JTextField> listBuyPrice = new ArrayList<>();

	private IDataSynchronizer dataSynchronizer;

	@Autowired
	public void setDataSynchronizer(IDataSynchronizer dataSynchronizer) {
		this.dataSynchronizer = dataSynchronizer;
	}

	void initialize() throws ParseException {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(true);

		int buttonHeight = 20;
		jButtonSynchronize.setMinimumSize(new Dimension(180, buttonHeight));
		jButtonSynchronize.setHorizontalTextPosition(SwingConstants.LEFT);

		jButtonSynchronize.setText("Synchronizacja danych");
		jButtonSynchronize.setBorderPainted(false);
		jButtonSynchronize.setFocusPainted(false);
		jButtonSynchronize.setBackground(Color.BLACK);
		jButtonSynchronize.setContentAreaFilled(false);

		jButtonSynchronize.addActionListener(new ListenerSynchronization());

		// delta from the top
		Box toolBox = new Box(BoxLayout.X_AXIS);
		toolBox.add(jButtonSynchronize);

		add(toolBox);
		add(Box.createRigidArea(new Dimension(0, 235 - buttonHeight)));

		for (int i = 0; i < Constants.FIELDS_COUNT; i++) {
			JFormattedTextField jTextFiledBuyPrice = createTextField();
			listBuyPrice.add(jTextFiledBuyPrice);

			JFormattedTextField jTextFiledSellPrice = createTextField();
			listSellPrice.add(jTextFiledSellPrice);

			Box rowBox = new Box(BoxLayout.X_AXIS);
			rowBox.add(Box.createRigidArea(new Dimension(Constants.FIELD_OFFSET_BUY, 0)));
			rowBox.add(jTextFiledBuyPrice);
			rowBox.add(Box.createRigidArea(new Dimension(Constants.FIELD_OFFSET_SELL, 0)));
			rowBox.add(jTextFiledSellPrice);

			add(rowBox);
			add(Box.createRigidArea(new Dimension(0, 2)));

		}

		refreshToolBoxVisibility(true);
	}

	private void synchronize() {

		dataSynchronizer.synchronize(setCurrencies);

		int fieldNumber = 0;
		for (CurrencyData currencyData : setCurrencies) {

			String sellPrice = Util.getCourse(currencyData, currencyData.getSellPrice());
			String buyPrice = Util.getCourse(currencyData, currencyData.getBuyPrice());

			listSellPrice.get(fieldNumber).setText(sellPrice);
			listBuyPrice.get(fieldNumber).setText(buyPrice);

			++fieldNumber;
		}
	}

	private JFormattedTextField createTextField() throws ParseException {

		DefaultFormatter formatter = new DefaultFormatter() {

			private final Pattern pattern = Pattern.compile(Constants.REG_EXP_COURSE);

			@Override
			public Object stringToValue(String string) throws ParseException {

				if (!pattern.matcher(string).matches()) {
					throw new ParseException(string, 0);
				}

				return super.stringToValue(string);
			}

			@Override
			public String valueToString(Object value) throws ParseException {
				String str = (String) value;

				BigDecimal valueBd = value == null || "".equals(value) ? Constants.PRICE_DEFAULT : new BigDecimal(str);
				return super.valueToString(Util.priceToString(valueBd));
			}

		};
		formatter.setAllowsInvalid(false);

		JFormattedTextField jTextFiled = new JFormattedTextField(formatter);
		Util.setComponentSize(jTextFiled, 170, 45);
		jTextFiled.setForeground(Color.RED);
		jTextFiled.setBorder(null);
		jTextFiled.setOpaque(false);
		jTextFiled.setFont(new Font("Curier New", Font.BOLD, 36));

		if (log.isDebugEnabled()) {
			// jTextFiled.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		}

		jTextFiled.setText(Util.priceToString(Constants.PRICE_DEFAULT));

		jTextFiled.setInputVerifier(new ListenerTextField());

		return jTextFiled;
	}

	void setState(boolean isVisible) {

		refreshToolBoxVisibility(isVisible);

	}

	private void refreshToolBoxVisibility(boolean isActive) {

		// jButtonSynchronize.setFont(jButtonSynchronize.getFont().)
		// jButtonSynchronize.setEnabled(isActive);
		jButtonSynchronize.setForeground(isActive ? Color.RED : Color.BLACK);

	}

}