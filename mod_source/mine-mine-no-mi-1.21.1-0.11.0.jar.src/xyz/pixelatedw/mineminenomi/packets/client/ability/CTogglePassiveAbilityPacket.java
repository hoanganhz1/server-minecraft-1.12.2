package xyz.pixelatedw.mineminenomi.packets.client.ability;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetPauseTickPacket;

public final record CTogglePassiveAbilityPacket(ResourceLocation ability) {
	// Fields
	public static final CustomPacketPayload.Type<CTogglePassiveAbilityPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_toggle_passive_ability"));
	public static final StreamCodec<FriendlyByteBuf, CTogglePassiveAbilityPacket> STREAM_CODEC;

	public CTogglePassiveAbilityPacket(AbilityCore<?> var1) {
		this((ResourceLocation)(var1.getRegistryKey()));
	}
	public void handle(ServerPayloadContext var1) {
		ServerPlayer var6 = null;
		AbilityCore var7 = null;
		ServerPlayer var2 = var1.player();
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			AbilityCore var4 = ((AbilityCore)(WyRegistry.ABILITIES.get(this.ability)));
			if (((var4) != (null)) && ((var4.getType()) == (AbilityType.PASSIVE))){
				IAbility var5 = var3.getPassiveAbility(var4);
				if ((var5) == (null)){
					return;
				}else{
					var6 = var2;
					var7 = var4;
					final ServerPlayer var6_f1 = var6;
					final AbilityCore var7_f2 = var7;
					Consumer<PauseTickComponent> var8 = (l0) -> {
			l0.setPause((LivingEntity)(var6_f1),!(l0.isPaused()));
			ModNetwork.sendToAllTrackingAndSelf(new SSetPauseTickPacket((LivingEntity)(var6_f1),var7_f2,l0.isPaused()),(LivingEntity)(var6_f1));
		};
					((java.util.Optional)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.PAUSE_TICK.get()))))).ifPresent((Consumer)(var8));
					return;
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
		Function<CTogglePassiveAbilityPacket, ResourceLocation> var0 = CTogglePassiveAbilityPacket::ability;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ResourceLocation.STREAM_CODEC,var0,(Function)(((Function<ResourceLocation, CTogglePassiveAbilityPacket>)(CTogglePassiveAbilityPacket::new)))));
	}
}