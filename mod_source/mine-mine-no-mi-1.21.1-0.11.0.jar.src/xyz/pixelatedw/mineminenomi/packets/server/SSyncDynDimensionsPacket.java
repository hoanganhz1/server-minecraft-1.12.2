package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SSyncDynDimensionsPacket(Set<ResourceKey<Level>> addedDims, Set<ResourceKey<Level>> removedDims) {
	// Fields
	public static final CustomPacketPayload.Type<SSyncDynDimensionsPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_sync_dyn_dimensions"));
	public static final StreamCodec<FriendlyByteBuf, SSyncDynDimensionsPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		LocalPlayer var2 = Minecraft.getInstance().player;
		if ((var2) == (null)){
			return;
		}else{
			Set var3 = var2.connection.levels();
			var3.addAll((Collection)(this.addedDims));
			Objects.requireNonNull(var3);
			this.removedDims.forEach((Consumer)(((Consumer<ResourceKey>)(var3::remove))));
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SSyncDynDimensionsPacket, Set> var0 = SSyncDynDimensionsPacket::addedDims;
		Function<SSyncDynDimensionsPacket, Set> var1 = SSyncDynDimensionsPacket::removedDims;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ResourceKey.streamCodec(Registries.DIMENSION).apply(ByteBufCodecs.collection((IntFunction)(((IntFunction<Set>)(HashSet::new))))),var0,ResourceKey.streamCodec(Registries.DIMENSION).apply(ByteBufCodecs.collection((IntFunction)(((IntFunction<Set>)(HashSet::new))))),var1,(BiFunction)(((BiFunction<Set, Set, SSyncDynDimensionsPacket>)(SSyncDynDimensionsPacket::new)))));
	}
}