package xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.dialogues.BarkeeperDialogueScreen;

public final record SOpenBarkeeperDialogueScreenPacket(int entityId) {
	// Fields
	public static final CustomPacketPayload.Type<SOpenBarkeeperDialogueScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_barkeeper_dialogue_screen"));
	public static final StreamCodec<FriendlyByteBuf, SOpenBarkeeperDialogueScreenPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, SOpenBarkeeperDialogueScreenPacket var1) {
		var0.writeInt(var1.entityId);
	}
	public static SOpenBarkeeperDialogueScreenPacket decode(FriendlyByteBuf var0) {
		return new SOpenBarkeeperDialogueScreenPacket(var0.readInt());
	}
	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			Minecraft.getInstance().setScreen((Screen)(new BarkeeperDialogueScreen(var3)));
			return;
		}else{
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SOpenBarkeeperDialogueScreenPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SOpenBarkeeperDialogueScreenPacket::decode)))));
	}
}