package xyz.pixelatedw.mineminenomi.particles.effects.horu;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class WinkParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = ((double)(WyHelper.randomWithRange(-2,2))) + (WyHelper.randomDouble());
		double var8 = ((double)(WyHelper.randomWithRange(-2,2))) + (WyHelper.randomDouble());
		double var9 = ((double)(WyHelper.randomWithRange(-2,2))) + (WyHelper.randomDouble());
		double var10 = 0.1D;
		var10 = (var10) * (((WyHelper.randomDouble()) * (2D)) + (0.25D));
		var7 = (var7) * ((var10) / (5D));
		var8 = (var8) * ((var10) / (5D));
		var9 = (var9) * ((var10) / (5D));
		SimpleParticleData var11 = new SimpleParticleData(((ParticleType)(ModParticleTypes.HORU.get())));
		var11.setLife(5);
		var11.setSize(5F);
		var11.setMotion(var7,var8,var9);
		var2.addParticle((ParticleOptions)(var11),var3,(var4) + (1.5D),var5,0D,0D,0D);
	}
}