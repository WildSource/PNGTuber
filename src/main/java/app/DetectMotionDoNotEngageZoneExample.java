package app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionDetectorAlgorithm;
import com.github.sarxos.webcam.WebcamMotionDetectorDefaultAlgorithm;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.util.ImageUtils;

public class DetectMotionDoNotEngageZoneExample extends JFrame implements Runnable, WebcamPanel.Painter {

	private static final long serialVersionUID = 1L;

	private static final BufferedImage TEXTURE = ImageUtils.readFromResource("commission.png");
	private static final Paint PAINT = new TexturePaint(TEXTURE, new Rectangle(0, 0, 10, 10));

	private static final Dimension RESOLUTION = WebcamResolution.VGA.getSize();
	private static final Rectangle DO_NOT_ENGAGE_ZONE = new Rectangle(0, 0, 650, 350);

	private final Webcam webcam;
	private final WebcamPanel panel;
	private final WebcamMotionDetector detector;

	private DetectVariable isMotionDetectVariable;

	public DetectMotionDoNotEngageZoneExample(DetectVariable data) {
		this.isMotionDetectVariable = data;

		setTitle("Motion Detector Demo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		webcam = Webcam.getDefault();
		webcam.setViewSize(RESOLUTION);
		webcam.open(true);

		panel = new WebcamPanel(webcam, false);
		panel.setPainter(this);
		panel.start();

		WebcamMotionDetectorAlgorithm algorithm = new WebcamMotionDetectorDefaultAlgorithm();
		algorithm.setDoNotEngageZones(Arrays.asList(DO_NOT_ENGAGE_ZONE));

		detector = new WebcamMotionDetector(webcam, algorithm, 500);
		detector.setPixelThreshold(20);
		detector.start();

		add(panel);

		pack();
		setVisible(true);
	}

	public void paintPanel(WebcamPanel panel, Graphics2D g2) {
		panel.getDefaultPainter().paintPanel(panel, g2);
	}

	public void paintImage(WebcamPanel panel, BufferedImage image, Graphics2D g2) {

		final double s = detector.getMotionArea();
		final Point cog = detector.getMotionCog();

		final int x = DO_NOT_ENGAGE_ZONE.x;
		final int y = DO_NOT_ENGAGE_ZONE.y;
		final int w = DO_NOT_ENGAGE_ZONE.width;
		final int h = DO_NOT_ENGAGE_ZONE.height;

		Graphics2D g = image.createGraphics();
		g.setPaint(PAINT);
		g.fillRect(x, y, w, h);
		g.setColor(Color.WHITE);
		g.drawString(String.format("Area: %.2f%%", s), 10, 20);

		if (detector.isMotion()) {
			flip();
			g.setStroke(new BasicStroke(2));
			g.setColor(Color.RED);
			g.drawOval(cog.x - 5, cog.y - 5, 10, 10);
		} else {
			flip();
			g.setColor(Color.GREEN);
			g.drawRect(cog.x - 5, cog.y - 5, 10, 10);
		}

		g.dispose();

		panel.getDefaultPainter().paintImage(panel, image, g2);
	}

	public void flip() {
		this.isMotionDetectVariable.flip();
	}

	public void run() {
		new DetectMotionDoNotEngageZoneExample(isMotionDetectVariable);
	}
}