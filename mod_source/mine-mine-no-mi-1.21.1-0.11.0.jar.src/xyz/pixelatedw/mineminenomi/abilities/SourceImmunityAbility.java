package xyz.pixelatedw.mineminenomi.abilities;

import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;

public abstract class SourceImmunityAbility extends PassiveAbility {
	// Fields
	public static final SourceImmunityAbility$SourceImmunityInfo EMPTY = new SourceImmunityAbility$SourceImmunityInfo();
	public static final SourceImmunityAbility$SourceImmunityInfo FIRE_IMMUNITY;
	public static final SourceImmunityAbility$SourceImmunityInfo MAGMA_IMMUNITY;
	public static final SourceImmunityAbility$SourceImmunityInfo LIGHTNING_IMMUNITY;
	protected final SourceImmunityAbility$SourceImmunityInfo immunityInfo;
	protected final DamageTakenComponent damageTakenComponent;

	public SourceImmunityAbility(AbilityCore<? extends SourceImmunityAbility> var1, @Nullable() SourceImmunityAbility$SourceImmunityInfo var2) {
		super((AbilityCore)(var1));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::damageCheck))));
		this.immunityInfo = ((var2) == (null)) ? (EMPTY) : (var2);
		AbilityComponent[] var3 = new AbilityComponent[]{this.damageTakenComponent};
		this.addComponents(var3);
	}
	public abstract boolean isDamageTaken(LivingEntity var0, DamageSource var1, float var2);
	public float damageCheck(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if (!(this.isDamageTaken(var1,var3,var4))){
			return 0F;
		}else{
			if (this.immunityInfo.isImmune(var3)){
				return 0F;
			}else{
				return var4;
			}
		}
	}
	static  {
		Predicate<DamageSource> var0 = (l0) -> {
			return (l0.is(DamageTypeTags.IS_FIRE)) && ((!(l0.typeHolder().is(DamageTypes.LAVA))) && (!(l0.typeHolder().is(ModDamageTypes.SUN_INCINERATION))));
		};
		Predicate<DamageSource> var1 = (l0) -> {
			return IDamageSourceHandler.getHandler(l0).hasElement(SourceElement.FIRE);
		};
		FIRE_IMMUNITY = new SourceImmunityAbility$SourceImmunityInfo().addSourceRule(var0).addSourceRule(var1);
		Predicate<DamageSource> var2 = (l0) -> {
			return (l0.is(DamageTypeTags.IS_FIRE)) && (!(l0.typeHolder().is(ModDamageTypes.SUN_INCINERATION)));
		};
		MAGMA_IMMUNITY = new SourceImmunityAbility$SourceImmunityInfo().addSourceRule(var2);
		ResourceKey[] var3 = new ResourceKey[]{DamageTypes.LIGHTNING_BOLT};
		Predicate<DamageSource> var4 = (l0) -> {
			return IDamageSourceHandler.getHandler(l0).hasElement(SourceElement.LIGHTNING);
		};
		LIGHTNING_IMMUNITY = new SourceImmunityAbility$SourceImmunityInfo().addSourceImmunities(var3).addSourceRule(var4);
	}
}