package xyz.pixelatedw.mineminenomi.packets.server.ability.component;

import com.mojang.datafixers.util.Function3;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;

public final record SSetOverlayLayersPacket(int entityId, int abilityId, Set<Integer> layers) {
	// Fields
	public static final CustomPacketPayload.Type<SSetOverlayLayersPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_set_overlay_layers"));
	public static final StreamCodec<FriendlyByteBuf, SSetOverlayLayersPacket> STREAM_CODEC;

	public SSetOverlayLayersPacket(LivingEntity var1, IAbility var2, Set<Integer> var3) {
		this(var1.getId(),IPacket.abilitySlot(var1,var2),(Set)(var3));
	}
	public void handle(ClientPayloadContext var1) {
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
					SSetOverlayLayersPacket var6 = this;
					Consumer<SkinOverlayComponent> var7 = (l0) -> {
			l0.setShownOverlays(this.layers);
		};
					((java.util.Optional)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.SKIN_OVERLAY.get()))))).ifPresent((Consumer)(var7));
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
		Function<SSetOverlayLayersPacket, Integer> var0 = SSetOverlayLayersPacket::entityId;
		Function<SSetOverlayLayersPacket, Integer> var1 = SSetOverlayLayersPacket::abilityId;
		Function<SSetOverlayLayersPacket, Set> var2 = SSetOverlayLayersPacket::layers;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,ByteBufCodecs.INT.apply(ByteBufCodecs.collection((IntFunction)(((IntFunction<Set>)(HashSet::new))))),var2,(Function3)(((com.mojang.datafixers.util.Function3)(SSetOverlayLayersPacket::new)))));
	}
}