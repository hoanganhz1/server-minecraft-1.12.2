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

public class FluffyCapeModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","fluffy_cape"),"main");
	private final ModelPart cape;
	private final ModelPart capeback;
	private final ModelPart rightSleeve;
	private final ModelPart leftSleeve;
	private final ModelPart capeback3;
	private final ModelPart capeback2;
	private final ModelPart rightSholderPad;
	private final ModelPart leftSholderPad;
	private final ModelPart neck;

	public FluffyCapeModel(ModelPart var1) {
		super(var1);
		this.cape = var1.getChild("cape");
		this.capeback = this.cape.getChild("capeback");
		this.rightSleeve = this.capeback.getChild("rightSleeve");
		this.leftSleeve = this.capeback.getChild("leftSleeve");
		this.capeback3 = this.capeback.getChild("capeback3");
		this.capeback2 = this.capeback.getChild("capeback2");
		this.rightSholderPad = this.capeback.getChild("rightSholderPad");
		this.leftSholderPad = this.capeback.getChild("leftSholderPad");
		this.neck = this.capeback.getChild("neck");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("cape",CubeListBuilder.create(),PartPose.offset(0F,-1F,0F)).addOrReplaceChild("capeback",CubeListBuilder.create().texOffs(0,25).addBox(-10F,-3.25F,-0.25F,21F,22F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.5F,3.25F,1.25F,0.0436F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("rightSleeve",CubeListBuilder.create().texOffs(0,50).addBox(-6.1F,0F,-2.75F,4F,16F,4F,new CubeDeformation(0F)),PartPose.offset(-4.9F,-3F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("leftSleeve",CubeListBuilder.create().texOffs(0,50).addBox(0.9F,0F,-2.75F,4F,16F,4F,new CubeDeformation(0F)),PartPose.offset(7.1F,-3F,0F));
		PartDefinition var4 = var1.addOrReplaceChild("capeback3",CubeListBuilder.create(),PartPose.offsetAndRotation(0.5F,12.1287F,3.3518F,-0.0436F,0F,0F)).addOrReplaceChild("capeback2_r1",CubeListBuilder.create().texOffs(45,47).addBox(-10.5F,-2.25F,1.75F,21F,12F,3F,new CubeDeformation(-0.02F)),PartPose.offsetAndRotation(0F,-3.1287F,-3.8518F,0.1745F,0F,0F));
		PartDefinition var5 = var1.addOrReplaceChild("capeback2",CubeListBuilder.create(),PartPose.offset(0F,0F,0F)).addOrReplaceChild("capeback2_r2",CubeListBuilder.create().texOffs(0,0).addBox(-10.5F,-11.25F,1.75F,21F,21F,4F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(0.5F,8F,1F,0.1745F,0F,0F));
		PartDefinition var6 = var1.addOrReplaceChild("rightSholderPad",CubeListBuilder.create().texOffs(22,0).addBox(-2F,-2.6F,-1.55F,6F,3F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(6.9F,-2.75F,-2.25F,-0.192F,0F,0F));
		PartDefinition var7 = var1.addOrReplaceChild("leftSholderPad",CubeListBuilder.create().texOffs(22,8).addBox(-16.8F,-1.6F,-1.3F,6F,3F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(6.9F,-3.75F,-2.25F,-0.192F,0F,0F));
		PartDefinition var8 = var1.addOrReplaceChild("neck",CubeListBuilder.create().texOffs(22,8).addBox(-16.8F,-1.6F,3.7F,10F,3F,3F,new CubeDeformation(-0.01F)),PartPose.offsetAndRotation(11.9F,-3.75F,-2.25F,-0.192F,0F,0F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		double var7 = var1.distanceToSqr(var1.xo,var1.yo,var1.zo);
		if (((var7) > (0D)) && ((var7) <= (0.02D))){
			var7 = (var7) + (0.02D);
		}
		double var8 = Mth.clamp(((var7) * (1000D)) - (1D),0D,70D);
		int var9 = ((var7) > (0.02D)) ? (1) : (0);
		if ((var9) != (0)){
			var8 = (var8) + ((double)((Mth.sin((float)(Mth.lerp(var8,(double)(var1.walkDistO),(double)(var1.walkDist))))) * (6F)));
		}
		this.capeback.xRot = (float)(Math.toRadians(var8));
		this.rightSleeve.xRot = (float)(Math.toRadians((var8) - ((double)(((var9) == (0)) ? (0) : (50)))));
		this.leftSleeve.xRot = (float)(Math.toRadians((var8) - ((double)(((var9) == (0)) ? (0) : (50)))));
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.cape.copyFrom(this.body);
		this.cape.render(var1,var2,var3,var4,var5);
	}
}