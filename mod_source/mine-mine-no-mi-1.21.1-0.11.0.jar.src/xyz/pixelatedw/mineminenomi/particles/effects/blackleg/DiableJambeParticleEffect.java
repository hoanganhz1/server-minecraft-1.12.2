package xyz.pixelatedw.mineminenomi.particles.effects.blackleg;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class DiableJambeParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		if (var1 instanceof LivingEntity){
			LivingEntity var7 = ((LivingEntity)(var1));
			float var8 = ((var7.yBodyRotO) + ((var7.yBodyRot) - (var7.yBodyRotO))) + (30F);
			double var9 = ((double)(Mth.sin(((var8) * (3.1415927F)) / (-180F)))) * (0.2D);
			double var10 = ((double)(Mth.cos(((var8) * (3.1415927F)) / (-180F)))) * (0.2D);
			double var11 = (var9) + ((WyHelper.randomDouble()) / (5D));
			double var12 = (0.6D) + ((WyHelper.randomDouble()) / (2.5D));
			double var13 = (var10) + ((WyHelper.randomDouble()) / (5D));
			int var14 = (3) + (WyHelper.randomWithRange(0,2));
			SimpleParticleData var15 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
			var15.setLife(var14);
			var15.setSize(((float)(var14)) / (2.5F));
			var15.setMotion(0D,0.002D,0D);
			var2.addParticle((ParticleOptions)(var15),true,(var3) + (var11),(var4) + (var12),(var5) + (var13),0D,0D,0D);
		}
	}
}