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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FightingFishEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.FightingFishModel;

public class FightingFishRenderer<T extends FightingFishEntity, M extends FightingFishModel<T>> extends MobRenderer<T, M> {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/fighting_fish.png");

	public FightingFishRenderer(EntityRendererProvider.Context var1) {
		super(var1,(EntityModel)(new FightingFishModel(var1.bakeLayer(FightingFishModel.LAYER_LOCATION))),1.5F);
	}
	public void scale(T var1, PoseStack var2, float var3) {
		var2.scale(var1.getSize(),var1.getSize(),var1.getSize());
		this.shadowRadius = (var1.getSize()) * (2.5F);
		var2.translate(0F,1F,0F);
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		super.render(var1,var2,var3,var4,var5,var6);
	}
	public ResourceLocation getTextureLocation(T var1) {
		return TEXTURE;
	}
}