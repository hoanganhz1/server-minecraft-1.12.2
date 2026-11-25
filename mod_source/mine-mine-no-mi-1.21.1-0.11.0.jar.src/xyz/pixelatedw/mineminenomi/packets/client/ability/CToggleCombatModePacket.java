package xyz.pixelatedw.mineminenomi.packets.client.ability;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SToggleCombatModePacket;

public final record CToggleCombatModePacket(boolean combatMode) {
	// Fields
	public static final CustomPacketPayload.Type<CToggleCombatModePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_toggle_combat_mode"));
	public static final StreamCodec<FriendlyByteBuf, CToggleCombatModePacket> STREAM_CODEC;

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			var3.setCombatMode(this.combatMode);
			ModNetwork.sendTo(new SToggleCombatModePacket(var3.isInCombatMode(),ServerConfig.getAbilityBars()),(Player)(var2));
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<CToggleCombatModePacket, Boolean> var0 = CToggleCombatModePacket::combatMode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.BOOL,var0,(Function)(((Function<Boolean, CToggleCombatModePacket>)(CToggleCombatModePacket::new)))));
	}
}