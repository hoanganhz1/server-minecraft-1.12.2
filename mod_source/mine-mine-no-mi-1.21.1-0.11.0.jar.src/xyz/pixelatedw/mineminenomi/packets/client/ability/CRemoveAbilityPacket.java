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
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SRemoveAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateEquippedAbilityPacket;

public final record CRemoveAbilityPacket(int slot) {
	// Fields
	public static final CustomPacketPayload.Type<CRemoveAbilityPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_remove_ability"));
	public static final StreamCodec<FriendlyByteBuf, CRemoveAbilityPacket> STREAM_CODEC;

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			IAbility var4 = var3.getEquippedAbility(this.slot);
			if ((var4) != (null)){
				if (((Boolean)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).map((Function)(((Function<CooldownComponent, Boolean>)(CooldownComponent::isOnCooldown)))).orElse(Boolean.valueOf(false)))).booleanValue()){
					return;
				}else{
					if (((Boolean)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).map((Function)(((Function<DisableComponent, Boolean>)(DisableComponent::isDisabled)))).orElse(Boolean.valueOf(false)))).booleanValue()){
						return;
					}else{
						if (((Boolean)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).map((Function)(((Function<ContinuousComponent, Boolean>)(ContinuousComponent::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue()){
							return;
						}else{
							if (((Boolean)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))).map((Function)(((Function<ChargeComponent, Boolean>)(ChargeComponent::isCharging)))).orElse(Boolean.valueOf(false)))).booleanValue()){
								return;
							}else{
								Function<StackComponent, Boolean> var5 = (l0) -> {
			return Boolean.valueOf(((l0.getDefaultStacks()) > (0)) && ((l0.getStacks()) != (l0.getDefaultStacks())));
		};
								if (((Boolean)(((java.util.Optional)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.STACK.get()))))).map((Function)(var5)).orElse(Boolean.valueOf(false)))).booleanValue()){
									return;
								}
							}
						}
					}
				}
			}
			var3.setEquippedAbility(this.slot,(IAbility)(null));
			ModNetwork.sendToAllTrackingAndSelf(new SRemoveAbilityPacket(var2.getId(),this.slot),(LivingEntity)(var2));
			if ((var3.getEquippedAbilitySlot(var4)) >= (0)){
				ModNetwork.sendToAllTrackingAndSelf(new SUpdateEquippedAbilityPacket((LivingEntity)(var2),var4),(LivingEntity)(var2));
			}
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<CRemoveAbilityPacket, Integer> var0 = CRemoveAbilityPacket::slot;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,(Function)(((Function<Integer, CRemoveAbilityPacket>)(CRemoveAbilityPacket::new)))));
	}
}