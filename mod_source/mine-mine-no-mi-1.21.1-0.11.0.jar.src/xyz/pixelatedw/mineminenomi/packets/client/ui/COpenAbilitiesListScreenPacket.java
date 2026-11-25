package xyz.pixelatedw.mineminenomi.packets.client.ui;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenAbilitySelectionScreenPacket;

public class COpenAbilitiesListScreenPacket implements IServerPacket {
	// Fields
	public static final CustomPacketPayload.Type<COpenAbilitiesListScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_open_abilities_list_screen"));
	public static final COpenAbilitiesListScreenPacket INSTANCE = new COpenAbilitiesListScreenPacket();
	public static final StreamCodec<FriendlyByteBuf, COpenAbilitiesListScreenPacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		Function<EntityStatsData, Boolean> var3 = (l0) -> {
			return Boolean.valueOf((l0.hasRace()) && ((l0.hasFaction()) && (l0.hasFightingStyle())));
		};
		if (!(((Boolean)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).map((Function)(var3)).orElse(Boolean.valueOf(false)))).booleanValue())){
			return;
		}else{
			ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var2)),(Player)(var2));
			ModNetwork.sendTo(new SOpenAbilitySelectionScreenPacket(),(Player)(var2));
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}