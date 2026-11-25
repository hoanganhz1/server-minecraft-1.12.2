package xyz.pixelatedw.mineminenomi.models.morphs;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

@OnlyIn(value=Dist.CLIENT)
public class Phoenix2Model<T extends LivingEntity> extends HumanoidMorphModel<T> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","phoenix_2"),"main");
	private final ModelPart beak;
	private final ModelPart redThing;

	public Phoenix2Model(ModelPart var1) {
		super(var1);
		this.beak = var1.getChild("beak");
		this.redThing = var1.getChild("red_thing");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot();
		var1.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-2F,-6F,-2F,4F,6F,3F),PartPose.offset(0F,15F,-4F));
		var1.addOrReplaceChild("beak",CubeListBuilder.create().texOffs(14,0).addBox(-2F,-4F,-4F,4F,2F,2F),PartPose.offset(0F,15F,-4F));
		var1.addOrReplaceChild("red_thing",CubeListBuilder.create().texOffs(14,4).addBox(-1F,-2F,-3F,2F,2F,2F),PartPose.offset(0F,15F,-4F));
		var1.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,9).addBox(-3F,-4F,-3F,6F,8F,6F),PartPose.offsetAndRotation(0F,16F,0F,1.5707964F,0F,0F));
		CubeListBuilder var2 = CubeListBuilder.create().texOffs(26,0).addBox(-1F,0F,-3F,3F,5F,3F);
		var1.addOrReplaceChild("right_leg",var2,PartPose.offset(-2F,19F,1F));
		var1.addOrReplaceChild("left_leg",var2,PartPose.offset(1F,19F,1F));
		var1.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(24,13).addBox(0F,0F,-3F,1F,4F,6F),PartPose.offset(-4F,13F,0F));
		var1.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(24,13).addBox(-1F,0F,-3F,1F,4F,6F),PartPose.offset(4F,13F,0F));
		return LayerDefinition.create(var0,64,32);
	}
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head,this.beak,this.redThing);
	}
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body,this.rightArm,this.leftArm,this.rightLeg,this.leftLeg);
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		var1.translate(0D,0.25D,0D);
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.head.xRot = (var6) * (0.017453292F);
		this.head.yRot = (var5) * (0.017453292F);
		this.beak.xRot = this.head.xRot;
		this.beak.yRot = this.head.yRot;
		this.redThing.xRot = this.head.xRot;
		this.redThing.yRot = this.head.yRot;
		this.rightLeg.xRot = ((Mth.cos((var2) * (0.6662F))) * (1.4F)) * (var3);
		this.leftLeg.xRot = ((Mth.cos(((var2) * (0.6662F)) + (3.1415927F))) * (1.4F)) * (var3);
		this.rightArm.x = -3F;
		this.leftArm.x = 3F;
		this.rightArm.zRot = (1.3F) + ((Mth.cos((var4) * (0.5F))) * (0.9F));
		this.leftArm.zRot = (-1.3F) + ((Mth.cos(((var4) * (0.5F)) + (3.1415927F))) * (0.9F));
	}
	public void translateToHand(HumanoidArm var1, PoseStack var2) {
		this.head.translateAndRotate(var2);
		var2.scale(0.7F,0.7F,0.7F);
		var2.mulPose(Axis.ZP.rotationDegrees(90F));
		var2.mulPose(Axis.XP.rotationDegrees(260F));
		var2.translate(0.25D,0.6D,0.2D);
	}
	public ModelPart getHead() {
		return this.head;
	}
}