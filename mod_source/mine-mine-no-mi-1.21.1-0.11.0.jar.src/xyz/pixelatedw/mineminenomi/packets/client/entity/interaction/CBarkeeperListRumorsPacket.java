package xyz.pixelatedw.mineminenomi.packets.client.entity.interaction;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.poi.NTEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.POIEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.entities.mobs.BarkeeperEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nInteractions;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SDialogueResponsePacket;

public final record CBarkeeperListRumorsPacket(int entityId) {
	// Fields
	public static final CustomPacketPayload.Type<CBarkeeperListRumorsPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_barkeeeper_list_rumors"));
	public static final StreamCodec<FriendlyByteBuf, CBarkeeperListRumorsPacket> STREAM_CODEC;

	public static void encode(FriendlyByteBuf var0, CBarkeeperListRumorsPacket var1) {
		var0.writeInt(var1.entityId);
	}
	public static CBarkeeperListRumorsPacket decode(FriendlyByteBuf var0) {
		return new CBarkeeperListRumorsPacket(var0.readInt());
	}
	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		Entity var3 = var2.level().getEntity(this.entityId);
		if (var3 instanceof BarkeeperEntity){
			BarkeeperEntity var4 = ((BarkeeperEntity)(var3));
			EntityStatsData var5 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
			if ((var5) == (null)){
				return;
			}else{
				int var6 = 1000;
				if ((var5.getBelly()) < ((long)(var6))){
					ModNetwork.sendTo(SDialogueResponsePacket.setMessage(ModI18nInteractions.getRandomNoBellyMessage()),(Player)(var2));
					return;
				}else{
					int var9 = 0;
					int var7 = ((var5.isMarine()) || (var5.isBountyHunter())) ? (1) : (0);
					Component var8 = null;
					if ((var7) != (0)){
						var8 = findTargetEvent((Player)(var2),(LivingEntity)(var4),true);
					}else{
						var9 = var2.getRandom().nextInt(2);
						if ((var9) == (0)){
							findTargetEvent((Player)(var2),(LivingEntity)(var4),false);
						}else{
							if ((var9) == (1)){
								findCaravanEvent((Player)(var2),(LivingEntity)(var4));
							}
						}
					}
					if ((var8) == (null)){
						ModNetwork.sendTo(SDialogueResponsePacket.setMessage(ModI18nInteractions.getRandomNothingNewMessage()),(Player)(var2));
					}else{
						var9 = var8.hashCode();
						if (((var4.previousHash) != (0)) && ((var9) == (var4.previousHash))){
							return;
						}else{
							var4.previousHash = var9;
							ModNetwork.sendTo(SDialogueResponsePacket.setMessage(var8),(Player)(var2));
							WyHelper.sendMessage((LivingEntity)(var2),var8,true);
						}
					}
					var5.alterBelly((long)(-(var6)),StatChangeSource.STORE);
					ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var2),(EntityStatsData)(var5)),(Player)(var2));
					return;
				}
			}
		}else{
			return;
		}
	}
	private static Component findCaravanEvent(Player var0, LivingEntity var1) {
		EventsWorldData var2 = EventsWorldData.get();
		LivingEntity var3 = var1;
		final LivingEntity var3_f1 = var3;
		Function<POIEventTarget, ImmutablePair> var4 = (l0) -> {
			return ImmutablePair.of(l0,Double.valueOf(l0.getPosition().distanceTo(var3_f1.position())));
		};
		Comparator<ImmutablePair> var5 = (l0, l1) -> {
			return (((((Double)(l1.right)).doubleValue()) - (((Double)(l0.right)).doubleValue())) > (0D)) ? (1) : (-1);
		};
		Function<ImmutablePair, POIEventTarget> var6 = (l0) -> {
			return ((POIEventTarget)(l0.left));
		};
		Optional var7 = var2.getCaravanPOIs().stream().map(var4).sorted(var5).map(var6).findFirst();
		if (!(var7.isPresent())){
			return null;
		}else{
			return ModI18nInteractions.getRandomCaravanRumorMessage(((POIEventTarget)(var7.get())).getPosition());
		}
	}
	private static Component findTargetEvent(Player var0, LivingEntity var1, boolean var2) {
		EventsWorldData var3 = EventsWorldData.get();
		boolean var4 = var2;
		final boolean var4_f1 = var4;
		Predicate<NTEventTarget> var5 = (l0) -> {
			if ((var4_f1) && (l0.getTrackedNPC().getFaction().equals(ModFactions.MARINE.get()))){
				return false;
			}else{
				return true;
			}
		};
		LivingEntity var6 = var1;
		final LivingEntity var6_f2 = var6;
		Function<NTEventTarget, ImmutablePair> var7 = (l0) -> {
			return ImmutablePair.of(l0,Double.valueOf(l0.getPosition().distanceTo(var6_f2.position())));
		};
		Comparator<ImmutablePair> var8 = (l0, l1) -> {
			return (((((Double)(l1.right)).doubleValue()) - (((Double)(l0.right)).doubleValue())) > (0D)) ? (1) : (-1);
		};
		Function<ImmutablePair, NTEventTarget> var9 = (l0) -> {
			return ((NTEventTarget)(l0.left));
		};
		Optional var10 = var3.getNotoriousTargets().stream().filter(var5).map(var7).sorted(var8).map(var9).findFirst();
		if (!(var10.isPresent())){
			return null;
		}else{
			TrackedNPC var11 = ((NTEventTarget)(var10.get())).getTrackedNPC();
			String var12 = var11.createTrackedMob(var1.level()).getDisplayName().getString();
			Component var13 = null;
			if (var11.getFaction().equals(ModFactions.PIRATE.get())){
				var13 = ModI18nInteractions.getRandomPirateRumorMessage(((NTEventTarget)(var10.get())).getPosition(),var12);
			}else{
				if (((NTEventTarget)(var10.get())).getTrackedNPC().getFaction().equals(ModFactions.MARINE.get())){
					var13 = ModI18nInteractions.getRandomMarineRumorMessage(((NTEventTarget)(var10.get())).getPosition(),var12);
				}
			}
			return var13;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = CBarkeeperListRumorsPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(CBarkeeperListRumorsPacket::decode)))));
	}
}