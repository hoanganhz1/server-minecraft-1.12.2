package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SRemoveShadowEventsPacket(int entityId) {
	// Fields
	public static final CustomPacketPayload.Type<SRemoveShadowEventsPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_remove_shadow_events"));
	public static final StreamCodec<FriendlyByteBuf, SRemoveShadowEventsPacket> STREAM_CODEC;

	public SRemoveShadowEventsPacket(LivingEntity var1) {
		this(var1.getId());
	}
	public static void encode(FriendlyByteBuf var0, SRemoveShadowEventsPacket var1) {
		var0.writeInt(var1.entityId);
	}
	public static SRemoveShadowEventsPacket decode(FriendlyByteBuf var0) {
		int var1 = var0.readInt();
		return new SRemoveShadowEventsPacket(var1);
	}
	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof LivingEntity)){
			LivingEntity var3 = ((LivingEntity)(var2));
			Consumer<EntityStatsData> var4 = (l0) -> {
			l0.setShadow(false);
		};
			((java.util.Optional)(EntityStatsData.get(var3))).ifPresent((Consumer)(var4));
			return;
		}else{
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SRemoveShadowEventsPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SRemoveShadowEventsPacket::decode)))));
	}
}