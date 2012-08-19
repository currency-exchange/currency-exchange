package pl.jw.currencyexchange.gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

class PanelBackground extends JPanel {

	private Image background;

	void initialize(Image image) {
		background = image;
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}
}