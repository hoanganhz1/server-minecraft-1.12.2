package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class ChiyupopoEffect extends BaseEffect {
	// Fields
	private float oldHealth;

	public ChiyupopoEffect() {
		super(MobEffectCategory.BENEFICIAL,WyHelper.hexToRGB("#FFC0CB").getRGB());
		this.oldHealth = 0F;
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		if ((var1.getHealth()) < (var1.getMaxHealth())){
			var1.heal(1F);
		}
		return true;
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		int var3 = (50) >> (var2);
		if ((var3) > (0)){
			return ((var1) % (var3)) == (0);
		}else{
			return true;
		}
	}
	public void startEffect(LivingEntity var1, MobEffectInstance var2) {
		this.oldHealth = var1.getHealth();
	}
	public void stopEffect(LivingEntity var1) {
		var1.setHealth(this.oldHealth);
	}
	public boolean isRemoveable() {
		return false;
	}
}