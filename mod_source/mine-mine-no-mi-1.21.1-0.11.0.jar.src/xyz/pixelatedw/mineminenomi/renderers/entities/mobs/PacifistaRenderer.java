package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.PacifistaModel;
import xyz.pixelatedw.mineminenomi.renderers.layers.PacifistaChargeLaserLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.PacifistaNumberLayer;

public class PacifistaRenderer<T extends PacifistaEntity, M extends PacifistaModel<T>> extends HumanoidMobRenderer<T, M> {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/pacifista.png");
	private static final float SCALE = 1.75F;

	public PacifistaRenderer(EntityRendererProvider.Context var1) {
		super(var1,(HumanoidModel)(new PacifistaModel(var1.bakeLayer(PacifistaModel.LAYER_LOCATION))),0.8F);
		this.addLayer((RenderLayer)(new PacifistaNumberLayer(var1,(RenderLayerParent)(this))));
		this.addLayer((RenderLayer)(new PacifistaChargeLaserLayer((RenderLayerParent)(this))));
	}
	public void scale(T var1, PoseStack var2, float var3) {
		var2.scale(1.75F,1.75F,1.75F);
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		super.render(var1,var2,var3,var4,var5,var6);
	}
	public ResourceLocation getTextureLocation(T var1) {
		return TEXTURE;
	}
}