package xyz.pixelatedw.mineminenomi.api.challenges;

public enum InProgressChallenge$Result {
	// Fields
	TBD,
	WIN,
	TIMEOUT,
	DEATH;

	InProgressChallenge$Result() {
	}
	public boolean isWin() {
		return this.equals(WIN);
	}
	public boolean isLoss() {
		return (this.equals(TIMEOUT)) || (this.equals(DEATH));
	}
}