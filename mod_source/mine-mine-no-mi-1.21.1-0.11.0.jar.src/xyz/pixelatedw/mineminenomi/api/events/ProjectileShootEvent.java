package xyz.pixelatedw.mineminenomi.api.events;

import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;

public class ProjectileShootEvent extends Event implements ICancellableEvent {
	// Fields
	private NuProjectileEntity projectile;
	private float velocity;
	private float inaccuracy;

	public ProjectileShootEvent(NuProjectileEntity var1, float var2, float var3) {
		this.projectile = var1;
		this.velocity = var2;
		this.inaccuracy = var3;
	}
	public NuProjectileEntity getProjectile() {
		return this.projectile;
	}
	public float getVelocity() {
		return this.velocity;
	}
	public float getInaccuracy() {
		return this.inaccuracy;
	}
}