package xyz.pixelatedw.mineminenomi.abilities.ryupteranodon;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.PropelledFlightAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PteranodonFlightAbility extends PropelledFlightAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<PteranodonFlightAbility>> INSTANCE;

	public PteranodonFlightAbility(AbilityCore<PteranodonFlightAbility> var1) {
		super((AbilityCore)(var1));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RyuPteraHelper::requiresEitherPoint))));
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::onDuringPassive))));
	}
	private void onDuringPassive(LivingEntity var1) {
	}
	public float getMaxSpeed(LivingEntity var1) {
		float var2 = 0F;
		if (((MorphInfo)(ModMorphs.PTERA_FLY.get())).isActive(var1)){
			var2 = (var1.isSprinting()) ? (0.8865127F) : (0.59241694F);
		}else{
			if (((MorphInfo)(ModMorphs.PTERA_ASSAULT.get())).isActive(var1)){
				var2 = (var1.isSprinting()) ? (0.7309927F) : (0.49309665F);
			}
		}
		MobEffectInstance var3 = var1.getEffect((Holder)(ModEffects.FATIGUE_EFFECT));
		if ((var3) != (null)){
			var2 = (var2) / ((1F) + (Math.min((float)(var3.getAmplifier()),3F)));
		}
		return var2;
	}
	protected float getAcceleration(LivingEntity var1) {
		if (((MorphInfo)(ModMorphs.PTERA_FLY.get())).isActive(var1)){
			return (var1.isSprinting()) ? (0.006332233F) : (0.00423155F);
		}else{
			if (((MorphInfo)(ModMorphs.PTERA_ASSAULT.get())).isActive(var1)){
				return (var1.isSprinting()) ? (0.005221376F) : (0.003522119F);
			}else{
				return 0F;
			}
		}
	}
	protected int getHeightDifference(LivingEntity var1) {
		return 128;
	}
	public float getSpeed(LivingEntity var1) {
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var2) == (null)){
			return 0F;
		}else{
			TempuraudonAbility var3 = ((TempuraudonAbility)(var2.getEquippedAbility(((AbilityCore)(TempuraudonAbility.INSTANCE.get())))));
			TankyudonAbility var4 = ((TankyudonAbility)(var2.getEquippedAbility(((AbilityCore)(TankyudonAbility.INSTANCE.get())))));
			BeakGrabAbility var5 = ((BeakGrabAbility)(var2.getEquippedAbility(((AbilityCore)(BeakGrabAbility.INSTANCE.get())))));
			if (((var3) != (null)) && (var3.isCharging())){
				return 0F;
			}else{
				if ((((var4) != (null)) && (var4.isContinuous())) || (((var5) != (null)) && (var5.isContinuous()))){
					var1.zza = 0.98F;
					float var6 = (this.speed) + (1F);
					this.speed = var6;
					return var6;
				}else{
					return this.speed;
				}
			}
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			AbilityDescriptionLine$IDescriptionLine[] lv1_2 = new AbilityDescriptionLine$IDescriptionLine[2];
			lv1_2[0] = AbilityDescriptionLine.NEW_LINE;
			Supplier[] lv1_3 = new Supplier[]{ModMorphs.PTERA_ASSAULT,ModMorphs.PTERA_FLY};
			lv1_2[1] = AbilityTooltipsHelper.getRequiredMorphTooltip(lv1_3);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(PteranodonFlightAbility::new)))).addDescriptionLine(lv1_2).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("pteranodon_flight","Pteranodon Flight",var0));
	}
}