package xyz.pixelatedw.mineminenomi.entities.clouds;

import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.entities.CloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KemuriBoshiCloudEntity extends CloudEntity {
	public KemuriBoshiCloudEntity(EntityType<? extends KemuriBoshiCloudEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public KemuriBoshiCloudEntity(Level var1, LivingEntity var2) {
		super(((EntityType)(ModEntities.KEMURI_BOSHI_CLOUD.get())),var1,var2);
	}
	public void tick() {
		super.tick();
		if (!(this.level().isClientSide)){
			Iterator var1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),this.getOwner(),this.blockPosition(),6D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class).iterator();
			do{
				if (var1.hasNext()){
					LivingEntity var2 = ((LivingEntity)(var1.next()));
					if (((this.getOwner()) != (var2)) && (!(var2.hasEffect(MobEffects.POISON)))){
						var2.addEffect(new MobEffectInstance(MobEffects.POISON,80,1));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			if (((this.tickCount) % (2)) == (0)){
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.KEMURI_BOSHI.get())),(Entity)(this),this.getX(),this.getY(),this.getZ());
			}else{
				return;
			}
		}
	}
}