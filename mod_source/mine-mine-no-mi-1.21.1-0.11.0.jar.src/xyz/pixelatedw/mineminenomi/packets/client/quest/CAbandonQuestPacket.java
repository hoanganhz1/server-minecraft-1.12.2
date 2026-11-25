package xyz.pixelatedw.mineminenomi.packets.client.quest;

import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;

public final record CAbandonQuestPacket(QuestId<?> questId) {
	// Fields
	public static final CustomPacketPayload.Type<CAbandonQuestPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_abandon_quest"));
	public static final StreamCodec<FriendlyByteBuf, CAbandonQuestPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, CAbandonQuestPacket var1) {
		var0.writeResourceLocation(var1.questId.getRegistryKey());
	}
	public static CAbandonQuestPacket decode(FriendlyByteBuf var0) {
		QuestId var1 = ((QuestId)(WyRegistry.QUESTS.get(var0.readResourceLocation())));
		return new CAbandonQuestPacket(var1);
	}
	public void handle(ServerPayloadContext var1) {
		if ((this.questId) == (null)){
			return;
		}else{
			ServerPlayer var2 = var1.player();
			QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get((Player)(var2)))).orElse((Object)(null))));
			if ((var3) == (null)){
				return;
			}else{
				var3.removeInProgressQuest(this.questId);
				ModNetwork.sendTo(new SSyncQuestDataPacket((Player)(var2),(QuestData)(var3)),(Player)(var2));
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = CAbandonQuestPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(CAbandonQuestPacket::decode)))));
	}
}