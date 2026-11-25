package xyz.pixelatedw.mineminenomi.packets.client.challenge;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public final record CStartChallengePacket(ResourceLocation id, boolean isFree) {
	// Fields
	public static final CustomPacketPayload.Type<CStartChallengePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_start_challenge"));
	public static final StreamCodec<FriendlyByteBuf, CStartChallengePacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, CStartChallengePacket var1) {
		var0.writeResourceLocation(var1.id);
		var0.writeBoolean(var1.isFree);
	}
	public static CStartChallengePacket decode(FriendlyByteBuf var0) {
		ResourceLocation var1 = var0.readResourceLocation();
		boolean var2 = var0.readBoolean();
		return new CStartChallengePacket(var1,var2);
	}
	public void handle(ServerPayloadContext var1) {
		if (!(ServerConfig.isChallengesEnabled())){
			return;
		}else{
			ServerPlayer var2 = var1.player();
			ServerLevel var3 = var2.serverLevel();
			ChallengeData var4 = ((ChallengeData)(((java.util.Optional)(ChallengeData.get((Player)(var2)))).orElse((Object)(null))));
			if ((var4) == (null)){
				return;
			}else{
				ChallengeCore var5 = ((ChallengeCore)(WyRegistry.CHALLENGES.get(this.id)));
				if ((var5) == (null)){
					return;
				}else{
					ArrayList var6 = new ArrayList();
					UnmodifiableIterator var7 = var4.getGroupMembersIds().iterator();
					do{
						if (var7.hasNext()){
							Player var8 = var3.getPlayerByUUID(((UUID)(var7.next())));
							if ((var8) != (null)){
								var6.add(var8);
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					ChallengesWorldData.get().startChallenge(var2,(List)(var6),var5,this.isFree);
					return;
				}
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = CStartChallengePacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(CStartChallengePacket::decode)))));
	}
}