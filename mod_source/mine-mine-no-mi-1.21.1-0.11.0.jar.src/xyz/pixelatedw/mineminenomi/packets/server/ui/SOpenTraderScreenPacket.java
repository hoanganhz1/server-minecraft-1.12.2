package xyz.pixelatedw.mineminenomi.packets.server.ui;

import com.mojang.datafixers.util.Function3;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.TradeEntry;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.SkypieanTraderEntity;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.TraderScreen;

public final record SOpenTraderScreenPacket(int traderId, List<TradeEntry> entries, int vearthTrades) {
	// Fields
	public static final CustomPacketPayload.Type<SOpenTraderScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_trader_screen"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SOpenTraderScreenPacket> STREAM_CODEC;

	public SOpenTraderScreenPacket(int var1, List<TradeEntry> var2) {
		this(var1,var2,0);
	}
	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.traderId);
		if (var2 instanceof TraderEntity){
			TraderEntity var3 = ((TraderEntity)(var2));
			var3.setTradingItems(this.entries);
			if (var3 instanceof SkypieanTraderEntity){
				((SkypieanTraderEntity)(var3)).setTradesLeft(this.vearthTrades);
			}
			Minecraft.getInstance().setScreen((Screen)(new TraderScreen(var3)));
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SOpenTraderScreenPacket, Integer> var0 = SOpenTraderScreenPacket::traderId;
		Function<SOpenTraderScreenPacket, List> var1 = SOpenTraderScreenPacket::entries;
		Function<SOpenTraderScreenPacket, Integer> var2 = SOpenTraderScreenPacket::vearthTrades;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,TradeEntry.STREAM_CODEC.apply(ByteBufCodecs.list()),var1,ByteBufCodecs.INT,var2,(Function3)(((com.mojang.datafixers.util.Function3)(SOpenTraderScreenPacket::new)))));
	}
}