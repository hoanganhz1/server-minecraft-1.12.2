package xyz.pixelatedw.mineminenomi.animations.ito;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ShufukuSagyoAnimation extends Animation<LivingEntity, EntityModel<LivingEntity>> {
	public ShufukuSagyoAnimation(AnimationId<ShufukuSagyoAnimation> var1) {
		super(var1);
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, EntityModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		if (var2 instanceof HumanoidModel){
			HumanoidModel var8 = ((HumanoidModel)(var2));
			int var9 = (((double)(var1.getHealth())) > (((double)(var1.getMaxHealth())) * (0.7D))) ? (1) : (0);
			if ((var9) == (0)){
				var8.rightArm.xRot = (((var8.rightArm.xRot) * (0.5F)) - (3.1415927F)) + (1.8F);
				var8.rightArm.yRot = -1.1F;
				var8.rightArm.zRot = -0.8F;
			}
			var8.leftArm.xRot = (((var8.leftArm.xRot) * (0.5F)) - (3.1415927F)) + (1.8F);
			var8.leftArm.yRot = 1.1F;
			var8.leftArm.zRot = 0.8F;
			if (var8 instanceof PlayerModel){
				PlayerModel var10 = ((PlayerModel)(var8));
				if ((var9) == (0)){
					var10.rightSleeve.copyFrom(var8.rightArm);
				}
				var10.leftSleeve.copyFrom(var8.leftArm);
			}else{
				return;
			}
		}
	}
	public void setup(LivingEntity var1, PoseStack var2, MultiBufferSource var3, int var4, float var5, float var6) {
	}
}