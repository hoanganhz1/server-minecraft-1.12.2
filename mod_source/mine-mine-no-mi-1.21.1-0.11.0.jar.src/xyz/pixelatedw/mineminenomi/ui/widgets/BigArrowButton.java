package xyz.pixelatedw.mineminenomi.ui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class BigArrowButton extends Button {
	// Fields
	private boolean isPressed;
	private boolean isFlipped;

	public BigArrowButton(int var1, int var2, int var3, int var4, Button.OnPress var5) {
		super(var1,var2,var3,var4,(Component)(Component.empty()),var5,Button.DEFAULT_NARRATION);
	}
	public BigArrowButton(int var1, int var2, int var3, int var4, Button.OnPress var5, Button.CreateNarration var6) {
		super(var1,var2,var3,var4,(Component)(Component.empty()),var5,var6);
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
		RenderSystem.setShaderColor(var5,var6,var7,this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		var1.blit((this.isFlipped) ? (ModResources.BIG_WOOD_BUTTON_RIGHT) : (ModResources.BIG_WOOD_BUTTON_LEFT),this.getX(),this.getY(),5F,15F,32,120,32,128);
		RenderSystem.setShaderColor(1F,1F,1F,1F);
		var1.pose().popPose();
		RenderSystem.disableDepthTest();
	}
	public void setIsPressed(boolean var1) {
		this.isPressed = var1;
	}
	public void setFlipped() {
		this.isFlipped = true;
	}
}