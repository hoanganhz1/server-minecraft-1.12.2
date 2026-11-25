package xyz.pixelatedw.mineminenomi.api.challenges;

import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule$Builder;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.Interval$Mutable;
import xyz.pixelatedw.mineminenomi.api.util.TPSDelta;
import xyz.pixelatedw.mineminenomi.challenges.arenas.PoneglyphLowspecArena;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;

public class InProgressChallenge {
	// Fields
	private static final int COUNTDOWN_SECONDS = 10;
	private static final int COUNTDOWN_TICKS = 200;
	private static final String[] LOADING_FRAMES;
	private static final BlockProtectionRule REPAIR_RULE;
	private final UUID id;
	private int inProgressTick;
	private Interval readyInterval;
	private Interval endWaitInterval;
	private Interval$Mutable tickInterval;
	private InProgressChallenge$Phase phase;
	private InProgressChallenge$Result result;
	private ServerPlayer owner;
	private BlockPos returnPosition;
	private ResourceKey<Level> returnDimension;
	private ServerLevel shard;
	private ChallengeCore$ArenaEntry arenaEntry;
	private BlockPos pos;
	private List<LivingEntity> group;
	private HashMap<UUID, CompoundTag> groupCache;
	private Set<LivingEntity> enemies;
	private int tickLimit;
	private long startTick;
	private long finishTick;
	private ChallengeCore<?> core;
	private boolean isFree;
	private boolean isCompleted;
	private Set<BlockPos> blocksPlaced;
	private Optional<ChallengeData> ownerProps;
	private Random random;
	private DimensionTransition homeTransition;
	private BlockQueue blockQueue;
	private long startBuildTime;
	private int totalBlocksPlaced;
	private float deltaTime;
	private Set<BlockPos> trackedManuallyPlacedBlocks;

