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
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.ArlongEntity;

public class FishmanSawsharkModel<T extends LivingEntity> extends HumanoidModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","fishman_sawshark"),"main");
	private final ModelPart nose;
	private final ModelPart nose2;
	private final ModelPart backBlade;

	public FishmanSawsharkModel(ModelPart var1) {
		super(var1);
		this.nose = this.head.getChild("nose");
		this.nose2 = this.nose.getChild("nose2");
		this.backBlade = this.body.getChild("backBlade");
	}
	public static LayerDefinition createLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		PartDefinition var2 = var1.addOrReplaceChild("head",CubeListBuilder.create(),PartPose.offset(0F,0F,0F)).addOrReplaceChild("nose",CubeListBuilder.create().texOffs(0,0).addBox(-2F,-0.5F,-2F,2F,1F,2F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,-2.75F,-3F,0F,-0.7854F,0F)).addOrReplaceChild("nose2",CubeListBuilder.create().texOffs(0,0).mirror().addBox(-2F,-0.5F,-2F,2F,1F,2F,new CubeDeformation(-0.001F)).mirror(false).texOffs(0,0).addBox(-3F,-0.5F,-3F,2F,1F,2F,new CubeDeformation(0F)),PartPose.offset(-1F,0F,-1F));
		PartDefinition var3 = var1.addOrReplaceChild("body",CubeListBuilder.create(),PartPose.offset(0F,0F,0F)).addOrReplaceChild("backBlade",CubeListBuilder.create().texOffs(0,0).addBox(-0.5F,-2.75F,0.25F,1F,7F,8F,new CubeDeformation(0F)),PartPose.offsetAndRotation(0F,0F,0F,-0.7854F,0F,0F));
		return LayerDefinition.create(var0,32,16);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		if (var1 instanceof ArlongEntity){
			this.nose.y = -3.75F;
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.head.render(var1,var2,var3,var4,var5);
		this.body.render(var1,var2,var3,var4,var5);
	}
}