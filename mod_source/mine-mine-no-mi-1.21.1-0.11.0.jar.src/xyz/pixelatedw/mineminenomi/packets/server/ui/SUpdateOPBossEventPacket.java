package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.Optional;
import java.util.UUID;
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
import xyz.pixelatedw.mineminenomi.api.entities.ClientBossExtraEvent;
import xyz.pixelatedw.mineminenomi.data.entity.CombatData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SUpdateOPBossEventPacket(UUID id, int totalBars, int activeBars) {
	// Fields
	public static final CustomPacketPayload.Type<SUpdateOPBossEventPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_update_op_boss_event"));
	public static final StreamCodec<FriendlyByteBuf, SUpdateOPBossEventPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, SUpdateOPBossEventPacket var1) {
		var0.writeUUID(var1.id);
		var0.writeInt(var1.totalBars);
		var0.writeInt(var1.activeBars);
	}
	public static SUpdateOPBossEventPacket decode(FriendlyByteBuf var0) {
		UUID var1 = var0.readUUID();
		int var2 = var0.readInt();
		int var3 = var0.readInt();
		return new SUpdateOPBossEventPacket(var1,var2,var3);
	}
	public void handle(ClientPayloadContext var1) {
		LocalPlayer var2 = Minecraft.getInstance().player;
		ClientBossExtraEvent var3 = new ClientBossExtraEvent();
		var3.setTotalBars(this.totalBars);
		var3.setActiveBars(this.activeBars);
		CombatData var4 = ((CombatData)(((java.util.Optional)(CombatData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var4) != (null)){
			var4.addExtraBossInfo(this.id,var3);
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SUpdateOPBossEventPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SUpdateOPBossEventPacket::decode)))));
	}
}