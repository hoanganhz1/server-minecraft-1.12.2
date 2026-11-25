package xyz.pixelatedw.mineminenomi.abilities.electro;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ElectroHelper {
	// Fields
	public static final UUID SULONG_DAMAGE_BONUS = UUID.fromString("b8ee5e5c-3dee-4ca6-93ae-8340172ae72b");
	public static final UUID SULONG_COOLDOWN_BONUS = UUID.fromString("7fbdd64f-bc93-4bf5-aa59-f877eb014c9e");
	public static final UUID SULONG_RANGE_BONUS = UUID.fromString("009c1be2-c51e-4a0d-8952-5556bbe4a75d");

	public static Result canTransformInSulong(LivingEntity var0, IAbility var1) {
		if (!(canTransform(var0.level()))){
			return Result.fail(ModI18nAbilities.MESSAGE_NEED_MOON);
		}else{
			return Result.success();
		}
	}
	public static Ability$ICanUseEvent requireEleclaw(int var0) {
		int var1 = var0;
		final int var1_f1 = var1;
		Ability$ICanUseEvent var2 = (l0, l1) -> {
			Function<AbilityData, EleclawAbility> lv2_3 = (l2_0) -> {
				return ((EleclawAbility)(l2_0.getEquippedAbility(((AbilityCore)(EleclawAbility.INSTANCE.get())))));
			};
			EleclawAbility lv2_4 = ((EleclawAbility)(((java.util.Optional)(AbilityData.get(l0))).map((Function)(lv2_3)).orElse(null)));
			if (((lv2_4) != (null)) && (lv2_4.isContinuous())){
				if (((((Integer)(lv2_4.getComponent(((AbilityComponentKey)(ModAbilityComponents.STACK.get()))).map((Function)(((Function<StackComponent, Integer>)(StackComponent::getStacks)))).orElse(Integer.valueOf(0)))).intValue()) - (var1_f1)) < (0)){
					Object[] lv2_5 = new Object[]{Integer.valueOf(var1_f1),((AbilityCore)(EleclawAbility.INSTANCE.get())).getLocalizedName().getString()};
					return Result.fail((Component)(Component.translatable(ModI18nAbilities.MESSAGE_NOT_ENOUGH_STACKS,lv2_5)));
				}else{
					return Result.success();
				}
			}else{
				return Result.fail(ModI18nAbilities.MESSAGE_NEED_ELECLAW);
			}
		};
		return var2;
	}
	public static boolean hasSulongActive(LivingEntity var0) {
		Function<AbilityData, SulongAbility> var1 = (l0) -> {
			return ((SulongAbility)(l0.getEquippedAbility(((AbilityCore)(SulongAbility.INSTANCE.get())))));
		};
		SulongAbility var2 = ((SulongAbility)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var1)).orElse(null)));
		if (((var2) != (null)) && (var2.isContinuous())){
			return true;
		}else{
			return false;
		}
	}
	public static boolean canTransform(Level var0) {
		if ((WyDebug.isDebug()) && (var0.isNight())){
			return true;
		}else{
			return ((var0.dimensionType().moonPhase(var0.getDayTime())) == (0)) && ((!(var0.isRaining())) && ((!(var0.dimensionType().hasCeiling())) && ((var0.dimensionType().hasSkyLight()) && (var0.isNight()))));
		}
	}
}