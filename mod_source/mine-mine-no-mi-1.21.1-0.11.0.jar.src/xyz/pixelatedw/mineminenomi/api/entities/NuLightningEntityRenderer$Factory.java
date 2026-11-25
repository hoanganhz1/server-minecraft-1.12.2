package xyz.pixelatedw.mineminenomi.api.entities;

import java.awt.Color;
import java.util.function.Supplier;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class NuLightningEntityRenderer$Factory implements EntityRendererProvider<NuLightningEntity> {
	// Fields
	 ResourceLocation texture;
	 boolean useArmSkin;
	 int renderMode;
	 int color;
	 Supplier<Color> calcColor;

	public NuLightningEntityRenderer$Factory() {
		this.useArmSkin = false;
		this.renderMode = 0;
		this.color = -1;
		this.calcColor = null;
	}
	public NuLightningEntityRenderer$Factory setTexture(ResourceLocation var1) {
		this.texture = var1;
		this.renderMode = 2;
		return this;
	}
	public NuLightningEntityRenderer$Factory setLightningMode() {
		this.renderMode = 1;
		return this;
	}
	public NuLightningEntityRenderer$Factory setUseArmSkin() {
		this.useArmSkin = true;
		this.renderMode = 2;
		return this;
	}
	public NuLightningEntityRenderer$Factory setColor(int var1) {
		this.color = var1;
		return this;
	}
	public NuLightningEntityRenderer$Factory setCalculatedColor(Supplier<Color> var1) {
		this.calcColor = var1;
		return this;
	}
	public EntityRenderer<NuLightningEntity> create(EntityRendererProvider.Context var1) {
		NuLightningEntityRenderer var2 = new NuLightningEntityRenderer(var1);
		var2.setTexture(this.texture);
		if (this.useArmSkin){
			var2.setUseArmSkin();
		}
		var2.setRenderMode(this.renderMode);
		var2.setColor(this.color);
		var2.setCalculatedColor(this.calcColor);
		return (EntityRenderer) (var2);
	}
}