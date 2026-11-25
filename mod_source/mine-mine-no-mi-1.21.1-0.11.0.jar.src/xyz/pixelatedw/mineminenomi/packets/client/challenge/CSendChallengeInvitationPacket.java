package xyz.pixelatedw.mineminenomi.packets.client.challenge;

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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public final record CSendChallengeInvitationPacket(UUID targetId, ResourceLocation challengeId, int slotId) {
	// Fields
	public static final CustomPacketPayload.Type<CSendChallengeInvitationPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_send_challenge_invitation"));
	public static final StreamCodec<FriendlyByteBuf, CSendChallengeInvitationPacket> STREAM_CODEC;
	private static final TargetPredicate TARGET_PICKER;

	public CSendChallengeInvitationPacket(Player var1, ChallengeCore<?> var2, int var3) {
		this((UUID)(var1.getUUID()),(ResourceLocation)(var2.getRegistryKey()),var3);
	}
	public static void encode(FriendlyByteBuf var0, CSendChallengeInvitationPacket var1) {
		var0.writeUUID(var1.targetId);
		var0.writeResourceLocation(var1.challengeId);
		var0.writeInt(var1.slotId);
	}
	public static CSendChallengeInvitationPacket decode(FriendlyByteBuf var0) {
		UUID var1 = var0.readUUID();
		ResourceLocation var2 = var0.readResourceLocation();
		int var3 = var0.readInt();
		return new CSendChallengeInvitationPacket((UUID)(var1),(ResourceLocation)(var2),var3);
	}
	public void handle(ServerPayloadContext var1) {
		if (!(ServerConfig.isChallengesEnabled())){
			return;
		}else{
			ServerPlayer var2 = var1.player();
			ServerLevel var3 = var2.serverLevel();
			Player var4 = var3.getPlayerByUUID(this.targetId);
			if (((var4) != (null)) && (var4.isAlive())){
				if (!(TARGET_PICKER.test((LivingEntity)(var2),(Entity)(var4)))){
					return;
				}else{
					if (WyHelper.isInCombat((LivingEntity)(var4))){
						return;
					}else{
						ChallengeCore var5 = ((ChallengeCore)(WyRegistry.CHALLENGES.get(this.challengeId)));
						if ((var5) == (null)){
							return;
						}else{
							ChallengeData var6 = ((ChallengeData)(((java.util.Optional)(ChallengeData.get(var4))).orElse((Object)(null))));
							if (((var6) != (null)) && (!(var6.isInGroup()))){
								if (!(var6.hasInvitationFrom((Player)(var2)))){
									var6.addInvitation(new ChallengeInvitation(var2.getUUID(),var5,var3.getGameTime(),this.slotId));
									Object[] var7 = new Object[]{var2.getGameProfile().getName(),var5.getLocalizedObjective().getString()};
									WyHelper.sendMessage((LivingEntity)(var4),(Component)(Component.translatable(ModI18nChallenges.MESSAGE_INVITATION,var7)),true);
								}
								return;
							}else{
								return;
							}
						}
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
		StreamEncoder var0 = CSendChallengeInvitationPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(CSendChallengeInvitationPacket::decode)))));
		TARGET_PICKER = new TargetPredicate().testFriendlyFaction();
	}
}