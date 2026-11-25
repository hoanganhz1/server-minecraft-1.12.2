package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveStatBonusAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ChangeStatsComponentTooltip;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class FishmanPassiveBonusesAbility extends PassiveStatBonusAbility {
	// Fields
	private static final AttributeModifier SWIM_SPEED_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","fishman_passive.swim_speed"),3D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	private static final AttributeModifier DAMAGE_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","fishman_passive.damage"),3D,AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier TOUGHNESS_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","fishman_passive.toughness"),2D,AttributeModifier.Operation.ADD_VALUE);
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<FishmanPassiveBonusesAbility>> INSTANCE;

	public FishmanPassiveBonusesAbility(AbilityCore<FishmanPassiveBonusesAbility> var1) {
		super((AbilityCore)(var1));
		this.pushStaticAttribute(NeoForgeMod.SWIM_SPEED,SWIM_SPEED_MODIFIER);
		this.pushStaticAttribute(Attributes.ATTACK_DAMAGE,DAMAGE_MODIFIER);
		this.pushStaticAttribute(ModAttributes.TOUGHNESS,TOUGHNESS_MODIFIER);
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::duringPassiveEvent))));
	}
	private void duringPassiveEvent(LivingEntity var1) {
		if (var1.isEyeInFluidType(((FluidType)(NeoForgeMod.WATER_TYPE.value())))){
			var1.setAirSupply(300);
			var1.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,250,0,false,false));
			var1.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,100,0,false,false));
		}
	}
	
	
	private static boolean canUnlock(LivingEntity var0) {
		return ((Boolean)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isFishman)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l1,l0,AbilityCategory.RACIAL,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(FishmanPassiveBonusesAbility::new)))).setIcon(ModResources.PERK_ICON).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(FishmanPassiveBonusesAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("fishman_passive_bonuses","Fishman Passive Bonuses",var0));
	}
public Predicate<LivingEntity> getCheck() {
		Predicate<LivingEntity> var1 = (l0) -> {
			boolean lv1_1 = ((Boolean)(DevilFruitData.get(l0).map((Function)(((Function<DevilFruitData, Boolean>)(DevilFruitData::hasAnyDevilFruit)))).orElse(Boolean.valueOf(false)))).booleanValue();
			if ((l0.isInWater()) && ((lv1_1) && (!(l0.hasEffect(ModEffects.BUBBLY_CORAL))))){
				return false;
			}else{
				return true;
			}
		};
		return var1;
	}public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<ChangeStatsComponentTooltip> var3 = (l0) -> {
		};
		ChangeStatsComponentTooltip.generate(var2,var3);
	}}