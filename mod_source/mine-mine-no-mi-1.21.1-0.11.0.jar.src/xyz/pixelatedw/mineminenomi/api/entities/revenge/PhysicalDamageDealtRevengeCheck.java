package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;

public class PhysicalDamageDealtRevengeCheck extends BaseRevengeCheck implements IHitRevengeCheck {
	// Fields
	private float prevHealth;
	private float lostHealth;
	private float multiplier;

	public PhysicalDamageDealtRevengeCheck() {
		this.multiplier = 1F;
	}
	public PhysicalDamageDealtRevengeCheck(float var1) {
		this.multiplier = 1F;
		this.multiplier = var1;
	}
	public boolean check(LivingEntity var1, DamageSource var2) {
		if ((this.prevHealth) == (0F)){
			this.prevHealth = var1.getHealth();
		}
		boolean var3 = false;
		if ((var2) != (null)){
			var3 = ((((((var3) ? 1 : 0)) | (((var2.is(DamageTypes.PLAYER_ATTACK)) || (var2.is(DamageTypes.MOB_ATTACK))) ? (1) : (0))) & 1) != 0);
			if (var2.is(ModDamageTypes.ABILITY)){
				var3 = IDamageSourceHandler.getHandler(var2).hasType(SourceType.PHYSICAL);
			}
		}
		if ((var3) && ((var1.getHealth()) < (this.prevHealth))){
			this.lostHealth = (this.prevHealth) - (var1.getHealth());
			this.prevHealth = var1.getHealth();
			return true;
		}else{
			return false;
		}
	}
	public void resetMarkers() {
		super.resetMarkers();
		this.prevHealth = 0F;
		this.lostHealth = 0F;
	}
	public int revengeMeterGain() {
		return Math.round((this.lostHealth) * (this.multiplier));
	}
	public float getDamagePool() {
		return this.lostHealth;
	}
}