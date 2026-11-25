package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.YagaraBullEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.YagaraBullModel;

public class YagaraBullRenderer<T extends YagaraBullEntity, M extends YagaraBullModel<T>> extends MobRenderer<T, M> {
	// Fields
	private static final float SCALE = 1.5F;

	public YagaraBullRenderer(EntityRendererProvider.Context var1) {
		super(var1,(EntityModel)(new YagaraBullModel(var1.bakeLayer(YagaraBullModel.LAYER_LOCATION))),1.3F);
	}
	public void scale(T var1, PoseStack var2, float var3) {
		var2.scale(1.5F,1.5F,1.5F);
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		if (var1.isSaddled()){
			((YagaraBullModel)(this.model)).renderSaddle();
		}
		super.render(var1,var2,var3,var4,var5,var6);
	}
	public ResourceLocation getTextureLocation(T var1) {
		return MobsHelper.YAGARA_BULL_TEXTURES[var1.getTextureId()];
	}
}