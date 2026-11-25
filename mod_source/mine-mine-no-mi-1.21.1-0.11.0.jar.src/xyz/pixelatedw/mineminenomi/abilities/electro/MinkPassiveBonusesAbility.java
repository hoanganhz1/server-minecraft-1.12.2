package xyz.pixelatedw.mineminenomi.abilities.electro;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveStatBonusAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ChangeStatsComponentTooltip;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModTags$Biomes;

public class MinkPassiveBonusesAbility extends PassiveStatBonusAbility {
	// Fields
	private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","mink_passive.speed"),0.5D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	private static final AttributeModifier JUMP_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","mink_passive.jump_bonus"),1.6D,AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier JUMP_RESISTANCE_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","mink_passive.fall_resistance"),2.25D,AttributeModifier.Operation.ADD_VALUE);
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<MinkPassiveBonusesAbility>> INSTANCE;

	public MinkPassiveBonusesAbility(AbilityCore<MinkPassiveBonusesAbility> var1) {
		super((AbilityCore)(var1));
		this.pushStaticAttribute(Attributes.MOVEMENT_SPEED,SPEED_MODIFIER);
		this.pushStaticAttribute(ModAttributes.JUMP_HEIGHT,JUMP_MODIFIER);
		this.pushStaticAttribute(ModAttributes.FALL_RESISTANCE,JUMP_RESISTANCE_MODIFIER);
	}
	private static boolean canUnlock(LivingEntity var0) {
		EntityStatsData var1 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return false;
		}else{
			return var1.isMink();
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(MinkPassiveBonusesAbility::new)))).setIcon(ModResources.PERK_ICON).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(MinkPassiveBonusesAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("mink_passive_bonuses","Mink Passive Bonuses",var0));
	}
	public Predicate<LivingEntity> getCheck() {
		Predicate<LivingEntity> var1 = (l0) -> {
			BlockPos lv1_1 = l0.blockPosition();
			if (l0.level().getBiome(lv1_1).is(ModTags$Biomes.MINK_DEBUFF_BIOMES)){
				return false;
			}else{
				return true;
			}
		};
		return var1;
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<ChangeStatsComponentTooltip> var3 = (l0) -> {
		};
		ChangeStatsComponentTooltip.generate(var2,var3);
	}
}