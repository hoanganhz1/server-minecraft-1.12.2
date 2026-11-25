package xyz.pixelatedw.mineminenomi.packets.client.ability;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SEquipAbilityPacket;

public final record CEquipAbilityPacket(int slot, ResourceLocation abilityId) {
	// Fields
	public static final CustomPacketPayload.Type<CEquipAbilityPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_equip_ability"));
	public static final StreamCodec<FriendlyByteBuf, CEquipAbilityPacket> STREAM_CODEC;

	public CEquipAbilityPacket(int var1, AbilityCore<?> var2) {
		this(var1,(ResourceLocation)(var2.getRegistryKey()));
	}
	public void handle(ServerPayloadContext var1) {
		try{
			ServerPlayer var2 = var1.player();
			if ((var2) == (null)){
				return;
			}else{
				int var3 = (ServerConfig.getAbilityBars()) * (8);
				if ((this.slot) > (var3)){
					return;
				}else{
					AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).orElse((Object)(null))));
					if ((var4) == (null)){
						return;
					}else{
						AbilityCore var5 = ((AbilityCore)(WyRegistry.ABILITIES.get(this.abilityId)));
						if ((var5) == (null)){
							return;
						}else{
							if (!(var4.hasUnlockedAbility(var5))){
								return;
							}else{
								IAbility var6 = var4.getEquippedAbility(this.slot);
								if ((var6) != (null)){
									if (((Boolean)(var6.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).map((Function)(((Function<CooldownComponent, Boolean>)(CooldownComponent::isOnCooldown)))).orElse(Boolean.valueOf(false)))).booleanValue()){
										return;
									}else{
										if (((Boolean)(var6.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).map((Function)(((Function<DisableComponent, Boolean>)(DisableComponent::isDisabled)))).orElse(Boolean.valueOf(false)))).booleanValue()){
											return;
										}else{
											if (((Boolean)(var6.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).map((Function)(((Function<ContinuousComponent, Boolean>)(ContinuousComponent::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue()){
												return;
											}else{
												if (((Boolean)(var6.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))).map((Function)(((Function<ChargeComponent, Boolean>)(ChargeComponent::isCharging)))).orElse(Boolean.valueOf(false)))).booleanValue()){
													return;
												}else{
													Function<StackComponent, Boolean> var7 = (l0) -> {
			return Boolean.valueOf(((l0.getDefaultStacks()) > (0)) && ((l0.getStacks()) != (l0.getDefaultStacks())));
		};
													if (((Boolean)(((java.util.Optional)(var6.getComponent(((AbilityComponentKey)(ModAbilityComponents.STACK.get()))))).map((Function)(var7)).orElse(Boolean.valueOf(false)))).booleanValue()){
														return;
													}
												}
											}
										}
									}
								}
								IAbility var7_1 = var5.createAbility();
								var4.setEquippedAbility(this.slot,var7_1);
								ModNetwork.sendToAllTrackingAndSelf(new SEquipAbilityPacket(var2.getId(),this.slot,var5.getRegistryKey()),(LivingEntity)(var2));
								return;
							}
						}
					}
				}
			}
		}catch(Exception var2_1){
			var2_1.printStackTrace();
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<CEquipAbilityPacket, Integer> var0 = CEquipAbilityPacket::slot;
		Function<CEquipAbilityPacket, ResourceLocation> var1 = CEquipAbilityPacket::abilityId;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ResourceLocation.STREAM_CODEC,var1,(BiFunction)(((BiFunction<Integer, ResourceLocation, CEquipAbilityPacket>)(CEquipAbilityPacket::new)))));
	}
}