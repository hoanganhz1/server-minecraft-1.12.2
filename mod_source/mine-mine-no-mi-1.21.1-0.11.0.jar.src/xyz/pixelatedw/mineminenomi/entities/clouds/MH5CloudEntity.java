package xyz.pixelatedw.mineminenomi.entities.clouds;

import java.util.Iterator;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.abilities.MH5Ability;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.entities.CloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MH5CloudEntity extends CloudEntity {
	public MH5CloudEntity(EntityType<? extends MH5CloudEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public MH5CloudEntity(Level var1, LivingEntity var2) {
		super(((EntityType)(ModEntities.MH5_CLOUD.get())),var1,var2);
	}
	public void remove(Entity.RemovalReason var1) {
		Iterator var2 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),(LivingEntity)(null),this.position(),100D,TargetPredicate.EVERYTHING,LivingEntity.class).iterator();
		do{
			if (var2.hasNext()){
				LivingEntity var3 = ((LivingEntity)(var2.next()));
				if ((var3.isUnderWater()) || (ItemsHelper.hasItemInSlot(var3,EquipmentSlot.HEAD,((Item)(ModArmors.MH5_GAS_MASK.get()))))){
					continue;
				}else{
					var3.hurt((DamageSource)(ModDamageSources.getInstance((LevelAccessor)(this.level())).ability((Entity)(this),((AbilityCore)(MH5Ability.INSTANCE.get())))),3.4028235e+38F);
					continue;
				}
			}else{
				break;
			}
		} while (true);
		super.remove(var1);
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && (((this.tickCount) % (2)) == (0))){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.MH5_GAS.get())),(Entity)(this),this.getX(),this.getY(),this.getZ());
		}
	}
}