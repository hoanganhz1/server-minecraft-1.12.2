package xyz.pixelatedw.mineminenomi.blocks.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;

public class RoadPoneglyphBlockEntity extends BlockEntity {
	public RoadPoneglyphBlockEntity(BlockPos var1, BlockState var2) {
		super(((BlockEntityType)(ModBlockEntities.PONEGLYPH.get())),var1,var2);
	}
	public void saveAdditional(CompoundTag var1, HolderLookup.Provider var2) {
		super.saveAdditional(var1,var2);
	}
	public void loadAdditional(CompoundTag var1, HolderLookup.Provider var2) {
		super.loadAdditional(var1,var2);
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