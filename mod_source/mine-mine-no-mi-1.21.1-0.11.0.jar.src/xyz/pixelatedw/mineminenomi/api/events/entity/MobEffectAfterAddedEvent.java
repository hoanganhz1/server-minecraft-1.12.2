package xyz.pixelatedw.mineminenomi.api.events.entity;

import javax.annotation.Nonnull;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class MobEffectAfterAddedEvent extends MobEffectEvent {
	public MobEffectAfterAddedEvent(LivingEntity var1, MobEffectInstance var2) {
		super(var1,var2);
	}
	@Nonnull()
	public MobEffectInstance getEffectInstance() {
		return super.getEffectInstance();
	}
}