package xyz.pixelatedw.mineminenomi.entities.clouds;

import java.util.Iterator;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.CloudEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.ChloroBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class ChloroBallCloudEntity extends CloudEntity {
	// Fields
	private ChloroBallProjectile proj;
	private static final Interval EXPANSION_INTERVAL = Interval.startAtMax(5);

	public ChloroBallCloudEntity(EntityType<? extends ChloroBallCloudEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public ChloroBallCloudEntity(Level var1, ChloroBallProjectile var2) {
		super(((EntityType)(ModEntities.CHLORO_BALL_CLOUD.get())),var1,var2.getOwner());
		this.proj = var2;
	}
	public void tick() {
		super.tick();
		if (!(super.level().isClientSide())){
			if ((this.proj) == (null)){
				super.discard();
				return;
			}else{
				Iterator var1 = TargetHelper.getEntitiesInArea((LevelAccessor)(super.level()),super.getOwner(),this.position(),(double)(this.proj.getPoisonRange()),TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class).iterator();
				do{
					if (var1.hasNext()){
						LivingEntity var2 = ((LivingEntity)(var1.next()));
						if (!(var2.hasEffect(MobEffects.BLINDNESS))){
							var2.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,200,this.proj.getPoisonAmplifier()));
						}
						if (!(var2.hasEffect(MobEffects.MOVEMENT_SLOWDOWN))){
							var2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,200,this.proj.getPoisonAmplifier()));
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				if (((this.tickCount) % (2)) == (0)){
					float var2_1 = (this.getBbHeight()) / (2F);
					float var3 = (this.getBbWidth()) / (2F);
					int var4 = 0;
					do{
						if (((float)(var4)) < (((var3) + (var2_1)) * (2F))){
							double var5 = ((this.random.nextDouble()) * (3.141592653589793D)) * (2D);
							double var6 = (Math.sqrt(this.random.nextDouble())) * ((double)(var3));
							double var7 = (this.getX()) + ((Math.cos(var5)) * (var6));
							double var8 = (this.getY()) + ((this.random.nextDouble()) * ((double)(this.getBbHeight())));
							double var9 = (this.getZ()) + ((Math.sin(var5)) * (var6));
							WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.CHLORO_BALL_CLOUD.get())),(Entity)(this),var7,var8,var9,this.proj.getPoisonParticles());
							var4++;
							continue;
						}else{
							break;
						}
					} while (true);
				}
			}
		}
		EntityDimensions var1_1 = super.getDimensions(Pose.STANDING);
		super.setSize(EntityDimensions.fixed((var1_1.width()) + (0.25F),(var1_1.height()) + (0.05F)));
	}
}