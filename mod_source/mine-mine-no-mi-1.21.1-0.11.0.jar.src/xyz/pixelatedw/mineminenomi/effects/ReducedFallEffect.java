package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class ReducedFallEffect extends BaseEffect {
	public ReducedFallEffect() {
		super(MobEffectCategory.BENEFICIAL,0);
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return true;
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		Vec3 var3 = var1.position();
		boolean var4 = var1.level().clip(new ClipContext(var3,var3.add(0D,-3D,0D),ClipContext.Block.COLLIDER,ClipContext.Fluid.ANY,(Entity)(var1))).getType().equals(HitResult.Type.BLOCK);
		if (((var1.getDeltaMovement().y) < (-1e-05D)) && (!(var4))){
			AbilityHelper.setDeltaMovement((Entity)(var1),var1.getDeltaMovement().multiply(1D,0.1D,1D));
		}
		return true;
	}
	public boolean shouldRenderParticles(MobEffectInstance var1) {
		return false;
	}
	public boolean shouldRenderHUD(MobEffectInstance var1) {
		return false;
	}
	public boolean isRemoveable() {
		return false;
	}
}