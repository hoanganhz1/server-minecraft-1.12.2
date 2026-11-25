package xyz.pixelatedw.mineminenomi.packets.server.ui;

import com.mojang.datafixers.util.Function3;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.components.DyeLayerComponent;

public final record SToggleDyeLayersPacket(int currentLayer, int maxLayers, boolean visible) {
	// Fields
	public static final CustomPacketPayload.Type<SToggleDyeLayersPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_toggle_dye_layers"));
	public static final StreamCodec<FriendlyByteBuf, SToggleDyeLayersPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		Screen var2 = Minecraft.getInstance().screen;
		if (var2 instanceof CraftingScreen){
			Iterator var3 = ((CraftingScreen)(var2)).children().iterator();
			do{
				if (var3.hasNext()){
					GuiEventListener var4 = ((GuiEventListener)(var3.next()));
					if (var4 instanceof DyeLayerComponent){
						DyeLayerComponent var5 = ((DyeLayerComponent)(var4));
						var5.setVisible(this.visible);
						var5.setLayer(this.currentLayer);
						var5.setMaxLayers(this.maxLayers);
						break;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SToggleDyeLayersPacket, Integer> var0 = SToggleDyeLayersPacket::currentLayer;
		Function<SToggleDyeLayersPacket, Integer> var1 = SToggleDyeLayersPacket::maxLayers;
		Function<SToggleDyeLayersPacket, Boolean> var2 = SToggleDyeLayersPacket::visible;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,ByteBufCodecs.BOOL,var2,(Function3)(((com.mojang.datafixers.util.Function3)(SToggleDyeLayersPacket::new)))));
	}
}