package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;

public enum VariAbility$Mode {
	// Fields
	ONE_MILLION_V(VariAbility.ONE_MILLION_V_VARI_NAME, 10F, 200F),
	TWENTY_MILLION_V(VariAbility.TWENTY_MILLION_V_VARI_NAME, 35F, 300F),
	ONE_HUNDRED_MILLION_V(VariAbility.ONE_HUNDRED_MILLION_V_VARI_NAME, 50F, 500F),
	TWO_HUNDRED_MILLION_V(VariAbility.TWO_HUNDRED_MILLION_V_VARI_NAME, 65F, 600F);
	private Component displayName;
	private float damage;
	 float cooldown;

	private VariAbility$Mode(Component var3, float var4, float var5) {
		this.displayName = var3;
		this.damage = var4;
		this.cooldown = var5;
	}
	public Component getDisplayName() {
		return this.displayName;
	}
	public float getDamage() {
		return this.damage;
	}
	public float getCooldown() {
		return this.cooldown;
	}
}