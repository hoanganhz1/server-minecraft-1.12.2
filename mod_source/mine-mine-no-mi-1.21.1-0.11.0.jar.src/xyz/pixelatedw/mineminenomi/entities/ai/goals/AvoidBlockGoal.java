package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class AvoidBlockGoal extends Goal {
	// Fields
	private PathfinderMob entity;
	private Vec3 toAvoid;
	private Set<Block> blocksToAvoid;
	private TagKey<Block> tagToAvoid;
	private Set<BlockPos> cachedPositions;
	private int radius;
	private long lastCheck;
	private Path path;
	private final PathNavigation pathNav;
	private double speed;
	private double sprintSpeed;

	public AvoidBlockGoal(PathfinderMob var1, Set<Block> var2) {
		this.blocksToAvoid = Sets.newHashSet();
		this.cachedPositions = new HashSet();
		this.radius = 15;
		this.speed = 2D;
		this.sprintSpeed = 4D;
		this.entity = var1;
		this.blocksToAvoid = var2;
		this.pathNav = var1.getNavigation();
	}
	public AvoidBlockGoal(PathfinderMob var1, TagKey<Block> var2) {
		this.blocksToAvoid = Sets.newHashSet();
		this.cachedPositions = new HashSet();
		this.radius = 15;
		this.speed = 2D;
		this.sprintSpeed = 4D;
		this.entity = var1;
		this.tagToAvoid = var2;
		this.pathNav = var1.getNavigation();
	}
	public void setSpeed(double var1) {
		this.speed = var1;
	}
	public void setSprintSpeed(double var1) {
		this.sprintSpeed = var1;
	}
	public boolean canUse() {
		if (((this.entity) != (null)) && (this.entity.isAlive())){
			if (((System.currentTimeMillis()) - (this.lastCheck)) < (1000L)){
				return false;
			}else{
				if ((this.cachedPositions.size()) > (0)){
					Iterator var1 = this.cachedPositions.iterator();
					do{
						if (var1.hasNext()){
							BlockPos var2 = ((BlockPos)(var1.next()));
							int var3 = 0;
							var3 = (var3) | (((this.entity.level().getBlockState(var2).is(this.tagToAvoid)) ? 1 : 0));
							var3 = (var3) | (((this.blocksToAvoid.contains(this.entity.level().getBlockState(var2).getBlock())) ? 1 : 0));
							if (((var3) != (0)) && ((this.entity.distanceToSqr((double)(var2.getX()),(double)(var2.getY()),(double)(var2.getZ()))) < ((double)((this.radius) * (this.radius))))){
								this.toAvoid = DefaultRandomPos.getPosTowards(this.entity,128,64,new Vec3((double)(var2.getX()),(double)(var2.getY()),(double)(var2.getZ())),1.5707963267948966D);
								if ((this.toAvoid) == (null)){
									return false;
								}else{
									this.path = this.pathNav.createPath(this.toAvoid.x,this.toAvoid.y,this.toAvoid.z,0);
									return (this.path) != (null);
								}
							}else{
								if ((var3) == (0)){
									var1.remove();
									continue;
								}else{
									continue;
								}
							}
						}else{
							break;
						}
					} while (true);
					return false;
				}else{
					AvoidBlockGoal var1_1 = this;
					Predicate<BlockState> var2_1 = (l0) -> {
			return (this.blocksToAvoid.contains(l0.getBlock())) || (l0.is(this.tagToAvoid));
		};
					List var3_1 = WyHelper.getNearbyBlocks(this.entity.blockPosition(),(LevelAccessor)(this.entity.level()),this.radius,this.radius,this.radius,var2_1);
					this.lastCheck = System.currentTimeMillis();
					this.cachedPositions.clear();
					this.cachedPositions.addAll((Collection)(var3_1));
					return false;
				}
			}
		}else{
			return false;
		}
	}
	public boolean canContinueToUse() {
		return !(this.pathNav.isDone());
	}
	public void start() {
		this.pathNav.moveTo(this.path,this.speed);
	}
	public void stop() {
		this.toAvoid = null;
	}
	public void tick() {
		if ((this.entity.distanceToSqr(this.toAvoid)) < (49D)){
			this.entity.getNavigation().setSpeedModifier(this.sprintSpeed);
		}else{
			this.entity.getNavigation().setSpeedModifier(this.speed);
		}
		this.pathNav.moveTo(this.path,this.speed);
	}
}