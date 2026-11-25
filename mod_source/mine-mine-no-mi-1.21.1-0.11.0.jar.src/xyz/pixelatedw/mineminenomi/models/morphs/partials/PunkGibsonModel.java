package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.projectile.Projectile;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.morph.IFirstPersonHandReplacer;

public class PunkGibsonModel<T extends Entity> extends EntityModel<T> implements IFirstPersonHandReplacer {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","punk_gibson"),"main");
	public final ModelPart rightArm;
	private final ModelPart smallArm;
	private final ModelPart bigArm;
	private final ModelPart finger4;
	private final ModelPart finger4b;
	private final ModelPart finger3;
	private final ModelPart finger3b;
	private final ModelPart finger2;
	private final ModelPart finger2b;
	private final ModelPart finger1;
	private final ModelPart finger1b;

	public PunkGibsonModel(ModelPart var1) {
		this.rightArm = var1.getChild("right_arm");
		this.smallArm = this.rightArm.getChild("smallArm");
		this.bigArm = this.rightArm.getChild("bigArm");
		this.finger4 = this.bigArm.getChild("finger4");
		this.finger4b = this.finger4.getChild("finger4b");
		this.finger3 = this.bigArm.getChild("finger3");
		this.finger3b = this.finger3.getChild("finger3b");
		this.finger2 = this.bigArm.getChild("finger2");
		this.finger2b = this.finger2.getChild("finger2b");
		this.finger1 = this.bigArm.getChild("finger1");
		this.finger1b = this.finger1.getChild("finger1b");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = HumanoidModel.createMesh(CubeDeformation.NONE,0F);
		PartDefinition var1 = var0.getRoot().addOrReplaceChild("right_arm",CubeListBuilder.create(),PartPose.offsetAndRotation(-2.75F,-0.75F,-0.5F,-1.5272F,0F,0F));
		var1.addOrReplaceChild("smallArm",CubeListBuilder.create().texOffs(64,83).addBox(-16F,-7F,-6F,16F,29F,16F,new CubeDeformation(0F)),PartPose.offset(-1.0019F,-0.0872F,2.5F));
		PartDefinition var2 = var1.addOrReplaceChild("bigArm",CubeListBuilder.create().texOffs(0,0).addBox(-12.7334F,0.2782F,-11.8359F,24F,58F,24F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-8.0019F,19.9128F,5F,0.0452F,0.3127F,-0.1608F));
		var2.addOrReplaceChild("finger4",CubeListBuilder.create().texOffs(0,103).addBox(-3.5F,-2F,-7F,7F,4F,14F,new CubeDeformation(0F)),PartPose.offsetAndRotation(11.3785F,55.124F,-6.8285F,0.7105F,-0.7987F,-0.3516F)).addOrReplaceChild("finger4b",CubeListBuilder.create().texOffs(0,85).addBox(-3.5F,-1.2224F,-14.1691F,7F,4F,14F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-1.0418F,-6.3826F,1.0472F,0F,0F));
		var2.addOrReplaceChild("finger3",CubeListBuilder.create().texOffs(0,103).addBox(-3.2897F,-0.6527F,-11.7155F,7F,4F,14F,new CubeDeformation(0F)),PartPose.offsetAndRotation(6.6941F,56.5544F,-11.7482F,0.8733F,-0.028F,-0.0259F)).addOrReplaceChild("finger3b",CubeListBuilder.create().texOffs(0,85).addBox(-3.2897F,0.9734F,-15.8321F,7F,4F,14F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-2.6608F,-11.5565F,1.0036F,0F,0F));
		var2.addOrReplaceChild("finger2",CubeListBuilder.create().texOffs(0,103).addBox(-3.299F,-0.6527F,-11.7065F,7F,4F,14F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-1.3059F,56.5544F,-11.7482F,0.8728F,0F,0.0076F)).addOrReplaceChild("finger2b",CubeListBuilder.create().texOffs(0,85).addBox(-3.299F,1.5005F,-15.4963F,7F,4F,14F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0F,-2.9108F,-12.3065F,1.0036F,0F,0F));
		var2.addOrReplaceChild("finger1",CubeListBuilder.create().texOffs(0,103).addBox(-3.1633F,-0.6465F,-11.6975F,7F,4F,14F,new CubeDeformation(0F)),PartPose.offsetAndRotation(-9.3674F,56.552F,-11.763F,0.8728F,0.043F,0.0076F)).addOrReplaceChild("finger1b",CubeListBuilder.create().texOffs(0,85).addBox(-3.3781F,1.5072F,-15.4983F,7F,4F,14F,new CubeDeformation(0.01F)),PartPose.offsetAndRotation(0.2148F,-2.91F,-12.3019F,1.0036F,0F,0F));
		return LayerDefinition.create(var0,128,128);
	}
	public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
		this.rightArm.xRot = (float)(Math.toRadians(-100D));
		this.rightArm.zRot = 0F;
		this.rightArm.yRot = 0.25F;
		this.rightArm.x = -3F;
		if ((this.attackTime) > (0F)){
			ModelPart var7 = this.rightArm;
			var7.xRot = (float)(((double)(var7.xRot)) + (((double)(Mth.sin((float)(((double)(this.attackTime)) * (3.141592653589793D))))) / (3D)));
			ModelPart var8 = this.rightArm;
			var8.yRot = (float)(((double)(var8.yRot)) - (((double)(Mth.sin((float)(((double)(this.attackTime)) * (3.141592653589793D))))) / (3D)));
			ModelPart var9 = this.rightArm;
			var9.zRot = (float)(((double)(var9.zRot)) - (((double)(Mth.sin((float)(((double)(this.attackTime)) * (3.141592653589793D))))) / (3D)));
		}
		if (var1 instanceof Projectile){
			this.rightArm.yRot = 0F;
			this.rightArm.x = 8F;
		}
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.rightArm.render(var1,var2,var3,var4,var5);
	}
	public boolean renderFirstPersonArm(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5, HumanoidArm var6, boolean var7) {
		var1.translate(-0.5D,0.2D,0.9D);
		var1.mulPose(Axis.YP.rotationDegrees(70F));
		var1.mulPose(Axis.XP.rotationDegrees(20F));
		float var8 = 0F;
		this.rightArm.zRot = var8;
		this.rightArm.yRot = var8;
		this.rightArm.xRot = var8;
		this.renderToBuffer(var1,var2,var3,var4,var5);
		return true;
	}
}