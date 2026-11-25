package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.Optional;
import java.util.function.Consumer;
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
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SUpdateRogueStateEventPacket(boolean state) {
	// Fields
	public static final CustomPacketPayload.Type<SUpdateRogueStateEventPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_update_rogue_state"));
	public static final StreamCodec<FriendlyByteBuf, SUpdateRogueStateEventPacket> STREAM_CODEC;

	public SUpdateRogueStateEventPacket(LivingEntity var1) {
		this(((Boolean)(EntityStatsData.get(var1).map(EntityStatsData::isRogue).orElse(Boolean.valueOf(false)))).booleanValue());
	}
	public void handle(ClientPayloadContext var1) {
		LocalPlayer var2 = Minecraft.getInstance().player;
		SUpdateRogueStateEventPacket var3 = this;
		Consumer<EntityStatsData> var4 = (l0) -> {
			l0.setRogue(this.state);
		};
		((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).ifPresent((Consumer)(var4));
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SUpdateRogueStateEventPacket, Boolean> var0 = SUpdateRogueStateEventPacket::state;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.BOOL,var0,(Function)(((Function<Boolean, SUpdateRogueStateEventPacket>)(SUpdateRogueStateEventPacket::new)))));
	}
}