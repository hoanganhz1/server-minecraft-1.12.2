package xyz.pixelatedw.mineminenomi.renderers.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.entities.IRandomizedTexture;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.CloneEntity;
import xyz.pixelatedw.mineminenomi.init.ModResources;

@OnlyIn(value=Dist.CLIENT)
public class CloneHumanoidRenderer<T extends CloneEntity, M extends HumanoidModel<T>> extends HumanoidMobRenderer<T, M> {
	// Fields
	protected ResourceLocation texture;
	protected boolean useSimpleModel;
	private EntityRendererProvider.Context ctx;

	public CloneHumanoidRenderer(EntityRendererProvider.Context var1) {
		super(var1,(HumanoidModel)(null),0.5F);
		this.ctx = var1;
	}
	public void init(T var1) {
		if ((this.model) == (null)){
			int var2 = 0;
			if ((var1.getOwner()) != (null)){
				LivingEntity var3 = var1.getOwner();
				if ((var3 instanceof AbstractClientPlayer) && (((AbstractClientPlayer)(var3)).getSkin().model().equals(PlayerSkin.Model.SLIM))){
					var2 = 1;
				}
			}
			this.model = (this.useSimpleModel) ? (new HumanoidModel(this.ctx.bakeLayer(ModelLayers.ZOMBIE))) : (new PlayerModel(this.ctx.bakeLayer(((var2) != (0)) ? (ModelLayers.PLAYER_SLIM) : (ModelLayers.PLAYER)),(var2) != (0)));
			if (this.useSimpleModel){
				this.addLayer((RenderLayer)(new HumanoidArmorLayer((RenderLayerParent)(this),(HumanoidModel)(new HumanoidArmorModel(this.ctx.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR))),(HumanoidModel)(new HumanoidArmorModel(this.ctx.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR))),this.ctx.getModelManager())));
			}else{
				this.addLayer((RenderLayer)(new HumanoidArmorLayer((RenderLayerParent)(this),(HumanoidModel)(new HumanoidArmorModel(this.ctx.bakeLayer(((var2) != (0)) ? (ModelLayers.PLAYER_SLIM_INNER_ARMOR) : (ModelLayers.PLAYER_INNER_ARMOR)))),(HumanoidModel)(new HumanoidArmorModel(this.ctx.bakeLayer(((var2) != (0)) ? (ModelLayers.PLAYER_SLIM_OUTER_ARMOR) : (ModelLayers.PLAYER_OUTER_ARMOR)))),this.ctx.getModelManager())));
			}
		}
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		this.init(var1);
		super.render(var1,var2,var3,var4,var5,var6);
	}
	public void setTexture(ResourceLocation var1) {
		this.texture = var1;
	}
	public void setSimpleModel() {
		this.useSimpleModel = true;
	}
	public ResourceLocation getTextureLocation(T var1) {
		if (var1.isUsingOwnerTexture()){
			LivingEntity var2 = var1.getOwner();
			if (var2 instanceof AbstractClientPlayer){
				ResourceLocation var3 = ((AbstractClientPlayer)(var2)).getSkin().texture();
				if ((var3) != (null)){
					return var3;
				}
			}
		}else{
			if ((this.texture) != (null)){
				return this.texture;
			}else{
				if (var1 instanceof IRandomizedTexture){
					return ((IRandomizedTexture)(var1)).getTexture();
				}
			}
		}
		return ModResources.NULL_ENTITY_TEXTURE;
	}
}