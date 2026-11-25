package xyz.pixelatedw.mineminenomi.api.helpers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexSorting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector2ic;
import org.lwjgl.opengl.GL11;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.extensions.IAgeableListModelExtension;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;

public class RendererHelper {
	// Fields
	public static final ResourceLocation VANILLA_VIGNETTE_TEX_PATH = ResourceLocation.parse("textures/misc/vignette.png");
	public static final ResourceLocation FORCEFIELD_LOCATION = ResourceLocation.parse("textures/misc/forcefield.png");
	private static final Color TOOLTIP_ICON_COLOR = WyHelper.hexToRGB("#333333");
	private static final Color WORLD_GOVT_COLOR = WyHelper.hexToRGB("#2B497B");

	public static void renderVignette(GuiGraphics var0, Entity var1, float var2, double var3, double var4) {
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO,GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,GlStateManager.SourceFactor.ONE,GlStateManager.DestFactor.ZERO);
		float var5 = var2;
		float var6 = var2;
		float var7 = var2;
		if ((var2) > (0.8F)){
			var5 = 0F;
			var6 = 1F;
			var7 = 1F;
		}
		var0.setColor(var5,var6,var7,1F);
		Minecraft var8 = Minecraft.getInstance();
		int var9 = var8.getWindow().getGuiScaledWidth();
		int var10 = var8.getWindow().getGuiScaledHeight();
		var0.blit(VANILLA_VIGNETTE_TEX_PATH,0,0,-90,0F,0F,var9,var10,var9,var10);
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		var0.setColor(1F,1F,1F,1F);
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableBlend();
	}
	public static void drawPlayerFactionIcon(Faction var0, @Nullable() JollyRoger var1, @Nullable() LivingEntity var2, PoseStack var3, MultiBufferSource var4, int var5) {
		if ((var0.equals(ModFactions.PIRATE.get())) && ((var1) != (null))){
			RenderSystem.enableDepthTest();
			var1.render(var3,0,0,0.0035F);
			RenderSystem.disableDepthTest();
		}else{
			if (var0.equals(ModFactions.REVOLUTIONARY_ARMY.get())){
				var3.translate(-0.5D,-0.5D,0D);
				var3.scale(4F,4F,1F);
				VertexConsumer var6 = var4.getBuffer(ModRenderTypes.getWantedPoster(ModResources.REVOLUTIONARY_ARMY_ICON_GREYSCALE));
				drawQuad(var3,var6,0F,0F,0F,1F,0F,0F,0.5F,0.5F,0F,1F,0F,1F,var5);
			}else{
				if (var0.equals(ModFactions.MARINE.get())){
					var3.translate(-0.5D,-0.5D,0D);
					var3.scale(4F,4F,1F);
					VertexConsumer var6_1 = var4.getBuffer(ModRenderTypes.getWantedPoster(ModResources.MARINE_ICON));
					drawQuad(var3,var6_1,1F,1F,1F,1F,0F,0F,0.5F,0.5F,0F,1F,0F,1F,var5);
				}else{
					if (var0.equals(ModFactions.WORLD_GOVERNMENT.get())){
						var3.translate(-0.5D,-0.5D,0D);
						var3.scale(4F,4F,1F);
						VertexConsumer var6_2 = var4.getBuffer(ModRenderTypes.getWantedPoster(ModResources.WORLD_GOV_ICON));
						drawQuad(var3,var6_2,((float)(WORLD_GOVT_COLOR.getRed())) / (255F),((float)(WORLD_GOVT_COLOR.getGreen())) / (255F),((float)(WORLD_GOVT_COLOR.getBlue())) / (255F),1F,0F,0F,0.5F,0.5F,0F,1F,0F,1F,var5);
					}else{
						return;
					}
				}
			}
		}
	}
	public static List<ModelPart> getArmPartsFrom(EntityModel<?> var0) {
		return (List<ModelPart>) (List) (getArmPartsFrom((EntityModel)(var0),(HumanoidArm)(HumanoidArm.RIGHT)));
	}
	public static List<ModelPart> getArmPartsFrom(EntityModel<?> var0, HumanoidArm var1) {
		ArrayList var2 = new ArrayList();
		if (var0 instanceof HumanoidModel){
			HumanoidModel var3 = ((HumanoidModel)(var0));
			var2.add(((var1) == (HumanoidArm.RIGHT)) ? (var3.rightArm) : (var3.leftArm));
			if (var3 instanceof PlayerModel){
				PlayerModel var4 = ((PlayerModel)(var3));
				var2.add(((var1) == (HumanoidArm.RIGHT)) ? (var4.rightSleeve) : (var4.leftSleeve));
			}
		}else{
			if (var0 instanceof DugongModel){
				DugongModel var3_1 = ((DugongModel)(var0));
				var2.add(((var1) == (HumanoidArm.RIGHT)) ? (var3_1.rightArm) : (var3_1.leftArm));
			}
		}
		return var2;
	}
	public static List<ModelPart> getLegPartsFrom(EntityModel<?> var0) {
		return (List<ModelPart>) (List) (getLegPartsFrom((EntityModel)(var0),(HumanoidArm)(HumanoidArm.RIGHT)));
	}
	public static List<ModelPart> getLegPartsFrom(EntityModel<?> var0, HumanoidArm var1) {
		ArrayList var2 = new ArrayList();
		if (var0 instanceof HumanoidModel){
			HumanoidModel var3 = ((HumanoidModel)(var0));
			var2.add(((var1) == (HumanoidArm.RIGHT)) ? (var3.rightLeg) : (var3.leftLeg));
			if (var3 instanceof PlayerModel){
				PlayerModel var4 = ((PlayerModel)(var3));
				var2.add(((var1) == (HumanoidArm.RIGHT)) ? (var4.rightPants) : (var4.leftPants));
			}
		}else{
			if (var0 instanceof DugongModel){
				var2.add(((DugongModel)(var0)).tailBase);
			}
		}
		return var2;
	}
	public static <A extends HumanoidModel<E>, E extends LivingEntity> void setPartVisibility(A var0, EquipmentSlot var1) {
		var0.setAllVisible(false);
		switch (RendererHelper$1.$SwitchMap$net$minecraft$world$entity$EquipmentSlot[var1.ordinal()]){
		case 1:
			var0.head.visible = true;
			var0.hat.visible = true;
			break;
		case 2:
			var0.body.visible = true;
			var0.rightArm.visible = true;
			var0.leftArm.visible = true;
			break;
		case 3:
			var0.body.visible = true;
			var0.rightLeg.visible = true;
			var0.leftLeg.visible = true;
			break;
		case 4:
			var0.rightLeg.visible = true;
			var0.leftLeg.visible = true;
			break;
		}
	}
	public static <M extends EntityModel<?>> List<ModelPart> getModelParts(M var0) {
		ArrayList var1 = new ArrayList();
		if (var0 instanceof HierarchicalModel){
			HierarchicalModel var2 = ((HierarchicalModel)(var0));
			Objects.requireNonNull(var1);
			var2.root().getAllParts().forEach((Consumer)(((Consumer<ModelPart>)(var1::add))));
		}else{
			if (var0 instanceof AgeableListModel){
				AgeableListModel var2_1 = ((AgeableListModel)(var0));
				Objects.requireNonNull(var1);
				((IAgeableListModelExtension)(var2_1)).getParts().forEach((Consumer)(((Consumer<ModelPart>)(var1::add))));
			}
		}
		return var1;
	}
	public static <M extends EntityModel<? extends Entity>> void resetModelToDefaultPivots(M var0) {
		getModelParts(var0).forEach((Consumer)(((Consumer<ModelPart>)(ModelPart::resetPose))));
	}
	public static void drawPlayerFace(Player var0, GuiGraphics var1, int var2, int var3, int var4, int var5) {
		PlayerInfo var6 = Minecraft.getInstance().player.connection.getPlayerInfo(var0.getUUID());
	}
	public static void drawLivingBust(LivingEntity var0, GuiGraphics var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
		Minecraft var8 = Minecraft.getInstance();
		double var9 = var8.getWindow().getGuiScale();
		double var10 = (double)((var2) - (var4));
		double var11 = (double)(var2);
		double var12 = ((double)(var3)) - (((double)(var4)) * (1.1D));
		double var13 = (double)(var3);
		float var14 = (float)(Math.atan((double)(((float)(var5)) / (40F))));
		float var15 = (float)(Math.atan((double)(((float)(var6)) / (40F))));
		Quaternionf var16 = new Quaternionf().rotateZ(3.1415927F);
		Quaternionf var17 = new Quaternionf().rotateX(((var15) * (20F)) * (0.017453292F));
		var16.mul((Quaternionfc)(var17));
		float var18 = var0.yBodyRot;
		float var19 = var0.getYRot();
		float var20 = var0.getXRot();
		float var21 = var0.yHeadRotO;
		float var22 = var0.yHeadRot;
		var0.yBodyRot = (180F) + ((var14) * (20F));
		var0.setYRot((180F) + ((var14) * (40F)));
		var0.setXRot((-(var15)) * (20F));
		var0.yHeadRot = var0.getYRot();
		var0.yHeadRotO = var0.getYRot();
		var1.pose().pushPose();
		var1.pose().translate((double)(var2),(double)(var3),50D);
		var1.pose().mulPose(new Matrix4f().scaling((float)(var4),(float)(var4),(float)(-(var4))));
		var1.pose().mulPose(var16);
		Lighting.setupForEntityInInventory();
		EntityRenderDispatcher var23 = Minecraft.getInstance().getEntityRenderDispatcher();
		if ((var17) != (null)){
			var17.conjugate();
			var23.overrideCameraOrientation(var17);
		}
		var23.setRenderShadow(false);
		GL11.glEnable(3089);
		GL11.glScissor((int)((var10) * (var9)),(int)(((double)(var8.getWindow().getHeight())) - ((var12) * (var9))),(int)((var11) * (var9)),(int)((var13) * (var9)));
		float var24 = var0.getDimensions(Pose.STANDING).height();
		float var25 = 0.0F;
		if ((var24) > (1.6F)){
			var25 = 0.05F;
		}else{
			var24 = (var24) - (1.8F);
			var25 = (var24) * (-1F);
		}
		var23.render((Entity)(var0),0D,(double)(var25),0D,0F,1F,var1.pose(),(MultiBufferSource)(var1.bufferSource()),(var7) ? (0) : (15728880));
		var1.bufferSource().getBuffer(RenderType.lines());
		GL11.glDisable(3089);
		var1.flush();
		var23.setRenderShadow(true);
		var1.pose().popPose();
		Lighting.setupFor3DItems();
		var0.yBodyRot = var18;
		var0.setYRot(var19);
		var0.setXRot(var20);
		var0.yHeadRotO = var21;
		var0.yHeadRot = var22;
	}
	public static void renderAbilityProtectionArea(PoseStack var0, Camera var1, BufferBuilder var2, double var3, double var4, double var5, float var6, float var7, float var8, boolean var9) {
		float var10 = (var9) ? (1F) : (0F);
		float var11 = (var9) ? (0.2F) : (1F);
		renderAreaBorder(var0,var1,var2,var3,var4,var5,var6,var7,var8,var10,var11,(var9) ? (0.4F) : (1F),1F,10000F,10000F,FORCEFIELD_LOCATION);
	}
	public static void renderAreaBorder(PoseStack var0, Camera var1, BufferBuilder var2, double var3, double var4, double var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14, ResourceLocation var15) {
		float var16 = (((float)(Util.getMillis())) % (var13)) / (var13);
		float var17 = (((float)(Util.getMillis())) % (var14)) / (var14);
		float var18 = (var16) - (0F);
		float var19 = (var17) - (1F);
		float var20 = (float)(var1.getPosition().x);
		float var21 = (float)(var1.getPosition().y);
		float var22 = (float)(var1.getPosition().z);
		int var23 = (((var7) != (0F)) && ((var7) < (256F))) ? (1) : (0);
		float var24 = (0F) - (var21);
		float var25 = (256F) - (var21);
		if ((var23) != (0)){
			var24 = (float)(((double)((-(var7)) - (var21))) + (var4));
			var25 = (float)(((double)((var7) - (var21))) + (var4));
		}
		float var26 = (float)(((double)((-(var6)) - (var20))) + (var3));
		float var27 = (float)(((double)(((var6) + (1F)) - (var20))) + (var3));
		float var28 = (float)(((double)((-(var8)) - (var22))) + (var5));
		float var29 = (float)(((double)(((var8) + (1F)) - (var22))) + (var5));
		RenderSystem.setShader((Supplier)(((Supplier<ShaderInstance>)(GameRenderer::getPositionTexColorShader))));
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE,GlStateManager.SourceFactor.ONE,GlStateManager.DestFactor.ZERO);
		RenderSystem.setShaderTexture(0,var15);
		RenderSystem.depthMask(Minecraft.useShaderTransparency());
		var0.pushPose();
		RenderSystem.polygonOffset(-3F,-3F);
		RenderSystem.enablePolygonOffset();
		RenderSystem.disableCull();
		var2.addVertex(var26,var24,var28).setUv(var18,var19).setColor(var9,var10,var11,var12);
		var2.addVertex(var26,var25,var28).setUv(var19,var19).setColor(var9,var10,var11,var12);
		var2.addVertex(var27,var25,var28).setUv(var19,var18).setColor(var9,var10,var11,var12);
		var2.addVertex(var27,var24,var28).setUv(var18,var18).setColor(var9,var10,var11,var12);
		var2.addVertex(var26,var24,var29).setUv(var18,var19).setColor(var9,var10,var11,var12);
		var2.addVertex(var26,var25,var29).setUv(var19,var19).setColor(var9,var10,var11,var12);
		var2.addVertex(var27,var25,var29).setUv(var19,var18).setColor(var9,var10,var11,var12);
		var2.addVertex(var27,var24,var29).setUv(var18,var18).setColor(var9,var10,var11,var12);
		var2.addVertex(var26,var24,var28).setUv(var18,var19).setColor(var9,var10,var11,var12);
		var2.addVertex(var26,var25,var28).setUv(var19,var19).setColor(var9,var10,var11,var12);
		var2.addVertex(var26,var25,var29).setUv(var19,var18).setColor(var9,var10,var11,var12);
		var2.addVertex(var26,var24,var29).setUv(var18,var18).setColor(var9,var10,var11,var12);
		var2.addVertex(var27,var24,var28).setUv(var18,var19).setColor(var9,var10,var11,var12);
		var2.addVertex(var27,var25,var28).setUv(var19,var19).setColor(var9,var10,var11,var12);
		var2.addVertex(var27,var25,var29).setUv(var19,var18).setColor(var9,var10,var11,var12);
		var2.addVertex(var27,var24,var29).setUv(var18,var18).setColor(var9,var10,var11,var12);
		if ((var23) != (0)){
			var2.addVertex(var27,var24,var28).setUv(var18,var19).setColor(var9,var10,var11,var12);
			var2.addVertex(var26,var24,var28).setUv(var19,var19).setColor(var9,var10,var11,var12);
			var2.addVertex(var26,var24,var29).setUv(var19,var18).setColor(var9,var10,var11,var12);
			var2.addVertex(var27,var24,var29).setUv(var18,var18).setColor(var9,var10,var11,var12);
			var2.addVertex(var27,var25,var28).setUv(var18,var19).setColor(var9,var10,var11,var12);
			var2.addVertex(var26,var25,var28).setUv(var19,var19).setColor(var9,var10,var11,var12);
			var2.addVertex(var26,var25,var29).setUv(var19,var18).setColor(var9,var10,var11,var12);
			var2.addVertex(var27,var25,var29).setUv(var18,var18).setColor(var9,var10,var11,var12);
		}
		BufferUploader.drawWithShader(var2.build());
		RenderSystem.enableCull();
		RenderSystem.polygonOffset(0F,0F);
		RenderSystem.disablePolygonOffset();
		RenderSystem.disableBlend();
		var0.popPose();
		RenderSystem.depthMask(true);
	}
	public static void drawStringWithBorder(Font var0, GuiGraphics var1, String var2, int var3, int var4, int var5) {
		drawStringWithBorder(var0,var1,(Component)(Component.literal(var2)),var3,var4,var5);
	}
	public static void drawStringWithBorder(Font var0, GuiGraphics var1, Component var2, int var3, int var4, int var5) {
		drawStringWithBorder(var0,var1,var2.getVisualOrderText(),var3,var4,var5);
	}
	public static void drawStringWithBorder(Font var0, GuiGraphics var1, FormattedCharSequence var2, int var3, int var4, int var5) {
		drawStringWithBorder(var0,var1,var2,var3,var4,var5,0);
		var1.flush();
	}
	public static void drawStringWithBorder(Font var0, GuiGraphics var1, FormattedCharSequence var2, int var3, int var4, int var5, int var6) {
		drawStringWithBorder(var0,var1.pose(),(MultiBufferSource)(var1.bufferSource()),var2,var3,var4,var5,var6);
		var1.flush();
	}
	public static void drawStringWithBorder(Font var0, PoseStack var1, MultiBufferSource var2, FormattedCharSequence var3, int var4, int var5, int var6, int var7) {
		if ((var3) == (null)){
			return;
		}else{
			var0.drawInBatch8xOutline(var3,(float)(var4),(float)(var5),var6,var7,var1.last().pose(),var2,255);
			return;
		}
	}
	@Deprecated()
	public static void drawTexturedModalRect(PoseStack var0, ResourceLocation var1, float var2, float var3, float var4) {
		drawTexturedModalRect(var0,var1,var2,var3,var4,0F,0F,256F,256F,1F,1F,1F,1F,1F,1F);
	}
	@Deprecated()
	public static void drawTexturedModalRect(PoseStack var0, ResourceLocation var1, float var2, float var3, float var4, float var5, float var6) {
		drawTexturedModalRect(var0,var1,var2,var3,var4,0F,0F,var5,var6,1F,1F,1F,1F,1F,1F);
	}
	@Deprecated()
	public static void drawTexturedModalRect(PoseStack var0, ResourceLocation var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
		drawTexturedModalRect(var0,var1,var2,var3,var4,var5,var6,var7,var8,1F,1F,1F,1F,1F,1F);
	}
	@Deprecated()
	public static void drawTexturedModalRect(PoseStack var0, ResourceLocation var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12) {
		drawTexturedModalRect(var0,var1,var2,var3,var4,var5,var6,var7,var8,1F,1F,var9,var10,var11,var12);
	}
	@Deprecated()
	public static void drawTexturedModalRect(PoseStack var0, ResourceLocation var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14) {
		float var17 = (var7) * (var9);
		float var18 = (var8) * (var10);
		Tesselator var19 = Tesselator.getInstance();
		RenderSystem.setShaderTexture(0,var1);
		RenderSystem.setShader((Supplier)(((Supplier<ShaderInstance>)(GameRenderer::getPositionTexColorShader))));
		BufferBuilder var20 = var19.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_TEX_COLOR);
		Matrix4f var21 = var0.last().pose();
		var20.addVertex(var21,var2,(var3) + (var18),var4).setUv((var5) * (0.00390625F),((var6) + (var8)) * (0.00390625F)).setColor(var11,var12,var13,var14);
		var20.addVertex(var21,(var2) + (var17),(var3) + (var18),var4).setUv(((var5) + (var7)) * (0.00390625F),((var6) + (var8)) * (0.00390625F)).setColor(var11,var12,var13,var14);
		var20.addVertex(var21,(var2) + (var17),var3,var4).setUv(((var5) + (var7)) * (0.00390625F),(var6) * (0.00390625F)).setColor(var11,var12,var13,var14);
		var20.addVertex(var21,var2,var3,var4).setUv((var5) * (0.00390625F),(var6) * (0.00390625F)).setColor(var11,var12,var13,var14);
		BufferUploader.drawWithShader(var20.build());
	}
	public static void drawColourOnScreen(int var0, int var1, float var2, float var3, float var4, float var5, float var6) {
		int var7 = ((var0) >> (16)) & (255);
		int var8 = ((var0) >> (8)) & (255);
		drawColourOnScreen(var7,var8,(var0) & (255),var1,var2,var3,var4,var5,var6);
	}
	public static void drawColourOnScreen(int var0, int var1, int var2, int var3, float var4, float var5, float var6, float var7, float var8) {
		if ((!((var6) <= (0F))) && (!((var7) <= (0F)))){
			RenderSystem.setProjectionMatrix(new Matrix4f().setOrtho(0F,var6,var7,0F,1000F,3000F),VertexSorting.ORTHOGRAPHIC_Z);
			Matrix4fStack var9 = RenderSystem.getModelViewStack();
			var9.pushMatrix();
			var9.identity();
			var9.translate(0F,0F,-2000F);
			RenderSystem.applyModelViewMatrix();
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.setShader((Supplier)(((Supplier<ShaderInstance>)(GameRenderer::getPositionColorShader))));
			BufferBuilder var10 = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_COLOR);
			var10.addVertex(var4,(var5) + (var7),var8).setColor(var0,var1,var2,var3);
			var10.addVertex((var4) + (var6),(var5) + (var7),var8).setColor(var0,var1,var2,var3);
			var10.addVertex((var4) + (var6),var5,var8).setColor(var0,var1,var2,var3);
			var10.addVertex(var4,var5,var8).setColor(var0,var1,var2,var3);
			BufferUploader.drawWithShader(var10.build());
			RenderSystem.disableBlend();
			var9.popMatrix();
			RenderSystem.applyModelViewMatrix();
			return;
		}else{
			return;
		}
	}
	public static void drawIcon(ResourceLocation var0, PoseStack var1, float var2, float var3, float var4, float var5, float var6) {
		drawIcon(var0,var1,var2,var3,var4,var5,var6,1F,1F,1F,1F,false);
	}
	public static void drawIcon(ResourceLocation var0, PoseStack var1, float var2, float var3, float var4, float var5, float var6, int var7) {
		float var8 = ((float)(((var7) >> (16)) & (255))) / (255F);
		float var9 = ((float)(((var7) >> (8)) & (255))) / (255F);
		drawIcon(var0,var1,var2,var3,var4,var5,var6,var8,var9,((float)((var7) & (255))) / (255F),1F,false);
	}
	public static void drawIcon(ResourceLocation var0, PoseStack var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10) {
		drawIcon(var0,var1,var2,var3,var4,var5,var6,var7,var8,var9,var10,false);
	}
	public static void drawIcon(ResourceLocation var0, PoseStack var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, boolean var11) {
		if ((var0) == (null)){
			LogManager.getLogger(RendererHelper.class).warn("Trying to render a null icon!");
			return;
		}else{
			Matrix4f var12 = var1.last().pose();
			float var13 = (var11) ? (-1F) : (1F);
			RenderSystem.enableBlend();
			RenderSystem.setShaderTexture(0,var0);
			RenderSystem.setShader((Supplier)(((Supplier<ShaderInstance>)(GameRenderer::getPositionTexColorShader))));
			BufferBuilder var14 = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_TEX_COLOR);
			var14.addVertex(var12,var2,(var3) + (var6),var4).setUv(0F,var13).setColor(var7,var8,var9,var10);
			var14.addVertex(var12,(var2) + (var5),(var3) + (var6),var4).setUv(var13,var13).setColor(var7,var8,var9,var10);
			var14.addVertex(var12,(var2) + (var5),var3,var4).setUv(var13,0F).setColor(var7,var8,var9,var10);
			var14.addVertex(var12,var2,var3,var4).setUv(0F,0F).setColor(var7,var8,var9,var10);
			BufferUploader.drawWithShader(var14.build());
			RenderSystem.disableBlend();
			return;
		}
	}
	public static void drawAbilityTooltip(IAbility var0, GuiGraphics var1, Collection<Component> var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, Font var12, ClientTooltipPositioner var13) {
		ItemStack var14 = ItemStack.EMPTY;
		if (!(var2.isEmpty())){
			ArrayList var15 = new ArrayList();
			Iterator<Component> var16 = var2.iterator();
			do{
				if (var16.hasNext()){
					Component var17 = ((Component)(var16.next()));
					if ((var17) == (null)){
						continue;
					}else{
						Objects.requireNonNull(var15);
						var12.split((FormattedText)(var17),var7).stream().map((Function)(((Function<FormattedCharSequence, ClientTooltipComponent>)(ClientTooltipComponent::create)))).forEach((Consumer)(((Consumer<ClientTooltipComponent>)(var15::add))));
						continue;
					}
				}else{
					break;
				}
			} while (true);
			int var17_1 = 0;
			int var18 = ((var2.size()) == (1)) ? (-2) : (0);
			Iterator var19 = var15.iterator();
			do{
				if (var19.hasNext()){
					ClientTooltipComponent var20 = ((ClientTooltipComponent)(var19.next()));
					int var21_1 = var20.getWidth(var12);
					if ((var21_1) > (var17_1)){
						var17_1 = var21_1;
					}
					var18 = (var18) + (var20.getHeight());
					continue;
				}else{
					break;
				}
			} while (true);
			Vector2ic var20_1 = var13.positionTooltip(var1.guiWidth(),var1.guiHeight(),var3,var4,var17_1,var18);
			int var21 = var20_1.x();
			int var21_1 = var20_1.y();
			var1.pose().pushPose();
			int var23 = 400;
			int var24 = var17_1;
			int var25 = var18;
			GuiGraphics var26 = var1;
			int var27 = var21;
			int var28 = var21_1;
			int var29 = var24;
			int var30 = var25;
			int var31 = var23;
			int var32 = var8;
			int var33 = var9;
			int var34 = var10;
			int var35 = var11;
			final GuiGraphics var26_f1 = var26;
			final int var27_f2 = var27;
			final int var28_f3 = var28;
			final int var29_f4 = var29;
			final int var30_f5 = var30;
			final int var31_f6 = var31;
			final int var32_f7 = var32;
			final int var33_f8 = var33;
			final int var34_f9 = var34;
			final int var35_f10 = var35;
			Runnable var36 = () -> {
			TooltipRenderUtil.renderTooltipBackground(var26_f1,var27_f2,var28_f3,var29_f4,var30_f5,var31_f6,var32_f7,var33_f8,var34_f9,var35_f10);
		};
			var1.drawManaged(var36);
			var1.pose().translate(0F,0F,(float)(var23));
			int var37 = var21_1;
			MultiBufferSource.BufferSource var38 = Minecraft.getInstance().renderBuffers().bufferSource();
			Matrix4f var39 = var1.pose().last().pose();
			int var40 = 0;
			do{
				if ((var40) < (((java.util.List)(var15)).size())){
					ClientTooltipComponent var41 = ((ClientTooltipComponent)(var15.get(var40)));
					var41.renderText(var12,var21,var37,var39,var38);
					var37 = (var37) + ((var41.getHeight()) + (((var40) == (0)) ? (2) : (0)));
					var40++;
					continue;
				}else{
					break;
				}
			} while (true);
			int var41_1 = var12.width(((Component)(var2.stream().findFirst().get())).getString());
			HashSet var42 = new HashSet();
			HashSet var43 = new HashSet();
			if (((var0.getCore().getSourceHakiNature()) != (null)) && ((var0.getCore().getSourceHakiNature().getTexture()) != (null))){
				var42.add(var0.getCore().getSourceHakiNature().getTexture());
			}
			if ((var0.getCore().getSourceTypes()) != (null)){
				Iterator var44 = var0.getCore().getSourceTypes().iterator();
				do{
					if (var44.hasNext()){
						SourceType var45 = ((SourceType)(var44.next()));
						if ((var45.getTexture()) != (null)){
							var42.add(var45.getTexture());
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				if (((java.util.Set)(var42)).contains((Object)(SourceType.FIST.getTexture()))){
					((java.util.Set)(var42)).remove((Object)(SourceType.PHYSICAL.getTexture()));
				}
			}
			if (((var0.getCore().getSourceElement()) != (null)) && ((var0.getCore().getSourceElement().getTexture()) != (null))){
				var43.add(var0.getCore().getSourceElement().getTexture());
			}
			int var44_1 = 4;
			Iterator var45_1 = var42.iterator();
			do{
				if (var45_1.hasNext()){
					ResourceLocation var46 = ((ResourceLocation)(var45_1.next()));
					var44_1 = (var44_1) + (12);
					drawIcon(var46,var1.pose(),(float)((((var21) + (var41_1)) + (var44_1)) - (12)),(float)(var21_1),500F,10F,10F,((float)(TOOLTIP_ICON_COLOR.getRed())) / (255F),((float)(TOOLTIP_ICON_COLOR.getGreen())) / (255F),((float)(TOOLTIP_ICON_COLOR.getBlue())) / (255F),1F);
					continue;
				}else{
					break;
				}
			} while (true);
			Iterator var46_1 = var43.iterator();
			do{
				if (var46_1.hasNext()){
					ResourceLocation var47 = ((ResourceLocation)(var46_1.next()));
					var44_1 = (var44_1) + (12);
					drawIcon(var47,var1.pose(),(float)((((var21) + (var41_1)) + (var44_1)) - (12)),(float)(var21_1),500F,10F,10F,1F,1F,1F,1F);
					continue;
				}else{
					break;
				}
			} while (true);
			var1.pose().popPose();
		}
	}
	public static void drawQuad(PoseStack var0, VertexConsumer var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, int var14) {
		PoseStack.Pose var15 = var0.last();
		Matrix4f var16 = var15.pose();
		Matrix3f var17 = var15.normal();
		drawVertex(var16,var17,var1,var2,var3,var4,var5,var6,(var7) + (var9),var10,var13,var14);
		drawVertex(var16,var17,var1,var2,var3,var4,var5,(var6) + (var8),(var7) + (var9),var11,var13,var14);
		drawVertex(var16,var17,var1,var2,var3,var4,var5,(var6) + (var8),var7,var11,var12,var14);
		drawVertex(var16,var17,var1,var2,var3,var4,var5,var6,var7,var10,var12,var14);
	}
	public static void drawVertex(Matrix4f var0, Matrix3f var1, VertexConsumer var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, int var11) {
		var2.addVertex(var0,var7,var8,0F).setColor(var3,var4,var5,var6).setUv(var9,var10).setOverlay(OverlayTexture.NO_OVERLAY).setLight(var11).setNormal(0F,1F,0F);
	}
}