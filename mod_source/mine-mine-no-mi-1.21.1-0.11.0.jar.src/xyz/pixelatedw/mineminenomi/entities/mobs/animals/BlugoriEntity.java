package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class BlugoriEntity extends AbstractGorillaEntity {
	public BlugoriEntity(EntityType<? extends BlugoriEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.MOVEMENT_SPEED,0.3D).add(Attributes.KNOCKBACK_RESISTANCE,0.20000000298023224D).add(Attributes.MAX_HEALTH,60D).add(Attributes.ATTACK_DAMAGE,5D).add(Attributes.FOLLOW_RANGE,70D).add(ModAttributes.FALL_RESISTANCE,2D).add(ModAttributes.PUNCH_DAMAGE,7D).add(ModAttributes.TOUGHNESS,2D);
	}
}