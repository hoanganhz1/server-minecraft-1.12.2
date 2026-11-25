package xyz.pixelatedw.mineminenomi.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.rendertypes.SimpleParticleRenderType;

public class SimpleParticle$Factory implements ParticleProvider<SimpleParticleData> {
	// Fields
	 ParticleRenderType type;
	 int maxFrames;

	public SimpleParticle$Factory(ResourceLocation var1) {
		this(var1,1);
	}
	public SimpleParticle$Factory(ResourceLocation var1, int var2) {
		this.maxFrames = 0;
		this.type = new SimpleParticleRenderType(var1);
		this.maxFrames = var2;
	}
	public Particle createParticle(SimpleParticleData var1, ClientLevel var2, double var3, double var4, double var5, double var6, double var7, double var8) {
		SimpleParticle var9 = new SimpleParticle(var1,this.type,var2,var3,var4,var5,var1.getDeltaMovementX(),var1.getDeltaMovementY(),var1.getDeltaMovementZ());
		var9.setMaxFrames(this.maxFrames);
		return var9;
	}
}