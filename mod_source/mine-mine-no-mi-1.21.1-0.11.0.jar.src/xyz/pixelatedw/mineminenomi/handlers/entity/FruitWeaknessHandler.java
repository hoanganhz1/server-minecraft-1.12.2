package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class FruitWeaknessHandler {
	// Fields
	private static final Predicate<IAbility> WATER_DISABLED_ABILITIES_PREDICATE;
	static  {
		Predicate<IAbility> var0 = (l0) -> {
			if (((l0.getCore().getType()) == (AbilityType.PASSIVE)) && ((l0.getCore().getCategory()) != (AbilityCategory.DEVIL_FRUITS))){
				return false;
			}else{
				return true;
			}
		};
		WATER_DISABLED_ABILITIES_PREDICATE = var0;
	}


	public static void tryApplyWaterWeakness(LivingEntity var0) {
		DevilFruitData var1 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var0))).orElse((Object)(null))));
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if (((var1) != (null)) && ((var2) != (null))){
			boolean var3 = var1.hasAnyDevilFruit();
			Predicate var4 = (Predicate) ((Predicate<AbilityCore>) ((l0) -> {
			return (l0.getCategory()) == (AbilityCategory.DEVIL_FRUITS);
		}));
			int var5 = ((var2.getUnlockedAbilities(var4).size()) > (0)) ? (1) : (0);
			if ((!(var3)) && ((var5) == (0))){
				return;
			}else{
				boolean var6 = var1.hasStrongWaterWeakness();
				boolean var7 = var1.hasKairosekiWeakness();
				boolean var8 = var1.hasWeakWaterWeakness();
				if ((((var0.isEyeInFluidType(((FluidType)(NeoForgeMod.WATER_TYPE.value())))) && (!(var0.hasEffect(ModEffects.BUBBLY_CORAL)))) ? (1) : (0)) != (0)){
					var6 = true;
					var8 = true;
				}
				if (((var0.level().getGameTime()) % (30L)) == (0L)){
					var6 = AbilityHelper.isAffectedByWater(var0);
					var8 = AbilityHelper.isAffectedByWater(var0,0.25F);
					var7 = AbilityHelper.isKairosekiNearby(var0);
					var1.setStrongWaterWeakness(var6);
					var1.setKairosekiWeakness(var7);
					var1.setWeakWaterWeakness(var8);
				}
				int var9 = 0;
				if ((var1.hasAnyDevilFruit()) && (var6)){
					if (var0 instanceof Player){
						Player var10 = ((Player)(var0));
						Player var11 = var10;
						if (var11.isCreative()){
							var9 = 1;
						}
					}else{
						var9 = 0;
					}
					if ((var9) == (0)){
						if (var0.isVisuallySwimming()){
							AbilityHelper.setDeltaMovement((Entity)(var0),var0.getDeltaMovement().x,(var0.getDeltaMovement().y) - (0.15D),var0.getDeltaMovement().z);
						}else{
							AbilityHelper.setDeltaMovement((Entity)(var0),var0.getDeltaMovement().x,(var0.getDeltaMovement().y) - (0.1D),var0.getDeltaMovement().z);
						}
					}
				}
				if (!(var0.level().isClientSide)){
					DeferredHolder var9_1 = null;
					if (var7){
						var9_1 = ModEffects.KAIROSEKI_WEAKNESS;
					}else{
						if (var6){
							var9_1 = ModEffects.WATER_WEAKNESS;
						}
					}
					if ((!(var6)) && (!(var7))){
						if (var8){
							var0.addEffect(new MobEffectInstance((Holder)(ModEffects.SWIM_SLOWDOWN),20,2,false,false,true));
							var0.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,20,1,false,false,true));
						}else{
							return;
						}
					}else{
						if ((var9_1) != (null)){
							var0.addEffect(new MobEffectInstance((Holder)(var9_1),40,0));
						}
						if (var0 instanceof Player){
							Player var10_1 = ((Player)(var0));
							if (ServerConfig.areExtraWaterChecksEnabled()){
								var10_1.causeFoodExhaustion(0.015F);
							}
						}
						AbilityHelper.disableAbilities(var0,20,WATER_DISABLED_ABILITIES_PREDICATE);
					}
				}
				return;
			}
		}else{
			return;
		}
	}}