package xyz.pixelatedw.mineminenomi.api;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class OneFruitEntry {
	// Fields
	private ResourceLocation fruitKey;
	 Optional<UUID> owner;
	 OneFruitEntry$Status status;
	 String statusMessage;
	 Optional<BlockPos> blockPos;
	private Optional<ResourceLocation> dimension;
	private Date lastUpdate;
	private List<OneFruitEntry$HistoryEntry> history;

	OneFruitEntry() {
		this.owner = (Optional) (((java.util.Optional)(Optional.empty())));
		this.statusMessage = "";
		this.blockPos = (Optional) (((java.util.Optional)(Optional.empty())));
		this.dimension = (Optional) (((java.util.Optional)(Optional.empty())));
		this.history = new ArrayList();
	}
	public OneFruitEntry(ResourceLocation var1, @Nullable() UUID var2, OneFruitEntry$Status var3, @Nullable() BlockPos var4, @Nullable() ResourceLocation var5, @Nullable() String var6) {
		this.owner = (Optional) (((java.util.Optional)(Optional.empty())));
		this.statusMessage = "";
		this.blockPos = (Optional) (((java.util.Optional)(Optional.empty())));
		this.dimension = (Optional) (((java.util.Optional)(Optional.empty())));
		this.history = new ArrayList();
		this.fruitKey = var1;
		if (!(Strings.isNullOrEmpty(var6))){
			this.setStatusMessage(var6);
		}
		this.blockPos = (Optional) (Optional.ofNullable(var4));
		this.setBlockPos(var4);
		this.dimension = (Optional) (Optional.ofNullable(var5));
		this.setDimensionLocation(var5);
		this.update(var2,var3,var5,var4);
	}
	public static OneFruitEntry from(CompoundTag var0) {
		OneFruitEntry var1 = new OneFruitEntry();
		var1.load(var0);
		return var1;
	}
	public ResourceLocation getKey() {
		return this.fruitKey;
	}
	public Optional<Item> getItemFromKey() {
		return Optional.ofNullable(((Item)(BuiltInRegistries.ITEM.get(this.fruitKey))));
	}
	public Optional<UUID> getOwner() {
		return this.owner;
	}
	public void setStatusMessage(String var1) {
		this.statusMessage = var1;
	}
	public void setBlockPos(BlockPos var1) {
		this.blockPos = (Optional) (Optional.ofNullable(var1));
	}
	public Optional<BlockPos> getBlockPos() {
		return this.blockPos;
	}
	public void setDimensionLocation(ResourceLocation var1) {
		this.dimension = (Optional) (Optional.ofNullable(var1));
	}
	public Optional<ResourceLocation> getDimensionLocation() {
		return this.dimension;
	}
	public OneFruitEntry$Status getStatus() {
		return this.status;
	}
	public void update(UUID var1, OneFruitEntry$Status var2, ResourceLocation var3, BlockPos var4) {
		this.owner = (Optional) (Optional.ofNullable(var1));
		this.status = var2;
		this.lastUpdate = new Date();
		this.dimension = (Optional) (Optional.ofNullable(var3));
		this.blockPos = (Optional) (Optional.ofNullable(var4));
		this.registerCurrentHistory();
	}
	public void forceLastUpdateNow() {
		this.lastUpdate = new Date();
	}
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void registerCurrentHistory() {
		this.history.add(new OneFruitEntry$HistoryEntry(this.owner,this.status,this.statusMessage,this.dimension,this.blockPos,this.lastUpdate));
		this.statusMessage = "";
	}
	public List<OneFruitEntry$HistoryEntry> getHistory() {
		return this.history;
	}
	public CompoundTag save() {
		CompoundTag var1 = new CompoundTag();
		var1.putString("fruit",this.fruitKey.toString());
		OneFruitEntry var2 = this;
		CompoundTag var3 = var1;
		final CompoundTag var3_f1 = var3;
		Consumer<? super UUID> var4 = (l0) -> {
			var3_f1.putUUID("owner",((UUID)(this.owner.get())));
		};
		((java.util.Optional)(this.owner)).ifPresent((Consumer)(var4));
		var1.putString("status",this.status.name());
		var1.putLong("lastUpdate",this.lastUpdate.getTime());
		ListTag var5 = new ListTag();
		int var6 = Math.min(1000,this.history.size());
		Iterator var7 = this.history.stream().skip((long)((this.history.size()) - (var6))).limit((long)(var6)).toList().iterator();
		do{
			if (var7.hasNext()){
				var5.add(((OneFruitEntry$HistoryEntry)(var7.next())).save());
				continue;
			}else{
				break;
			}
		} while (true);
		var1.put("history",(Tag)(var5));
		return var1;
	}
	public void load(CompoundTag var1) {
		this.fruitKey = ResourceLocation.parse(var1.getString("fruit"));
		if (var1.hasUUID("owner")){
			this.owner = (Optional) (Optional.of(var1.getUUID("owner")));
		}
		this.status = OneFruitEntry$Status.valueOf(var1.getString("status"));
		this.lastUpdate = new Date(var1.getLong("lastUpdate"));
		ListTag var2 = var1.getList("history",10);
		this.history.clear();
		int var3 = 0;
		do{
			if ((var3) < (var2.size())){
				CompoundTag var4 = var2.getCompound(var3);
				this.history.add(OneFruitEntry$HistoryEntry.from(var4));
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}