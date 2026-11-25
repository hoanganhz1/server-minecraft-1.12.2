package xyz.pixelatedw.mineminenomi.ui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class BookSignSideButton extends Button {
	// Fields
	private boolean isPressed;
	private boolean isFlipped;
	private int textureWidth;
	private ResourceLocation iconTexture;
	private float iconScale;
	private int iconPosX;
	private int iconPosY;

	public BookSignSideButton(int var1, int var2, int var3, int var4, Component var5, Button.OnPress var6) {
		super(var1,var2,var3,var4,var5,var6,Button.DEFAULT_NARRATION);
		this.textureWidth = 120;
		this.iconScale = 1F;
	}
	public BookSignSideButton(int var1, int var2, int var3, int var4, Component var5, Button.OnPress var6, Button.CreateNarration var7) {
		super(var1,var2,var3,var4,var5,var6,var7);
		this.textureWidth = 120;
		this.iconScale = 1F;
	}
	public void renderWidget(GuiGraphics var1, int var2, int var3, float var4) {
		RenderSystem.enableDepthTest();
		var1.pose().pushPose();
		float var5 = 1F;
		float var6 = 1F;
		float var7 = 1F;
		if ((this.active) && (!(this.isPressed))){
			if (this.isHovered){
				var1.pose().translate(0D,0.5D,1D);
				float var8 = 0.6F;
				var7 = var8;
				var6 = var8;
				var5 = var8;
			}
		}else{
			float var8_1 = 0.4F;
			var7 = var8_1;
			var6 = var8_1;
			var5 = var8_1;
		}
		Font var8_2 = Minecraft.getInstance().font;
		RenderSystem.setShaderColor(var5,var6,var7,this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		var1.blit(ModResources.BUTTON,this.getX(),this.getY(),(float)((this.isFlipped) ? (this.textureWidth) : (0)),8F,this.textureWidth,50,(this.isFlipped) ? (-120) : (120),50);
		RenderSystem.setShaderColor(1F,1F,1F,1F);
		if ((this.iconTexture) != (null)){
			var1.pose().pushPose();
			RenderSystem.enableBlend();
			var1.pose().translate((float)(this.iconPosX),(float)(this.iconPosY),151F);
			var1.pose().scale(this.iconScale,this.iconScale,this.iconScale);
			RendererHelper.drawIcon(this.iconTexture,var1.pose(),0F,0F,1F,16F,16F);
			var1.pose().popPose();
		}
		int var9 = (this.getFGColor()) | ((Mth.ceil((this.alpha) * (255F))) << (24));
		int var10 = (var8_2.width((FormattedText)(this.getMessage()))) / (2);
		RendererHelper.drawStringWithBorder(var8_2,var1,this.getMessage(),(((this.getX()) + (10)) + ((this.width) / (2))) - (var10),(this.getY()) + (((this.height) - (8)) / (2)),var9);
		var1.pose().popPose();
		RenderSystem.disableDepthTest();
	}
	public void setIsPressed(boolean var1) {
		this.isPressed = var1;
	}
	public void setFlipped() {
		this.isFlipped = true;
	}
	public void setTextureWidth(int var1) {
		this.textureWidth = var1;
	}
	public void setIcon(ResourceLocation var1, int var2, int var3, float var4) {
		this.iconTexture = var1;
		this.iconPosX = var2;
		this.iconPosY = var3;
		this.iconScale = var4;
	}
}