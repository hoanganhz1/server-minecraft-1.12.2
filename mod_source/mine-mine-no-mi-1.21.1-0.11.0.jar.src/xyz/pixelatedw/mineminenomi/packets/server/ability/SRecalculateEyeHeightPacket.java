package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SRecalculateEyeHeightPacket(int entityId) {
	// Fields
	public static final CustomPacketPayload.Type<SRecalculateEyeHeightPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_recalculate_eye_height"));
	public static final StreamCodec<FriendlyByteBuf, SRecalculateEyeHeightPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof LivingEntity)){
			var2.refreshDimensions();
			return;
		}else{
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SRecalculateEyeHeightPacket, Integer> var0 = SRecalculateEyeHeightPacket::entityId;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,(Function)(((Function<Integer, SRecalculateEyeHeightPacket>)(SRecalculateEyeHeightPacket::new)))));
	}
}