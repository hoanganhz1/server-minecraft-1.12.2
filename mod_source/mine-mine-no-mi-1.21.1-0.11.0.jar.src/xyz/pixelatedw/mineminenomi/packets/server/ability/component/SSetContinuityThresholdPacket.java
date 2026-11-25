package xyz.pixelatedw.mineminenomi.packets.server.ability.component;

import com.mojang.datafixers.util.Function3;
import java.util.Optional;
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
import xyz.pixelatedw.mineminenomi.packets.IPacket;

public final record SSetContinuityThresholdPacket(int entityId, int abilityId, float thresholdTime) {
	// Fields
	public static final CustomPacketPayload.Type<SSetContinuityThresholdPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_set_continuity_threshold"));
	public static final StreamCodec<FriendlyByteBuf, SSetContinuityThresholdPacket> STREAM_CODEC;

	public SSetContinuityThresholdPacket(LivingEntity var1, IAbility var2, float var3) {
		this(var1.getId(),IPacket.abilitySlot(var1,var2),var3);
	}
	public void handle(ClientPayloadContext var1) {
		LivingEntity var7 = null;
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
					SSetContinuityThresholdPacket var6 = this;
					var7 = var3;
					final LivingEntity var7_f1 = var7;
					Consumer<ContinuousComponent> var8 = (l0) -> {
			l0.setThresholdTime(var7_f1,this.thresholdTime);
		};
					((java.util.Optional)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))).ifPresent((Consumer)(var8));
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
		Function<SSetContinuityThresholdPacket, Integer> var0 = SSetContinuityThresholdPacket::entityId;
		Function<SSetContinuityThresholdPacket, Integer> var1 = SSetContinuityThresholdPacket::abilityId;
		Function<SSetContinuityThresholdPacket, Float> var2 = SSetContinuityThresholdPacket::thresholdTime;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,ByteBufCodecs.FLOAT,var2,(Function3)(((com.mojang.datafixers.util.Function3)(SSetContinuityThresholdPacket::new)))));
	}
}