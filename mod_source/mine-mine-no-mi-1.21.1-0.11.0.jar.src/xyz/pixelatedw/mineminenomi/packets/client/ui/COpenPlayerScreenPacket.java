package xyz.pixelatedw.mineminenomi.packets.client.ui;

import com.google.common.collect.ImmutableList;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.CombatData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCharacterCreatorScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenPlayerScreenPacket;

public class COpenPlayerScreenPacket implements IServerPacket {
	// Fields
	public static final CustomPacketPayload.Type<COpenPlayerScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_open_player_screen"));
	public static final COpenPlayerScreenPacket INSTANCE = new COpenPlayerScreenPacket();
	public static final StreamCodec<FriendlyByteBuf, COpenPlayerScreenPacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			if ((var3.hasRace()) && ((var3.hasFaction()) && (var3.hasFightingStyle()))){
				Optional var4 = QuestData.get((Player)(var2));
				Optional var5 = ChallengeData.get((Player)(var2));
				Optional var6 = CombatData.get((LivingEntity)(var2));
				double var7 = var3.getDoriki();
				boolean var8 = ServerConfig.isQuestsEnabled();
				int var9 = ((Integer)(var4.map((Function)(((Function<QuestData, Integer>)(QuestData::countInProgressQuests)))).orElse(Integer.valueOf(0)))).intValue();
				boolean var10 = ServerConfig.isChallengesEnabled();
				int var11 = ((Integer)(var5.map((Function)(((Function<ChallengeData, Integer>)(ChallengeData::countChallenges)))).orElse(Integer.valueOf(0)))).intValue();
				boolean var12 = ((Boolean)(var6.map((Function)(((Function<CombatData, Boolean>)(CombatData::isInCombatCache)))).orElse(Boolean.valueOf(false)))).booleanValue();
				boolean var13 = NuWorld.isChallengeDimension(var2.level());
				Function<ChallengeData, Integer> var14 = (l0) -> {
			return Integer.valueOf(l0.getInvitations().size());
		};
				int var15 = ((Integer)(((java.util.Optional)(var5)).map((Function)(var14)).orElse(Integer.valueOf(0)))).intValue();
				int var16 = ((FactionsWorldData.get().getCrewWithMember(var2.getUUID())) != (null)) ? (1) : (0);
				ModNetwork.sendTo(new SOpenPlayerScreenPacket(var7,var8,var9,var10,var11,var12,var13,var15,(var16) != (0)),(Player)(var2));
			}else{
				boolean var4_1 = ServerConfig.getRaceRandomizer();
				boolean var5_1 = ServerConfig.getAllowSubRaceSelect();
				ModNetwork.sendTo(new SOpenCharacterCreatorScreenPacket(var4_1,var5_1),(Player)(var2));
			}
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}