package xyz.pixelatedw.mineminenomi.blocks.blockentity;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.items.components.WantedPosterData;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;
import xyz.pixelatedw.mineminenomi.init.ModFactions;

public class WantedPosterBlockEntity extends BlockEntity {
	// Fields
	private WantedPosterData wantedPosterData;

	public WantedPosterBlockEntity(BlockPos var1, BlockState var2) {
		super(((BlockEntityType)(ModBlockEntities.WANTED_POSTER.get())),var1,var2);
	}
	public void setWantedPosterData(WantedPosterData var1) {
		this.wantedPosterData = var1;
	}
	public WantedPosterData getWantedPosterData() {
		if ((this.wantedPosterData) == (null)){
			this.wantedPosterData = WantedPosterData.empty();
		}
		return this.wantedPosterData;
	}
	public boolean isPirate() {
		Function<Faction, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(l0.equals(ModFactions.PIRATE.get()));
		};
		return ((Boolean)(((java.util.Optional)(this.wantedPosterData.faction())).map((Function)(var1)).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public boolean isRevolutionary() {
		Function<Faction, Boolean> var1 = (l0) -> {
			return Boolean.valueOf(l0.equals(ModFactions.REVOLUTIONARY_ARMY.get()));
		};
		return ((Boolean)(((java.util.Optional)(this.wantedPosterData.faction())).map((Function)(var1)).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public void saveAdditional(CompoundTag var1, HolderLookup.Provider var2) {
		super.saveAdditional(var1,var2);
		if ((this.wantedPosterData) == (null)){
			return;
		}else{
			var1.put("Data",(Tag)(this.wantedPosterData.writeTag()));
			return;
		}
	}
	public void loadAdditional(CompoundTag var1, HolderLookup.Provider var2) {
		super.loadAdditional(var1,var2);
		this.wantedPosterData = WantedPosterData.from(var1.getCompound("Data"));
	}
	public CompoundTag getUpdateTag(HolderLookup.Provider var1) {
		CompoundTag var2 = super.getUpdateTag(var1);
		this.saveAdditional(var2,var1);
		return var2;
	}
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create((BlockEntity)(this));
	}
}