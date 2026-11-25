package xyz.pixelatedw.mineminenomi.packets.server.ability;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.audio.DrumsOfLiberationTickableSound;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;

public final record SToggleDrumsOfLiberationSoundPacket(int entityId, boolean shouldPlay) {
	// Fields
	public static final CustomPacketPayload.Type<SToggleDrumsOfLiberationSoundPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_toggle_drums_of_liberation_sound"));
	public static final StreamCodec<FriendlyByteBuf, SToggleDrumsOfLiberationSoundPacket> STREAM_CODEC;

	public SToggleDrumsOfLiberationSoundPacket(LivingEntity var1, boolean var2) {
		this(var1.getId(),var2);
	}
	public static void encode(FriendlyByteBuf var0, SToggleDrumsOfLiberationSoundPacket var1) {
		var0.writeInt(var1.entityId);
		var0.writeBoolean(var1.shouldPlay);
	}
	public static SToggleDrumsOfLiberationSoundPacket decode(FriendlyByteBuf var0) {
		int var1 = var0.readInt();
		boolean var2 = var0.readBoolean();
		return new SToggleDrumsOfLiberationSoundPacket(var1,var2);
	}
	public void handle(ClientPayloadContext var1) {
		Minecraft var2 = Minecraft.getInstance();
		Entity var3 = var2.level.getEntity(this.entityId);
		if ((var3) == (null)){
			return;
		}else{
			if (this.shouldPlay){
				var2.getSoundManager().play((SoundInstance)(new DrumsOfLiberationTickableSound(((SoundEvent)(ModSounds.DRUMS_OF_LIBERATION_1.get())),0,var3)));
				var2.getSoundManager().play((SoundInstance)(new DrumsOfLiberationTickableSound(((SoundEvent)(ModSounds.DRUMS_OF_LIBERATION_2.get())),1,var3)));
			}else{
				var2.getSoundManager().stop(((SoundEvent)(ModSounds.DRUMS_OF_LIBERATION_1.get())).getLocation(),SoundSource.PLAYERS);
				var2.getSoundManager().stop(((SoundEvent)(ModSounds.DRUMS_OF_LIBERATION_2.get())).getLocation(),SoundSource.PLAYERS);
			}
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = SToggleDrumsOfLiberationSoundPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SToggleDrumsOfLiberationSoundPacket::decode)))));
	}
}