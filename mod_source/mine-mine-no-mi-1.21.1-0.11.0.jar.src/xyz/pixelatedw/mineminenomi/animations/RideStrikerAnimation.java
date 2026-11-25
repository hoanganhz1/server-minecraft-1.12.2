package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles;
import xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class RideStrikerAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
	public RideStrikerAnimation(AnimationId<RideStrikerAnimation> var1) {
		super(var1);
		this.setAnimationPostAngles((Animation$IAnimationPostAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationPostAngles)(this::setup))));
		this.setAnimationAngles((Animation$IAnimationAngles)(((xyz.pixelatedw.mineminenomi.api.animations.Animation$IAnimationAngles)(this::angles))));
	}
	public void angles(LivingEntity var1, HumanoidModel<LivingEntity> var2, float var3, float var4, float var5, float var6, float var7) {
		Function<DevilFruitData, Boolean> var8 = (l0) -> {
			return Boolean.valueOf(l0.hasDevilFruit(ModFruits.MERA_MERA_NO_MI));
		};
		if (!(((Boolean)(((java.util.Optional)(DevilFruitData.get(var1))).map((Function)(var8)).orElse(Boolean.valueOf(false)))).booleanValue())){
			return;
		}else{
			ModelPart var9 = var2.head;
			var9.yRot = (float)(((double)(var9.yRot)) + (Math.toRadians(45D)));
			var2.leftLeg.xRot = 0F;
			var2.rightLeg.xRot = 0F;
			ModelPart var10 = var2.rightLeg;
			var10.yRot = (float)(((double)(var10.yRot)) + (Math.toRadians(40D)));
			return;
		}
	}
	public void setup(LivingEntity var1, EntityModel<LivingEntity> var2, PoseStack var3, MultiBufferSource var4, int var5, float var6, float var7, float var8, float var9, float var10, float var11) {
		Function<DevilFruitData, Boolean> var12 = (l0) -> {
			return Boolean.valueOf(l0.hasDevilFruit(ModFruits.MERA_MERA_NO_MI));
		};
		if (!(((Boolean)(((java.util.Optional)(DevilFruitData.get(var1))).map((Function)(var12)).orElse(Boolean.valueOf(false)))).booleanValue())){
			return;
		}else{
			var3.mulPose(Axis.YP.rotationDegrees(-50F));
			return;
		}
	}
}