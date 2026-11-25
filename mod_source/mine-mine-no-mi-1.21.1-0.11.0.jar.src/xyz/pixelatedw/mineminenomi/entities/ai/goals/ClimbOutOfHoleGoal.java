package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class ClimbOutOfHoleGoal extends TickedGoal<Mob> {
	// Fields
	private static final int COOLDOWN = 60;
	private LivingEntity target;
	private int blocksHeight;
	private BlockPos targetPos;

	public ClimbOutOfHoleGoal(Mob var1) {
		super(var1);
		this.blocksHeight = 0;
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (!(this.hasTimePassedSinceLastEnd(60F))){
				return false;
			}else{
				this.target = this.entity.getTarget();
				if ((this.target.getY()) <= (this.entity.getY())){
					return false;
				}else{
					this.blocksHeight = GoalHelper.getFrontWallHeight((LivingEntity)(this.entity));
					if ((this.blocksHeight) <= (0)){
						return false;
					}else{
						BlockPos var1 = this.entity.blockPosition().offset(this.entity.getDirection().getNormal());
						this.targetPos = var1.above((this.blocksHeight) + (1));
						if (!(this.canFit((LivingEntity)(this.entity),this.targetPos))){
							return false;
						}else{
							if (GoalHelper.hasSolidBlockAbove((LivingEntity)(this.entity))){
								return false;
							}else{
								if (!(GoalHelper.hasBlockInFace((LivingEntity)(this.entity)))){
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
	}
	private boolean canFit(LivingEntity var1, BlockPos var2) {
		AABB var3 = var1.getBoundingBox();
		AABB var4 = new AABB(((double)(var2.getX())) - (var3.getXsize()),((double)((var2.getY()) + (1))) - (var3.getYsize()),((double)(var2.getZ())) - (var3.getZsize()),((double)(var2.getX())) + (var3.getXsize()),((double)(var2.getY())) + (var3.getYsize()),((double)(var2.getZ())) + (var3.getZsize()));
		BlockPos.MutableBlockPos var5 = new BlockPos.MutableBlockPos();
		int var6 = ((int)(var4.minY)) + (1);
		LOOP_2:
		do{
			if (((double)(var6)) < ((var4.maxY) + (1D))){
				int var7 = ((int)(var4.minX)) - (1);
				LOOP_1:
				do{
					if (((double)(var7)) < ((var4.maxX) + (1D))){
						int var8 = ((int)(var4.minZ)) - (1);
						do{
							if (((double)(var8)) < ((var4.maxZ) + (1D))){
								var5.set(var7,var6,var8);
								if (!(var1.level().getBlockState((BlockPos)(var5)).isAir())){
									return false;
								}else{
									var8++;
									continue;
								}
							}else{
								break;
							}
						} while (true);
						var7++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				var6++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
		return true;
	}
	public boolean canContinueToUse() {
		return false;
	}
	public void start() {
		super.start();
		this.entity.moveTo(this.targetPos,this.entity.getYRot(),this.entity.getXRot());
		this.entity.getNavigation().stop();
	}
	public void tick() {
		super.tick();
	}
	public void stop() {
		super.stop();
	}
}