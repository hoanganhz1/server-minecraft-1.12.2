package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.joml.Quaternionf;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.TornadoEntity;
import xyz.pixelatedw.mineminenomi.models.entities.TornadoModel;

public class TornadoRenderer<T extends TornadoEntity> extends EntityRenderer<T> {
	// Fields
	private static final int OUTER_TORNADO;
	private static final int INNER_TORNADO;
	private static final ResourceLocation[] TEXTURES;
	private TornadoModel<TornadoEntity> model;

	protected TornadoRenderer(EntityRendererProvider.Context var1) {
		super(var1);
		this.shadowRadius = 0.05F;
		this.model = (TornadoModel) (new TornadoModel(var1.bakeLayer(TornadoModel.LAYER_LOCATION)));
	}
	public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
		float var7 = ((float)(var1.tickCount)) + (var3);
		float var8 = var1.getSize();
		var4.pushPose();
		var4.pushPose();
		var4.scale(var8,var8,var8);
		var4.mulPose(Axis.XP.rotationDegrees(180F));
		var4.translate(0D,-1.4D,0D);
		VertexConsumer var9 = var5.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(var1)));
		this.model.setupAnim(var1,0F,0F,var7,0F,0F);
		this.model.renderToBuffer(var4,var9,var6,OverlayTexture.NO_OVERLAY,OUTER_TORNADO);
		var4.pushPose();
		var4.scale(0.85F,0.85F,0.85F);
		var4.translate(0D,0.05D,0D);
		this.model.setupAnim(var1,0F,0F,(-(var7)) / (2F),0F,0F);
		this.model.renderToBuffer(var4,var9,var6,OverlayTexture.NO_OVERLAY,INNER_TORNADO);
		var4.popPose();
		var4.popPose();
		float var10 = (var8) / (3F);
		var4.translate(0F,5F,0F);
		var4.pushPose();
		var4.mulPose(Axis.XP.rotationDegrees(180F));
		var4.translate(var10,-(var10),var10);
		int var13 = 0;
		LOOP_1:
		do{
			if ((var13) < (8)){
				float var14 = ((float)(var13)) * (45F);
				float var15 = 0F;
				do{
					if ((var15) < (8F)){
						float var16 = (var15) * (45F);
						var4.pushPose();
						var4.translate(-(var10),-(var10),-(var10));
						var4.mulPose(Axis.YP.rotationDegrees((var14) + (((float)(var1.tickCount)) * (var1.getSpeed()))));
						var4.mulPose(Axis.ZN.rotationDegrees((var16) - ((((float)(var1.tickCount)) * (var1.getSpeed())) / (2F))));
						var4.translate(var10,var10,var10);
						ItemStack var17 = new ItemStack((ItemLike)(Blocks.SAND));
						Minecraft.getInstance().getItemRenderer().renderStatic(var17,ItemDisplayContext.NONE,var6,OverlayTexture.NO_OVERLAY,var4,var5,var1.level(),var1.getId());
						var4.popPose();
						var15 = (var15) + (1F);
						continue;
					}else{
						break;
					}
				} while (true);
				var13++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
		var4.popPose();
		var4.popPose();
	}
	public ResourceLocation getTextureLocation(T var1) {
		int var2 = Math.min(30,(var1.tickCount) % (30));
		return TEXTURES[(var2) / (10)];
	}
	static  {
		OUTER_TORNADO = WyHelper.packColor(1F,1F,1F,0.4F);
		INNER_TORNADO = WyHelper.packColor(1F,1F,1F,0.6F);
		ResourceLocation[] var0 = new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/projectiles/tornado1.png"),ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/projectiles/tornado2.png"),ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/projectiles/tornado3.png")};
		TEXTURES = var0;
	}
}