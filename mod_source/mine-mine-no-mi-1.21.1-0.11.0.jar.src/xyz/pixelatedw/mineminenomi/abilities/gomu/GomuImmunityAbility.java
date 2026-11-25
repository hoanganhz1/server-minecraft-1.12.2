package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.SourceImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.SourceImmunityAbility$SourceImmunityInfo;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GomuImmunityAbility extends SourceImmunityAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GomuImmunityAbility>> INSTANCE;

	public GomuImmunityAbility(AbilityCore<? extends SourceImmunityAbility> var1) {
		super(var1,SourceImmunityAbility.LIGHTNING_IMMUNITY);
	}
	public boolean isDamageTaken(LivingEntity var1, DamageSource var2, float var3) {
		return true;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GomuImmunityAbility::new)))).setHidden().build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gomu_immunity","Gomu Immunity",var0));
	}
}