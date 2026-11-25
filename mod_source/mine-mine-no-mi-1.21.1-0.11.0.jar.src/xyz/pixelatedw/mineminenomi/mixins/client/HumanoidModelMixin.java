package xyz.pixelatedw.mineminenomi.mixins.client;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

@Mixin(value={HumanoidModel.class})
public abstract class HumanoidModelMixin<T extends LivingEntity> extends AgeableListModel<T> {
	// Fields
	@Shadow() public ModelPart head;
	@Shadow() public ModelPart body;
	@Shadow() public ModelPart rightArm;
	@Shadow() public ModelPart leftArm;
	@Shadow() public ModelPart rightLeg;
	@Shadow() public ModelPart leftLeg;
	@Shadow() public ModelPart hat;

	@Inject(method={"setupAnim"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$preSetupAnimation(T var1, float var2, float var3, float var4, float var5, float var6, CallbackInfo var7) {
		boolean var8 = ((Boolean)(EntityStatsData.get(var1).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isBlackLeg)))).orElse(Boolean.valueOf(false)))).booleanValue();
		RendererHelper.resetModelToDefaultPivots(((HumanoidModel)(this)));
		if ((var8) && (var1.getMainHandItem().isEmpty())){
			this.attackTime = 0F;
		}
	}
	@Inject(method={"setupAnim"}, at={@At(value="TAIL")})
	public void mineminenomi$setupAnimation(T var1, float var2, float var3, float var4, float var5, float var6, CallbackInfo var7) {
		Optional var8 = EntityStatsData.get(var1);
		boolean var9 = ((Boolean)(var8.map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isBlackLeg)))).orElse(Boolean.valueOf(false)))).booleanValue();
		boolean var10 = ((Boolean)(var8.map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isInCombatMode)))).orElse(Boolean.valueOf(false)))).booleanValue();
		if ((var9) && (!((!(var10)) && (!(ClientConfig.isBlackLegAlwaysUp()))))){
			if ((((var1.xo) - (var1.getX())) == (0D)) && ((((var1.zo) - (var1.getZ())) == (0D)) && (!(var1.hasEffect((Holder)(ModEffects.MOVEMENT_BLOCKED)))))){
				ModelPart var11 = this.rightLeg;
				var11.y = (var11.y) - (4F);
				ModelPart var12 = this.rightLeg;
				var12.z = (var12.z) - (3F);
			}
			if (((var1.attackAnim) > (0F)) && (var1.getMainHandItem().isEmpty())){
				float var11_1 = var1.attackAnim;
				ModelPart var12_1 = this.rightLeg;
				var12_1.xRot = (var12_1.xRot) - ((var1.attackAnim) * (2F));
				ModelPart var13 = this.rightLeg;
				var13.zRot = (var13.zRot) + ((var1.attackAnim) * (2F));
				this.body.yRot = (Mth.sin((Mth.sqrt(var11_1)) * (6.2831855F))) * (0.2F);
				this.rightArm.z = (Mth.sin(this.body.yRot)) * (5F);
				this.rightArm.x = (-(Mth.cos(this.body.yRot))) * (5F);
				this.leftArm.z = (-(Mth.sin(this.body.yRot))) * (5F);
				this.leftArm.x = (Mth.cos(this.body.yRot)) * (5F);
				ModelPart var14 = this.rightArm;
				var14.yRot = (var14.yRot) + (this.body.yRot);
				ModelPart var15 = this.leftArm;
				var15.yRot = (var15.yRot) + (this.body.yRot);
				ModelPart var16 = this.leftArm;
				var16.xRot = (var16.xRot) + (this.body.yRot);
				this.head.copyFrom(this.hat);
			}
		}
		boolean var11_2 = var1.getActiveEffects().stream().anyMatch((Predicate)(((Predicate<MobEffectInstance>)((l0) -> {
			Holder lv1_1 = l0.getEffect();
			int lv1_2 = 0;
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
		}))));
		Animation.animationAngles(var1,((HumanoidModel)(this)),var2,var3,var4,var5,var6);
	}
}