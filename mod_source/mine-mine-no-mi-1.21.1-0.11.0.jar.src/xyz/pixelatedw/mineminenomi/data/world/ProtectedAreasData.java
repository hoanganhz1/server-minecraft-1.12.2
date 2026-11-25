package xyz.pixelatedw.mineminenomi.data.world;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;

public class ProtectedAreasData extends SavedData {
	// Fields
	private static final String IDENTIFIER = "mineminenomi-protected-areas";
	private HashMap<String, ProtectedArea> abilityProtections;
	private final ServerLevel level;

	private ProtectedAreasData(ServerLevel var1) {
		this.abilityProtections = (HashMap) (new HashMap());
		this.level = var1;
	}
	public static ProtectedAreasData get(ServerLevel var0) {
		ServerLevel var1 = var0;
		final ServerLevel var1_f1 = var1;
		Supplier<ProtectedAreasData> var2 = () -> {
			return new ProtectedAreasData(var1_f1);
		};
		ServerLevel var3 = var0;
		final ServerLevel var3_f2 = var3;
		BiFunction<CompoundTag, HolderLookup.Provider, ProtectedAreasData> var4 = (l0, l1) -> {
			return load(l0,l1,var3_f2);
		};
		return ((ProtectedAreasData)(var0.getDataStorage().computeIfAbsent(new SavedData.Factory(var2,var4),"mineminenomi-protected-areas")));
	}
	public static ProtectedAreasData load(CompoundTag var0, HolderLookup.Provider var1, ServerLevel var2) {
		ProtectedAreasData var3 = new ProtectedAreasData(var2);
		ListTag var4 = var0.getList("protectedAreas",10);
		var3.abilityProtections.clear();
		int var5 = 0;
		do{
			if ((var5) < (var4.size())){
				ProtectedArea var6 = ProtectedArea.from(var2,var4.getCompound(var5));
				var3.abilityProtections.put(var6.getLabel(),var6);
				var5++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var3;
	}
	public CompoundTag save(CompoundTag var1, HolderLookup.Provider var2) {
		ListTag var3 = new ListTag();
		if ((this.abilityProtections.size()) > (0)){
			Iterator var4 = this.abilityProtections.entrySet().iterator();
			do{
				if (var4.hasNext()){
					var3.add(((ProtectedArea)(((Map.Entry)(var4.next())).getValue())).save());
					continue;
				}else{
					break;
				}
			} while (true);
		}
		var1.put("protectedAreas",(Tag)(var3));
		return var1;
	}
	public void tick() {
		this.level.getProfiler().push("world restoration");
		Iterator var1 = this.getAllRestrictions().values().iterator();
		do{
			if (var1.hasNext()){
				((ProtectedArea)(var1.next())).restoreBlocks((Level)(this.level));
				continue;
			}else{
				break;
			}
		} while (true);
		this.level.getProfiler().pop();
	}
	public boolean isInsideRestrictedArea(int var1, int var2, int var3) {
		if ((this.abilityProtections.size()) <= (0)){
			return false;
		}else{
			Iterator var4 = this.abilityProtections.values().iterator();
			do{
				if (var4.hasNext()){
					if (((ProtectedArea)(var4.next())).isInside((Level)(this.level),var1,var2,var3)){
						return true;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return false;
		}
	}
	@Nullable()
	public @Nullable() ProtectedArea getProtectedArea(String var1) {
		return ((ProtectedArea)(this.abilityProtections.get(var1)));
	}
	public Optional<ProtectedArea> getProtectedArea(int var1, int var2, int var3) {
		ProtectedAreasData var4 = this;
		int var5 = var1;
		int var6 = var2;
		int var7 = var3;
		final int var5_f1 = var5;
		final int var6_f2 = var6;
		final int var7_f3 = var7;
		Predicate<ProtectedArea> var8 = (l0) -> {
			return l0.isInside((Level)(this.level),var5_f1,var6_f2,var7_f3);
		};
		Comparator<ProtectedArea> var9 = (l0, l1) -> {
			return ((l0.getSize()) > (l1.getSize())) ? (1) : (-1);
		};
		return (Optional<ProtectedArea>) (Optional) (((java.util.stream.Stream)(((java.util.stream.Stream)(this.abilityProtections.values().stream())).filter((Predicate)(var8)))).sorted((Comparator)(var9)).findFirst());
	}
	public void addRestrictedArea(ProtectedArea var1) {
		this.abilityProtections.put(var1.getLabel(),var1);
		this.setDirty();
	}
	public void addRestrictedArea(BlockPos var1, int var2, String var3) {
		ProtectedArea var4 = new ProtectedArea(var1,var2,var3);
		this.abilityProtections.put(var4.getLabel(),var4);
		this.setDirty();
	}
	public void resizeRestrictedArea(String var1, int var2) {
		((ProtectedArea)(this.abilityProtections.get(var1))).setSize(var2);
		this.setDirty();
	}
	public void removeRestrictedArea(String var1) {
		this.abilityProtections.remove(var1);
		this.setDirty();
	}
	public void removeRestrictedArea(int var1, int var2, int var3) {
		Iterator var4 = this.abilityProtections.values().iterator();
		do{
			if (var4.hasNext()){
				ProtectedArea var5 = ((ProtectedArea)(var4.next()));
				if (((var1) == (var5.getCenter().getX())) && (((var2) == (var5.getCenter().getY())) && ((var3) == (var5.getCenter().getZ())))){
					this.abilityProtections.remove(var5.getLabel());
					this.setDirty();
					return;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public Map<String, ProtectedArea> getAllRestrictions() {
		return (Map<String, ProtectedArea>) (Map) (Collections.unmodifiableMap((Map)(this.abilityProtections)));
	}
}