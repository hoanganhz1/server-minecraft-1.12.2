package xyz.pixelatedw.mineminenomi.particles.effects;

public interface ParticleEffect$IFactory<A extends ParticleEffect$Details> {
	public abstract A create();
}