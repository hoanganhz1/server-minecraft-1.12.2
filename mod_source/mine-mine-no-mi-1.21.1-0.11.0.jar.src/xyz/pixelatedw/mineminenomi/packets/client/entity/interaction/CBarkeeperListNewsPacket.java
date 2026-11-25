package xyz.pixelatedw.mineminenomi.packets.client.entity.interaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.poi.NTEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.NewsEntry;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.NPCWorldData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nInteractions;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SDialogueResponsePacket;

public class CBarkeeperListNewsPacket implements IServerPacket {
	// Fields
	public static final CustomPacketPayload.Type<CBarkeeperListNewsPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_barkeeper_list_news"));
	private static final CBarkeeperListNewsPacket INSTANCE = new CBarkeeperListNewsPacket();
	public static final StreamCodec<FriendlyByteBuf, CBarkeeperListNewsPacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		NPCWorldData var3 = NPCWorldData.get();
		List var4 = ((List)(((java.util.stream.Stream)(EventsWorldData.get().getNotoriousTargets().stream().map((Function)(((Function<NTEventTarget, TrackedNPC>)(NTEventTarget::getTrackedNPC)))))).collect((Collector)(Collectors.toList()))));
		TrackedNPC var5 = null;
		if (((var4) != (null)) && (!(var4.isEmpty()))){
			Collections.shuffle(var4);
			var5 = ((TrackedNPC)(var4.get(0)));
		}else{
			ArrayList var6 = new ArrayList((Collection)(var3.getTrackedMobs()));
			Collections.shuffle((List)(var6));
			var5 = ((TrackedNPC)(var6.get(0)));
		}
		if ((var5) != (null)){
			NewsEntry var6_1 = var5.getNewsEntry(var2.level());
			if ((var6_1) != (null)){
				if ((var6_1.getMessage()) == (null)){
					ModNetwork.sendTo(SDialogueResponsePacket.setMessage(ModI18nInteractions.getRandomNothingNewMessage()),(Player)(var2));
					return;
				}else{
					ModNetwork.sendTo(SDialogueResponsePacket.setMessage(var6_1.getMessage()),(Player)(var2));
				}
			}else{
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}