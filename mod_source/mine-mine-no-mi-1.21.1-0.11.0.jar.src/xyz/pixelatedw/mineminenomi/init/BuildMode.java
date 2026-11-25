package xyz.pixelatedw.mineminenomi.init;

public enum BuildMode {
	// Fields
	RELEASE,
	DEV,
	EARLY_ACCESS,
	QA,
	PROMO,
	PUBLIC_QA;
	public static final BuildMode MODE = RELEASE;

	private BuildMode() {
	}
}