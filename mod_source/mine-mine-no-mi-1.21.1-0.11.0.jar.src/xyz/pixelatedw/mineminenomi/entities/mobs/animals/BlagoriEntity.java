package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class BlagoriEntity extends AbstractGorillaEntity {
	public BlagoriEntity(EntityType<? extends BlagoriEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.MOVEMENT_SPEED,0.3D).add(Attributes.KNOCKBACK_RESISTANCE,0.4000000059604645D).add(Attributes.MAX_HEALTH,80D).add(Attributes.ATTACK_DAMAGE,7D).add(Attributes.FOLLOW_RANGE,80D).add(ModAttributes.FALL_RESISTANCE,2D).add(ModAttributes.PUNCH_DAMAGE,9D).add(ModAttributes.TOUGHNESS,4D);
	}
}