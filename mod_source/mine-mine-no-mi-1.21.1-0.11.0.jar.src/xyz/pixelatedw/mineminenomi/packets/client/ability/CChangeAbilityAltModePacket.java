package xyz.pixelatedw.mineminenomi.packets.client.ability;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public final record CChangeAbilityAltModePacket(int slot) {
	// Fields
	public static final CustomPacketPayload.Type<CChangeAbilityAltModePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_change_ability_mode"));
	public static final StreamCodec<FriendlyByteBuf, CChangeAbilityAltModePacket> STREAM_CODEC;

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var6 = null;
		ServerPlayer var2 = var1.player();
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			IAbility var4 = var3.getEquippedAbility(this.slot);
			if (((var4) != (null)) && (!(var2.isSpectator()))){
				Result var5 = var4.canUse((LivingEntity)(var2));
				if (var5.isFail()){
					if ((var5.getMessage()) != (null)){
						WyHelper.sendMessage((LivingEntity)(var2),var5.getMessage());
					}
					return;
				}else{
					var6 = var2;
					final ServerPlayer var6_f1 = var6;
					Consumer var7 = (Consumer) ((Consumer<AltModeComponent>) ((l0) -> {
			if ((!(l0.isDisabled())) && (!(l0.isAutomatic()))){
				l0.setNextInCycle((LivingEntity)(var6_f1));
				return;
			}else{
				return;
			}
		}));
					var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.ALT_MODE.get()))).ifPresent(var7);
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
		Function<CChangeAbilityAltModePacket, Integer> var0 = CChangeAbilityAltModePacket::slot;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,(Function)(((Function<Integer, CChangeAbilityAltModePacket>)(CChangeAbilityAltModePacket::new)))));
	}
}