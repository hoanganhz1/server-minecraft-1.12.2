package xyz.pixelatedw.mineminenomi.packets.client.challenge;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.authlib.GameProfile;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.challenge.SUpdateChallengeGroupPacket;

public final record CAcceptChallengeInvitationPacket(UUID id, int groupSlot) {
	// Fields
	public static final CustomPacketPayload.Type<CAcceptChallengeInvitationPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_accept_challenge_invitation"));
	public static final StreamCodec<FriendlyByteBuf, CAcceptChallengeInvitationPacket> STREAM_CODEC;

	public CAcceptChallengeInvitationPacket(ChallengeInvitation var1) {
		this(var1.getSenderId(),var1.getGroupSlot());
	}
	public static void encode(FriendlyByteBuf var0, CAcceptChallengeInvitationPacket var1) {
		var0.writeUUID(var1.id);
		var0.writeInt(var1.groupSlot);
	}
	public static CAcceptChallengeInvitationPacket decode(FriendlyByteBuf var0) {
		UUID var1 = var0.readUUID();
		int var2 = var0.readInt();
		return new CAcceptChallengeInvitationPacket(var1,var2);
	}
	public void handle(ServerPayloadContext var1) {
		if (!(ServerConfig.isChallengesEnabled())){
			return;
		}else{
			ServerPlayer var2 = var1.player();
			ServerLevel var3 = var2.serverLevel();
			ChallengeData var4 = ((ChallengeData)(((java.util.Optional)(ChallengeData.get((Player)(var2)))).orElse((Object)(null))));
			if (((var4) != (null)) && ((!(var4.isInGroup())) && (!(WyHelper.isInCombat((LivingEntity)(var2)))))){
				Optional var5 = var4.getInvitationFrom(this.id);
				if (!(var5.isPresent())){
					return;
				}else{
					ChallengeInvitation var6 = ((ChallengeInvitation)(var5.get()));
					Player var7 = var3.getPlayerByUUID(var6.getSenderId());
					if (((var7) != (null)) && (var7.isAlive())){
						if (var6.isExpired((Level)(var3))){
							return;
						}else{
							if ((ChallengesWorldData.get().getInProgressChallengeFor((LivingEntity)(var2))) != (null)){
								return;
							}else{
								ChallengeData var8 = ((ChallengeData)(((java.util.Optional)(ChallengeData.get(var7))).orElse((Object)(null))));
								if ((var8) == (null)){
									return;
								}else{
									Object[] var9 = new Object[]{var2.getGameProfile().getName()};
									MutableComponent var10 = Component.translatable(ModI18nChallenges.MESSAGE_GROUP_JOIN,var9);
									WyHelper.sendMessage((LivingEntity)(var7),(Component)(var10),true);
									UnmodifiableIterator var11 = var8.getGroupMembersIds().iterator();
									do{
										if (var11.hasNext()){
											WyHelper.sendMessage((LivingEntity)(var3.getPlayerByUUID(((UUID)(var11.next())))),(Component)(var10),true);
											continue;
										}else{
											break;
										}
									} while (true);
									var8.addGroupMember((Player)(var2));
									var4.setInGroup(var7.getUUID());
									var4.removeInvitationFrom(var7);
									ModNetwork.sendTo(new SUpdateChallengeGroupPacket(var2.getUUID(),this.groupSlot),var7);
									return;
								}
							}
						}
					}else{
						return;
					}
				}
			}else{
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = CAcceptChallengeInvitationPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(CAcceptChallengeInvitationPacket::decode)))));
	}
}