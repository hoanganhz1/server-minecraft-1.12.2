package xyz.pixelatedw.mineminenomi.packets.server.ability;

import com.mojang.datafixers.util.Function4;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SDisableAbilityPacket(int entityId, Set<ResourceLocation> abilityIds, int disableTicks, boolean state) {
	// Fields
	public static final CustomPacketPayload.Type<SDisableAbilityPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_disable_ability"));
	public static final StreamCodec<FriendlyByteBuf, SDisableAbilityPacket> STREAM_CODEC;

	public SDisableAbilityPacket(LivingEntity var1, Set<IAbility> var2, int var3, boolean var4) {
		this(var1.getId(),(Set<ResourceLocation>)(Set)(((Set)(((java.util.stream.Stream)(var2.stream().map((Function<IAbility, ResourceLocation>)((l0) -> {
			return l0.getCore().getRegistryKey();
		})))).collect((Collector)(Collectors.toSet()))))),var3,var4);
	}
	public void handle(ClientPayloadContext var1) {
		Player var12_1 = null;
		Player var13 = null;
		Player var15_1 = null;
		Player var17 = null;
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof Player)){
			Player var3 = ((Player)(var2));
			AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var3)))).orElse((Object)(null))));
			if ((var4) == (null)){
				return;
			}else{
				SDisableAbilityPacket var5 = this;
				Predicate<IAbility> var6 = (l0) -> {
			return (l0.hasComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get())))) && (this.abilityIds.contains(l0.getCore().getRegistryKey()));
		};
				Set var7 = var4.getPassiveAbilities(var6);
				SDisableAbilityPacket var8 = this;
				Predicate<IAbility> var9 = (l0) -> {
			return (l0.hasComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get())))) && (this.abilityIds.contains(l0.getCore().getRegistryKey()));
		};
				Set var10 = var4.getEquippedAbilities(var9);
				if (this.state){
					Function<IAbility, DisableComponent> var11 = (l0) -> {
			return ((DisableComponent)(l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).get()));
		};
					SDisableAbilityPacket var12 = this;
					var13 = var3;
					final Player var13_f1 = var13;
					Consumer<DisableComponent> var14 = (l0) -> {
			l0.startDisable((LivingEntity)(var13_f1),(float)(this.disableTicks));
		};
					((java.util.stream.Stream)(((java.util.stream.Stream)(var7.stream())).map((Function)(var11)))).forEach((Consumer)(var14));
					Function<IAbility, DisableComponent> var15 = (l0) -> {
			return ((DisableComponent)(l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).get()));
		};
					SDisableAbilityPacket var16 = this;
					var17 = var3;
					final Player var17_f2 = var17;
					Consumer<DisableComponent> var18 = (l0) -> {
			l0.startDisable((LivingEntity)(var17_f2),(float)(this.disableTicks));
		};
					((java.util.stream.Stream)(((java.util.stream.Stream)(var10.stream())).map((Function)(var15)))).forEach((Consumer)(var18));
				}else{
					Function<IAbility, DisableComponent> var11_1 = (l0) -> {
			return ((DisableComponent)(l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).get()));
		};
					var12_1 = var3;
					final Player var12_1_f3 = var12_1;
					Consumer<DisableComponent> var13_1 = (l0) -> {
			l0.stopDisable((LivingEntity)(var12_1_f3));
		};
					((java.util.stream.Stream)(((java.util.stream.Stream)(var7.stream())).map((Function)(var11_1)))).forEach((Consumer)(var13_1));
					Function<IAbility, DisableComponent> var14_1 = (l0) -> {
			return ((DisableComponent)(l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).get()));
		};
					var15_1 = var3;
					final Player var15_1_f4 = var15_1;
					Consumer<DisableComponent> var16_1 = (l0) -> {
			l0.stopDisable((LivingEntity)(var15_1_f4));
		};
					((java.util.stream.Stream)(((java.util.stream.Stream)(var10.stream())).map((Function)(var14_1)))).forEach((Consumer)(var16_1));
				}
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
		Function<SDisableAbilityPacket, Integer> var0 = SDisableAbilityPacket::entityId;
		Function<SDisableAbilityPacket, Set> var1 = SDisableAbilityPacket::abilityIds;
		Function<SDisableAbilityPacket, Integer> var2 = SDisableAbilityPacket::disableTicks;
		Function<SDisableAbilityPacket, Boolean> var3 = SDisableAbilityPacket::state;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.collection((IntFunction)(((IntFunction<Set>)(HashSet::new))))),var1,ByteBufCodecs.INT,var2,ByteBufCodecs.BOOL,var3,(Function4)(((com.mojang.datafixers.util.Function4)(SDisableAbilityPacket::new)))));
	}
}