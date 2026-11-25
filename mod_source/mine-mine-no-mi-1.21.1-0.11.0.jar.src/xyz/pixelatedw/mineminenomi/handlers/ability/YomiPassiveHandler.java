package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.events.entity.YomiTriggerEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.triggers.YomiReviveTrigger;
import xyz.pixelatedw.mineminenomi.handlers.entity.StatsRestoreHandler;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class YomiPassiveHandler {
	public static void tick(LivingEntity var0) {
		DevilFruitData var1 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var0))).orElse((Object)(null))));
		if (((var1) != (null)) && (var1.hasYomiPower())){
			if (var0 instanceof Player){
				Player var2 = ((Player)(var0));
				if ((var2.getFoodData().getFoodLevel()) <= (18)){
					var2.getFoodData().setFoodLevel(18);
				}
			}
			var0.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,10,(var0.isSprinting()) ? (4) : (0),false,false));
			return;
		}else{
			return;
		}
	}
	public static void handleWaterRunning(LivingEntity var0) {
		DevilFruitData var1 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var0))).orElse((Object)(null))));
		if (((var1) != (null)) && (var1.hasYomiPower())){
			if ((var0.zza) <= (0F)){
				return;
			}else{
				BlockState var2 = var0.level().getBlockState(var0.blockPosition().below());
				if ((var2.getFluidState().isSource()) && ((((java.lang.Object)(var2.getFluidState().getFluidType())).equals(NeoForgeMod.WATER_TYPE.value())) && (var0.isSprinting()))){
					AbilityHelper.setDeltaMovement((Entity)(var0),var0.getLookAngle().normalize().multiply(0.5D,0D,0.5D));
					var0.fallDistance = 0F;
					if (!(var0.level().isClientSide)){
						int var3 = 0;
						do{
							if ((var3) < (32)){
								double var4 = (var0.getX()) + ((WyHelper.randomDouble()) * (0.5D));
								double var5 = (var0.getY()) + ((WyHelper.randomDouble()) * (0.5D));
								double var6 = (var0.getZ()) + ((WyHelper.randomDouble()) * (0.5D));
								((ServerLevel)(var0.level())).sendParticles((ParticleOptions)(new BlockParticleOption(ParticleTypes.BLOCK,var2)),var4,var5,var6,1,0D,0D,0D,0D);
								var3++;
								continue;
							}else{
								break;
							}
						} while (true);
					}else{
						return;
					}
				}
				return;
			}
		}else{
			return;
		}
	}
	public static boolean triggerFirstDeath(Player var0, Player var1) {
		DevilFruitData var2 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var2) == (null)){
			return false;
		}else{
			if ((((var2.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI)) && (!(var2.hasYomiPower()))) ? (1) : (0)) == (0)){
				return false;
			}else{
				NeoForge.EVENT_BUS.post((Event)(new YomiTriggerEvent(var0,var1)));
				if (var0 instanceof ServerPlayer){
					ServerPlayer var3 = ((ServerPlayer)(var0));
					((YomiReviveTrigger)(ModAdvancements.YOMI_REVIVE.get())).trigger(var3);
				}
				StatsRestoreHandler.restoreFullData(var0,var1);
				DevilFruitData var3_1 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var1)))).orElse((Object)(null))));
				var3_1.setYomiPower(true);
				var3_1.setDevilFruit(((AkumaNoMiItem)(ModFruits.YOMI_YOMI_NO_MI.get())));
				var3_1.addMorph(((MorphInfo)(ModMorphs.YOMI_SKELETON.get())));
				return true;
			}
		}
	}
	public static void milkHeal(LivingEntity var0, ItemStack var1) {
		DevilFruitData var2 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			if (((var1.getItem()) == (Items.MILK_BUCKET)) && (var2.hasYomiPower())){
				var0.heal(8F);
			}
			return;
		}
	}
}