package xyz.pixelatedw.mineminenomi.packets.server.ability;

import com.mojang.datafixers.util.Function3;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
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
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;

public final record SUpdateAbilityNBTPacket(int entityId, ResourceLocation abilityId, CompoundTag data) {
	// Fields
	public static final CustomPacketPayload.Type<SUpdateAbilityNBTPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_update_ability_nbt"));
	public static final StreamCodec<FriendlyByteBuf, SUpdateAbilityNBTPacket> STREAM_CODEC;

	public SUpdateAbilityNBTPacket(LivingEntity var1, IAbility var2) {
		this(var1.getId(),var2.getCore().getRegistryKey(),IPacket.abilityNBT(var1,var2));
	}
	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof LivingEntity)){
			LivingEntity var3 = ((LivingEntity)(var2));
			AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var3))).orElse((Object)(null))));
			if ((var4) == (null)){
				return;
			}else{
				AbilityCore var5 = ((AbilityCore)(WyRegistry.ABILITIES.get(this.abilityId)));
				if ((var5) == (null)){
					return;
				}else{
					IAbility var6 = var4.getEquippedOrPassiveAbility(var5);
					if ((var6) == (null)){
						return;
					}else{
						var6.load((HolderLookup.Provider)(var3.registryAccess()),this.data);
						return;
					}
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
		Function<SUpdateAbilityNBTPacket, Integer> var0 = SUpdateAbilityNBTPacket::entityId;
		Function<SUpdateAbilityNBTPacket, ResourceLocation> var1 = SUpdateAbilityNBTPacket::abilityId;
		Function<SUpdateAbilityNBTPacket, CompoundTag> var2 = SUpdateAbilityNBTPacket::data;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ResourceLocation.STREAM_CODEC,var1,ByteBufCodecs.COMPOUND_TAG,var2,(Function3)(((com.mojang.datafixers.util.Function3)(SUpdateAbilityNBTPacket::new)))));
	}
}