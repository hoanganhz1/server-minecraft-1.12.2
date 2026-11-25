package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.data.entity.CarryData;
import xyz.pixelatedw.mineminenomi.handlers.ability.MorphsHandler;

@Mixin(value={LivingEntityRenderer.class})
public class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
	// Fields
	@Shadow() protected M model;
	private Optional<Float> prevYaw;

	public LivingEntityRendererMixin() {
		this.prevYaw = (Optional) (((java.util.Optional)(Optional.empty())));
	}
	@Inject(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/model/EntityModel;setupAnim(Lnet/minecraft/world/entity/Entity;FFFFF)V", shift=At.Shift.AFTER)}, locals=LocalCapture.CAPTURE_FAILHARD)
	public void mineminenomi$postSetupAnim(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6, CallbackInfo var7, boolean var8, float var9, float var10, float var11, float var12, float var13, float var14, float var15) {
		LivingEntityRenderer var16 = ((LivingEntityRenderer)(this));
		MorphsHandler.postRenderHook(var1,this.model,var16,var4,var13,var11,var3);
		Animation.animationPostAngles(var1,this.model,var4,var5,var6,var3,var15,var14,var13,var11,var12);
	}
	@Inject(method={"render"}, at={@At(value="TAIL")})
	public void mineminenomi$render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6, CallbackInfo var7) {
		if (var1 instanceof Player){
			LivingEntity var8 = ((LivingEntity)(CarryData.get(var1).map((Function)(((Function<CarryData, LivingEntity>)(CarryData::getLeashHolder)))).orElse(null)));
			if ((var8) != (null)){
				this.renderLeash(var1,var3,var4,var5,var8);
			}else{
				return;
			}
		}
	}
	private <E extends Entity> void renderLeash(T var1, float var2, PoseStack var3, MultiBufferSource var4, E var5) {
		var3.pushPose();
		Vec3 var6 = var5.getRopeHoldPosition(var2);
		double var7 = ((double)((Mth.lerp(var2,var1.yBodyRot,var1.yBodyRotO)) * (0.017453292F))) + (1.5707963267948966D);
		Vec3 var8 = Vec3.ZERO.add(0D,((double)(var1.getBbHeight())) / (1.65D),0D);
		double var9 = ((Math.cos(var7)) * (var8.z)) + ((Math.sin(var7)) * (var8.x));
		double var10 = ((Math.sin(var7)) * (var8.z)) - ((Math.cos(var7)) * (var8.x));
		double var11 = (Mth.lerp((double)(var2),var1.xo,var1.getX())) + (var9);
		double var12 = (Mth.lerp((double)(var2),var1.yo,var1.getY())) + (var8.y);
		double var13 = (Mth.lerp((double)(var2),var1.zo,var1.getZ())) + (var10);
		var3.translate(var9,var8.y,var10);
		float var14 = (float)((var6.x) - (var11));
		float var15 = (float)((var6.y) - (var12));
		float var16 = (float)((var6.z) - (var13));
		VertexConsumer var18 = var4.getBuffer(RenderType.leash());
		Matrix4f var19 = var3.last().pose();
		float var20 = (float)(((Mth.fastInvSqrt((double)(((var14) * (var14)) + ((var16) * (var16))))) * (0.02500000037252903D)) / (2D));
		float var21 = (var16) * (var20);
		float var22 = (var14) * (var20);
		BlockPos var23 = BlockPos.containing((Position)(var1.getEyePosition(var2)));
		BlockPos var24 = BlockPos.containing((Position)(var5.getEyePosition(var2)));
		int var25 = (var1.isOnFire()) ? (15) : (var1.level().getBrightness(LightLayer.BLOCK,var23));
		int var26 = (var5.isOnFire()) ? (15) : (var5.level().getBrightness(LightLayer.BLOCK,var24));
		int var27 = var1.level().getBrightness(LightLayer.SKY,var23);
		int var28 = var1.level().getBrightness(LightLayer.SKY,var24);
		int var29 = 0;
		do{
			if ((var29) <= (24)){
				addVertexPair(var18,var19,var14,var15,var16,var25,var26,var27,var28,0.025F,0.025F,var21,var22,var29,false);
				var29++;
				continue;
			}else{
				break;
			}
		} while (true);
		var29 = 24;
		do{
			if ((var29) >= (0)){
				addVertexPair(var18,var19,var14,var15,var16,var25,var26,var27,var28,0.025F,0F,var21,var22,var29,true);
				var29--;
				continue;
			}else{
				break;
			}
		} while (true);
		var3.popPose();
	}
	private static void addVertexPair(VertexConsumer var0, Matrix4f var1, float var2, float var3, float var4, int var5, int var6, int var7, int var8, float var9, float var10, float var11, float var12, int var13, boolean var14) {
		float var15 = ((float)(var13)) / (24F);
		int var16 = (int)(Mth.lerp(var15,(float)(var5),(float)(var6)));
		int var17 = LightTexture.pack(var16,(int)(Mth.lerp(var15,(float)(var7),(float)(var8))));
		float var18 = (((var13) % (2)) == ((var14) ? (1) : (0))) ? (0.7F) : (1F);
		float var19 = (0.5F) * (var18);
		float var20 = (0.4F) * (var18);
		float var21 = (0.3F) * (var18);
		float var22 = (var2) * (var15);
		float var23 = ((var3) > (0F)) ? (((var3) * (var15)) * (var15)) : ((var3) - (((var3) * ((1F) - (var15))) * ((1F) - (var15))));
		float var24 = (var4) * (var15);
		var0.addVertex(var1,(var22) - (var11),(var23) + (var10),(var24) + (var12)).setColor(var19,var20,var21,1F).setLight(var17);
		var0.addVertex(var1,(var22) + (var11),((var23) + (var9)) - (var10),(var24) - (var12)).setColor(var19,var20,var21,1F).setLight(var17);
	}
	@Inject(method={"setupRotations"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$applyRotations(T var1, PoseStack var2, float var3, float var4, float var5, float var6, CallbackInfo var7) {
		LivingEntityRenderer var8 = ((LivingEntityRenderer)(this));
		MorphsHandler.preRenderHook(var1,this.model,var8,var2,(float)(var1.tickCount),var4,var5);
		if (var1.getActiveEffects().stream().anyMatch((Predicate)(((Predicate<MobEffectInstance>)((l0) -> {
			Holder lv1_1 = l0.getEffect();
			int lv1_2;
			if (lv1_1 instanceof IBindBodyEffect){
				IBindBodyEffect lv1_3 = ((IBindBodyEffect)(lv1_1));
				if (lv1_3.isBlockingRotation()){
					lv1_2 = 1;
				}else{
					lv1_2 = 0;
				}
			}else{
				lv1_2 = 0;
			}
			return (((lv1_2) & 1) != 0);
		}))))){
			if (!(this.prevYaw.isPresent())){
				this.prevYaw = (Optional) (Optional.of(Float.valueOf(var4)));
			}
			if ((var1.getPose()) != (Pose.SLEEPING)){
				var2.mulPose(Axis.YP.rotationDegrees((180F) - (((Float)(this.prevYaw.get())).floatValue())));
			}
			var7.cancel();
		}else{
			if (this.prevYaw.isPresent()){
				this.prevYaw = (Optional) (((java.util.Optional)(Optional.empty())));
			}else{
				return;
			}
		}
	}
}