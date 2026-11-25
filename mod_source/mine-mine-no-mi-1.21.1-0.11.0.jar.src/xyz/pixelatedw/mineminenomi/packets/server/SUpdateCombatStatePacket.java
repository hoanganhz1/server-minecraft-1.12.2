package xyz.pixelatedw.mineminenomi.packets.server;

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
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.data.entity.CombatData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SUpdateCombatStatePacket(int attackerId) {
	// Fields
	public static final CustomPacketPayload.Type<SUpdateCombatStatePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_update_combat_state"));
	public static final StreamCodec<FriendlyByteBuf, SUpdateCombatStatePacket> STREAM_CODEC;

	public SUpdateCombatStatePacket(@Nullable() LivingEntity var1) {
		this(((var1) == (null)) ? (-1) : (var1.getId()));
	}
	public void handle(ClientPayloadContext var1) {
		LocalPlayer var2 = Minecraft.getInstance().player;
		CombatData var3 = ((CombatData)(((java.util.Optional)(CombatData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			if ((this.attackerId) >= (0)){
				Entity var4 = var2.level().getEntity(this.attackerId);
				if (var4 instanceof LivingEntity){
					var3.setInCombatCache(((LivingEntity)(var4)));
				}else{
					return;
				}
			}else{
				var3.setInCombatCache((LivingEntity)(null));
			}
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SUpdateCombatStatePacket, Integer> var0 = SUpdateCombatStatePacket::attackerId;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,(Function)(((Function<Integer, SUpdateCombatStatePacket>)(SUpdateCombatStatePacket::new)))));
	}
}