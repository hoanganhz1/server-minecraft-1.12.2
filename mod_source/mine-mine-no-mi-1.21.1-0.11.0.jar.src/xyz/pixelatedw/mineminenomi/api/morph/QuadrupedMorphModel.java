package xyz.pixelatedw.mineminenomi.api.morph;

import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public abstract class QuadrupedMorphModel<T extends LivingEntity> extends QuadrupedModel<T> implements IMorphModel, ArmedModel, HeadedModel {
	// Fields
	public float swimAmount;

	public QuadrupedMorphModel(ModelPart var1) {
		super(var1,false,0F,0F,0F,0F,0);
	}
	public void prepareMobModel(T var1, float var2, float var3, float var4) {
		this.swimAmount = var1.getSwimAmount(var4);
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
				if (((double)(this.head.xRot)) > (0.6D)){
					this.head.xRot = 0.6F;
				}else{
					if (((double)(this.head.xRot)) < (-0.6D)){
						this.head.xRot = -0.6F;
					}else{
						return;
					}
				}
			}
		}
	}
	public void setupAttackAnimation(T var1, float var2) {
		if ((this.attackTime) > (0F)){
			ModelPart var3 = this.head;
			var3.yRot = (var3.yRot) + (this.body.yRot);
			float var4 = (1F) - (this.attackTime);
			var4 = (var4) * (var4);
			var4 = (var4) * (var4);
			var4 = (1F) - (var4);
			float var5 = Mth.sin((var4) * (3.1415927F));
			float var6 = ((Mth.sin((this.attackTime) * (3.1415927F))) * (-((this.head.xRot) - (0.1F)))) * (0.15F);
			this.getHead().xRot = (float)(((double)(this.head.xRot)) + ((((double)(var5)) * (0.8D)) + ((double)(var6))));
			ModelPart var7 = this.getHead();
			var7.zRot = (var7.zRot) - ((Mth.sin((this.attackTime) * (3.1415927F))) * (-0.8F));
		}
	}
	public float rotlerpRad(float var1, float var2, float var3) {
		float var4 = ((var3) - (var2)) % (6.2831855F);
		if ((var4) < (-3.1415927F)){
			var4 = (var4) + (6.2831855F);
		}
		if ((var4) >= (3.1415927F)){
			var4 = (var4) - (6.2831855F);
		}
		return (var2) + ((var1) * (var4));
	}
}