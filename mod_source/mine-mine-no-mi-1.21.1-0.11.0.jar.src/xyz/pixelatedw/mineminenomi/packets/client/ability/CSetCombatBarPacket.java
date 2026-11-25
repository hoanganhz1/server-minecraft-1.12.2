package xyz.pixelatedw.mineminenomi.packets.client.ability;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public final record CSetCombatBarPacket(int bar) {
	// Fields
	public static final CustomPacketPayload.Type<CSetCombatBarPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_set_combat_bar"));
	public static final StreamCodec<FriendlyByteBuf, CSetCombatBarPacket> STREAM_CODEC;

	public void handle(ServerPayloadContext var1) {
		if ((ServerConfig.getAbilityBars()) < ((this.bar) + (1))){
			return;
		}else{
			AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var1.player())))).orElse((Object)(null))));
			if ((var2) == (null)){
				return;
			}else{
				var2.setCombatBarSet(this.bar);
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<CSetCombatBarPacket, Integer> var0 = CSetCombatBarPacket::bar;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,(Function)(((Function<Integer, CSetCombatBarPacket>)(CSetCombatBarPacket::new)))));
	}
}