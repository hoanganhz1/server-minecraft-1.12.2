package xyz.pixelatedw.mineminenomi.packets.client.entity;

import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public final record CUpdateInventoryPagePacket(int entityId, int pageId) {
	// Fields
	public static final CustomPacketPayload.Type<CUpdateInventoryPagePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_update_inventory_page"));
	public static final StreamCodec<FriendlyByteBuf, CUpdateInventoryPagePacket> STREAM_CODEC;

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		Entity var3 = var2.level().getEntity(this.entityId);
		if ((var3) == (null)){
			return;
		}else{
			if (var3 instanceof WhiteWalkieEntity){
				WhiteWalkieEntity var4 = ((WhiteWalkieEntity)(var3));
				var4.setInventoryPage(this.pageId);
				var4.openInventory((Player)(var2));
			}
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<CUpdateInventoryPagePacket, Integer> var0 = CUpdateInventoryPagePacket::entityId;
		Function<CUpdateInventoryPagePacket, Integer> var1 = CUpdateInventoryPagePacket::pageId;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,(BiFunction)(((BiFunction<Integer, Integer, CUpdateInventoryPagePacket>)(CUpdateInventoryPagePacket::new)))));
	}
}