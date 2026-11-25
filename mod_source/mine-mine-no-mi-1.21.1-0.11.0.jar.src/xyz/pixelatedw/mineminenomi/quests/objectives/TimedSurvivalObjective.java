package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ISurviveObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective$IStartObjective;

public class TimedSurvivalObjective extends Objective implements ISurviveObjective {
	// Fields
	private int timeToSurvive;
	private float initialHP;

	public TimedSurvivalObjective(Quest var1, Component var2, int var3) {
		super(var1,var2);
		this.timeToSurvive = var3;
		this.setMaxProgress((float)(this.timeToSurvive));
		this.addStartEvent(100,(Objective$IStartObjective)(((xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective$IStartObjective)(this::onStartEvent))));
	}
	private boolean onStartEvent(Player var1) {
		this.initialHP = var1.getHealth();
		return true;
	}
	public boolean checkTime(Player var1) {
		if ((var1.getHealth()) > (this.initialHP)){
			this.initialHP = var1.getHealth();
		}
		if ((var1.getHealth()) < (this.initialHP)){
			this.setProgress(var1,0F,false);
			this.initialHP = var1.getHealth();
			return false;
		}else{
			return true;
		}
	}
}