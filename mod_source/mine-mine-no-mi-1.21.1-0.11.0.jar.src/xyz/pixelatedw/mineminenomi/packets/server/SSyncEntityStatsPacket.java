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
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SSyncEntityStatsPacket(int entityId, CompoundTag data) {
	// Fields
	public static final CustomPacketPayload.Type<SSyncEntityStatsPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_sync_entity_stats"));
	public static final StreamCodec<FriendlyByteBuf, SSyncEntityStatsPacket> STREAM_CODEC;

	public SSyncEntityStatsPacket(LivingEntity var1) {
		this(var1.getId(),(CompoundTag)(EntityStatsData.getOrCreate(var1).serializeNBT((HolderLookup.Provider)(var1.registryAccess()))));
	}
	public SSyncEntityStatsPacket(LivingEntity var1, EntityStatsData var2) {
		this(var1.getId(),(CompoundTag)(var2.serializeNBT((HolderLookup.Provider)(var1.registryAccess()))));
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
					SSyncEntityStatsPacket var4 = this;
					var5 = var2;
					final Entity var5_f1 = var5;
					Consumer<EntityStatsData> var6 = (l0) -> {
			l0.deserializeNBT((HolderLookup.Provider)(var5_f1.registryAccess()),this.data);
		};
					((java.util.Optional)(EntityStatsData.get(var3))).ifPresent((Consumer)(var6));
					AttributeHelper.updateHPAttribute(var3);
				}
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SSyncEntityStatsPacket, Integer> var0 = SSyncEntityStatsPacket::entityId;
		Function<SSyncEntityStatsPacket, CompoundTag> var1 = SSyncEntityStatsPacket::data;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.COMPOUND_TAG,var1,(BiFunction)(((BiFunction<Integer, CompoundTag, SSyncEntityStatsPacket>)(SSyncEntityStatsPacket::new)))));
	}
}