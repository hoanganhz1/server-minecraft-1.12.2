package xyz.pixelatedw.mineminenomi.api.quests.objectives;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.components.QuestComponent;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.quest.SFinishObjectivePacket;

public class Objective {
	// Fields
	private Quest parent;
	private Component title;
	private boolean isHidden;
	private boolean isOptional;
	private float maxProgress;
	private float progress;
	private Set<Objective> requirements;
	private Set<QuestComponent> components;
	private final PriorityEventPool<Objective$IStartObjective> startEvents;
	private final PriorityEventPool<Objective$IRestartObjective> restartEvents;
	private final PriorityEventPool<Objective$ICompleteObjective> completeEvents;

	public Objective(Quest var1, Component var2) {
		this.maxProgress = 1F;
		this.requirements = new HashSet();
		this.components = new HashSet();
		this.startEvents = (PriorityEventPool) (new PriorityEventPool());
		this.restartEvents = (PriorityEventPool) (new PriorityEventPool());
		this.completeEvents = (PriorityEventPool) (new PriorityEventPool());
		this.parent = var1;
		this.title = var2;
	}
	public Quest getParent() {
		return this.parent;
	}
	public Objective addStartEvent(Objective$IStartObjective var1) {
		return this.addStartEvent(100,var1);
	}
	public Objective addStartEvent(int var1, Objective$IStartObjective var2) {
		this.startEvents.addEvent(var1,var2);
		return this;
	}
	public Objective addRestartEvent(Objective$IRestartObjective var1) {
		return this.addRestartEvent(100,var1);
	}
	public Objective addRestartEvent(int var1, Objective$IRestartObjective var2) {
		this.restartEvents.addEvent(var1,var2);
		return this;
	}
	public Objective addCompleteEvent(Objective$ICompleteObjective var1) {
		return this.addCompleteEvent(100,var1);
	}
	public Objective addCompleteEvent(int var1, Objective$ICompleteObjective var2) {
		this.completeEvents.addEvent(var1,var2);
		return this;
	}
	public void setProgress(Player var1, float var2, boolean var3) {
		Player var5 = null;
		if ((this.isLocked()) && (!(var3))){
			return;
		}else{
			if ((this.isComplete()) && (!(var3))){
				return;
			}else{
				double var4 = (double)(this.getProgress());
				this.progress = Mth.clamp(var2,0F,this.getMaxProgress());
				if (this.isComplete()){
					var5 = var1;
					final Player var5_f1 = var5;
					Consumer<Objective$ICompleteObjective> var6 = (l0) -> {
			l0.complete(var5_f1);
		};
					((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.completeEvents)).dispatch((Consumer)(var6));
					if ((((var4) != ((double)(this.getProgress()))) ? (1) : (0)) != (0)){
						ModNetwork.sendTo(new SFinishObjectivePacket(),var1);
					}else{
						return;
					}
				}
				return;
			}
		}
	}
	public void alterProgress(Player var1, float var2, boolean var3) {
		this.setProgress(var1,(this.progress) + (var2),var3);
	}
	public void alterProgress(Player var1, float var2) {
		this.setProgress(var1,(this.progress) + (var2),false);
	}
	public float getProgress() {
		return this.progress;
	}
	public void setMaxProgress(float var1) {
		this.maxProgress = var1;
	}
	public float getMaxProgress() {
		return this.maxProgress;
	}
	public boolean isComplete() {
		return (this.progress) >= (this.maxProgress);
	}
	public <T extends Objective> T addRequirements(Objective... var1) {
		Objective[] var2 = var1;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				this.addRequirement(var2[var4]);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		return (T) (this);
	}
	public <T extends Objective> T addRequirement(Objective var1) {
		if (!(this.requirements.contains(var1))){
			this.requirements.add(var1);
		}
		return (T) (this);
	}
	public void addComponents(QuestComponent... var1) {
		QuestComponent[] var2 = var1;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				this.addComponent(var2[var4]);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void addComponent(QuestComponent var1) {
		if (!(this.components.contains(var1))){
			this.components.add(var1);
		}
	}
	public Set<QuestComponent> getComponents() {
		return this.components;
	}
	public Objective setOptional() {
		this.isOptional = true;
		return this;
	}
	public boolean isOptional() {
		return this.isOptional;
	}
	public Objective markHidden() {
		this.isHidden = true;
		return this;
	}
	public boolean isHidden() {
		return this.isHidden;
	}
	public boolean isLocked() {
		if ((this.requirements.size()) <= (0)){
			return false;
		}else{
			if (this.requirements.stream().allMatch((Predicate)(((Predicate<Objective>)((l0) -> {
			return (!(l0.isOptional())) && (l0.isComplete());
		}))))){
				return false;
			}else{
				return true;
			}
		}
	}
	public boolean hasEvents() {
		return (this.startEvents.countEventsInPool()) > (0L);
	}
	public Component getLocalizedTitle() {
		return this.title;
	}
	public CompoundTag save() {
		CompoundTag var1 = new CompoundTag();
		var1.putBoolean("isHidden",this.isHidden);
		var1.putFloat("progress",this.progress);
		return var1;
	}
	public void load(CompoundTag var1) {
		this.isHidden = var1.getBoolean("isHidden");
		this.progress = var1.getFloat("progress");
	}
}