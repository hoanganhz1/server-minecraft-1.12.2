package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.QuestsTrackerScreen;

public final record SOpenQuestTrackerScreenPacket(Quest[] quests) {
	// Fields
	public static final CustomPacketPayload.Type<SOpenQuestTrackerScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_quest_tracker_screen"));
	public static final StreamCodec<FriendlyByteBuf, SOpenQuestTrackerScreenPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, SOpenQuestTrackerScreenPacket var1) {
		var0.writeInt((int)(Arrays.stream(var1.quests).filter((Predicate)(((Predicate<Quest>)(Objects::nonNull)))).count()));
		Quest[] var2 = var1.quests;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				Quest var5 = var2[var4];
				if ((var5) != (null)){
					CompoundTag var6 = new CompoundTag();
					CompoundTag var7 = var5.save(var6);
					var0.writeNbt((Tag)(var7));
				}
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static SOpenQuestTrackerScreenPacket decode(FriendlyByteBuf var0) {
		int var1 = var0.readInt();
		Quest[] var2 = new Quest[var1];
		int var3 = 0;
		do{
			if ((var3) < (var1)){
				CompoundTag var4 = var0.readNbt();
				if (!(var4.isEmpty())){
					var2[var3] = Quest.from(var4);
				}
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		return new SOpenQuestTrackerScreenPacket(var2);
	}
	public void handle(ClientPayloadContext var1) {
		LocalPlayer var2 = Minecraft.getInstance().player;
		Minecraft.getInstance().setScreen((Screen)(new QuestsTrackerScreen((Player)(var2),this.quests)));
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SOpenQuestTrackerScreenPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SOpenQuestTrackerScreenPacket::decode)))));
	}
}