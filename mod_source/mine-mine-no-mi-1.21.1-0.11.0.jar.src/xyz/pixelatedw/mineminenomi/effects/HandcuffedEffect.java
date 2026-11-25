package xyz.pixelatedw.mineminenomi.effects;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.enums.HandcuffType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class HandcuffedEffect extends BaseEffect implements IBindHandsEffect {
	// Fields
	private Holder<MobEffect> holder;
	private HandcuffType type;

	public HandcuffedEffect(HandcuffType var1) {
		super(MobEffectCategory.HARMFUL,0);
		this.type = var1;
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-0.30000001192092896D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-0.10000000149011612D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(ModAttributes.MINING_SPEED,AttributeHelper.EFFECT_MINING_SPEED_ID,-0.10000000149011612D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
	}
	public boolean shouldUpdateClient() {
		return true;
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return true;
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		if ((this.holder) == (null)){
			Optional var3 = BuiltInRegistries.MOB_EFFECT.getResourceKey(this);
			if (var3.isPresent()){
				this.holder = (Holder) (((Holder)(var1.registryAccess().holder(((ResourceKey)(var3.get()))).orElse(null))));
			}
		}
		MobEffectInstance var3_1 = var1.getEffect(this.holder);
		if ((var3_1) == (null)){
			return false;
		}else{
			if ((var3_1.getDuration()) <= (1)){
				var1.removeEffect(this.holder);
				Predicate<IAbility> var4 = (l0) -> {
			return ((l0) != (null)) && ((l0.getCore().getCategory()) == (AbilityCategory.DEVIL_FRUITS));
		};
				AbilityHelper.enableAbilities(var1,var4);
			}
			if ((this.type) == (HandcuffType.KAIROSEKI)){
				Predicate<IAbility> var4_1 = (l0) -> {
			return ((l0) != (null)) && ((l0.getCore().getCategory()) == (AbilityCategory.DEVIL_FRUITS));
		};
				AbilityHelper.disableAbilities(var1,var3_1.getDuration(),var4_1);
			}
			return true;
		}
	}
	public void startEffect(LivingEntity var1, MobEffectInstance var2) {
		ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(var1,ModAnimations.HANDCUFFED,(var2.getDuration()) + (1),true),var1);
	}
	public void stopEffect(LivingEntity var1) {
		ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(var1,ModAnimations.HANDCUFFED),var1);
		if ((this.type) == (HandcuffType.KAIROSEKI)){
			MobEffectInstance var2 = var1.getEffect(this.holder);
			MobEffectInstance var3 = var2;
			final MobEffectInstance var3_f1 = var3;
			Predicate<IAbility> var4 = (l0) -> {
			if ((l0) == (null)){
				return false;
			}else{
				if ((l0.getCore().getCategory()) != (AbilityCategory.DEVIL_FRUITS)){
					return false;
				}else{
					if (((var3_f1) != (null)) && (((Math.round(((Float)(l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).map((Function)(((Function<DisableComponent, Float>)(DisableComponent::getDisableTime)))).orElse(Float.valueOf(0F)))).floatValue())) - (1)) > (var3_f1.getDuration()))){
						return false;
					}else{
						return true;
					}
				}
			}
		};
			AbilityHelper.enableAbilities(var1,var4);
		}
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean isBlockingSwings() {
		return true;
	}
	public HandcuffType getType() {
		return this.type;
	}
}