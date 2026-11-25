package xyz.pixelatedw.mineminenomi.packets.client.crew;

import java.util.List;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
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
import xyz.pixelatedw.mineminenomi.api.events.entity.CrewEvent$Leave;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public class CLeaveCrewPacket implements IServerPacket {
	// Fields
	public static final CustomPacketPayload.Type<CLeaveCrewPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_leave_crew"));
	private static final CLeaveCrewPacket INSTANCE = new CLeaveCrewPacket();
	public static final StreamCodec<FriendlyByteBuf, CLeaveCrewPacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		UUID var3 = var2.getUUID();
		FactionsWorldData var4 = FactionsWorldData.get();
		Crew var5 = var4.getCrewWithMember(var3);
		if (((var5) != (null)) && (!(((CrewEvent$Leave)(NeoForge.EVENT_BUS.post((Event)(new CrewEvent$Leave((Player)(var2),var5))))).isCanceled()))){
			boolean var6 = false;
			if ((((var5.getMembers().size()) == (1)) ? (1) : (0)) == (0)){
				Object[] var7 = new Object[]{var2.getName().getString()};
				FactionHelper.sendMessageToCrew(var2.level(),var5,(Component)(Component.translatable(ModI18n.CREW_MESSAGE_LEFT,var7)));
				if (var5.getCaptain().getUUID().equals(var3)){
					var6 = true;
				}
			}
			var4.removeCrewMember(var2.level(),var5,var3);
			if ((var6) && ((var5.getCaptain()) != (null))){
				Object[] var7_1 = new Object[]{var5.getCaptain().getUsername()};
				FactionHelper.sendMessageToCrew(var2.level(),var5,(Component)(Component.translatable(ModI18n.CREW_MESSAGE_NEW_CAPTAIN,var7_1)));
			}else{
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}