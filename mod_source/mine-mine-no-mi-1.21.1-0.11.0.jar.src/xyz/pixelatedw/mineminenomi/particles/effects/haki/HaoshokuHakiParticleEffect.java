package xyz.pixelatedw.mineminenomi.particles.effects.haki;

import java.awt.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$IFactory;

public class HaoshokuHakiParticleEffect extends ParticleEffect<HaoshokuHakiParticleEffect$Details> {
	public HaoshokuHakiParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<HaoshokuHakiParticleEffect$Details>) (HaoshokuHakiParticleEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, HaoshokuHakiParticleEffect$Details var6) {
		int var7 = var6.size;
		int var8 = var6.color;
		if ((var8) == (0)){
			var8 = 16711680;
		}
		BlockPos var9 = new BlockPos((int)(var3),(int)(var4),(int)(var5));
		double var10 = (double)(-(var7));
		LOOP_1:
		do{
			if ((var10) < ((double)(var7))){
				double var11 = (double)(-(var7));
				do{
					if ((var11) < ((double)(var7))){
						BlockPos var12 = new BlockPos((int)((var3) + (var10)),(int)(var4),(int)((var5) + (var11)));
						if ((!(var9.closerThan((Vec3i)(var12),(double)(var7)))) || (((var9.hashCode()) % (20)) < (5))){

						}else{
							BlockState var13 = var2.getBlockState(var12.below());
							if (var13.isAir()){

							}else{
								int var14 = 0;
								do{
									if ((var14) < (10)){
										var2.addParticle((ParticleOptions)(new BlockParticleOption(ParticleTypes.BLOCK,var13)),true,((var3) + ((double)(WyHelper.randomWithRange(-3,3)))) + (var10),var4,((var5) + ((double)(WyHelper.randomWithRange(-3,3)))) + (var11),0D,0D,0D);
										var14++;
										continue;
									}else{
										break;
									}
								} while (true);
							}
						}
						var11 = (var11) + (1D);
						continue;
					}else{
						break;
					}
				} while (true);
				var10 = (var10) + (1D);
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
		Vec3 var11_1 = new Vec3(var3,var4,var5);
		double var12_1 = 2D;
		double var13_1 = 0D;
		do{
			if ((var13_1) <= (6.283185307179586D)){
				double var14_1 = ((var12_1) * (Math.cos(var13_1))) + ((WyHelper.randomDouble()) / (5D));
				double var15 = ((var12_1) * (Math.sin(var13_1))) + ((WyHelper.randomDouble()) / (5D));
				Vec3 var16 = var11_1.subtract(var11_1.add(new Vec3(var14_1,var4,var15))).normalize().multiply(3.5D,1D,3.5D);
				Color var17 = new Color(var8);
				SimpleParticleData var18 = new SimpleParticleData(((ParticleType)(ModParticleTypes.CHIYU.get())));
				var18.setLife(15);
				var18.setSize(3F);
				var18.setMotion(-(var16.x),0D,-(var16.z));
				var18.setColor(((float)(var17.getRed())) / (255F),((float)(var17.getGreen())) / (255F),((float)(var17.getBlue())) / (255F),0.4F);
				var2.addParticle((ParticleOptions)(var18),true,(var3) + (var14_1),(var4) + (0.3D),(var5) + (var15),0D,0D,0D);
				SimpleParticleData var19 = new SimpleParticleData(((ParticleType)(ModParticleTypes.CHIYU.get())));
				var19.setLife(15);
				var19.setSize(2F);
				var19.setMotion(-(var16.x),0D,-(var16.z));
				var19.setColor(0F,0F,0F,0.6F);
				var2.addParticle((ParticleOptions)(var19),true,(var3) + (var14_1),(var4) + (0.3D),(var5) + (var15),0D,0D,0D);
				var13_1 = (var13_1) + (0.04908738521234052D);
				continue;
			}else{
				break;
			}
		} while (true);
	}
}