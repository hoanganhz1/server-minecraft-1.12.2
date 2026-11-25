package xyz.pixelatedw.mineminenomi.entities;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class BombEntity extends Entity implements TraceableEntity {
	// Fields
	private final TargetPredicate targetPredicate;
	private final LivingEntity owner;
	private int fuseTime;
	private boolean canDestroyBlocks;
	private boolean explodeOnImpact;

	public BombEntity(EntityType<? extends BombEntity> var1, Level var2) {
		super(var1,var2);
		this.fuseTime = 300;
		this.canDestroyBlocks = false;
		this.explodeOnImpact = false;
		this.owner = null;
		this.targetPredicate = new TargetPredicate().selector((java.util.function.Predicate)(Predicates.alwaysTrue()));
	}
	public BombEntity(Level var1, LivingEntity var2) {
		super(((EntityType)(ModEntities.BOMB.get())),var1);
		this.fuseTime = 300;
		this.canDestroyBlocks = false;
		this.explodeOnImpact = false;
		this.owner = var2;
		LivingEntity var3 = var2;
		final LivingEntity var3_f1 = var3;
		java.util.function.Predicate<Entity> var4 = (l0) -> {
			if (l0.equals(var3_f1)){
				return false;
			}else{
				if (l0 instanceof BombEntity){
					return false;
				}else{
					if (ModEntityPredicates.getFriendlyFactions(var3_f1).test(l0)){
						return false;
					}else{
						return true;
					}
				}
			}
		};
		this.targetPredicate = new TargetPredicate().selector(var4);
	}
	public boolean canCollideWith(Entity var1) {
		if (var1 instanceof BombEntity){
			return false;
		}else{
			return true;
		}
	}
	public void setFuseTime(int var1) {
		this.fuseTime = var1;
	}
	public void setDestroyBlocks() {
		this.canDestroyBlocks = true;
	}
	public void setExplodeOnImpact() {
		this.explodeOnImpact = true;
	}
	public void tick() {
		Level var1 = super.level();
		if ((var1) == (null)){
			return;
		}else{
			this.xo = this.getX();
			this.yo = this.getY();
			this.zo = this.getZ();
			if (((this.tickCount) % (3)) == (0)){
				var1.addParticle((ParticleOptions)(ParticleTypes.FLAME),this.getX(),(this.getY()) + (1D),this.getZ(),0D,0D,0D);
			}
			if (!(var1.isClientSide)){
				this.move(MoverType.SELF,this.getDeltaMovement());
				if (!(this.isNoGravity())){
					AbilityHelper.setDeltaMovement(this,this.getDeltaMovement().add(0D,-0.09799999743700027D,0D));
					AbilityHelper.setDeltaMovement(this,this.getDeltaMovement().multiply(0.98D,0.98D,0.98D));
					if ((!(this.verticalCollision)) && (!(this.horizontalCollision))){

					}else{
						AbilityHelper.setDeltaMovement(this,Vec3.ZERO);
						if (this.explodeOnImpact){
							this.kill();
						}
					}
				}
				if ((((this.tickCount) % (10)) == (0)) && ((TargetHelper.getEntitiesInArea((LevelAccessor)(var1),(LivingEntity)(null),this.blockPosition(),2D,this.targetPredicate,LivingEntity.class).size()) > (0))){
					this.kill();
				}
				if ((this.tickCount) >= (this.fuseTime)){
					this.kill();
				}else{
					return;
				}
			}
			return;
		}
	}
	public void kill() {
		Level var1 = super.level();
		if ((var1) == (null)){
			return;
		}else{
			if (!(var1.isClientSide)){
				AbilityExplosion var2 = new AbilityExplosion((Entity)(this),(IAbility)(null),this.getX(),this.getY(),this.getZ(),4F);
				var2.setExplosionSound(true);
				var2.setDamageOwner(false);
				var2.setDestroyBlocks(this.canDestroyBlocks);
				var2.setFireAfterExplosion(false);
				var2.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION4));
				var2.setDamageEntities(true);
				var2.explode();
			}
			super.kill();
			return;
		}
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
	public boolean isPushable() {
		return false;
	}
	public boolean canBeCollidedWith() {
		return true;
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
	}
	@Nullable()
	public Entity getOwner() {
		return this.owner;
	}
}