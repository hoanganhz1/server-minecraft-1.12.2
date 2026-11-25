package xyz.pixelatedw.mineminenomi.api.entities;

import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;

@Deprecated()
public interface ITamableEntity {
	@Deprecated()
	public abstract LivingEntity getOwner();
	@Deprecated()		
@Nullable()
	public default LivingEntity getOwnerIfAlive() {
		LivingEntity var1 = this.getOwner();
		return ((var1) != (null)) ? ((var1.isAlive()) ? (var1) : (null)) : (null);
	}
}