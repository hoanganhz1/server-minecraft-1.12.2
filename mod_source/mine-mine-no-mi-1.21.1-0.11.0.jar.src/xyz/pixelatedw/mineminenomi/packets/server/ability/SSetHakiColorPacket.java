package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.awt.Color;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig$HaoshokuColoringLogic;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CSetHakiColorPacket;

public final record SSetHakiColorPacket(int entityId, ServerConfig$HaoshokuColoringLogic coloringLogic) {
	// Fields
	public static final CustomPacketPayload.Type<SSetHakiColorPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_set_haki_color"));
	public static final StreamCodec<FriendlyByteBuf, SSetHakiColorPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.entityId);
		if (((var2) != (null)) && (var2 instanceof LivingEntity)){
			HakiData var3 = ((HakiData)(((java.util.Optional)(HakiData.get(((LivingEntity)(var2))))).orElse((Object)(null))));
			if ((var3) == (null)){
				return;
			}else{
				int var4 = 16711680;
				if ((this.coloringLogic) == (ServerConfig$HaoshokuColoringLogic.CUSTOM)){
					var4 = ClientConfig.getHakiColor().getRGB();
				}else{
					if ((this.coloringLogic) == (ServerConfig$HaoshokuColoringLogic.RANDOM)){
						Random var5 = new Random(var2.getUUID().getMostSignificantBits());
						int var6 = WyHelper.randomWithRange(var5,0,255);
						int var7 = WyHelper.randomWithRange(var5,0,255);
						int var8 = WyHelper.randomWithRange(var5,0,255);
						var4 = new Color(var6,var7,var8).getRGB();
					}
				}
				int var5_1 = WyHelper.intToRGB(var4,50).getRGB();
				var3.setHaoshokuHakiColour(var5_1);
				ModNetwork.sendToServer(new CSetHakiColorPacket(var5_1));
				return;
			}
		}else{
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SSetHakiColorPacket, Integer> var0 = SSetHakiColorPacket::entityId;
		Function<SSetHakiColorPacket, ServerConfig$HaoshokuColoringLogic> var1 = SSetHakiColorPacket::coloringLogic;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,NeoForgeStreamCodecs.enumCodec(ServerConfig$HaoshokuColoringLogic.class),var1,(BiFunction)(((BiFunction<Integer, ServerConfig$HaoshokuColoringLogic, SSetHakiColorPacket>)(SSetHakiColorPacket::new)))));
	}
}