package xyz.pixelatedw.mineminenomi.packets.client.entity;

import com.mojang.datafixers.util.Function3;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public final record CSyncPlayerInputPacket(float xxa, float zza, boolean isJumping) {
	// Fields
	public static final CustomPacketPayload.Type<CSyncPlayerInputPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_sync_player_input"));
	public static final StreamCodec<FriendlyByteBuf, CSyncPlayerInputPacket> STREAM_CODEC;

	public CSyncPlayerInputPacket(LocalPlayer var1) {
		this(var1.xxa,var1.zza,var1.input.jumping);
	}
	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		var2.xxa = this.xxa;
		var2.zza = this.zza;
		var2.setJumping(this.isJumping);
		CSyncPlayerInputPacket var3 = this;
		Consumer<EntityStatsData> var4 = (l0) -> {
			l0.setLeftImpulse(this.xxa);
			l0.setForwardImpulse(this.zza);
			l0.setJumping(this.isJumping);
		};
		((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).ifPresent((Consumer)(var4));
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<CSyncPlayerInputPacket, Float> var0 = CSyncPlayerInputPacket::xxa;
		Function<CSyncPlayerInputPacket, Float> var1 = CSyncPlayerInputPacket::zza;
		Function<CSyncPlayerInputPacket, Boolean> var2 = CSyncPlayerInputPacket::isJumping;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.FLOAT,var0,ByteBufCodecs.FLOAT,var1,ByteBufCodecs.BOOL,var2,(Function3)(((com.mojang.datafixers.util.Function3)(CSyncPlayerInputPacket::new)))));
	}
}