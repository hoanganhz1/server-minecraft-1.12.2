package xyz.pixelatedw.mineminenomi.particles.effects.mero;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class SlaveArrowParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		SimpleParticleData var7 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERO.get())));
		var7.setLife(60);
		var7.setSize(20F);
		var7.setColor(1F,1F,1F,0.4F);
		var7.setHasScaleDecay(false);
		var7.setFunction(EasingFunction.ELASTIC_IN);
		var2.addParticle((ParticleOptions)(var7),true,var3,(var4) + (1D),var5,0D,0D,0D);
	}
}