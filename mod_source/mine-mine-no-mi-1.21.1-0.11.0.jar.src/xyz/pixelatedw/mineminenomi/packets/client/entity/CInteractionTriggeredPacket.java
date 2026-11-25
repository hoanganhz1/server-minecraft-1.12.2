package xyz.pixelatedw.mineminenomi.packets.client.entity;

import com.mojang.datafixers.util.Function3;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.events.entity.InteractionTriggeredEvent;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public final record CInteractionTriggeredPacket(int button, InteractionHand hand, boolean isHittingEntity) {
	// Fields
	public static final CustomPacketPayload.Type<CInteractionTriggeredPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_interaction_triggered"));
	public static final StreamCodec<FriendlyByteBuf, CInteractionTriggeredPacket> STREAM_CODEC;

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		NeoForge.EVENT_BUS.post((Event)(new InteractionTriggeredEvent((LivingEntity)(var2),this.button,this.hand,this.isHittingEntity)));
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<CInteractionTriggeredPacket, Integer> var0 = CInteractionTriggeredPacket::button;
		Function<CInteractionTriggeredPacket, InteractionHand> var1 = CInteractionTriggeredPacket::hand;
		Function<CInteractionTriggeredPacket, Boolean> var2 = CInteractionTriggeredPacket::isHittingEntity;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,NeoForgeStreamCodecs.enumCodec(InteractionHand.class),var1,ByteBufCodecs.BOOL,var2,(Function3)(((com.mojang.datafixers.util.Function3)(CInteractionTriggeredPacket::new)))));
	}
}