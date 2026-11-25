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

public class PlankButton extends Button {
	public PlankButton(int var1, int var2, int var3, int var4, Component var5, Button.OnPress var6) {
		super(var1,var2,var3,var4,var5,var6,Button.DEFAULT_NARRATION);
	}
	public PlankButton(int var1, int var2, int var3, int var4, Component var5, Button.OnPress var6, Button.CreateNarration var7) {
		super(var1,var2,var3,var4,var5,var6,var7);
	}
	private int getTextureY() {
		int var1 = 1;
		if (!(this.active)){
			var1 = 0;
		}else{
			if (this.isHoveredOrFocused()){
				var1 = 2;
			}
		}
		return (0) + ((var1) * (30));
	}
	public void renderWidget(GuiGraphics var1, int var2, int var3, float var4) {
		RenderSystem.enableDepthTest();
		if (this.isHovered){

		}else{
			var1.pose().pushPose();
			float var5 = 1F;
			float var6 = 1F;
			float var7 = 1F;
			if (!(this.active)){
				float var8 = 0.4F;
				var7 = var8;
				var6 = var8;
				var5 = var8;
			}else{
				if (this.isHovered){
					var1.pose().translate(0D,1D,0D);
					float var8_1 = 0.6F;
					var7 = var8_1;
					var6 = var8_1;
					var5 = var8_1;
				}
			}
			Font var8_2 = Minecraft.getInstance().font;
			RenderSystem.setShaderColor(var5,var6,var7,this.alpha);
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableDepthTest();
			var1.blitSprite(ModResources.PLANK_BUTTON,this.getX(),this.getY(),this.getWidth(),this.getHeight());
			RenderSystem.setShaderColor(1F,1F,1F,1F);
			int var9 = (this.getFGColor()) | ((Mth.ceil((this.alpha) * (255F))) << (24));
			int var10 = (var8_2.width((FormattedText)(this.getMessage()))) / (2);
			RendererHelper.drawStringWithBorder(var8_2,var1,this.getMessage(),((this.getX()) + ((this.width) / (2))) - (var10),(this.getY()) + (((this.height) - (8)) / (2)),var9);
			var1.pose().popPose();
			RenderSystem.disableDepthTest();
			return;
		}
	}
}