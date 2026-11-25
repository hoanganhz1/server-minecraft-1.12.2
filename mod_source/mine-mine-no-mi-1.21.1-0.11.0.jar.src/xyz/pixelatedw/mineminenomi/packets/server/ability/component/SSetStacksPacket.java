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
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;

public final record SSetStacksPacket(int entityId, int abilityId, int stacks) {
	// Fields
	public static final CustomPacketPayload.Type<SSetStacksPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_set_stacks"));
	public static final StreamCodec<FriendlyByteBuf, SSetStacksPacket> STREAM_CODEC;

	public SSetStacksPacket(LivingEntity var1, IAbility var2, int var3) {
		this(var1.getId(),IPacket.abilitySlot(var1,var2),var3);
	}
	public void handle(ClientPayloadContext var1) {
		LivingEntity var7 = null;
		IAbility var8 = null;
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
					SSetStacksPacket var6 = this;
					var7 = var3;
					var8 = var5;
					final LivingEntity var7_f1 = var7;
					final IAbility var8_f2 = var8;
					Consumer<StackComponent> var9 = (l0) -> {
			l0.setStacks(var7_f1,var8_f2,this.stacks);
		};
					((java.util.Optional)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.STACK.get()))))).ifPresent((Consumer)(var9));
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
		Function<SSetStacksPacket, Integer> var0 = SSetStacksPacket::entityId;
		Function<SSetStacksPacket, Integer> var1 = SSetStacksPacket::abilityId;
		Function<SSetStacksPacket, Integer> var2 = SSetStacksPacket::stacks;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,ByteBufCodecs.INT,var2,(Function3)(((com.mojang.datafixers.util.Function3)(SSetStacksPacket::new)))));
	}
}