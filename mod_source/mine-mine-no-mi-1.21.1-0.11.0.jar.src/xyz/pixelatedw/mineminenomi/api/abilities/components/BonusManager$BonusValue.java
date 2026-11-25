package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.UUID;

public class BonusManager$BonusValue {
	// Fields
	 UUID id;
	private BonusOperation type;
	private String name;
	private float value;

	public BonusManager$BonusValue(UUID var1, String var2, BonusOperation var3, float var4) {
		this.id = var1;
		this.name = var2;
		this.type = var3;
		this.value = var4;
	}
	public UUID getUUID() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public BonusOperation getType() {
		return this.type;
	}
	public float getValue() {
		return this.value;
	}
	public String toString() {
		String var1 = this.name;
		String var2 = this.getType().name();
		float var3 = this.getValue();
		String var4 = var1 + " | " + var2 + " " + var3;
		return var4;
	}
}