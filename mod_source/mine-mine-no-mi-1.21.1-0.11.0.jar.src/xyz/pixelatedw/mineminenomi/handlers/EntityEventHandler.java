package xyz.pixelatedw.mineminenomi.handlers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.AnimalTameEvent;
import net.neoforged.neoforge.event.entity.living.EffectParticleModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.CommandAbility;
import xyz.pixelatedw.mineminenomi.abilities.doa.AirDoorAbility;
import xyz.pixelatedw.mineminenomi.abilities.doa.DoaHelper;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.abilities.nagi.SilentAbility;
import xyz.pixelatedw.mineminenomi.api.IDestroyableItem;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$DamageState;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.caps.command.CommandReceiverCapability;
import xyz.pixelatedw.mineminenomi.api.caps.command.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler$NuDamageValue;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.enums.SoulboundMark;
import xyz.pixelatedw.mineminenomi.api.events.ProjectileShootEvent;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityCanUseEvent;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityUseEvent$Post;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityUseEvent$Pre;
import xyz.pixelatedw.mineminenomi.api.events.entity.EntityCarryEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.InteractionTriggeredEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.LivingHealByEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.MobEffectAfterAddedEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.PlayerUseItemResultEvent;
import xyz.pixelatedw.mineminenomi.api.events.stats.DorikiEvent$Post;
import xyz.pixelatedw.mineminenomi.api.events.stats.HakiExpEvent$Post;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.DamageSourceHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.EffectsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.TPSDelta;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig$HaoshokuColoringLogic;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.AnimationData;
import xyz.pixelatedw.mineminenomi.data.entity.CameraLockData;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.CombatData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.effects.BaseEffect;
import xyz.pixelatedw.mineminenomi.entities.AkumaNoMiEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.handlers.ability.ComponentsHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.HisoPassivesHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.KagePassivesHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.KnockdownHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.KukuPassivesHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.MaguPassivesHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.MorphsHandler$Common;
import xyz.pixelatedw.mineminenomi.handlers.ability.OutOfBodyHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.SwordsmanPassivesHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.ValidationHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.YomiPassiveHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.AnimationsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.AttributesHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.BountyHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.CarryHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.CommandReceiverHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.EffectsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.EnchantmentsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.FactionHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.FruitWeaknessHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.GoalsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.InputHandler$Common;
import xyz.pixelatedw.mineminenomi.handlers.entity.ItemsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.KairosekiCoatingHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.PatreonHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.ProjectilesHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.QuestsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.RevengeHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.StatsGainHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.StatsRestoreHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.SyncHandler;
import xyz.pixelatedw.mineminenomi.handlers.protection.AbilityProtectionHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.BlocksHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.ChallengesHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.OneFruitPerWorldHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler$Common;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SViewProtectionPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SSetHakiColorPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SForceDismountEntityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SSetServerMaxBarsPacket;

