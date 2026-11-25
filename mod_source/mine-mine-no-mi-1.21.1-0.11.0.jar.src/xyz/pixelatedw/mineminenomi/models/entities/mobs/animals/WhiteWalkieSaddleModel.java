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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;

public class WhiteWalkieSaddleModel<T extends WhiteWalkieEntity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","white_walkie_saddle"),"main");
	private final ModelPart leftBag;
	private final ModelPart rightBag;
	private final ModelPart saddle;
	private final ModelPart saddlechair;

	public WhiteWalkieSaddleModel(ModelPart var1) {
		this.leftBag = var1.getChild("leftBag");
		this.rightBag = var1.getChild("rightBag");
		this.saddle = var1.getChild("saddle");
		this.saddlechair = this.saddle.getChild("saddlechair");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("leftBag",CubeListBuilder.create().texOffs(0,0).addBox(-2.5F,-2.5F,1.5F,5F,5F,4F,new CubeDeformation(0.1F)).texOffs(0,0).addBox(-2.5F,-2.5F,-5.5F,5F,5F,4F,new CubeDeformation(0.1F)).texOffs(54,23).addBox(-2.5F,-2.5F,-9.5F,5F,5F,19F,new CubeDeformation(0F)),PartPose.offset(14.9375F,5.4511F,2.7092F));
		PartDefinition var3 = var1.addOrReplaceChild("rightBag",CubeListBuilder.create().texOffs(0,0).addBox(-2.5F,-2.5F,1.5F,5F,5F,4F,new CubeDeformation(0.1F)).texOffs(54,23).addBox(-2.5F,-2.5F,-9.5F,5F,5F,19F,new CubeDeformation(0F)).texOffs(0,0).addBox(-2.5F,-2.5F,-5.5F,5F,5F,4F,new CubeDeformation(0.1F)),PartPose.offset(-14.9375F,5.4511F,2.7092F));
		PartDefinition var4 = var1.addOrReplaceChild("saddle",CubeListBuilder.create().texOffs(0,0).addBox(-11F,-11.0385F,-17.5417F,22F,2F,21F,new CubeDeformation(0.4F)).texOffs(0,24).addBox(-13.4375F,-10.726F,-17.5417F,3F,16F,21F,new CubeDeformation(0F)).texOffs(0,24).addBox(10.4375F,-10.726F,-17.5417F,3F,16F,21F,new CubeDeformation(0F)),PartPose.offset(0F,3.4271F,9.751F)).addOrReplaceChild("saddlechair",CubeListBuilder.create().texOffs(30,26).addBox(-6F,-4.5F,-1F,12F,9F,2F,new CubeDeformation(0.4F)),PartPose.offsetAndRotation(0F,-15.5214F,1.7193F,-0.1309F,0F,0F));
		return LayerDefinition.create(var0,128,128);
	}
	public void prepareMobModel(T var1, float var2, float var3, float var4) {
		if (var1 instanceof WhiteWalkieEntity){
			T var5 = var1;
			if ((var5.getChests()) <= (0)){
				this.rightBag.visible = false;
				this.leftBag.visible = false;
			}else{
				if ((var5.getChests()) == (1)){
					this.rightBag.visible = true;
					this.leftBag.visible = false;
				}else{
					this.rightBag.visible = true;
					this.leftBag.visible = true;
				}
			}
		}
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.leftBag.render(var1,var2,var3,var4,var5);
		this.rightBag.render(var1,var2,var3,var4,var5);
		this.saddle.render(var1,var2,var3,var4,var5);
	}
}