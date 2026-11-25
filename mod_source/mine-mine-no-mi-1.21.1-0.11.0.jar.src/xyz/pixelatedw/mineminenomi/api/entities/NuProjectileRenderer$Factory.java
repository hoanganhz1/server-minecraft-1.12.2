package xyz.pixelatedw.mineminenomi.api.entities;

import java.awt.Color;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class NuProjectileRenderer$Factory<T extends NuProjectileEntity> implements EntityRendererProvider<T> {
	// Fields
	protected Supplier<? extends EntityModel<T>> rawModel;
	protected Function<EntityRendererProvider.Context, EntityModel<T>> model;
	protected float scaleX;
	protected float scaleY;
	protected float scaleZ;
	protected float rotationX;
	protected float rotationY;
	protected float rotationZ;
	protected Color colour;
	protected Supplier<Color> calcColor;
	protected ResourceLocation texture;
	protected boolean useArmSkin;
	protected boolean useLegSkin;
	protected boolean isGlowing;

	public NuProjectileRenderer$Factory() {
		this.scaleX = 1F;
		this.scaleY = 1F;
		this.scaleZ = 1F;
		this.rotationX = 0F;
		this.rotationY = 0F;
		this.rotationZ = 0F;
		this.colour = new Color(255,255,255,255);
		this.calcColor = null;
		this.useArmSkin = false;
		this.useLegSkin = false;
		this.isGlowing = false;
	}
	public NuProjectileRenderer$Factory<T> setModel(Supplier<? extends EntityModel<T>> var1) {
		this.rawModel = var1;
		return this;
	}
	public NuProjectileRenderer$Factory<T> setModel(Function<EntityRendererProvider.Context, EntityModel<T>> var1) {
		this.model = var1;
		return this;
	}
	public NuProjectileRenderer$Factory<T> setUseArmSkin() {
		this.useArmSkin = true;
		return this;
	}
	public NuProjectileRenderer$Factory<T> setUseLegSkin() {
		this.useLegSkin = true;
		return this;
	}
	public NuProjectileRenderer$Factory<T> setTexture(ResourceLocation var1) {
		this.texture = var1;
		return this;
	}
	public NuProjectileRenderer$Factory<T> setColor(float var1, float var2, float var3) {
		this.colour = new Color(var1,var2,var3,1F);
		return this;
	}
	public NuProjectileRenderer$Factory<T> setColor(float var1, float var2, float var3, float var4) {
		this.colour = new Color(var1,var2,var3,var4);
		return this;
	}
	public NuProjectileRenderer$Factory<T> setColor(int var1) {
		int var2 = ((var1) > (16777215)) ? (1) : (0);
		this.colour = new Color(var1,(var2) != (0));
		return this;
	}
	public NuProjectileRenderer$Factory<T> setColor(String var1) {
		this.colour = WyHelper.hexToRGB(var1);
		return this;
	}
	public NuProjectileRenderer$Factory<T> setAlpha(int var1) {
		this.colour = new Color(this.colour.getRed(),this.colour.getGreen(),this.colour.getBlue(),var1);
		return this;
	}
	public NuProjectileRenderer$Factory<T> setCalculatedColor(Supplier<Color> var1) {
		this.calcColor = var1;
		return this;
	}
	public NuProjectileRenderer$Factory<T> setScale(double var1) {
		float var2 = (float)(var1);
		this.scaleZ = var2;
		this.scaleY = var2;
		this.scaleX = var2;
		return this;
	}
	public NuProjectileRenderer$Factory<T> setScale(double var1, double var2, double var3) {
		this.scaleX = (float)(var1);
		this.scaleY = (float)(var2);
		this.scaleZ = (float)(var3);
		return this;
	}
	public NuProjectileRenderer$Factory<T> setRotation(float var1, float var2, float var3) {
		this.rotationX = var1;
		this.rotationY = var2;
		this.rotationZ = var3;
		return this;
	}
	public NuProjectileRenderer$Factory<T> setGlowing() {
		this.isGlowing = true;
		return this;
	}
	public EntityRenderer<T> create(EntityRendererProvider.Context var1) {
		EntityModel var2 = null;
		if ((this.rawModel) != (null)){
			var2 = ((EntityModel)(this.rawModel.get()));
		}else{
			if ((this.model) != (null)){
				var2 = ((EntityModel)(((java.util.function.Function)(this.model)).apply((Object)(var1))));
			}
		}
		NuProjectileRenderer var3 = new NuProjectileRenderer(var1,var2);
		var3.setTexture(this.texture);
		var3.setScale(this.scaleX,this.scaleY,this.scaleZ);
		var3.setRotation(this.rotationX,this.rotationY,this.rotationZ);
		var3.setColor(this.colour);
		if (this.useArmSkin){
			var3.setUseArmSkin();
		}
		if (this.useLegSkin){
			var3.setUseLegSkin();
		}
		if (this.isGlowing){
			var3.setGlowing();
		}
		var3.calcColor = this.calcColor;
		return var3;
	}
}