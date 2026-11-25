package xyz.pixelatedw.mineminenomi.packets.client.challenge;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public class CDisbandChallengeGroupPacket implements IServerPacket {
	// Fields
	public static final CustomPacketPayload.Type<CDisbandChallengeGroupPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_disband_challenge_group"));
	private static final CDisbandChallengeGroupPacket INSTANCE = new CDisbandChallengeGroupPacket();
	public static final StreamCodec<FriendlyByteBuf, CDisbandChallengeGroupPacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ServerPayloadContext var1) {
		if (!(ServerConfig.isChallengesEnabled())){
			return;
		}else{
			ServerPlayer var2 = var1.player();
			ServerLevel var3 = var2.serverLevel();
			ChallengeData var4 = ((ChallengeData)(((java.util.Optional)(ChallengeData.get((Player)(var2)))).orElse((Object)(null))));
			if (((var4) != (null)) && ((!(var4.getGroupMembersIds().isEmpty())) && (!(var4.isInGroup())))){
				WyHelper.sendMessage((LivingEntity)(var2),ModI18nChallenges.MESSAGE_GROUP_DISBAND,true);
				var4.setInGroup((UUID)(null));
				UnmodifiableIterator var5 = var4.getGroupMembersIds().iterator();
				do{
					if (var5.hasNext()){
						UUID var6 = ((UUID)(var5.next()));
						Player var7 = var3.getPlayerByUUID(var6);
						if ((var7) == (null)){
							continue;
						}else{
							WyHelper.sendMessage((LivingEntity)(var7),ModI18nChallenges.MESSAGE_GROUP_DISBAND,true);
							Consumer<ChallengeData> var8 = (l0) -> {
			l0.setInGroup((UUID)(null));
		};
							((java.util.Optional)(ChallengeData.get(var7))).ifPresent((Consumer)(var8));
							var4.removeGroupMember(var6);
							continue;
						}
					}else{
						break;
					}
				} while (true);
				return;
			}else{
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}