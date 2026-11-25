package xyz.pixelatedw.mineminenomi.packets.server.ability;

import com.mojang.datafixers.util.Function3;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SEquipAbilityPacket(int entityId, int slot, ResourceLocation abilityId) {
	// Fields
	public static final CustomPacketPayload.Type<SEquipAbilityPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_equip_ability"));
	public static final StreamCodec<FriendlyByteBuf, SEquipAbilityPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		IAbility var6 = null;
		try{
			Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
			if (((var2) != (null)) && (var2 instanceof LivingEntity)){
				LivingEntity var3 = ((LivingEntity)(var2));
				if ((this.abilityId) == (null)){
					String var4 = var2.getDisplayName().getString();
					String var5 = var4 + " tried equipping an invalid ability!";
					WyDebug.warn(var5);
					return;
				}else{
					IAbility var4_1 = AbilityCore.get(this.abilityId).createAbility();
					SEquipAbilityPacket var5_1 = this;
					var6 = var4_1;
					final IAbility var6_f1 = var6;
					Consumer<AbilityData> var7 = (l0) -> {
			l0.setEquippedAbility(this.slot,var6_f1);
		};
					((java.util.Optional)(AbilityData.get(var3))).ifPresent((Consumer)(var7));
					return;
				}
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
		Function<SEquipAbilityPacket, Integer> var0 = SEquipAbilityPacket::entityId;
		Function<SEquipAbilityPacket, Integer> var1 = SEquipAbilityPacket::slot;
		Function<SEquipAbilityPacket, ResourceLocation> var2 = SEquipAbilityPacket::abilityId;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,ResourceLocation.STREAM_CODEC,var2,(Function3)(((com.mojang.datafixers.util.Function3)(SEquipAbilityPacket::new)))));
	}
}