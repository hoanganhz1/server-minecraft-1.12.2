package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;

public class NoHandsEffect extends BaseEffect implements IBindHandsEffect {
	public NoHandsEffect() {
		super(MobEffectCategory.NEUTRAL,0);
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return true;
	}
	public boolean isBlockingSwings() {
		return true;
	}
}