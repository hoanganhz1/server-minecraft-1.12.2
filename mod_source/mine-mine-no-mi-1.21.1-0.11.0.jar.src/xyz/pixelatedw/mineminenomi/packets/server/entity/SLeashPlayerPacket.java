package xyz.pixelatedw.mineminenomi.packets.server.entity;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.data.entity.CarryData;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SLeashPlayerPacket(UUID leashedId, UUID leashHolderId) {
	// Fields
	public static final CustomPacketPayload.Type<SLeashPlayerPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_leash_player"));
	public static final StreamCodec<FriendlyByteBuf, SLeashPlayerPacket> STREAM_CODEC;

	public SLeashPlayerPacket(Player var1, @Nullable() Player var2) {
		this((UUID)(var1.getUUID()),(UUID)(((var2) == (null)) ? (ModValues.NIL_UUID) : (var2.getUUID())));
	}
	public void handle(ClientPayloadContext var1) {
		Minecraft var2 = Minecraft.getInstance();
		UUID var3 = this.leashHolderId;
		UUID var4 = this.leashedId;
		Player var5 = var2.level.getPlayerByUUID(var4);
		if ((var5) == (null)){
			return;
		}else{
			CarryData var6 = ((CarryData)(((java.util.Optional)(CarryData.get((LivingEntity)(var5)))).orElse((Object)(null))));
			if ((var6) == (null)){
				return;
			}else{
				if (!(var3.equals(ModValues.NIL_UUID))){
					Player var7 = var2.level.getPlayerByUUID(this.leashHolderId);
					if ((var7) == (null)){
						var6.dropLeash();
						return;
					}else{
						var6.setLeashedTo((LivingEntity)(var7));
					}
				}else{
					var6.dropLeash();
				}
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SLeashPlayerPacket, UUID> var0 = SLeashPlayerPacket::leashedId;
		Function<SLeashPlayerPacket, UUID> var1 = SLeashPlayerPacket::leashHolderId;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(UUIDUtil.STREAM_CODEC,var0,UUIDUtil.STREAM_CODEC,var1,(BiFunction)(((BiFunction<UUID, UUID, SLeashPlayerPacket>)(SLeashPlayerPacket::new)))));
	}
}