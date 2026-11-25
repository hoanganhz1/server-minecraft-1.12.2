package xyz.pixelatedw.mineminenomi.animations.pacifista;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class HeadLaserChargeAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public HeadLaserChargeAnimation(AnimationId<HeadLaserChargeAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::postAngles))));
	}
	public void postAngles(LivingEntity var1, HumanoidModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		var2.head.y = 2F;
		var2.hat.y = 2F;
		var2.head.z = ((float)((WyHelper.randomDouble(var1.getRandom())) / (4D))) - (0.75F);
		var2.head.x = (float)((WyHelper.randomDouble(var1.getRandom())) / (4D));
	}
}