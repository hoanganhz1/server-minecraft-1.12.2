package xyz.pixelatedw.mineminenomi.models.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class CabajiScarfModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","cabaji_scarf"),"main");
	private final ModelPart scarfbase;
	private final ModelPart scarftail;
	private final ModelPart scarftail2;

	public CabajiScarfModel(ModelPart var1) {
		super(var1);
		this.scarfbase = var1.getChild("scarfbase");
		this.scarftail = this.scarfbase.getChild("scarftail");
		this.scarftail2 = this.scarftail.getChild("scarftail2");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("scarfbase",CubeListBuilder.create().texOffs(0,0).addBox(-5F,-3F,-5F,10F,4F,10F,new CubeDeformation(0F)),PartPose.offset(0F,0F,0F)).addOrReplaceChild("scarftail",CubeListBuilder.create().texOffs(14,14).addBox(-3F,0F,0F,6F,11F,1F,new CubeDeformation(0F)),PartPose.offset(1.75F,-0.75F,3.75F)).addOrReplaceChild("scarftail2",CubeListBuilder.create().texOffs(0,14).addBox(-3F,0F,0F,6F,11F,1F,new CubeDeformation(0F)),PartPose.offset(0F,11F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		float var9 = 0.0F;
		double var7 = var1.distanceToSqr(var1.xo,var1.yo,var1.zo);
		if (((var7) > (0D)) && ((var7) <= (0.02D))){
			var7 = (var7) + (0.02D);
		}
		double var8 = Mth.clamp(((var7) * (1000D)) - (1D),0D,70D);
		if ((((var7) > (0.02D)) ? (1) : (0)) != (0)){
			var8 = (var8) + ((double)((Mth.sin((float)(Mth.lerp(var8,(double)(var1.walkDistO),(double)(var1.walkDist))))) * (6F)));
		}
		if ((var1.getXRot()) >= (45F)){
			var9 = (90F) - (var1.getXRot());
			var8 = (var8) + ((double)((var9) - (45F)));
		}else{
			if ((var1.getXRot()) < (-10F)){
				var9 = (90F) - (var1.getXRot());
				var8 = (var8) + ((double)((var9) - (85F)));
			}
		}
		this.scarftail.xRot = (float)(Math.toRadians(var8));
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.scarfbase.copyFrom(this.head);
		this.scarfbase.render(var1,var2,var3,var4,var5);
	}
}