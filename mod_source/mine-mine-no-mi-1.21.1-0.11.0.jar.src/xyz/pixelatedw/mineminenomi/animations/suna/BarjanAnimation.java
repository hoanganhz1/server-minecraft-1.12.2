package xyz.pixelatedw.mineminenomi.animations.suna;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class BarjanAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public BarjanAnimation(AnimationId<BarjanAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		double var8 = 90D;
		if ((this.getTime()) > (20L)){
			var8 = (var8) - ((double)(((this.getTime()) - (20L)) * (20L)));
		}
		double var9 = (Mth.clamp(var8,0D,180D)) - (130D);
		double var10 = (-(Mth.clamp(var8,0D,180D))) + (20D);
		if ((var8) <= (0D)){
			return;
		}else{
			var2.rightArm.yRot = (float)(Math.toRadians(var9));
			var2.rightArm.xRot = (float)(Math.toRadians(var10));
			return;
		}
	}
	public void setup(LivingEntity var1, EntityModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
	}
}