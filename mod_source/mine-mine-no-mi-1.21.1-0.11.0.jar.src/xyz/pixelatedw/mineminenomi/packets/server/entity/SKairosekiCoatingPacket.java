package xyz.pixelatedw.mineminenomi.packets.server.entity;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.KairosekiCoatingData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SKairosekiCoatingPacket(int entityId, int coatingLevel) {
	// Fields
	public static final CustomPacketPayload.Type<SKairosekiCoatingPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_kairoseki_coating"));
	public static final StreamCodec<FriendlyByteBuf, SKairosekiCoatingPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if ((var2) == (null)){
			return;
		}else{
			KairosekiCoatingData var3 = ((KairosekiCoatingData)(((java.util.Optional)(KairosekiCoatingData.get(var2))).orElse((Object)(null))));
			if ((var3) == (null)){
				return;
			}else{
				var3.setCoatingLevel(this.coatingLevel);
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SKairosekiCoatingPacket, Integer> var0 = SKairosekiCoatingPacket::entityId;
		Function<SKairosekiCoatingPacket, Integer> var1 = SKairosekiCoatingPacket::coatingLevel;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,(BiFunction)(((BiFunction<Integer, Integer, SKairosekiCoatingPacket>)(SKairosekiCoatingPacket::new)))));
	}
}