package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.core.Holder;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.EffectImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.EffectImmunityAbility$ImmunityInfo;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class YukiImmunityAbility extends EffectImmunityAbility {
	// Fields
	private static final EffectImmunityAbility$ImmunityInfo IMMUNITY_INFO;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<YukiImmunityAbility>> INSTANCE;

	public YukiImmunityAbility(AbilityCore<? extends YukiImmunityAbility> var1) {
		super((AbilityCore)(var1),IMMUNITY_INFO);
	}
	static  {
		Holder[] var0 = new Holder[]{ModEffects.FROSTBITE};
		IMMUNITY_INFO = new EffectImmunityAbility$ImmunityInfo().addLogiaImmunities().addImmunityEffects(var0);
		AbilityRegistryEntry var1 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(YukiImmunityAbility::new)))).setHidden().build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("yuki_immunities","Yuki Immunities",var1));
	}
}