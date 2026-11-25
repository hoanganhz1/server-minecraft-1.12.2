package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SSyncHakiDataPacket(int entityId, CompoundTag data) {
	// Fields
	public static final CustomPacketPayload.Type<SSyncHakiDataPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_sync_haki_data"));
	public static final StreamCodec<FriendlyByteBuf, SSyncHakiDataPacket> STREAM_CODEC;

	public SSyncHakiDataPacket(LivingEntity var1) {
		this(var1.getId(),HakiData.getOrCreate(var1).serializeNBT((HolderLookup.Provider)(var1.registryAccess())));
	}
	public void handle(ClientPayloadContext var1) {
		Entity var5 = null;
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if ((var2) == (null)){
			return;
		}else{
			if (this.data.isEmpty()){
				return;
			}else{
				if (var2 instanceof LivingEntity){
					LivingEntity var3 = ((LivingEntity)(var2));
					SSyncHakiDataPacket var4 = this;
					var5 = var2;
					final Entity var5_f1 = var5;
					Consumer<HakiData> var6 = (l0) -> {
			l0.deserializeNBT((HolderLookup.Provider)(var5_f1.registryAccess()),this.data);
		};
					((java.util.Optional)(HakiData.get(var3))).ifPresent((Consumer)(var6));
				}
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SSyncHakiDataPacket, Integer> var0 = SSyncHakiDataPacket::entityId;
		Function<SSyncHakiDataPacket, CompoundTag> var1 = SSyncHakiDataPacket::data;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.COMPOUND_TAG,var1,(BiFunction)(((BiFunction<Integer, CompoundTag, SSyncHakiDataPacket>)(SSyncHakiDataPacket::new)))));
	}
}