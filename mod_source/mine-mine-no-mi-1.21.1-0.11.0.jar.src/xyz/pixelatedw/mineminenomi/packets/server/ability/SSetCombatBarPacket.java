package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.Optional;
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
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SSetCombatBarPacket(int bar) {
	// Fields
	public static final CustomPacketPayload.Type<SSetCombatBarPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_set_combat_bar"));
	public static final StreamCodec<FriendlyByteBuf, SSetCombatBarPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(Minecraft.getInstance().player)))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			var2.setCombatBarSet(this.bar);
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SSetCombatBarPacket, Integer> var0 = SSetCombatBarPacket::bar;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,(Function)(((Function<Integer, SSetCombatBarPacket>)(SSetCombatBarPacket::new)))));
	}
}