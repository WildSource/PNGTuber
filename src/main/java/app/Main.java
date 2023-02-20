package app;

public class Main {

	public static void main(String[] args) {
		PNGTuberImage image = new PNGTuberImage("commission.png");
		DetectVariable volatileDetectVariable = new DetectVariable();
		Thread detecThread = new Thread(new DetectMotionDoNotEngageZoneExample(volatileDetectVariable));
		Thread camTuberThread = new Thread(new PNGTuber(volatileDetectVariable, image));

		detecThread.start();
		camTuberThread.start();
	}

}
