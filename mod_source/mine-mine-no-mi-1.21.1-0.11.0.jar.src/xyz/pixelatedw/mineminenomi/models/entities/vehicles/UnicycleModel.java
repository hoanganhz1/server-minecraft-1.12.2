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
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.CabajiEntity;
import xyz.pixelatedw.mineminenomi.entities.vehicles.UnicycleEntity;

public class UnicycleModel extends EntityModel<UnicycleEntity> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","unicycle"),"main");
	private final ModelPart seat;
	private final ModelPart support;
	private final ModelPart pedals;
	private final ModelPart left;
	private final ModelPart leftPedal;
	private final ModelPart right;
	private final ModelPart rightPedal;
	private final ModelPart wheel;

	public UnicycleModel(EntityRendererProvider.Context var1) {
		ModelPart var2 = var1.bakeLayer(LAYER_LOCATION);
		this.seat = var2.getChild("seat");
		this.support = this.seat.getChild("support");
		this.pedals = this.support.getChild("pedals");
		this.left = this.pedals.getChild("left");
		this.leftPedal = this.left.getChild("leftPedal");
		this.right = this.pedals.getChild("right");
		this.rightPedal = this.right.getChild("rightPedal");
		this.wheel = this.support.getChild("wheel");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("seat",CubeListBuilder.create().texOffs(10,23).addBox(-2F,-2F,-3.25F,4F,2F,7F,new CubeDeformation(0F)).texOffs(20,0).addBox(-2F,-3F,2.75F,4F,3F,2F,new CubeDeformation(0.001F)),PartPose.offset(0F,11F,-0.5F)).addOrReplaceChild("support",CubeListBuilder.create().texOffs(0,0).addBox(-1F,-4F,-0.5F,2F,4F,2F,new CubeDeformation(-0.1F)).texOffs(20,11).addBox(-2F,-1F,-0.5F,4F,1F,2F,new CubeDeformation(0.001F)).texOffs(12,11).addBox(1F,-1F,-0.5F,1F,6F,2F,new CubeDeformation(-0.05F)).texOffs(12,11).addBox(-2F,-1F,-0.5F,1F,6F,2F,new CubeDeformation(-0.05F)).texOffs(0,20).addBox(-1F,3.5F,-0.5F,2F,2F,2F,new CubeDeformation(0F)),PartPose.offset(0F,3.5F,0F));
		PartDefinition var2 = var1.addOrReplaceChild("pedals",CubeListBuilder.create(),PartPose.offset(0F,4.5F,0.5F));
		var2.addOrReplaceChild("left",CubeListBuilder.create().texOffs(12,8).addBox(-0.0833F,-0.6667F,-0.5F,2F,1F,1F,new CubeDeformation(0F)),PartPose.offset(1.0833F,0.1667F,0F)).addOrReplaceChild("leftPedal",CubeListBuilder.create().texOffs(11,1).addBox(0F,-0.5F,-1F,2F,1F,2F,new CubeDeformation(0.1F)).texOffs(11,5).addBox(-1.5F,-0.5F,-0.5F,3F,1F,1F,new CubeDeformation(0F)),PartPose.offset(2.4167F,0.8333F,0F));
		var2.addOrReplaceChild("right",CubeListBuilder.create().texOffs(12,8).mirror().addBox(-2F,-0.5F,-0.5F,2F,1F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-1F,0F,0F,3.1416F,0F,0F)).addOrReplaceChild("rightPedal",CubeListBuilder.create().texOffs(11,1).mirror().addBox(-2F,-0.5F,-1F,2F,1F,2F,new CubeDeformation(0.001F)).mirror(false).texOffs(11,5).mirror().addBox(-1.5F,-0.5F,-0.5F,3F,1F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-2.5F,1F,0F,3.1416F,0F,0F));
		PartDefinition var3 = var1.addOrReplaceChild("wheel",CubeListBuilder.create().texOffs(0,15).addBox(-1F,-0.9946F,-5F,2F,1.9891F,2F,new CubeDeformation(0.001F)).texOffs(1,9).addBox(-0.5F,-0.5054F,-3F,1F,1F,3F,new CubeDeformation(-0.0001F)).texOffs(0,15).addBox(-1F,-0.9946F,3F,2F,1.9891F,2F,new CubeDeformation(0.001F)).texOffs(0,8).addBox(-0.5F,-0.5054F,0F,1F,1F,3F,new CubeDeformation(-0.0002F)).texOffs(0,15).addBox(-1F,3F,-0.9946F,2F,2F,1.9891F,new CubeDeformation(0.001F)).texOffs(0,8).addBox(-0.5F,0F,-0.4946F,1F,3F,1F,new CubeDeformation(0.0001F)).texOffs(0,15).addBox(-1F,-5F,-0.9946F,2F,2F,1.9891F,new CubeDeformation(0.001F)).texOffs(0,8).addBox(-0.5F,-3F,-0.5054F,1F,3F,1F,new CubeDeformation(-0.0001F)),PartPose.offset(0F,4.5F,0.5F));
		var3.addOrReplaceChild("hexadecagon_r1",CubeListBuilder.create().texOffs(0,15).addBox(-1F,-5F,-0.9946F,2F,2F,1.9891F,new CubeDeformation(-0.001F)).texOffs(0,15).addBox(-1F,3F,-0.9946F,2F,2F,1.9891F,new CubeDeformation(-0.001F)).texOffs(0,15).addBox(-1F,-0.9946F,3F,2F,1.9891F,2F,new CubeDeformation(-0.001F)).texOffs(0,15).addBox(-1F,-0.9946F,-5F,2F,1.9891F,2F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(0F,0F,0F,-0.3927F,0F,0F));
		var3.addOrReplaceChild("hexadecagon_r2",CubeListBuilder.create().texOffs(0,15).addBox(-1F,-5F,-0.9946F,2F,2F,1.9891F,new CubeDeformation(-0.001F)).texOffs(0,15).addBox(-1F,3F,-0.9946F,2F,2F,1.9891F,new CubeDeformation(-0.001F)).texOffs(0,15).addBox(-1F,-0.9946F,3F,2F,1.9891F,2F,new CubeDeformation(-0.001F)).texOffs(0,15).addBox(-1F,-0.9946F,-5F,2F,1.9891F,2F,new CubeDeformation(-0.001F)),PartPose.offsetAndRotation(0F,0F,0F,0.3927F,0F,0F));
		var3.addOrReplaceChild("inner_r1",CubeListBuilder.create().texOffs(1,9).addBox(-1F,-0.0054F,1F,1F,1F,3F,new CubeDeformation(-0.0001F)),PartPose.offsetAndRotation(0.5F,-0.5F,0F,-0.7854F,0F,0F));
		var3.addOrReplaceChild("hexadecagon_r3",CubeListBuilder.create().texOffs(0,15).addBox(-1F,-0.9946F,3F,2F,1.9891F,2F,new CubeDeformation(0.001F)).texOffs(0,15).addBox(-1F,-0.9946F,-5F,2F,1.9891F,2F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(0F,0F,0F,-0.7854F,0F,0F));
		var3.addOrReplaceChild("inner_r2",CubeListBuilder.create().texOffs(0,8).addBox(-1F,-0.0054F,1F,1F,1F,3F,new CubeDeformation(-0.0003F)),PartPose.offsetAndRotation(0.5F,0F,-0.75F,0.7854F,0F,0F));
		var3.addOrReplaceChild("hexadecagon_r4",CubeListBuilder.create().texOffs(0,15).addBox(-1F,-0.9946F,3F,2F,1.9891F,2F,new CubeDeformation(0.001F)).texOffs(0,15).addBox(-1F,-0.9946F,-5F,2F,1.9891F,2F,new CubeDeformation(0.001F)),PartPose.offsetAndRotation(0F,0F,0F,0.7854F,0F,0F));
		var3.addOrReplaceChild("inner_r3",CubeListBuilder.create().texOffs(0,8).addBox(-1F,-0.0054F,-3F,1F,1F,3F,new CubeDeformation(0.0003F)),PartPose.offsetAndRotation(0.5F,-0.5F,0.25F,-0.7854F,0F,0F));
		var3.addOrReplaceChild("inner_r4",CubeListBuilder.create().texOffs(0,8).addBox(-1F,-0.0054F,-3F,1F,1F,3F,new CubeDeformation(0.0002F)),PartPose.offsetAndRotation(0.5F,-0.25F,-0.5F,0.7854F,0F,0F));
		return LayerDefinition.create(var0,32,32);
	}
	public void setupAnim(UnicycleEntity var1, float var2, float var3, float var4, float var5, float var6) {
		Player var7 = null;
		float var8 = 0F;
		LivingEntity var9 = null;
		if ((var1.getControllingPassenger()) != (null)){
			var9 = var1.getControllingPassenger();
			if (var9 instanceof Player){
				Player var10 = ((Player)(var9));
				var7 = var10;
				var8 = var10.zza;
			}
		}else{
			LivingEntity var9_1 = var1.getControllingPassenger();
			if (var9_1 instanceof CabajiEntity){
				CabajiEntity var10_1 = ((CabajiEntity)(var9_1));
				double var11 = Math.abs((var1.getX()) - (var1.xOld));
				double var12 = Math.abs((var1.getZ()) - (var1.zOld));
				if (((var10_1.getTarget()) == (null)) && ((!((var11) > (0D))) && (!((var12) > (0D))))){

				}else{
					var8 = 1F;
				}
			}
		}
		if ((var7) != (null)){
			ModelPart var9_1_1 = this.wheel;
			var9_1_1.xRot = (var9_1_1.xRot) + (((0.1F) * (var8)) % (360F));
			ModelPart var10_2 = this.pedals;
			var10_2.xRot = (var10_2.xRot) + (((0.2F) * (var8)) % (360F));
			ModelPart var11_1 = this.rightPedal;
			var11_1.xRot = (var11_1.xRot) - (((0.2F) * (var8)) % (360F));
			ModelPart var12_1 = this.leftPedal;
			var12_1.xRot = (var12_1.xRot) - (((0.2F) * (var8)) % (360F));
			if ((var8) == (0F)){
				float var13 = 0F;
				this.leftPedal.xRot = var13;
				this.rightPedal.xRot = var13;
				this.pedals.xRot = var13;
			}else{
				return;
			}
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.seat.render(var1,var2,var3,var4,var5);
	}
}