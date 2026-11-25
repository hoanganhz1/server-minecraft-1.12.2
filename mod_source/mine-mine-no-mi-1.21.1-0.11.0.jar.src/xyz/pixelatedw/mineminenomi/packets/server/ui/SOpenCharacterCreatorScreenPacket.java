package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.CharacterCreatorScreen;

public final record SOpenCharacterCreatorScreenPacket(boolean hasRandomizedRace, boolean allowSubRaceSelect) {
	// Fields
	public static final CustomPacketPayload.Type<SOpenCharacterCreatorScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_character_creator_screen"));
	public static final StreamCodec<FriendlyByteBuf, SOpenCharacterCreatorScreenPacket> STREAM_CODEC;

	public void handle(ClientPayloadContext var1) {
		Minecraft.getInstance().setScreen((Screen)(new CharacterCreatorScreen(this.hasRandomizedRace,this.allowSubRaceSelect)));
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<SOpenCharacterCreatorScreenPacket, Boolean> var0 = SOpenCharacterCreatorScreenPacket::hasRandomizedRace;
		Function<SOpenCharacterCreatorScreenPacket, Boolean> var1 = SOpenCharacterCreatorScreenPacket::allowSubRaceSelect;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.BOOL,var0,ByteBufCodecs.BOOL,var1,(BiFunction)(((BiFunction<Boolean, Boolean, SOpenCharacterCreatorScreenPacket>)(SOpenCharacterCreatorScreenPacket::new)))));
	}
}