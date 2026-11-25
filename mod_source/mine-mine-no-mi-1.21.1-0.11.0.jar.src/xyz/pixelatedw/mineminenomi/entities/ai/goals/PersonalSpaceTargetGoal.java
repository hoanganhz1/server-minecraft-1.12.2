package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;

public class PersonalSpaceTargetGoal extends TickedGoal<Mob> {
	// Fields
	private static final int TICKS_BEFORE_MARK_UPDATE = 200;
	private HashMap<Integer, Long> personalSpaceTargets;
	private Interval checkInterval;
	private final int ticksBeforeAttacking;
	private final double checkDistance;
	private final Predicate<LivingEntity> targetCheck;

	public PersonalSpaceTargetGoal(Mob var1) {
		this(var1,10D,200);
	}
	public PersonalSpaceTargetGoal(Mob var1, double var2) {
		this(var1,var2,200);
	}
	public PersonalSpaceTargetGoal(Mob var1, double var2, int var3) {
		super(var1);
		this.personalSpaceTargets = (HashMap) (new HashMap());
		this.checkInterval = new Interval(20);
		this.checkDistance = var2;
		this.ticksBeforeAttacking = var3;
		this.targetCheck = this.getDefaultTargetCheck((LivingEntity)(var1));
	}
	public PersonalSpaceTargetGoal(Mob var1, double var2, int var3, Predicate<LivingEntity> var4) {
		super(var1);
		this.personalSpaceTargets = (HashMap) (new HashMap());
		this.checkInterval = new Interval(20);
		this.checkDistance = var2;
		this.ticksBeforeAttacking = var3;
		this.targetCheck = var4;
	}
	public boolean canUse() {
		if (!(this.entity.isAlive())){
			return false;
		}else{
			if (GoalHelper.hasAliveTarget(this.entity)){
				return false;
			}else{
				if (!(this.hasTimePassedSinceLastEnd(60F))){
					return false;
				}else{
					return true;
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (!(this.entity.isAlive())){
			return false;
		}else{
			if (GoalHelper.hasAliveTarget(this.entity)){
				return false;
			}else{
				if ((GoalHelper.getNearbyVisibleEntities((LivingEntity)(this.entity),this.checkDistance,(Predicate)(null)).size()) <= (0)){
					return false;
				}else{
					return true;
				}
			}
		}
	}
	public void start() {
		super.start();
		this.checkInterval.restartIntervalToMax();
		this.personalSpaceTargets.clear();
	}
	public void tick() {
		super.tick();
		if (this.checkInterval.canTick()){
			List var1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.entity.level()),(LivingEntity)(this.entity),this.checkDistance,(TargetPredicate)(null),LivingEntity.class);
			if ((var1.size()) > (0)){
				Iterator var2 = var1.iterator();
				do{
					if (var2.hasNext()){
						LivingEntity var3 = ((LivingEntity)(var2.next()));
						if (!(this.targetCheck.test(var3))){
							continue;
						}else{
							if (this.personalSpaceTargets.containsKey(Integer.valueOf(var3.getId()))){
								long var4 = ((Long)(this.personalSpaceTargets.get(Integer.valueOf(var3.getId())))).longValue();
								if ((this.entity.level().getGameTime()) >= ((var4) + ((long)(this.ticksBeforeAttacking)))){
									this.entity.setTarget(var3);
									continue;
								}else{
									continue;
								}
							}else{
								this.personalSpaceTargets.put(Integer.valueOf(var3.getId()),Long.valueOf(this.entity.level().getGameTime()));
								continue;
							}
						}
					}else{
						break;
					}
				} while (true);
			}else{
				return;
			}
		}
	}
	public void stop() {
		super.stop();
	}
	public Predicate<LivingEntity> getDefaultTargetCheck(LivingEntity var1) {
		LivingEntity var2 = var1;
		final LivingEntity var2_f1 = var2;
		Predicate<LivingEntity> var3 = (l0) -> {
			if (l0 instanceof Player){
				return true;
			}else{
				return !(((java.lang.Object)(l0.getType())).equals((Object)(var2_f1.getType())));
			}
		};
		return var3;
	}
}