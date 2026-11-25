package xyz.pixelatedw.mineminenomi.packets.client.ability;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityCanUseEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public final record CUseAbilityPacket(int slot) {
	// Fields
	public static final CustomPacketPayload.Type<CUseAbilityPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_use_ability"));
	public static final StreamCodec<FriendlyByteBuf, CUseAbilityPacket> STREAM_CODEC;

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		var2.getCommandSenderWorld().getProfiler().push("abilityUse");
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			Ability var4 = ((Ability)(var3.getEquippedAbility(this.slot)));
			if (((var4) != (null)) && (!(var2.isSpectator()))){
				if (((AbilityCanUseEvent)(NeoForge.EVENT_BUS.post((Event)(new AbilityCanUseEvent((Player)(var2),(IAbility)(var4)))))).isCanceled()){
					return;
				}else{
					if ((this.slot) > ((8) * (ServerConfig.getAbilityBars()))){
						return;
					}else{
						var4.use((LivingEntity)(var2));
						var2.getCommandSenderWorld().getProfiler().pop();
						return;
					}
				}
			}else{
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<CUseAbilityPacket, Integer> var0 = CUseAbilityPacket::slot;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,(Function)(((Function<Integer, CUseAbilityPacket>)(CUseAbilityPacket::new)))));
	}
}