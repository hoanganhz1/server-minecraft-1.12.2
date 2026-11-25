package xyz.pixelatedw.mineminenomi.models.entities.projectiles;

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
import net.minecraft.world.entity.Entity;

public class HydraModel<T extends Entity> extends EntityModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","hydra"),"main");
	private final ModelPart head;
	private final ModelPart head1;
	private final ModelPart head2;
	private final ModelPart head3;
	private final ModelPart head4;
	private final ModelPart rightHorn1;
	private final ModelPart rightHorn2;
	private final ModelPart leftHorn1;
	private final ModelPart leftHorn2;
	private final ModelPart jaw1;
	private final ModelPart jaw2;
	private final ModelPart teeth1;
	private final ModelPart teeth2;

	public HydraModel(ModelPart var1) {
		this.head = var1.getChild("head");
		this.head1 = this.head.getChild("head1");
		this.head2 = this.head.getChild("head2");
		this.head3 = this.head.getChild("head3");
		this.head4 = this.head.getChild("head4");
		this.rightHorn1 = this.head.getChild("rightHorn1");
		this.rightHorn2 = this.head.getChild("rightHorn2");
		this.leftHorn1 = this.head.getChild("leftHorn1");
		this.leftHorn2 = this.head.getChild("leftHorn2");
		this.jaw1 = this.head.getChild("jaw1");
		this.jaw2 = this.head.getChild("jaw2");
		this.teeth1 = this.head.getChild("teeth1");
		this.teeth2 = this.head.getChild("teeth2");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = new MeshDefinition();
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offset(0F,-4.6083F,1.5417F));
		PartDefinition var2 = var1.addOrReplaceChild("head1",CubeListBuilder.create().texOffs(0,0).addBox(-4F,-5F,-10F,8F,10F,10F,new CubeDeformation(0F)),PartPose.offset(0F,2.6083F,6.4583F));
		PartDefinition var3 = var1.addOrReplaceChild("head2",CubeListBuilder.create().texOffs(0,21).addBox(-4F,-0.5F,-0.5F,4F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.6917F,-5.2417F,-0.0447F,0.0869F,0.0876F));
		PartDefinition var4 = var1.addOrReplaceChild("head3",CubeListBuilder.create().texOffs(0,21).addBox(0F,-0.5F,-0.5F,4F,1F,1F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-1.6917F,-5.2417F,-0.0447F,-0.0869F,-0.0876F));
		PartDefinition var5 = var1.addOrReplaceChild("head4",CubeListBuilder.create().texOffs(11,21).addBox(-4F,-1.5F,-2F,8F,3F,2F,new CubeDeformation(0F)),PartPose.offset(0F,-0.8917F,-3.3417F));
		PartDefinition var6 = var1.addOrReplaceChild("rightHorn1",CubeListBuilder.create().texOffs(33,16).mirror().addBox(-1.5F,-1.5F,0F,3F,3F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(-2.6F,-0.9917F,3.4583F,0.7046F,-0.122F,-0.0528F));
		PartDefinition var7 = var1.addOrReplaceChild("rightHorn2",CubeListBuilder.create().texOffs(33,26).addBox(-1F,-1F,0F,2F,2F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-3.5F,-4.3917F,7.2583F,0.9923F,-0.1213F,-0.1231F));
		PartDefinition var8 = var1.addOrReplaceChild("leftHorn1",CubeListBuilder.create().texOffs(33,16).addBox(-1.5F,-1.5F,0F,3F,3F,6F,new CubeDeformation(0F)),PartPose.offsetAndRotation(2.6F,-0.9917F,3.4583F,0.7046F,0.122F,0.0528F));
		PartDefinition var9 = var1.addOrReplaceChild("leftHorn2",CubeListBuilder.create().texOffs(33,26).mirror().addBox(-1F,-1F,0F,2F,2F,6F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(3.5F,-4.3917F,7.2583F,0.9923F,0.1213F,0.1231F));
		PartDefinition var10 = var1.addOrReplaceChild("jaw1",CubeListBuilder.create().texOffs(0,27).addBox(-4F,0F,-10F,8F,2F,10F,new CubeDeformation(0F)),PartPose.offset(0F,0.6083F,-3.5417F));
		PartDefinition var11 = var1.addOrReplaceChild("jaw2",CubeListBuilder.create().texOffs(0,27).mirror().addBox(-4F,0F,-10F,8F,2F,10F,new CubeDeformation(0F)).mirror(false),PartPose.offsetAndRotation(0F,5.6083F,-3.5417F,0.2269F,0F,0F));
		PartDefinition var12 = var1.addOrReplaceChild("teeth1",CubeListBuilder.create().texOffs(28,39).addBox(-4F,0F,-10F,8F,2F,10F,new CubeDeformation(0F)),PartPose.offset(0F,2.6083F,-3.4417F));
		PartDefinition var13 = var1.addOrReplaceChild("teeth2",CubeListBuilder.create().texOffs(28,52).addBox(-4F,0F,-10.3F,8F,2F,10F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,3.6083F,-3.5417F,0.2269F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
	}
}