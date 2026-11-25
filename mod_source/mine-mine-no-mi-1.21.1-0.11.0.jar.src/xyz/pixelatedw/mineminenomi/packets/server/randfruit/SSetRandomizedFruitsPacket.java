package xyz.pixelatedw.mineminenomi.packets.server.randfruit;

import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler$Client;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SSetRandomizedFruitsPacket(boolean isRandomized) {
	// Fields
	public static final CustomPacketPayload.Type<SSetRandomizedFruitsPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_set_randomized_fruits"));
	public static final StreamCodec<FriendlyByteBuf, SSetRandomizedFruitsPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		RandomizedFruitsHandler$Client.HAS_RANDOMIZED_FRUIT = this.isRandomized;
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SSetRandomizedFruitsPacket, Boolean> var0 = SSetRandomizedFruitsPacket::isRandomized;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.BOOL,var0,(Function)(((Function<Boolean, SSetRandomizedFruitsPacket>)(SSetRandomizedFruitsPacket::new)))));
	}
}