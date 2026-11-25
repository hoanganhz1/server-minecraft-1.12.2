package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.DoppelmanEntity;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.CloneHumanoidRenderer;

public class DoppelmanRenderer extends CloneHumanoidRenderer<DoppelmanEntity, PlayerModel<DoppelmanEntity>> {
	// Fields
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/doppelman.png");

	public DoppelmanRenderer(EntityRendererProvider.Context var1) {
		super(var1);
	}
	public void scale(DoppelmanEntity var1, PoseStack var2, float var3) {
		float var4 = Math.max(1F,(1F) + (((float)(var1.getShadows())) / (7F)));
		var2.scale(var4,var4,var4);
	}
	public ResourceLocation getTextureLocation(DoppelmanEntity var1) {
		return TEXTURE;
	}
}