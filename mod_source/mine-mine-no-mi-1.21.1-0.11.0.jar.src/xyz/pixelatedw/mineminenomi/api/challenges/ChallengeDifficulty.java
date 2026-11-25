package xyz.pixelatedw.mineminenomi.api.challenges;

public enum ChallengeDifficulty {
	// Fields
	STANDARD,
	HARD,
	ULTIMATE;

	private ChallengeDifficulty() {
	}
	public String capitalize() {
		ChallengeDifficulty var1 = values()[this.ordinal()];
		String var2 = var1.name().substring(0,1).toUpperCase();
		String var3 = var1.name().substring(1).toLowerCase();
		String var4 = var2 + var3;
		return var4;
	}
}