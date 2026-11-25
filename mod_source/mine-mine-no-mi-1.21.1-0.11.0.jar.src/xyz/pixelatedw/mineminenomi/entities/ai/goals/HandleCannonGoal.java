package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.StructuresHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.entities.vehicles.CannonEntity;

public class HandleCannonGoal extends TickedGoal<Mob> {
	// Fields
	private CannonEntity cannon;
	private LivingEntity target;
	private long lastShot;

	public HandleCannonGoal(Mob var1) {
		super(var1);
	}
	public boolean canUse() {
		if (((this.entity.tickCount) % (100)) != (0)){
			return false;
		}else{
			if (this.entity.isPassenger()){
				return false;
			}else{
				if (!(GoalHelper.hasAliveTarget(this.entity))){
					return false;
				}else{
					this.target = this.entity.getTarget();
					if (GoalHelper.isWithinDistance((LivingEntity)(this.entity),this.target,5D)){
						return false;
					}else{
						StructureStart var1 = StructuresHelper.getStructureAt(((ServerLevel)(this.entity.level())),this.entity.blockPosition());
						StructureStart var2 = StructuresHelper.getStructureAt(((ServerLevel)(this.entity.level())),this.target.blockPosition());
						if (((var1) != (null)) && ((var1) == (var2))){
							return false;
						}else{
							List var3 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.entity.level()),(LivingEntity)(this.entity),5D,TargetPredicate.EVERYTHING,CannonEntity.class);
							Predicate<CannonEntity> var4 = (l0) -> {
			return !(l0.isVehicle());
		};
							Optional var5 = var3.stream().filter(var4).findFirst();
							if (!(var5.isPresent())){
								return false;
							}else{
								this.cannon = ((CannonEntity)(var5.get()));
								return true;
							}
						}
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (((this.cannon) != (null)) && (this.cannon.isAlive())){
			if (!(GoalHelper.hasAliveTarget(this.entity))){
				return false;
			}else{
				if (GoalHelper.isWithinDistance((LivingEntity)(this.entity),this.target,5D)){
					return false;
				}else{
					StructureStart var1 = StructuresHelper.getStructureAt(((ServerLevel)(this.entity.level())),this.entity.blockPosition());
					StructureStart var2 = StructuresHelper.getStructureAt(((ServerLevel)(this.entity.level())),this.target.blockPosition());
					if (((var1) != (null)) && ((var1) == (var2))){
						return false;
					}else{
						return true;
					}
				}
			}
		}else{
			return false;
		}
	}
	public void start() {
		super.start();
		this.entity.startRiding((Entity)(this.cannon),true);
	}
	public void tick() {
		super.tick();
		GoalHelper.lookAtEntity(this.entity,(Entity)(this.target));
		this.cannon.setXRot((this.entity.getXRot()) - (8F));
		this.cannon.setYRot(this.entity.getYRot());
		if ((this.lastShot) == (0L)){
			this.lastShot = this.entity.level().getGameTime();
		}
		if ((this.entity.level().getGameTime()) >= ((this.lastShot) + (100L))){
			this.entity.swing(InteractionHand.MAIN_HAND);
			this.lastShot = this.entity.level().getGameTime();
		}
	}
	public void stop() {
		super.stop();
		this.entity.stopRiding();
	}
}