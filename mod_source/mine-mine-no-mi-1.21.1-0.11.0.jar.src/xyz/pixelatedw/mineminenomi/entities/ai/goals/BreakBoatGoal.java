package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.data.entity.KairosekiCoatingData;

public class BreakBoatGoal extends Goal {
	// Fields
	private final PathfinderMob entity;
	private Entity boat;

	public BreakBoatGoal(PathfinderMob var1) {
		this.entity = var1;
	}
	public boolean canUse() {
		Iterator var1 = this.entity.level().getEntitiesOfClass(Boat.class,this.entity.getBoundingBox().expandTowards(2D,2D,2D)).iterator();
		do{
			if (var1.hasNext()){
				Boat var2 = ((Boat)(var1.next()));
				LivingEntity var3 = var2.getControllingPassenger();
				Function<KairosekiCoatingData, Boolean> var4 = (l0) -> {
			return Boolean.valueOf(l0.isFullyCoated());
		};
				boolean var5 = ((Boolean)(((java.util.Optional)(KairosekiCoatingData.get((Entity)(this.entity)))).map((Function)(var4)).orElse(Boolean.valueOf(false)))).booleanValue();
				if (((var3) != (null)) && ((var3 instanceof LivingEntity) && ((var2.isAlive()) && ((GoalHelper.canSee((Mob)(this.entity),(Entity)(var2))) && (var5))))){
					this.boat = var2;
					break;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return ((this.boat) != (null)) && (this.boat.isAlive());
	}
	public boolean canContinueToUse() {
		return ((this.boat) != (null)) && (this.boat.isAlive());
	}
	public void start() {
		AttributeInstance var1 = this.entity.getAttribute(Attributes.ATTACK_DAMAGE);
		float var2 = (float)(((var1) != (null)) ? (var1.getValue()) : (1D));
		this.boat.hurt(this.entity.damageSources().mobAttack((LivingEntity)(this.entity)),var2);
		this.boat = null;
	}
	public void stop() {
		this.boat = null;
	}
}