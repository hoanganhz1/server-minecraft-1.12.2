package xyz.pixelatedw.mineminenomi.models.morphs.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public abstract class MorphArmorModel<T extends LivingEntity> extends HumanoidModel<T> {
	public MorphArmorModel(ModelPart var1) {
		super(var1);
	}
	public MorphArmorModel(ModelPart var1, Function<ResourceLocation, RenderType> var2) {
		super(var1,var2);
	}
	public static MeshDefinition createBodyLayer(CubeDeformation var0) {
		MeshDefinition var1 = HumanoidModel.createMesh(var0,0F);
		PartDefinition var2 = var1.getRoot();
		var2.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,16).addBox(-2F,0F,-2F,4F,12F,4F,var0.extend(-0.1F)),PartPose.offset(-1.9F,12F,0F));
		var2.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,16).mirror().addBox(-2F,0F,-2F,4F,12F,4F,var0.extend(-0.1F)),PartPose.offset(1.9F,12F,0F));
		return var1;
	}
	public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, int var5) {
		super.renderToBuffer(var1,var2,var3,var4,var5);
	}
}