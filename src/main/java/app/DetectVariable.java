package app;

public class DetectVariable {
	private static volatile boolean isMotion = false;

	public boolean getMotion() {
		return isMotion;
	}

	public void flip() {
		if (isMotion) {
			isMotion = false;
		}
		if (!isMotion) {
			isMotion = true;
		}
	}
}
