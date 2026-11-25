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
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;

public final record SUpdateEquippedAbilityPacket(int entityId, int abilitySlot, CompoundTag tag) {
	// Fields
	public static final CustomPacketPayload.Type<SUpdateEquippedAbilityPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_update_equipped_ability"));
	public static final StreamCodec<FriendlyByteBuf, SUpdateEquippedAbilityPacket> STREAM_CODEC;

	public SUpdateEquippedAbilityPacket(LivingEntity var1, IAbility var2) {
		this(var1.getId(),IPacket.abilitySlot(var1,var2),IPacket.abilityNBT(var1,var2));
	}
	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof LivingEntity)){
			LivingEntity var3 = ((LivingEntity)(var2));
			if ((this.abilitySlot) < (0)){
				return;
			}else{
				AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var3))).orElse((Object)(null))));
				if ((var4) == (null)){
					return;
				}else{
					IAbility var5 = var4.getEquippedAbility(this.abilitySlot);
					if ((var5) == (null)){
						return;
					}else{
						var5.load((HolderLookup.Provider)(var2.registryAccess()),this.tag);
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
		Function<SUpdateEquippedAbilityPacket, Integer> var0 = SUpdateEquippedAbilityPacket::entityId;
		Function<SUpdateEquippedAbilityPacket, Integer> var1 = SUpdateEquippedAbilityPacket::abilitySlot;
		Function<SUpdateEquippedAbilityPacket, CompoundTag> var2 = SUpdateEquippedAbilityPacket::tag;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,ByteBufCodecs.COMPOUND_TAG,var2,(Function3)(((com.mojang.datafixers.util.Function3)(SUpdateEquippedAbilityPacket::new)))));
	}
}