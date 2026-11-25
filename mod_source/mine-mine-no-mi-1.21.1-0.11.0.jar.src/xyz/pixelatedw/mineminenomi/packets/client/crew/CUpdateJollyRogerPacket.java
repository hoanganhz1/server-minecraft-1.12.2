package xyz.pixelatedw.mineminenomi.packets.client.crew;

import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.events.JollyRogerEvent$Update;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public final record CUpdateJollyRogerPacket(JollyRoger jollyRoger) {
	// Fields
	public static final CustomPacketPayload.Type<CUpdateJollyRogerPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_update_jolly_roger"));
	public static final StreamCodec<FriendlyByteBuf, CUpdateJollyRogerPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, CUpdateJollyRogerPacket var1) {
		var0.writeNbt((Tag)(var1.jollyRoger.write()));
	}
	public static CUpdateJollyRogerPacket decode(FriendlyByteBuf var0) {
		JollyRoger var1 = JollyRoger.from(var0.readNbt());
		return new CUpdateJollyRogerPacket(var1);
	}
	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		UUID var3 = var2.getUUID();
		FactionsWorldData var4 = FactionsWorldData.get();
		Crew var5 = var4.getCrewWithCaptain(var3);
		if ((var5) != (null)){
			NeoForge.EVENT_BUS.post((Event)(new JollyRogerEvent$Update(this.jollyRoger,var5)));
			var4.updateCrewJollyRoger(var2,var5,this.jollyRoger);
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = CUpdateJollyRogerPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(CUpdateJollyRogerPacket::decode)))));
	}
}