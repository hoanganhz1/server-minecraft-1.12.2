package xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ITrainer;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.dialogues.TrainerDialogueScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.dialogues.TrainerDialogueScreen$QuestEntry;

public class SOpenTrainerDialogueScreenPacket implements IClientPacket {
	// Fields
	public static final CustomPacketPayload.Type<SOpenTrainerDialogueScreenPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","s_open_trainer_dialogue_screen"));
	public static final StreamCodec<FriendlyByteBuf, SOpenTrainerDialogueScreenPacket> STREAM_CODEC;
	private int questGiverEntity;
	private boolean isInCombat;
	private List<QuestId<?>> availableQuests;
	private List<QuestId<?>> completedQuests;
	private List<QuestId<?>> lockedQuests;

	private SOpenTrainerDialogueScreenPacket() {
		this.availableQuests = new ArrayList();
		this.completedQuests = new ArrayList();
		this.lockedQuests = new ArrayList();
	}
	public <T extends LivingEntity & ITrainer> SOpenTrainerDialogueScreenPacket(Player var1, T var2) {
		this.availableQuests = new ArrayList();
		this.completedQuests = new ArrayList();
		this.lockedQuests = new ArrayList();
		QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get(var1))).orElse((Object)(null))));
		if ((var3) != (null)){
			List var4 = ((ITrainer)(var2)).getAvailableQuests(var1);
			QuestData var5 = var3;
			Predicate var6 = (Predicate) ((Predicate<QuestId>) ((l0) -> {
			Quest lv1_2 = var5.getInProgressQuest(l0);
			if (((lv1_2) != (null)) && (lv1_2.isComplete())){
				return true;
			}else{
				return ((((lv1_2) != (null)) ? (1) : (0)) == (0)) && ((!(var5.hasFinishedQuest(l0))) || (l0.isRepeatable()));
			}
		}));
			this.availableQuests = (List) (var4.stream().filter(var6).toList());
			QuestData var7 = var3;
			Predicate var8 = (Predicate) ((Predicate<QuestId>) ((l0) -> {
			return (var7.hasInProgressQuest(l0)) && (var7.getInProgressQuest(l0).isComplete());
		}));
			this.completedQuests = (List) (var4.stream().filter(var8).toList());
			Player var9 = var1;
			Predicate var10 = (Predicate) ((Predicate<QuestId>) ((l0) -> {
			return l0.isLocked(var9);
		}));
			this.lockedQuests = (List) (var4.stream().filter(var10).toList());
		}
		this.questGiverEntity = var2.getId();
		this.isInCombat = WyHelper.isInCombat((LivingEntity)(var1));
	}
	public static void encode(FriendlyByteBuf var0, SOpenTrainerDialogueScreenPacket var1) {
		var0.writeInt(var1.questGiverEntity);
		var0.writeBoolean(var1.isInCombat);
		var0.writeInt(var1.availableQuests.size());
		Iterator var2 = var1.availableQuests.iterator();
		do{
			if (var2.hasNext()){
				QuestId var3 = ((QuestId)(var2.next()));
				var0.writeResourceLocation(var3.getRegistryKey());
				continue;
			}else{
				break;
			}
		} while (true);
		var0.writeInt(var1.completedQuests.size());
		Iterator var3_1 = var1.completedQuests.iterator();
		do{
			if (var3_1.hasNext()){
				QuestId var4 = ((QuestId)(var3_1.next()));
				var0.writeResourceLocation(var4.getRegistryKey());
				continue;
			}else{
				break;
			}
		} while (true);
		var0.writeInt(var1.lockedQuests.size());
		Iterator var4_1 = var1.lockedQuests.iterator();
		do{
			if (var4_1.hasNext()){
				QuestId var5 = ((QuestId)(var4_1.next()));
				var0.writeResourceLocation(var5.getRegistryKey());
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static SOpenTrainerDialogueScreenPacket decode(FriendlyByteBuf var0) {
		SOpenTrainerDialogueScreenPacket var1 = new SOpenTrainerDialogueScreenPacket();
		var1.questGiverEntity = var0.readInt();
		var1.isInCombat = var0.readBoolean();
		int var2 = var0.readInt();
		if ((var2) > (0)){
			int var3 = 0;
			do{
				if ((var3) < (var2)){
					QuestId var4 = QuestId.get(var0.readResourceLocation());
					var1.availableQuests.add(var4);
					var3++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		int var3 = var0.readInt();
		if ((var3) > (0)){
			int var4_1 = 0;
			do{
				if ((var4_1) < (var3)){
					QuestId var5 = QuestId.get(var0.readResourceLocation());
					var1.completedQuests.add(var5);
					var4_1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		int var4_1 = var0.readInt();
		if ((var4_1) > (0)){
			int var5_1 = 0;
			do{
				if ((var5_1) < (var4_1)){
					QuestId var6 = QuestId.get(var0.readResourceLocation());
					var1.lockedQuests.add(var6);
					var5_1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		return var1;
	}
	public void handle(ClientPayloadContext var1) {
		Entity var2 = Minecraft.getInstance().level.getEntity(this.questGiverEntity);
		if (var2 instanceof ITrainer){
			ITrainer var3 = ((ITrainer)(var2));
			if (var3 instanceof LivingEntity){
				LivingEntity var4 = ((LivingEntity)(var3));
				ArrayList var5 = new ArrayList();
				Iterator var6 = this.availableQuests.iterator();
				do{
					if (var6.hasNext()){
						QuestId var7 = ((QuestId)(var6.next()));
						TrainerDialogueScreen$QuestEntry var8 = new TrainerDialogueScreen$QuestEntry(var7);
						if (this.completedQuests.contains(var7)){
							var8.setComplete();
						}
						if (this.lockedQuests.contains(var7)){
							var8.setLocked();
						}
						var5.add(var8);
						continue;
					}else{
						break;
					}
				} while (true);
				Minecraft.getInstance().setScreen((Screen)(new TrainerDialogueScreen(var4,(List)(var5),this.isInCombat)));
				return;
			}else{
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
		StreamEncoder var0 = SOpenTrainerDialogueScreenPacket::encode;
		STREAM_CODEC = (StreamCodec) (StreamCodec.of(var0,(StreamDecoder)(((net.minecraft.network.codec.StreamDecoder)(SOpenTrainerDialogueScreenPacket::decode)))));
	}
}