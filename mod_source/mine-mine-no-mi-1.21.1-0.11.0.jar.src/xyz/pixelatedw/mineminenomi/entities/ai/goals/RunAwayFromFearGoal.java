package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public class RunAwayFromFearGoal extends TickedGoal<Mob> {
	// Fields
	private static final int COOLDOWN = 100;
	private int targetPosX;
	private int targetPosY;
	private int targetPosZ;
	private double speed;
	private LivingEntity target;
	private final double doriki;
	private final Predicate<Entity> filter;

	public RunAwayFromFearGoal(Mob var1, double var2, double var3) {
		super(var1);
		this.speed = var2;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE,Goal.Flag.TARGET));
		this.doriki = ((Double)(EntityStatsData.get((LivingEntity)(var1)).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue();
		Mob var4 = var1;
		double var5 = var3;
		final Mob var4_f1 = var4;
		final double var5_f2 = var5;
		Predicate<Entity> var6 = (l0) -> {
			if (!(l0 instanceof LivingEntity)){
				return false;
			}else{
				if (MobsHelper.shouldAttackTarget((LivingEntity)(var4_f1)).test(l0)){
					return false;
				}else{
					double lv1_3 = (double)(MobsHelper.getThreatLevel((LivingEntity)(var4_f1)));
					if (((double)(MobsHelper.getThreatLevel(((LivingEntity)(l0))))) > ((lv1_3) * (var5_f2))){
						return true;
					}else{
						return false;
					}
				}
			}
		};
		this.filter = (Predicate) (EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(ModEntityPredicates.getEnemyFactions((LivingEntity)(this.entity))).and(var6));
	}
	public boolean canUse() {
		if (NuWorld.isChallengeDimension(this.entity.level())){
			return false;
		}else{
			if ((this.doriki) >= (6000D)){
				return false;
			}else{
				if (((this.getLastEndTick()) != (0L)) && (!(this.hasTimePassedSinceLastEnd(100F)))){
					return false;
				}else{
					Optional var1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.entity.level()),(LivingEntity)(this.entity),20D,new TargetPredicate().selector(this.filter),LivingEntity.class).stream().findAny();
					if (var1.isPresent()){
						this.target = ((LivingEntity)(var1.get()));
						return true;
					}else{
						return false;
					}
				}
			}
		}
	}
	public void start() {
	}
	public void tick() {
		if (((this.target) != (null)) && ((this.target.isAlive()) && (this.entity.getNavigation().isDone()))){
			BlockPos var1 = this.target.blockPosition();
			this.targetPosX = (int)((this.entity.getX()) - (((double)(var1.getX())) - (this.entity.getX())));
			this.targetPosY = (int)((this.entity.getY()) - (((double)(var1.getY())) - (this.entity.getY())));
			this.targetPosZ = (int)((this.entity.getZ()) - (((double)(var1.getZ())) - (this.entity.getZ())));
			this.entity.getNavigation().moveTo((double)(this.targetPosX),(double)(this.targetPosY),(double)(this.targetPosZ),this.speed);
		}
	}
	public boolean canContinueToUse() {
		return ((this.target) != (null)) && ((this.target.isAlive()) && ((this.entity.distanceTo((Entity)(this.target))) < (40F)));
	}
}