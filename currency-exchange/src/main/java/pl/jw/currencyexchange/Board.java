package pl.jw.currencyexchange;

import java.io.IOException;
import java.text.ParseException;

import javax.swing.SwingUtilities;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pl.jw.currencyexchange.gui.FrameBoard;

public class Board {

	public static void main(String[] args) throws IOException, ParseException {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "application-context.xml" });

		final FrameBoard frameBoard = context.getBean(FrameBoard.class);
		frameBoard.initialize();

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				frameBoard.setVisible(true);

			}
		});

	}
}
