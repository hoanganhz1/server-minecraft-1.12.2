package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.DenDenMushiEntity;

public class DenDenMushiModel<T extends DenDenMushiEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","den_den_mushi"),"main");
	private final ModelPart leftEyeStem;
	private final ModelPart leftEye;
	private final ModelPart rightEyeStem;
	private final ModelPart rightEye;
	private final ModelPart body;
	private final ModelPart lowerBody;
	private final ModelPart shell;

	public DenDenMushiModel(ModelPart var1) {
		this.leftEyeStem = var1.getChild("leftEyeStem");
		this.leftEye = this.leftEyeStem.getChild("leftEye");
		this.rightEyeStem = var1.getChild("rightEyeStem");
		this.rightEye = this.rightEyeStem.getChild("rightEye");
		this.body = var1.getChild("body");
		this.lowerBody = this.body.getChild("lowerBody");
		this.shell = this.body.getChild("shell");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("leftEyeStem",CubeListBuilder.create().texOffs(29,0).mirror().addBox(-0.5F,-2F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offset(1.5F,20F,-2.5F)).addOrReplaceChild("leftEye",CubeListBuilder.create().texOffs(34,3).mirror().addBox(-1F,-1F,-1F,2F,2F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0.2F,-3F,0F));
		var1.addOrReplaceChild("rightEyeStem",CubeListBuilder.create().texOffs(29,0).mirror().addBox(-0.5F,-2F,-0.5F,1F,2F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offset(-0.5F,20F,-2.5F)).addOrReplaceChild("rightEye",CubeListBuilder.create().texOffs(34,3).mirror().addBox(-1F,-1F,-1F,2F,2F,2F,new CubeDeformation(0F)).mirror(false),PartPose.offset(-0.1667F,-3F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(21,11).mirror().addBox(-1.5F,-1.5F,-1.5F,3F,3F,3F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0.5F,21.5F,-2.5F));
		var2.addOrReplaceChild("lowerBody",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-2.5F,-0.5F,-4.5F,5F,1F,9F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0F,2F,3F));
		var2.addOrReplaceChild("shell",CubeListBuilder.create().texOffs(0,11).mirror().addBox(-2.5F,-2.5F,-2.5F,5F,5F,5F,new CubeDeformation(0F)).mirror(false),PartPose.offset(0F,-1F,4F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.leftEyeStem.render(var1,var2,var3,var4,var5);
		this.rightEyeStem.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
	}
}