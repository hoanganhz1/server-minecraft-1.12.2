package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;

public class EatNearbyFishGoal extends TickedGoal<Mob> {
	// Fields
	private static final int COOLDOWN = 40;
	private List<AbstractFish> fishes;
	private AbstractFish target;
	private long lastCheck;
	private boolean justAte;

	public EatNearbyFishGoal(Mob var1) {
		super(var1);
		this.fishes = new ArrayList();
		this.lastCheck = 0L;
		this.justAte = false;
		this.setFlags(EnumSet.of(Goal.Flag.LOOK,Goal.Flag.MOVE,Goal.Flag.TARGET));
	}
	public boolean canUse() {
		if (GoalHelper.hasAliveTarget(this.entity)){
			return false;
		}else{
			if ((this.lastCheck) == (0L)){
				this.lastCheck = this.entity.level().getGameTime();
				return false;
			}else{
				if (!(this.hasTimePassedSinceLastCheck(40F))){
					return false;
				}else{
					if (!(this.hasTimePassedSinceLastEnd(40F))){
						return false;
					}else{
						this.fishes = (List) (TargetHelper.getEntitiesInArea((LevelAccessor)(this.entity.level()),(LivingEntity)(this.entity),this.getFollowDistance(),(TargetPredicate)(null),AbstractFish.class));
						if ((this.fishes.size()) <= (0)){
							this.lastCheck = this.entity.level().getGameTime();
							return false;
						}else{
							this.target = ((AbstractFish)(this.fishes.get(0)));
							return true;
						}
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (((this.target) != (null)) && (this.target.isAlive())){
			if (this.justAte){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	protected double getFollowDistance() {
		return this.entity.getAttributeValue(Attributes.FOLLOW_RANGE);
	}
	public void start() {
		super.start();
		this.justAte = false;
	}
	public void tick() {
		super.tick();
		if (((this.entity.tickCount) % (20)) == (0)){
			this.entity.getNavigation().moveTo((Entity)(this.target),1.2D);
		}
		if (((this.target) != (null)) && ((this.entity.distanceToSqr((Entity)(this.target))) < (25D))){
			this.target.remove(Entity.RemovalReason.DISCARDED);
			this.entity.level().broadcastEntityEvent((Entity)(this.target),(byte)(3));
			int var1 = 0;
			do{
				if ((var1) < (20)){
					double var2 = (this.entity.getRandom().nextGaussian()) * (0.02D);
					double var3 = (this.entity.getRandom().nextGaussian()) * (0.02D);
					double var4 = (this.entity.getRandom().nextGaussian()) * (0.02D);
					this.entity.level().addParticle((ParticleOptions)(ParticleTypes.POOF),this.entity.getRandomX(1D),this.entity.getRandomY(),this.entity.getRandomZ(1D),var2,var3,var4);
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
			this.justAte = true;
		}
	}
	public void stop() {
		super.stop();
	}
	public boolean hasTimePassedSinceLastCheck(float var1) {
		return ((float)(this.entity.level().getGameTime())) >= (((float)(this.lastCheck)) + (var1));
	}
}