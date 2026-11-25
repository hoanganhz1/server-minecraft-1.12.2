package xyz.pixelatedw.mineminenomi.entities.mobs.abilities;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class MirageCloneEntity extends CloneEntity {
	public MirageCloneEntity(EntityType<? extends MirageCloneEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public MirageCloneEntity(Level var1, LivingEntity var2) {
		super(((EntityType)(ModMobs.MIRAGE_CLONE.get())),var1,var2);
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,35D).add(Attributes.MOVEMENT_SPEED,0.25D).add(Attributes.MAX_HEALTH,5D).add(Attributes.KNOCKBACK_RESISTANCE,10D).add(Attributes.ARMOR,2D);
	}
	public void remove(Entity.RemovalReason var1) {
		if (!(this.level().isClientSide)){
			int var2 = 0;
			do{
				if ((var2) < (10)){
					double var3 = WyHelper.randomDouble();
					double var4 = WyHelper.randomDouble();
					double var5 = WyHelper.randomDouble();
					if (((var2) % (2)) == (0)){
						((ServerLevel)(this.getCommandSenderWorld())).sendParticles((ParticleOptions)(ParticleTypes.CLOUD),(this.getX()) + (var3),((this.getY()) + (1.5D)) + (var4),(this.getZ()) + (var5),1,0D,0D,0D,0.05D);
					}else{
						((ServerLevel)(this.getCommandSenderWorld())).sendParticles((ParticleOptions)(ParticleTypes.POOF),(this.getX()) + (var3),((this.getY()) + (1.5D)) + (var4),(this.getZ()) + (var5),1,0D,0D,0D,0.05D);
					}
					var2++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		super.remove(var1);
	}
}