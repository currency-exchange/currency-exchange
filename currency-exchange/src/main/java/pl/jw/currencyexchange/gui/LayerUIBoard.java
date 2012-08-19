package pl.jw.currencyexchange.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.LayerUI;

final class LayerUIBoard extends LayerUI<JPanel> {
	private final BufferedImage backgroundImage;

	LayerUIBoard(BufferedImage backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		g.drawImage(backgroundImage, 0, 0, null);

		super.paint(g, c);

	}
}