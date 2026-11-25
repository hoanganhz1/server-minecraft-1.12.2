package xyz.pixelatedw.mineminenomi.packets.server.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.EncyclopediaScreen;

public final record SOpenEncyclopediaScreenPacket(ItemStack book) {
	// Fields
	public static final CustomPacketPayload.Type<SOpenEncyclopediaScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_encyclopedia_screen"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SOpenEncyclopediaScreenPacket> STREAM_CODEC;

	public static void encode(RegistryFriendlyByteBuf var0, SOpenEncyclopediaScreenPacket var1) {
		ItemStack.STREAM_CODEC.encode(var0,var1.book);
	}
	public static SOpenEncyclopediaScreenPacket decode(RegistryFriendlyByteBuf var0) {
		ItemStack var1 = ((ItemStack)(ItemStack.STREAM_CODEC.decode(var0)));
		return new SOpenEncyclopediaScreenPacket(var1);
	}
	public void handle(ClientPayloadContext var1) {
		Minecraft.getInstance().setScreen((Screen)(new EncyclopediaScreen(this.book)));
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SOpenEncyclopediaScreenPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SOpenEncyclopediaScreenPacket::decode)))));
	}
}