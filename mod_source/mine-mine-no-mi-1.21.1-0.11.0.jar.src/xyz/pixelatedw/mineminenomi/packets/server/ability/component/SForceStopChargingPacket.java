package xyz.pixelatedw.mineminenomi.packets.server.ability.component;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;

public final record SForceStopChargingPacket(int entityId, int abilityId) {
	// Fields
	public static final CustomPacketPayload.Type<SForceStopChargingPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_force_stop_charge"));
	public static final StreamCodec<FriendlyByteBuf, SForceStopChargingPacket> STREAM_CODEC;

	public SForceStopChargingPacket(LivingEntity var1, IAbility var2) {
		this(var1.getId(),IPacket.abilitySlot(var1,var2));
	}
	public void handle(ClientPayloadContext var1) {
		LivingEntity var6 = null;
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof LivingEntity)){
			LivingEntity var3 = ((LivingEntity)(var2));
			AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var3))).orElse((Object)(null))));
			if ((var4) == (null)){
				return;
			}else{
				IAbility var5 = var4.getEquippedAbility(this.abilityId);
				if ((var5) == (null)){
					return;
				}else{
					var6 = var3;
					final LivingEntity var6_f1 = var6;
					Consumer<ChargeComponent> var7 = (l0) -> {
			l0.forceStopCharging(var6_f1);
		};
					((java.util.Optional)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))))).ifPresent((Consumer)(var7));
					return;
				}
			}
		}else{
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SForceStopChargingPacket, Integer> var0 = SForceStopChargingPacket::entityId;
		Function<SForceStopChargingPacket, Integer> var1 = SForceStopChargingPacket::abilityId;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,(BiFunction)(((BiFunction<Integer, Integer, SForceStopChargingPacket>)(SForceStopChargingPacket::new)))));
	}
}