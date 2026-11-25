package xyz.pixelatedw.mineminenomi.abilities;

import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IAbilityGoalFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class LogiaHeatImmunityAbility extends EffectImmunityAbility {
	// Fields
	private static final EffectImmunityAbility$ImmunityInfo IMMUNITY_INFO = HEAT_RESISTANCES.clone().addLogiaImmunities();
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<LogiaHeatImmunityAbility>> INSTANCE;

	public LogiaHeatImmunityAbility(AbilityCore<? extends LogiaHeatImmunityAbility> var1) {
		super((AbilityCore)(var1),IMMUNITY_INFO);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			AbilityCore$IAbilityGoalFactory[] lv1_2 = new AbilityCore$IAbilityGoalFactory[1];
			AbilityCore$IAbilityGoalFactory lv1_3 = AlwaysActiveAbilityWrapperGoal::new;
			lv1_2[0] = lv1_3;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(LogiaHeatImmunityAbility::new)))).setHidden().addGoalWrapper(lv1_2).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("heat_logia_immunities","Heat Logia Immunities",var0));
	}
}