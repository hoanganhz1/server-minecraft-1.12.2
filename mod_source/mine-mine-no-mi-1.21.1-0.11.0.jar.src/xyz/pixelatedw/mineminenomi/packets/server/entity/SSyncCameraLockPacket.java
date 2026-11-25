package xyz.pixelatedw.mineminenomi.packets.server.entity;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.CameraLockData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SSyncCameraLockPacket(CompoundTag tag) {
	// Fields
	public static final CustomPacketPayload.Type<SSyncCameraLockPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_sync_camera_lock"));
	public static final StreamCodec<FriendlyByteBuf, SSyncCameraLockPacket> STREAM_CODEC;

	public SSyncCameraLockPacket(Player var1) {
		this((CompoundTag)(CameraLockData.getOrCreate((LivingEntity)(var1)).serializeNBT((HolderLookup.Provider)(var1.registryAccess()))));
	}
	public void handle(ClientPayloadContext var1) {
		LocalPlayer var2 = Minecraft.getInstance().player;
		CameraLockData var3 = ((CameraLockData)(((java.util.Optional)(CameraLockData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			var3.deserializeNBT((HolderLookup.Provider)(var2.registryAccess()),this.tag);
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SSyncCameraLockPacket, CompoundTag> var0 = SSyncCameraLockPacket::tag;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.COMPOUND_TAG,var0,(Function)(((Function<CompoundTag, SSyncCameraLockPacket>)(SSyncCameraLockPacket::new)))));
	}
}