package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.ChallengesScreen;

public final record SOpenChallengesScreenPacket(List<Challenge> challenges, List<ChallengeInvitation> invites) {
	// Fields
	public static final CustomPacketPayload.Type<SOpenChallengesScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_challenges_screen"));
	public static final StreamCodec<FriendlyByteBuf, SOpenChallengesScreenPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, SOpenChallengesScreenPacket var1) {
		var0.writeInt(var1.challenges.size());
		Iterator var2 = var1.challenges.iterator();
		do{
			if (var2.hasNext()){
				Challenge var3 = ((Challenge)(var2.next()));
				var0.writeResourceLocation(var3.getCore().getRegistryKey());
				var0.writeNbt((Tag)(var3.save(new CompoundTag())));
				continue;
			}else{
				break;
			}
		} while (true);
		var0.writeInt(var1.invites.size());
		Iterator var3_1 = var1.invites.iterator();
		do{
			if (var3_1.hasNext()){
				ChallengeInvitation var4 = ((ChallengeInvitation)(var3_1.next()));
				var0.writeUUID(var4.getSenderId());
				var0.writeResourceLocation(var4.getChallenge().getRegistryKey());
				var0.writeLong(var4.getSendTime());
				var0.writeInt(var4.getGroupSlot());
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static SOpenChallengesScreenPacket decode(FriendlyByteBuf var0) {
		ArrayList var1 = new ArrayList();
		int var2 = var0.readInt();
		int var3 = 0;
		do{
			if ((var3) < (var2)){
				ChallengeCore var4 = ((ChallengeCore)(WyRegistry.CHALLENGES.get(var0.readResourceLocation())));
				if ((var4) == (null)){

				}else{
					Challenge var5 = var4.createChallenge();
					var5.load(var0.readNbt());
					var1.add(var5);
				}
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		ArrayList var4_1 = new ArrayList();
		var2 = var0.readInt();
		int var5_1 = 0;
		do{
			if ((var5_1) < (var2)){
				UUID var6 = var0.readUUID();
				ChallengeCore var7 = ((ChallengeCore)(WyRegistry.CHALLENGES.get(var0.readResourceLocation())));
				long var8 = var0.readLong();
				int var9 = var0.readInt();
				var4_1.add(new ChallengeInvitation(var6,var7,var8,var9));
				var5_1++;
				continue;
			}else{
				break;
			}
		} while (true);
		return new SOpenChallengesScreenPacket((List)(var1),(List)(var4_1));
	}
	public void handle(ClientPayloadContext var1) {
		Minecraft.getInstance().setScreen((Screen)(new ChallengesScreen(this.challenges,this.invites)));
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SOpenChallengesScreenPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SOpenChallengesScreenPacket::decode)))));
	}
}