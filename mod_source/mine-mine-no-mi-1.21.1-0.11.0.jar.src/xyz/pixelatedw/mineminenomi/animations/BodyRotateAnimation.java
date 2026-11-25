package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class BodyRotateAnimation extends Animation<LivingEntity, AgeableListModel<LivingEntity>> {
	// Fields
	protected float speed;

	public BodyRotateAnimation(AnimationId<? extends BodyRotateAnimation> var1, float var2) {
		super((AnimationId)(var1));
		this.speed = 40F;
		this.speed = var2;
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
	}
	public void setup(LivingEntity var1, AgeableListModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		float var12 = (((float)(this.getTime())) * (this.speed)) + (var6);
		var3.mulPose(Axis.YP.rotationDegrees(var12));
	}
}