package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class TameEntityObjective extends Objective {
	public TameEntityObjective(Quest var1, Component var2, int var3) {
		super(var1,var2);
		this.setMaxProgress((float)(var3));
	}
}