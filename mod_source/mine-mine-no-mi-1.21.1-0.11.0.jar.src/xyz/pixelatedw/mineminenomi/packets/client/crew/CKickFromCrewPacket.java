package xyz.pixelatedw.mineminenomi.packets.client.crew;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.Crew$Member;
import xyz.pixelatedw.mineminenomi.api.events.entity.CrewEvent$Kick;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public final record CKickFromCrewPacket(UUID uuid) {
	// Fields
	public static final CustomPacketPayload.Type<CKickFromCrewPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_kick_from_crew"));
	public static final StreamCodec<FriendlyByteBuf, CKickFromCrewPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, CKickFromCrewPacket var1) {
		var0.writeUUID(var1.uuid);
	}
	public static CKickFromCrewPacket decode(FriendlyByteBuf var0) {
		UUID var1 = var0.readUUID();
		return new CKickFromCrewPacket(var1);
	}
	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		UUID var3 = this.uuid;
		FactionsWorldData var4 = FactionsWorldData.get();
		Crew var5 = var4.getCrewWithCaptain(var2.getUUID());
		Player var6 = var2.level().getPlayerByUUID(var3);
		if (((var6) != (null)) && (((var5) != (null)) && ((var5.hasMember(var3)) && ((!(var5.getCaptain().getUUID().equals(var3))) && (!(((CrewEvent$Kick)(NeoForge.EVENT_BUS.post((Event)(new CrewEvent$Kick(var6,var5))))).isCanceled())))))){
			Object[] var7 = new Object[]{var5.getMember(var3).getUsername()};
			FactionHelper.sendMessageToCrew(var2.level(),var5,(Component)(Component.translatable(ModI18n.CREW_MESSAGE_KICKED,var7)));
			var4.removeCrewMember(var2.level(),var5,var3);
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = CKickFromCrewPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(CKickFromCrewPacket::decode)))));
	}
}