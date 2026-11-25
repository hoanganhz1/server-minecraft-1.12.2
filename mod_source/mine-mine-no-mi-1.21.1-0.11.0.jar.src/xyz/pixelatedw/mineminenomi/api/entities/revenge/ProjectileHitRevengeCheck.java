package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;

public class ProjectileHitRevengeCheck extends BaseRevengeCheck implements IHitRevengeCheck {
	// Fields
	private final int revengeGain;
	private float prevHealth;
	private int hitCount;

	public ProjectileHitRevengeCheck(int var1) {
		this.revengeGain = var1;
	}
	public boolean check(LivingEntity var1, DamageSource var2) {
		if ((this.prevHealth) == (0F)){
			this.prevHealth = var1.getHealth();
		}
		int var3 = 0;
		if ((var2) != (null)){
			var3 = (var3) | (((var2.is(DamageTypes.MOB_PROJECTILE)) ? 1 : 0));
			var3 = (var3) | (((var2.getDirectEntity() instanceof NuProjectileEntity) ? 1 : 0));
		}
		if (((var3) != (0)) && ((var1.getHealth()) < (this.prevHealth))){
			this.hitCount = (this.hitCount) + (1);
			this.prevHealth = var1.getHealth();
			return true;
		}else{
			return false;
		}
	}
	public void resetMarkers() {
		super.resetMarkers();
		this.prevHealth = 0F;
		this.hitCount = 0;
	}
	public int revengeMeterGain() {
		return this.revengeGain;
	}
	public int getHits() {
		return this.hitCount;
	}
}