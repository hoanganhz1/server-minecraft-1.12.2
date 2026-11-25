package xyz.pixelatedw.mineminenomi.packets.server.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.CrewDetailsScreen;

public final record SOpenCrewScreenPacket(Crew crew) {
	// Fields
	public static final CustomPacketPayload.Type<SOpenCrewScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_crew_screen"));
	public static final StreamCodec<FriendlyByteBuf, SOpenCrewScreenPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, SOpenCrewScreenPacket var1) {
		var0.writeNbt((Tag)(var1.crew.write()));
	}
	public static SOpenCrewScreenPacket decode(FriendlyByteBuf var0) {
		Crew var1 = Crew.from(var0.readNbt());
		return new SOpenCrewScreenPacket(var1);
	}
	public void handle(ClientPayloadContext var1) {
		Minecraft.getInstance().setScreen((Screen)(new CrewDetailsScreen(this.crew)));
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SOpenCrewScreenPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SOpenCrewScreenPacket::decode)))));
	}
}