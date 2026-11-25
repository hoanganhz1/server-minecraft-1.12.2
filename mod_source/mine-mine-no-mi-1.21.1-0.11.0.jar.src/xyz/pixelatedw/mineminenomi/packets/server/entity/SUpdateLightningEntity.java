package xyz.pixelatedw.mineminenomi.packets.server.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.entities.NuLightningEntity;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SUpdateLightningEntity(int entityId, float length, int fadeTime, int startFadeTime, Vec3 lookVec, float xRot, float yRot, boolean mimicVanilla) {
	// Fields
	public static final CustomPacketPayload.Type<SUpdateLightningEntity> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_update_lightning"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SUpdateLightningEntity> STREAM_CODEC;

	public SUpdateLightningEntity(NuLightningEntity var1) {
		this(var1.getId(),var1.getLength(),var1.getFadeTime(),var1.getStartFadeTime(),var1.getLookAngle().normalize(),((var1.getOwner()) != (null)) ? (var1.getOwner().getXRot()) : (0F),((var1.getOwner()) != (null)) ? (var1.getOwner().getYRot()) : (0F),var1.getMimicVanilla());
	}
	public static void encode(RegistryFriendlyByteBuf var0, SUpdateLightningEntity var1) {
		var0.writeInt(var1.entityId);
		var0.writeFloat(var1.length);
		var0.writeInt(var1.fadeTime);
		var0.writeInt(var1.startFadeTime);
		var0.writeDouble(var1.lookVec.x);
		var0.writeDouble(var1.lookVec.y);
		var0.writeDouble(var1.lookVec.z);
		var0.writeFloat(var1.xRot);
		var0.writeFloat(var1.yRot);
		var0.writeBoolean(var1.mimicVanilla);
	}
	public static SUpdateLightningEntity decode(RegistryFriendlyByteBuf var0) {
		int var1 = var0.readInt();
		float var2 = var0.readFloat();
		int var3 = var0.readInt();
		int var4 = var0.readInt();
		Vec3 var5 = new Vec3(var0.readDouble(),var0.readDouble(),var0.readDouble());
		float var6 = var0.readFloat();
		float var7 = var0.readFloat();
		boolean var8 = var0.readBoolean();
		return new SUpdateLightningEntity(var1,var2,var3,var4,var5,var6,var7,var8);
	}
	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof NuLightningEntity)){
			NuLightningEntity var3 = ((NuLightningEntity)(var2));
			var3.setLength(this.length);
			var3.startFade(this.fadeTime,this.startFadeTime);
			var3.setBeamLookVector(this.lookVec);
			var3.setXRot(this.xRot);
			var3.setYRot(this.yRot);
			var3.setLightningMimic(this.mimicVanilla);
			return;
		}else{
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SUpdateLightningEntity::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SUpdateLightningEntity::decode)))));
	}
}