package xyz.pixelatedw.mineminenomi.renderers.entities.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileRenderer;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.ChloroBallProjectile;
import xyz.pixelatedw.mineminenomi.models.SphereModel;

public class ChloroBallRenderer extends NuProjectileRenderer<ChloroBallProjectile, SphereModel<ChloroBallProjectile>> {
	// Fields
	private static final Color NORMAL_COLOR = WyHelper.hexToRGB("#A020F0");
	private static final Color DEMON_COLOR = WyHelper.hexToRGB("#5A0000");
	private static final Vec3 NORMAL_SCALE = new Vec3(2D,2D,2D);
	private static final Vec3 DEMON_SCALE = new Vec3(4D,4D,4D);

	public ChloroBallRenderer(EntityRendererProvider.Context var1) {
		super(var1,new SphereModel(var1.bakeLayer(SphereModel.LAYER_LOCATION)));
		this.setNormalDetails();
	}
	public void render(ChloroBallProjectile var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		if (var1.isDemonMode()){
			this.setDemonDetails();
		}else{
			this.setNormalDetails();
		}
		super.render(var1,var2,var3,var4,var5,var6);
	}
	private void setDemonDetails() {
		this.setColor(DEMON_COLOR);
		this.setScale(DEMON_SCALE);
	}
	private void setNormalDetails() {
		this.setColor(NORMAL_COLOR);
		this.setScale(NORMAL_SCALE);
	}
}