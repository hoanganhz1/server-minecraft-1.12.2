package xyz.pixelatedw.mineminenomi.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class SpikeEntity extends Entity {
	// Fields
	public static final float DAMAGE = 3F;
	public static final int EFFECT_TIME = 60;
	protected boolean inGround;
	protected List<LivingEntity> targets;

	public SpikeEntity(EntityType<? extends SpikeEntity> var1, Level var2) {
		super(var1,var2);
		this.targets = new ArrayList();
	}
	public SpikeEntity(Level var1) {
		super(((EntityType)(ModEntities.SPIKE.get())),var1);
		this.targets = new ArrayList();
	}
	public void tick() {
		super.tick();
		if (!(this.onGround())){
			this.move(MoverType.SELF,new Vec3(0D,-0.3D,0D));
		}
		if (this.level().isClientSide){
			return;
		}else{
			Iterator var1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),(LivingEntity)(null),this.blockPosition(),0.1D,(TargetPredicate)(null),LivingEntity.class).iterator();
			do{
				if (var1.hasNext()){
					LivingEntity var2 = ((LivingEntity)(var1.next()));
					var2.hurt(this.damageSources().cactus(),3F);
					if ((var2.getRandom().nextDouble()) < (0.5D)){
						var2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,60,1,false,true));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			if ((this.tickCount) > ((1200) + (WyHelper.randomWithRange(0,100)))){
				this.remove(Entity.RemovalReason.DISCARDED);
			}
			return;
		}
	}
	protected float getGravityVelocity() {
		return 0.03F;
	}
	public boolean shouldRenderAtSqrDistance(double var1) {
		if ((var1) < (4096D)){
			return true;
		}else{
			return super.shouldRenderAtSqrDistance(var1);
		}
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
	public boolean canBeCollidedWith() {
		return false;
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
		var1.putBoolean("inGround",this.inGround);
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
		this.inGround = var1.getBoolean("inGround");
	}
}