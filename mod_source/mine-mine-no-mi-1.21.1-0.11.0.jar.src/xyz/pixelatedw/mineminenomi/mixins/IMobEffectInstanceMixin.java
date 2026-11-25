package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={MobEffectInstance.class})
public interface IMobEffectInstanceMixin {
	@Accessor(value="duration")
	public abstract void setDuration(int var0);
	@Accessor(value="amplifier")
	public abstract void setAmplifier(int var0);
}