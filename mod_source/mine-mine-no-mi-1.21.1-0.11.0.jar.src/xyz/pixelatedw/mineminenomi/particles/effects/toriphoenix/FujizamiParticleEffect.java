package xyz.pixelatedw.mineminenomi.particles.effects.toriphoenix;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$NoDetails;

public class FujizamiParticleEffect extends ParticleEffect<ParticleEffect$NoDetails> {
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, ParticleEffect$NoDetails var6) {
		float var7 = ((var1) == (Minecraft.getInstance().player)) ? (0.15F) : (0.5F);
		int var8 = 0;
		do{
			if ((var8) < (64)){
				double var9 = (WyHelper.randomDouble()) / (1.2D);
				double var10 = WyHelper.randomDouble();
				double var11 = (WyHelper.randomDouble()) / (1.2D);
				SimpleParticleData var12 = new SimpleParticleData(((ParticleType)(ModParticleTypes.BLUE_FLAME.get())));
				var12.setLife(20);
				var12.setColor(1F,1F,1F,var7);
				var12.setSize(2F);
				var2.addParticle((ParticleOptions)(var12),true,(var3) + (var9),((var4) + (0.5D)) + (var10),(var5) + (var11),0D,0D,0D);
				var8++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}