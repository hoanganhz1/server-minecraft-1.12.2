package xyz.pixelatedw.mineminenomi.data.world;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.OneFruitEntry;
import xyz.pixelatedw.mineminenomi.api.OneFruitEntry$Status;
import xyz.pixelatedw.mineminenomi.api.events.onefruit.DroppedDevilFruitEvent;
import xyz.pixelatedw.mineminenomi.api.events.onefruit.InventoryDevilFruitEvent;
import xyz.pixelatedw.mineminenomi.api.events.onefruit.LostDevilFruitEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class OneFruitWorldData extends SavedData {
	// Fields
	private static final String IDENTIFIER = "mineminenomi-onefruit";
	private List<OneFruitEntry> oneFruitEntries;

	private OneFruitWorldData() {
		this.oneFruitEntries = new ArrayList();
	}
	public static OneFruitWorldData get() {
		ServerLevel var0 = ServerLifecycleHooks.getCurrentServer().overworld();
		Supplier<OneFruitWorldData> var1 = OneFruitWorldData::new;
		return ((OneFruitWorldData)(var0.getDataStorage().computeIfAbsent(new SavedData.Factory(var1,(BiFunction)(((BiFunction<CompoundTag, HolderLookup.Provider, OneFruitWorldData>)(OneFruitWorldData::load)))),"mineminenomi-onefruit")));
	}
	public static OneFruitWorldData load(CompoundTag var0, HolderLookup.Provider var1) {
		OneFruitWorldData var2 = new OneFruitWorldData();
		ListTag var3 = var0.getList("oneFruitList",10);
		var2.oneFruitEntries.clear();
		int var4 = 0;
		do{
			if ((var4) < (var3.size())){
				OneFruitEntry var5 = OneFruitEntry.from(var3.getCompound(var4));
				var2.oneFruitEntries.add(var5);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public CompoundTag save(CompoundTag var1, HolderLookup.Provider var2) {
		ListTag var3 = new ListTag();
		if ((this.oneFruitEntries.size()) > (0)){
			Iterator var4 = this.oneFruitEntries.iterator();
			do{
				if (var4.hasNext()){
					var3.add(((OneFruitEntry)(var4.next())).save());
					continue;
				}else{
					break;
				}
			} while (true);
		}
		var1.put("oneFruitList",(Tag)(var3));
		return var1;
	}
	public List<OneFruitEntry> getOneFruitEntries() {
		return this.oneFruitEntries;
	}
	@Nullable()
	public @Nullable() OneFruitEntry getOneFruitEntry(ResourceLocation var1) {
		ResourceLocation var2 = var1;
		final ResourceLocation var2_f1 = var2;
		Predicate<OneFruitEntry> var3 = (l0) -> {
			return l0.getKey().equals(var2_f1);
		};
		Optional var4 = this.oneFruitEntries.stream().filter(var3).findFirst();
		if (var4.isPresent()){
			return ((OneFruitEntry)(var4.get()));
		}else{
			return null;
		}
	}
	public boolean isFruitAvailable(AkumaNoMiItem var1) {
		return this.isFruitAvailable(var1.getRegistryKey());
	}
	public boolean isFruitAvailable(ResourceLocation var1) {
		ResourceLocation var2 = var1;
		final ResourceLocation var2_f1 = var2;
		Predicate<OneFruitEntry> var3 = (l0) -> {
			return l0.getKey().equals(var2_f1);
		};
		Optional var4 = this.oneFruitEntries.stream().filter(var3).findFirst();
		if (!(var4.isPresent())){
			return true;
		}else{
			if ((var4.isPresent()) && ((((OneFruitEntry)(var4.get())).getStatus()) == (OneFruitEntry$Status.LOST))){
				return true;
			}else{
				return false;
			}
		}
	}
	public boolean isFruitInWorld(ResourceLocation var1) {
		return this.oneFruitEntries.stream().anyMatch((Predicate)(((Predicate<OneFruitEntry>)((l0) -> {
			return (l0.getKey().equals(var1)) && ((l0.getStatus()) != (OneFruitEntry$Status.LOST));
		}))));
	}
	public boolean isFruitInUse(ResourceLocation var1) {
		return this.oneFruitEntries.stream().anyMatch((Predicate)(((Predicate<OneFruitEntry>)((l0) -> {
			return (l0.getKey().equals(var1)) && ((l0.getStatus()) == (OneFruitEntry$Status.IN_USE));
		}))));
	}
	public boolean isFruitDuped(ResourceLocation var1, UUID var2) {
		return this.oneFruitEntries.stream().anyMatch((Predicate)(((Predicate<OneFruitEntry>)((l0) -> {
			int lv5_3 = ((l0.getKey().equals(var1)) && ((l0.getStatus()) != (OneFruitEntry$Status.LOST))) ? (1) : (0);
			int lv5_4 = ((l0.getOwner().isPresent()) && (!(((UUID)(l0.getOwner().get())).equals(var2)))) ? (1) : (0);
			return ((lv5_3) != (0)) && ((lv5_4) != (0));
		}))));
	}
	private boolean addOneFruit(ResourceLocation var1, @Nullable() UUID var2, OneFruitEntry$Status var3) {
		return this.addOneFruit(var1,var2,var3,(ResourceLocation)(null),(BlockPos)(null),(String)(null));
	}
	private boolean addOneFruit(ResourceLocation var1, @Nullable() UUID var2, OneFruitEntry$Status var3, BlockPos var4) {
		return this.addOneFruit(var1,var2,var3,(ResourceLocation)(null),var4,(String)(null));
	}
	private boolean addOneFruit(ResourceLocation var1, @Nullable() UUID var2, OneFruitEntry$Status var3, ResourceLocation var4, BlockPos var5) {
		return this.addOneFruit(var1,var2,var3,var4,var5,(String)(null));
	}
	private boolean addOneFruit(ResourceLocation var1, @Nullable() UUID var2, OneFruitEntry$Status var3, @Nullable() ResourceLocation var4, @Nullable() BlockPos var5, @Nullable() String var6) {
		if (((var1) != (null)) && (ServerConfig.hasOneFruitPerWorldSimpleLogic())){
			if (this.isFruitInWorld(var1)){
				return false;
			}else{
				OneFruitEntry var7 = new OneFruitEntry(var1,var2,var3,var5,var4,var6);
				this.oneFruitEntries.add(var7);
				this.setDirty();
				return true;
			}
		}else{
			return false;
		}
	}
	public boolean updateOneFruit(ResourceLocation var1, @Nullable() UUID var2, OneFruitEntry$Status var3) {
		return this.updateOneFruit(var1,var2,var3,(ResourceLocation)(null),(BlockPos)(null),(String)(null),false);
	}
	public boolean updateOneFruit(ResourceLocation var1, @Nullable() UUID var2, OneFruitEntry$Status var3, @Nullable() String var4) {
		return this.updateOneFruit(var1,var2,var3,(ResourceLocation)(null),(BlockPos)(null),var4,false);
	}
	public boolean updateOneFruit(ResourceLocation var1, @Nullable() UUID var2, OneFruitEntry$Status var3, @Nullable() BlockPos var4, @Nullable() String var5) {
		return this.updateOneFruit(var1,var2,var3,(ResourceLocation)(null),var4,var5,false);
	}
	public boolean updateOneFruit(ResourceLocation var1, @Nullable() UUID var2, OneFruitEntry$Status var3, @Nullable() BlockPos var4, @Nullable() ResourceLocation var5, String var6) {
		return this.updateOneFruit(var1,var2,var3,var5,var4,var6,false);
	}
	public boolean updateOneFruit(ResourceLocation var1, @Nullable() UUID var2, OneFruitEntry$Status var3, @Nullable() ResourceLocation var4, @Nullable() BlockPos var5, @Nullable() String var6, boolean var7) {
		if (((var1) != (null)) && (ServerConfig.hasOneFruitPerWorldSimpleLogic())){
			ResourceLocation var8 = var1;
			final ResourceLocation var8_f1 = var8;
			Predicate<OneFruitEntry> var9 = (l0) -> {
			return l0.getKey().equals(var8_f1);
		};
			Optional var10 = this.oneFruitEntries.stream().filter(var9).findFirst();
			if (!(var10.isPresent())){
				this.addOneFruit(var1,var2,var3,var4,var5,var6);
			}else{
				if (!(var7)){
					int var11 = ((((OneFruitEntry)(var10.get())).getOwner().isPresent()) && (((UUID)(((OneFruitEntry)(var10.get())).getOwner().get())).equals(var2))) ? (1) : (0);
					if (((((OneFruitEntry)(var10.get())).getStatus()) == (OneFruitEntry$Status.IN_USE)) && (!(((var3) == (OneFruitEntry$Status.LOST)) && ((var11) != (0))))){
						return false;
					}else{
						if (((((OneFruitEntry)(var10.get())).getStatus()) == (OneFruitEntry$Status.INVENTORY)) && (((var3) == (OneFruitEntry$Status.DROPPED)) && ((var11) == (0)))){
							return false;
						}else{
							if (((((OneFruitEntry)(var10.get())).getStatus()) == (OneFruitEntry$Status.INVENTORY)) && (((var3) == (OneFruitEntry$Status.INVENTORY)) && ((var11) == (0)))){
								return false;
							}else{
								if (((((OneFruitEntry)(var10.get())).getStatus()) == (OneFruitEntry$Status.INVENTORY)) && (((var3) == (OneFruitEntry$Status.IN_USE)) && ((var11) == (0)))){
									return false;
								}
							}
						}
					}
				}
				if (!(Strings.isNullOrEmpty(var6))){
					((OneFruitEntry)(var10.get())).setStatusMessage(var6);
				}
				((OneFruitEntry)(var10.get())).update(var2,var3,var4,var5);
				this.setDirty();
			}
			return true;
		}else{
			return false;
		}
	}
	public void lostOneFruit(ResourceLocation var1, @Nullable() LivingEntity var2, String var3) {
		if (((var1) != (null)) && (ServerConfig.hasOneFruitPerWorldSimpleLogic())){
			ResourceLocation var4 = var1;
			final ResourceLocation var4_f1 = var4;
			Predicate<OneFruitEntry> var5 = (l0) -> {
			return l0.getKey().equals(var4_f1);
		};
			Optional var6 = this.oneFruitEntries.stream().filter(var5).findFirst();
			if (var6.isPresent()){
				((OneFruitEntry)(var6.get())).setStatusMessage(var3);
				OneFruitEntry var7 = ((OneFruitEntry)(var6.get()));
				var7.update(((var2) != (null)) ? (var2.getUUID()) : (null),OneFruitEntry$Status.LOST,(ResourceLocation)(null),(BlockPos)(null));
				this.setDirty();
				Item var8 = ((Item)(BuiltInRegistries.ITEM.get(((OneFruitEntry)(var6.get())).getKey())));
				NeoForge.EVENT_BUS.post((Event)(new LostDevilFruitEvent(var2,var8,var3)));
			}
			return;
		}else{
			return;
		}
	}
	public boolean dropOneFruit(AkumaNoMiItem var1, @Nullable() LivingEntity var2, ResourceLocation var3, BlockPos var4, @Nullable() String var5) {
		return this.dropOneFruit(var1,var2,var3,var4,var5,false);
	}
	public boolean dropOneFruit(AkumaNoMiItem var1, @Nullable() LivingEntity var2, ResourceLocation var3, BlockPos var4, @Nullable() String var5, boolean var6) {
		DroppedDevilFruitEvent var7 = new DroppedDevilFruitEvent(var2,(Item)(var1),var3,var4,var5);
		NeoForge.EVENT_BUS.post((Event)(var7));
		OneFruitWorldData var8 = get();
		return var8.updateOneFruit(var1.getRegistryKey(),((var2) != (null)) ? (var2.getUUID()) : (null),OneFruitEntry$Status.DROPPED,var3,var4,var5,var6);
	}
	public boolean inventoryOneFruit(AkumaNoMiItem var1, @Nullable() LivingEntity var2, @Nullable() String var3) {
		return this.inventoryOneFruit(var1,var2,var3,false);
	}
	public boolean inventoryOneFruit(AkumaNoMiItem var1, @Nullable() LivingEntity var2, @Nullable() String var3, boolean var4) {
		InventoryDevilFruitEvent var5 = new InventoryDevilFruitEvent(var2,(Item)(var1),var3);
		NeoForge.EVENT_BUS.post((Event)(var5));
		OneFruitWorldData var6 = get();
		return var6.updateOneFruit(var1.getRegistryKey(),((var2) != (null)) ? (var2.getUUID()) : (null),OneFruitEntry$Status.INVENTORY,(ResourceLocation)(null),(BlockPos)(null),var3,var4);
	}
	public void forceUpdateOneFruit(ResourceLocation var1) {
		if (((var1) != (null)) && (ServerConfig.hasOneFruitPerWorldSimpleLogic())){
			ResourceLocation var2 = var1;
			final ResourceLocation var2_f1 = var2;
			Predicate<OneFruitEntry> var3 = (l0) -> {
			return l0.getKey().equals(var2_f1);
		};
			Optional var4 = this.oneFruitEntries.stream().filter(var3).findFirst();
			if (var4.isPresent()){
				((OneFruitEntry)(var4.get())).forceLastUpdateNow();
				this.setDirty();
			}
			return;
		}else{
			return;
		}
	}
	public void wipeOneFruitHistory(ResourceLocation var1) {
		if (((var1) != (null)) && (ServerConfig.hasOneFruitPerWorldSimpleLogic())){
			ResourceLocation var2 = var1;
			final ResourceLocation var2_f1 = var2;
			Predicate<OneFruitEntry> var3 = (l0) -> {
			return l0.getKey().equals(var2_f1);
		};
			Optional var4 = this.oneFruitEntries.stream().filter(var3).findFirst();
			if (var4.isPresent()){
				((OneFruitEntry)(var4.get())).getHistory().clear();
				this.setDirty();
			}
			return;
		}else{
			return;
		}
	}
}