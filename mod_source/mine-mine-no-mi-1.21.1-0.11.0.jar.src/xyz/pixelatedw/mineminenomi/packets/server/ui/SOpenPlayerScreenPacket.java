package xyz.pixelatedw.mineminenomi.packets.server.ui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.PlayerStatsScreen;

public final record SOpenPlayerScreenPacket(double doriki, boolean hasQuests, int questAmount, boolean hasChallenges, int challengeAmount, boolean isInCombat, boolean isInChallengeDimension, int invites, boolean hasCrew) {
	// Fields
	public static final CustomPacketPayload.Type<SOpenPlayerScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_player_screen"));
	public static final StreamCodec<FriendlyByteBuf, SOpenPlayerScreenPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, SOpenPlayerScreenPacket var1) {
		var0.writeDouble(var1.doriki);
		var0.writeBoolean(var1.hasQuests);
		var0.writeInt(var1.questAmount);
		var0.writeBoolean(var1.hasChallenges);
		var0.writeInt(var1.challengeAmount);
		var0.writeBoolean(var1.isInCombat);
		var0.writeBoolean(var1.isInChallengeDimension);
		var0.writeInt(var1.invites);
		var0.writeBoolean(var1.hasCrew);
	}
	public static SOpenPlayerScreenPacket decode(FriendlyByteBuf var0) {
		double var1 = var0.readDouble();
		boolean var2 = var0.readBoolean();
		int var3 = var0.readInt();
		boolean var4 = var0.readBoolean();
		int var5 = var0.readInt();
		boolean var6 = var0.readBoolean();
		boolean var7 = var0.readBoolean();
		int var8 = var0.readInt();
		boolean var9 = var0.readBoolean();
		return new SOpenPlayerScreenPacket(var1,var2,var3,var4,var5,var6,var7,var8,var9);
	}
	public void handle(ClientPayloadContext var1) {
		PlayerStatsScreen.open(this.doriki,this.hasQuests,this.questAmount,this.hasChallenges,this.challengeAmount,this.isInCombat,this.isInChallengeDimension,this.invites,this.hasCrew);
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SOpenPlayerScreenPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SOpenPlayerScreenPacket::decode)))));
	}
}