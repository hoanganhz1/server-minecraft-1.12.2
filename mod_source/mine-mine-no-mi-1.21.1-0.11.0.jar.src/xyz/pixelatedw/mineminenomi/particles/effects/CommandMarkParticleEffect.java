package xyz.pixelatedw.mineminenomi.particles.effects;

import java.awt.Color;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class CommandMarkParticleEffect extends ParticleEffect<CommandMarkParticleEffect$Details> {
	public CommandMarkParticleEffect() {
		super((ParticleEffect$IFactory) ((ParticleEffect$IFactory<CommandMarkParticleEffect$Details>) (CommandMarkParticleEffect$Details::new)));
	}
	public void spawn(Entity var1, Level var2, double var3, double var4, double var5, CommandMarkParticleEffect$Details var6) {
		Color var7 = var6.getColor();
		SimpleParticleData var8 = new SimpleParticleData(((ParticleType)(ModParticleTypes.COMMAND_MARK.get())));
		var8.setLife(5);
		var8.setSize((var6.isMainMark()) ? (5F) : (4F));
		var8.setColor(((float)(var7.getRed())) / (255F),((float)(var7.getGreen())) / (255F),((float)(var7.getBlue())) / (255F),1F);
		var8.setFunction(EasingFunction.BOUNCE_IN);
		var8.setMotion(0D,0.01D,0D);
		var2.addParticle((ParticleOptions)(var8),true,var3,(var4) + (0.75D),var5,0D,0D,0D);
	}
}