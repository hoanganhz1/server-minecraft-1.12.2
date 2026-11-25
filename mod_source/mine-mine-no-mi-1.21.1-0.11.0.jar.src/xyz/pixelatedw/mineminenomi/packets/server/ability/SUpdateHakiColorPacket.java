package xyz.pixelatedw.mineminenomi.packets.server.ability;

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
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SUpdateHakiColorPacket(int entityId, int color) {
	// Fields
	public static final CustomPacketPayload.Type<SUpdateHakiColorPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_update_haki_color"));
	public static final StreamCodec<FriendlyByteBuf, SUpdateHakiColorPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof LivingEntity)){
			HakiData var3 = ((HakiData)(((java.util.Optional)(HakiData.get(((LivingEntity)(var2))))).orElse((Object)(null))));
			if ((var3) == (null)){
				return;
			}else{
				var3.setHaoshokuHakiColour(this.color);
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
		Function<SUpdateHakiColorPacket, Integer> var0 = SUpdateHakiColorPacket::entityId;
		Function<SUpdateHakiColorPacket, Integer> var1 = SUpdateHakiColorPacket::color;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,(BiFunction)(((BiFunction<Integer, Integer, SUpdateHakiColorPacket>)(SUpdateHakiColorPacket::new)))));
	}
}