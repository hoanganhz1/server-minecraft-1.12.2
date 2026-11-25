package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.Optional;
import java.util.function.BiFunction;
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
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SRemoveAbilityPacket(int entityId, int slot) {
	// Fields
	public static final CustomPacketPayload.Type<SRemoveAbilityPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_remove_ability"));
	public static final StreamCodec<FriendlyByteBuf, SRemoveAbilityPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		try{
			Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
			if (((var2) != (null)) && (var2 instanceof LivingEntity)){
				LivingEntity var3 = ((LivingEntity)(var2));
				SRemoveAbilityPacket var4 = this;
				Consumer<AbilityData> var5 = (l0) -> {
			l0.setEquippedAbility(this.slot,(IAbility)(null));
		};
				((java.util.Optional)(AbilityData.get(var3))).ifPresent((Consumer)(var5));
				return;
			}else{
				return;
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
		Function<SRemoveAbilityPacket, Integer> var0 = SRemoveAbilityPacket::entityId;
		Function<SRemoveAbilityPacket, Integer> var1 = SRemoveAbilityPacket::slot;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,(BiFunction)(((BiFunction<Integer, Integer, SRemoveAbilityPacket>)(SRemoveAbilityPacket::new)))));
	}
}