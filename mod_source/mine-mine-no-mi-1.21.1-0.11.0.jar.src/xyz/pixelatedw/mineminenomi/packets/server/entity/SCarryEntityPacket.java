package xyz.pixelatedw.mineminenomi.packets.server.entity;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.CarryData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SCarryEntityPacket(int targetId) {
	// Fields
	public static final CustomPacketPayload.Type<SCarryEntityPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_carry_entity"));
	public static final StreamCodec<FriendlyByteBuf, SCarryEntityPacket> STREAM_CODEC;

	public SCarryEntityPacket(LivingEntity var1) {
		this(((var1) == (null)) ? (-1) : (var1.getId()));
	}
	public void handle(ClientPayloadContext var1) {
		Minecraft var2 = Minecraft.getInstance();
		LocalPlayer var3 = var2.player;
		if ((var3) == (null)){
			return;
		}else{
			CarryData var4 = ((CarryData)(((java.util.Optional)(CarryData.get((LivingEntity)(var3)))).orElse((Object)(null))));
			if ((var4) == (null)){
				return;
			}else{
				if ((this.targetId) >= (0)){
					Entity var5 = var2.level.getEntity(this.targetId);
					if (((var5) != (null)) && (var5 instanceof LivingEntity)){
						LivingEntity var6 = ((LivingEntity)(var5));
						var4.startCarrying(var6);
					}else{
						var4.stopCarrying();
						return;
					}
				}else{
					var4.stopCarrying();
				}
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SCarryEntityPacket, Integer> var0 = SCarryEntityPacket::targetId;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,(Function)(((Function<Integer, SCarryEntityPacket>)(SCarryEntityPacket::new)))));
	}
}