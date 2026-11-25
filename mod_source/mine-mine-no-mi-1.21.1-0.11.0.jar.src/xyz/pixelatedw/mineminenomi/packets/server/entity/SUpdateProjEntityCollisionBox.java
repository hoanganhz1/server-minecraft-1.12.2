package xyz.pixelatedw.mineminenomi.packets.server.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SUpdateProjEntityCollisionBox(int entityId, double sizeX, double sizeY, double sizeZ) {
	// Fields
	public static final CustomPacketPayload.Type<SUpdateProjEntityCollisionBox> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_update_projectile_entity_collision"));
	public static final StreamCodec<FriendlyByteBuf, SUpdateProjEntityCollisionBox> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, SUpdateProjEntityCollisionBox var1) {
		var0.writeInt(var1.entityId);
		var0.writeDouble(var1.sizeX);
		var0.writeDouble(var1.sizeY);
		var0.writeDouble(var1.sizeZ);
	}
	public static SUpdateProjEntityCollisionBox decode(FriendlyByteBuf var0) {
		int var1 = var0.readInt();
		double var2 = var0.readDouble();
		double var3 = var0.readDouble();
		double var4 = var0.readDouble();
		return new SUpdateProjEntityCollisionBox(var1,var2,var3,var4);
	}
	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof NuProjectileEntity)){
			NuProjectileEntity var3 = ((NuProjectileEntity)(var2));
			var3.setEntityCollisionSize(this.sizeX,this.sizeY,this.sizeZ);
			return;
		}else{
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SUpdateProjEntityCollisionBox::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SUpdateProjEntityCollisionBox::decode)))));
	}
}