package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.handlers.ui.CombatModeHandler;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SToggleCombatModePacket(boolean combatMode, int bars) {
	// Fields
	public static final CustomPacketPayload.Type<SToggleCombatModePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_toggle_combat_mode"));
	public static final StreamCodec<FriendlyByteBuf, SToggleCombatModePacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		LocalPlayer var2 = Minecraft.getInstance().player;
		if ((var2) == (null)){
			return;
		}else{
			SToggleCombatModePacket var3 = this;
			Consumer<EntityStatsData> var4 = (l0) -> {
			l0.setCombatMode(this.combatMode);
		};
			((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).ifPresent((Consumer)(var4));
			CombatModeHandler.abilityBars = this.bars;
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SToggleCombatModePacket, Boolean> var0 = SToggleCombatModePacket::combatMode;
		Function<SToggleCombatModePacket, Integer> var1 = SToggleCombatModePacket::bars;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.BOOL,var0,ByteBufCodecs.INT,var1,(BiFunction)(((BiFunction<Boolean, Integer, SToggleCombatModePacket>)(SToggleCombatModePacket::new)))));
	}
}