package xyz.pixelatedw.mineminenomi.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.WantedPosterData;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.NPCWorldData;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.items.WantedPosterItem;

public class WantedPosterPackageEntity extends Entity {
	// Fields
	private int life;

	public WantedPosterPackageEntity(EntityType<? extends WantedPosterPackageEntity> var1, Level var2) {
		super(var1,var2);
		this.life = 6000;
	}
	public WantedPosterPackageEntity(Level var1) {
		this(((EntityType)(ModEntities.WANTED_POSTER_PACKAGE.get())),var1);
	}
	public boolean hurt(DamageSource var1, float var2) {
		if (this.isInvulnerableTo(var1)){
			return false;
		}else{
			if ((!(this.isRemoved())) && (!(this.level().isClientSide))){
				this.kill();
				this.markHurt();
			}
			return true;
		}
	}
	public boolean canCollideWith(Entity var1) {
		return true;
	}
	public boolean canBeCollidedWith() {
		return true;
	}
	public boolean isPushable() {
		return false;
	}
	public boolean isPickable() {
		return this.isAlive();
	}
	public void kill() {
		if (!(this.level().isClientSide)){
			FactionsWorldData var1 = FactionsWorldData.get();
			Vec3 var2 = new Vec3((this.position().x()) + (0.5D),(this.position().y()) - (0.5D),(this.position().z()) + (0.5D));
			this.spawnPlayerPosters(this.level(),var2,var1);
			this.spawnNotoriousPosters(this.level(),var2,var1);
			this.spawnChallengePosters(this.level(),var2);
		}
		super.kill();
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && ((this.life--) <= (0))){
			this.remove(Entity.RemovalReason.DISCARDED);
			return;
		}else{
			if (!(this.isNoGravity())){
				this.setDeltaMovement(this.getDeltaMovement().add(0D,-0.003D,0D));
			}
			this.move(MoverType.SELF,this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
			return;
		}
	}
	private void spawnNotoriousPosters(Level var1, Vec3 var2, FactionsWorldData var3) {
		NotoriousEntity var15_1 = null;
		int var4 = (1) + (var1.getRandom().nextInt(3));
		NPCWorldData var5 = NPCWorldData.get();
		EventsWorldData var6 = EventsWorldData.get();
		int var7 = 0;
		do{
			if ((var7) < (var4)){
				Optional var8 = var5.getRandomTrackedMob(((Faction)(ModFactions.PIRATE.get())));
				if ((var8.isPresent()) && (!(var6.hasNTEventFor(((TrackedNPC)(var8.get())))))){
					ItemStack var9 = new ItemStack(((ItemLike)(ModBlocks.WANTED_POSTER.get())));
					NotoriousEntity var10 = ((TrackedNPC)(var8.get())).createTrackedMob(var1);
					Vec3 var11 = null;
					int var12 = 0;
					do{
						if ((var12) < (5)){
							int var13 = (1000) + (var1.getRandom().nextInt(5000));
							if (var1.getRandom().nextBoolean()){
								var13 = (var13) * (-1);
							}
							int var14 = (1000) + (var1.getRandom().nextInt(5000));
							if (var1.getRandom().nextBoolean()){
								var14 = (var14) * (-1);
							}
							Vec3 var15 = var2.add((double)(var13),1D,(double)(var14));
							if (var1.getBiome(new BlockPos((int)(var15.x),(int)(var15.y),(int)(var15.z))).is(BiomeTags.IS_OCEAN)){
								var12++;
								continue;
							}else{
								var11 = var15;
								break;
							}
						}else{
							break;
						}
					} while (true);
					if ((var11) == (null)){
						var7++;
						continue;
					}else{
						var6.addNotoriousTarget(((ServerLevel)(var1)),var11,72000L,((TrackedNPC)(var8.get())));
						WantedPosterData var13_1 = WantedPosterData.create((LivingEntity)(var10),((TrackedNPC)(var8.get())).getBounty());
						WantedPosterData var14_1 = var13_1.setTrackedPosition(var11);
						var15_1 = var10;
						final NotoriousEntity var15_1_f1 = var15_1;
						Function<Faction, Boolean> var16 = (l0) -> {
			return Boolean.valueOf(l0.canReceiveBounty((LivingEntity)(var15_1_f1)));
		};
						if (((Boolean)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var10)).flatMap((Function)(((Function<EntityStatsData, Optional>)(EntityStatsData::getFaction)))))).map((Function)(var16)).orElse(Boolean.valueOf(false)))).booleanValue()){
							var3.issueBounty(((TrackedNPC)(var8.get())).getUUID(),((TrackedNPC)(var8.get())).getBounty());
						}
						var9.set(ModDataComponents.WANTED_POSTER_DATA,(Object)(var14_1));
						var1.addFreshEntity((Entity)(new ItemEntity(var1,var2.x,(var2.y) + (1D),var2.z,var9)));
						var7++;
						continue;
					}
				}else{
					var7++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	private void spawnChallengePosters(Level var1, Vec3 var2) {
		int var3 = var1.getRandom().nextInt(3);
		Predicate var4 = (Predicate) ((Predicate<ChallengeCore>) ((l0) -> {
			return (l0.getDifficulty()) == (ChallengeDifficulty.STANDARD);
		}));
		List var5 = ((List)(((java.util.stream.Stream)(WyRegistry.CHALLENGES.stream().filter(var4))).collect((Collector)(WyHelper.toShuffledList()))));
		if ((var5.size()) > (0)){
			int var6 = 0;
			do{
				if ((var6) < (var3)){
					ChallengeCore var7 = ((ChallengeCore)(var5.get(var1.getRandom().nextInt(var5.size()))));
					ItemStack var8 = new ItemStack(((ItemLike)(ModItems.CHALLENGE_POSTER.get())));
					var8.set(DataComponents.CUSTOM_NAME,(Object)(var7.getLocalizedTitle()));
					var8.set(ModDataComponents.CHALLENGE,(Object)(var7.getRegistryKey()));
					var1.addFreshEntity((Entity)(new ItemEntity(var1,var2.x,(var2.y) + (1D),var2.z,var8)));
					var6++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	private void spawnPlayerPosters(Level var1, Vec3 var2, FactionsWorldData var3) {
		Set var11 = null;
		HashSet var4 = new HashSet();
		if ((var3.getAllBounties().size()) > (0)){
			FactionsWorldData var5 = var3;
			final FactionsWorldData var5_f1 = var5;
			Predicate<Player> var6 = (l0) -> {
	Object lv4_3 = null;
			return ((l0) != (null)) && ((l0.isAlive()) && ((((Boolean)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(l0)).flatMap((Function)(((Function<EntityStatsData, Optional>)(EntityStatsData::getFaction)))))).map((Function)(lv4_3)).orElse(Boolean.valueOf(false)))).booleanValue()) && ((var5_f1.getBounty(l0.getUUID())) != (0L))));
		};
			Predicate<Player> var7 = var6;
			FactionsWorldData var8 = var3;
			Set var9 = var4;
			final FactionsWorldData var8_f2 = var8;
			final Set var9_f3 = var9;
			Consumer<Player> var10 = (l0) -> {
			ImmutablePair lv5_3 = ImmutablePair.of(l0.getUUID(),Long.valueOf(var8_f2.getBounty(l0.getUUID())));
			var9_f3.add(lv5_3);
		};
			((java.util.stream.Stream)(((java.util.stream.Stream)(TargetHelper.getNearbyPlayers((LevelAccessor)(var1),var2,10D,(Predicate)(null)).stream())).filter((Predicate)(var7)).limit(5L))).forEach((Consumer)(var10));
			if ((((java.util.Set)(var4)).size()) < (5)){
				var11 = var4;
				final Set var11_f1 = var11;
				Predicate var12 = (Predicate) ((Predicate<Map.Entry>) ((l0) -> {
			return !(var11_f1.contains(l0));
		}));
				Function var13 = (Function) ((Function<Map.Entry, ImmutablePair>) ((l0) -> {
			return ImmutablePair.of(((UUID)(l0.getKey())),((Long)(l0.getValue())));
		}));
				List var14 = ((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(var3.getAllBounties().entrySet().stream())).filter((Predicate)(var12)).map(var13))).collect((Collector)(Collectors.toList()))));
				Collections.shuffle(var14);
				((java.util.Set)(var4)).addAll((Collection)(var14.subList(0,Math.min(3,var14.size()))));
			}
			if (!(((java.util.Set)(var4)).isEmpty())){
				Iterator var11_1 = var4.iterator();
				do{
					if (var11_1.hasNext()){
						Pair var12_1 = ((Pair)(var11_1.next()));
						Player var13_1 = var1.getPlayerByUUID(((UUID)(var12_1.getKey())));
						if ((var13_1) != (null)){
							ItemStack var14_1 = WantedPosterItem.getWantedPosterStack(var13_1,((Long)(var12_1.getValue())).longValue());
							var1.addFreshEntity((Entity)(new ItemEntity(var1,var2.x,(var2.y) + (1D),var2.z,var14_1)));
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}else{
				return;
			}
		}
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
}