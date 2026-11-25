package xyz.pixelatedw.mineminenomi.packets.client.challenge;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.ui.events.ChallengesScreenEvent;

public final record CFetchPossibleInvitesPacket(int slot) {
	// Fields
	public static final CustomPacketPayload.Type<CFetchPossibleInvitesPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_fetch_possible_invites"));
	public static final StreamCodec<FriendlyByteBuf, CFetchPossibleInvitesPacket> STREAM_CODEC;
	private static final TargetPredicate TARGET_PICKER;

	public static void encode(FriendlyByteBuf var0, CFetchPossibleInvitesPacket var1) {
		var0.writeInt(var1.slot);
	}
	public static CFetchPossibleInvitesPacket decode(FriendlyByteBuf var0) {
		int var1 = var0.readInt();
		return new CFetchPossibleInvitesPacket(var1);
	}
	public void handle(ServerPayloadContext var1) {
		ChallengeData var5 = null;
		if (!(ServerConfig.isChallengesEnabled())){
			return;
		}else{
			ServerPlayer var2 = var1.player();
			ChallengeData var3 = ((ChallengeData)(((java.util.Optional)(ChallengeData.get((Player)(var2)))).orElse((Object)(null))));
			if ((var3) == (null)){
				return;
			}else{
				List var4 = TargetHelper.getEntitiesInArea((LevelAccessor)(var2.level()),(LivingEntity)(var2),20D,TARGET_PICKER,LivingEntity.class);
				var4.removeIf((Predicate)(((Predicate<LivingEntity>)((l0) -> {
			return !(l0 instanceof Player);
		}))));
				var5 = var3;
				final ChallengeData var5_f1 = var5;
				Predicate<LivingEntity> var6 = (l0) -> {
			UnmodifiableIterator lv2_2 = var5_f1.getGroupMembersIds().iterator();
			do{
				if (lv2_2.hasNext()){
					UUID lv2_3 = ((UUID)(lv2_2.next()));
					if (((lv2_3) != (null)) && (lv2_3.equals(l0.getUUID()))){
						return true;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return false;
		};
				((java.util.Collection)(var4)).removeIf((Predicate)(var6));
				List var7 = ((List)(((java.util.stream.Stream)(var4.stream().map((Function)(((Function<LivingEntity, Integer>)(Entity::getId)))))).collect((Collector)(Collectors.toList()))));
				ChallengesScreenEvent var8 = new ChallengesScreenEvent();
				var8.setPossibleInvites(this.slot,var7);
				var8.sendEvent(var2);
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		StreamEncoder var0 = CFetchPossibleInvitesPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(CFetchPossibleInvitesPacket::decode)))));
		TARGET_PICKER = new TargetPredicate().testFriendlyFaction();
	}
}