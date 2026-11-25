package xyz.pixelatedw.mineminenomi.abilities.yomi;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.EffectImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.EffectImmunityAbility$ImmunityInfo;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class YomiImmunityAbility extends EffectImmunityAbility {
	// Fields
	private static final EffectImmunityAbility$ImmunityInfo IMMUNITY_INFO;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<YomiImmunityAbility>> INSTANCE;

	public YomiImmunityAbility(AbilityCore<? extends YomiImmunityAbility> var1) {
		super((AbilityCore)(var1),IMMUNITY_INFO);
	}
	static  {
		Holder[] var0 = new Holder[]{ModEffects.FROSTBITE,ModEffects.DIZZY,ModEffects.DRUNK,ModEffects.UNCONSCIOUS};
		Holder[] var1 = new Holder[]{MobEffects.HARM,MobEffects.REGENERATION,MobEffects.HUNGER,MobEffects.POISON};
		IMMUNITY_INFO = new EffectImmunityAbility$ImmunityInfo().addImmunityEffects(var0).addImmunityEffects(var1).addResistanceEffect((Holder)(ModEffects.FROZEN),2).addResistanceEffect((Holder)(ModEffects.PARALYSIS),2);
		AbilityRegistryEntry var2 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(YomiImmunityAbility::new)))).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(YomiImmunityAbility::canUnlock)))).setHidden().build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("yomi_immunities","Yomi Immunities",var2));
	}
	private static boolean canUnlock(LivingEntity var0) {
		Function<DevilFruitData, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(l0.hasYomiPower());
		};
		return ((Boolean)(((java.util.Optional)(DevilFruitData.get(var0))).map((Function)(var1)).orElse(Boolean.valueOf(false)))).booleanValue();
	}
}