package xyz.pixelatedw.mineminenomi.data.world;

import com.google.common.collect.Iterators;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.StructuresHelper;
import xyz.pixelatedw.mineminenomi.api.poi.CountdownEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.NTEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.POIEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.POIEventTarget$EventTriggerAction;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.mobs.CaptainEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.GruntEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModLootTables;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class EventsWorldData extends SavedData {
	// Fields
	private static final String IDENTIFIER = "mineminenomi-events";
	private Set<NTEventTarget> notoriousTarget;
	private Set<POIEventTarget> caravans;
	private Set<CountdownEventTarget> busterCalls;
	private Set<POIEventTarget> celestialVisits;
	private int tick;

	public EventsWorldData() {
		this.notoriousTarget = new HashSet();
		this.caravans = new HashSet();
		this.busterCalls = new HashSet();
		this.celestialVisits = new HashSet();
		this.tick = 0;
	}
	public static EventsWorldData get() {
		ServerLevel var0 = ServerLifecycleHooks.getCurrentServer().overworld();
		Supplier<EventsWorldData> var1 = EventsWorldData::new;
		return ((EventsWorldData)(var0.getDataStorage().computeIfAbsent(new SavedData.Factory(var1,(BiFunction)(((BiFunction<CompoundTag, HolderLookup.Provider, EventsWorldData>)(EventsWorldData::load)))),"mineminenomi-events")));
	}
	public static EventsWorldData load(CompoundTag var0, HolderLookup.Provider var1) {
		EventsWorldData var2 = new EventsWorldData();
		ListTag var3 = var0.getList("notoriousTargets",10);
		int var4 = 0;
		do{
			if ((var4) < (var3.size())){
				CompoundTag var5 = var3.getCompound(var4);
				if (!(var5.isEmpty())){
					NTEventTarget var6 = new NTEventTarget();
					var6.load(var5);
					var2.notoriousTarget.add(var6);
				}
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		ListTag var5_1 = var0.getList("caravans",10);
		int var6_1 = 0;
		do{
			if ((var6_1) < (var5_1.size())){
				CompoundTag var7 = var5_1.getCompound(var6_1);
				if (!(var7.isEmpty())){
					POIEventTarget var8 = new POIEventTarget();
					var2.setupCaravan(var8);
					var8.load(var7);
					var2.caravans.add(var8);
				}
				var6_1++;
				continue;
			}else{
				break;
			}
		} while (true);
		ListTag var7_1 = var0.getList("busterCalls",10);
		int var8_1 = 0;
		do{
			if ((var8_1) < (var7_1.size())){
				CompoundTag var9 = var7_1.getCompound(var8_1);
				if (!(var9.isEmpty())){
					CountdownEventTarget var10 = new CountdownEventTarget();
					var2.setupSpecialBusterCall(var10,var10.getTargetId());
					var10.load(var9);
					var2.busterCalls.add(var10);
				}
				var8_1++;
				continue;
			}else{
				break;
			}
		} while (true);
		ListTag var9_1 = var0.getList("celestialVisits",10);
		int var10_1 = 0;
		do{
			if ((var10_1) < (var9_1.size())){
				CompoundTag var11 = var9_1.getCompound(var10_1);
				if (!(var11.isEmpty())){
					POIEventTarget var12 = new POIEventTarget();
					var2.setupCelestialVisit(var12);
					var12.load(var11);
					var2.celestialVisits.add(var12);
				}
				var10_1++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public CompoundTag save(CompoundTag var1, HolderLookup.Provider var2) {
		ListTag var3 = new ListTag();
		Iterator var4 = this.notoriousTarget.iterator();
		do{
			if (var4.hasNext()){
				var3.add(((POIEventTarget)(var4.next())).save());
				continue;
			}else{
				break;
			}
		} while (true);
		var1.put("notoriousTargets",(Tag)(var3));
		ListTag var5 = new ListTag();
		Iterator var6 = this.caravans.iterator();
		do{
			if (var6.hasNext()){
				var5.add(((POIEventTarget)(var6.next())).save());
				continue;
			}else{
				break;
			}
		} while (true);
		var1.put("caravans",(Tag)(var5));
		ListTag var7 = new ListTag();
		Iterator var8 = this.busterCalls.iterator();
		do{
			if (var8.hasNext()){
				var7.add(((CountdownEventTarget)(var8.next())).save());
				continue;
			}else{
				break;
			}
		} while (true);
		var1.put("busterCalls",(Tag)(var7));
		ListTag var9 = new ListTag();
		Iterator var10 = this.celestialVisits.iterator();
		do{
			if (var10.hasNext()){
				var9.add(((POIEventTarget)(var10.next())).save());
				continue;
			}else{
				break;
			}
		} while (true);
		var1.put("celestialVisits",(Tag)(var9));
		return var1;
	}
	public void tick(ServerLevel var1) {
		this.tick = (this.tick) + (1);
		this.tickEvents(var1);
		this.startNewEvents(var1);
	}
	public void startNewEvents(ServerLevel var1) {
		if (((this.tick) % (300)) == (0)){
			int var3 = 0;
			List var2 = var1.players();
			Collections.shuffle(var2);
			if (ServerConfig.canSpawnCelestialVisits()){
				var3 = 0;
				if (((((var1.random.nextInt(3)) == (0)) ? (1) : (0)) != (0)) && ((this.celestialVisits.size()) < (10))){
					Iterator var4 = var2.iterator();
					do{
						if (var4.hasNext()){
							ServerPlayer var5 = ((ServerPlayer)(var4.next()));
							if ((var3) > (1)){
								break;
							}else{
								Optional var6 = this.findEventPositionAround(var1,var5.position(),100,1000,false);
								if ((var6.isPresent()) && ((this.canSpawnCVPoint(((Vec3)(var6.get())))) && (this.addCelestialVisit(var1,((Vec3)(var6.get())))))){
									var3++;
									continue;
								}else{
									continue;
								}
							}
						}else{
							break;
						}
					} while (true);
				}
			}
			if (ServerConfig.canSpawnCaravans()){
				var3 = 0;
				if ((this.caravans.size()) < (25)){
					Iterator var4_1 = var2.iterator();
					do{
						if (var4_1.hasNext()){
							ServerPlayer var5_1 = ((ServerPlayer)(var4_1.next()));
							if ((var3) > (3)){
								break;
							}else{
								EntityStatsData var6_1 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var5_1)))).orElse((Object)(null))));
								if (((var6_1) != (null)) && ((!(var6_1.isMarine())) && (!(var6_1.isBountyHunter())))){
									Optional var7 = this.findEventPositionAround(var1,var5_1.position(),1000,5000,false);
									if ((var7.isPresent()) && (this.addCaravan(var1,((Vec3)(var7.get())),72000L))){
										var3++;
										continue;
									}else{
										continue;
									}
								}else{
									continue;
								}
							}
						}else{
							break;
						}
					} while (true);
				}
			}
			if (ServerConfig.canSpawnNotoriousTargets()){
				NPCWorldData var3_1 = NPCWorldData.get();
				int var4_2 = 0;
				if ((this.notoriousTarget.size()) < (5)){
					Iterator var5_2 = var2.iterator();
					do{
						if (var5_2.hasNext()){
							ServerPlayer var6_2 = ((ServerPlayer)(var5_2.next()));
							if ((var4_2) > (2)){
								break;
							}else{
								Optional var7_1 = this.findEventPositionAround(var1,var6_2.position(),1000,5000,false);
								if (var7_1.isPresent()){
									Optional var8 = var3_1.getRandomTrackedMob((var1.getRandom().nextBoolean()) ? (((Faction)(ModFactions.MARINE.get()))) : (((Faction)(ModFactions.PIRATE.get()))));
									if ((var8.isPresent()) && (!(this.hasNTEventFor(((TrackedNPC)(var8.get())))))){
										this.addNotoriousTarget(var1,((Vec3)(var7_1.get())),72000L,((TrackedNPC)(var8.get())));
										var4_2++;
										continue;
									}else{
										continue;
									}
								}else{
									continue;
								}
							}
						}else{
							break;
						}
					} while (true);
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	public void tickEvents(ServerLevel var1) {
		int var2 = 0;
		Iterator var3 = this.notoriousTarget.iterator();
		do{
			if (var3.hasNext()){
				if ((((NTEventTarget)(var3.next())).getTrackedNPC()) == (null)){
					var3.remove();
					var2 = 1;
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		Iterator[] var4 = new Iterator[]{this.busterCalls.iterator()};
		Iterator var5 = Iterators.concat(var4);
		if (ServerConfig.canSpawnNotoriousTargets()){
			var5 = Iterators.concat(var5,this.notoriousTarget.iterator());
		}
		if (ServerConfig.canSpawnCelestialVisits()){
			var5 = Iterators.concat(var5,this.celestialVisits.iterator());
		}
		if (ServerConfig.canSpawnCaravans()){
			var5 = Iterators.concat(var5,this.caravans.iterator());
		}
		LOOP_1:
		do{
			if (var5.hasNext()){
				POIEventTarget var6 = ((POIEventTarget)(var5.next()));
				if (var6.hasExpired(var1)){
					var5.remove();
					var2 = 1;
					continue;
				}else{
					Iterator var7 = var1.players().iterator();
					do{
						if (var7.hasNext()){
							if (var6.shouldTrigger(((ServerPlayer)(var7.next())))){
								var6.getTriggerAction().trigger(var1,var6);
								var5.remove();
								var2 = 1;
								return;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					var6.tick();
					continue LOOP_1;
				}
			}else{
				break;
			}
		} while (true);
		if ((var2) != (0)){
			this.setDirty();
		}
	}
	public Optional<Vec3> findEventPositionAround(ServerLevel var1, Vec3 var2, int var3, int var4, boolean var5) {
		Optional var6 = Optional.empty();
		int var7 = 0;
		do{
			if ((var7) < (5)){
				int var8 = (var3) + (var1.getRandom().nextInt(var4));
				if (var1.getRandom().nextBoolean()){
					var8 = (var8) * (-1);
				}
				int var9 = (var3) + (var1.getRandom().nextInt(var4));
				if (var1.getRandom().nextBoolean()){
					var9 = (var9) * (-1);
				}
				Vec3 var10 = var2.add((double)(var8),1D,(double)(var9));
				BlockPos var11 = new BlockPos((int)(var10.x),(int)(var10.y),(int)(var10.z));
				if ((!(var5)) && (var1.getBiome(var11).is(BiomeTags.IS_OCEAN))){
					var7++;
					continue;
				}else{
					var6 = Optional.of(var10);
					break;
				}
			}else{
				break;
			}
		} while (true);
		return var6;
	}
	public boolean addCelestialVisit(ServerLevel var1, Vec3 var2) {
		POIEventTarget var3 = new POIEventTarget(var1,var2,72000L);
		this.setupCelestialVisit(var3);
		this.celestialVisits.add(var3);
		this.setDirty();
		return true;
	}
	private void setupCelestialVisit(POIEventTarget var1) {
		POIEventTarget$EventTriggerAction var2 = (l0, l1) -> {
			int lv1_2 = l0.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,(int)(l1.getPosition().x()),(int)(l1.getPosition().z()));
			BlockPos lv1_3 = new BlockPos((int)(l1.getPosition().x()),lv1_2,(int)(l1.getPosition().z()));
			EntityType lv1_4 = ((EntityType)(ModMobs.CELESTIAL_DRAGON.get()));
			EntityType lv1_5 = ((EntityType)(ModMobs.MARINE_GRUNT.get()));
			lv1_4.spawn(l0,(ItemStack)(null),(Player)(null),lv1_3,MobSpawnType.EVENT,false,false);
			int lv1_6 = 0;
			do{
				if ((lv1_6) < (6)){
					lv1_5.spawn(l0,(ItemStack)(null),(Player)(null),lv1_3,MobSpawnType.EVENT,false,false);
					lv1_6++;
					continue;
				}else{
					break;
				}
			} while (true);
		};
		var1.setTriggerAction(var2);
	}
	private boolean canSpawnCVPoint(Vec3 var1) {
		Iterator var2 = this.celestialVisits.iterator();
		do{
			if (var2.hasNext()){
				if ((Math.abs(((POIEventTarget)(var2.next())).getPosition().distanceToSqr(var1))) < (22500D)){
					return false;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return true;
	}
	public Set<POIEventTarget> getCelestialVisitsPOIs() {
		return new LinkedHashSet((Collection)(this.celestialVisits));
	}
	public void addSpecialBusterCall(ServerLevel var1, LivingEntity var2) {
		CountdownEventTarget var3 = new CountdownEventTarget(var1,var2,72000L,10);
		this.setupSpecialBusterCall(var3,var2.getUUID());
		this.busterCalls.add(var3);
		this.setDirty();
	}
	private void setupSpecialBusterCall(CountdownEventTarget var1, UUID var2) {
		CountdownEventTarget var3 = var1;
		final CountdownEventTarget var3_f1 = var3;
		POIEventTarget$EventTriggerAction var4 = (l0, l1) -> {
			Vec3 lv2_3 = l1.getPosition();
			BlockPos lv2_4 = new BlockPos((int)(lv2_3.x()),(int)(lv2_3.y()),(int)(lv2_3.z()));
			if (((!(l0.getBiome(lv2_4).is(BiomeTags.IS_OCEAN))) ? (1) : (0)) == (0)){
				return;
			}else{
				Optional lv2_5 = NPCWorldData.get().getRandomTrackedMob(((Faction)(ModFactions.MARINE.get())));
				EntityType lv2_6 = ((EntityType)(ModMobs.MARINE_CAPTAIN.get()));
				EntityType lv2_7 = ((EntityType)(ModMobs.PACIFISTA.get()));
				EntityType lv2_8 = ((EntityType)(ModMobs.MARINE_GRUNT.get()));
				int lv2_9 = ((l0.random.nextInt(3)) == (0)) ? (1) : (0);
				LivingEntity lv2_10 = ((LivingEntity)(((java.util.Optional)(var3_f1.getTarget(l0))).orElse((Object)(null))));
				BlockPos lv2_11 = WyHelper.findOnGroundSpawnLocation(l0,lv2_6,lv2_4,10);
				if ((lv2_11) == (null)){
					int lv2_12 = l0.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,(int)(l1.getPosition().x()),(int)(l1.getPosition().z()));
					lv2_11 = new BlockPos((int)(l1.getPosition().x()),lv2_12,(int)(l1.getPosition().z()));
				}
				if (lv2_5.isPresent()){
					Vec3 lv2_12_1 = new Vec3((double)(lv2_11.getX()),(double)(lv2_11.getY()),(double)(lv2_11.getZ()));
					NotoriousEntity lv2_13 = ((TrackedNPC)(lv2_5.get())).spawnTrackedMob(l0,lv2_12_1);
					if ((lv2_13) != (null)){
						lv2_13.setTarget(lv2_10);
					}
				}
				int lv2_12_2 = 0;
				do{
					if ((lv2_12_2) < (4)){
						BlockPos lv2_13_1 = WyHelper.findOnGroundSpawnLocation(l0,lv2_8,lv2_4,20);
						if ((lv2_13_1) != (null)){
							if ((lv2_9) != (0)){
								PacifistaEntity lv2_14 = ((PacifistaEntity)(lv2_7.spawn(l0,((ItemStack)(null)),((Player)(null)),lv2_13_1,MobSpawnType.EVENT,false,false)));
								if ((lv2_14) != (null)){
									lv2_14.setTarget(lv2_10);
								}else{
									lv2_12_2++;
									continue;
								}
							}else{
								CaptainEntity lv2_14_1 = ((CaptainEntity)(lv2_6.spawn(l0,((ItemStack)(null)),((Player)(null)),lv2_13_1,MobSpawnType.EVENT,false,false)));
								if ((lv2_14_1) != (null)){
									lv2_14_1.setTarget(lv2_10);
								}else{
									lv2_12_2++;
									continue;
								}
							}
							lv2_12_2++;
							continue;
						}else{
							lv2_12_2++;
							continue;
						}
					}else{
						break;
					}
				} while (true);
				lv2_12_2 = 0;
				do{
					if ((lv2_12_2) < (20)){
						BlockPos lv2_13_2 = WyHelper.findOnGroundSpawnLocation(l0,lv2_8,lv2_4,20);
						if ((lv2_13_2) != (null)){
							GruntEntity lv2_14_2 = ((GruntEntity)(lv2_8.spawn(l0,((ItemStack)(null)),((Player)(null)),lv2_13_2,MobSpawnType.EVENT,false,false)));
							if ((lv2_14_2) != (null)){
								MobsHelper.removeFearGoals((Mob)(lv2_14_2));
								lv2_14_2.setTarget(lv2_10);
								lv2_12_2++;
								continue;
							}else{
								lv2_12_2++;
								continue;
							}
						}else{
							lv2_12_2++;
							continue;
						}
					}else{
						break;
					}
				} while (true);
				l0.playSound((Player)(null),lv2_4,SoundEvents.BELL_BLOCK,SoundSource.HOSTILE,1F,1F);
				l0.playSound((Player)(null),lv2_4,((SoundEvent)(ModSounds.GENERIC_EXPLOSION.get())),SoundSource.HOSTILE,1F,1F);
				return;
			}
		};
		var1.setTriggerAction(var4);
	}
	public boolean addCaravan(ServerLevel var1, Vec3 var2, long var3) {
		BlockPos var4 = new BlockPos((int)(var2.x()),(int)(var2.y()),(int)(var2.z()));
		if ((!(var1.getBiome(var4).is(BiomeTags.HAS_VILLAGE_PLAINS))) && (!(var1.getBiome(var4).is(BiomeTags.IS_FOREST)))){
			return false;
		}else{
			Iterator var5 = this.caravans.iterator();
			do{
				if (var5.hasNext()){
					if ((((POIEventTarget)(var5.next())).getPosition().distanceToSqr(var2)) < (2500D)){
						return false;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			POIEventTarget var6 = new POIEventTarget(var1,var2,var3);
			this.setupCaravan(var6);
			this.caravans.add(var6);
			this.setDirty();
			return true;
		}
	}
	private void setupCaravan(POIEventTarget var1) {
		POIEventTarget$EventTriggerAction var2 = (l0, l1) -> {
			int lv3_2 = l0.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,(int)(l1.getPosition().x()),(int)(l1.getPosition().z()));
			BlockPos lv3_3 = new BlockPos((int)(l1.getPosition().x()),lv3_2,(int)(l1.getPosition().z()));
			BlockPos lv3_4 = lv3_3;
			int lv3_5 = (2) + (l0.getRandom().nextInt(5));
			int lv3_6 = ((l0.getRandom().nextInt(3)) == (0)) ? (1) : (0);
			boolean lv3_7 = false;
			if ((lv3_6) != (0)){
				lv3_5 = (lv3_5) + (5);
				if ((l0.getRandom().nextInt(3)) == (0)){
					lv3_7 = true;
				}
			}
			Optional lv3_8 = NPCWorldData.get().getRandomTrackedMob(((Faction)(ModFactions.MARINE.get())));
			if (!(lv3_8.isPresent())){
				lv3_6 = 0;
			}
			StructurePlaceSettings lv3_9 = new StructurePlaceSettings().setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(false);
			lv3_9.clearProcessors().addProcessor((StructureProcessor)(BlockIgnoreProcessor.STRUCTURE_AND_AIR)).setRandom(RandomSource.create(Util.getMillis()));
			Optional lv3_10 = StructuresHelper.getStructure(l0,ResourceLocation.fromNamespaceAndPath("mineminenomi","unaligned/caravan"));
			if (lv3_10.isPresent()){
				BlockPos lv3_11 = lv3_3.below();
				((StructureTemplate)(lv3_10.get())).placeInWorld((ServerLevelAccessor)(l0),lv3_11,lv3_11,lv3_9,RandomSource.create(Util.getMillis()),2);
				Iterator lv3_12 = ((StructureTemplate)(lv3_10.get())).filterBlocks(lv3_11,lv3_9,Blocks.STRUCTURE_BLOCK).iterator();
				do{
					if (lv3_12.hasNext()){
						StructureTemplate.StructureBlockInfo lv3_13 = ((StructureTemplate.StructureBlockInfo)(lv3_12.next()));
						if ((lv3_13.nbt()) != (null)){
							BlockPos lv3_14 = lv3_13.pos();
							if ((StructureMode.valueOf(lv3_13.nbt().getString("mode"))) == (StructureMode.DATA)){
								String lv3_15 = lv3_13.nbt().getString("metadata");
								if (lv3_15.equals("caravan_loot")){
									StructuresHelper.spawnLoot((LevelAccessor)(l0),lv3_14,((lv3_6) != (0)) ? (ModLootTables.CARAVAN_HARD_CHEST) : (ModLootTables.CARAVAN_EASY_CHEST));
									continue;
								}else{
									if (lv3_15.equals("caravan_spawn")){
										lv3_4 = lv3_14;
										continue;
									}else{
										continue;
									}
								}
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
			}
			int lv3_11_1 = 0;
			do{
				if ((lv3_11_1) < (lv3_5)){
					GruntEntity lv3_12_1 = ((GruntEntity)(((EntityType)(ModMobs.MARINE_GRUNT.get())).spawn(l0,((ItemStack)(null)),((Player)(null)),lv3_4,MobSpawnType.EVENT,false,false)));
					if ((lv3_12_1) != (null)){
						MobsHelper.removeFearGoals((Mob)(lv3_12_1));
					}
					lv3_11_1++;
					continue;
				}else{
					break;
				}
			} while (true);
			if ((lv3_6) != (0)){
				NotoriousEntity lv3_12_2 = ((TrackedNPC)(lv3_8.get())).createTrackedMob((Level)(l0));
				lv3_12_2.setPos((double)(lv3_4.getX()),(double)(lv3_4.getY()),(double)(lv3_4.getZ()));
				l0.addFreshEntity((Entity)(lv3_12_2));
			}else{
				CaptainEntity lv3_12_3 = ((CaptainEntity)(((EntityType)(ModMobs.MARINE_CAPTAIN.get())).spawn(l0,((ItemStack)(null)),((Player)(null)),lv3_4,MobSpawnType.EVENT,false,false)));
				if ((lv3_12_3) != (null)){
					MobsHelper.removeFearGoals((Mob)(lv3_12_3));
				}
			}
			if (lv3_7){
				PacifistaEntity lv3_12_4 = ((PacifistaEntity)(((EntityType)(ModMobs.PACIFISTA.get())).spawn(l0,((ItemStack)(null)),((Player)(null)),lv3_4,MobSpawnType.EVENT,false,false)));
				if ((lv3_12_4) != (null)){
					MobsHelper.removeFearGoals((Mob)(lv3_12_4));
				}
			}
			int lv3_12_5 = 0;
			do{
				if ((lv3_12_5) < (3)){
					EntityType.VILLAGER.spawn(l0,((ItemStack)(null)),((Player)(null)),lv3_4,MobSpawnType.EVENT,false,false);
					lv3_12_5++;
					continue;
				}else{
					break;
				}
			} while (true);
		};
		var1.setTriggerAction(var2);
	}
	public Set<POIEventTarget> getCaravanPOIs() {
		return new LinkedHashSet((Collection)(this.caravans));
	}
	public void addNotoriousTarget(ServerLevel var1, Vec3 var2, long var3, TrackedNPC var4) {
		NPCWorldData.get().updateTrackedMob(var1,var4);
		NTEventTarget var5 = new NTEventTarget(var1,var2,var3,var4);
		this.notoriousTarget.add(var5);
		this.setDirty();
	}
	public Set<NTEventTarget> getNotoriousTargets() {
		return new LinkedHashSet((Collection)(this.notoriousTarget));
	}
	public boolean hasNTEventAt(Vec3 var1) {
		return this.notoriousTarget.stream().anyMatch((Predicate)(((Predicate<NTEventTarget>)((l0) -> {
			return l0.getPosition().equals(var1);
		}))));
	}
	public boolean hasNTEventFor(TrackedNPC var1) {
		return this.notoriousTarget.stream().anyMatch((Predicate)(((Predicate<NTEventTarget>)((l0) -> {
			return l0.getTrackedNPC().equals(var1);
		}))));
	}
	public boolean hasNTEventFor(UUID var1) {
		Predicate<NTEventTarget> var2 = (l0) -> {
			return (l0.getTrackedNPC()) != (null);
		};
		return this.notoriousTarget.stream().filter(var2).anyMatch((Predicate)(((Predicate<NTEventTarget>)((l0) -> {
			return l0.getTrackedNPC().getUUID().equals(var1);
		}))));
	}
	public void wipeAllEvents() {
		this.notoriousTarget.clear();
		this.caravans.clear();
		this.busterCalls.clear();
		this.celestialVisits.clear();
	}
}