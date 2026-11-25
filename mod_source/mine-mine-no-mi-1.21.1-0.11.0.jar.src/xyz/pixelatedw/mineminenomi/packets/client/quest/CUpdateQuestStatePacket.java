package xyz.pixelatedw.mineminenomi.packets.client.quest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
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
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ITrainer;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.ui.events.TrialsScreenEvent;

public final record CUpdateQuestStatePacket(ResourceLocation questId, int trainerId) {
	// Fields
	private static final TargetPredicate IS_TRAINER_CHECK;
	public static final CustomPacketPayload.Type<CUpdateQuestStatePacket> TYPE;
	public static final StreamCodec<FriendlyByteBuf, CUpdateQuestStatePacket> STREAM_CODEC;

	public CUpdateQuestStatePacket(QuestId<?> var1, Entity var2) {
		this((ResourceLocation)(var1.getRegistryKey()),var2.getId());
	}
	public static void encode(FriendlyByteBuf var0, CUpdateQuestStatePacket var1) {
		var0.writeResourceLocation(var1.questId);
		var0.writeInt(var1.trainerId);
	}
	public static CUpdateQuestStatePacket decode(FriendlyByteBuf var0) {
		ResourceLocation var1 = var0.readResourceLocation();
		int var2 = var0.readInt();
		return new CUpdateQuestStatePacket((ResourceLocation)(var1),var2);
	}
	public void handle(ServerPayloadContext var1) {
		QuestId var8 = null;
		if ((this.questId) == (null)){
			return;
		}else{
			TrialsScreenEvent var2 = new TrialsScreenEvent();
			ServerPlayer var3 = var1.player();
			if ((var3) == (null)){
				return;
			}else{
				QuestData var4 = ((QuestData)(((java.util.Optional)(QuestData.get((Player)(var3)))).orElse((Object)(null))));
				if ((var4) == (null)){
					return;
				}else{
					Entity var5 = var3.level().getEntity(this.trainerId);
					if (var5 instanceof ITrainer){
						ITrainer var6 = ((ITrainer)(var5));
						if (!(TargetHelper.getEntitiesInArea((LevelAccessor)(var3.level()),(LivingEntity)(var3),10D,IS_TRAINER_CHECK,Entity.class).contains(var5))){
							return;
						}else{
							QuestId var7 = ((QuestId)(WyRegistry.QUESTS.get(this.questId)));
							if (((var7) != (null)) && (!(var7.isLocked((Player)(var3))))){
								var8 = var7;
								final QuestId var8_f1 = var8;
								Predicate var9 = (Predicate) ((Predicate<QuestId>) ((l0) -> {
			return ((java.lang.Object)(l0)).equals((Object)(var8_f1));
		}));
								if (!(var6.getAvailableQuests((Player)(var3)).stream().anyMatch(var9))){
									return;
								}else{
									boolean var10 = false;
									Quest var11 = var4.getInProgressQuest(var7);
									if ((var4.hasInProgressQuest(var7)) && (((var11) != (null)) && (var11.isComplete()))){
										var11.tryComplete((Player)(var3));
										var10 = true;
									}else{
										if ((!(var4.hasInProgressQuest(var7))) && (!(var4.isQuestOnCooldown(var7)))){
											var7.createQuest().onQuestStart((Player)(var3));
											var10 = true;
										}
									}
									if (var10){
										var2.setQuestId(var7);
										var2.sendEvent(var3);
										ModNetwork.sendTo(new SSyncQuestDataPacket((Player)(var3)),(Player)(var3));
									}
									return;
								}
							}else{
								return;
							}
						}
					}else{
						return;
					}
				}
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Class var0 = ITrainer.class;
		Objects.requireNonNull(var0);
		IS_TRAINER_CHECK = new TargetPredicate().selector((Predicate)(((Predicate<Entity>)(var0::isInstance))));
		TYPE = (CustomPacketPayload.Type) (new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_update_quest_state")));
		StreamEncoder var1 = CUpdateQuestStatePacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var1,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(CUpdateQuestStatePacket::decode)))));
	}
}