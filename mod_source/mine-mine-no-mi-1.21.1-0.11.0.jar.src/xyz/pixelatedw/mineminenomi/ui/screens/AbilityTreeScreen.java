package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.platform.InputConstants;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nTutorials;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.node.CUnlockAbilityNodePacket;
import xyz.pixelatedw.mineminenomi.ui.widgets.AnimatedTextButton;

public class AbilityTreeScreen extends Screen {
	// Fields
	private static final int NODE_SIZE = 16;
	private static final int MARGIN_SIZE = 2;
	private static final int GRID_STEP = 20;
	private static final float LINE_THICKNESS = 15F;
	private float zoom;
	private LocalPlayer player;
	private AbilityData abilityProps;
	private EntityStatsData entityProps;
	private boolean editMode;
	private AbilityNode draggingNode;
	private AbilityNode hoveredNode;
	private AbilityNode clickedNode;
	private Map<AbilityNode, AbilityNode$NodePos> editedPositions;
	private float dragNodeOffsetX;
	private float dragNodeOffsetY;
	private float dragOffsetX;
	private float dragOffsetY;
	private float dragOriginX;
	private float dragOriginY;
	private float dragStartX;
	private float dragStartY;
	private boolean isDragging;
	private int dragTicks;
	private int iconRadius;
	private int marginScaled;
	private float gridStepScaled;
	private int centerX;
	private int centerY;
	public List<AbilityNode> unlockableNodes;
	private int tpLimit;

