package xyz.pixelatedw.mineminenomi.blocks.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;

public class PoneglyphBlockEntity extends BlockEntity {
	// Fields
	private PoneglyphBlockEntity$Type entryType;
	private ChallengeCore<?> challenge;

	public PoneglyphBlockEntity(BlockPos var1, BlockState var2) {
		super(((BlockEntityType)(ModBlockEntities.PONEGLYPH.get())),var1,var2);
		this.entryType = PoneglyphBlockEntity$Type.CHALLENGE;
	}
	public PoneglyphBlockEntity$Type getEntryType() {
		return this.entryType;
	}
	public void setEntryType(PoneglyphBlockEntity$Type var1) {
		this.entryType = var1;
	}
	public ChallengeCore<?> getChallenge() {
		return (ChallengeCore<?>) (ChallengeCore) (this.challenge);
	}
	public void setChallenge(ChallengeCore<?> var1) {
		this.challenge = var1;
	}
	public void saveAdditional(CompoundTag var1, HolderLookup.Provider var2) {
		super.saveAdditional(var1,var2);
		var1.putInt("entryType",this.entryType.ordinal());
		if ((this.challenge) != (null)){
			var1.putString("entryId",this.challenge.getRegistryKey().toString());
		}
	}
	public void loadAdditional(CompoundTag var1, HolderLookup.Provider var2) {
		super.loadAdditional(var1,var2);
		this.entryType = PoneglyphBlockEntity$Type.values()[var1.getInt("entryType")];
		if (var1.contains("entryId")){
			ResourceLocation var3 = ResourceLocation.parse(var1.getString("entryId"));
			this.challenge = (ChallengeCore) (((ChallengeCore)(WyRegistry.CHALLENGES.get(var3))));
		}
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