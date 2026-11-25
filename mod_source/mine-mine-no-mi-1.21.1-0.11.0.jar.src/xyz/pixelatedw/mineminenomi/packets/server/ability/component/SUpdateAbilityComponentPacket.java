package xyz.pixelatedw.mineminenomi.packets.server.ability.component;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;

public final record SUpdateAbilityComponentPacket(int entityId, int abilityId, ResourceLocation componentKey, CompoundTag data) {
	// Fields
	public static final CustomPacketPayload.Type<SUpdateAbilityComponentPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_update_ability_component"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SUpdateAbilityComponentPacket> STREAM_CODEC;

	public SUpdateAbilityComponentPacket(LivingEntity var1, IAbility var2, AbilityComponent<? extends IAbility> var3) {
		this(var1.getId(),IPacket.abilitySlot(var1,var2),var3.getKey().getId(),var3.save((HolderLookup.Provider)(var1.registryAccess())));
	}
	public static void encode(RegistryFriendlyByteBuf var0, SUpdateAbilityComponentPacket var1) {
		var0.writeInt(var1.entityId);
		var0.writeInt(var1.abilityId);
		var0.writeResourceLocation(var1.componentKey);
		var0.writeNbt((Tag)(var1.data));
	}
	public static SUpdateAbilityComponentPacket decode(RegistryFriendlyByteBuf var0) {
		int var1 = var0.readInt();
		int var2 = var0.readInt();
		ResourceLocation var3 = var0.readResourceLocation();
		CompoundTag var4 = var0.readNbt();
		return new SUpdateAbilityComponentPacket(var1,var2,var3,var4);
	}
	public void handle(ClientPayloadContext var1) {
		Entity var7 = null;
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
					SUpdateAbilityComponentPacket var6 = this;
					var7 = var2;
					final Entity var7_f1 = var7;
					Consumer var8 = (Consumer) ((Consumer<AbilityComponent>) ((l0) -> {
			l0.load((HolderLookup.Provider)(var7_f1.registryAccess()),this.data);
		}));
					var5.getComponent(((AbilityComponentKey)(WyRegistry.ABILITY_COMPONENTS.get(this.componentKey)))).ifPresent(var8);
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
		StreamEncoder var0 = SUpdateAbilityComponentPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SUpdateAbilityComponentPacket::decode)))));
	}
}