package xyz.pixelatedw.mineminenomi.abilities.beta;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffects;
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

public class BetaImmunityAbility extends EffectImmunityAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BetaImmunityAbility>> INSTANCE;
	private static final EffectImmunityAbility$ImmunityInfo IMMUNITY_INFO;

	public BetaImmunityAbility(AbilityCore<? extends BetaImmunityAbility> var1) {
		super((AbilityCore)(var1),IMMUNITY_INFO);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BetaImmunityAbility::new)))).setHidden().build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("beta_immunities","Beta Immunities",var0));
		Holder[] var1 = new Holder[]{ModEffects.STICKY};
		Holder[] var2 = new Holder[]{MobEffects.CONFUSION,MobEffects.MOVEMENT_SLOWDOWN};
		IMMUNITY_INFO = new EffectImmunityAbility$ImmunityInfo().addImmunityEffects(var1).addImmunityEffects(var2);
	}
}