package xyz.pixelatedw.mineminenomi.packets.server.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen;

public class SOpenAbilitySelectionScreenPacket implements IClientPacket {
	// Fields
	public static final CustomPacketPayload.Type<SOpenAbilitySelectionScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_abilities_selection_screen"));
	public static final SOpenAbilitySelectionScreenPacket INSTANCE = new SOpenAbilitySelectionScreenPacket();
	public static final StreamCodec<FriendlyByteBuf, SOpenAbilitySelectionScreenPacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ClientPayloadContext var1) {
		if ((var1.player()) == (null)){
			return;
		}else{
			Minecraft.getInstance().setScreen((Screen)(new AbilitiesListScreen()));
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}