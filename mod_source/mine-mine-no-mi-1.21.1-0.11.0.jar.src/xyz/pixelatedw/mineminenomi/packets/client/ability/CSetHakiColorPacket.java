package xyz.pixelatedw.mineminenomi.packets.client.ability;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateHakiColorPacket;

public final record CSetHakiColorPacket(int color) {
	// Fields
	public static final CustomPacketPayload.Type<CSetHakiColorPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_set_haki_color"));
	public static final StreamCodec<FriendlyByteBuf, CSetHakiColorPacket> STREAM_CODEC;

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		HakiData var3 = ((HakiData)(((java.util.Optional)(HakiData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			var3.setHaoshokuHakiColour(this.color);
			ModNetwork.sendToAllTracking(new SUpdateHakiColorPacket(var2.getId(),this.color),(Entity)(var2));
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<CSetHakiColorPacket, Integer> var0 = CSetHakiColorPacket::color;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,(Function)(((Function<Integer, CSetHakiColorPacket>)(CSetHakiColorPacket::new)))));
	}
}