package xyz.pixelatedw.mineminenomi.handlers.entity;

import com.google.common.base.Predicates;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.FlyAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiAuraAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.config.GeneralConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;

public class StatsRestoreHandler {
	public static void restoreHakiPassively(Player var0) {
		HakiData var1 = ((HakiData)(((java.util.Optional)(HakiData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			float var2 = (float)(var1.getMaxOveruse());
			float var3 = (float)(var1.getHakiOveruse());
			if ((var2) <= (0F)){
				return;
			}else{
				if ((var3) >= (var2)){
					var0.addEffect(new MobEffectInstance((Holder)(ModEffects.HAKI_OVERUSE),100,2));
				}else{
					if (((double)(var3)) >= (((double)(var2)) * (0.95D))){
						var0.addEffect(new MobEffectInstance((Holder)(ModEffects.HAKI_OVERUSE),80,1));
					}else{
						if (((double)(var3)) >= (((double)(var2)) * (0.9D))){
							var0.addEffect(new MobEffectInstance((Holder)(ModEffects.HAKI_OVERUSE),40,0));
						}
					}
				}
				AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
				if ((var4) == (null)){
					return;
				}else{
					boolean var5 = false;
					Iterator var6 = var4.getEquippedAbilities(AbilityCategory.HAKI.isAbilityPartofCategory()).iterator();
					do{
						if (var6.hasNext()){
							IAbility var7 = ((IAbility)(var6.next()));
							if (((var7) != (null)) && (!(var7 instanceof KenbunshokuHakiAuraAbility))){
								var5 = ((Boolean)(var7.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).map((Function)(((Function<ContinuousComponent, Boolean>)(ContinuousComponent::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue();
								if (var5){
									break;
								}else{
									continue;
								}
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					if (var5){
						return;
					}else{
						var1.alterHakiOveruse((int)((-(Math.max(5F,(var1.getTotalHakiExp()) / (30F)))) * (6F)));
						return;
					}
				}
			}
		}
	}
	public static void restoreHakiFromSleep(Player var0) {
		if ((!(WyHelper.isInCombat((LivingEntity)(var0)))) && (var0.isSleeping())){
			HakiData var1 = ((HakiData)(((java.util.Optional)(HakiData.get((LivingEntity)(var0)))).orElse((Object)(null))));
			if ((var1) == (null)){
				return;
			}else{
				int var2 = (int)(((double)((var1.getMaxHakiExp()) * (140F))) / (100D));
				var1.setHakiOveruse((var1.getHakiOveruse()) - (var2));
			}
		}
	}
	public static void restoreStats(Player var0, Player var1) {
		RegistryAccess var2 = var0.registryAccess();
		DevilFruitData var3 = DevilFruitData.getOrCreate((LivingEntity)(var0));
		DevilFruitData var4 = DevilFruitData.getOrCreate((LivingEntity)(var1));
		AbilityData var5 = AbilityData.getOrCreate((LivingEntity)(var1));
		EntityStatsData var6 = EntityStatsData.getOrCreate((LivingEntity)(var0));
		CompoundTag var7 = var6.serializeNBT((HolderLookup.Provider)(var2));
		EntityStatsData var8 = EntityStatsData.getOrCreate((LivingEntity)(var1));
		var8.deserializeNBT((HolderLookup.Provider)(var2),var7);
		if (((Boolean)(GeneralConfig.RACE_KEEP.get())).booleanValue()){
			var8.setRace(((Race)(((java.util.Optional)(var6.getRace())).orElse((Object)(null)))));
			var8.setSubRace(((Race)(((java.util.Optional)(var6.getSubRace())).orElse((Object)(null)))));
		}
		if (((Boolean)(GeneralConfig.FACTION_KEEP.get())).booleanValue()){
			var8.setFaction(((Faction)(((java.util.Optional)(var6.getFaction())).orElse((Object)(null)))));
		}
		if (((Boolean)(GeneralConfig.FIGHTING_STYLE_KEEP.get())).booleanValue()){
			var8.setFightingStyle(((FightingStyle)(((java.util.Optional)(var6.getFightingStyle())).orElse((Object)(null)))));
		}
		if (((Boolean)(GeneralConfig.DEVIL_FRUIT_KEEP.get())).booleanValue()){
			var4.deserializeNBT((HolderLookup.Provider)(var2),var3.serializeNBT((HolderLookup.Provider)(var2)));
		}
		var8.alterDoriki((WyHelper.percentage((double)(ServerConfig.getDorikiKeepPercentage()),var6.getDoriki())) - (var6.getDoriki()),StatChangeSource.DEATH);
		var8.alterBounty(((long)(WyHelper.percentage((double)(ServerConfig.getBountyKeepPercentage()),(double)(var6.getBounty())))) - (var6.getBounty()),StatChangeSource.DEATH);
		var8.alterBelly(((long)(WyHelper.percentage((double)(ServerConfig.getBellyKeepPercentage()),(double)(var6.getBelly())))) - (var6.getBelly()),StatChangeSource.DEATH);
		var8.alterExtol(((long)(WyHelper.percentage((double)(ServerConfig.getBellyKeepPercentage()),(double)(var6.getExtol())))) - (var6.getExtol()),StatChangeSource.DEATH);
		RegistryAccess var9 = var2;
		Player var10 = var1;
		final RegistryAccess var9_f1 = var9;
		final Player var10_f2 = var10;
		Consumer<HakiData> var11 = (l0) -> {
			CompoundTag lv1_3 = l0.serializeNBT(var9_f1);
			HakiData lv1_4 = HakiData.getOrCreate((LivingEntity)(var10_f2));
			lv1_4.deserializeNBT(var9_f1,lv1_3);
			lv1_4.alterBusoshokuHakiExp(((float)(WyHelper.percentage((double)(ServerConfig.getHakiExpKeepPercentage()),(double)(l0.getBusoshokuHakiExp())))) - (l0.getBusoshokuHakiExp()),StatChangeSource.DEATH);
			lv1_4.alterKenbunshokuHakiExp(((float)(WyHelper.percentage((double)(ServerConfig.getHakiExpKeepPercentage()),(double)(l0.getKenbunshokuHakiExp())))) - (l0.getKenbunshokuHakiExp()),StatChangeSource.DEATH);
		};
		((java.util.Optional)(HakiData.get((LivingEntity)(var0)))).ifPresent((Consumer)(var11));
		var8.alterLoyalty(((double)((int)(WyHelper.percentage((double)(((Integer)(GeneralConfig.LOYALTY_KEEP_PERCENTAGE.get())).intValue()),var6.getLoyalty())))) - (var6.getLoyalty()),StatChangeSource.DEATH);
		restorePermaData(var0,var1);
		AbilityHelper.enableAbilities((LivingEntity)(var1),(Predicate)(Predicates.alwaysTrue()));
		ProgressionHandler.checkAllForNewUnlocks(var1,false);
		ModNetwork.sendTo(new SSyncDevilFruitPacket((LivingEntity)(var1)),var1);
		ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var1)),var1);
		Predicate<IAbility> var12 = (l0) -> {
			return (l0 instanceof FlyAbility) && (!(((FlyAbility)(l0)).isPaused()));
		};
		if (((((var5.getPassiveAbilities(var12).size()) > (0)) ? (1) : (0)) != (0)) && ((!(var1.isCreative())) && (!(var1.isSpectator())))){
			var1.getAbilities().mayfly = false;
			var1.getAbilities().flying = false;
			((ServerPlayer)(var1)).connection.send((Packet)(new ServerboundPlayerAbilitiesPacket(var1.getAbilities())));
		}
	}
	public static void restoreFullData(Player var0, Player var1) {
		RegistryAccess var2 = var0.registryAccess();
		RegistryAccess var3 = var2;
		Player var4 = var1;
		final RegistryAccess var3_f1 = var3;
		final Player var4_f2 = var4;
		Consumer<EntityStatsData> var5 = (l0) -> {
			CompoundTag lv3_3 = l0.serializeNBT(var3_f1);
			EntityStatsData lv3_4 = EntityStatsData.getOrCreate((LivingEntity)(var4_f2));
			lv3_4.deserializeNBT(var3_f1,lv3_3);
			lv3_4.setCola(Math.max(Math.min(l0.getCola(),lv3_4.getMaxCola()),0));
		};
		((java.util.Optional)(EntityStatsData.get((LivingEntity)(var0)))).ifPresent((Consumer)(var5));
		RegistryAccess var6 = var2;
		Player var7 = var1;
		final RegistryAccess var6_f3 = var6;
		final Player var7_f4 = var7;
		Consumer<DevilFruitData> var8 = (l0) -> {
			CompoundTag lv4_3 = l0.serializeNBT(var6_f3);
			DevilFruitData lv4_4 = DevilFruitData.getOrCreate((LivingEntity)(var7_f4));
			lv4_4.deserializeNBT(var6_f3,lv4_3);
			lv4_4.clearMorphs();
		};
		((java.util.Optional)(DevilFruitData.get((LivingEntity)(var0)))).ifPresent((Consumer)(var8));
		RegistryAccess var9 = var2;
		Player var10 = var1;
		final RegistryAccess var9_f5 = var9;
		final Player var10_f6 = var10;
		Consumer<AbilityData> var11 = (l0) -> {
			CompoundTag lv5_3 = l0.serializeNBT(var9_f5);
			AbilityData.getOrCreate((LivingEntity)(var10_f6)).deserializeNBT(var9_f5,lv5_3);
		};
		((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).ifPresent((Consumer)(var11));
		RegistryAccess var12 = var2;
		Player var13 = var1;
		final RegistryAccess var12_f7 = var12;
		final Player var13_f8 = var13;
		Consumer<HakiData> var14 = (l0) -> {
			CompoundTag lv6_3 = l0.serializeNBT(var12_f7);
			HakiData.getOrCreate((LivingEntity)(var13_f8)).deserializeNBT(var12_f7,lv6_3);
		};
		((java.util.Optional)(HakiData.get((LivingEntity)(var0)))).ifPresent((Consumer)(var14));
		restorePermaData(var0,var1);
	}
	private static void restorePermaData(Player var0, Player var1) {
		RegistryAccess var2 = var0.registryAccess();
		RegistryAccess var3 = var2;
		Player var4 = var1;
		final RegistryAccess var3_f1 = var3;
		final Player var4_f2 = var4;
		Consumer<QuestData> var5 = (l0) -> {
			CompoundTag lv7_3 = l0.serializeNBT(var3_f1);
			QuestData.getOrCreate(var4_f2).deserializeNBT(var3_f1,lv7_3);
		};
		((java.util.Optional)(QuestData.get(var0))).ifPresent((Consumer)(var5));
		RegistryAccess var6 = var2;
		Player var7 = var1;
		final RegistryAccess var6_f3 = var6;
		final Player var7_f4 = var7;
		Consumer<ChallengeData> var8 = (l0) -> {
			CompoundTag lv8_3 = l0.serializeNBT(var6_f3);
			ChallengeData.getOrCreate(var7_f4).deserializeNBT(var6_f3,lv8_3);
		};
		((java.util.Optional)(ChallengeData.get(var0))).ifPresent((Consumer)(var8));
		RegistryAccess var9 = var2;
		Player var10 = var1;
		final RegistryAccess var9_f5 = var9;
		final Player var10_f6 = var10;
		Consumer<AbilityData> var11 = (l0) -> {
			CompoundTag lv9_3 = l0.serializeNBT(var9_f5);
			AbilityData.getOrCreate((LivingEntity)(var10_f6)).deserializeNBT(var9_f5,lv9_3);
		};
		((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).ifPresent((Consumer)(var11));
		ProgressionHandler.checkAllForNewUnlocks(var1,false);
		AttributeHelper.updateHPAttribute((LivingEntity)(var1));
		var1.setHealth(var1.getMaxHealth());
		EntityStatsData var12 = EntityStatsData.getOrCreate((LivingEntity)(var1));
		var12.setShadow(true);
		var12.setStrawDoll(true);
		var12.setHeart(true);
	}
}