package xyz.pixelatedw.mineminenomi.abilities;

import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class LogiaImmunityAbility extends EffectImmunityAbility {
	// Fields
	private static final EffectImmunityAbility$ImmunityInfo IMMUNITY_INFO = new EffectImmunityAbility$ImmunityInfo().addLogiaImmunities();
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<LogiaImmunityAbility>> INSTANCE;

	public LogiaImmunityAbility(AbilityCore<? extends LogiaImmunityAbility> var1) {
		super((AbilityCore)(var1),IMMUNITY_INFO);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(LogiaImmunityAbility::new)))).setHidden().build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("logia_immunities","Logia Immunities",var0));
	}
}