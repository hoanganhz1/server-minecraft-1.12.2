package xyz.pixelatedw.mineminenomi.data.world;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModFactions;

public class NPCWorldData extends SavedData {
	// Fields
	private static final String IDENTIFIER = "mineminenomi-npcs";
	private Random random;
	private Interval updateInterval;
	private Interval updateNewsInterval;
	private Set<TrackedNPC> tracked;
	private long trackedId;

	public NPCWorldData() {
		this.random = new Random();
		this.updateInterval = Interval.startAtMax(300);
		this.updateNewsInterval = Interval.startAtMax(24000);
		this.tracked = new LinkedHashSet();
		this.trackedId = 0L;
	}
	public static NPCWorldData get() {
		ServerLevel var0 = ServerLifecycleHooks.getCurrentServer().overworld();
		Supplier<NPCWorldData> var1 = NPCWorldData::new;
		return ((NPCWorldData)(var0.getDataStorage().computeIfAbsent(new SavedData.Factory(var1,(BiFunction)(((BiFunction<CompoundTag, HolderLookup.Provider, NPCWorldData>)(NPCWorldData::load)))),"mineminenomi-npcs")));
	}
	public static NPCWorldData load(CompoundTag var0, HolderLookup.Provider var1) {
		NPCWorldData var2 = new NPCWorldData();
		var2.trackedId = var0.getLong("globalTrackedId");
		ListTag var3 = var0.getList("trackedNPCs",10);
		int var4 = 0;
		do{
			if ((var4) < (var3.size())){
				CompoundTag var5 = var3.getCompound(var4);
				if (!(var5.isEmpty())){
					TrackedNPC var6 = TrackedNPC.from(var5);
					var2.tracked.add(var6);
				}
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public CompoundTag save(CompoundTag var1, HolderLookup.Provider var2) {
		var1.putLong("globalTrackedId",this.trackedId);
		ListTag var3 = new ListTag();
		Iterator var4 = this.tracked.iterator();
		do{
			if (var4.hasNext()){
				var3.add(((TrackedNPC)(var4.next())).save());
				continue;
			}else{
				break;
			}
		} while (true);
		var1.put("trackedNPCs",(Tag)(var3));
		return var1;
	}
	public void setup(ServerLevel var1) {
		int var6 = 0;
		Predicate<TrackedNPC> var2 = (l0) -> {
			return l0.getFaction().equals(ModFactions.PIRATE.get());
		};
		int var3 = (int)(this.tracked.stream().filter(var2).count());
		Predicate<TrackedNPC> var4 = (l0) -> {
			return l0.getFaction().equals(ModFactions.MARINE.get());
		};
		int var5 = (int)(this.tracked.stream().filter(var4).count());
		if ((var3) <= (0)){
			var6 = Math.abs(((5) + (var1.getRandom().nextInt(10))) - (var3));
			do{
				if ((var6) > (0)){
					this.addRandomTrackedMob(var1,((Faction)(ModFactions.PIRATE.get())));
					var6--;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		if ((var5) <= (0)){
			var6 = Math.abs(((5) + (var1.getRandom().nextInt(15))) - (var5));
			do{
				if ((var6) > (0)){
					this.addRandomTrackedMob(var1,((Faction)(ModFactions.MARINE.get())));
					var6--;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	public void tick(ServerLevel var1) {
		int var2 = 0;
		if (this.updateInterval.canTick()){
			Iterator var3 = this.tracked.iterator();
			do{
				if (var3.hasNext()){
					TrackedNPC var4 = ((TrackedNPC)(var3.next()));
					var4.recalculateDifficulty(var1);
					var2++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		if (this.updateNewsInterval.canTick()){
			Iterator var3_1 = this.tracked.iterator();
			do{
				if (var3_1.hasNext()){
					TrackedNPC var4_1 = ((TrackedNPC)(var3_1.next()));
					var4_1.updateNewsEntry((Level)(var1));
					var2++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		if ((var2) > (0)){
			this.setDirty();
		}
	}
	public Set<TrackedNPC> getTrackedMobs() {
		return new LinkedHashSet((Collection)(this.tracked));
	}
	public void updateTrackedMob(ServerLevel var1, TrackedNPC var2) {
		var2.recalculateDifficulty(var1);
		this.setDirty();
	}
	private void addRandomTrackedMob(ServerLevel var1, Faction var2) {
		this.addTrackedMob(var1,var2,var1.getRandom().nextLong());
	}
	private void addTrackedMob(ServerLevel var1, Faction var2, long var3) {
		TrackedNPC var4 = new TrackedNPC(var1,this.trackedId++,var2,var3);
		this.tracked.add(var4);
		this.setDirty();
	}
	public void removeTrackedMob(ServerLevel var1, TrackedNPC var2) {
		if ((var2) == (null)){
			return;
		}else{
			this.tracked.remove(var2);
			TrackedNPC var3 = var2;
			final TrackedNPC var3_f1 = var3;
			Predicate<TrackedNPC> var4 = (l0) -> {
			return l0.getFaction().equals(var3_f1.getFaction());
		};
			if ((this.tracked.stream().filter(var4).count()) < (5L)){
				this.addRandomTrackedMob(var1,var2.getFaction());
			}
			this.setDirty();
			return;
		}
	}
	public Optional<TrackedNPC> getTrackedMob(long var1) {
		long var2 = var1;
		final long var2_f1 = var2;
		Predicate<TrackedNPC> var3 = (l0) -> {
			return (l0.getId()) == (var2_f1);
		};
		return (Optional<TrackedNPC>) (Optional) (((java.util.stream.Stream)(this.tracked.stream())).filter((Predicate)(var3)).findFirst());
	}
	public Optional<TrackedNPC> getTrackedMobBySeed(long var1) {
		long var2 = var1;
		final long var2_f1 = var2;
		Predicate<TrackedNPC> var3 = (l0) -> {
			return (l0.getSeed()) == (var2_f1);
		};
		return (Optional<TrackedNPC>) (Optional) (((java.util.stream.Stream)(this.tracked.stream())).filter((Predicate)(var3)).findFirst());
	}
	public Optional<TrackedNPC> getRandomTrackedMob(Faction var1) {
		Faction var2 = var1;
		final Faction var2_f1 = var2;
		Predicate<TrackedNPC> var3 = (l0) -> {
			return l0.getFaction().equals(var2_f1);
		};
		return (Optional<TrackedNPC>) (Optional) (((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.tracked.stream())).filter((Predicate)(var3)))).collect((Collector)(WyHelper.toShuffledList())))).stream().findAny());
	}
}