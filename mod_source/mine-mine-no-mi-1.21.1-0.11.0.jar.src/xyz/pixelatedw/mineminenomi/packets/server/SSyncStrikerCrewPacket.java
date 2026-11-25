package xyz.pixelatedw.mineminenomi.packets.server;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SSyncStrikerCrewPacket(int entityId, Crew crew) {
	// Fields
	public static final CustomPacketPayload.Type<SSyncStrikerCrewPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_sync_striker_crew"));
	public static final StreamCodec<FriendlyByteBuf, SSyncStrikerCrewPacket> STREAM_CODEC;

	public SSyncStrikerCrewPacket(StrikerEntity var1, Crew var2) {
		this(var1.getId(),(Crew)(var2));
	}
	public static void encode(FriendlyByteBuf var0, SSyncStrikerCrewPacket var1) {
		var0.writeInt(var1.entityId);
		var0.writeNbt((Tag)(var1.crew.write()));
	}
	public static SSyncStrikerCrewPacket decode(FriendlyByteBuf var0) {
		return new SSyncStrikerCrewPacket(var0.readInt(),(Crew)(Crew.from(var0.readNbt())));
	}
	public void handle(ClientPayloadContext var1) {
		Minecraft var2 = Minecraft.getInstance();
		if ((var2.player) == (null)){
			return;
		}else{
			Entity var3 = var2.level.getEntity(this.entityId);
			if ((var3) == (null)){
				return;
			}else{
				if (var3 instanceof StrikerEntity){
					((StrikerEntity)(var3)).setCrew(this.crew);
				}
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SSyncStrikerCrewPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SSyncStrikerCrewPacket::decode)))));
	}
}