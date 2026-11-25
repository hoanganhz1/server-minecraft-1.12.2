package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class SilentEffect extends BaseEffect {
	public SilentEffect() {
		super(MobEffectCategory.NEUTRAL,0);
	}
	public void startEffect(LivingEntity var1, MobEffectInstance var2) {
		var1.setSilent(true);
	}
	public void stopEffect(LivingEntity var1) {
		var1.setSilent(false);
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean shouldUpdateClient() {
		return true;
	}
}