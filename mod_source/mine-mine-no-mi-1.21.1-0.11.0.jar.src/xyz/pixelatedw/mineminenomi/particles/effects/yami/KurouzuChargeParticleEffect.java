package xyz.pixelatedw.mineminenomi.particles.effects.yami;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class KurouzuChargeParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		SimpleParticleData var7 = new SimpleParticleData(((ParticleType)(ModParticleTypes.KUROUZU.get())));
		var7.setColor(1F,1F,1F,0.25F);
		var7.setLife(1);
		var7.setSize(10F);
		var7.setHasScaleDecay(false);
		var7.setRotation(0F,0F,1F);
		var2.addParticle((ParticleOptions)(var7),true,var3,var4,var5,0D,0D,0D);
	}
}