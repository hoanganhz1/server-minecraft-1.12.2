package xyz.pixelatedw.mineminenomi.packets.server.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.CreateCrewScreen;

public class SOpenCreateCrewScreenPacket implements IClientPacket {
	// Fields
	public static final CustomPacketPayload.Type<SOpenCreateCrewScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_create_crew_screen"));
	public static final SOpenCreateCrewScreenPacket INSTANCE = new SOpenCreateCrewScreenPacket();
	public static final StreamCodec<FriendlyByteBuf, SOpenCreateCrewScreenPacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ClientPayloadContext var1) {
		Minecraft.getInstance().setScreen((Screen)(new CreateCrewScreen()));
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}