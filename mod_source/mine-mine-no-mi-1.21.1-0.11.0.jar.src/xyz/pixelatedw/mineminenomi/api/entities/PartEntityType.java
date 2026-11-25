package xyz.pixelatedw.mineminenomi.api.entities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.entity.PartEntity;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;

public class PartEntityType<P extends PartEntity<T>, T extends Entity> {
	// Fields
	private final PartEntityType$PartEntityFactory<P, T> factory;

	public PartEntityType(PartEntityType$PartEntityFactory<P, T> var1) {
		this.factory = var1;
	}
	public ResourceLocation getKey() {
		return WyRegistry.PART_ENTITY_TYPES.getKey(this);
	}
	public P create(T var1) {
		return (P) (this.factory.create(var1));
	}
}