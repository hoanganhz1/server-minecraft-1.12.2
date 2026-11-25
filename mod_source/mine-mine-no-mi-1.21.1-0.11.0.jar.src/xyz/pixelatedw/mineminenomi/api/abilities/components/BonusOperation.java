package xyz.pixelatedw.mineminenomi.api.abilities.components;

public enum BonusOperation {
	// Fields
	ADD,
	MUL;

	private BonusOperation() {
	}
	public String toString() {
		return this.toString().toUpperCase();
	}
}