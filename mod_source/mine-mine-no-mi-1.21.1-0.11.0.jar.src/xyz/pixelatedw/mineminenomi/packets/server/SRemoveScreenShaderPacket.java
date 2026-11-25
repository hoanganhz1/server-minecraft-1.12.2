package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SRemoveScreenShaderPacket(ResourceLocation shader) {
	// Fields
	public static final CustomPacketPayload.Type<SRemoveScreenShaderPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_remove_screen_shader"));
	public static final StreamCodec<FriendlyByteBuf, SRemoveScreenShaderPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		LocalPlayer var2 = Minecraft.getInstance().player;
		if ((var2) == (null)){
			return;
		}else{
			EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
			if ((var3) != (null)){
				var3.removeScreenShader(this.shader);
			}
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SRemoveScreenShaderPacket, ResourceLocation> var0 = SRemoveScreenShaderPacket::shader;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ResourceLocation.STREAM_CODEC,var0,(Function)(((Function<ResourceLocation, SRemoveScreenShaderPacket>)(SRemoveScreenShaderPacket::new)))));
	}
}