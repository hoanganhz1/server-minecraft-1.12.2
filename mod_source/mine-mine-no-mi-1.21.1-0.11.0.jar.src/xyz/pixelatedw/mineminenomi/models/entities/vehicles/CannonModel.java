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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.entities.vehicles.CannonEntity;

public class CannonModel extends EntityModel<CannonEntity> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","cannon"),"main");
	private final ModelPart cart;
	private final ModelPart cartBottom;
	private final ModelPart cartWheel1;
	private final ModelPart cartWheel3;
	private final ModelPart cartWheel2;
	private final ModelPart cartWheel4;
	private final ModelPart cartWallFront;
	private final ModelPart cartWallRight;
	private final ModelPart cartWallRight1;
	private final ModelPart cartWallRight2;
	private final ModelPart cartWallLeft;
	private final ModelPart cartWallLeft1;
	private final ModelPart cartWallLeft2;
	private final ModelPart cannon;
	private final ModelPart cannonDeco3;
	private final ModelPart cannonBack2;
	private final ModelPart cannonBackKnob;
	private final ModelPart cannonBack;
	private final ModelPart cannonDeco;
	private final ModelPart cannonThing;
	private final ModelPart cannonDeco2;

	public CannonModel(EntityRendererProvider.Context var1) {
		ModelPart var2 = var1.bakeLayer(LAYER_LOCATION);
		this.cart = var2.getChild("cart");
		this.cartBottom = this.cart.getChild("cartBottom");
		this.cartWheel1 = this.cartBottom.getChild("cartWheel1");
		this.cartWheel3 = this.cartBottom.getChild("cartWheel3");
		this.cartWheel2 = this.cartBottom.getChild("cartWheel2");
		this.cartWheel4 = this.cartBottom.getChild("cartWheel4");
		this.cartWallFront = this.cart.getChild("cartWallFront");
		this.cartWallRight = this.cart.getChild("cartWallRight");
		this.cartWallRight1 = this.cartWallRight.getChild("cartWallRight1");
		this.cartWallRight2 = this.cartWallRight.getChild("cartWallRight2");
		this.cartWallLeft = this.cart.getChild("cartWallLeft");
		this.cartWallLeft1 = this.cartWallLeft.getChild("cartWallLeft1");
		this.cartWallLeft2 = this.cartWallLeft.getChild("cartWallLeft2");
		this.cannon = var2.getChild("cannon");
		this.cannonDeco3 = this.cannon.getChild("cannonDeco3");
		this.cannonBack2 = this.cannon.getChild("cannonBack2");
		this.cannonBackKnob = this.cannon.getChild("cannonBackKnob");
		this.cannonBack = this.cannon.getChild("cannonBack");
		this.cannonDeco = this.cannon.getChild("cannonDeco");
		this.cannonThing = this.cannon.getChild("cannonThing");
		this.cannonDeco2 = this.cannon.getChild("cannonDeco2");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("cart",CubeListBuilder.create().texOffs(24,44).addBox(-2.5F,0F,-6F,5F,5F,15F,new CubeDeformation(0F)),PartPose.offset(0F,16F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("cartBottom",CubeListBuilder.create().texOffs(0,43).addBox(-6F,-0.5F,-10F,12F,1F,20F,new CubeDeformation(0F)),PartPose.offset(0F,5.5F,3F));
		PartDefinition var4 = var3.addOrReplaceChild("cartWheel1",CubeListBuilder.create().texOffs(0,12).addBox(-1F,-2.5F,-2.5F,2F,5F,5F,new CubeDeformation(0F)),PartPose.offset(-6F,0.5F,-5.5F));
		PartDefinition var5 = var3.addOrReplaceChild("cartWheel3",CubeListBuilder.create().texOffs(0,12).addBox(-1F,-2.5F,-2.5F,2F,5F,5F,new CubeDeformation(0F)),PartPose.offset(6F,0.5F,-5.5F));
		PartDefinition var6 = var3.addOrReplaceChild("cartWheel2",CubeListBuilder.create().texOffs(0,12).addBox(-1F,-2.5F,-2.5F,2F,5F,5F,new CubeDeformation(0F)),PartPose.offset(-6F,0.5F,5F));
		PartDefinition var7 = var3.addOrReplaceChild("cartWheel4",CubeListBuilder.create().texOffs(0,12).addBox(-1F,-2.5F,-2.5F,2F,5F,5F,new CubeDeformation(0F)),PartPose.offset(6F,0.5F,5F));
		PartDefinition var8 = var2.addOrReplaceChild("cartWallFront",CubeListBuilder.create().texOffs(0,45).addBox(-6F,-3F,-0.5F,12F,6F,1F,new CubeDeformation(0F)),PartPose.offset(0F,2F,-6.5F));
		PartDefinition var9 = var2.addOrReplaceChild("cartWallRight",CubeListBuilder.create().texOffs(0,44).addBox(-1F,-1F,-9F,2F,2F,18F,new CubeDeformation(0F)),PartPose.offset(5F,4F,3F));
		PartDefinition var10 = var9.addOrReplaceChild("cartWallRight1",CubeListBuilder.create().texOffs(0,44).addBox(-8F,15F,6F,2F,2F,16F,new CubeDeformation(0F)),PartPose.offset(7F,-18F,-15F));
		PartDefinition var11 = var9.addOrReplaceChild("cartWallRight2",CubeListBuilder.create().texOffs(0,44).addBox(-8F,15F,6F,2F,2F,14F,new CubeDeformation(0F)),PartPose.offset(7F,-20F,-15F));
		PartDefinition var12 = var2.addOrReplaceChild("cartWallLeft",CubeListBuilder.create().texOffs(0,44).addBox(-1F,-1F,-9F,2F,2F,18F,new CubeDeformation(0F)),PartPose.offset(-5F,4F,3F));
		PartDefinition var13 = var12.addOrReplaceChild("cartWallLeft1",CubeListBuilder.create().texOffs(0,44).addBox(-8F,0F,6F,2F,2F,14F,new CubeDeformation(0F)),PartPose.offset(7F,-5F,-15F));
		PartDefinition var14 = var12.addOrReplaceChild("cartWallLeft2",CubeListBuilder.create().texOffs(0,44).addBox(-8F,0F,6F,2F,2F,16F,new CubeDeformation(0F)),PartPose.offset(7F,-3F,-15F));
		PartDefinition var15 = var1.addOrReplaceChild("cannon",CubeListBuilder.create().texOffs(0,0).addBox(-3F,-5.5F,-18F,6F,6F,24F,new CubeDeformation(0F)),PartPose.offset(0F,15F,3F));
		PartDefinition var16 = var15.addOrReplaceChild("cannonDeco3",CubeListBuilder.create().texOffs(0,0).addBox(-3.5F,-3.5F,-0.5F,7F,7F,1F,new CubeDeformation(0F)),PartPose.offset(0F,-2.5F,-5F));
		PartDefinition var17 = var15.addOrReplaceChild("cannonBack2",CubeListBuilder.create().texOffs(0,0).addBox(-3F,-3F,-1F,6F,6F,2F,new CubeDeformation(0F)),PartPose.offset(0F,-2.5F,8F));
		PartDefinition var18 = var15.addOrReplaceChild("cannonBackKnob",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-1F,-1F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offset(0F,-2.5F,10F));
		PartDefinition var19 = var15.addOrReplaceChild("cannonBack",CubeListBuilder.create().texOffs(0,0).addBox(-3.5F,-3.5F,-2F,7F,7F,4F,new CubeDeformation(0F)),PartPose.offset(0F,-2.5F,5F));
		PartDefinition var20 = var15.addOrReplaceChild("cannonDeco",CubeListBuilder.create().texOffs(46,0).addBox(-3.5F,-3.5F,-1F,7F,7F,2F,new CubeDeformation(0F)),PartPose.offset(0F,-2.5F,-18F));
		PartDefinition var21 = var15.addOrReplaceChild("cannonThing",CubeListBuilder.create().texOffs(0,1).addBox(-15.5F,0F,6F,15F,2F,2F,new CubeDeformation(0F)),PartPose.offset(8F,-1.5F,-8F));
		PartDefinition var22 = var15.addOrReplaceChild("cannonDeco2",CubeListBuilder.create().texOffs(0,0).addBox(-11.5F,-3.5F,6F,7F,7F,1F,new CubeDeformation(0F)),PartPose.offset(8F,-2.5F,-20.5F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(CannonEntity var1, float var2, float var3, float var4, float var5, float var6) {
		if ((var1.getControllingPassenger()) != (null)){
			LivingEntity var7 = var1.getControllingPassenger();
			if (var7 instanceof Player){
				Player var8 = ((Player)(var7));
				ModelPart var9 = this.cartWheel1;
				var9.xRot = (var9.xRot) + ((0.02F) * (var8.zza));
				ModelPart var10 = this.cartWheel2;
				var10.xRot = (var10.xRot) + ((0.02F) * (var8.zza));
				ModelPart var11 = this.cartWheel3;
				var11.xRot = (var11.xRot) + ((0.02F) * (var8.zza));
				ModelPart var12 = this.cartWheel4;
				var12.xRot = (var12.xRot) + ((0.02F) * (var8.zza));
			}
		}
		this.cannon.xRot = (float)(Math.toRadians((double)(var1.getXRot())));
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.cart.render(var1,var2,var3,var4,var5);
		this.cannon.render(var1,var2,var3,var4,var5);
	}
}