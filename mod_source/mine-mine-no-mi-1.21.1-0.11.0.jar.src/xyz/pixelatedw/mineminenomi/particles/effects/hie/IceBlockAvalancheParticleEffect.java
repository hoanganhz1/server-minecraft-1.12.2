package xyz.pixelatedw.mineminenomi.particles.effects.hie;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class IceBlockAvalancheParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		double var7 = 0D;
		LOOP_1:
		do{
			if ((var7) < (3.141592653589793D)){
				var7 = (var7) + (0.7853981633974483D);
				double var8 = 0D;
				do{
					if ((var8) <= (6.283185307179586D)){
						double var9 = (((8D) * (Math.cos(var8))) * (Math.sin(var7))) + (WyHelper.randomDouble());
						double var10 = ((var4) - (3D)) - (1D);
						double var11 = (((8D) * (Math.sin(var8))) * (Math.sin(var7))) + (WyHelper.randomDouble());
						double var12 = (var9) / (40D);
						double var13 = -0.2D;
						double var14 = (var11) / (40D);
						SimpleParticleData var15 = new SimpleParticleData(((ParticleType)(ModParticleTypes.HIE.get())));
						var15.setLife(14);
						var15.setAnimationSpeed(2);
						var15.setRotation(0F,0F,1F);
						var15.setRotationSpeed((((var8) % (2D)) == (0D)) ? (0.07F) : (-0.07F));
						var15.setSize(10F);
						var15.setMotion(-(var12),var13,-(var14));
						if ((Math.random()) > (0.7D)){
							var2.addParticle((ParticleOptions)(var15),true,((var3) + (var9)) + (WyHelper.randomDouble()),var10,((var5) + (var11)) + (WyHelper.randomDouble()),0D,0D,0D);
						}
						var8 = (var8) + (0.5235987755982988D);
						continue;
					}else{
						continue LOOP_1;
					}
				} while (true);
			}else{
				break;
			}
		} while (true);
	}
}