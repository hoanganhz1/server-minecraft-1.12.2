package xyz.pixelatedw.mineminenomi.packets.client.ui;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCrewScreenPacket;

public class COpenCrewScreenPacket implements IServerPacket {
	// Fields
	public static final CustomPacketPayload.Type<COpenCrewScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_open_crew_screen"));
	public static final COpenCrewScreenPacket INSTANCE = new COpenCrewScreenPacket();
	public static final StreamCodec<FriendlyByteBuf, COpenCrewScreenPacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		Crew var3 = FactionsWorldData.get().getCrewWithMember(var2.getUUID());
		if ((var3) == (null)){
			return;
		}else{
			ModNetwork.sendTo(new SOpenCrewScreenPacket(var3),(Player)(var2));
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}