	public AbilityTreeScreen(int var1, List<AbilityNode> var2) {
		super((Component)(Component.empty()));
		this.zoom = 1F;
		this.editMode = false;
		this.editedPositions = new HashMap();
		this.dragNodeOffsetX = 0F;
		this.dragNodeOffsetY = 0F;
		this.dragOffsetX = 0F;
		this.dragOffsetY = 0F;
		this.dragOriginX = this.dragOffsetX;
		this.dragOriginY = this.dragOffsetY;
		this.dragStartX = -1F;
		this.dragStartY = -1F;
		this.isDragging = true;
		this.dragTicks = 0;
		this.unlockableNodes = new ArrayList();
		this.tpLimit = var1;
		this.unlockableNodes = var2;
	}
	public void init() {
		Minecraft var1 = super.getMinecraft();
		this.player = var1.player;
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(this.player)))).orElse((Object)(null))));
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(this.player)))).orElse((Object)(null))));
		if (((var2) != (null)) && ((var3) != (null))){

		}else{
			super.onClose();
		}
		this.abilityProps = var2;
		this.entityProps = var3;
		if (WyDebug.isDebug()){
			String var4 = (this.editMode) ? ("On") : ("Off");
			String var5 = "Edit Mode: " + var4;
			AbilityTreeScreen var6 = this;
			Button.OnPress var7 = (l0) -> {
			this.editMode = !(this.editMode);
			String lv1_2 = (this.editMode) ? ("On") : ("Off");
			String lv1_3 = "Edit Mode: " + lv1_2;
			l0.setMessage((Component)(Component.literal(lv1_3)));
		};
			this.addRenderableWidget((GuiEventListener)(Button.builder((Component)(Component.literal(var5)),var7).pos(8,8).size(100,20).build()));
			Iterator var8 = this.abilityProps.getNodes().iterator();
			do{
				if (var8.hasNext()){
					AbilityNode var9 = ((AbilityNode)(var8.next()));
					this.editedPositions.put(var9,var9.getPosition());
					continue;
				}else{
					break;
				}
			} while (true);
		}
		int var4_1 = this.width;
		Button.OnPress var5_1 = (l0) -> {
		};
		AnimatedTextButton var6_1 = new AnimatedTextButton((var4_1) - (20),(this.height) - (20),20,20,ModI18n.GUI_HELP_SHORT,var5_1);
		var6_1.setTooltip(this.getHelpTooltips());
		if (ClientConfig.getHelpButtonShown()){
			this.addRenderableWidget((GuiEventListener)(var6_1));
		}
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		super.render(var1,var2,var3,var4);
		if ((WyDebug.isDebug()) && (!(this.editMode))){
			this.setFocused((GuiEventListener)(null));
		}
		LocalPlayer var5 = super.getMinecraft().player;
		this.iconRadius = Math.round(((16F) * (this.zoom)) / (2F));
		this.marginScaled = Math.round((2F) * (this.zoom));
		this.gridStepScaled = (20F) * (this.zoom);
		this.hoveredNode = null;
		Iterator var6 = this.abilityProps.getNodes().iterator();
		LOOP_1:
		do{
			if (var6.hasNext()){
				AbilityNode var7 = ((AbilityNode)(var6.next()));
				var1.pose().pushPose();
				AbilityNode$NodePos var8 = this.getNodePosition(var7);
				this.centerX = this.getCenteredDragX(var8);
				this.centerY = this.getCenteredDragY(var8);
				int var9 = -7829368;
				int var10 = 0;
				if (var7.isUnlocked((LivingEntity)(var5))){
					var9 = -15486189;
					var10 = -15826930;
				}else{
					if (this.unlockableNodes.contains(var7)){
						var9 = -23296;
						var10 = -3374080;
					}else{
						var9 = -4713452;
						var10 = -8057073;
					}
				}
				int var11 = (((this.centerX) - (this.iconRadius)) - (this.marginScaled)) - (1);
				int var12 = (((this.centerY) - (this.iconRadius)) - (this.marginScaled)) - (1);
				int var13 = (((this.centerX) + (this.iconRadius)) + (this.marginScaled)) - (1);
				int var14 = (((this.centerY) + (this.iconRadius)) + (this.marginScaled)) - (1);
				var1.fill(var11,var12,var13,var14,10,var10);
				var1.fill((var11) + (2),(var12) + (2),(var13) - (2),(var14) - (2),15,var9);
				float var15 = (((float)(this.centerX)) - (1F)) - ((float)(this.iconRadius));
				float var16 = (((float)(this.centerY)) - (1F)) - ((float)(this.iconRadius));
				int var17 = (this.iconRadius) * (2);
				RendererHelper.drawIcon(var7.getIcon(),var1.pose(),var15,var16,15F,(float)(var17),(float)(var17));
				if ((!(this.isDragging)) && (((var2) >= ((this.centerX) - (this.iconRadius))) && (((var2) <= ((this.centerX) + (this.iconRadius))) && (((var3) >= ((this.centerY) - (this.iconRadius))) && ((var3) <= ((this.centerY) + (this.iconRadius))))))){
					this.hoveredNode = var7;
					if ((this.draggingNode) == (null)){
						this.drawNodeTooltip(var1,this.hoveredNode);
					}
					int var18 = ((this.centerX) - (this.iconRadius)) - (1);
					int var19 = ((this.centerY) - (this.iconRadius)) - (1);
					int var20 = ((this.centerX) + (this.iconRadius)) - (1);
					var1.fill(var18,var19,var20,((this.centerY) + (this.iconRadius)) - (1),20,1090519039);
					if (this.editMode){
						AbilityNode$NodePos var21 = ((AbilityNode$NodePos)(((java.util.Map)(this.editedPositions)).getOrDefault((Object)(var7),(Object)(var7.getPosition()))));
						float var22 = var21.x();
						float var23 = var21.y();
						String var24 = "Grid Position: (" + var22 + ", " + var23 + ")";
						String var25 = var24;
						int var26 = ((this.width) / (2)) - ((this.font.width(var25)) / (2));
						int var27 = 5;
						var1.drawString(this.font,var25,var26,var27,16777215,true);
					}
				}
				var1.pose().translate(0D,0D,-40D);
				Iterator var18_1 = var7.getPrerequisites().iterator();
				do{
					if (var18_1.hasNext()){
						AbilityNode var19_1 = ((AbilityNode)(var18_1.next()));
						AbilityNode$NodePos var20_1 = this.getNodePosition(var19_1);
						float var21_1 = (float)(this.getCenteredDragX(var20_1));
						this.drawLine(var1,var19_1,var7,var21_1,(float)(this.getCenteredDragY(var20_1)),(float)(this.centerX),(float)(this.centerY),-8355712,(15F) * (this.zoom));
						continue;
					}else{
						break;
					}
				} while (true);
				var1.pose().popPose();
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
		if (ServerConfig.isQuestProgressionEnabled()){
			this.drawTrainingPointsPanel(var1);
		}
	}
	public boolean mouseClicked(double var1, double var2, int var3) {
		this.dragStartX = (float)(var1);
		this.dragStartY = (float)(var2);
		this.dragOriginX = this.dragOffsetX;
		this.dragOriginY = this.dragOffsetY;
		if ((var3) == (0)){
			if ((this.hoveredNode) != (null)){
				this.clickedNode = this.hoveredNode;
			}
			if (this.editMode){
				Iterator var4 = this.editedPositions.entrySet().iterator();
				do{
					if (var4.hasNext()){
						Map.Entry var5 = ((Map.Entry)(var4.next()));
						AbilityNode var6 = ((AbilityNode)(var5.getKey()));
						AbilityNode$NodePos var7 = ((AbilityNode$NodePos)(var5.getValue()));
						float var8 = (float)(this.getCenteredDragX(var7));
						float var9 = (float)(this.getCenteredDragY(var7));
						if (((var1) >= ((double)((var8) - ((float)(this.iconRadius))))) && (((var1) <= ((double)((var8) + ((float)(this.iconRadius))))) && (((var2) >= ((double)((var9) - ((float)(this.iconRadius))))) && ((var2) <= ((double)((var9) + ((float)(this.iconRadius)))))))){
							this.draggingNode = var6;
							this.dragNodeOffsetX = ((float)(var1)) - (var8);
							this.dragNodeOffsetY = ((float)(var2)) - (var9);
							return true;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}
			this.dragStartX = this.getScreenCenterX();
			this.dragStartY = this.getScreenCenterY();
		}
		return super.mouseClicked(var1,var2,var3);
	}
	public boolean mouseDragged(double var1, double var2, int var3, double var4, double var5) {
		if (((this.dragStartX) != (-1F)) && ((this.dragStartY) != (-1F))){
			if ((!(this.editMode)) && ((this.clickedNode) != (null))){
				return false;
			}else{
				this.isDragging = true;
				this.dragTicks = (this.dragTicks) + (1);
				if ((var3) == (0)){
					float var10 = 0.0F;
					float var11 = 0.0F;
					if ((this.editMode) && ((this.draggingNode) != (null))){
						float var6 = (((float)(var1)) - (this.dragNodeOffsetX)) - ((this.dragOffsetX) * (this.zoom));
						float var7 = (((float)(var2)) - (this.dragNodeOffsetY)) - ((this.dragOffsetY) * (this.zoom));
						float var8 = (((var6) * (2F)) - ((float)(this.width))) / ((2F) * (this.gridStepScaled));
						float var9 = (((var7) * (2F)) - ((float)(this.height))) / ((2F) * (this.gridStepScaled));
						var10 = (float)(Math.round(var8));
						var11 = (float)(Math.round(var9));
						this.editedPositions.put(this.draggingNode,new AbilityNode$NodePos(var10,var11));
						return true;
					}else{
						double var6_1 = (double)((this.minecraft.getWindow().getScreenWidth()) / (2));
						double var7_1 = (double)((this.minecraft.getWindow().getScreenHeight()) / (2));
						InputConstants.grabOrReleaseMouse(this.minecraft.getWindow().getWindow(),212995,var6_1,var7_1);
						if ((this.dragTicks) <= (1)){
							return false;
						}else{
							var10 = (float)((var1) - ((double)(this.dragStartX)));
							var11 = (float)((var2) - ((double)(this.dragStartY)));
							this.dragOffsetX = (this.dragOffsetX) + ((var10) / (this.zoom));
							this.dragOffsetY = (this.dragOffsetY) + ((var11) / (this.zoom));
							this.dragStartX = this.getScreenCenterX();
							this.dragStartY = this.getScreenCenterY();
						}
					}
				}
				return super.mouseDragged(var1,var2,var3,var4,var5);
			}
		}else{
			return super.mouseDragged(var1,var2,var3,var4,var5);
		}
	}
	public boolean mouseReleased(double var1, double var2, int var3) {
		if ((var3) == (0)){
			GLFW.glfwSetInputMode(this.minecraft.getWindow().getWindow(),208897,212993);
			if ((!(this.isDragging)) && (((this.clickedNode) != (null)) && ((this.hoveredNode) == (this.clickedNode)))){
				Pair var4 = this.abilityProps.getCoreIndexPair(this.clickedNode);
				if ((var4) != (null)){
					ModNetwork.sendToServer(new CUnlockAbilityNodePacket(((AbilityCore)(var4.getKey())),((Integer)(var4.getValue())).intValue()));
				}
			}
			this.clickedNode = null;
			this.draggingNode = null;
		}
		this.dragStartX = -1F;
		this.dragStartY = -1F;
		this.isDragging = false;
		this.dragTicks = 0;
		return super.mouseReleased(var1,var2,var3);
	}
	public boolean mouseScrolled(double var1, double var2, double var3, double var4) {
		this.zoom = Mth.clamp((this.zoom) + ((0.1F) * ((float)(var4))),0.2F,5F);
		return true;
	}
	public boolean isPauseScreen() {
		return true;
	}
	private static AbilityNode$NodePos clipToSquareEdge(float var0, float var1, float var2, float var3, float var4) {
		float var5 = (var2) - (var0);
		float var6 = (var3) - (var1);
		if (((var5) == (0F)) && ((var6) == (0F))){
			return new AbilityNode$NodePos(var0,var1);
		}else{
			float var7 = Math.abs(var5);
			float var8 = Math.abs(var6);
			float var9 = 0.0F;
			if ((var7) > (var8)){
				var9 = (var4) / (var7);
			}else{
				var9 = (var4) / (var8);
			}
			float var10 = (var0) + ((var5) * (var9));
			float var11 = (var1) + ((var6) * (var9));
			return new AbilityNode$NodePos(var10,var11);
		}
	}
	private void drawLine(GuiGraphics var1, AbilityNode var2, AbilityNode var3, float var4, float var5, float var6, float var7, int var8, float var9) {
		double var10 = (double)((var6) - (var4));
		double var11 = (double)((var7) - (var5));
		if ((Math.sqrt(((var10) * (var10)) + ((var11) * (var11)))) == (0D)){
			return;
		}else{
			RenderSystem.enableBlend();
			RenderSystem.setShader((Supplier)(((Supplier<ShaderInstance>)(GameRenderer::getRendertypeLinesShader))));
			BufferBuilder var12 = Tesselator.getInstance().begin(VertexFormat.Mode.LINE_STRIP,DefaultVertexFormat.POSITION_COLOR_NORMAL);
			Matrix4f var13 = var1.pose().last().pose();
			RenderSystem.lineWidth(var9);
			int var14 = ((var8) >> (24)) & (255);
			int var15 = ((var8) >> (16)) & (255);
			int var16 = ((var8) >> (8)) & (255);
			int var17 = (var8) & (255);
			int var18 = 0;
			if (var3.isUnlocked((LivingEntity)(this.player))){
				var15 = 0;
				var16 = 128;
				var17 = 0;
				var18++;
			}
			var12.addVertex(var13,var4,var5,0F).setColor(var15,var16,var17,var14).setNormal((float)((int)(var10)),(float)((int)(var11)),0F);
			var12.addVertex(var13,var6,var7,0F).setColor(var15,var16,var17,var14).setNormal((float)(-((int)(var10))),(float)(-((int)(var11))),0F);
			var12.addVertex(var13,var4,var5,0F).setColor(var15,var16,var17,var14).setNormal((float)((int)(var10)),(float)((int)(var11)),0F);
			BufferUploader.drawWithShader(var12.buildOrThrow());
			BufferBuilder var19 = Tesselator.getInstance().begin(VertexFormat.Mode.LINE_STRIP,DefaultVertexFormat.POSITION_COLOR_NORMAL);
			RenderSystem.lineWidth((var9) / (4F));
			if (var2.isUnlocked((LivingEntity)(this.player))){
				var15 = 0;
				var16 = 179;
				var17 = 0;
				var18++;
			}else{
				var15 = (int)(((float)(var15)) * (1.2F));
				var16 = (int)(((float)(var16)) * (1.2F));
				var17 = (int)(((float)(var17)) * (1.2F));
			}
			if ((var18) >= (2)){
				RenderSystem.lineWidth((var9) / (2F));
			}
			var19.addVertex(var13,var4,var5,0F).setColor(var15,var16,var17,var14).setNormal((float)((int)(var10)),(float)((int)(var11)),0F);
			var19.addVertex(var13,var6,var7,0F).setColor(var15,var16,var17,var14).setNormal((float)(-((int)(var10))),(float)(-((int)(var11))),0F);
			var19.addVertex(var13,var4,var5,0F).setColor(var15,var16,var17,var14).setNormal((float)((int)(var10)),(float)((int)(var11)),0F);
			BufferUploader.drawWithShader(var19.buildOrThrow());
			RenderSystem.disableBlend();
			RenderSystem.lineWidth(1F);
			return;
		}
	}
	private void drawNodeTooltip(GuiGraphics var1, AbilityNode var2) {
		int var3 = ((this.centerX) - (1)) - (this.iconRadius);
		int var4 = ((this.centerY) - (1)) - (this.iconRadius);
		int var5 = (this.iconRadius) * (2);
		List var6 = List.of(var2.getLocalizedName(),Component.literal(" "),var2.getTooltip());
		ArrayList var7 = new ArrayList();
		int var8 = 200;
		Iterator var9 = var6.iterator();
		do{
			if (var9.hasNext()){
				Component var10 = ((Component)(var9.next()));
				var7.addAll((Collection)(this.font.split((FormattedText)(var10),var8)));
				continue;
			}else{
				break;
			}
		} while (true);
		Font var10_1 = this.font;
		Objects.requireNonNull(var10_1);
		int var11 = var7.stream().mapToInt((ToIntFunction)(((ToIntFunction<FormattedCharSequence>)(var10_1::width)))).max().orElse(0);
		Objects.requireNonNull(this.font);
		int var12 = 9;
		int var13 = (((java.util.List)(var7)).size()) * (var12);
		int var14 = 8;
		int var15 = ((var3) + (var5)) + (var14);
		int var16 = (var4) + (2);
		int var17 = 4;
		int var18 = -267386864;
		int var19 = 1347420415;
		int var20 = 1344798847;
		int var21 = (var15) - (var17);
		int var22 = (var16) - (var17);
		int var23 = 50;
		int var24 = ((var15) + (var11)) + (var17);
		int var25 = ((var16) + (var13)) + (var17);
		if ((var24) > (this.width)){
			var15 = (((var3) - (var14)) - (var11)) - ((var17) * (2));
			var21 = (var15) - (var17);
			var24 = ((var15) + (var11)) + (var17);
		}
		if ((var25) > (this.height)){
			var16 = Math.max(2,((this.height) - (var13)) - (var17));
			var22 = (var16) - (var17);
			var25 = ((var16) + (var13)) + (var17);
		}
		var1.fillGradient(var21,var22,var24,var25,var23,var18,var18);
		var1.fillGradient(var21,var22,(var21) + (1),var25,var23,var19,var20);
		var1.fillGradient((var24) - (1),var22,var24,var25,var23,var19,var20);
		var1.fillGradient(var21,var22,var24,(var22) + (1),var23,var19,var19);
		var1.fillGradient(var21,(var25) - (1),var24,var25,var23,var20,var20);
		var1.pose().pushPose();
		var1.pose().translate(0F,0F,(float)(var23));
		int var26 = 0;
		do{
			if ((var26) < (((java.util.List)(var7)).size())){
				var1.drawString(this.font,((FormattedCharSequence)(var7.get(var26))),var15,(var16) + ((var26) * (var12)),-1,false);
				var26++;
				continue;
			}else{
				break;
			}
		} while (true);
		var1.pose().popPose();
	}
	private void drawTrainingPointsPanel(GuiGraphics var1) {
		if ((this.entityProps) == (null)){
			return;
		}else{
			int var2 = 6;
			int var3 = 2;
			Objects.requireNonNull(this.font);
			int var4 = (9) + (var3);
			String var5 = "Training Points";
			ArrayList var6 = new ArrayList();
			int var7 = this.font.width(var5);
			int var8 = this.entityProps.getSpentTrainingPoints();
			int var9 = this.tpLimit;
			String var10 = "" + var8 + " / " + var9;
			var6.add(Component.literal(var10).getString());
			TrainingPointType[] var11 = TrainingPointType.values();
			int var12 = var11.length;
			int var13 = 0;
			do{
				if ((var13) < (var12)){
					TrainingPointType var14 = var11[var13];
					switch (AbilityTreeScreen$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$TrainingPointType[var14.ordinal()]){
					case 1:

					case 2:

					case 3:

					case 4:

					default:
						Object var15 = null;
						if ((null) == (null)){

						}else{
							Object[] var16 = new Object[]{Integer.valueOf(this.entityProps.getTrainingPoints(var14))};
							String var17 = Component.translatable((String)(null),var16).getString();
							var6.add(var17);
							var7 = Math.max(var7,this.font.width(var17));
						}
						var13++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			int var14_1 = (var7) + ((var2) * (2));
			var12 = ((var2) * (2)) + ((var4) * ((((java.util.List)(var6)).size()) + (1)));
			var13 = 8;
			int var15_1 = 36;
			int var16_1 = -1342177280;
			int var17_1 = -1862270977;
			int var18 = -10496;
			int var19 = -1;
			var1.pose().translate(0F,0F,100F);
			var1.fill(var13,var15_1,(var13) + (var14_1),(var15_1) + (var12),var16_1);
			var1.fill(var13,var15_1,(var13) + (var14_1),(var15_1) + (1),var17_1);
			var1.fill(var13,((var15_1) + (var12)) - (1),(var13) + (var14_1),(var15_1) + (var12),var17_1);
			var1.fill(var13,var15_1,(var13) + (1),(var15_1) + (var12),var17_1);
			var1.fill(((var13) + (var14_1)) - (1),var15_1,(var13) + (var14_1),(var15_1) + (var12),var17_1);
			int var20 = (var13) + (var2);
			int var21 = (var15_1) + (var2);
			var1.drawString(this.font,var5,var20,var21,var18,false);
			var21 = (var21) + (var4);
			Iterator var22 = var6.iterator();
			do{
				if (var22.hasNext()){
					String var23 = ((String)(var22.next()));
					var1.drawString(this.font,var23,var20,var21,var19,false);
					var21 = (var21) + (var4);
					continue;
				}else{
					break;
				}
			} while (true);
			var1.pose().translate(0F,0F,-100F);
			return;
		}
	}
	private int getCenteredDragX(AbilityNode$NodePos var1) {
		return Math.round((((this.dragOffsetX) * (this.zoom)) + (((float)(this.width)) / (2F))) + ((this.gridStepScaled) * (var1.x())));
	}
	private int getCenteredDragY(AbilityNode$NodePos var1) {
		return Math.round((((this.dragOffsetY) * (this.zoom)) + (((float)(this.height)) / (2F))) + ((this.gridStepScaled) * (var1.y())));
	}
	private AbilityNode$NodePos getNodePosition(AbilityNode var1) {
		return (this.editMode) ? (((AbilityNode$NodePos)(((java.util.Map)(this.editedPositions)).getOrDefault((Object)(var1),(Object)(var1.getPosition()))))) : (var1.getPosition());
	}
	private final float getScreenCenterX() {
		return (float)(((Math.floor((double)(((float)(this.minecraft.getWindow().getScreenWidth())) / (2F)))) * ((double)(this.minecraft.getWindow().getGuiScaledWidth()))) / ((double)(this.minecraft.getWindow().getScreenWidth())));
	}
	private final float getScreenCenterY() {
		return (float)(((Math.floor((double)(((float)(this.minecraft.getWindow().getScreenHeight())) / (2F)))) * ((double)(this.minecraft.getWindow().getGuiScaledHeight()))) / ((double)(this.minecraft.getWindow().getScreenHeight())));
	}
	private Tooltip getHelpTooltips() {
		StringBuilder var1 = new StringBuilder();
		var1.append(ModI18nTutorials.ABILITY_TREE.getString());
		return Tooltip.create((Component)(Component.literal(var1.toString())));
	}
}