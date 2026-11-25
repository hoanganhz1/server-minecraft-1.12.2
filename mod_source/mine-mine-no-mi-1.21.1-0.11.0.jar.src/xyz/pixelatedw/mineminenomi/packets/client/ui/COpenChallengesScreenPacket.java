package xyz.pixelatedw.mineminenomi.packets.client.ui;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenChallengesScreenPacket;

public class COpenChallengesScreenPacket implements IServerPacket {
	// Fields
	public static final CustomPacketPayload.Type<COpenChallengesScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_open_challenges_screen"));
	public static final COpenChallengesScreenPacket INSTANCE = new COpenChallengesScreenPacket();
	public static final StreamCodec<FriendlyByteBuf, COpenChallengesScreenPacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		Optional var3 = ChallengeData.get((Player)(var2));
		List var4 = ((List)(var3.map((Function)(((Function<ChallengeData, List>)(ChallengeData::getChallenges)))).orElse(Lists.newArrayList())));
		List var5 = ((List)(var3.map((Function)(((Function<ChallengeData, ImmutableList>)(ChallengeData::getInvitations)))).orElse(ImmutableList.of())));
		ModNetwork.sendTo(new SOpenChallengesScreenPacket(var4,var5),(Player)(var2));
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}