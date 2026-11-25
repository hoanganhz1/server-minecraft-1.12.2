package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.IWithHome;

public class RandomWalkingAroundHomeGoal<E extends PathfinderMob & IWithHome> extends RandomStrollGoal {
	// Fields
	protected E mob;
	protected final float probability;
	private int distance;
	private int offset;

	public RandomWalkingAroundHomeGoal(E var1, double var2) {
		this(var1,var2,0.001F);
		this.mob = var1;
	}
	public RandomWalkingAroundHomeGoal(E var1, double var2, float var3) {
		super(var1,var2);
		this.distance = 10;
		this.offset = 7;
		this.mob = var1;
		this.probability = var3;
	}
	public RandomWalkingAroundHomeGoal<E> setWalkDistance(int var1) {
		this.distance = var1;
		return this;
	}
	public RandomWalkingAroundHomeGoal<E> setWalkOffset(int var1) {
		this.offset = var1;
		return this;
	}
	@Nullable()
	protected Vec3 getPosition() {
		if (!(((IWithHome)(this.mob)).getHomePosition().isPresent())){
			return super.getPosition();
		}else{
			Vec3 var1 = ((Vec3)(((IWithHome)(this.mob)).getHomePosition().get()));
			if (this.mob.isInWaterOrBubble()){
				Vec3 var2 = WyHelper.findValidGroundLocation(this.mob.level(),var1,(this.distance) * (2),this.offset);
				return ((var2) == (null)) ? (super.getPosition()) : (var2);
			}else{
				if ((this.mob.getRandom().nextFloat()) >= (this.probability)){
					return WyHelper.findValidGroundLocation(this.mob.level(),var1,this.distance,this.offset);
				}else{
					return super.getPosition();
				}
			}
		}
	}
}