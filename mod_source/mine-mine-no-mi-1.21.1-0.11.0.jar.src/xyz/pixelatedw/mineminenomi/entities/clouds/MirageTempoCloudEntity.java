package xyz.pixelatedw.mineminenomi.entities.clouds;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.CloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MirageTempoCloudEntity extends CloudEntity {
	public MirageTempoCloudEntity(EntityType<? extends MirageTempoCloudEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public MirageTempoCloudEntity(Level var1, LivingEntity var2) {
		super(((EntityType)(ModEntities.MIRAGE_TEMPO_CLOUD.get())),var1,var2);
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && (((this.tickCount) % (2)) == (0))){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.KEMURI_BOSHI.get())),(Entity)(this),this.getX(),this.getY(),this.getZ());
		}
	}
}