package xyz.pixelatedw.mineminenomi.api.quests.components;

import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public abstract class QuestComponent {
	// Fields
	private final Objective objective;

	public QuestComponent(Objective var1) {
		this.objective = var1;
	}
	public Objective getObjective() {
		return this.objective;
	}
	public boolean isValid() {
		if (((this.objective) != (null)) && ((!(this.objective.isHidden())) && ((!(this.objective.isLocked())) && (!(this.objective.isComplete()))))){
			Quest var1 = this.objective.getParent();
			if (((var1) != (null)) && (!(var1.isComplete()))){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}