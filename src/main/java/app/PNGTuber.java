package app;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PNGTuber extends JFrame implements Runnable {

	private static final long serialVersionUID = -4155675855358448509L;

	private DetectVariable isMotionVariable;

	private PNGTuberImage image;

	public PNGTuber(DetectVariable data, PNGTuberImage image) {
		this.isMotionVariable = data;
		this.image = image;
	}

	public boolean getMotion() {
		return this.isMotionVariable.getMotion();
	}

	public JPanel getImage() {
		return this.image;
	}

	public void run() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(500, 300));
		setResizable(false);
		setTitle("PNGTuber");
		setVisible(true);

		while (true) {
			if (getMotion()) {
				add(getImage());
				pack();
			}
			if (!getMotion()) {
				remove(getImage());
			}
		}

	}

}
