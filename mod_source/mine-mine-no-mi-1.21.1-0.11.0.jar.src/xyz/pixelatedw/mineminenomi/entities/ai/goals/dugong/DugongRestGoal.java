package xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong;

import java.util.EnumSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;
import xyz.pixelatedw.mineminenomi.init.ModMemoryModuleTypes;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class DugongRestGoal extends TickedGoal<AbstractDugongEntity> {
	// Fields
	private static final int ZZZ_BUBBLE_TIMER = 50;
	private Interval canUseInterval;
	private int sleepBubbleTimer;
	private int restTime;
	private int nextRestTime;
	private float startHealth;
	private float startYaw;

	public DugongRestGoal(AbstractDugongEntity var1) {
		super(var1);
		this.canUseInterval = new Interval(40);
		this.sleepBubbleTimer = 50;
		this.nextRestTime = 2;
		this.setFlags(EnumSet.of(Goal.Flag.JUMP,Goal.Flag.MOVE,Goal.Flag.LOOK));
	}
	public boolean canUse() {
		if (!(this.canUseInterval.canTick())){
			return false;
		}else{
			if (!(((AbstractDugongEntity)(this.entity)).isIdling())){
				return false;
			}else{
				if (((AbstractDugongEntity)(this.entity)).hasMemoryValue(((MemoryModuleType)(ModMemoryModuleTypes.LAST_EXPLOSION_HEARD.get())))){
					return false;
				}else{
					if ((this.getLastEndTick()) <= (0L)){
						this.setLastEndTick(((AbstractDugongEntity)(this.entity)).level().getGameTime());
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
	public boolean canContinueToUse() {
		if (((AbstractDugongEntity)(this.entity)).isEnraged()){
			return false;
		}else{
			if (((AbstractDugongEntity)(this.entity)).isCheering()){
				return false;
			}else{
				if (!(((AbstractDugongEntity)(this.entity)).isSleeping())){
					return false;
				}else{
					if (((AbstractDugongEntity)(this.entity)).hasMemoryValue(((MemoryModuleType)(ModMemoryModuleTypes.LAST_EXPLOSION_HEARD.get())))){
						return false;
					}else{
						if ((((AbstractDugongEntity)(this.entity)).getHealth()) < (this.startHealth)){
							return false;
						}else{
							if (GoalHelper.shouldMove(this.entity)){
								return false;
							}else{
								if (GoalHelper.hasAliveTarget(this.entity)){
									return false;
								}else{
									return !(this.hasTimePassedSinceStart(WyHelper.secondsToTicks((float)(this.restTime))));
								}
							}
						}
					}
				}
			}
		}
	}
	public void start() {
		super.start();
		((AbstractDugongEntity)(this.entity)).setResting(true);
		this.restTime = (20) + (((AbstractDugongEntity)(this.entity)).getRandom().nextInt(20));
		this.nextRestTime = (2) + (((AbstractDugongEntity)(this.entity)).getRandom().nextInt(3));
		((AbstractDugongEntity)(this.entity)).getNavigation().stop();
		this.startHealth = ((AbstractDugongEntity)(this.entity)).getHealth();
		this.startYaw = ((AbstractDugongEntity)(this.entity)).yBodyRot;
	}
	public void tick() {
		super.tick();
		this.sleepBubbleTimer = (this.sleepBubbleTimer) - (1);
		if ((this.sleepBubbleTimer) < (0)){
			Vec3 var1 = ((AbstractDugongEntity)(this.entity)).getLookAngle().scale(0.75D);
			SimpleParticleData var2 = new SimpleParticleData(((ParticleType)(ModParticleTypes.AWA.get())));
			var2.setLife(20);
			var2.setSize(2F);
			var2.setHasScaleDecay(false);
			WyHelper.spawnParticles(var2,((ServerLevel)(((AbstractDugongEntity)(this.entity)).level())),(((AbstractDugongEntity)(this.entity)).getX()) + (var1.x),((((AbstractDugongEntity)(this.entity)).getEyeY()) + (var1.y)) - (0.85D),(((AbstractDugongEntity)(this.entity)).getZ()) + (var1.z));
			this.sleepBubbleTimer = (50) + (((AbstractDugongEntity)(this.entity)).getRandom().nextInt(20));
		}
		this.startYaw = ((AbstractDugongEntity)(this.entity)).yBodyRot;
	}
	public void stop() {
		super.stop();
		((AbstractDugongEntity)(this.entity)).setResting(false);
	}
}