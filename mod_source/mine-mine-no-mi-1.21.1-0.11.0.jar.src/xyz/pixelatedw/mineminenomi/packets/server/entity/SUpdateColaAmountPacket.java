package xyz.pixelatedw.mineminenomi.packets.server.entity;

import com.mojang.datafixers.util.Function4;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SUpdateColaAmountPacket(int entityId, int cola, int maxCola, int ultraCola) {
	// Fields
	public static final CustomPacketPayload.Type<SUpdateColaAmountPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_update_cola_amount"));
	public static final StreamCodec<FriendlyByteBuf, SUpdateColaAmountPacket> STREAM_CODEC;

	public SUpdateColaAmountPacket(LivingEntity var1) {
		this(var1.getId(),fetchCola(var1),fetchMaxCola(var1),fetchUltraCola(var1));
	}
	private static int fetchCola(LivingEntity var0) {
		return ((Integer)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Integer>)(EntityStatsData::getCola)))).orElse(Integer.valueOf(0)))).intValue();
	}
	private static int fetchMaxCola(LivingEntity var0) {
		return ((Integer)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Integer>)(EntityStatsData::getMaxCola)))).orElse(Integer.valueOf(0)))).intValue();
	}
	private static int fetchUltraCola(LivingEntity var0) {
		return ((Integer)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Integer>)(EntityStatsData::getUltraCola)))).orElse(Integer.valueOf(0)))).intValue();
	}
	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof LivingEntity)){
			LivingEntity var3 = ((LivingEntity)(var2));
			EntityStatsData var4 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var3))).orElse((Object)(null))));
			if ((var4) == (null)){
				return;
			}else{
				int var5 = Mth.clamp(this.cola,0,this.maxCola);
				var4.setUltraCola(this.ultraCola);
				var4.setCola(var5);
				return;
			}
		}else{
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SUpdateColaAmountPacket, Integer> var0 = SUpdateColaAmountPacket::entityId;
		Function<SUpdateColaAmountPacket, Integer> var1 = SUpdateColaAmountPacket::cola;
		Function<SUpdateColaAmountPacket, Integer> var2 = SUpdateColaAmountPacket::maxCola;
		Function<SUpdateColaAmountPacket, Integer> var3 = SUpdateColaAmountPacket::ultraCola;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,ByteBufCodecs.INT,var2,ByteBufCodecs.INT,var3,(Function4)(((com.mojang.datafixers.util.Function4)(SUpdateColaAmountPacket::new)))));
	}
}