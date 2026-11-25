package xyz.pixelatedw.mineminenomi.models.entities.vehicles;

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
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;

public class StrikerModel extends EntityModel<StrikerEntity> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","striker"),"main");
	private final ModelPart body;
	private final ModelPart tip;
	private final ModelPart tip2;
	private final ModelPart engine;
	private final ModelPart sail;
	private final ModelPart pole;
	private final ModelPart cloth;

	public StrikerModel(EntityRendererProvider.Context var1) {
		ModelPart var2 = var1.bakeLayer(LAYER_LOCATION);
		this.body = var2.getChild("body");
		this.tip = this.body.getChild("tip");
		this.tip2 = this.tip.getChild("tip2");
		this.engine = this.body.getChild("engine");
		this.sail = var2.getChild("sail");
		this.pole = this.sail.getChild("pole");
		this.cloth = this.pole.getChild("cloth");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(100,11).addBox(10.1563F,-3.7984F,-2.5F,5F,6F,5F,new CubeDeformation(0F)).texOffs(40,78).addBox(2.9063F,-3.5109F,-4F,9F,7F,8F,new CubeDeformation(0F)).texOffs(85,38).addBox(2.9063F,-3.1984F,-4.5F,7F,3F,9F,new CubeDeformation(0F)).texOffs(50,38).addBox(-14.0938F,-0.1984F,-4.5F,20F,3F,1F,new CubeDeformation(0F)).texOffs(0,37).addBox(-14.0938F,-0.1984F,3.5F,21F,3F,1F,new CubeDeformation(0F)).texOffs(50,0).addBox(-20.0938F,0.8016F,-4F,23F,3F,8F,new CubeDeformation(0F)).texOffs(84,33).addBox(-14.0938F,-3.1984F,4.5F,18F,4F,1F,new CubeDeformation(0F)).texOffs(90,110).addBox(-14.0938F,-3.1984F,-5.5F,18F,4F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0.0938F,20.1984F,-6F,0F,1.5708F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("tip",CubeListBuilder.create().texOffs(75,100).addBox(-3.5F,-2F,-2F,6F,5F,4F,new CubeDeformation(0F)),PartPose.offsetAndRotation(17.1641F,-2.6046F,0F,0F,0F,-0.2182F)).addOrReplaceChild("tip2",CubeListBuilder.create().texOffs(66,78).addBox(-1.726F,-1.0764F,-1.5F,6F,4F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2.1676F,-1.0169F,0F,0F,0F,-0.2618F));
		PartDefinition var4 = var2.addOrReplaceChild("engine",CubeListBuilder.create().texOffs(27,0).addBox(12.5625F,-2.5F,0.5F,4F,2F,4F,new CubeDeformation(0F)).texOffs(100,85).addBox(10.5625F,-2.85F,-0.5F,4F,2F,6F,new CubeDeformation(0F)).texOffs(100,50).addBox(9.5625F,-3.5F,-1F,3F,4F,7F,new CubeDeformation(0F)).texOffs(40,93).addBox(7.75F,-2.5F,-2F,2F,2F,9F,new CubeDeformation(0F)).texOffs(0,47).addBox(-5F,-2.5F,6.5F,14F,2F,1F,new CubeDeformation(0F)).texOffs(0,41).addBox(-5F,-2.5F,-2.5F,14F,2F,1F,new CubeDeformation(0F)).texOffs(90,68).addBox(-7F,-5.5F,-2.5F,3F,7F,10F,new CubeDeformation(0F)).texOffs(50,23).addBox(-17F,-5F,-1F,10F,8F,7F,new CubeDeformation(0F)).texOffs(53,95).addBox(-16F,-6F,0F,8F,1F,5F,new CubeDeformation(0F)).texOffs(0,22).addBox(-17F,-4F,-2F,10F,6F,9F,new CubeDeformation(0F)).texOffs(50,11).addBox(-5F,-0.5F,-2.5F,13F,2F,10F,new CubeDeformation(0F)).texOffs(0,0).addBox(-25F,-7F,-3F,8F,11F,11F,new CubeDeformation(0F)).texOffs(0,78).addBox(-23F,-7.5F,-3.5F,4F,12F,12F,new CubeDeformation(0F)).texOffs(86,13).addBox(-27F,-6.5F,-2.5F,2F,10F,10F,new CubeDeformation(0F)).texOffs(74,78).addBox(-28F,-7F,-3F,2F,11F,11F,new CubeDeformation(0F)),PartPose.offset(-6.0938F,-1.1984F,-2.5F));
		PartDefinition var5 = var1.addOrReplaceChild("sail",CubeListBuilder.create().texOffs(27,6).addBox(-1.5F,-1F,-1.5F,3F,2F,3F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,12F,12F,0F,1.5708F,0F)).addOrReplaceChild("pole",CubeListBuilder.create().texOffs(32,78).addBox(-1.025F,-41.0417F,-1F,2F,41F,2F,new CubeDeformation(0F)).texOffs(0,0).addBox(-1.525F,-31.0417F,-22F,3F,3F,44F,new CubeDeformation(0F)),PartPose.offset(0.025F,-0.9583F,0F)).addOrReplaceChild("cloth",CubeListBuilder.create().texOffs(0,0).addBox(0F,-1F,-25F,0F,28F,50F,new CubeDeformation(0F)),PartPose.offset(1.175F,-28.6042F,0F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(StrikerEntity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.body.render(var1,var2,var3,var4,var5);
		this.sail.render(var1,var2,var3,var4,var5);
	}
}