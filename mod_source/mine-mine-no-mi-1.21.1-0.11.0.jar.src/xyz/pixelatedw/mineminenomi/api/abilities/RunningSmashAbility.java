package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;

public abstract class RunningSmashAbility extends PassiveAbility {
	// Fields
	private final HitTrackerComponent hitTrackerComponent;
	private final DealDamageComponent dealDamageComponent;
	private final RangeComponent rangeComponent;
	protected static final float DEFAULT_RANGE = 1.5F;
	protected static final float DEFAULT_DAMAGE = 2F;
	private float area;
	private float damage;
	private Interval resetInterval;

	public RunningSmashAbility(AbilityCore<? extends RunningSmashAbility> var1) {
		this(var1,1.5F,2F);
	}
	public RunningSmashAbility(AbilityCore<? extends RunningSmashAbility> var1, float var2, float var3) {
		super((AbilityCore)(var1));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.resetInterval = new Interval(20);
		this.area = var2;
		this.damage = var3;
		AbilityComponent[] var4 = new AbilityComponent[]{this.hitTrackerComponent,this.rangeComponent,this.dealDamageComponent};
		super.addComponents(var4);
		super.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::duringPassiveEvent))));
	}
	private void duringPassiveEvent(LivingEntity var1) {
		if (this.resetInterval.canTick()){
			this.hitTrackerComponent.clearHits();
		}
		if ((var1.isSprinting()) && (this.canSmash(var1))){
			List var2 = this.rangeComponent.getTargetsInLine(var1,1F,this.area);
			var2.removeIf((Predicate)(((Predicate<LivingEntity>)((l0) -> {
			return ((l0.getVehicle()) != (null)) && (l0.getVehicle().equals(var1));
		}))));
			Iterator var3 = var2.iterator();
			do{
				if (var3.hasNext()){
					LivingEntity var4 = ((LivingEntity)(var3.next()));
					if ((this.hitTrackerComponent.canHit((Entity)(var4))) && (this.dealDamageComponent.hurtTarget(var1,var4,this.damage))){
						Vec3 var5 = var1.getLookAngle();
						AbilityHelper.setDeltaMovement((Entity)(var4),(var5.x) * (2D),0.2D,(var5.z) * (2D));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
	}
	public abstract boolean canSmash(LivingEntity var0);
}