package xyz.pixelatedw.mineminenomi.abilities.brawler;

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
import net.minecraft.world.item.ItemStack;
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

public class BrawlerPassiveBonusesAbility extends PassiveStatBonusAbility {
	// Fields
	public static final ResourceLocation BRAWLER_ATTACK_DAMAGE_BONUS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","brawler_bonus.attack_damage");
	public static final ResourceLocation BRAWLER_ATTACK_SPEED_BONUS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","brawler_bonus.attack_speed");
	private static final AttributeModifier BRAWLER_ATTACK_SPEED_MODIFIER = new AttributeModifier(BRAWLER_ATTACK_SPEED_BONUS_ID,-0.5D,AttributeModifier.Operation.ADD_VALUE);
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BrawlerPassiveBonusesAbility>> INSTANCE;

	public BrawlerPassiveBonusesAbility(AbilityCore<BrawlerPassiveBonusesAbility> var1) {
		super((AbilityCore)(var1));
		this.pushDynamicAttribute(ModAttributes.PUNCH_DAMAGE,(Function)(((Function<LivingEntity, AttributeModifier>)(this::getModifier))));
		this.pushStaticAttribute(Attributes.ATTACK_SPEED,BRAWLER_ATTACK_SPEED_MODIFIER);
	}
	private AttributeModifier getModifier(LivingEntity var1) {
		double var2 = this.getBonus(var1);
		return new AttributeModifier(BRAWLER_ATTACK_DAMAGE_BONUS_ID,var2,AttributeModifier.Operation.ADD_VALUE);
	}
	private double getBonus(LivingEntity var1) {
		return (2D) + ((((Double)(EntityStatsData.get(var1).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue()) * (0.0005D));
	}
	private static boolean canUnlock(LivingEntity var0) {
		return ((Boolean)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isBrawler)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BrawlerPassiveBonusesAbility::new)))).setIcon(ModResources.PERK_ICON).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(BrawlerPassiveBonusesAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("brawler_passive_bonus","Brawler Passive Bonuses",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<ChangeStatsComponentTooltip> var3 = (l0) -> {
		};
		ChangeStatsComponentTooltip.generate(var2,var3);
	}
	public Predicate<LivingEntity> getCheck() {
		Predicate<LivingEntity> var1 = (l0) -> {
			if (!(l0.getMainHandItem().isEmpty())){
				return false;
			}else{
				return true;
			}
		};
		return var1;
	}
}