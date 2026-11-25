package xyz.pixelatedw.mineminenomi.ui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.ui.widgets.PlankButton;

public class EncyclopediaScreen extends Screen {
	// Fields
	private int currPage;
	private ItemStack bookStack;
	private List<DFEncyclopediaEntry> entries;

	public EncyclopediaScreen(ItemStack var1) {
		super((Component)(Component.empty()));
		this.entries = Lists.newArrayList();
		this.bookStack = var1;
		this.entries = this.getDefaultList(var1);
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		this.renderBackground(var1,var2,var3,var4);
		RenderSystem.setShaderColor(1F,1F,1F,1F);
		int var5 = ((this.width) - (192)) / (2);
		int var6 = 2;
		var1.blit(BookViewScreen.BOOK_LOCATION,var5,var6,0,0,192,192);
		DFEncyclopediaEntry var7 = (!(this.entries.isEmpty())) ? (((DFEncyclopediaEntry)(this.entries.get(this.currPage)))) : (null);
		Object[] var8 = new Object[]{Integer.valueOf((this.currPage) + (1)),Integer.valueOf(Math.max(this.getPageCount(),1))};
		MutableComponent var9 = Component.translatable("book.pageIndicator",var8);
		int var10 = this.font.width((FormattedText)(var9));
		RendererHelper.drawStringWithBorder(this.font,var1,(Component)(var9),(((var5) - (var10)) + (192)) - (44),18,-1);
		var1.pose().pushPose();
		ResourceLocation var11 = null;
		Color var12 = ((var7) != (null)) ? (((Color)(((java.util.Optional)(var7.getBaseColor())).orElse((Object)(Color.BLACK))))) : (Color.BLACK);
		Color var13 = ((var7) != (null)) ? (((Color)(((java.util.Optional)(var7.getStemColor())).orElse((Object)(Color.BLACK))))) : (Color.BLACK);
		ResourceLocation var14 = null;
		if (((var7) != (null)) && ((var7.getShape().isPresent()) && ((((Integer)(var7.getShape().get())).intValue()) > (0)))){
			String var15 = String.valueOf(var7.getShape().get());
			String var16 = "textures/items/fruits/generic/generic_fruit_" + var15 + ".png";
			var14 = ResourceLocation.fromNamespaceAndPath("mineminenomi",var16);
			String var17 = String.valueOf(var7.getShape().get());
			String var18 = "textures/items/fruits/generic/generic_fruit_" + var17 + "_stem.png";
			var11 = ResourceLocation.fromNamespaceAndPath("mineminenomi",var18);
		}else{
			var14 = ModResources.QUESTION_MARK;
		}
		var1.pose().translate((float)((var5) + (55)),(float)((var6) + (40)),2F);
		var1.pose().translate(64F,64F,0F);
		var1.pose().scale(0.8F,0.8F,0.8F);
		var1.pose().translate(-64F,-64F,1F);
		if (var14.equals(ModResources.QUESTION_MARK)){
			drawUpperIcon(var14,var1,0,0,64,32,((float)(var13.getRed())) / (255F),((float)(var13.getGreen())) / (255F),((float)(var13.getBlue())) / (255F),1F);
			drawLowerIcon(var14,var1,0,32,64,32,((float)(var12.getRed())) / (255F),((float)(var12.getGreen())) / (255F),((float)(var12.getBlue())) / (255F),1F);
		}else{
			RendererHelper.drawIcon(var14,var1.pose(),0F,0F,1F,64F,64F,((float)(var12.getRed())) / (255F),((float)(var12.getGreen())) / (255F),((float)(var12.getBlue())) / (255F),1F);
		}
		if (((var11) != (null)) && (((var7) != (null)) && ((var7.getShape().isPresent()) && ((((Integer)(var7.getShape().get())).intValue()) > (0))))){
			RendererHelper.drawIcon(var11,var1.pose(),0F,0F,1F,64F,64F,((float)(var13.getRed())) / (255F),((float)(var13.getGreen())) / (255F),((float)(var13.getBlue())) / (255F),1F);
		}
		var1.pose().popPose();
		if ((var7) != (null)){
			Component var15_1 = var7.getDevilFruit().getDevilFruitName();
			int var16_1 = (var7.isComplete()) ? (ChatFormatting.GOLD.getColor().intValue()) : (-1);
			List var17_1 = this.font.split((FormattedText)(var15_1),110);
			int var18_1 = 0;
			do{
				if ((var18_1) < (var17_1.size())){
					RendererHelper.drawStringWithBorder(this.font,var1,((FormattedCharSequence)(var17_1.get(var18_1))),((var5) + (93)) - ((this.font.width(((FormattedCharSequence)(var17_1.get(var18_1))))) / (2)),((var6) + (113)) + ((var18_1) * (12)),var16_1);
					var18_1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		Iterator var15_2 = this.renderables.iterator();
		do{
			if (var15_2.hasNext()){
				((Renderable)(var15_2.next())).render(var1,var2,var3,var4);
				continue;
			}else{
				break;
			}
		} while (true);
	}
	protected void init() {
		int var1 = ((this.width) - (192)) / (2);
		int var2 = 2;
		EncyclopediaScreen var3 = this;
		Button.OnPress var4 = (l0) -> {
			this.nextPage();
		};
		this.addRenderableWidget((GuiEventListener)(new PageButton((var1) + (116),(var2) + (159),true,var4,true)));
		EncyclopediaScreen var5 = this;
		Button.OnPress var6 = (l0) -> {
			this.previousPage();
		};
		this.addRenderableWidget((GuiEventListener)(new PageButton((var1) + (43),(var2) + (159),false,var6,true)));
		EncyclopediaScreen var7 = this;
		Button.OnPress var8 = (l0) -> {
			this.entries = this.getDefaultList(this.bookStack);
			this.currPage = 0;
		};
		this.addRenderableWidget((GuiEventListener)(new PlankButton(((this.width) / (2)) - (150),196,98,25,ModI18n.GUI_ALL,var8)));
		EncyclopediaScreen var9 = this;
		Button.OnPress var10 = (l0) -> {
			Predicate<DFEncyclopediaEntry> lv5_2 = (l2_0) -> {
				return (l2_0.getCompletion()) > (0);
			};
			this.entries = (List) (((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.getDefaultList(this.bookStack).stream())).filter((Predicate)(lv5_2)))).collect((Collector)(Collectors.toList())))));
			this.currPage = 0;
		};
		this.addRenderableWidget((GuiEventListener)(new PlankButton(((this.width) / (2)) - (50),196,98,25,ModI18n.GUI_PARTIALLY_COMPLETE_LABEL,var10)));
		EncyclopediaScreen var11 = this;
		Button.OnPress var12 = (l0) -> {
			Predicate<DFEncyclopediaEntry> lv7_2 = (l2_0) -> {
				return (l2_0.getCompletion()) >= (3);
			};
			this.entries = (List) (((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.getDefaultList(this.bookStack).stream())).filter((Predicate)(lv7_2)))).collect((Collector)(Collectors.toList())))));
			this.currPage = 0;
		};
		this.addRenderableWidget((GuiEventListener)(new PlankButton(((this.width) / (2)) + (50),196,98,25,ModI18n.GUI_ONLY_COMPLETE_LABEL,var12)));
		super.init();
	}
	private void previousPage() {
		if ((this.currPage) > (0)){
			this.currPage = (this.currPage) - (1);
		}else{
			if ((this.currPage) == (0)){
				this.currPage = (this.getPageCount()) - (1);
			}else{
				return;
			}
		}
	}
	private void nextPage() {
		if ((this.currPage) < ((this.getPageCount()) - (1))){
			this.currPage = (this.currPage) + (1);
		}else{
			if ((this.currPage) == ((this.getPageCount()) - (1))){
				this.currPage = 0;
			}else{
				return;
			}
		}
	}
	private int getPageCount() {
		return this.entries.size();
	}
	public List<DFEncyclopediaEntry> getDefaultList(ItemStack var1) {
		ArrayList var2 = Lists.newArrayList();
		HashMap var3 = ((HashMap)(var1.get(ModDataComponents.ENCYCLOPEDIA_UNLOCKS)));
		Iterator var4 = ModValues.DEVIL_FRUITS.iterator();
		do{
			if (var4.hasNext()){
				AkumaNoMiItem var5 = ((AkumaNoMiItem)(var4.next()));
				if ((var5.getRegistryKey()) == (null)){
					continue;
				}else{
					ResourceLocation var6 = var5.getRegistryKey();
					if (var3.containsKey(var6)){
						DFEncyclopediaEntry var7 = ((DFEncyclopediaEntry)(var3.get(var6)));
						var7.setDevilFruit(var5);
						var2.add(var7);
						continue;
					}else{
						DFEncyclopediaEntry var7_1 = DFEncyclopediaEntry.of(Optional.empty(),Optional.empty(),Optional.empty());
						var7_1.setDevilFruit(var5);
						var2.add(var7_1);
						continue;
					}
				}
			}else{
				break;
			}
		} while (true);
		Collections.reverse((List)(var2));
		return var2;
	}
	public boolean isPauseScreen() {
		return false;
	}
	public static void drawUpperIcon(ResourceLocation var0, GuiGraphics var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8, float var9) {
		Matrix4f var10 = var1.pose().last().pose();
		RenderSystem.setShaderTexture(0,var0);
		RenderSystem.setShader((Supplier)(((Supplier<ShaderInstance>)(GameRenderer::getPositionTexColorShader))));
		BufferBuilder var11 = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_TEX_COLOR);
		var11.addVertex(var10,(float)(var2),(float)((var3) + (var5)),1F).setColor(var6,var7,var8,var9).setUv(0F,0.5F);
		var11.addVertex(var10,(float)((var2) + (var4)),(float)((var3) + (var5)),1F).setColor(var6,var7,var8,var9).setUv(1F,0.5F);
		var11.addVertex(var10,(float)((var2) + (var4)),(float)(var3),1F).setColor(var6,var7,var8,var9).setUv(1F,0F);
		var11.addVertex(var10,(float)(var2),(float)(var3),1F).setColor(var6,var7,var8,var9).setUv(0F,0F);
		BufferUploader.drawWithShader(var11.buildOrThrow());
	}
	public static void drawLowerIcon(ResourceLocation var0, GuiGraphics var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8, float var9) {
		Matrix4f var10 = var1.pose().last().pose();
		RenderSystem.setShaderTexture(0,var0);
		RenderSystem.setShader((Supplier)(((Supplier<ShaderInstance>)(GameRenderer::getPositionTexColorShader))));
		BufferBuilder var11 = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_TEX_COLOR);
		var11.addVertex(var10,(float)(var2),(float)((var3) + (var5)),1F).setColor(var6,var7,var8,var9).setUv(0F,1F);
		var11.addVertex(var10,(float)((var2) + (var4)),(float)((var3) + (var5)),1F).setColor(var6,var7,var8,var9).setUv(1F,1F);
		var11.addVertex(var10,(float)((var2) + (var4)),(float)(var3),1F).setColor(var6,var7,var8,var9).setUv(1F,0.5F);
		var11.addVertex(var10,(float)(var2),(float)(var3),1F).setColor(var6,var7,var8,var9).setUv(0F,0.5F);
		BufferUploader.drawWithShader(var11.buildOrThrow());
	}
}