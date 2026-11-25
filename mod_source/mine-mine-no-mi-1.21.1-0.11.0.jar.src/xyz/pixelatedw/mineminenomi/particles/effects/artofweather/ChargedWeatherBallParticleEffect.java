package xyz.pixelatedw.mineminenomi.particles.effects.artofweather;

import java.awt.Color;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class ChargedWeatherBallParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	// Fields
	private float red;
	private float green;
	private float blue;
	private Supplier<ParticleType<SimpleParticleData>> particle;

	public ChargedWeatherBallParticleEffect(Color var1, Supplier<ParticleType<SimpleParticleData>> var2) {
		this.red = ((float)(var1.getRed())) / (255F);
		this.green = ((float)(var1.getGreen())) / (255F);
		this.blue = ((float)(var1.getBlue())) / (255F);
		this.particle = var2;
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		int var7 = 0;
		do{
			if ((var7) < (10)){
				double var8 = (WyHelper.randomDouble()) / (1.5D);
				double var9 = (WyHelper.randomDouble()) / (1.5D);
				double var10 = (WyHelper.randomDouble()) / (1.5D);
				SimpleParticleData var11 = new SimpleParticleData(((ParticleType)(this.particle.get())));
				var11.setLife(4);
				var11.setSize(2F);
				var11.setMotion(0D,0.02D,0D);
				var11.setColor(this.red,this.green,this.blue);
				var2.addParticle((ParticleOptions)(var11),true,(var3) + (var8),((var4) + (1D)) + (var9),(var5) + (var10),0D,0D,0D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}