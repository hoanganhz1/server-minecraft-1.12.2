package xyz.pixelatedw.mineminenomi.data.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class UnloadFruitTimeStampsData extends SavedData {
	// Fields
	private static final String IDENTIFIER = "mineminenomi-unloaded-chunk-times";
	private Map<String, Long> unloadTimes;

	public UnloadFruitTimeStampsData() {
		this.unloadTimes = new HashMap();
	}
	public static UnloadFruitTimeStampsData get() {
		Supplier<UnloadFruitTimeStampsData> var0 = UnloadFruitTimeStampsData::new;
		return ((UnloadFruitTimeStampsData)(ServerLifecycleHooks.getCurrentServer().overworld().getDataStorage().computeIfAbsent(new SavedData.Factory(var0,(BiFunction)(((BiFunction<CompoundTag, HolderLookup.Provider, UnloadFruitTimeStampsData>)(UnloadFruitTimeStampsData::load)))),"mineminenomi-unloaded-chunk-times")));
	}
	public void addUnloadTime(ResourceLocation var1, long var2) {
		this.unloadTimes.put(var1.toString(),Long.valueOf(var2));
		this.setDirty();
	}
	public void removeUnloadTime(ResourceLocation var1) {
		this.unloadTimes.remove(var1.toString());
		this.setDirty();
	}
	public long getUnloadTime(ResourceLocation var1) {
		return ((Long)(this.unloadTimes.get(var1.toString()))).longValue();
	}
	public Map<String, Long> getUnloadTimes() {
		return this.unloadTimes;
	}
	public CompoundTag save(CompoundTag var1, HolderLookup.Provider var2) {
		CompoundTag var3 = new CompoundTag();
		Objects.requireNonNull(var3);
		this.unloadTimes.forEach((BiConsumer)(((BiConsumer<String, Long>)(var3::putLong))));
		var1.put("unloadTimeMap",(Tag)(var3));
		return var1;
	}
	public static UnloadFruitTimeStampsData load(CompoundTag var0, HolderLookup.Provider var1) {
		UnloadFruitTimeStampsData var2 = new UnloadFruitTimeStampsData();
		CompoundTag var3 = var0.getCompound("unloadTimeMap");
		UnloadFruitTimeStampsData var4 = var2;
		CompoundTag var5 = var3;
		final UnloadFruitTimeStampsData var4_f1 = var4;
		final CompoundTag var5_f2 = var5;
		Consumer<String> var6 = (l0) -> {
			var4_f1.unloadTimes.put(l0,Long.valueOf(var5_f2.getLong(l0)));
		};
		((java.lang.Iterable)(var3.getAllKeys())).forEach((Consumer)(var6));
		return var2;
	}
}