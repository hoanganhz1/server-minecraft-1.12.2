package xyz.pixelatedw.mineminenomi.data.world;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import xyz.pixelatedw.mineminenomi.api.enums.SoulboundMark;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class ExtendedWorldData extends SavedData {
	// Fields
	private static final String IDENTIFIER = "mineminenomi";
	private HashMap<UUID, SoulboundMark> linkedItemStatus;
	private boolean newWorld;

	public ExtendedWorldData() {
		this.linkedItemStatus = (HashMap) (new HashMap());
		this.newWorld = false;
	}
	public static ExtendedWorldData get() {
		ServerLevel var0 = ServerLifecycleHooks.getCurrentServer().overworld();
		Supplier<ExtendedWorldData> var1 = ExtendedWorldData::new;
		return ((ExtendedWorldData)(var0.getDataStorage().computeIfAbsent(new SavedData.Factory(var1,(BiFunction)(((BiFunction<CompoundTag, HolderLookup.Provider, ExtendedWorldData>)(ExtendedWorldData::load)))),"mineminenomi")));
	}
	public static ExtendedWorldData load(CompoundTag var0, HolderLookup.Provider var1) {
		ExtendedWorldData var2 = new ExtendedWorldData();
		CompoundTag var3 = var0.getCompound("linkedItemMarks");
		var2.linkedItemStatus.clear();
		CompoundTag var4 = var3;
		ExtendedWorldData var5 = var2;
		final CompoundTag var4_f1 = var4;
		final ExtendedWorldData var5_f2 = var5;
		Consumer<String> var6 = (l0) -> {
			SoulboundMark lv1_3 = SoulboundMark.values()[var4_f1.getInt(l0)];
			var5_f2.linkedItemStatus.put(UUID.fromString(l0),lv1_3);
		};
		((java.util.stream.Stream)(var3.getAllKeys().stream())).forEach((Consumer)(var6));
		var2.newWorld = var0.getBoolean("newWorld");
		return var2;
	}
	public CompoundTag save(CompoundTag var1, HolderLookup.Provider var2) {
		CompoundTag var3 = new CompoundTag();
		if ((this.linkedItemStatus.size()) > (0)){
			Iterator var4 = this.linkedItemStatus.entrySet().iterator();
			do{
				if (var4.hasNext()){
					Map.Entry var5 = ((Map.Entry)(var4.next()));
					var3.putInt(((UUID)(var5.getKey())).toString(),((SoulboundMark)(var5.getValue())).ordinal());
					continue;
				}else{
					break;
				}
			} while (true);
		}
		var1.put("linkedItemMarks",(Tag)(var3));
		var1.putBoolean("newWorld",this.newWorld);
		return var1;
	}
	public boolean isNewWorldActive() {
		return this.isNewWorldActive(BlockPos.ZERO);
	}
	public boolean isNewWorldActive(BlockPos var1) {
		if (!(ServerConfig.isNewWorldModeEnabled())){
			return false;
		}else{
			if ((Math.sqrt(var1.distSqr((Vec3i)(BlockPos.ZERO)))) < (ServerConfig.getNewWorldStartRadius())){
				return false;
			}else{
				return this.newWorld;
			}
		}
	}
	public void triggerNewWorld() {
		if ((ServerConfig.isNewWorldModeEnabled()) && (!(this.newWorld))){
			this.newWorld = true;
			this.setDirty();
			return;
		}else{
			return;
		}
	}
	public void resetNewWorld() {
		this.newWorld = false;
		this.setDirty();
	}
	public void markDead(UUID var1) {
		this.linkedItemStatus.put(var1,SoulboundMark.DEATH);
		this.setDirty();
	}
	public void markRestore(UUID var1, SoulboundMark var2) {
		this.linkedItemStatus.put(var1,var2);
		this.setDirty();
	}
	public void removeMark(UUID var1) {
		this.linkedItemStatus.remove(var1);
		this.setDirty();
	}
	public boolean isOnDeathList(UUID var1) {
		return (this.linkedItemStatus.get(var1)) == (SoulboundMark.DEATH);
	}
	public boolean isOnRestoreList(UUID var1, SoulboundMark var2) {
		return (this.linkedItemStatus.get(var1)) == (var2);
	}
}