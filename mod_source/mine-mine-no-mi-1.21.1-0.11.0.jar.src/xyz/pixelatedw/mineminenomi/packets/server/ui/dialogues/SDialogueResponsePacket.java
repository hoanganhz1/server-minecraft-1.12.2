package xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.DialogueScreen;

public class SDialogueResponsePacket implements IClientPacket {
	// Fields
	public static final CustomPacketPayload.Type<SDialogueResponsePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_dialogue_response"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SDialogueResponsePacket> STREAM_CODEC;
	private Component message;
	private boolean closeDialogue;

	public static SDialogueResponsePacket closeDialogue() {
		SDialogueResponsePacket var0 = new SDialogueResponsePacket();
		var0.closeDialogue = true;
		return var0;
	}
	public static SDialogueResponsePacket setMessage(Component var0) {
		SDialogueResponsePacket var1 = new SDialogueResponsePacket();
		var1.message = var0;
		return var1;
	}
	public static void encode(RegistryFriendlyByteBuf var0, SDialogueResponsePacket var1) {
		var0.writeBoolean(var1.closeDialogue);
		int var2 = ((var1.message) != (null)) ? (1) : (0);
		var0.writeBoolean((var2) != (0));
		if ((var2) != (0)){
			String var3 = Component.Serializer.toJson(var1.message,(HolderLookup.Provider)(var0.registryAccess()));
			var0.writeInt(var3.length());
			var0.writeUtf(var3,var3.length());
		}
	}
	public static SDialogueResponsePacket decode(RegistryFriendlyByteBuf var0) {
		SDialogueResponsePacket var1 = new SDialogueResponsePacket();
		var1.closeDialogue = var0.readBoolean();
		if (var0.readBoolean()){
			int var2 = var0.readInt();
			var1.message = Component.Serializer.fromJson(var0.readUtf(var2),(HolderLookup.Provider)(var0.registryAccess()));
		}
		return var1;
	}
	public void handle(ClientPayloadContext var1) {
		Screen var2 = Minecraft.getInstance().screen;
		if (var2 instanceof DialogueScreen){
			DialogueScreen var3 = ((DialogueScreen)(var2));
			if (this.closeDialogue){
				var3.onClose();
				return;
			}else{
				if ((this.message) != (null)){
					var3.setMessage(this.message);
				}
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
		StreamEncoder var0 = SDialogueResponsePacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SDialogueResponsePacket::decode)))));
	}
}