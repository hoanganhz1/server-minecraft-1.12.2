package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

public class YomiModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","yomi"),"main");

	public YomiModel(ModelPart var1) {
		super(var1);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		super.setupAnim(var1,var2,var3,var4,var5,var6);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		var1.translate(0D,0D,0D);
		ModelPart var8 = this.getArm(var6);
		if ((var8) != (null)){
			var8.render(var1,var2,var3,var4,var5);
			return true;
		}else{
			return false;
		}
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		super.translateToHand(var1,var2);
		var2.translate(((var1) == (HumanoidArm.RIGHT)) ? (0.06D) : (-0.06D),0D,0D);
	}
}