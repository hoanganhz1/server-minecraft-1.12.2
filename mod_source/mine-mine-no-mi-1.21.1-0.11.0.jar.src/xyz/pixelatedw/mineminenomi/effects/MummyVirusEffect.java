package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITransmisibleByProximityEffect;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class MummyVirusEffect extends BaseEffect implements IColorOverlayEffect, ITransmisibleByProximityEffect {
	// Fields
	private static final Color OVERLAY_COLOR = new Color(0F,0F,0F,0.5F);

	public MummyVirusEffect() {
		super(MobEffectCategory.HARMFUL,WyHelper.hexToRGB("#beb936").getRGB());
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return true;
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		if (var1.level().isClientSide){
			return false;
		}else{
			MobEffectInstance var3 = var1.getEffect((Holder)(ModEffects.MUMMY_VIRUS));
			if ((var3) == (null)){
				return false;
			}else{
				int var4 = var3.getDuration();
				if (((var4) % (10)) == (0)){
					if (!(var1.hasEffect((Holder)(ModEffects.HUNGER)))){
						var1.addEffect(new MobEffectInstance((Holder)(ModEffects.HUNGER),20,0));
					}
					if (!(var1.hasEffect((Holder)(ModEffects.BLEEDING)))){
						var1.addEffect(new MobEffectInstance((Holder)(ModEffects.BLEEDING),20,0));
					}
				}
				if ((var4) < (20)){
					var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MUMMY_VIRUS_ANTIBODY),840,0));
				}
				return true;
			}
		}
	}
	@Nullable()
	public Color getBodyOverlayColor(int var1, int var2) {
		return null;
	}
	public Color getViewOverlayColor(int var1, int var2) {
		return OVERLAY_COLOR;
	}
	public boolean isTransmisibleByProximity() {
		return true;
	}
	public float poximityTransmisionDistance() {
		return 2F;
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean isLingering() {
		return true;
	}
}