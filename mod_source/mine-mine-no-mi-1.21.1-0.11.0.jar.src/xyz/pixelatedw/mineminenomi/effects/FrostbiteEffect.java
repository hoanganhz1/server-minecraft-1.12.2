package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.damagesources.BaseDamageSource;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.EffectsHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public class FrostbiteEffect extends DamageOverTimeEffect implements ITextureOverlayEffect, IBindBodyEffect {
	// Fields
	private boolean autoIncrease;

	public FrostbiteEffect() {
		this(false);
	}
	public FrostbiteEffect(boolean var1) {
		super((l0) -> {
			return ModDamageSources.getInstance((LevelAccessor)(l0.level())).frostbites();
		},1F,40);
		this.autoIncrease = var1;
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-0.03999999910593033D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(ModAttributes.JUMP_HEIGHT,AttributeHelper.EFFECT_JUMP_HEIGHT_ID,-0.05000000074505806D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(NeoForgeMod.SWIM_SPEED,AttributeHelper.EFFECT_SWIM_SPEED_ID,-0.05000000074505806D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-0.03999999910593033D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}
	public boolean shouldUpdateClient() {
		return true;
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		super.applyEffectTick(var1,var2);
		MobEffectInstance var3 = var1.getEffect((Holder)(ModEffects.FROSTBITE));
		if ((var3) == (null)){
			return false;
		}else{
			int var4 = var3.getDuration();
			int var5 = var3.getAmplifier();
			if ((this.autoIncrease) && (((var4) % (120)) == (0))){
				((IMobEffectInstanceMixin)(var3)).setAmplifier((var5) + (1));
				WyHelper.sendApplyEffectToAllNearby(var1,var1.position(),100,var3);
			}
			if ((var2) > (10)){
				if ((var1.getEffect((Holder)(ModEffects.FROSTBITE))) != (null)){
					var1.addEffect(new MobEffectInstance((Holder)(ModEffects.FROZEN),var4,0));
				}
				var1.removeEffect((Holder)(ModEffects.FROSTBITE));
			}
			return true;
		}
	}
	public boolean isBlockingRotation() {
		return false;
	}
	public ResourceLocation getBodyTexture(int var1, int var2) {
		if ((var2) < (2)){
			return ModResources.FROSTBITE_1;
		}else{
			if (((var2) >= (2)) && ((var2) < (4))){
				return ModResources.FROSTBITE_2;
			}else{
				if (((var2) >= (4)) && ((var2) < (6))){
					return ModResources.FROSTBITE_3;
				}else{
					if (((var2) >= (6)) && ((var2) < (8))){
						return ModResources.FROSTBITE_4;
					}else{
						return ModResources.FROSTBITE_5;
					}
				}
			}
		}
	}
	public static void addFrostbiteStacks(LivingEntity var0, int var1) {
		if ((var0) == (null)){
			return;
		}else{
			if ((var1) <= (0)){
				var1 = 1;
			}
			MobEffectInstance var2 = var0.getEffect((Holder)(ModEffects.FROSTBITE));
			if ((var2) == (null)){
				MobEffectInstance var3 = new MobEffectInstance((Holder)(ModEffects.FROSTBITE),(20) * (var1),var1);
				var0.addEffect(var3);
			}else{
				EffectsHelper.updateEffect(var0,var2,(var2.getDuration()) + ((20) * (var1)),(var2.getAmplifier()) + (var1));
			}
			return;
		}
	}
}