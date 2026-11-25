package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import java.util.function.Function;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class DamageOverTimeEffect extends BaseEffect {
	// Fields
	private final float baseDamage;
	private final int freq;
	private Function<LivingEntity, DamageSource> damageFunction;
	private Function<Integer, Float> damageScaling;

	public DamageOverTimeEffect() {
		this((l0) -> {
			return l0.damageSources().magic();
		},2F,20);
	}
	public DamageOverTimeEffect(Function<LivingEntity, DamageSource> var1, float var2, int var3) {
		this(WyHelper.hexToRGB("#000000").getRGB(),var1,var2,var3);
	}
	public DamageOverTimeEffect(int var1, Function<LivingEntity, DamageSource> var2, float var3, int var4) {
		super(MobEffectCategory.HARMFUL,var1);
		this.damageFunction = var2;
		this.baseDamage = var3;
		this.freq = var4;
		DamageOverTimeEffect var5 = this;
		Function<Integer, Float> var6 = (Integer l0) -> {
			return Float.valueOf((this.baseDamage) * ((float)(l0.intValue())));
		};
		this.damageScaling = var6;
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return ((var1) % (this.freq)) == (1);
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		float var3 = Math.max(this.baseDamage,((Float)(((java.util.function.Function)(this.damageScaling)).apply((Object)(Integer.valueOf(var2))))).floatValue());
		var1.invulnerableTime = 0;
		var1.hurt(((DamageSource)(((java.util.function.Function)(this.damageFunction)).apply((Object)(var1)))),var3);
		return true;
	}
	public float getBaseDamage() {
		return this.baseDamage;
	}
	public Function<Integer, Float> getDamageScaling() {
		return this.damageScaling;
	}
	public void setDamageScaling(Function<Integer, Float> var1) {
		this.damageScaling = var1;
	}
	public boolean isRemoveable() {
		return false;
	}
}