package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.effects.IVanishEffect;

public class VanishEffect extends BaseEffect implements IVanishEffect {
	public VanishEffect() {
		super(MobEffectCategory.NEUTRAL,8356754);
	}
	public boolean shouldUpdateClient() {
		return true;
	}
	public boolean isVanished(LivingEntity var1, int var2, int var3) {
		return true;
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean disableParticles() {
		return false;
	}
}