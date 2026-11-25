package xyz.pixelatedw.mineminenomi.data.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.components.QuestComponentIndex;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.triggers.CompleteQuestTrigger;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;

public class QuestData implements INBTSerializable<CompoundTag> {
	// Fields
	private Player owner;
	private Quest[] inProgressQuests;
	private Map<QuestId<? extends Quest>, Double> onCooldownQuests;
	private List<QuestId<?>> finishedQuests;
	private final QuestComponentIndex componentIndex;

	public static QuestData createCapability(IAttachmentHolder var0) {
		return new QuestData(((Player)(var0)));
	}
	public static Optional<QuestData> get(Player var0) {
		if (!(var0.hasData(ModAttachments.QUEST))){
			var0.setData(ModAttachments.QUEST,new QuestData(var0));
		}
		return (Optional<QuestData>) (Optional) (var0.getExistingData(ModAttachments.QUEST));
	}
	public static QuestData getOrCreate(Player var0) {
		if (!(var0.hasData(ModAttachments.QUEST))){
			var0.setData(ModAttachments.QUEST,new QuestData(var0));
		}
		return ((QuestData)(var0.getData(ModAttachments.QUEST)));
	}
	private QuestData(Player var1) {
		this.inProgressQuests = new Quest[4];
		this.onCooldownQuests = new HashMap();
		this.finishedQuests = new ArrayList();
		this.componentIndex = new QuestComponentIndex();
		this.owner = var1;
	}
	public QuestComponentIndex getComponentIndex() {
		return this.componentIndex;
	}
	public boolean isQuestOnCooldown(QuestId<? extends Quest> var1) {
		return (this.getQuestCooldown(var1)) > (((double)(System.currentTimeMillis())) / (50D));
	}
	public double getQuestCooldown(QuestId<? extends Quest> var1) {
		return ((Double)(((java.util.Map)(this.onCooldownQuests)).getOrDefault((Object)(var1),(Object)(Double.valueOf(var1.getRepeatCooldown()))))).doubleValue();
	}
	public boolean addQuestOnCooldown(QuestId<? extends Quest> var1) {
		if ((var1.isRepeatable()) && (!(this.isQuestOnCooldown(var1)))){
			double var2 = var1.getRepeatCooldown();
			if ((var2) > (0D)){
				this.onCooldownQuests.put(var1,Double.valueOf((var2) + (((double)(System.currentTimeMillis())) / (50D))));
			}
			return true;
		}else{
			return false;
		}
	}
	public void setQuestCooldown(QuestId<? extends Quest> var1, double var2) {
		if ((var2) > (0D)){
			this.onCooldownQuests.put(var1,Double.valueOf((var2) + (((double)(System.currentTimeMillis())) / (50D))));
		}else{
			this.onCooldownQuests.remove(var1);
		}
	}
	public boolean addInProgressQuest(QuestId<?> var1) {
		int var2 = 0;
		do{
			if ((var2) < (this.inProgressQuests.length)){
				if ((this.inProgressQuests[var2]) == (null)){
					this.inProgressQuests[var2] = var1.createQuest();
					this.getComponentIndex().indexQuestComponents(this.inProgressQuests[var2]);
					return true;
				}else{
					var2++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return false;
	}
	public boolean addInProgressQuest(Quest var1) {
		int var2 = 0;
		do{
			if ((var2) < (this.inProgressQuests.length)){
				if ((this.inProgressQuests[var2]) == (null)){
					this.inProgressQuests[var2] = var1;
					this.getComponentIndex().indexQuestComponents(var1);
					return true;
				}else{
					var2++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return false;
	}
	public boolean setInProgressQuest(int var1, Quest var2) {
		if (((this.getInProgressQuest(var2.getCore())) == (null)) && ((var1) <= (4))){
			this.inProgressQuests[var1] = var2;
			this.getComponentIndex().indexQuestComponents(var2);
			return true;
		}else{
			return false;
		}
	}
	public boolean removeInProgressQuest(QuestId<?> var1) {
		Quest var2 = this.getInProgressQuest(var1);
		if ((var2) != (null)){
			int var3 = 0;
			do{
				if ((var3) < (this.inProgressQuests.length)){
					Quest var4 = this.inProgressQuests[var3];
					if (((var4) != (null)) && (var4.equals(var2))){
						this.getComponentIndex().unindexQuestComponents(var4);
						var4.resetProgress(this.owner);
						this.inProgressQuests[var3] = null;
						return true;
					}else{
						var3++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		return false;
	}
	public boolean hasInProgressQuest(QuestId<?> var1) {
		Predicate<? super Quest> var2 = (l0) -> {
			return (l0) != (null);
		};
		return Arrays.stream(this.inProgressQuests).filter(var2).anyMatch((Predicate)(((Predicate<Quest>)((l0) -> {
			return ((java.lang.Object)(l0.getCore())).equals((Object)(var1));
		}))));
	}
	public <T extends Quest> T getInProgressQuest(QuestId<T> var1) {
		QuestId<T> var2 = var1;
		final QuestId<T> var2_f1 = var2;
		Predicate<? super Quest> var3 = (l0) -> {
			return ((l0) != (null)) && (((java.lang.Object)(l0.getCore())).equals((Object)(var2_f1)));
		};
		return (T) (((Quest)(((java.util.Optional)(((java.util.stream.Stream)(Arrays.stream(this.inProgressQuests))).filter((Predicate)(var3)).findFirst())).orElse((Object)(null)))));
	}
	public Quest getInProgressQuest(int var1) {
		return this.inProgressQuests[var1];
	}
	public int getInProgressQuestSlot(Quest var1) {
		int var2 = 0;
		do{
			if ((var2) < (this.inProgressQuests.length)){
				if (((this.inProgressQuests[var2]) != (null)) && (this.inProgressQuests[var2].equals(var1))){
					return var2;
				}else{
					var2++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return -1;
	}
	public List<Objective> getInProgressObjectives() {
		Predicate<? super Quest> var1 = (l0) -> {
			return ((l0) != (null)) && (!(l0.isComplete()));
		};
		Function var2 = (Function) ((Function<Quest, Stream>) ((l0) -> {
			return l0.getObjectives().stream();
		}));
		Predicate<Objective> var3 = (l0) -> {
			return (!(l0.isHidden())) && (!(l0.isLocked()));
		};
		return (List<Objective>) (List) (((java.util.stream.Stream)(((java.util.stream.Stream)(Arrays.stream(this.getInProgressQuests()))).filter((Predicate)(var1)).flatMap(var2))).filter((Predicate)(var3)).toList());
	}
	public Quest[] getInProgressQuests() {
		return this.inProgressQuests;
	}
	public void clearInProgressQuests() {
		int var1 = 0;
		do{
			if ((var1) < (this.inProgressQuests.length)){
				if ((this.inProgressQuests[var1]) != (null)){
					this.inProgressQuests[var1] = null;
				}
				var1++;
				continue;
			}else{
				break;
			}
		} while (true);
		this.getComponentIndex().clear();
	}
	public int countInProgressQuests() {
		Predicate<? super Quest> var1 = (l0) -> {
			return (l0) != (null);
		};
		ToIntFunction<Quest> var2 = (l0) -> {
			return 1;
		};
		return Arrays.stream(this.inProgressQuests).filter(var1).mapToInt(var2).sum();
	}
	public boolean addFinishedQuest(QuestId<?> var1) {
		if ((this.getFinishedQuest(var1)) != (null)){
			return false;
		}else{
			Player var2 = this.owner;
			if (var2 instanceof ServerPlayer){
				ServerPlayer var3 = ((ServerPlayer)(var2));
				((CompleteQuestTrigger)(ModAdvancements.COMPLETE_QUEST.get())).trigger(var3,var1);
			}
			this.finishedQuests.add(var1);
			return true;
		}
	}
	public boolean removeFinishedQuest(QuestId<?> var1) {
		QuestId var2 = this.getFinishedQuest(var1);
		if ((var2) != (null)){
			this.finishedQuests.remove(var2);
			return true;
		}else{
			return false;
		}
	}
	public boolean hasFinishedQuest(QuestId<?> var1) {
		if (!(ServerConfig.isQuestProgressionEnabled())){
			return true;
		}else{
			Predicate var2 = (Predicate) ((Predicate<QuestId>) ((l0) -> {
			return (l0) != (null);
		}));
			return this.finishedQuests.stream().filter(var2).anyMatch((Predicate)(((java.util.function.Predicate)((Predicate) ((Predicate<QuestId>) ((l0) -> {
			return ((java.lang.Object)(l0)).equals((Object)(var1));
		}))))));
		}
	}
	public <T extends QuestId<?>> T getFinishedQuest(T var1) {
		T var2 = var1;
		final T var2_f1 = var2;
		Predicate var3 = (Predicate) ((Predicate<QuestId>) ((l0) -> {
			return ((l0) != (null)) && (((java.lang.Object)(l0)).equals((Object)(var2_f1)));
		}));
		return (T) (((QuestId)(((java.util.Optional)(((java.util.stream.Stream)(this.finishedQuests.stream())).filter((Predicate)(var3)).findFirst())).orElse((Object)(null)))));
	}
	public List<QuestId<?>> getFinishedQuests() {
		Predicate var1 = (Predicate) ((Predicate<QuestId>) ((l0) -> {
			return (l0) != (null);
		}));
		return ((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.finishedQuests.stream())).filter((Predicate)(var1)))).collect((Collector)(Collectors.toList()))));
	}
	public void clearFinishedQuests() {
		this.finishedQuests.clear();
	}
	public int countFinishedQuests() {
		return this.finishedQuests.size();
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		ListTag var3 = new ListTag();
		Iterator var4 = this.onCooldownQuests.entrySet().iterator();
		do{
			if (var4.hasNext()){
				Map.Entry var5 = ((Map.Entry)(var4.next()));
				QuestId var6 = ((QuestId)(var5.getKey()));
				ResourceLocation var7 = WyRegistry.QUESTS.getKey(var6);
				if ((var7) == (null)){
					continue;
				}else{
					double var8 = ((Double)(var5.getValue())).doubleValue();
					CompoundTag var9 = new CompoundTag();
					var9.putString("id",var7.toString());
					var9.putDouble("cooldown",var8);
					var3.add(var9);
					continue;
				}
			}else{
				break;
			}
		} while (true);
		var2.put("questsOnCooldownTag",(Tag)(var3));
		ListTag var5_1 = new ListTag();
		int var6_1 = 0;
		do{
			if ((var6_1) < (this.getInProgressQuests().length)){
				Quest var7_1 = this.getInProgressQuests()[var6_1];
				if ((var7_1) != (null)){
					var5_1.add(var7_1.save(new CompoundTag()));
				}
				var6_1++;
				continue;
			}else{
				break;
			}
		} while (true);
		var2.put("quests_in_tracker",(Tag)(var5_1));
		ListTag var7_2 = new ListTag();
		Iterator var8_1 = this.getFinishedQuests().iterator();
		do{
			if (var8_1.hasNext()){
				QuestId var9_1 = ((QuestId)(var8_1.next()));
				ResourceLocation var10 = WyRegistry.QUESTS.getKey(var9_1);
				if ((var10) != (null)){
					CompoundTag var11 = new CompoundTag();
					var11.putString("id",var10.toString());
					var7_2.add(var11);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		var2.put("finished_quests",(Tag)(var7_2));
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		this.onCooldownQuests.clear();
		this.clearInProgressQuests();
		this.clearFinishedQuests();
		ListTag var3 = var2.getList("questsOnCooldownTag",10);
		int var4 = 0;
		do{
			if ((var4) < (var3.size())){
				CompoundTag var5 = var3.getCompound(var4);
				ResourceLocation var6 = ResourceLocation.parse(var5.getString("id"));
				QuestId var7 = ((QuestId)(WyRegistry.QUESTS.get(var6)));
				if ((var7) == (null)){

				}else{
					double var8 = var5.getDouble("cooldown");
					this.onCooldownQuests.put(var7,Double.valueOf(var8));
				}
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		ListTag var5_1 = var2.getList("quests_in_tracker",10);
		int var6_1 = 0;
		do{
			if ((var6_1) < (var5_1.size())){
				try{
					Quest var7_1 = Quest.from(var5_1.getCompound(var6_1));
					if ((var7_1) == (null)){

					}else{
						this.setInProgressQuest(var6_1,var7_1);
					}
				}catch(Exception var7_2){

				}
				var6_1++;
				continue;
			}else{
				break;
			}
		} while (true);
		ListTag var7_3 = var2.getList("finished_quests",10);
		int var8_1 = 0;
		do{
			if ((var8_1) < (var7_3.size())){
				try{
					ResourceLocation var9 = ResourceLocation.parse(var7_3.getCompound(var8_1).getString("id"));
					this.addFinishedQuest(((QuestId)(WyRegistry.QUESTS.get(var9))));
				}catch(Exception var9_1){

				}
				var8_1++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}