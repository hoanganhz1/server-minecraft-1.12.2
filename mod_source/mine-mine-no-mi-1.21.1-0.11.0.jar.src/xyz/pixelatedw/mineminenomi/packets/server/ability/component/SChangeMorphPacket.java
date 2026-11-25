package xyz.pixelatedw.mineminenomi.packets.server.ability.component;

import com.mojang.datafixers.util.Function4;
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
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;

public final record SChangeMorphPacket(int entityId, int abilitySlot, MorphInfo morphInfo, boolean isEnabled) {
	// Fields
	public static final CustomPacketPayload.Type<SChangeMorphPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_change_morph"));
	public static final StreamCodec<FriendlyByteBuf, SChangeMorphPacket> STREAM_CODEC;

	public SChangeMorphPacket(LivingEntity var1, IAbility var2, @Nullable() MorphInfo var3, boolean var4) {
		this(var1.getId(),IPacket.abilitySlot(var1,var2),(MorphInfo)(var3),var4);
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
				IAbility var5 = var4.getEquippedAbility(this.abilitySlot);
				if ((var5) == (null)){
					return;
				}else{
					SChangeMorphPacket var6 = this;
					var7 = var3;
					final LivingEntity var7_f1 = var7;
					Consumer<MorphComponent> var8 = (l0) -> {
			if (this.isEnabled){
				l0.startMorph(var7_f1,this.morphInfo);
			}else{
				l0.stopMorph(var7_f1);
			}
			this.morphInfo.updateMorphSize(var7_f1);
		};
					((java.util.Optional)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.MORPH.get()))))).ifPresent((Consumer)(var8));
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
		Function<SChangeMorphPacket, Integer> var0 = SChangeMorphPacket::entityId;
		Function<SChangeMorphPacket, Integer> var1 = SChangeMorphPacket::abilitySlot;
		Function<SChangeMorphPacket, MorphInfo> var2 = SChangeMorphPacket::morphInfo;
		Function<SChangeMorphPacket, Boolean> var3 = SChangeMorphPacket::isEnabled;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,MorphInfo.STREAM_CODEC,var2,ByteBufCodecs.BOOL,var3,(Function4)(((com.mojang.datafixers.util.Function4)(SChangeMorphPacket::new)))));
	}
}