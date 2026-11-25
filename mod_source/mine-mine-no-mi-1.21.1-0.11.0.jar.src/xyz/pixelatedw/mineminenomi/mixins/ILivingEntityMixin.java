package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={LivingEntity.class})
public interface ILivingEntityMixin {
	@Invoker(value="onEffectRemoved")
	public abstract void invokeOnEffectRemoved(MobEffectInstance var0);
	@Invoker(value="getJumpPower")
	public abstract float invokeGetJumpPower();
	@Accessor(value="jumping")
	public abstract boolean isJumping();
}