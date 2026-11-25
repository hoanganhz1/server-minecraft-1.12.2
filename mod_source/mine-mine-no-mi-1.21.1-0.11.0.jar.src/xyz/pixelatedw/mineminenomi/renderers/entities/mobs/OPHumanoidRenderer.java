package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModResources;

@OnlyIn(value=Dist.CLIENT)
public class OPHumanoidRenderer<T extends Mob, M extends HumanoidModel<T>> extends HumanoidMobRenderer<T, M> {
	// Fields
	protected float[] scale;
	protected ResourceLocation texture;

	public OPHumanoidRenderer(EntityRendererProvider.Context var1, Supplier<M> var2, float[] var3) {
		super(var1,((HumanoidModel)(var2.get())),0.5F);
		this.scale = var3;
		this.addLayer((RenderLayer)(new HumanoidArmorLayer((RenderLayerParent)(this),((HumanoidModel)(var2.get())),((HumanoidModel)(var2.get())),var1.getModelManager())));
	}
	public void scale(T var1, PoseStack var2, float var3) {
		var2.scale(this.scale[0],this.scale[1],this.scale[2]);
	}
	public void setTexture(ResourceLocation var1) {
		this.texture = var1;
	}
	public ResourceLocation getTextureLocation(T var1) {
		if ((this.texture) != (null)){
			return this.texture;
		}else{
			if (var1 instanceof OPEntity){
				ResourceLocation var2 = ((OPEntity)(var1)).getCurrentTexture();
				if ((var2) != (null)){
					return var2;
				}
			}
			return ModResources.NULL_ENTITY_TEXTURE;
		}
	}
}