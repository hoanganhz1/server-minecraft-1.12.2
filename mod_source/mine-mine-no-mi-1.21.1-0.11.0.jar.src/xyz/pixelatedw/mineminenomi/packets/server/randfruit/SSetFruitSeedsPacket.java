package xyz.pixelatedw.mineminenomi.packets.server.randfruit;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler$Client;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler$Common;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SSetFruitSeedsPacket(HashMap<Integer, Long> seeds, long worldSeed) {
	// Fields
	public static final CustomPacketPayload.Type<SSetFruitSeedsPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_set_fruit_seeds"));
	public static final StreamCodec<FriendlyByteBuf, SSetFruitSeedsPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		RandomizedFruitsHandler$Client.FRUIT_SEEDS = this.seeds;
		RandomizedFruitsHandler$Client.DIRTY = true;
		RandomizedFruitsHandler$Common.SEED = this.worldSeed;
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		IntFunction<HashMap> var0 = HashMap::new;
		Function<SSetFruitSeedsPacket, HashMap> var1 = SSetFruitSeedsPacket::seeds;
		Function<SSetFruitSeedsPacket, Long> var2 = SSetFruitSeedsPacket::worldSeed;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.map(var0,ByteBufCodecs.INT,ByteBufCodecs.VAR_LONG),var1,ByteBufCodecs.VAR_LONG,var2,(BiFunction)(((BiFunction<HashMap, Long, SSetFruitSeedsPacket>)(SSetFruitSeedsPacket::new)))));
	}
}