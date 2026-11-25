package xyz.pixelatedw.mineminenomi.packets.server.entity;

import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SForceDismountEntityPacket(int targetId) {
	// Fields
	public static final CustomPacketPayload.Type<SForceDismountEntityPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_force_dismount_entity"));
	public static final StreamCodec<FriendlyByteBuf, SForceDismountEntityPacket> STREAM_CODEC;

	public SForceDismountEntityPacket(LivingEntity var1) {
		this(var1.getId());
	}
	public void handle(ClientPayloadContext var1) {
		LocalPlayer var2 = Minecraft.getInstance().player;
		if ((var2) == (null)){
			return;
		}else{
			var2.ejectPassengers();
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SForceDismountEntityPacket, Integer> var0 = SForceDismountEntityPacket::targetId;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,(Function)(((Function<Integer, SForceDismountEntityPacket>)(SForceDismountEntityPacket::new)))));
	}
}