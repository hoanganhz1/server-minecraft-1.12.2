package xyz.pixelatedw.mineminenomi.packets.client.entity.interaction;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nInteractions;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SDialogueResponsePacket;

public final record CDoctorHealPacket(int entityId) {
	// Fields
	public static final CustomPacketPayload.Type<CDoctorHealPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_doctor_heal"));
	public static final StreamCodec<FriendlyByteBuf, CDoctorHealPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, CDoctorHealPacket var1) {
		var0.writeInt(var1.entityId);
	}
	public static CDoctorHealPacket decode(FriendlyByteBuf var0) {
		int var1 = var0.readInt();
		return new CDoctorHealPacket(var1);
	}
	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		double var3 = (0.5D) + (var2.entityInteractionRange());
		Entity var4 = var2.level().getEntity(this.entityId);
		if (((var4) != (null)) && ((var4.isAlive()) && (!((var4.distanceToSqr((Entity)(var2))) > ((var3) * (var3)))))){
			EntityStatsData var5 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
			if ((var5) == (null)){
				return;
			}else{
				int var6 = ((int)((var2.getMaxHealth()) - (var2.getHealth()))) * (10);
				if ((var5.getBelly()) < ((long)(var6))){
					ModNetwork.sendTo(SDialogueResponsePacket.setMessage(ModI18nInteractions.getRandomNoBellyMessage()),(Player)(var2));
					return;
				}else{
					if (WyHelper.isInCombat((LivingEntity)(var2))){
						ModNetwork.sendTo(SDialogueResponsePacket.setMessage(ModI18nInteractions.NO_COMBAT_ALLOWED_MESSAGE),(Player)(var2));
						return;
					}else{
						var5.alterBelly((long)(-(var6)),StatChangeSource.STORE);
						var2.heal(var2.getMaxHealth());
						var2.removeEffect(MobEffects.POISON);
						ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var2)),(Player)(var2));
						ModNetwork.sendTo(SDialogueResponsePacket.closeDialogue(),(Player)(var2));
						return;
					}
				}
			}
		}else{
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = CDoctorHealPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(CDoctorHealPacket::decode)))));
	}
}