package xyz.pixelatedw.mineminenomi.packets.server.ui.acks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.ui.IEventReceiverScreen;
import xyz.pixelatedw.mineminenomi.api.ui.SimpleMessageScreenEvent;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SSimpleMessageScreenEventPacket(SimpleMessageScreenEvent event) {
	// Fields
	public static final CustomPacketPayload.Type<SSimpleMessageScreenEventPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_simple_message_screen_event"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SSimpleMessageScreenEventPacket> STREAM_CODEC;

	public static void encode(RegistryFriendlyByteBuf var0, SSimpleMessageScreenEventPacket var1) {
		var0.writeNbt((Tag)(var1.event.serializeNBT((HolderLookup.Provider)(var0.registryAccess()))));
	}
	public static SSimpleMessageScreenEventPacket decode(RegistryFriendlyByteBuf var0) {
		SimpleMessageScreenEvent var1 = new SimpleMessageScreenEvent();
		var1.deserializeNBT((HolderLookup.Provider)(var0.registryAccess()),var0.readNbt());
		return new SSimpleMessageScreenEventPacket(var1);
	}
	public void handle(ClientPayloadContext var1) {
		Screen var2 = Minecraft.getInstance().screen;
		if (var2 instanceof IEventReceiverScreen){
			((IEventReceiverScreen)(var2)).handleEvent(this.event);
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SSimpleMessageScreenEventPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SSimpleMessageScreenEventPacket::decode)))));
	}
}