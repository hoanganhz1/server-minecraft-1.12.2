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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SStopContinuityPacket(int entityId, int abilityId) {
	// Fields
	public static final CustomPacketPayload.Type<SStopContinuityPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_stop_continuity"));
	public static final StreamCodec<FriendlyByteBuf, SStopContinuityPacket> STREAM_CODEC;

	public SStopContinuityPacket(LivingEntity var1, IAbility var2) {
		this(var1.getId(),((Integer)(AbilityData.get(var1).map((Function<AbilityData, Integer>)((l0) -> {
			return Integer.valueOf(l0.getEquippedAbilitySlot(var2));
		})).orElse(Integer.valueOf(-1)))).intValue());
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
					Consumer<ContinuousComponent> var7 = (l0) -> {
			l0.stopContinuity(var6_f1);
		};
					((java.util.Optional)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))).ifPresent((Consumer)(var7));
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
		Function<SStopContinuityPacket, Integer> var0 = SStopContinuityPacket::entityId;
		Function<SStopContinuityPacket, Integer> var1 = SStopContinuityPacket::abilityId;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,(BiFunction)(((BiFunction<Integer, Integer, SStopContinuityPacket>)(SStopContinuityPacket::new)))));
	}
}