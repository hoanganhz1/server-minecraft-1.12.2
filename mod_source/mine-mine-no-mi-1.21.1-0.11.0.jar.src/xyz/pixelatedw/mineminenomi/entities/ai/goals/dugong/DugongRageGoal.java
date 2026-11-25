package xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;

public class DugongRageGoal extends TickedGoal<AbstractDugongEntity> {
	// Fields
	private static final AttributeModifier RAGE_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","dugong_rage.attack"),10D,AttributeModifier.Operation.ADD_VALUE);
	private Interval canUseInterval;

	public DugongRageGoal(AbstractDugongEntity var1) {
		super(var1);
		this.canUseInterval = new Interval(10);
	}
	public boolean canUse() {
		if (!(this.canUseInterval.canTick())){
			return false;
		}else{
			if ((((AbstractDugongEntity)(this.entity)).getRageTarget()) == (null)){
				return false;
			}else{
				if (((AbstractDugongEntity)(this.entity)).isOwnedBy(((AbstractDugongEntity)(this.entity)).getRageTarget())){
					((AbstractDugongEntity)(this.entity)).setEnraged((LivingEntity)(null));
					return false;
				}else{
					return true;
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if (((((AbstractDugongEntity)(this.entity)).getRageTarget()) != (null)) && (((AbstractDugongEntity)(this.entity)).getRageTarget().isAlive())){
			return true;
		}else{
			return false;
		}
	}
	public void start() {
		super.start();
		int var1 = 0;
		do{
			if ((var1) < (5)){
				double var2 = (((AbstractDugongEntity)(this.entity)).getRandom().nextGaussian()) * (0.2D);
				double var3 = (((AbstractDugongEntity)(this.entity)).getRandom().nextGaussian()) * (0.2D);
				double var4 = (((AbstractDugongEntity)(this.entity)).getRandom().nextGaussian()) * (0.2D);
				((ServerLevel)(((AbstractDugongEntity)(this.entity)).getCommandSenderWorld())).sendParticles((ParticleOptions)(ParticleTypes.ANGRY_VILLAGER),(((AbstractDugongEntity)(this.entity)).getX()) + (var2),((((AbstractDugongEntity)(this.entity)).getY()) + (1D)) + (var3),(((AbstractDugongEntity)(this.entity)).getZ()) + (var4),1,0D,0D,0D,0D);
				var1++;
				continue;
			}else{
				break;
			}
		} while (true);
		((AbstractDugongEntity)(this.entity)).setTarget(((AbstractDugongEntity)(this.entity)).getRageTarget());
		((AbstractDugongEntity)(this.entity)).setResting(false);
		((AbstractDugongEntity)(this.entity)).stopTraining();
		AttributeInstance var2_1 = ((AbstractDugongEntity)(this.entity)).getAttribute(Attributes.ATTACK_DAMAGE);
		if ((var2_1) != (null)){
			var2_1.removeModifier(RAGE_MODIFIER);
			var2_1.addTransientModifier(RAGE_MODIFIER);
		}
	}
	public void stop() {
		super.stop();
		((AbstractDugongEntity)(this.entity)).setEnraged((LivingEntity)(null));
		((AbstractDugongEntity)(this.entity)).setTarget((LivingEntity)(null));
		AttributeInstance var1 = ((AbstractDugongEntity)(this.entity)).getAttribute(Attributes.ATTACK_DAMAGE);
		if ((var1) != (null)){
			var1.removeModifier(RAGE_MODIFIER);
		}
	}
}