package xyz.pixelatedw.mineminenomi.models.blocks;

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
import net.minecraft.world.level.Level;

public class FlagModel extends EntityModel<Entity> {
	// Fields
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi","flag"),"main");
	private final ModelPart flag1;
	private final ModelPart flag2;
	private final ModelPart flag3;

	public FlagModel(ModelPart var1) {
		this.flag1 = var1.getChild("flag1");
		this.flag2 = this.flag1.getChild("flag2");
		this.flag3 = this.flag2.getChild("flag3");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition var0 = new MeshDefinition();
		var0.getRoot().addOrReplaceChild("flag1",CubeListBuilder.create().texOffs(0,0).addBox(0F,-8F,0F,8F,16F,0F,new CubeDeformation(0F)),PartPose.offset(-8F,16F,0F)).addOrReplaceChild("flag2",CubeListBuilder.create().texOffs(8,0).addBox(0F,-8F,0F,8F,16F,0F,new CubeDeformation(0F)),PartPose.offset(8F,0F,0F)).addOrReplaceChild("flag3",CubeListBuilder.create().texOffs(16,0).addBox(0F,-8F,0F,8F,16F,0F,new CubeDeformation(0F)),PartPose.offset(8F,0F,0F));
		return LayerDefinition.create(var0,64,64);
	}
	public void setupAnim(Entity var1, float var2, float var3, float var4, float var5, float var6) {
	}
	public void flagAnimation(Level var1, long var2) {
		this.flag1.y = 16F;
		this.flag1.x = -8F;
		this.flag2.y = 0F;
		this.flag2.x = 8F;
		this.flag3.y = 0F;
		this.flag3.x = 8F;
		ModelPart var3 = this.flag1;
		var3.y = (var3.y) + ((float)((Math.cos(((double)(((float)(var2)) * (0.05F))) + (3.141592653589793D))) * (0.20000000298023224D)));
		ModelPart var4 = this.flag2;
		var4.y = (var4.y) + ((float)((Math.cos(((double)(((float)(var2)) * (0.1F))) + (3.141592653589793D))) * (0.30000001192092896D)));
		ModelPart var5 = this.flag3;
		var5.y = (var5.y) + ((float)((Math.cos(((double)(((float)(var2)) * (0.1F))) + (3.141592653589793D))) * (0.4000000059604645D)));
		this.flag1.yRot = (float)((Math.cos((double)((((float)(var2)) * (0.12F)) + (3.1415927F)))) * (0.009999999776482582D));
		this.flag2.yRot = (float)((Math.cos((double)((((float)(var2)) * (0.1F)) + (3.1415927F)))) * (0.019999999552965164D));
		this.flag3.yRot = (float)((Math.cos((double)((((float)(var2)) * (0.05F)) + (3.1415927F)))) * (0.019999999552965164D));
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		this.flag1.render(var1,var2,var3,var4,var5);
	}
}