package xyz.pixelatedw.mineminenomi.entities.ai.goals.mr3;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr3Entity;

public class Mr3PhaseSwitcherGoal extends TickedGoal<Mr3Entity> {
	// Fields
	private boolean startStandardPhase;
	private boolean startChampionPhase;
	private boolean startTokudaiPhase;
	private boolean isPostTokudai;

	public Mr3PhaseSwitcherGoal(Mr3Entity var1) {
		super(var1);
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if ((((Mr3Entity)(this.entity)).isStandardPhase()) && (!(this.isPostTokudai))){
				if (GoalHelper.hasHealthAbovePercentage((LivingEntity)(this.entity),50D)){
					return false;
				}else{
					if ((GoalHelper.hasHealthUnderPercentage((LivingEntity)(this.entity),50D)) && (((Mr3Entity)(this.entity)).isChampionOnCooldown())){
						this.startTokudaiPhase = true;
						return true;
					}else{
						this.startChampionPhase = true;
						return true;
					}
				}
			}else{
				if (((Mr3Entity)(this.entity)).isChampionPhase()){
					if (((Mr3Entity)(this.entity)).isChampionOnCooldown()){
						this.startStandardPhase = true;
						return true;
					}else{
						if (GoalHelper.hasHealthAbovePercentage((LivingEntity)(this.entity),30D)){
							return false;
						}else{
							this.startTokudaiPhase = true;
							return true;
						}
					}
				}else{
					if ((((Mr3Entity)(this.entity)).isTokudaiPhase()) && (((Mr3Entity)(this.entity)).isTokudaiOnCooldown())){
						this.startStandardPhase = true;
						return true;
					}else{
						return false;
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		return false;
	}
	public void start() {
		super.start();
		this.switchPhase();
	}
	public void tick() {
		super.tick();
	}
	public void stop() {
		super.stop();
	}
	public void switchPhase() {
		if (this.startStandardPhase){
			((Mr3Entity)(this.entity)).startStandardPhase();
		}else{
			if (this.startChampionPhase){
				((Mr3Entity)(this.entity)).startChampionPhase();
			}else{
				if (this.startTokudaiPhase){
					((Mr3Entity)(this.entity)).startTokudaiPhase();
					this.isPostTokudai = true;
				}
			}
		}
		this.startStandardPhase = false;
		this.startChampionPhase = false;
		this.startTokudaiPhase = false;
	}
}