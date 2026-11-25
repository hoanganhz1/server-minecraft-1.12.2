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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;

public final record SChangeAbilityAltModePacket<E extends Enum<E>>(int entityId, int abilityId, int mode) {
	// Fields
	public static final CustomPacketPayload.Type<SChangeAbilityAltModePacket<?>> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_change_ability_mode"));
	public static final StreamCodec<FriendlyByteBuf, SChangeAbilityAltModePacket<?>> STREAM_CODEC;

	public SChangeAbilityAltModePacket(LivingEntity var1, IAbility var2, E var3) {
		this(var1.getId(),IPacket.abilitySlot(var1,var2),var3.ordinal());
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
					SChangeAbilityAltModePacket var6 = this;
					var7 = var3;
					final LivingEntity var7_f1 = var7;
					Consumer var8 = (Consumer) ((Consumer<AltModeComponent>) ((l0) -> {
			l0.setMode(var7_f1,((Enum[])(l0.getAltMode().getEnumConstants()))[this.mode]);
		}));
					var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.ALT_MODE.get()))).ifPresent(var8);
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
		Function<SChangeAbilityAltModePacket, Integer> var0 = SChangeAbilityAltModePacket::entityId;
		Function<SChangeAbilityAltModePacket, Integer> var1 = SChangeAbilityAltModePacket::abilityId;
		Function<SChangeAbilityAltModePacket, Integer> var2 = SChangeAbilityAltModePacket::mode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,ByteBufCodecs.INT,var2,(Function3)(((com.mojang.datafixers.util.Function3)(SChangeAbilityAltModePacket::new)))));
	}
}