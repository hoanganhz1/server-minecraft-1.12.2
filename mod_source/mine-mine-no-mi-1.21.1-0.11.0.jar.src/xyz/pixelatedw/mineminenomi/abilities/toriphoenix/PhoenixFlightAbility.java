package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
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
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PhoenixFlightAbility extends PropelledFlightAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<PhoenixFlightAbility>> INSTANCE;

	public PhoenixFlightAbility(AbilityCore<PhoenixFlightAbility> var1) {
		super((AbilityCore)(var1));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::canFly))));
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::onDuringPassive))));
	}
	private void onDuringPassive(LivingEntity var1) {
	}
	public float getMaxSpeed(LivingEntity var1) {
		float var2 = 0F;
		if (((MorphInfo)(ModMorphs.PHOENIX_FLY.get())).isActive(var1)){
			var2 = (var1.isSprinting()) ? (0.9541735F) : (0.63775486F);
		}else{
			if (((MorphInfo)(ModMorphs.PHOENIX_ASSAULT.get())).isActive(var1)){
				var2 = (var1.isSprinting()) ? (0.8012779F) : (0.5353319F);
			}
		}
		MobEffectInstance var3 = var1.getEffect((Holder)(ModEffects.FATIGUE_EFFECT));
		if ((var3) != (null)){
			var2 = (var2) / ((1F) + (Math.min((float)(var3.getAmplifier()),3F)));
		}
		return var2;
	}
	protected float getAcceleration(LivingEntity var1) {
		if (((MorphInfo)(ModMorphs.PHOENIX_FLY.get())).isActive(var1)){
			return (var1.isSprinting()) ? (0.006815525F) : (0.004555392F);
		}else{
			if (((MorphInfo)(ModMorphs.PHOENIX_ASSAULT.get())).isActive(var1)){
				return (var1.isSprinting()) ? (0.005723413F) : (0.003823799F);
			}else{
				return 0F;
			}
		}
	}
	protected int getHeightDifference(LivingEntity var1) {
		return 128;
	}
	private Result canFly(LivingEntity var1, IAbility var2) {
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return Result.fail(null);
		}else{
			PhoenixAssaultPointAbility var4 = ((PhoenixAssaultPointAbility)(var3.getEquippedAbility(((AbilityCore)(PhoenixAssaultPointAbility.INSTANCE.get())))));
			PhoenixFlyPointAbility var5 = ((PhoenixFlyPointAbility)(var3.getEquippedAbility(((AbilityCore)(PhoenixFlyPointAbility.INSTANCE.get())))));
			TenseiNoSoenAbility var6 = ((TenseiNoSoenAbility)(var3.getEquippedAbility(((AbilityCore)(TenseiNoSoenAbility.INSTANCE.get())))));
			int var7 = (((var6) != (null)) && (var6.isContinuous())) ? (1) : (0);
			if ((((var4) != (null)) && (var4.isContinuous())) || (((var5) != (null)) && ((var5.isContinuous()) && ((var7) == (0))))){
				return Result.success();
			}else{
				return Result.fail(null);
			}
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			AbilityDescriptionLine$IDescriptionLine[] lv2_2 = new AbilityDescriptionLine$IDescriptionLine[2];
			lv2_2[0] = AbilityDescriptionLine.NEW_LINE;
			Supplier[] lv2_3 = new Supplier[]{ModMorphs.PHOENIX_FLY};
			lv2_2[1] = AbilityTooltipsHelper.getRequiredMorphTooltip(lv2_3);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(PhoenixFlightAbility::new)))).addDescriptionLine(lv2_2).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("phoenix_flight","Phoenix Flight",var0));
	}
	public float getSpeed(LivingEntity var1) {
		Function<AbilityData, FujiazamiAbility> var2 = (l0) -> {
			return ((FujiazamiAbility)(l0.getEquippedAbility(((AbilityCore)(FujiazamiAbility.INSTANCE.get())))));
		};
		FujiazamiAbility var3 = ((FujiazamiAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var2)).orElse(null)));
		if (((var3) != (null)) && (var3.isContinuous())){
			return 0F;
		}else{
			MobEffectInstance var4 = var1.getEffect((Holder)(ModEffects.FATIGUE_EFFECT));
			if ((var4) != (null)){
				return ((this.speed) * (1F)) - (Math.min((float)(var4.getAmplifier()),3F));
			}else{
				return this.speed;
			}
		}
	}
}