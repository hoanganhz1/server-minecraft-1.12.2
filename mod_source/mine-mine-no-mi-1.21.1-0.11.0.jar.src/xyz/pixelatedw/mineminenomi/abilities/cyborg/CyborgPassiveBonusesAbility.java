package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
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

public class CyborgPassiveBonusesAbility extends PassiveStatBonusAbility {
	// Fields
	private static final AttributeModifier CYBORG_ARMOR = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","cyborg_passive.armor"),10D,AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier CYBORG_ARMOR_TOUGHNESS = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","cyborg_passive.armor_toughness"),4D,AttributeModifier.Operation.ADD_VALUE);
	private static final AttributeModifier CYBORG_DAMAGE = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","cyborg_passive.damage"),2D,AttributeModifier.Operation.ADD_VALUE);
	private static final Predicate<LivingEntity> CYBORG_CHECK;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<CyborgPassiveBonusesAbility>> INSTANCE;

	public CyborgPassiveBonusesAbility(AbilityCore<CyborgPassiveBonusesAbility> var1) {
		super((AbilityCore)(var1));
		this.pushStaticAttribute(Attributes.ARMOR,CYBORG_ARMOR);
		this.pushStaticAttribute(Attributes.ARMOR_TOUGHNESS,CYBORG_ARMOR_TOUGHNESS);
		this.pushStaticAttribute(ModAttributes.PUNCH_DAMAGE,CYBORG_DAMAGE);
	}
	public Predicate<LivingEntity> getCheck() {
		return CYBORG_CHECK;
	}
	private static boolean canUnlock(LivingEntity var0) {
		return CYBORG_CHECK.test(var0);
	}
	static  {
		Predicate<LivingEntity> var0 = (l0) -> {
			return ((Boolean)(EntityStatsData.get(l0).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isCyborg)))).orElse(Boolean.valueOf(false)))).booleanValue();
		};
		CYBORG_CHECK = var0;
		AbilityRegistryEntry var1 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(CyborgPassiveBonusesAbility::new)))).setIcon(ModResources.PERK_ICON).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(CyborgPassiveBonusesAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("cyborg_passive_bonuses","Cyborg Passive Bonuses",var1));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<ChangeStatsComponentTooltip> var3 = (l0) -> {
		};
		ChangeStatsComponentTooltip.generate(var2,var3);
	}
}