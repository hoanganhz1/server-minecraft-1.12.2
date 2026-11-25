package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.init.ModKeybindings;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SSetServerMaxBarsPacket(int bars) {
	// Fields
	public static final CustomPacketPayload.Type<SSetServerMaxBarsPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_set_server_max_bars"));
	public static final StreamCodec<FriendlyByteBuf, SSetServerMaxBarsPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		ModKeybindings.serverMaxBars = this.bars;
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SSetServerMaxBarsPacket, Integer> var0 = SSetServerMaxBarsPacket::bars;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,(Function)(((Function<Integer, SSetServerMaxBarsPacket>)(SSetServerMaxBarsPacket::new)))));
	}
}