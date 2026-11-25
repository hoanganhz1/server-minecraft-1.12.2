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
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.ui.IEventReceiverScreen;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SScreenDataEventPacket(CompoundTag eventData) {
	// Fields
	public static final CustomPacketPayload.Type<SScreenDataEventPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_screen_data_event"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SScreenDataEventPacket> STREAM_CODEC;

	public SScreenDataEventPacket(Player var1, INBTSerializable<CompoundTag> var2) {
		this(((CompoundTag)(var2.serializeNBT((HolderLookup.Provider)(var1.registryAccess())))));
	}
	public static void encode(RegistryFriendlyByteBuf var0, SScreenDataEventPacket var1) {
		var0.writeNbt((Tag)(var1.eventData));
	}
	public static SScreenDataEventPacket decode(RegistryFriendlyByteBuf var0) {
		return new SScreenDataEventPacket(var0.readNbt());
	}
	public void handle(ClientPayloadContext var1) {
		Minecraft var2 = Minecraft.getInstance();
		IEventReceiverScreen.tryHandle(var2.screen,(HolderLookup.Provider)(var2.player.registryAccess()),this.eventData);
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SScreenDataEventPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SScreenDataEventPacket::decode)))));
	}
}