package pl.jw.currencyexchange.gui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import pl.jw.currencyexchange.Constants;
import pl.jw.currencyexchange.Util;

public class FrameBoard extends JFrame {

	private final class MouseListenerDecoration extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			dispose();

			boolean newState = !isUndecorated();
			setUndecorated(newState);

			setExtendedState(isUndecorated() ? Frame.MAXIMIZED_BOTH : Frame.NORMAL);

			panelBoard.setState(!newState);

			setVisible(true);
		}
	}

	@Autowired
	private PanelBoard panelBoard;

	private final JLayer<JPanel> jLayer = new JLayer<>();

	public void setPanelBoard(PanelBoard panelBoard) {
		this.panelBoard = panelBoard;
	}

	public void initialize() throws IOException, ParseException {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panelBoard.initialize();
		panelBoard.setOpaque(false);

		// setLocationRelativeTo(null);

		Util.setComponentSize(this, 1024, 768 + 25);

		final BufferedImage backgroundImage = ImageIO.read(ClassLoader.getSystemResource(Constants.IMAGE_BACKGROUND));

		jLayer.setView(panelBoard);
		jLayer.setUI(new LayerUIBoard(backgroundImage));

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		Box horizontalBox = new Box(BoxLayout.X_AXIS);
		horizontalBox.add(Box.createHorizontalGlue());
		horizontalBox.add(jLayer);
		horizontalBox.add(Box.createHorizontalGlue());

		getContentPane().setBackground(Color.BLACK);
		getContentPane().add(Box.createVerticalGlue());
		getContentPane().add(horizontalBox);
		getContentPane().add(Box.createVerticalGlue());

		panelBoard.addMouseListener(new MouseListenerDecoration());

	}

}