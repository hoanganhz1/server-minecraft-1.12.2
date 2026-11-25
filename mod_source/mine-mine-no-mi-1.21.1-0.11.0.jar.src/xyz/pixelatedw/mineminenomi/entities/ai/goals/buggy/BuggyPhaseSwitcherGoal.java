package xyz.pixelatedw.mineminenomi.entities.ai.goals.buggy;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.BuggyEntity;

public class BuggyPhaseSwitcherGoal extends TickedGoal<BuggyEntity> {
	// Fields
	private static final int COOLDOWN = 600;
	private final double carPhaseHPThreshold;
	private final int carPhaseDuration;
	private boolean startNormalPhase;
	private boolean startCarPhase;
	private long carPhaseStartTick;

	public BuggyPhaseSwitcherGoal(BuggyEntity var1) {
		super(var1);
		this.carPhaseHPThreshold = (var1.isDifficultyStandard()) ? (20D) : (40D);
		this.carPhaseDuration = (var1.isDifficultyStandard()) ? (600) : (1200);
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (!(this.hasTimePassedSinceLastEnd(600F))){
				return false;
			}else{
				int var1 = (((double)(((BuggyEntity)(this.entity)).getHealth())) < (WyHelper.percentage(this.carPhaseHPThreshold,(double)(((BuggyEntity)(this.entity)).getMaxHealth())))) ? (1) : (0);
				int var2 = ((((BuggyEntity)(this.entity)).getKitingMeterCompletion()) > ((((BuggyEntity)(this.entity)).isDifficultyStandard()) ? (0.7F) : (0.5F))) ? (1) : (0);
				if ((((BuggyEntity)(this.entity)).isNormalPhaseActive()) && (!(((var1) == (0)) && ((var2) == (0))))){
					this.startCarPhase = true;
					return true;
				}else{
					if ((((BuggyEntity)(this.entity)).isCarPhaseActive()) && (this.hasCarPhaseTimePassed())){
						this.startNormalPhase = true;
						return true;
					}else{
						return false;
					}
				}
			}
		}
	}
	private boolean hasCarPhaseTimePassed() {
		return (((BuggyEntity)(this.entity)).level().getGameTime()) >= ((this.carPhaseStartTick) + ((long)(this.carPhaseDuration)));
	}
	public boolean canContinueToUse() {
		return false;
	}
	public void start() {
		super.start();
		if (this.startCarPhase){
			((BuggyEntity)(this.entity)).startCarPhase();
			this.carPhaseStartTick = ((BuggyEntity)(this.entity)).level().getGameTime();
		}else{
			if (this.startNormalPhase){
				((BuggyEntity)(this.entity)).startNormalPhase();
			}
		}
		this.startNormalPhase = false;
		this.startCarPhase = false;
	}
	public void tick() {
		super.tick();
	}
	public void stop() {
		super.stop();
	}
}