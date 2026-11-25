package xyz.pixelatedw.mineminenomi.packets.server.challenge;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.ChallengesScreen;

public final record SUpdateChallengeGroupPacket(UUID memberId, int groupSlot) {
	// Fields
	public static final CustomPacketPayload.Type<SUpdateChallengeGroupPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_update_challenge_group"));
	public static final StreamCodec<FriendlyByteBuf, SUpdateChallengeGroupPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, SUpdateChallengeGroupPacket var1) {
		var0.writeUUID(var1.memberId);
		var0.writeInt(var1.groupSlot);
	}
	public static SUpdateChallengeGroupPacket decode(FriendlyByteBuf var0) {
		UUID var1 = var0.readUUID();
		int var2 = var0.readInt();
		return new SUpdateChallengeGroupPacket(var1,var2);
	}
	public void handle(ClientPayloadContext var1) {
		Minecraft var2 = Minecraft.getInstance();
		if ((var2.player) == (null)){
			return;
		}else{
			Player var3 = var2.level.getPlayerByUUID(this.memberId);
			if ((var3) == (null)){
				return;
			}else{
				Screen var4 = var2.screen;
				if (var4 instanceof ChallengesScreen){
					ChallengesScreen var5 = ((ChallengesScreen)(var4));
					var5.setGroupMember(this.groupSlot,(LivingEntity)(var3));
					return;
				}else{
					return;
				}
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SUpdateChallengeGroupPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SUpdateChallengeGroupPacket::decode)))));
	}
}