package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.function.Supplier;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity$TrainingMethod;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WanderingDugongEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;
import xyz.pixelatedw.mineminenomi.renderers.layers.WanderingDugongScarsLayer;

public class WanderingDugongRenderer<T extends WanderingDugongEntity, M extends DugongModel<T>> extends MobRenderer<T, M> {
	// Fields
	private static final ResourceLocation BASE_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/kung_fu_dugong.png");

	public WanderingDugongRenderer(EntityRendererProvider.Context var1, Supplier<M> var2) {
		super(var1,(EntityModel)(((DugongModel)(var2.get()))),0.4F);
		this.addLayer((RenderLayer)(new ItemInHandLayer((RenderLayerParent)(this),var1.getItemInHandRenderer())));
		this.addLayer((RenderLayer)(new WanderingDugongScarsLayer((RenderLayerParent)(this))));
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		super.render(var1,var2,var3,var4,var5,var6);
	}
	protected void setupRotations(T var1, PoseStack var2, float var3, float var4, float var5, float var6) {
		super.setupRotations(var1,var2,var3,var4,var5,var6);
		if (var1.isTraining()){
			switch (WanderingDugongRenderer$1.$SwitchMap$xyz$pixelatedw$mineminenomi$entities$mobs$animals$AbstractDugongEntity$TrainingMethod[var1.getTrainingMode().ordinal()]){
			case 1:
				var2.translate(0D,0.25D,0.5D);
				var2.mulPose(Axis.XP.rotationDegrees(-75F));
				return;
			case 2:

			default:
				return;
			}
		}else{
			return;
		}
	}
	public ResourceLocation getTextureLocation(T var1) {
		return BASE_TEXTURE;
	}
}