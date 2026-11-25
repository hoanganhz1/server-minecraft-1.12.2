package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import java.util.Optional;
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
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.enums.NetType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class CaughtInNetEffect extends BaseEffect implements ITextureOverlayEffect {
	// Fields
	public static final ResourceLocation NET_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/armor/net.png");
	public static final ResourceLocation KAIROSEKI_NET_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/armor/kairoseki_net.png");
	private Holder<MobEffect> holder;
	private NetType type;

	public CaughtInNetEffect(NetType var1) {
		super(MobEffectCategory.HARMFUL,WyHelper.hexToRGB("#000000").getRGB());
		this.type = var1;
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(NeoForgeMod.SWIM_SPEED,AttributeHelper.EFFECT_SWIM_SPEED_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(ModAttributes.JUMP_HEIGHT,AttributeHelper.EFFECT_JUMP_HEIGHT_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_SPEED,AttributeHelper.EFFECT_ATTACK_SPEED_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		this.addAttributeModifier(ModAttributes.MINING_SPEED,AttributeHelper.EFFECT_MINING_SPEED_ID,-1D,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
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
			if ((this.type) == (NetType.NORMAL)){
				if (var1.isOnFire()){
					var1.removeEffect(this.holder);
				}
				if (CombatHelper.isLogiaBlocking(var1)){
					var1.removeEffect(this.holder);
				}else{
					return true;
				}
			}else{
				if ((this.type) == (NetType.KAIROSEKI)){
					Predicate<IAbility> var4_1 = (l0) -> {
			return ((l0) != (null)) && ((l0.getCore().getCategory()) == (AbilityCategory.DEVIL_FRUITS));
		};
					AbilityHelper.disableAbilities(var1,var3_1.getDuration(),var4_1);
				}else{
					return true;
				}
			}
			return true;
		}
	}
	public void startEffect(LivingEntity var1, MobEffectInstance var2) {
		ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(var1,ModAnimations.CAUGHT_IN_NET,var2.getDuration(),true),var1);
	}
	public void stopEffect(LivingEntity var1) {
		ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(var1,ModAnimations.CAUGHT_IN_NET),var1);
	}
	public boolean isRemoveable() {
		return false;
	}
	public boolean isBlockingSwings() {
		return true;
	}
	public boolean isBlockingRotation() {
		return true;
	}
	public NetType getType() {
		return this.type;
	}
	public ResourceLocation getBodyTexture(int var1, int var2) {
		switch (CaughtInNetEffect$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NetType[this.type.ordinal()]){
		case 1:

		case 2:

		default:
			return NET_TEXTURE;
		}
	}
}