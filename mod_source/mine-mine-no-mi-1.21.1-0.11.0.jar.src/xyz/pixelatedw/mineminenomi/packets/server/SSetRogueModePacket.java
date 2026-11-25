package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SSetRogueModePacket(boolean isInRogue) {
	// Fields
	public static final CustomPacketPayload.Type<SSetRogueModePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_set_rogue_mode"));
	public static final StreamCodec<FriendlyByteBuf, SSetRogueModePacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, SSetRogueModePacket var1) {
		var0.writeBoolean(var1.isInRogue);
	}
	public static SSetRogueModePacket decode(FriendlyByteBuf var0) {
		boolean var1 = var0.readBoolean();
		return new SSetRogueModePacket(var1);
	}
	public void handle(ClientPayloadContext var1) {
		LocalPlayer var2 = Minecraft.getInstance().player;
		if ((var2) == (null)){
			return;
		}else{
			EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
			if ((var3) == (null)){
				return;
			}else{
				var3.setRogue(this.isInRogue);
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SSetRogueModePacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SSetRogueModePacket::decode)))));
	}
}