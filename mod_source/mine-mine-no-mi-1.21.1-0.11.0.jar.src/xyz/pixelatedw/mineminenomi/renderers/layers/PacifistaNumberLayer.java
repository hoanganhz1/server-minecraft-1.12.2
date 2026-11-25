package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Random;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.PacifistaModel;

public class PacifistaNumberLayer<T extends PacifistaEntity, M extends PacifistaModel<T>> extends RenderLayer<T, M> {
	// Fields
	private static final DecimalFormat FORMAT = new DecimalFormat("0000");
	private static final Random RANDOM = new Random();
	private final Font font;
	private final String idLabel;
	private final float labelPosX;

	public PacifistaNumberLayer(EntityRendererProvider.Context var1, RenderLayerParent<T, M> var2) {
		super(var2);
		this.font = var1.getFont();
		int var3 = RANDOM.nextInt(9999);
		String var4 = FORMAT.format((long)(var3));
		String var5 = "PX-" + var4;
		this.idLabel = var5;
		this.labelPosX = (float)((-(this.font.width(this.idLabel))) / (2));
	}
	public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		var1.pushPose();
		float var11 = 0.003F;
		float var12 = (((PacifistaModel)(this.getParentModel())).head.y) * (0.07F);
		float var13 = (((PacifistaModel)(this.getParentModel())).head.x) * (0.02F);
		float var14 = (((PacifistaModel)(this.getParentModel())).head.z) * (0.01F);
		var1.mulPose(Axis.YP.rotationDegrees((var9) - (90F)));
		var1.mulPose(Axis.ZN.rotationDegrees(var10));
		var1.translate((-0.15F) + (var13),(-0.055F) + (var12),(-0.251F) + (var14));
		var1.scale(var11,var11,1F);
		Matrix4f var15 = var1.last().pose();
		int var16 = ((int)((Minecraft.getInstance().options.getBackgroundOpacity(0F)) * (255F))) << (24);
		if (var4.isAlive()){
			this.font.drawInBatch(this.idLabel,this.labelPosX,0F,0,false,var15,var2,Font.DisplayMode.NORMAL,var16,var3);
		}
		var1.popPose();
	}
}