@EventBusSubscriber(modid="mineminenomi")
public class EntityEventHandler {
	@SubscribeEvent()
	public static void onEntityUpdate(EntityTickEvent.Pre var0) {
		LivingEntity var3 = null;
		Entity var1 = var0.getEntity();
		if (var1 instanceof LivingEntity){
			LivingEntity var2 = ((LivingEntity)(var1));
			if (!(var2.isAlive())){
				return;
			}else{
				if (!(var2.level().isClientSide)){
					var3 = var2;
					final LivingEntity var3_f1 = var3;
					Consumer<EntityStatsData> var4 = (l0) -> {
			var3_f1.xxa = l0.getLeftImpulse();
			var3_f1.zza = l0.getForwardImpulse();
			if ((l0.getAllyInflictedInvisibilityCooldown()) > (0F)){
				l0.alterAllyInflictedInvisibilityCooldown(-(TPSDelta.INSTANCE.getDeltaTime()));
			}
		};
					((java.util.Optional)(EntityStatsData.get(var2))).ifPresent((Consumer)(var4));
					int var5 = (((var2.tickCount) % (20)) == (0)) ? (1) : (0);
					int var6 = (((var2.tickCount) % (1200)) == (0)) ? (1) : (0);
					if (var2 instanceof Player){
						Player var7 = ((Player)(var2));
						QuestsHandler.handleTickableObjective(var7);
						QuestsHandler.handleSurvivalObjective(var7);
						StatsGainHandler.increaseLoyaltyOverTime(var7);
						StatsGainHandler.givePassiveKenbunshokuExp(var7);
						StatsRestoreHandler.restoreHakiFromSleep(var7);
						MaguPassivesHandler.lavaMovementBoost(var7);
						ItemsHandler.handleEnmaCurse(var7);
						CarryHandler.dragLeashedTarget(var7);
						CarryHandler.moveCarriedTarget(var7);
						OutOfBodyHandler.blockPortalUsage(var7);
						if ((var5) != (0)){
							StatsRestoreHandler.restoreHakiPassively(var7);
						}
					}
					if (var2 instanceof Mob){
						CommandReceiverHandler.canMaintainCommand(((Mob)(var2)));
					}
					if (var2 instanceof Animal){
						Animal var7_1 = ((Animal)(var2));
						if ((var5) != (0)){
							HisoPassivesHandler.lookoutMarking(var7_1);
						}
						if ((var6) != (0)){
							HisoPassivesHandler.lookoutCleanup(var7_1);
						}
					}
					KagePassivesHandler.tryBurningInSun(var2);
					YomiPassiveHandler.tick(var2);
					EffectsHandler.removeUnneededEffects(var2);
					EffectsHandler.launchedCollision(var2);
					EffectsHandler.bubblyCoralTick(var2);
					EffectsHandler.checkOilCovered(var2);
					EntityStatsData var7_2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var2))).orElse((Object)(null))));
					if (((var7_2) != (null)) && ((var7_2.getInvulnerableTime()) > (0))){
						var7_2.alterInvulnerableTime(-1);
					}
					CombatData.get(var2).ifPresent((Consumer)(((Consumer<CombatData>)(CombatData::tick))));
					ValidationHandler.validateAllItemsForComponentChecks(var2);
					ItemsHandler.insideTrapBlock(var2);
					ItemsHandler.handleImbuingTagging(var2);
					RevengeHandler.checkTicks(var2);
					if ((var5) != (0)){
						Iterator var8 = var2.getActiveEffects().iterator();
						do{
							if (var8.hasNext()){
								EffectsHandler.trySpreadProximityEffect(var2,((MobEffectInstance)(var8.next())));
								continue;
							}else{
								break;
							}
						} while (true);
					}
				}else{
					AnimationData.get(var2).ifPresent((Consumer)(((Consumer<AnimationData>)(AnimationData::tick))));
				}
				AbilityData.get(var2).ifPresent((Consumer)(((Consumer<AbilityData>)(AbilityData::tickGCD))));
				if (var2 instanceof Player){
					ChallengeData.get(((Player)(var2))).ifPresent((Consumer)(((Consumer<ChallengeData>)(ChallengeData::tickInvitations))));
				}
				YomiPassiveHandler.handleWaterRunning(var2);
				FruitWeaknessHandler.tryApplyWaterWeakness(var2);
				AttributesHandler.applyJumpForce(var2);
				AbilityData.get(var2).ifPresent((Consumer)(((Consumer<AbilityData>)(AbilityData::tickAbilities))));
				return;
			}
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onPlayerTick(PlayerTickEvent.Post var0) {
		Player var1 = var0.getEntity();
		if (!(var1.level().isClientSide)){
			CameraLockData.serverTick(((ServerPlayer)(var1)));
		}else{
			CameraLockData.clientTick(var1);
		}
	}
	@SubscribeEvent()
	public static void onPlayerLoginEvent(PlayerEvent.PlayerLoggedInEvent var0) {
		Player var1 = var0.getEntity();
		if (!(var1.level().isClientSide)){
			Player var2 = var1;
			final Player var2_f1 = var2;
			Consumer<AbilityData> var3 = (l0) -> {
			Player lv3_2 = var2_f1;
			final Player lv3_2_f1 = lv3_2;
			Consumer<IAbility> lv3_3 = (l2_0) -> {
				AbilityHelper.emergencyStopAbility((LivingEntity)(lv3_2_f1),l2_0);
			};
			((java.lang.Iterable)(l0.getEquippedAndPassiveAbilities())).forEach((Consumer)(lv3_3));
		};
			((java.util.Optional)(AbilityData.get((LivingEntity)(var1)))).ifPresent((Consumer)(var3));
			if (NuWorld.isChallengeDimension(var1.level())){
				ChallengesHandler.kickPlayerFromChallengeOnLogin(((ServerPlayer)(var1)),var1.level());
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent var0) {
		Player var1 = var0.getEntity();
		if (!(var1.level().isClientSide)){
			Player var2 = var1;
			final Player var2_f1 = var2;
			Consumer<AbilityData> var3 = (l0) -> {
			Player lv5_2 = var2_f1;
			final Player lv5_2_f1 = lv5_2;
			Consumer<IAbility> lv5_3 = (l2_0) -> {
				AbilityHelper.emergencyStopAbility((LivingEntity)(lv5_2_f1),l2_0);
			};
			((java.lang.Iterable)(l0.getEquippedAndPassiveAbilities())).forEach((Consumer)(lv5_3));
		};
			((java.util.Optional)(AbilityData.get((LivingEntity)(var1)))).ifPresent((Consumer)(var3));
			if (ServerConfig.hasOneFruitPerWorldSimpleLogic()){
				OneFruitPerWorldHandler.playerLogout(var1);
				if (ServerConfig.hasOneFruitPerWorldExtendedLogic()){
					OneFruitPerWorldHandler.dropFruitsFromNearbyContainers(var1);
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onJoinLevelEvent(EntityJoinLevelEvent var0) {
		ExtendedWorldData var6 = null;
		ExtendedWorldData var6_1 = null;
		Mob var6_2 = null;
		UUID var7 = null;
		UUID var7_1 = null;
		Level var1 = var0.getLevel();
		if (var1.isClientSide){
			return;
		}else{
			UUID var2 = var0.getEntity().getUUID();
			ExtendedWorldData var3 = ExtendedWorldData.get();
			if (var3.isOnDeathList(var2)){
				if (var0.getEntity().hurt(ModDamageSources.getInstance((LevelAccessor)(var1)).souldbound((LivingEntity)(null)),3.4028235e+38F)){
					var3.removeMark(var2);
				}
			}else{
				Entity var4 = var0.getEntity();
				if (var4 instanceof LivingEntity){
					LivingEntity var5 = ((LivingEntity)(var4));
					if (var3.isOnRestoreList(var2,SoulboundMark.RESTORE_HEART)){
						var6 = var3;
						var7 = var2;
						final ExtendedWorldData var6_f1 = var6;
						final UUID var7_f2 = var7;
						Consumer<EntityStatsData> var8 = (l0) -> {
			l0.setHeart(true);
			var6_f1.removeMark(var7_f2);
		};
						((java.util.Optional)(EntityStatsData.get(var5))).ifPresent((Consumer)(var8));
					}else{
						if (var3.isOnRestoreList(var2,SoulboundMark.RESTORE_DOLL)){
							var6_1 = var3;
							var7_1 = var2;
							final ExtendedWorldData var6_1_f3 = var6_1;
							final UUID var7_1_f4 = var7_1;
							Consumer<EntityStatsData> var8_1 = (l0) -> {
			l0.setStrawDoll(true);
			var6_1_f3.removeMark(var7_1_f4);
		};
							((java.util.Optional)(EntityStatsData.get(var5))).ifPresent((Consumer)(var8_1));
						}
					}
				}
			}
			Entity var4_1 = var0.getEntity();
			if (var4_1 instanceof Mob){
				Mob var5_1 = ((Mob)(var4_1));
				if (!(AbilityProtectionHandler.shouldMobSpawnInProtectionArea(var5_1))){
					var0.setCanceled(true);
					return;
				}else{
					var6_2 = var5_1;
					final Mob var6_2_f5 = var6_2;
					Consumer<ICommandReceiver> var7_2 = (l0) -> {
			CommandAbility.addCommandGoals(var6_2_f5,l0);
		};
					((java.util.Optional)(CommandReceiverCapability.get((LivingEntity)(var5_1)))).ifPresent((Consumer)(var7_2));
				}
			}
			Entity var5_2 = var0.getEntity();
			if (var5_2 instanceof LivingEntity){
				LivingEntity var6_3 = ((LivingEntity)(var5_2));
				EntityStatsData.get(var6_3).ifPresent((Consumer)(((Consumer<EntityStatsData>)(EntityStatsData::port))));
				AttributeHelper.updateHPAttribute(var6_3);
				AttributeHelper.updateToughnessAttribute(var6_3);
				EffectsHandler.removeAbilityEffectsOnJoin(var6_3);
			}
			Entity var6_4 = var0.getEntity();
			if (var6_4 instanceof Mob){
				Mob var7_3 = ((Mob)(var6_4));
				GoalsHandler.parseNBTGoals(var7_3);
				StatsGainHandler.setSpawnStats(var7_3);
			}
			Entity var7_4 = var0.getEntity();
			if (var7_4 instanceof Creeper){
				GoalsHandler.addNewCreeperGoals(((Creeper)(var7_4)));
			}
			Entity var8_2 = var0.getEntity();
			if (var8_2 instanceof IronGolem){
				GoalsHandler.addNewIronGolemGoals(((IronGolem)(var8_2)));
			}
			Entity var9 = var0.getEntity();
			if (var9 instanceof AkumaNoMiEntity){
				AkumaNoMiEntity var10 = ((AkumaNoMiEntity)(var9));
				if (((!(ServerConfig.hasChunkUnloadingRealisticLogic())) && (!(ServerConfig.hasChunkUnloadingInstantLogic()))) || (!(var0.loadedFromDisk()))){

				}else{
					var0.setCanceled(OneFruitPerWorldHandler.cleanup(var10));
				}
				if ((ServerConfig.hasOneFruitPerWorldSimpleLogic()) && (!(var0.isCanceled()))){
					var0.setCanceled(OneFruitPerWorldHandler.cancelDrop(var10));
				}
			}
			Entity var10_1 = var0.getEntity();
			if (var10_1 instanceof ServerPlayer){
				ServerPlayer var11 = ((ServerPlayer)(var10_1));
				SyncHandler.joinWorldSync((Player)(var11));
				ValidationHandler.forceSelectionScreen((Player)(var11));
				ProgressionHandler.forceAbilityUnlockAdvancementChecks(var11);
				ValidationHandler.validateFaction((Player)(var11));
				if (ServerConfig.hasOneFruitPerWorldSimpleLogic()){
					OneFruitPerWorldHandler.playerLogin(var11);
					if (ServerConfig.hasOneFruitPerWorldExtendedLogic()){
						OneFruitPerWorldHandler.dropFruitsFromNearbyContainers((Player)(var11));
					}
				}
				ModNetwork.sendTo(SViewProtectionPacket.update(var11.serverLevel()),(Player)(var11));
			}
			return;
		}
	}
	@SubscribeEvent()
	public static void onDatapackSync(OnDatapackSyncEvent var0) {
		if ((var0.getPlayer()) != (null)){
			ServerPlayer var1 = var0.getPlayer();
			if (!(PatreonHandler.handlePatreonAccess(var1))){
				return;
			}else{
				ModNetwork.sendTo(new SSetServerMaxBarsPacket(ServerConfig.getAbilityBars()),(Player)(var1));
				ModNetwork.sendTo(new SSetHakiColorPacket(var1.getId(),ServerConfig.getHaoshokuColoringLogic()),(Player)(var1));
				RandomizedFruitsHandler$Common.setRandomizationSeed((Player)(var1));
			}
		}
	}
	@SubscribeEvent()
	public static void onLivingDies(LivingDeathEvent var0) {
		LivingEntity var1 = var0.getEntity();
		Level var2 = var1.level();
		DamageSource var3 = var0.getSource();
		Entity var4 = var3.getEntity();
		if (!(var2.isClientSide)){
			ServerLevel var5 = ((ServerLevel)(var2));
			FactionsWorldData var6 = FactionsWorldData.get();
			if (!(AbilityProtectionHandler.shouldDieInProtectedArea(var1))){
				var0.setCanceled(true);
				return;
			}else{
				if (var1 instanceof ServerPlayer){
					ServerPlayer var7 = ((ServerPlayer)(var1));
					if (NuWorld.isChallengeDimension(var7.level())){
						var7.addEffect(new MobEffectInstance((Holder)(ModEffects.CHALLENGE_FAILED),40,0,false,false));
						var7.setHealth(1F);
						var0.setCanceled(true);
						return;
					}else{
						if (ServerConfig.hasOneFruitPerWorldSimpleLogic()){
							OneFruitPerWorldHandler.playerDeath(var7);
						}
					}
				}else{
					if (var1 instanceof EnderDragon){
						ExtendedWorldData.get().triggerNewWorld();
					}
				}
				if (var4 instanceof Player){
					BountyHandler.redeemBounty(var5,((Player)(var4)),var1);
				}
				StatsGainHandler.giveStats(var1,var3);
				StatsGainHandler.giveBusoshokuExp(var1,var3);
				EffectsHandler.handcuffedDeath(var1);
				QuestsHandler.handleKillObjective(var1,var3);
				var6.removeBounty(var1.getUUID());
			}
		}
	}
	@SubscribeEvent()
	public static void onClonePlayer(PlayerEvent.Clone var0) {
		Player var1 = var0.getOriginal();
		Player var2 = var0.getEntity();
		if (var0.isWasDeath()){
			int var3 = (!(AbilityProtectionHandler.shouldLoseStatsInProtectionArea((LivingEntity)(var1)))) ? (1) : (0);
			if ((!(NuWorld.isChallengeDimension(var1.level()))) && ((var3) == (0))){
				if (YomiPassiveHandler.triggerFirstDeath(var1,var2)){
					return;
				}else{
					StatsRestoreHandler.restoreStats(var1,var2);
				}
			}else{
				var2.getInventory().replaceWith(var1.getInventory());
				var2.setHealth(var1.getHealth());
				var2.experienceLevel = var1.experienceLevel;
				var2.totalExperience = var1.totalExperience;
				var2.experienceProgress = var1.experienceProgress;
				var2.setScore(var1.getScore());
				StatsRestoreHandler.restoreFullData(var1,var2);
				return;
			}
		}else{
			StatsRestoreHandler.restoreFullData(var1,var2);
		}
	}
	@SubscribeEvent()
	public static void onPlayerStartsTracking(PlayerEvent.StartTracking var0) {
		if (var0.getEntity().level().isClientSide){
			return;
		}else{
			Player var1 = var0.getEntity();
			Entity var2 = var0.getTarget();
			if (var2 instanceof Mob){
				SyncHandler.trackingSync((LivingEntity)(((Mob)(var2))),var1);
			}else{
				Entity var3 = var0.getTarget();
				if (var3 instanceof Player){
					SyncHandler.trackingSync((LivingEntity)(((Player)(var3))),var1);
				}
			}
			Entity var3_1 = var0.getTarget();
			if (var3_1 instanceof Projectile){
				Projectile var4 = ((Projectile)(var3_1));
				ProjectilesHandler.setProjectileHakiData(var4,var4.getOwner());
			}
			return;
		}
	}
	@SubscribeEvent()
	public static void onEntityFall(LivingFallEvent var0) {
		var0.setDistance(AttributesHandler.applyFallResistance(var0.getEntity(),var0.getDistance()));
	}
	@SubscribeEvent()
	public static void onEntityJump(LivingEvent.LivingJumpEvent var0) {
		AttributesHandler.applyJumpHeight(var0.getEntity());
	}
	@SubscribeEvent()
	public static void onPlayerInteracts(PlayerInteractEvent.RightClickEmpty var0) {
		Player var1 = var0.getEntity();
		if (OutOfBodyHandler.isOutOfBody((LivingEntity)(var1))){
			return;
		}else{
			if (((var0.getHand()) != (InteractionHand.OFF_HAND)) && (!(var1.level().isClientSide))){
				CarryHandler.tryDropCarried(var1);
				return;
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onPlayerInteractSpecific(PlayerInteractEvent.EntityInteractSpecific var0) {
		Player var1 = var0.getEntity();
		Entity var2 = var0.getTarget();
		if (KairosekiCoatingHandler.applyKairosekiCoat(var1,var2,var0.getItemStack())){
			var0.setCanceled(true);
			return;
		}else{
			if (var2 instanceof LivingEntity){
				LivingEntity var3 = ((LivingEntity)(var2));
				if (FactionHandler.tryInvitingToCrew(var1,var3)){
					var0.setCanceled(true);
					return;
				}else{
					EffectsHandler.unlockHandcuffs((LivingEntity)(var1),var3);
					QuestsHandler.handleEntityInteractionObjective(var1,var3);
				}
			}
			if ((!(var0.getLevel().isClientSide)) && ((var0.getTarget() instanceof ArmorStand) && ((var0.getItemStack().getItem() instanceof AkumaNoMiItem) && (ServerConfig.hasOneFruitPerWorldExtendedLogic())))){
				var0.setCanceled(true);
			}
			return;
		}
	}
	@SubscribeEvent()
	public static void onPlayerInteract(PlayerInteractEvent.EntityInteract var0) {
		Player var1 = var0.getEntity();
		Entity var2 = var0.getTarget();
		if (var2 instanceof ItemFrame){
			ItemFrame var3 = ((ItemFrame)(var2));
			if (ServerConfig.hasOneFruitPerWorldExtendedLogic()){
				ItemStack var4 = var3.getItem();
				var0.setCanceled((!(var4.isEmpty())) && (var4.getItem() instanceof AkumaNoMiItem));
			}
		}
		if (((var0.getHand()) != (InteractionHand.OFF_HAND)) && (!(var1.level().isClientSide))){
			Entity var3_1 = var0.getTarget();
			if (var3_1 instanceof Player){
				Player var4_1 = ((Player)(var3_1));
				if (CarryHandler.tryLeashTarget(var1,var4_1)){
					var0.setCanceled(true);
					return;
				}else{
					if (CarryHandler.tryStopLeashing(var4_1)){
						var0.setCanceled(true);
						return;
					}else{
						MorphsHandler$Common.startRidingZoan(var1,var4_1);
					}
				}
				return;
			}else{
				Entity var4_2 = var0.getTarget();
				if ((var4_2 instanceof LivingEntity) && (CarryHandler.tryPickupTarget(var1,((LivingEntity)(var4_2))))){
					var0.setCanceled(true);
					return;
				}else{
					return;
				}
			}
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onEntityTargetedEvent(LivingChangeTargetEvent var0) {
		LivingEntity var1 = var0.getNewAboutToBeSetTarget();
		if ((var1) != (null)){
			if (OutOfBodyHandler.isOutOfBody(var1)){
				var0.setCanceled(true);
				return;
			}else{
				if (DoaHelper.isInsideDoor(var1)){
					var0.setCanceled(true);
					return;
				}else{
					return;
				}
			}
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onMount(EntityMountEvent var0) {
		if (!(var0.getLevel().isClientSide)){
			Entity var1 = var0.getEntityMounting();
			Entity var2 = var0.getEntityBeingMounted();
			boolean var3 = var0.isMounting();
			if (var1 instanceof LivingEntity){
				AnimationsHandler.toggleMountingAnimations(((LivingEntity)(var1)),var2,var3);
			}
			if ((!(var3)) && (var2 instanceof Player)){
				Player var4 = ((Player)(var2));
				ModNetwork.sendTo(new SForceDismountEntityPacket((LivingEntity)(var4)),var4);
			}
			if (var2 instanceof LivingEntity){
				LivingEntity var4_1 = ((LivingEntity)(var2));
				if (var3){
					EffectsHandler.tryLinkEffectsWithPassangers(var4_1);
				}else{
					EffectsHandler.tryUnlinkEffectsWithPassangers(var4_1);
				}
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onCarry(EntityCarryEvent var0) {
		LivingEntity var1 = var0.getEntity();
		if (var0.isCarrying()){
			EffectsHandler.tryLinkEffectsWithPassangers(var1);
		}else{
			EffectsHandler.tryUnlinkEffectsWithPassangers(var1);
		}
	}
	@SubscribeEvent()
	public static void onTameEvent(AnimalTameEvent var0) {
		QuestsHandler.handleTameObjective(var0.getTamer());
	}
	@SubscribeEvent()
	public static void onLivingAttack(LivingIncomingDamageEvent var0) {
		if (var0.getEntity().level().isClientSide){
			return;
		}else{
			DamageSource var1 = var0.getSource();
			Entity var2 = var1.getEntity();
			Entity var3 = var1.getDirectEntity();
			LivingEntity var4 = var0.getEntity();
			if ((OutOfBodyHandler.isOutOfBody(var4)) && (!(var1.is(ModDamageTypes.OUT_OF_BODY)))){
				var0.setCanceled(true);
				return;
			}else{
				if (BlocksHandler.tryBlockSuffocationForLogia(var4,var1)){
					var0.setCanceled(true);
					return;
				}else{
					float var5 = var0.getAmount();
					if (var2 instanceof LivingEntity){
						LivingEntity var6 = ((LivingEntity)(var2));
						if ((EffectsHelper.isHandcuffed(var6)) && (var1.is(DamageTypes.MOB_ATTACK))){
							var0.setCanceled(true);
							return;
						}else{
							if (ComponentsHandler.tryTriggerHitComponents(var6,var4,var1)){
								var0.setCanceled(true);
								return;
							}else{
								if (FactionHandler.tryBlockingFriendlyFire(var6,var4,var1,var5)){
									var0.setCanceled(true);
									return;
								}
							}
						}
					}
					if (!(AbilityProtectionHandler.shouldHurtEntityInProtectionArea(var4,var1))){
						var0.setCanceled(true);
						return;
					}else{
						if (var3 instanceof LivingEntity){
							LivingEntity var6_1 = ((LivingEntity)(var3));
							ItemsHandler.cuckShield(var4,var6_1);
						}
						var5 = ComponentsHandler.handleDamageTakenComponents(var4,var1,var5,DamageTakenComponent$DamageState.ATTACK);
						QuestsHandler.handleDamageTakenObjective(var4,var1,var5,DamageTakenComponent$DamageState.ATTACK);
						if ((((var5) <= (0F)) ? (1) : (0)) != (0)){
							var0.setCanceled(true);
							return;
						}else{
							EffectsHandler.damageHandcuffs(var4,var5);
							return;
						}
					}
				}
			}
		}
	}
	@SubscribeEvent()
	public static void onLivingDamage(LivingDamageEvent.Pre var0) {
		if (var0.getEntity().level().isClientSide){
			return;
		}else{
			LivingEntity var1 = var0.getEntity();
			DamageSource var2 = var0.getSource();
			boolean var3 = false;
			int var4 = ((var2.is(DamageTypes.PLAYER_ATTACK)) || (var2.is(DamageTypes.MOB_ATTACK))) ? (1) : (0);
			boolean var5 = var2.is(ModDamageTypes.ABILITY);
			boolean var6 = var2.is(DamageTypeTags.IS_PROJECTILE);
			Entity var7 = var2.getEntity();
			Entity var8 = var2.getDirectEntity();
			if (((var4) == (0)) && ((!(var5)) && (!(var6)))){

			}else{
				var3 = true;
			}
			IDamageSourceHandler var9 = IDamageSourceHandler.getHandler(var2);
			if (!(var9.isInitialized())){
				IDamageSourceHandler$NuDamageValue var10 = DamageSourceHelper.initializeDamageValueFromSource(var2);
				var9.addDamage(var0.getNewDamage(),var10);
			}
			boolean var10_1 = false;
			LivingEntity var11 = null;
			if (var3){
				if (var8 instanceof LivingEntity){
					LivingEntity var12 = ((LivingEntity)(var8));
					EnchantmentsHandler.tryImpactDialEffect(var12,var1);
					EnchantmentsHandler.tryFlashDialEffect(var12,var1);
				}
				if (var7 instanceof LivingEntity){
					LivingEntity var12_1 = ((LivingEntity)(var7));
					ItemStack var13 = var12_1.getMainHandItem();
					var4 = (var4) | (((var9.hasType(SourceType.FIST)) && (var13.isEmpty())) ? (1) : (0));
					if (var9.hasType(SourceType.INDIRECT)){
						var4 = 0;
					}
					if ((var4) != (0)){
						var11 = var12_1;
					}
					var10_1 = true;
				}
				if ((var2.getEntity() instanceof Player) && (FGCommand.SHOW_DEBUG_DAMAGE)){
					float var12_2 = var9.getTotalBaseDamage();
					float var13_1 = var9.getTotalAbilityDamage();
					float var14 = var9.getGlobalPiercing();
					String var15 = "\nBase Damage: " + var12_2 + "\nAbility Damage: " + var13_1 + "\nGlobal Pierce: " + var14;
					WyDebug.debug(var15);
				}
			}
			if ((var11) != (null)){
				ComponentsHandler.triggerDamageSourceComponents(var11,var1,var2);
			}
			float var12_3 = AttributesHandler.getDamageAfterProtection(var2,var1);
			if (var8 instanceof LivingEntity){
				LivingEntity var13_2 = ((LivingEntity)(var8));
				if (EffectsHandler.applyHandcuffs(var1,var13_2,var12_3)){
					var0.setNewDamage(0F);
					return;
				}
			}
			if ((var11) != (null)){
				var12_3 = EffectsHandler.applyBreakableEffectDamage(var1,var12_3);
			}
			float var13_3 = ComponentsHandler.handleDamageTakenComponents(var1,var2,var12_3,DamageTakenComponent$DamageState.HURT);
			if ((var11) != (null)){
				ComponentsHandler.triggerHitComponents(var11,var1,var2,(Set)(null));
			}
			QuestsHandler.handleDamageTakenObjective(var1,var2,var13_3,DamageTakenComponent$DamageState.HURT);
			if (var7 instanceof LivingEntity){
				LivingEntity var14_1 = ((LivingEntity)(var7));
				QuestsHandler.handleHitObjective(var14_1,var1,var2,var13_3);
				if (KnockdownHandler.tryKnockdown(var14_1,var1,var13_3)){
					var0.setNewDamage(0F);
					return;
				}
			}
			if (var1 instanceof Player){
				StatsGainHandler.giveOnHitKenbunshokuExp(((Player)(var1)),var2,var13_3);
			}
			RevengeHandler.checkHits(var1,var2);
			if (var10_1){
				AttributesHandler.handleHurtMath(var2,var1,var13_3);
				var0.setNewDamage(0F);
			}
			float var14_2 = var13_3;
			float var15_1 = ComponentsHandler.handleDamageTakenComponents(var1,var2,var14_2,DamageTakenComponent$DamageState.DAMAGE);
			EffectsHandler.damageBubblyCoral(var1);
			EffectsHandler.storeDrunkDamage(var1,var15_1);
			EffectsHandler.effectsReduction(var1,var2);
			if ((!(var1.level().isClientSide)) && (var1 instanceof OPBossEntity)){
				OPBossEntity var16 = ((OPBossEntity)(var1));
				if (((var16.getDamageCeiling()) > (0F)) && ((var14_2) > (var16.getDamageCeiling()))){
					float var17 = (var14_2) - (var16.getDamageCeiling());
					var0.setNewDamage(((var14_2) - (var17)) + ((var17) / (3F)));
					return;
				}
			}
			QuestsHandler.handleDamageTakenObjective(var1,var2,var15_1,DamageTakenComponent$DamageState.DAMAGE);
			return;
		}
	}
	@SubscribeEvent()
	public static void onPlayerHitsEntity(AttackEntityEvent var0) {
		Player var1 = var0.getEntity();
		if (OutOfBodyHandler.isOutOfBody((LivingEntity)(var1))){
			var0.setCanceled(true);
			return;
		}else{
			if (DoaHelper.isInsideDoor((LivingEntity)(var1))){
				var0.setCanceled(true);
				return;
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onProjectileImpactEvent(ProjectileImpactEvent var0) {
		Projectile var1 = var0.getProjectile();
		if ((var0.getRayTraceResult().getType()) == (HitResult.Type.ENTITY)){
			Entity var2 = ((EntityHitResult)(var0.getRayTraceResult())).getEntity();
			if (var2 instanceof LivingEntity){
				EffectsHandler.applyWetEffectFromProjectile(((LivingEntity)(var2)),var1);
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onProjectileShoot(ProjectileShootEvent var0) {
		QuestsHandler.handleProjectileShotObjective((Projectile)(var0.getProjectile()));
	}
	@SubscribeEvent()
	public static void onEntityLeaveLevel(EntityLeaveLevelEvent var0) {
		Entity var1 = var0.getEntity();
		if (var1 instanceof ItemEntity){
			ItemEntity var2 = ((ItemEntity)(var1));
			Entity.RemovalReason var3 = var2.getRemovalReason();
			if (((var3) != (null)) && (var3.shouldDestroy())){
				ItemStack var4 = var2.getItem();
				if (var4.isEmpty()){
					return;
				}else{
					Item var5 = var4.getItem();
					if (var5 instanceof IDestroyableItem){
						IDestroyableItem var6 = ((IDestroyableItem)(var5));
						var6.onEntityDestroyed(var2,var4,var3);
					}else{
						return;
					}
				}
			}else{
				return;
			}
		}
		Entity var2_1 = var0.getEntity();
		if (var2_1 instanceof Projectile){
			Projectile var3_1 = ((Projectile)(var2_1));
			if (var3_1.isRemoved()){
				QuestsHandler.handleProjectileDespawnObjective(var3_1);
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onEntityHeal(LivingHealEvent var0) {
		var0.setAmount(AttributesHandler.applyExtraHeal(var0.getEntity(),var0.getAmount()));
	}
	public static void onHealedByEvent(LivingHealByEvent var0) {
		LivingEntity var1 = var0.getEntity();
		LivingEntity var2 = var0.getHealer();
		float var3 = var0.getAmount();
		if (var2 instanceof Player){
			QuestsHandler.handleHealObjective(((Player)(var2)),var1,var3);
		}
	}
	@SubscribeEvent()
	public static void onItemUsed(LivingEntityUseItemEvent.Start var0) {
		LivingEntity var1 = var0.getEntity();
		ItemStack var2 = var0.getItem();
		if (DoaHelper.isInsideDoor(var1)){
			var0.setCanceled(true);
			return;
		}else{
			if (ChallengesHandler.isItemBlockedInChallenge(var1,var2)){
				var0.setCanceled(true);
				return;
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onPlayerUseItemResult(PlayerUseItemResultEvent var0) {
		Player var1 = var0.getEntity();
		if ((var1) == (null)){
			return;
		}else{
			if (var1.level().isClientSide){
				return;
			}else{
				if (var0.getInteractionResult().indicateItemUse()){
					QuestsHandler.handleUseItemObjective(var1,var0.getOriginalStack(),0);
				}
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onItemStoppedUsed(LivingEntityUseItemEvent.Stop var0) {
		LivingEntity var1 = var0.getEntity();
		ItemStack var2 = var0.getItem();
		int var3 = var0.getDuration();
		if (var2.isEmpty()){
			return;
		}else{
			if (var1 instanceof Player){
				QuestsHandler.handleUseItemObjective(((Player)(var1)),var2,var3);
			}
			if ((ItemsHelper.isBowOrGun(var2)) && (ComponentsHandler.triggerBowShootComponents(var1))){
				var0.setCanceled(true);
				return;
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onItemConsumed(LivingEntityUseItemEvent.Finish var0) {
		LivingEntity var1 = var0.getEntity();
		ItemStack var2 = var0.getItem();
		if (var2.isEmpty()){
			return;
		}else{
			EffectsHandler.damageDehydrationEffect(var1,var2);
			YomiPassiveHandler.milkHeal(var1,var2);
			return;
		}
	}
	@SubscribeEvent()
	public static void onEntityItemPickup(ItemEntityPickupEvent.Pre var0) {
		Player var1 = var0.getPlayer();
		ItemEntity var2 = var0.getItemEntity();
		ItemStack var3 = var2.getItem();
		if (OutOfBodyHandler.isOutOfBody((LivingEntity)(var1))){
			var0.setCanPickup(TriState.FALSE);
			return;
		}else{
			if (DoaHelper.isInsideDoor((LivingEntity)(var1))){
				var0.setCanPickup(TriState.FALSE);
				return;
			}else{
				if (var1 instanceof Player){
					Player var4 = var1;
					if (ItemsHandler.handleEmptyHandsPickup(var4,var2)){
						var0.setCanPickup(TriState.FALSE);
						return;
					}else{
						QuestsHandler.handleCollectItemObjective(var4,var3,var3.getCount());
						TriState var5 = (OneFruitPerWorldHandler.cancelPickUpSimple(var4,var3)) ? (TriState.FALSE) : (TriState.DEFAULT);
						if (ServerConfig.hasOneFruitPerWorldSimpleLogic()){
							var0.setCanPickup(var5);
							return;
						}
					}
				}
				Item var4_1 = var3.getItem();
				if (var4_1 instanceof AkumaNoMiItem){
					AkumaNoMiItem var5_1 = ((AkumaNoMiItem)(var4_1));
					OneFruitWorldData.get().inventoryOneFruit(var5_1,(LivingEntity)(var1),(String)(null));
				}
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onItemTossed(ItemTossEvent var0) {
		Player var1 = var0.getPlayer();
		ItemStack var2 = var0.getEntity().getItem();
		if (OutOfBodyHandler.isOutOfBody((LivingEntity)(var1))){
			var1.addItem(var0.getEntity().getItem());
			var0.setCanceled(true);
			return;
		}else{
			if (DoaHelper.isInsideDoor((LivingEntity)(var1))){
				var1.addItem(var0.getEntity().getItem());
				var0.setCanceled(true);
				return;
			}else{
				ItemsHandler.removeImbuingTag(var2);
				QuestsHandler.handleCollectItemObjective(var1,var2,-(var2.getCount()));
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onItemExpire(ItemExpireEvent var0) {
		if (ServerConfig.hasOneFruitPerWorldSimpleLogic()){
			Item var1 = var0.getEntity().getItem().getItem();
			if (var1 instanceof AkumaNoMiItem){
				OneFruitPerWorldHandler.expire(((AkumaNoMiItem)(var1)));
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onItemAttributesUpdate(ItemAttributeModifierEvent var0) {
		SwordsmanPassivesHandler.applySwordsmanBounsAttribute(var0,var0.getItemStack());
	}
	@SubscribeEvent()
	public static void onEquipmentUpdate(LivingEquipmentChangeEvent var0) {
		if (var0.getEntity().level().isClientSide){
			return;
		}else{
			LivingEntity var1 = var0.getEntity();
			EquipmentSlot var2 = var0.getSlot();
			ItemStack var3 = var0.getTo();
			ProgressionHandler.checkForEquipmentUnlocks(var1);
			SwordsmanPassivesHandler.applySwordsmanBonusTag(var1,var2,var3);
			if (var1 instanceof Player){
				QuestsHandler.handleEquipmentObjective(((Player)(var1)),var2,var3);
			}
			return;
		}
	}
	@SubscribeEvent()
	public static void onEntityRightClickItem(PlayerInteractEvent.RightClickItem var0) {
		Player var1 = var0.getEntity();
		ItemStack var2 = var0.getItemStack();
		DevilFruitData var3 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		if ((ServerConfig.areExtraWaterChecksEnabled()) && (((var3) != (null)) && ((var3.hasAnyDevilFruit()) && (AbilityHelper.isAffectedByWater((LivingEntity)(var1)))))){
			var0.setCanceled(true);
			return;
		}else{
			if (OutOfBodyHandler.isOutOfBody((LivingEntity)(var1))){
				var0.setCanceled(true);
				return;
			}else{
				KukuPassivesHandler.enableKukuEating(var1,var2);
				RandomizedFruitsHandler$Common.handleClueUsage(var1,var2);
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onEntityBreaksBlocks(BlockEvent.BreakEvent var0) {
		Player var1 = var0.getPlayer();
		BlockState var2 = var0.getState();
		if (OutOfBodyHandler.isOutOfBody((LivingEntity)(var1))){
			var0.setCanceled(true);
			return;
		}else{
			if (DoaHelper.isInsideDoor((LivingEntity)(var1))){
				var0.setCanceled(true);
				return;
			}else{
				if (var2.is(Tags.Blocks.CHESTS)){
					FactionHandler.tryLowerLoyaltyFromStealing(var1);
				}
				if (var0.getState().getBlock() instanceof LeavesBlock){
					OneFruitPerWorldHandler.breakLeaves(var0.getPlayer(),((Level)(var0.getLevel())),var0.getPos(),ServerConfig.hasOneFruitPerWorldSimpleLogic());
				}
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onEntityPlaceBlocks(BlockEvent.EntityPlaceEvent var0) {
		Entity var1 = var0.getEntity();
		if (var1 instanceof LivingEntity){
			LivingEntity var2 = ((LivingEntity)(var1));
			DevilFruitData var3 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var2))).orElse((Object)(null))));
			if ((ServerConfig.areExtraWaterChecksEnabled()) && (((var3) != (null)) && ((var3.hasAnyDevilFruit()) && (AbilityHelper.isAffectedByWater(var2))))){
				var0.setCanceled(true);
				return;
			}else{
				if (OutOfBodyHandler.isOutOfBody(var2)){
					var0.setCanceled(true);
					return;
				}else{
					if (DoaHelper.isInsideDoor(var2)){
						var0.setCanceled(true);
						return;
					}else{
						return;
					}
				}
			}
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed var0) {
		Player var1 = var0.getEntity();
		DevilFruitData var2 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		float var3 = var0.getOriginalSpeed();
		float var4 = AttributesHandler.applyBreakSpeed(var1,var3);
		if (((var2) != (null)) && ((var2.hasAnyDevilFruit()) && (AbilityHelper.isAffectedByWater((LivingEntity)(var1))))){
			var4 = (var4) / (15F);
			if (ServerConfig.areExtraWaterChecksEnabled()){
				var4 = 0F;
			}
		}
		float var5 = EffectsHandler.applySwingSpeedEffects((LivingEntity)(var1),var4);
		if (HakiHelper.hasImbuingActive((LivingEntity)(var1))){
			var5 = (var5) + (5F);
		}
		if ((var5) != (var0.getOriginalSpeed())){
			var0.setNewSpeed(var5);
		}
	}
	@SubscribeEvent()
	public static void onEntityLeftClickBlocks(PlayerInteractEvent.LeftClickBlock var0) {
		Player var1 = var0.getEntity();
		DevilFruitData var2 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		if ((ServerConfig.areExtraWaterChecksEnabled()) && (((var2) != (null)) && ((var2.hasAnyDevilFruit()) && (AbilityHelper.isAffectedByWater((LivingEntity)(var1)))))){
			var0.setCanceled(true);
			return;
		}else{
			if (OutOfBodyHandler.isOutOfBody((LivingEntity)(var1))){
				var0.setCanceled(true);
				return;
			}else{
				if (DoaHelper.isInsideDoor((LivingEntity)(var1))){
					var0.setCanceled(true);
					return;
				}else{
					return;
				}
			}
		}
	}
	@SubscribeEvent()
	public static void onEntityRightClickBlocks(PlayerInteractEvent.RightClickBlock var0) {
		Player var1 = var0.getEntity();
		DevilFruitData var2 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		if ((ServerConfig.areExtraWaterChecksEnabled()) && (((var2) != (null)) && ((var2.hasAnyDevilFruit()) && (AbilityHelper.isAffectedByWater((LivingEntity)(var1)))))){
			var0.setCanceled(true);
			return;
		}else{
			if (OutOfBodyHandler.isOutOfBody((LivingEntity)(var1))){
				var0.setCanceled(true);
				return;
			}else{
				if (DoaHelper.isInsideDoor((LivingEntity)(var1))){
					var0.setCanceled(true);
					return;
				}else{
					return;
				}
			}
		}
	}
	@SubscribeEvent()
	public static void onEffectModificationEvent(EffectParticleModificationEvent var0) {
		MobEffectInstance var1 = var0.getEffect();
		Object var2 = var1.getEffect().value();
		if (var2 instanceof BaseEffect){
			BaseEffect var3 = ((BaseEffect)(var2));
			if ((var3.shouldRenderParticles(var1)) && ((var3.getColor()) != (0))){
				return;
			}else{
				var0.setVisible(false);
			}
		}
	}
	@SubscribeEvent()
	public static void onPotionExpires(MobEffectEvent.Applicable var0) {
		LivingEntity var1 = var0.getEntity();
		if (!(EffectsHandler.checkApplicableEffect(var1,var0.getEffectInstance()))){
			var0.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
		}
	}
	@SubscribeEvent()
	public static void onPotionAdded(MobEffectAfterAddedEvent var0) {
		LivingEntity var1 = var0.getEntity();
		MobEffectInstance var2 = var0.getEffectInstance();
		EffectsHandler.disableAbilitiesEffect(var1,var2);
		EffectsHandler.tryLinkEffectsWithPassangers(var1);
		EffectsHandler.trySpreadProximityEffect(var1,var2);
		EffectsHandler.applyScreenShader(var1,var2);
		if (!(var1.level().isClientSide)){
			Object var3 = var2.getEffect().value();
			if (var3 instanceof BaseEffect){
				((BaseEffect)(var3)).onStartEffect(var1,var2);
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onPotionExpires(MobEffectEvent.Expired var0) {
		LivingEntity var3 = null;
		LivingEntity var1 = var0.getEntity();
		MobEffectInstance var2 = var0.getEffectInstance();
		if ((var2) == (null)){
			return;
		}else{
			if (var2.getEffect().equals(ModEffects.DRUNK)){
				var3 = var1;
				final LivingEntity var3_f1 = var3;
				Consumer<CombatData> var4 = (l0) -> {
			var3_f1.hurt(ModDamageSources.getInstance((LevelAccessor)(var3_f1.level())).storedDamage(),l0.getStoredDamage());
			l0.setStoredDamage(0F);
		};
				((java.util.Optional)(CombatData.get(var1))).ifPresent((Consumer)(var4));
			}
			EffectsHandler.enableAbilitiesEffect(var1,var2);
			EffectsHandler.tryUnlinkEffectsWithPassangers(var1);
			EffectsHandler.removeScreenShader(var1,var2);
			return;
		}
	}
	@SubscribeEvent()
	public static void onPotionRemoved(MobEffectEvent.Remove var0) {
		LivingEntity var1 = var0.getEntity();
		MobEffectInstance var2 = var0.getEffectInstance();
		if ((var2) == (null)){
			return;
		}else{
			if (EffectsHandler.blockMilkRemoval(var1,var2)){
				var0.setCanceled(true);
			}
			EffectsHandler.enableAbilitiesEffect(var1,var2);
			EffectsHandler.tryUnlinkEffectsWithPassangers(var1);
			EffectsHandler.removeScreenShader(var1,var2);
			if (!(var1.level().isClientSide)){
				Object var3 = var2.getEffect().value();
				if (var3 instanceof BaseEffect){
					((BaseEffect)(var3)).onStopEffect(var1);
				}
			}
			if (var1 instanceof Player){
				QuestsHandler.handleCureEffectObjective(((Player)(var1)),var2);
			}
			return;
		}
	}
	@SubscribeEvent()
	public static void onHakiExpGained(HakiExpEvent$Post var0) {
		ProgressionHandler.checkForHakiUnlocks(var0.getEntity());
	}
	@SubscribeEvent()
	public static void onDorikiGained(DorikiEvent$Post var0) {
		Player var1 = var0.getEntity();
		QuestsHandler.handleReachDorikiObjective(var1,var0.getDoriki());
		ProgressionHandler.checkForRacialUnlocks(var1);
		ProgressionHandler.checkForHakiUnlocks(var1);
	}
	@SubscribeEvent()
	public static void onAbilityUseCheck(AbilityCanUseEvent var0) {
		LivingEntity var1 = var0.getEntity();
		IAbility var2 = var0.getAbility();
		if ((DoaHelper.isInsideDoor(var1)) && (!(var2 instanceof AirDoorAbility))){
			var0.setCanceled(true);
			return;
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onPostAbilityUsed(AbilityUseEvent$Pre var0) {
		LivingEntity var1 = var0.getEntity();
		IAbility var2 = var0.getAbility();
		if (OutOfBodyHandler.isOutOfBody(var1)){
			if (OutOfBodyHandler.canUseAbility(var1,var2)){
				return;
			}else{
				var0.setCanceled(true);
				return;
			}
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onPostAbilityUsed(AbilityUseEvent$Post var0) {
		LivingEntity var1 = var0.getEntity();
		IAbility var2 = var0.getAbility();
		if (var1 instanceof Player){
			QuestsHandler.handleUseAbilityObjective(((Player)(var1)),var2);
		}
	}
	@SubscribeEvent()
	public static void onChestOpened(PlayerContainerEvent.Open var0) {
		Player var1 = var0.getEntity();
		if (var0.getContainer() instanceof ChestMenu){
			FactionHandler.tryLowerLoyaltyFromStealing(var1);
		}
	}
	@SubscribeEvent()
	public static void onContainerClose(PlayerContainerEvent.Close var0) {
		QuestsHandler.handleRemainingCollectedItems(var0.getEntity());
		AbstractContainerMenu var1 = var0.getContainer();
		if (var1 instanceof InventoryMenu){
			InventoryMenu var2 = ((InventoryMenu)(var1));
			if (ServerConfig.hasOneFruitPerWorldExtendedLogic()){
				OneFruitPerWorldHandler.containerClose(var2,var0.getEntity());
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onServerMessage(ServerChatEvent var0) {
		ServerPlayer var1 = var0.getPlayer();
		Function<AbilityData, SilentAbility> var2 = (l0) -> {
			return ((SilentAbility)(l0.getEquippedAbility(((AbilityCore)(SilentAbility.INSTANCE.get())))));
		};
		boolean var3 = ((Boolean)(((java.util.Optional)(AbilityData.get((LivingEntity)(var1)))).map((Function)(var2)).map((Function)(((Function<SilentAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue();
		if ((var1.hasEffect((Holder)(ModEffects.SILENT))) && (!(var3))){
			var0.setCanceled(true);
			return;
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onInteractionTriggered(InteractionTriggeredEvent var0) {
		LivingEntity var1 = var0.getEntity();
		if (InputHandler$Common.canBlockHandSwing(var1,var0.getHand())){
			var0.setCanceled(true);
			return;
		}else{
			if (var1.level().isClientSide){
				return;
			}else{
				if ((var0.getHand()) != (InteractionHand.MAIN_HAND)){
					var0.setCanceled(true);
					return;
				}else{
					if (var1 instanceof Player){
						Player var2 = ((Player)(var1));
						if ((((var0.isAttack()) && (var0.isHittingEntity())) ? (1) : (0)) == (0)){
							ComponentsHandler.triggerSwingComponents(var2);
						}
						QuestsHandler.handleSwingObjective(var2);
						return;
					}else{
						return;
					}
				}
			}
		}
	}
}