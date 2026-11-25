package xyz.pixelatedw.mineminenomi.particles.effects;

import java.util.function.Supplier;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class LogiaParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	// Fields
	private Supplier<ParticleType<SimpleParticleData>> particle;

	public LogiaParticleEffect(Supplier<ParticleType<SimpleParticleData>> var1) {
		this.particle = var1;
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		Minecraft var7 = Minecraft.getInstance();
		boolean var8 = var7.player.equals(var1);
		int var9 = ((var7.options.getCameraType()) == (CameraType.FIRST_PERSON)) ? (1) : (0);
		if ((var8) && ((var9) != (0))){
			return;
		}else{
			int var10 = 0;
			do{
				if ((var10) < (WyHelper.randomWithRange(7,12))){
					double var11 = (WyHelper.randomDouble()) / (1.7D);
					double var12 = (0.25D) + ((WyHelper.randomDouble()) / (3D));
					double var13 = (WyHelper.randomDouble()) / (1.7D);
					int var14 = (1) + (WyHelper.randomWithRange(0,4));
					double var15 = (WyHelper.randomDouble()) / (50D);
					if ((var15) < (0D)){
						var15 = 0.005D;
					}
					if (((this.particle) == (ModParticleTypes.GORO)) && (MineMineNoMi.hasBlueGoroPackSelected())){
						this.particle = (Supplier) (ModParticleTypes.GORO_YELLOW);
					}
					SimpleParticleData var16 = new SimpleParticleData(((ParticleType)(this.particle.get())));
					var16.setLife(var14);
					if ((this.particle) == (ModParticleTypes.HIE)){
						var16.setLife(7);
						var16.setAnimationSpeed(1);
					}
					var16.setSize(1.5F);
					var16.setMotion(0D,var15,0D);
					var2.addParticle((ParticleOptions)(var16),true,(var3) + (var11),((var4) + (1D)) + (var12),(var5) + (var13),0D,0D,0D);
					var10++;
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
}