package xyz.pixelatedw.mineminenomi.api;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class OneFruitEntry$HistoryEntry {
	// Fields
	 Optional<UUID> owner;
	 OneFruitEntry$Status status;
	 String statusMessage;
	 Optional<BlockPos> blockPos;
	private Optional<ResourceLocation> dimensionId;
	private Date date;

	OneFruitEntry$HistoryEntry() {
		this.owner = (Optional) (((java.util.Optional)(Optional.empty())));
		this.statusMessage = "";
		this.blockPos = (Optional) (((java.util.Optional)(Optional.empty())));
		this.dimensionId = (Optional) (((java.util.Optional)(Optional.empty())));
	}
	public OneFruitEntry$HistoryEntry(Optional<UUID> var1, OneFruitEntry$Status var2, String var3, Optional<ResourceLocation> var4, Optional<BlockPos> var5, Date var6) {
		this.owner = (Optional) (((java.util.Optional)(Optional.empty())));
		this.statusMessage = "";
		this.blockPos = (Optional) (((java.util.Optional)(Optional.empty())));
		this.dimensionId = (Optional) (((java.util.Optional)(Optional.empty())));
		this.owner = var1;
		this.status = var2;
		this.statusMessage = var3;
		this.blockPos = var5;
		this.dimensionId = var4;
		this.date = var6;
	}
	public static OneFruitEntry$HistoryEntry from(CompoundTag var0) {
		OneFruitEntry$HistoryEntry var1 = new OneFruitEntry$HistoryEntry();
		var1.load(var0);
		return var1;
	}
	public Optional<UUID> getOwner() {
		return this.owner;
	}
	public OneFruitEntry$Status getStatus() {
		return this.status;
	}
	public String getStatusMessage() {
		return this.statusMessage;
	}
	public Optional<BlockPos> getBlockPos() {
		return this.blockPos;
	}
	public Optional<ResourceLocation> getDimensionLocation() {
		return this.dimensionId;
	}
	public Date getDate() {
		return this.date;
	}
	public CompoundTag save() { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public void load(CompoundTag var1) {
		if (var1.hasUUID("uuid")){
			this.owner = (Optional) (Optional.of(var1.getUUID("uuid")));
		}
		if ((var1.contains("posX")) && ((var1.contains("posY")) && (var1.contains("posZ")))){
			this.blockPos = (Optional) (Optional.of(new BlockPos(var1.getInt("posX"),var1.getInt("posY"),var1.getInt("posZ"))));
		}
		if (var1.contains("dimension")){
			this.dimensionId = (Optional) (Optional.ofNullable(ResourceLocation.tryParse(var1.getString("dimension"))));
		}
		this.status = OneFruitEntry$Status.valueOf(var1.getString("status"));
		this.statusMessage = var1.getString("statusMessage");
		this.date = new Date(var1.getLong("date"));
	}
}