	public InProgressChallenge(UUID var1, ServerPlayer var2, ServerLevel var3, @Nullable() List<LivingEntity> var4, Challenge var5, boolean var6) {
		this.readyInterval = ((Interval)(Interval.startAtMax(10).trackTPS()));
		this.endWaitInterval = ((Interval)(Interval.startAtMax(10).trackTPS()));
		this.tickInterval = ((Interval$Mutable)(new Interval$Mutable(5).trackTPS()));
		this.phase = InProgressChallenge$Phase.BUILD;
		this.result = InProgressChallenge$Result.TBD;
		this.groupCache = (HashMap) (new HashMap());
		this.enemies = new HashSet();
		this.isFree = false;
		this.isCompleted = false;
		this.ownerProps = (Optional) (((java.util.Optional)(Optional.empty())));
		this.deltaTime = 0F;
		this.trackedManuallyPlacedBlocks = new HashSet();
		this.id = var1;
		this.owner = var2;
		this.returnPosition = this.owner.blockPosition();
		this.returnDimension = (ResourceKey) (this.owner.level().dimension());
		this.core = var5.getCore();
		this.shard = var3;
		this.arenaEntry = PoneglyphLowspecArena.ARENA_INFO;
		this.pos = new BlockPos(0,50,0);
		this.group = new ArrayList();
		((java.util.List)(this.group)).add((Object)(var2));
		if ((var4) != (null)){
			((java.util.List)(this.group)).addAll((Collection)(var4));
		}
		this.isFree = var6;
		this.ownerProps = (Optional) (ChallengeData.get((Player)(this.owner)));
		this.random = new Random();
		this.homeTransition = new DimensionTransition(var2.getServer().getLevel(this.returnDimension),Vec3.atCenterOf((Vec3i)(this.returnPosition)),Vec3.ZERO,0F,0F,false,DimensionTransition.DO_NOTHING);
		this.blockQueue = new BlockQueue(this.shard).setSpeed(ServerConfig.getArenaGenerationSpeed());
		this.tickLimit = ((var5.getCore().getTimeLimit()) * (60)) * (20);
		this.inProgressTick = 0;
		this.tickInterval.restartIntervalToMax();
		this.readyInterval.restartIntervalToMax();
		this.endWaitInterval.restartIntervalToMax();
	}
	public void tick() {
		this.deltaTime = TPSDelta.INSTANCE.getDeltaTime();
		this.inProgressTick = (int)(((float)(this.inProgressTick)) + (this.deltaTime));
		if ((((this.phase) != (InProgressChallenge$Phase.RUN)) && ((this.phase) != (InProgressChallenge$Phase.END))) || (!(this.group.isEmpty()))){
			if (!(this.tickInterval.canTick())){
				return;
			}else{
				if (this.phase.equals(InProgressChallenge$Phase.POSTEND)){
					this.executePostEndPhase();
				}else{
					if (this.phase.equals(InProgressChallenge$Phase.END)){
						this.executeEndPhase();
					}else{
						if (this.phase.equals(InProgressChallenge$Phase.RUN)){
							this.executeRunPhase();
						}else{
							if (this.phase.equals(InProgressChallenge$Phase.BUILD)){
								this.executeBuildPhase();
							}else{
								if (this.phase.equals(InProgressChallenge$Phase.SPAWN)){
									this.executeSpawnPhase();
								}else{
									if (this.phase.equals(InProgressChallenge$Phase.READY)){
										this.executeReadyPhase();
									}else{
										return;
									}
								}
							}
						}
					}
				}
				return;
			}
		}else{
			this.resetChallengeBonus();
			this.wipeOldEntities(false);
			this.phase = InProgressChallenge$Phase.POSTEND;
			return;
		}
	}
	private void executeBuildPhase() {
		ChallengeData var1 = ((ChallengeData)(((java.util.Optional)(this.ownerProps)).orElse((Object)(null))));
		if ((var1) == (null)){
			this.result = InProgressChallenge$Result.TBD;
			this.phase = InProgressChallenge$Phase.POSTEND;
			return;
		}else{
			if (!(this.blockQueue.isRunning())){
				int var2 = -5;
				LOOP_1:
				do{
					if ((var2) < (5)){
						int var3 = -5;
						do{
							if ((var3) < (5)){
								MineMineNoMi.TICKET_CONTROLLER.forceChunk(this.shard,this.pos,var2,var3,true,false);
								var3++;
								continue;
							}else{
								break;
							}
						} while (true);
						var2++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				if (var1.isArenaDirty()){
					long var3_1 = System.nanoTime();
					ChallengeArena var4 = this.arenaEntry.arena();
					if ((var1.getPreviousChallenge()) != (null)){
						ChallengeCore$ArenaEntry var5 = var1.getPreviousChallenge().getArenaFromStyle(var1.getPreviousArenaStyle(),var1.getPreviousArenaClass());
						if ((var5) != (null)){
							var4 = var5.arena();
						}
					}
					if ((var4) == (null)){
						ChallengeArena var5_1 = this.arenaEntry.arena();
					}
					new SimpleBlockPlacer().setBlock(Blocks.AIR.defaultBlockState()).setRule(REPAIR_RULE).setFlag(512).setSize(50).generate((Level)(this.shard),this.pos,BlockGenerators.CUBE);
					long var5_2 = ((System.nanoTime()) - (var3_1)) / (1000000L);
					float var6 = ((float)(var5_2)) / (1000F);
					String var7 = "EMERGENCY CLEAR TIME: " + var6 + "s";
					WyDebug.debug(var7);
				}
				this.trackedManuallyPlacedBlocks.clear();
				this.startBuildTime = this.shard.getGameTime();
				this.arenaEntry.arena().buildArena(this,this.blockQueue);
				this.totalBlocksPlaced = this.blockQueue.getQueueSize();
				int var3_2 = this.blockQueue.getQueueSize();
				String var4_1 = "Size: " + var3_2 + " blocks";
				WyDebug.debug(var4_1);
				this.blockQueue.start();
			}else{
				if (this.blockQueue.isDone()){
					this.sendGroupActionbar(ModI18nChallenges.STARTING_CHALLENGE,5,20,5);
					float var2_1 = ((float)((this.shard.getGameTime()) - (this.startBuildTime))) / (20F);
					float var3_3 = var2_1;
					String var4_2 = "BUILD TIME: " + var3_3 + "s";
					WyDebug.debug(var4_2);
					var1.markArenaDirty(true);
					var1.setPreviousChallenge(this.core,this.arenaEntry.arena().getStyle(),this.arenaEntry.arena().getClass().getName());
					this.phase = InProgressChallenge$Phase.SPAWN;
					this.tickInterval.setInterval(100);
					this.blockQueue.stop();
					return;
				}else{
					this.blockQueue.tick();
					StringBuilder var2_2 = new StringBuilder((String)("Generating Arena - "));
					long var3_4 = ((this.shard.getGameTime()) - (this.startBuildTime)) / (20L);
					long var4_3 = var3_4;
					String var5_3 = "" + var4_3 + "s - ";
					var2_2.append(var5_3);
					float var6_1 = ((1F) - (((float)(this.blockQueue.getQueueSize())) / ((float)(this.totalBlocksPlaced)))) * (100F);
					String var7_1 = String.format("%.1f",new Object[]{Float.valueOf(var6_1)});
					String var8 = var7_1 + "%";
					var2_2.append(var8);
					this.sendGroupActionbar((Component)(Component.literal(var2_2.toString())),5,20,5);
				}
			}
			return;
		}
	}
	private void executeSpawnPhase() {
		this.wipeOldEntities(true);
		this.spawnChallengers();
		this.spawnEnemies();
		this.phase = InProgressChallenge$Phase.READY;
		this.tickInterval.setInterval(20);
	}
	private void executeReadyPhase() {
		if ((this.readyInterval.getTick()) <= (10)){
			String var1 = String.valueOf((Object)(ChatFormatting.GOLD));
			int var2 = (this.readyInterval.getTick()) - (1);
			String var3 = var1 + var2;
			this.sendGroupTitle((Component)(Component.literal(var3)),5,(int)(Math.ceil((double)((10F) * ((1F) + (this.deltaTime))))),5);
		}
		if (this.readyInterval.canTick()){
			this.phase = InProgressChallenge$Phase.RUN;
			Consumer<? super LivingEntity> var1_1 = (l0) -> {
			l0.removeEffect((Holder)(ModEffects.IN_EVENT));
		};
			((java.lang.Iterable)(this.group)).forEach((Consumer)(var1_1));
			Consumer<? super LivingEntity> var2_1 = (l0) -> {
			l0.removeEffect((Holder)(ModEffects.IN_EVENT));
		};
			((java.lang.Iterable)(this.enemies)).forEach((Consumer)(var2_1));
			this.sendGroupTitle(ModI18nChallenges.MESSAGE_START_FIGHT,2,(int)(Math.ceil((double)((5F) * ((1F) + (this.deltaTime))))),2);
			this.startTick = this.shard.getGameTime();
		}
	}
	private void executeRunPhase() {
		if ((this.inProgressTick) > (this.tickLimit)){
			this.phase = InProgressChallenge$Phase.END;
			this.result = InProgressChallenge$Result.TIMEOUT;
			this.tickInterval.setInterval(20);
			return;
		}else{
			int var1 = 0;
			Iterator var2 = this.group.iterator();
			do{
				if (var2.hasNext()){
					LivingEntity var3 = ((LivingEntity)(var2.next()));
					if (var3.hasEffect((Holder)(ModEffects.CHALLENGE_FAILED))){
						this.despawnChallenger(var3);
						continue;
					}else{
						if ((var3.isAlive()) && (NuWorld.isChallengeDimension(var3.level()))){
							var1++;
							break;
						}else{
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
			if ((var1) <= (0)){
				this.group.clear();
				this.phase = InProgressChallenge$Phase.END;
				this.result = InProgressChallenge$Result.DEATH;
				return;
			}else{
				if (!(this.enemies.isEmpty())){
					this.enemies.removeIf((Predicate)(((Predicate<LivingEntity>)((l0) -> {
			return ((l0) == (null)) || (!(l0.isAlive()));
		}))));
					return;
				}else{
					if (this.enemies.isEmpty()){
						this.phase = InProgressChallenge$Phase.END;
						this.result = InProgressChallenge$Result.WIN;
						this.finishTick = this.shard.getGameTime();
						this.tickInterval.setInterval(20);
						return;
					}else{
						return;
					}
				}
			}
		}
	}
	private void executePostEndPhase() {
		if (this.blockQueue.isRunning()){
			if (this.blockQueue.isDone()){
				this.blockQueue.stop();
			}
			this.blockQueue.tick();
		}
	}
	private void executeEndPhase() {
		if (this.endWaitInterval.canTick()){
			this.phase = InProgressChallenge$Phase.POSTEND;
			long var1 = System.nanoTime();
			new SimpleBlockPlacer().setBlock(Blocks.AIR.defaultBlockState()).setRule(REPAIR_RULE).setFlag(512).setSize(50).generate((Level)(this.shard),this.pos,BlockGenerators.CUBE);
			long var2 = ((System.nanoTime()) - (var1)) / (1000000L);
			float var3 = ((float)(var2)) / (1000F);
			String var4 = "CLEAR TIME: " + var3 + "s";
			WyDebug.debug(var4);
			MineMineNoMi.TICKET_CONTROLLER.forceChunk(this.shard,this.pos,0,0,false,false);
			Iterator var5 = this.getGroup().iterator();
			do{
				if (var5.hasNext()){
					this.despawnChallenger(((LivingEntity)(var5.next())));
					continue;
				}else{
					break;
				}
			} while (true);
			if ((this.result.isWin()) && (!(this.isCompleted))){
				this.completeChallenge();
				this.isCompleted = true;
			}
			this.groupCache.clear();
			Consumer<? super ChallengeData> var6 = (l0) -> {
			l0.markArenaDirty(false);
		};
			((java.util.Optional)(this.ownerProps)).ifPresent((Consumer)(var6));
			ChallengesWorldData.get().stopChallenge(this);
		}else{
			Object[] var1_1 = new Object[]{Integer.valueOf(this.endWaitInterval.getTick())};
			String var2_1 = Component.translatable(ModI18nChallenges.MESSAGE_END_COUNTDOWN,var1_1).getString();
			String var3_1 = "§f§l" + var2_1 + "§r";
			this.sendGroupActionbar((Component)(Component.literal(var3_1)),5,Math.round((20F) * ((1F) + (this.deltaTime))),5);
		}
	}
	private void completeChallenge() {
		Iterator var1 = this.getGroup().iterator();
		do{
			if (var1.hasNext()){
				LivingEntity var2 = ((LivingEntity)(var1.next()));
				if ((var2.isAlive()) && (var2 instanceof ServerPlayer)){
					ServerPlayer var3 = ((ServerPlayer)(var2));
					ChallengeData var4 = ((ChallengeData)(((java.util.Optional)(ChallengeData.get((Player)(var3)))).orElse((Object)(null))));
					if ((var4) == (null)){
						continue;
					}else{
						Challenge var5 = var4.getChallenge(this.getCore());
						if ((var5) == (null)){
							continue;
						}else{
							int var6 = Math.round(((float)((this.finishTick) - (this.startTick))) / (20F));
							String var7 = "";
							if ((this.hasRestrictions()) && (!(this.hasActiveRestrictions()))){
								var7 = " (TRAINING)";
							}else{
								if (var5.isPersonalBest(var6)){
									var7 = " (PB)";
								}
							}
							String var8 = String.format("§2§l%02d:%02d§r%s",new Object[]{Integer.valueOf((var6) / (60)),Integer.valueOf((var6) % (60)),var7});
							String var9 = "";
							if (this.hasRewards()){
								var9 = var5.getRewards((Player)(var3));
								var5.tryUpdateBestTime(var6);
								var5.setComplete((Player)(var3),true);
							}
							String var10 = ModI18nChallenges.MESSAGE_COMPLETION_REPORT.getString();
							String var11 = var8;
							String var12 = var10 + var11;
							MutableComponent var13 = Component.literal(var12);
							var3.sendSystemMessage(this.core.getLocalizedTitle());
							var3.sendSystemMessage((Component)(var13));
							if ((var9) != (null)){
								var3.sendSystemMessage((Component)(Component.literal(var9)));
								continue;
							}else{
								continue;
							}
						}
					}
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public void despawnChallenger(LivingEntity var1) {
		if ((var1.isAlive()) && ((NuWorld.isChallengeDimension(var1.level())) && (var1 instanceof ServerPlayer))){
			ServerPlayer var2 = ((ServerPlayer)(var1));
			AbilityHelper.disableAbilities((LivingEntity)(var2),1,(Predicate)(Predicates.alwaysTrue()));
			CompoundTag var3 = ((CompoundTag)(this.groupCache.get(var2.getUUID())));
			if ((var3) != (null)){
				ChallengeCache.load((LivingEntity)(var2),var3);
			}
			var2.fallDistance = 0F;
			var2.removeAllEffects();
			var2.setRemainingFireTicks(0);
			var2.removeEffect((Holder)(ModEffects.CHALLENGE_FAILED));
			var1.changeDimension(this.homeTransition);
		}
	}
	private void spawnEnemies() {
		Set var1 = this.core.getEnemySpawns().getEnemySpawns(this,this.arenaEntry.enemyPosition().getEnemySpawnPos(this));
		this.enemies = (Set) (((Set)(((java.util.stream.Stream)(var1.stream().map((Function)(((Function<ChallengeArena$EnemySpawn, LivingEntity>)(ChallengeArena$EnemySpawn::getEntity)))))).collect((Collector)(Collectors.toSet())))));
		Iterator var2 = var1.iterator();
		do{
			if (var2.hasNext()){
				ChallengeArena$EnemySpawn var3 = ((ChallengeArena$EnemySpawn)(var2.next()));
				LivingEntity var4 = var3.getEntity();
				this.getShard().addFreshEntity((Entity)(var4));
				var4.teleportTo((double)(var3.getSpawnPos().getPos().getX()),(double)(var3.getSpawnPos().getPos().getY()),(double)(var3.getSpawnPos().getPos().getZ()));
				var4.setYRot(var3.getSpawnPos().getYaw());
				var4.setXRot(var3.getSpawnPos().getPitch());
				if (var4 instanceof Mob){
					Mob var5 = ((Mob)(var4));
					var5.setTarget((LivingEntity)(this.getOwner()));
					GoalHelper.lookAtEntity(var5,(Entity)(this.getOwner()));
				}
				var4.addEffect(new MobEffectInstance((Holder)(ModEffects.IN_EVENT),300,0));
				var4.heal(var4.getMaxHealth());
				continue;
			}else{
				break;
			}
		} while (true);
	}
	private void spawnChallengers() {
		LivingEntity var10 = null;
		LivingEntity var12 = null;
		LivingEntity var8 = null;
		int var1 = 0;
		Iterator var2 = this.group.iterator();
		do{
			if (var2.hasNext()){
				LivingEntity var3 = ((LivingEntity)(var2.next()));
				if (var3 instanceof ServerPlayer){
					ServerPlayer var4 = ((ServerPlayer)(var3));
					ChallengeArena$SpawnPosition var5 = this.getChallengerPosition().getChallengerSpawnPos(var1,this);
					var3.changeDimension(new DimensionTransition(this.shard,Vec3.atCenterOf((Vec3i)(var5.getPos())),Vec3.ZERO,var5.getYaw(),var5.getPitch(),false,DimensionTransition.DO_NOTHING));
					var1++;
					if (ServerConfig.isChallengesCachingEnabled()){
						this.groupCache.put(var3.getUUID(),ChallengeCache.save((LivingEntity)(var4)));
					}
				}
				var3.heal(var3.getMaxHealth());
				if (var3 instanceof Player){
					((Player)(var3)).getFoodData().eat(20,20F);
				}
				var3.removeAllEffects();
				var3.addEffect(new MobEffectInstance((Holder)(ModEffects.IN_EVENT),300,0));
				AbilityData var4_1 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var3))).orElse((Object)(null))));
				HakiData var5_1 = ((HakiData)(((java.util.Optional)(HakiData.get(var3))).orElse((Object)(null))));
				if ((var4_1) != (null)){
					Iterator var6 = var4_1.getEquippedAbilities().iterator();
					do{
						if (var6.hasNext()){
							IAbility var7 = ((IAbility)(var6.next()));
							if ((var7) == (null)){
								continue;
							}else{
								var8 = var3;
								final LivingEntity var8_f1 = var8;
								Consumer<DisableComponent> var9 = (l0) -> {
			l0.startDisable(var8_f1,1F);
		};
								((java.util.Optional)(var7.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))))).ifPresent((Consumer)(var9));
								var10 = var3;
								final LivingEntity var10_f2 = var10;
								Consumer<DisableComponent> var11 = (l0) -> {
			l0.stopDisable(var10_f2);
		};
								((java.util.Optional)(var7.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))))).ifPresent((Consumer)(var11));
								var12 = var3;
								final LivingEntity var12_f3 = var12;
								Consumer<CooldownComponent> var13 = (l0) -> {
			l0.stopCooldown(var12_f3);
		};
								((java.util.Optional)(var7.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var13));
								continue;
							}
						}else{
							break;
						}
					} while (true);
				}
				if ((var5_1) != (null)){
					var5_1.setHakiOveruse(0);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		this.shard.updateSkyBrightness();
	}
	public void wipeOldEntities(boolean var1) {
		try{
			ArrayList var2 = new ArrayList();
			Objects.requireNonNull(var2);
			this.shard.getAllEntities().forEach((Consumer)(((Consumer<Entity>)(var2::add))));
			Iterator var3 = var2.iterator();
			do{
				if (var3.hasNext()){
					Entity var4 = ((Entity)(var3.next()));
					if ((!(this.group.contains(var4))) && (!(this.enemies.contains(var4)))){
						var4.discard();
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}catch(Exception var2_2){
			String var2_1 = String.valueOf((Object)(this.core.getRegistryKey()));
			String var3_1 = "Something went wrong while wiping old entities from a challenge: " + var2_1;
			WyDebug.error(var3_1);
			var2_2.printStackTrace();
		}
	}
	public void resetChallengeBonus() {
		if (!(this.result.isLoss())){
			return;
		}else{
			ChallengeData var1 = ((ChallengeData)(((java.util.Optional)(ChallengeData.get((Player)(this.owner)))).orElse((Object)(null))));
			if ((var1) == (null)){
				return;
			}else{
				Challenge var2 = var1.getChallenge(this.getCore());
				if ((var2) == (null)){
					return;
				}else{
					var2.resetBonus();
					return;
				}
			}
		}
	}
	private void sendGroupTitle(Component var1, int var2, int var3, int var4) {
		this.sendGroupTitle(var1,(Component)(null),var2,var3,var4);
	}
	private void sendGroupTitle(Component var1, @Nullable() Component var2, int var3, int var4, int var5) {
		Iterator var6 = this.group.iterator();
		do{
			if (var6.hasNext()){
				LivingEntity var7 = ((LivingEntity)(var6.next()));
				if (var7 instanceof ServerPlayer){
					ServerPlayer var8 = ((ServerPlayer)(var7));
					try{
						var8.connection.send((Packet)(new ClientboundSetTitlesAnimationPacket(var3,var4,var5)));
						MutableComponent var9 = ComponentUtils.updateForEntity(var8.createCommandSourceStack(),var1,(Entity)(var8),0);
						var8.connection.send((Packet)(new ClientboundSetTitleTextPacket((Component)(var9))));
						if ((var2) != (null)){
							MutableComponent var10 = ComponentUtils.updateForEntity(var8.createCommandSourceStack(),var2,(Entity)(var8),0);
							var8.connection.send((Packet)(new ClientboundSetSubtitleTextPacket((Component)(var10))));
							continue;
						}else{
							continue;
						}
					}catch(Exception var9_1){
						var9_1.printStackTrace();
						continue;
					}
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	private void sendGroupActionbar(Component var1, int var2, int var3, int var4) {
		Iterator var5 = this.group.iterator();
		do{
			if (var5.hasNext()){
				LivingEntity var6 = ((LivingEntity)(var5.next()));
				if ((var6.isAlive()) && (var6 instanceof ServerPlayer)){
					ServerPlayer var7 = ((ServerPlayer)(var6));
					try{
						var7.connection.send((Packet)(new ClientboundSetTitlesAnimationPacket(var2,var3,var4)));
						MutableComponent var8 = ComponentUtils.updateForEntity(var7.createCommandSourceStack(),var1,(Entity)(var7),0);
						var7.connection.send((Packet)(new ClientboundSetActionBarTextPacket((Component)(var8))));
					}catch(Exception var8_1){
						var8_1.printStackTrace();
					}
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public boolean hasRewards() {
		return (this.isStandardDifficulty()) || (this.hasActiveRestrictions());
	}
	public boolean hasRestrictions() {
		return !(this.isStandardDifficulty());
	}
	public boolean hasActiveRestrictions() {
		return !(this.isFree);
	}
	public boolean canDelete() {
		return (this.phase) == (InProgressChallenge$Phase.POSTEND);
	}
	public boolean isBuilding() {
		return ((this.phase) == (InProgressChallenge$Phase.BUILD)) || ((this.phase) == (InProgressChallenge$Phase.POSTEND));
	}
	public boolean isStandardDifficulty() {
		return (this.core.getDifficulty()) == (ChallengeDifficulty.STANDARD);
	}
	public boolean isHardDifficulty() {
		return (this.core.getDifficulty()) == (ChallengeDifficulty.HARD);
	}
	public boolean isUltimateDifficulty() {
		return (this.core.getDifficulty()) == (ChallengeDifficulty.ULTIMATE);
	}
	public BlockPos getArenaPos() {
		return this.pos;
	}
	public UUID getId() {
		return this.id;
	}
	public ChallengeCore<?> getCore() {
		return (ChallengeCore<?>) (ChallengeCore) (this.core);
	}
	public List<LivingEntity> getGroup() {
		return this.group;
	}
	public HashMap<UUID, CompoundTag> getGroupCache() {
		return this.groupCache;
	}
	public Set<LivingEntity> getEnemies() {
		return this.enemies;
	}
	public ServerPlayer getOwner() {
		return this.owner;
	}
	public ServerLevel getShard() {
		return this.shard;
	}
	public ChallengeCore$IChallengerPosition getChallengerPosition() {
		return this.arenaEntry.challengerPosition();
	}
	public ChallengeArena getArena() {
		return this.arenaEntry.arena();
	}
	public Random getRNG() {
		return this.random;
	}
	public ResourceKey<Level> getReturnDimension() {
		return this.returnDimension;
	}
	public BlockPos getReturnPosition() {
		return this.returnPosition;
	}
	public void trackBlockPos(BlockPos var1) {
		this.trackedManuallyPlacedBlocks.add(var1);
	}
	public void trackBlockPos(Set<BlockPos> var1) {
		((java.util.Set)(this.trackedManuallyPlacedBlocks)).addAll((Collection)(var1));
	}
	static  {
		String[] var0 = new String[]{"o o o","O o o","o O o","o o O","o o o"};
		LOADING_FRAMES = var0;
		TagKey[] var1 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_AIR,ModTags$Blocks.BLOCK_PROT_RESTRICTED};
		REPAIR_RULE = new BlockProtectionRule$Builder(new BlockProtectionRule[0]).addBannedTags(var1).setEverythingFlag().build();
	}
}