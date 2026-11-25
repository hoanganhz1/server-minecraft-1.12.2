package xyz.pixelatedw.mineminenomi.particles.effects.yami;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class BlackHoleParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = (WyHelper.randomDouble()) / (5D);
		double var8 = (0.05D) + ((double)(Mth.abs(((float)(WyHelper.randomDouble())) / (12F))));
		double var9 = (WyHelper.randomDouble()) / (5D);
		SimpleParticleData var10 = new SimpleParticleData(((ParticleType)(ModParticleTypes.DARKNESS.get())));
		var10.setLife(21);
		var10.setAnimationSpeed(3);
		var10.setSize(20F);
		var10.setMotion(var7,var8,var9);
		var10.setRotation(0F,0F,1F);
		var2.addParticle((ParticleOptions)(var10),true,(var3) + (WyHelper.randomDouble()),var4,(var5) + (WyHelper.randomDouble()),0D,0D,0D);
	}
}