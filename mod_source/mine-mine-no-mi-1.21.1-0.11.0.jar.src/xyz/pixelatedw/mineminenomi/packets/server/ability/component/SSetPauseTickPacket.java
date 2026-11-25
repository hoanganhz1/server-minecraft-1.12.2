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
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SSetPauseTickPacket(int entityId, ResourceLocation ability, boolean pauseFlag) {
	// Fields
	public static final CustomPacketPayload.Type<SSetPauseTickPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_set_pause_tick"));
	public static final StreamCodec<FriendlyByteBuf, SSetPauseTickPacket> STREAM_CODEC;

	public SSetPauseTickPacket(LivingEntity var1, AbilityCore<?> var2, boolean var3) {
		this(var1.getId(),(ResourceLocation)(var2.getRegistryKey()),var3);
	}
	public void handle(ClientPayloadContext var1) {
		LivingEntity var8 = null;
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof LivingEntity)){
			AbilityCore var3 = ((AbilityCore)(WyRegistry.ABILITIES.get(this.ability)));
			if (((var3) != (null)) && ((var3.getType()) == (AbilityType.PASSIVE))){
				LivingEntity var4 = ((LivingEntity)(var2));
				AbilityData var5 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var4))).orElse((Object)(null))));
				if ((var5) == (null)){
					return;
				}else{
					IAbility var6 = var5.getPassiveAbility(var3);
					if ((var6) == (null)){
						return;
					}else{
						SSetPauseTickPacket var7 = this;
						var8 = var4;
						final LivingEntity var8_f1 = var8;
						Consumer<PauseTickComponent> var9 = (l0) -> {
			l0.setPause(var8_f1,this.pauseFlag);
		};
						((java.util.Optional)(var6.getComponent(((AbilityComponentKey)(ModAbilityComponents.PAUSE_TICK.get()))))).ifPresent((Consumer)(var9));
						return;
					}
				}
			}else{
				return;
			}
		}else{
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SSetPauseTickPacket, Integer> var0 = SSetPauseTickPacket::entityId;
		Function<SSetPauseTickPacket, ResourceLocation> var1 = SSetPauseTickPacket::ability;
		Function<SSetPauseTickPacket, Boolean> var2 = SSetPauseTickPacket::pauseFlag;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ResourceLocation.STREAM_CODEC,var1,ByteBufCodecs.BOOL,var2,(Function3)(((com.mojang.datafixers.util.Function3)(SSetPauseTickPacket::new)))));
	}
}