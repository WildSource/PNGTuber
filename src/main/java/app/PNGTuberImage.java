package app;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class PNGTuberImage extends JPanel {

	private static final long serialVersionUID = -3371361015509060589L;

	Image image;

	public PNGTuberImage(String pathToImage) {
		image = Toolkit.getDefaultToolkit().getImage(pathToImage);

	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(this.image, 500, 500, this);
	}

	public Image getImage() {
		return this.image;
	}

}
