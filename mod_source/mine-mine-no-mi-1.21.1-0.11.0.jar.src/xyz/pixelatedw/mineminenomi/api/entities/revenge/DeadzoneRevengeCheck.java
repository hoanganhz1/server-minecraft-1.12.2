package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.effects.InEventEffect;
import xyz.pixelatedw.mineminenomi.effects.UnconsciousEffect;

public class DeadzoneRevengeCheck extends BaseRevengeCheck implements ITickRevengeCheck {
	// Fields
	private final int revengeGain;
	private final float deadzone = 2F;
	private Vec3 pivot;

	public DeadzoneRevengeCheck(int var1) {
		this.revengeGain = var1;
	}
	public boolean check(LivingEntity var1) {
		if (var1 instanceof Mob){
			Mob var2 = ((Mob)(var1));
			if (((var2.getTarget()) != (null)) && (((var1.getLastHurtMob()) != (null)) && ((var2.getTarget().equals(var1.getLastHurtMob())) && (((var1.getLastHurtMobTimestamp()) + (50)) > (var1.tickCount))))){
				return false;
			}
		}
		Iterator var2_1 = var1.getActiveEffects().iterator();
		do{
			if (var2_1.hasNext()){
				MobEffectInstance var3 = ((MobEffectInstance)(var2_1.next()));
				if ((!(var3.getEffect() instanceof InEventEffect)) && (!(var3.getEffect() instanceof UnconsciousEffect))){
					continue;
				}else{
					return false;
				}
			}else{
				break;
			}
		} while (true);
		if ((this.pivot) == (null)){
			this.pivot = var1.position();
		}
		Objects.requireNonNull(this);
		Objects.requireNonNull(this);
		if ((this.pivot.distanceTo(var1.position())) < ((double)((2F) * (2F)))){
			this.pivot = var1.position();
			return true;
		}else{
			this.pivot = var1.position();
			return false;
		}
	}
	public void resetMarkers() {
		super.resetMarkers();
		this.pivot = null;
	}
	public int revengeMeterGain() {
		return this.revengeGain;
	}
}