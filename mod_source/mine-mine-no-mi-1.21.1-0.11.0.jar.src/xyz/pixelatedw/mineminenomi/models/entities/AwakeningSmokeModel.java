package xyz.pixelatedw.mineminenomi.models.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class AwakeningSmokeModel extends EntityModel<LivingEntity> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","awakening_smoke"),"main");
	private final ModelPart smoke;
	private final ModelPart rightSmoke;
	private final ModelPart rightSmoke2;
	private final ModelPart rightSmoke3;
	private final ModelPart leftSmoke;
	private final ModelPart leftSmoke2;
	private final ModelPart leftSmoke3;
	private final ModelPart backSmoke;
	private final ModelPart backSmoke3;
	private final ModelPart backSmoke2;
	private final ModelPart backSmoke5;
	private final ModelPart backSmoke4;

	public AwakeningSmokeModel(ModelPart var1) {
		this.smoke = var1.getChild("smoke");
		this.rightSmoke = this.smoke.getChild("rightSmoke");
		this.rightSmoke2 = this.rightSmoke.getChild("rightSmoke2");
		this.rightSmoke3 = this.rightSmoke2.getChild("rightSmoke3");
		this.leftSmoke = this.smoke.getChild("leftSmoke");
		this.leftSmoke2 = this.leftSmoke.getChild("leftSmoke2");
		this.leftSmoke3 = this.leftSmoke2.getChild("leftSmoke3");
		this.backSmoke = this.smoke.getChild("backSmoke");
		this.backSmoke3 = this.backSmoke.getChild("backSmoke3");
		this.backSmoke2 = this.backSmoke.getChild("backSmoke2");
		this.backSmoke5 = this.backSmoke.getChild("backSmoke5");
		this.backSmoke4 = this.backSmoke.getChild("backSmoke4");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("smoke",CubeListBuilder.create(),PartPose.offset(0F,0F,0.5F));
		var1.addOrReplaceChild("rightSmoke",CubeListBuilder.create().texOffs(0,15).addBox(-2F,-1.3844F,0F,4F,4F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-6F,-1.1156F,-2.112F,-0.5672F,0F,0F)).addOrReplaceChild("rightSmoke2",CubeListBuilder.create().texOffs(0,9).addBox(-2F,-2.5F,0F,4F,5F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,4.8844F,0.888F,0.3491F,0F,0F)).addOrReplaceChild("rightSmoke3",CubeListBuilder.create().texOffs(0,0).addBox(-2F,-2.6188F,-0.0759F,4F,8F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,5F,0.75F,0.2618F,0F,0F));
		var1.addOrReplaceChild("leftSmoke",CubeListBuilder.create().texOffs(0,15).mirror().addBox(-2F,-1.3844F,0F,4F,4F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(6F,-1.1156F,-2.112F,-0.5672F,0F,0F)).addOrReplaceChild("leftSmoke2",CubeListBuilder.create().texOffs(0,9).mirror().addBox(-2F,-2.5F,0F,4F,5F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,4.8844F,0.888F,0.3491F,0F,0F)).addOrReplaceChild("leftSmoke3",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-2F,-2.6188F,-0.0759F,4F,8F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,5F,0.75F,0.2618F,0F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("backSmoke",CubeListBuilder.create().texOffs(0,20).addBox(-1.5F,-2.435F,0.1314F,4F,5F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-6.5F,-3.065F,1.0686F,-1.309F,0F,0F));
		var2.addOrReplaceChild("backSmoke3",CubeListBuilder.create().texOffs(0,26).addBox(-2.5F,-3.5F,-0.25F,16F,5F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.9475F,-6.5053F,-1.1009F,0.5236F,0F,0F));
		var2.addOrReplaceChild("backSmoke2",CubeListBuilder.create().texOffs(11,0).addBox(-1.5F,-2.75F,-0.0977F,4F,4F,0F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.0525F,-3.5053F,-0.1009F,0.2618F,0F,0F));
		var2.addOrReplaceChild("backSmoke5",CubeListBuilder.create().texOffs(11,0).mirror().addBox(-1.5F,-2.75F,-0.0977F,4F,4F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(11.9475F,-3.5053F,-0.1009F,0.2618F,0F,0F));
		var2.addOrReplaceChild("backSmoke4",CubeListBuilder.create().texOffs(0,20).mirror().addBox(-1.5F,-2.435F,0.1314F,4F,5F,0F,new CubeDeformation(0F)).mirror(false),PartPose.offset(12F,0F,0F));
		return LayerDefinition.create(var0,32,32);
	}
	public void setupAnim(LivingEntity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.smoke.render(var1,var2,var3,var4,var5);
	}
}