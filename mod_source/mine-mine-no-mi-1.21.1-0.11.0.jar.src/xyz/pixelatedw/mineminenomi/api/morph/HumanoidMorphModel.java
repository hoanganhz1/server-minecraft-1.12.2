package xyz.pixelatedw.mineminenomi.api.morph;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public abstract class HumanoidMorphModel<T extends LivingEntity> extends HumanoidModel<T> implements IMorphModel, IFirstPersonHandReplacer {
	public HumanoidMorphModel(ModelPart var1) {
		super(var1);
		this.hat.skipDraw = true;
	}
	@Nullable()
	public ModelPart getArm(HumanoidArm var1) {
		return ((var1) == (HumanoidArm.RIGHT)) ? (this.rightArm) : (this.leftArm);
	}
	@Nullable()
	public ModelPart getLeg(HumanoidArm var1) {
		return ((var1) == (HumanoidArm.RIGHT)) ? (this.rightLeg) : (this.leftLeg);
	}
	public HumanoidArm getAttackSide(T var1) {
		HumanoidArm var2 = var1.getMainArm();
		return ((var1.swingingArm) == (InteractionHand.MAIN_HAND)) ? (var2) : (var2.getOpposite());
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		ModelPart var8 = (var7) ? (this.getLeg(var6)) : ((ModelPart)(this.getArm(var6)));
		if ((var8) != (null)){
			float var9 = 0F;
			var8.zRot = var9;
			var8.yRot = var9;
			var8.xRot = var9;
			var8.render(var1,var2,var3,var4,var5);
			return true;
		}else{
			return false;
		}
	}
	public boolean renderItemInHand(T var1, HumanoidArm var2, PoseStack var3) {
		this.translateToHand(var2,var3);
		return true;
	}
	public void setupHeadRotation(T var1, float var2, float var3) {
		int var4 = ((var1.getFallFlyingTicks()) > (4)) ? (1) : (0);
		boolean var5 = var1.isVisuallySwimming();
		this.head.yRot = (var3) * (0.017453292F);
		if ((var4) != (0)){
			this.head.xRot = -0.7853982F;
		}else{
			if ((this.swimAmount) > (0F)){
				if (var5){
					this.head.xRot = this.rotlerpRad(this.head.xRot,-0.7853982F,this.swimAmount);
				}else{
					this.head.xRot = this.rotlerpRad(this.head.xRot,(var2) * (0.017453292F),this.swimAmount);
				}
			}else{
				this.head.xRot = (var2) * (0.017453292F);
				if (((double)(this.head.xRot)) > (0.4D)){
					this.head.xRot = 0.4F;
				}else{
					if (((double)(this.head.xRot)) < (-0.4D)){
						this.head.xRot = -0.4F;
					}else{
						return;
					}
				}
			}
		}
	}
	public void setupAttackAnimation(T var1, float var2) {
		this.setupAttackAnimation(var1,var2,0F);
	}
	public void setupAttackAnimation(T var1, float var2, float var3) {
		this.attackTime = var1.attackAnim;
		boolean var4 = ((Boolean)(EntityStatsData.get(var1).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isBlackLeg)))).orElse(Boolean.valueOf(false)))).booleanValue();
		HumanoidArm var5 = this.getAttackSide(var1);
		if (!(var1.getMainHandItem().isEmpty())){
			ModelPart var6 = this.getArm(var1.getMainArm());
			var6.xRot = (var6.xRot) + (-0.15F);
		}
		if ((this.attackTime) > (0F)){
			float var10 = 0.0F;
			float var11 = 0.0F;
			float var9 = 0.0F;
			if (var4){
				ModelPart var6_1 = this.getLeg(var5);
				this.body.yRot = (Mth.sin((Mth.sqrt(this.attackTime)) * (6.2831855F))) * (0.2F);
				ModelPart var7 = this.rightLeg;
				var7.yRot = (var7.yRot) + (this.body.yRot);
				ModelPart var8 = this.leftLeg;
				var8.yRot = (var8.yRot) + (this.body.yRot);
				var9 = (1F) - (this.attackTime);
				var9 = (var9) * (var9);
				var9 = (var9) * (var9);
				var9 = (1F) - (var9);
				var10 = Mth.sin((var9) * (3.1415927F));
				var11 = ((Mth.sin((this.attackTime) * (3.1415927F))) * (-((this.head.xRot) - (0.7F)))) * (0.75F);
				var6_1.xRot = (float)(((double)(this.rightArm.xRot)) - ((((double)(var10)) * (1.5D)) + ((double)(var11))));
				var6_1.yRot = (var6_1.yRot) + ((this.body.yRot) * (2F));
			}else{
				ModelPart var6_2 = this.getArm(var5);
				var9 = this.attackTime;
				this.body.yRot = (Mth.sin((Mth.sqrt(var9)) * (6.2831855F))) * (0.2F);
				if ((var5) == (HumanoidArm.LEFT)){
					ModelPart var8_1 = this.body;
					var8_1.yRot = (var8_1.yRot) * (-1F);
				}
				this.rightArm.z = (Mth.sin(this.body.yRot)) * (5F);
				this.rightArm.x = (-(var3)) - ((Mth.cos(this.body.yRot)) * (5F));
				this.leftArm.z = (-(Mth.sin(this.body.yRot))) * (5F);
				this.leftArm.x = (var3) + ((Mth.cos(this.body.yRot)) * (5F));
				ModelPart var8_2 = this.rightArm;
				var8_2.yRot = (var8_2.yRot) + (this.body.yRot);
				ModelPart var9_1 = this.leftArm;
				var9_1.yRot = (var9_1.yRot) + (this.body.yRot);
				ModelPart var10_1 = this.leftArm;
				var10_1.xRot = (var10_1.xRot) + (this.body.yRot);
				var9 = (1F) - (this.attackTime);
				var9 = (var9) * (var9);
				var9 = (var9) * (var9);
				var9 = (1F) - (var9);
				var10 = Mth.sin((var9) * (3.1415927F));
				var11 = ((Mth.sin((this.attackTime) * (3.1415927F))) * (-((this.head.xRot) - (0.7F)))) * (0.75F);
				var6_2.xRot = (var6_2.xRot) - (((var10) * (1.2F)) + (var11));
				var6_2.yRot = (var6_2.yRot) + ((this.body.yRot) * (2F));
				var6_2.zRot = (var6_2.zRot) + ((Mth.sin((this.attackTime) * (3.1415927F))) * (-0.4F));
			}
		}
	}
}