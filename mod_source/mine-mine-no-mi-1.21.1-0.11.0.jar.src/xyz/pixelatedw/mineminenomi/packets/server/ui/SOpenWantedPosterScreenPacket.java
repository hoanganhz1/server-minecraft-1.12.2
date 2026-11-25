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
import xyz.pixelatedw.mineminenomi.api.items.components.WantedPosterData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.WantedPosterScreen;

public final record SOpenWantedPosterScreenPacket(CompoundTag wantedData) {
	// Fields
	public static final CustomPacketPayload.Type<SOpenWantedPosterScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_wanted_poster_screen"));
	public static final StreamCodec<FriendlyByteBuf, SOpenWantedPosterScreenPacket> STREAM_CODEC;

	public SOpenWantedPosterScreenPacket(WantedPosterData var1) {
		this((CompoundTag)(var1.writeTag()));
	}
	public static void encode(FriendlyByteBuf var0, SOpenWantedPosterScreenPacket var1) {
		var0.writeNbt((Tag)(var1.wantedData));
	}
	public static SOpenWantedPosterScreenPacket decode(FriendlyByteBuf var0) {
		return new SOpenWantedPosterScreenPacket((CompoundTag)(var0.readNbt()));
	}
	public void handle(ClientPayloadContext var1) {
		WantedPosterData var2 = WantedPosterData.from(this.wantedData);
		Minecraft.getInstance().setScreen((Screen)(new WantedPosterScreen(var2)));
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SOpenWantedPosterScreenPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SOpenWantedPosterScreenPacket::decode)))));
	}
}