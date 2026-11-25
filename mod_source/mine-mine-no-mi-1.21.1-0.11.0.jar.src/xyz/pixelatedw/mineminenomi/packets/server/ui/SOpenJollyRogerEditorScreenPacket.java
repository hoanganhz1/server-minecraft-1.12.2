package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.JollyRogerEditorScreen;

public final record SOpenJollyRogerEditorScreenPacket(boolean isEditing, Crew crew, List<JollyRogerElement> elements) {
	// Fields
	public static final CustomPacketPayload.Type<SOpenJollyRogerEditorScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_jolly_roger_editor_screen"));
	public static final StreamCodec<FriendlyByteBuf, SOpenJollyRogerEditorScreenPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, SOpenJollyRogerEditorScreenPacket var1) {
		var0.writeBoolean(var1.isEditing);
		var0.writeInt(var1.elements.size());
		Iterator var2 = var1.elements.iterator();
		do{
			if (var2.hasNext()){
				var0.writeResourceLocation(((JollyRogerElement)(var2.next())).getRegistryKey());
				continue;
			}else{
				break;
			}
		} while (true);
		var0.writeNbt((Tag)(var1.crew.write()));
	}
	public static SOpenJollyRogerEditorScreenPacket decode(FriendlyByteBuf var0) {
		boolean var1 = var0.readBoolean();
		int var2 = var0.readInt();
		ArrayList var3 = new ArrayList();
		int var4 = 0;
		do{
			if ((var4) < (var2)){
				ResourceLocation var5 = var0.readResourceLocation();
				JollyRogerElement var6 = ((JollyRogerElement)(WyRegistry.JOLLY_ROGER_ELEMENTS.get(var5)));
				if ((var6) != (null)){
					var3.add(var6);
				}else{
					String var7 = String.valueOf((Object)(var5));
					String var8 = var7 + " could not be found as a jolly roger element!";
					WyDebug.warn(var8);
				}
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		Crew var5_1 = Crew.from(var0.readNbt());
		return new SOpenJollyRogerEditorScreenPacket(var1,var5_1,(List)(var3));
	}
	public void handle(ClientPayloadContext var1) {
		Minecraft.getInstance().setScreen((Screen)(new JollyRogerEditorScreen(this.isEditing,this.crew,this.elements)));
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SOpenJollyRogerEditorScreenPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SOpenJollyRogerEditorScreenPacket::decode)))));
	}
}