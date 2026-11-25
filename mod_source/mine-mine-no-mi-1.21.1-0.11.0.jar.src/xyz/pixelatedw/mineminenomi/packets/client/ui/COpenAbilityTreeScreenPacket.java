package xyz.pixelatedw.mineminenomi.packets.client.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenAbilityTreeScreenPacket;

public class COpenAbilityTreeScreenPacket implements IServerPacket {
	// Fields
	public static final CustomPacketPayload.Type<COpenAbilityTreeScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_open_ability_tree_screen"));
	public static final COpenAbilityTreeScreenPacket INSTANCE = new COpenAbilityTreeScreenPacket();
	public static final StreamCodec<FriendlyByteBuf, COpenAbilityTreeScreenPacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		Function<EntityStatsData, Boolean> var3 = (l0) -> {
			return Boolean.valueOf((l0.hasRace()) && ((l0.hasFaction()) && (l0.hasFightingStyle())));
		};
		if (!(((Boolean)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).map((Function)(var3)).orElse(Boolean.valueOf(false)))).booleanValue())){
			return;
		}else{
			ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var2)),(Player)(var2));
			ArrayList var4 = new ArrayList();
			ServerPlayer var5 = var2;
			List var6 = var4;
			final ServerPlayer var5_f1 = var5;
			final List var6_f2 = var6;
			Consumer<AbilityData> var7 = (l0) -> {
			Iterator lv2_3 = l0.getNodes().iterator();
			do{
				if (lv2_3.hasNext()){
					AbilityNode lv2_4 = ((AbilityNode)(lv2_3.next()));
					if (lv2_4.canUnlock((LivingEntity)(var5_f1))){
						var6_f2.add(Integer.valueOf(lv2_4.hashCode()));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		};
			((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).ifPresent((Consumer)(var7));
			ModNetwork.sendTo(new SOpenAbilityTreeScreenPacket(AbilityHelper.getTrainingPointsLimit((LivingEntity)(var2)),(List)(var4)),(Player)(var2));
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}