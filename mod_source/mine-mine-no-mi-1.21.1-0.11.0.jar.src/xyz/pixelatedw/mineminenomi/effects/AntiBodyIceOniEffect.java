package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class AntiBodyIceOniEffect extends BaseEffect {
	public AntiBodyIceOniEffect() {
		super(MobEffectCategory.BENEFICIAL,WyHelper.hexToRGB("#2a7b7d").getRGB());
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return true;
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		Level var3 = var1.level();
		if (((var3) != (null)) && (!(var3.isClientSide))){
			MobEffectInstance var4 = var1.getEffect((Holder)(ModEffects.ICE_ONI_ANTIBODY));
			if ((var4) == (null)){
				return false;
			}else{
				if (((var4.getDuration()) % (10)) == (0)){
					var1.removeEffect((Holder)(ModEffects.ICE_ONI));
				}
				return true;
			}
		}else{
			return false;
		}
	}
	public boolean isLingering() {
		return true;
	}
}