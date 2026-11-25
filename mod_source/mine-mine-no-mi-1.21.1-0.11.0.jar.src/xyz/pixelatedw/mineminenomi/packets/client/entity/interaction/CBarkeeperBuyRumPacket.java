package xyz.pixelatedw.mineminenomi.packets.client.entity.interaction;

import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nInteractions;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SDialogueResponsePacket;

public class CBarkeeperBuyRumPacket implements IServerPacket {
	// Fields
	public static final CustomPacketPayload.Type<CBarkeeperBuyRumPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_barkeeper_buy_rum"));
	private static final CBarkeeperBuyRumPacket INSTANCE = new CBarkeeperBuyRumPacket();
	public static final StreamCodec<FriendlyByteBuf, CBarkeeperBuyRumPacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			int var4 = 100;
			if ((var3.getBelly()) < ((long)(var4))){
				ModNetwork.sendTo(SDialogueResponsePacket.setMessage(ModI18nInteractions.getRandomNoBellyMessage()),(Player)(var2));
				return;
			}else{
				var3.alterBelly((long)(-(var4)),StatChangeSource.STORE);
				var2.addItem(((Item)(ModItems.BOTTLE_OF_RUM.get())).getDefaultInstance());
				ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var2)),(Player)(var2));
				ModNetwork.sendTo(SDialogueResponsePacket.closeDialogue(),(Player)(var2));
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}