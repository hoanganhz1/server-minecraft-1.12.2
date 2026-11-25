package xyz.pixelatedw.mineminenomi.packets.server.quest;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;

public class SFinishObjectivePacket implements IClientPacket {
	// Fields
	public static final CustomPacketPayload.Type<SFinishObjectivePacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_finish_objective"));
	private static final SFinishObjectivePacket INSTANCE = new SFinishObjectivePacket();
	public static final StreamCodec<FriendlyByteBuf, SFinishObjectivePacket> STREAM_CODEC = IPacket.defaultStreamCodec(INSTANCE);

	public void handle(ClientPayloadContext var1) {
		Minecraft.getInstance().getSoundManager().play((SoundInstance)(SimpleSoundInstance.forUI(SoundEvents.EXPERIENCE_ORB_PICKUP,1F)));
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
}