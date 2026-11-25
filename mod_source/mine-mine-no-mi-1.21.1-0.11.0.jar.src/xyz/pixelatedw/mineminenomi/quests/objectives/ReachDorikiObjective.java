package xyz.pixelatedw.mineminenomi.quests.objectives;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class ReachDorikiObjective extends Objective implements IReachDorikiObjective {
	// Fields
	private int doriki;

	public ReachDorikiObjective(Quest var1, Component var2, int var3) {
		super(var1,var2);
		this.setMaxProgress(1F);
		this.doriki = var3;
	}
	public boolean checkDoriki(Player var1) {
		Function<EntityStatsData, Double> var2 = (l0) -> {
			return Double.valueOf(l0.getDoriki());
		};
		return (((Double)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).map((Function)(var2)).orElse(Double.valueOf(0D)))).doubleValue()) >= ((double)(this.doriki));
	}
}