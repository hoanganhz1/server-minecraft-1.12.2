package xyz.pixelatedw.mineminenomi.api.quests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;

public abstract class Quest {
	// Fields
	private QuestId<? extends Quest> core;
	private List<Objective> objectives;
	private final PriorityEventPool<Quest$IStartQuestEvent> startEvents;
	private final PriorityEventPool<Quest$ICompleteQuestEvent> completeEvents;

	public Quest(QuestId<? extends Quest> var1) {
		this.objectives = new ArrayList();
		this.startEvents = (PriorityEventPool) (new PriorityEventPool());
		this.completeEvents = (PriorityEventPool) (new PriorityEventPool());
		this.core = var1;
	}
	@Nullable()
	public static Quest from(CompoundTag var0) {
		QuestId var1 = QuestId.get(ResourceLocation.parse(var0.getString("id")));
		if ((var1) == (null)){
			return null;
		}else{
			Quest var2 = var1.createQuest();
			if ((var2) == (null)){
				return null;
			}else{
				var2.load(var0);
				return var2;
			}
		}
	}
	public void addStartEvent(Quest$IStartQuestEvent var1) {
		this.startEvents.addEvent(100,var1);
	}
	public void addStartEvent(int var1, Quest$IStartQuestEvent var2) {
		this.startEvents.addEvent(var1,var2);
	}
	public void addCompleteEvent(Quest$ICompleteQuestEvent var1) {
		this.completeEvents.addEvent(100,var1);
	}
	public void addCompleteEvent(int var1, Quest$ICompleteQuestEvent var2) {
		this.completeEvents.addEvent(var1,var2);
	}
	public void addObjectives(Objective... var1) {
		Objective[] var2 = var1;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				Objective var5 = var2[var4];
				this.objectives.add(var5);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void onQuestStart(Player var1) {
		QuestData var2 = ((QuestData)(((java.util.Optional)(QuestData.get(var1))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			var2.addInProgressQuest(this);
			this.triggerStartEvents(var1);
			return;
		}
	}
	public boolean tryComplete(Player var1) {
		return this.tryComplete(var1,false);
	}
	public boolean tryComplete(Player var1, boolean var2) {
		QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get(var1))).orElse((Object)(null))));
		if (((var3) != (null)) && (!((!(this.isComplete())) && (!(var2))))){
			this.triggerCompleteEvents(var1);
			var3.addQuestOnCooldown(this.getCore());
			var3.addFinishedQuest(this.getCore());
			var3.removeInProgressQuest(this.getCore());
			return true;
		}else{
			return false;
		}
	}
	public void resetIncompleteObjectives(Player var1) {
		Predicate<Objective> var2 = (l0) -> {
			return !(l0.isComplete());
		};
		Player var3 = var1;
		final Player var3_f1 = var3;
		Consumer<Objective> var4 = (l0) -> {
			l0.setProgress(var3_f1,0F,false);
		};
		((java.util.stream.Stream)(((java.util.stream.Stream)(this.objectives.stream())).filter((Predicate)(var2)))).forEach((Consumer)(var4));
	}
	protected void triggerStartEvents(Player var1) {
		Player var2 = var1;
		final Player var2_f1 = var2;
		Consumer<Quest$IStartQuestEvent> var3 = (l0) -> {
			l0.start(var2_f1);
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.startEvents)).dispatch((Consumer)(var3));
	}
	protected void triggerCompleteEvents(Player var1) {
		Player var2 = var1;
		final Player var2_f1 = var2;
		Consumer<Quest$ICompleteQuestEvent> var3 = (l0) -> {
			l0.complete(var2_f1);
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.completeEvents)).dispatch((Consumer)(var3));
	}
	public boolean isComplete() {
		return (this.getProgress()) >= (1F);
	}
	public float getProgress() {
		int var1 = this.objectives.size();
		Predicate<Objective> var2 = (l0) -> {
			return (!(l0.isOptional())) && (l0.isComplete());
		};
		return ((float)(this.objectives.stream().filter(var2).count())) / ((float)(var1));
	}
	public void resetProgress(Player var1) {
		Player var2 = var1;
		final Player var2_f1 = var2;
		Consumer<Objective> var3 = (l0) -> {
			l0.setProgress(var2_f1,0F,true);
		};
		((java.util.stream.Stream)(this.objectives.stream())).forEach((Consumer)(var3));
	}
	public List<Objective> getObjectives() {
		return this.objectives;
	}
	public QuestId<? extends Quest> getCore() {
		return (QuestId<? extends Quest>) (QuestId) (this.core);
	}
	public CompoundTag save(CompoundTag var1) {
		var1.putString("id",WyRegistry.QUESTS.getKey(this.core).toString());
		ListTag var2 = new ListTag();
		Iterator var3 = this.getObjectives().iterator();
		do{
			if (var3.hasNext()){
				var2.add(((Objective)(var3.next())).save());
				continue;
			}else{
				break;
			}
		} while (true);
		var1.put("objectives",(Tag)(var2));
		return var1;
	}
	public void load(CompoundTag var1) {
		ListTag var2 = var1.getList("objectives",10);
		int var3 = 0;
		do{
			if (((var3) < (var2.size())) && ((var3) < (this.getObjectives().size()))){
				CompoundTag var4 = var2.getCompound(var3);
				((Objective)(this.getObjectives().get(var3))).load(var4);
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public boolean equals(Object var1) {
		if (((var1) != (null)) && ((this.getCore()) != (null))){
			if (var1 instanceof Quest){
				Quest var2 = ((Quest)(var1));
				if ((var2.getCore()) == (null)){
					return false;
				}else{
					if (((java.lang.Object)(this.getCore())).equals((Object)(var2.getCore()))){
						return true;
					}else{
						return false;
					}
				}
			}else{
				if (var1 instanceof QuestId){
					QuestId var2_1 = ((QuestId)(var1));
					if (((java.lang.Object)(this.getCore())).equals((Object)(var2_1))){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	}
}