package xyz.pixelatedw.mineminenomi.packets.server;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.blocks.FlagBlock;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SSetFlagOnFirePacket(BlockPos pos, boolean isOnFire) {
	// Fields
	public static final CustomPacketPayload.Type<SSetFlagOnFirePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_set_flag_on_fire"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SSetFlagOnFirePacket> STREAM_CODEC;

	public static void encode(RegistryFriendlyByteBuf var0, SSetFlagOnFirePacket var1) {
		var0.writeBlockPos(var1.pos);
		var0.writeBoolean(var1.isOnFire);
	}
	public static SSetFlagOnFirePacket decode(RegistryFriendlyByteBuf var0) {
		return new SSetFlagOnFirePacket(var0.readBlockPos(),var0.readBoolean());
	}
	public void handle(ClientPayloadContext var1) {
		ClientLevel var2 = Minecraft.getInstance().level;
		if (var2.getBlockState(this.pos).getBlock() instanceof FlagBlock){
			BlockEntity var3 = var2.getBlockEntity(this.pos);
			if (((var3) != (null)) && (var3 instanceof FlagBlockEntity)){
				((FlagBlockEntity)(var3)).setOnFire(this.isOnFire);
			}else{
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SSetFlagOnFirePacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SSetFlagOnFirePacket::decode)))));
	}
}