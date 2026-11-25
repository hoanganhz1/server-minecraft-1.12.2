package xyz.pixelatedw.mineminenomi.entities.ai.goals.whitewalkie;

import java.util.EnumSet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;
import xyz.pixelatedw.mineminenomi.init.ModMemoryModuleTypes;

public class WhiteWalkieSleepGoal extends TickedGoal<WhiteWalkieEntity> {
	// Fields
	private static final int COOLDOWN = 40;
	private int restTime;
	private int nextRestTime;
	private float startHealth;
	private float startYaw;

	public WhiteWalkieSleepGoal(WhiteWalkieEntity var1) {
		super(var1);
		this.nextRestTime = 2;
		this.setFlags(EnumSet.of(Goal.Flag.JUMP,Goal.Flag.MOVE,Goal.Flag.LOOK));
	}
	public boolean canUse() {
		if (!(this.hasTimePassedSinceLastEnd(40F))){
			return false;
		}else{
			if (!(((WhiteWalkieEntity)(this.entity)).isIdling())){
				return false;
			}else{
				if (!(((WhiteWalkieEntity)(this.entity)).level().isNight())){
					return false;
				}else{
					if (((WhiteWalkieEntity)(this.entity)).hasMemoryValue(((MemoryModuleType)(ModMemoryModuleTypes.LAST_EXPLOSION_HEARD.get())))){
						return false;
					}else{
						if ((this.getLastEndTick()) <= (0L)){
							this.setLastEndTick(((WhiteWalkieEntity)(this.entity)).level().getGameTime());
						}
						if (!(this.hasTimePassedSinceLastEnd(WyHelper.minutesToTicks((float)(this.nextRestTime))))){
							return false;
						}else{
							if (GoalHelper.hasAliveTarget(this.entity)){
								return false;
							}else{
								return true;
							}
						}
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (GoalHelper.hasAliveTarget(this.entity)){
			return false;
		}else{
			if (!(((WhiteWalkieEntity)(this.entity)).isSleeping())){
				return false;
			}else{
				if (((WhiteWalkieEntity)(this.entity)).hasMemoryValue(((MemoryModuleType)(ModMemoryModuleTypes.LAST_EXPLOSION_HEARD.get())))){
					return false;
				}else{
					if ((((WhiteWalkieEntity)(this.entity)).getHealth()) < (this.startHealth)){
						return false;
					}else{
						if (GoalHelper.shouldMove(this.entity)){
							return false;
						}else{
							return !(this.hasTimePassedSinceStart(WyHelper.secondsToTicks((float)(this.restTime))));
						}
					}
				}
			}
		}
	}
	public void start() {
		super.start();
		((WhiteWalkieEntity)(this.entity)).setSleeping(true);
		this.restTime = (60) + (((WhiteWalkieEntity)(this.entity)).getRandom().nextInt(60));
		this.nextRestTime = (2) + (((WhiteWalkieEntity)(this.entity)).getRandom().nextInt(3));
		((WhiteWalkieEntity)(this.entity)).getNavigation().stop();
		this.startHealth = ((WhiteWalkieEntity)(this.entity)).getHealth();
		this.startYaw = ((WhiteWalkieEntity)(this.entity)).yBodyRot;
	}
	public void tick() {
		super.tick();
		WhiteWalkieEntity var1 = ((WhiteWalkieEntity)(this.entity));
		var1.yBodyRot = this.startYaw;
	}
	public void stop() {
		super.stop();
		((WhiteWalkieEntity)(this.entity)).setSleeping(false);
	}
}