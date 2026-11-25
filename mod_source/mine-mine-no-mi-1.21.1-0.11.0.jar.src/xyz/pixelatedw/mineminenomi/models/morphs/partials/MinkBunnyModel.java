package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;

public class MinkBunnyModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","mink_bunny_partial"),"main");
	private final ModelPart ears;
	private final ModelPart rightEar1;
	private final ModelPart rightEar2;
	private final ModelPart leftEar1;
	private final ModelPart leftEar2;
	private final ModelPart tail;

	public MinkBunnyModel(ModelPart var1) {
		super(var1);
		this.ears = var1.getChild("ears");
		this.rightEar1 = this.ears.getChild("rightEar1");
		this.rightEar2 = this.rightEar1.getChild("rightEar2");
		this.leftEar1 = this.ears.getChild("leftEar1");
		this.leftEar2 = this.leftEar1.getChild("leftEar2");
		this.tail = var1.getChild("tail");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(new CubeDeformation(0F),0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("ears",CubeListBuilder.create(),PartPose.offset(0F,8F,0F));
		PartDefinition var3 = var2.addOrReplaceChild("rightEar1",CubeListBuilder.create().texOffs(0,25).addBox(-2F,-5F,0F,3F,5F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-2F,-8F,0F,0F,-0.2182F,-0.2182F)).addOrReplaceChild("rightEar2",CubeListBuilder.create().texOffs(0,16).addBox(-1.4685F,-5.2761F,-0.1179F,3F,5F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-0.5315F,-4.4739F,0.1179F,0.2967F,0F,0F));
		PartDefinition var4 = var2.addOrReplaceChild("leftEar1",CubeListBuilder.create().texOffs(0,25).mirror().addBox(-1F,-5F,0F,3F,5F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(2F,-8F,0F,0F,0.2182F,0.2182F)).addOrReplaceChild("leftEar2",CubeListBuilder.create().texOffs(0,16).mirror().addBox(-1.5315F,-5.2761F,-0.1179F,3F,5F,1F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0.5315F,-4.4739F,0.1179F,0.2967F,0F,0F));
		PartDefinition var5 = var1.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(0,0).addBox(-3F,7F,2F,6F,6F,6F,new CubeDeformation(0F)),PartPose.offset(0F,3F,1F));
		return LayerDefinition.create(var0,32,32);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.tail.yRot = (float)((Math.sin(((double)(var4)) * (0.06D))) / (6D));
		this.tail.xRot = (-(this.body.xRot)) + ((float)((Math.sin(((double)(var4)) * (0.02D))) / (10D)));
		if (var1.isCrouching()){
			this.tail.xRot = -0.2F;
			this.tail.y = 9.5F;
		}
		if ((var1 instanceof Player) && (((Player)(var1)).getAbilities().flying)){
			double var7 = Math.abs((var1.xo) - (var1.getX()));
			double var8 = Math.abs((var1.zo) - (var1.getZ()));
			if ((!((var7) >= (0.2D))) && (!((var8) >= (0.2D)))){
				return;
			}else{
				this.tail.xRot = -0.2F;
			}
		}else{
			this.tail.y = 10F;
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.ears.copyFrom(this.head);
		this.tail.copyFrom(this.body);
		this.ears.render(var1,var2,var3,var4,var5);
		this.tail.render(var1,var2,var3,var4,var5);
	}
}