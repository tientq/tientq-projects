package control;

public class Setting {
	private static boolean showCandidate;
	private static boolean showError;
	private static boolean showLighting;
	private static boolean showBlock;
	private static boolean smartDigit;
	private static boolean sound;
	private static boolean note;

	public Setting() {
		setShowCandidate(false);
		setShowError(true);
		setShowLighting(true);
		setShowBlock(true);
		setSmartDigit(false);
		setSound(true);
		setNote(false);
	}

	public static boolean isShowCandidate() {
		return showCandidate;
	}

	public static void setShowCandidate(boolean b) {
		showCandidate = b;
	}

	public static boolean isShowError() {
		return showError;
	}

	public static void setShowError(boolean b) {
		showError = b;
	}

	public static boolean isShowLighting() {
		return showLighting;
	}

	public static void setShowLighting(boolean b) {
		showLighting = b;
	}

	public static boolean isShowBlock() {
		return showBlock;
	}

	public static void setShowBlock(boolean b) {
		showBlock = b;
	}

	public static boolean isSmartDigit() {
		return smartDigit;
	}

	public static void setSmartDigit(boolean b) {
		smartDigit = b;
	}

	public static boolean isSound() {
		return sound;
	}

	public static void setSound(boolean b) {
		sound = b;
	}

	public static boolean isNote() {
		return note;
	}

	public static void setNote(boolean note) {
		Setting.note = note;
	}
}
