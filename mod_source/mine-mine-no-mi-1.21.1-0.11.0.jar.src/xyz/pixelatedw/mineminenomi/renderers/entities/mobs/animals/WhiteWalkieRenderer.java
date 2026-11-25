package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Predicate;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.WhiteWalkieModel;
import xyz.pixelatedw.mineminenomi.renderers.layers.EyesLayer;
import xyz.pixelatedw.mineminenomi.renderers.layers.WhiteWalkieSaddleLayer;

public class WhiteWalkieRenderer<T extends WhiteWalkieEntity, M extends WhiteWalkieModel<T>> extends MobRenderer<T, M> {
	// Fields
	private static final RenderType SLEEPING_EYES = ModRenderTypes.getEyesLayerRenderType(ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/white_walkie_sleeping_eyes.png"));
	public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/white_walkie.png");

	public WhiteWalkieRenderer(EntityRendererProvider.Context var1) {
		super(var1,(EntityModel)(new WhiteWalkieModel(var1.bakeLayer(WhiteWalkieModel.LAYER_LOCATION))),1.2F);
		this.addLayer((RenderLayer)(new EyesLayer((RenderLayerParent)(this),SLEEPING_EYES,(Predicate)(((Predicate<WhiteWalkieEntity>)(WhiteWalkieEntity::isSleeping))))));
		this.addLayer((RenderLayer)(new WhiteWalkieSaddleLayer(var1,(RenderLayerParent)(this))));
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		super.render(var1,var2,var3,var4,var5,var6);
	}
	protected void setupRotations(T var1, PoseStack var2, float var3, float var4, float var5, float var6) {
		super.setupRotations(var1,var2,var3,var4,var5,var6);
		if (var1.isSleeping()){
			var2.translate(0D,-0.55D,0D);
			((WhiteWalkieModel)(this.getModel())).setSleepPosition(true);
		}else{
			((WhiteWalkieModel)(this.getModel())).setSleepPosition(false);
		}
	}
	public ResourceLocation getTextureLocation(T var1) {
		return TEXTURE;
	}
}