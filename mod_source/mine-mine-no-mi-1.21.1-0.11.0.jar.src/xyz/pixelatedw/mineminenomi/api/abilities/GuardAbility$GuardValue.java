package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.util.Mth;

public class GuardAbility$GuardValue {
	// Fields
	 GuardAbility$GuardKind kind;
	private GuardAbility$GuardBreakKind breakKind;
	 float value;
	private float breakLimit;

	private GuardAbility$GuardValue(GuardAbility$GuardKind var1, float var2) {
		this.kind = GuardAbility$GuardKind.FIXED_AMOUNT;
		this.breakKind = GuardAbility$GuardBreakKind.PER_HIT;
		this.kind = var1;
		this.value = var2;
	}
	private GuardAbility$GuardValue(GuardAbility$GuardKind var1, float var2, GuardAbility$GuardBreakKind var3, float var4) {
		this.kind = GuardAbility$GuardKind.FIXED_AMOUNT;
		this.breakKind = GuardAbility$GuardBreakKind.PER_HIT;
		this.kind = var1;
		this.value = var2;
		this.setBreakKind(var3);
		this.setBreakLimit(var4);
	}
	public static GuardAbility$GuardValue fixedAmount(float var0) {
		return new GuardAbility$GuardValue(GuardAbility$GuardKind.FIXED_AMOUNT,var0);
	}
	public static GuardAbility$GuardValue fixedAmount(float var0, GuardAbility$GuardBreakKind var1, float var2) {
		return new GuardAbility$GuardValue(GuardAbility$GuardKind.FIXED_AMOUNT,var0,var1,var2);
	}
	public static GuardAbility$GuardValue percentage(float var0) {
		float var1 = (float)(Mth.clamp((double)(var0),0D,1D));
		return new GuardAbility$GuardValue(GuardAbility$GuardKind.PERCENTAGE,var1);
	}
	public static GuardAbility$GuardValue percentage(float var0, GuardAbility$GuardBreakKind var1, float var2) {
		float var3 = (float)(Mth.clamp((double)(var0),0D,1D));
		return new GuardAbility$GuardValue(GuardAbility$GuardKind.PERCENTAGE,var3,var1,var2);
	}
	public void setBreakKind(GuardAbility$GuardBreakKind var1) {
		this.breakKind = var1;
	}
	public float getValue() {
		return this.value;
	}
	public GuardAbility$GuardKind getKind() {
		return this.kind;
	}
	public GuardAbility$GuardBreakKind getBreakKind() {
		return this.breakKind;
	}
	public float getBreakLimit() {
		return this.breakLimit;
	}
	public void setBreakLimit(float var1) {
		this.breakLimit = var1;
	}
}