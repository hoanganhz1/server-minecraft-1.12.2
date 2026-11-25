package xyz.pixelatedw.mineminenomi.ui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

public class SimpleButton extends Button {
	// Fields
	private boolean isPressed;
	private boolean textOnly;
	private float fontSize;
	private int defaultTextColor;
	private int outlineColor;
	private int defaultTopGradientColor;
	private int defaultBottomGradientColor;
	private int hoverTextColor;
	private int hoverTopGradientColor;
	private int hoverBottomGradientColor;
	private int pressedTextColor;
	private int pressedTopGradientColor;
	private int pressedBottomGradientColor;

	public SimpleButton(int var1, int var2, int var3, int var4, Component var5, Button.OnPress var6) {
		super(var1,var2,var3,var4,var5,var6,Button.DEFAULT_NARRATION);
		this.fontSize = 1F;
		this.defaultTextColor = 16777215;
		this.outlineColor = 0;
		this.defaultTopGradientColor = -5000269;
		this.defaultBottomGradientColor = -7105645;
		this.hoverTextColor = 65365;
		this.hoverTopGradientColor = -5000269;
		this.hoverBottomGradientColor = -11513776;
		this.pressedTextColor = this.defaultTextColor;
		this.pressedTopGradientColor = -16724992;
		this.pressedBottomGradientColor = -16755456;
	}
	public void setFontSize(float var1) {
		this.fontSize = var1;
	}
	public void setTextOnly() {
		this.textOnly = true;
	}
	public void setDefaultTextColor(int var1) {
		this.defaultTextColor = var1;
	}
	public void setHoverTextColor(int var1) {
		this.hoverTextColor = var1;
	}
	public void setPressedTextColor(int var1) {
		this.pressedTextColor = var1;
	}
	public void renderWidget(GuiGraphics var1, int var2, int var3, float var4) {
		RenderSystem.enableDepthTest();
		var1.pose().pushPose();
		if (this.visible){
			this.isHovered = ((var2) >= (this.getX())) && (((var3) >= (this.getY())) && (((var2) < ((this.getX()) + (this.width))) && ((var3) < ((this.getY()) + (this.height)))));
			int var5 = this.defaultTextColor;
			int var6 = this.defaultTopGradientColor;
			int var7 = this.defaultBottomGradientColor;
			if (this.isPressed){
				var5 = this.pressedTextColor;
				var6 = this.pressedTopGradientColor;
				var7 = this.pressedBottomGradientColor;
			}
			if (this.isHovered){
				var1.pose().translate(0D,1D,0D);
				var5 = this.hoverTextColor;
				var6 = this.hoverTopGradientColor;
				var7 = this.hoverBottomGradientColor;
			}
			int var8 = 1;
			if (!(this.textOnly)){
				var1.fillGradient((this.getX()) - (var8),(this.getY()) - (var8),((this.width) + (this.getX())) + (var8),((this.height) + (this.getY())) + (var8),this.outlineColor,this.outlineColor);
				var1.fillGradient(this.getX(),this.getY(),(this.width) + (this.getX()),(this.height) + (this.getY()),var6,var7);
			}
			Font var9 = Minecraft.getInstance().font;
			var1.pose().pushPose();
			int var10 = (int)((((float)(this.getX())) - ((((float)(var9.width((FormattedText)(this.getMessage())))) * (this.fontSize)) / (2F))) + ((float)((this.width) / (2))));
			int var11 = ((this.getY()) + ((this.height) / (2))) - (4);
			var1.pose().translate((float)(var10),(float)(var11),2F);
			var1.pose().scale(this.fontSize,this.fontSize,this.fontSize);
			RendererHelper.drawStringWithBorder(var9,var1,this.getMessage().getString(),0,0,var5);
			var1.pose().popPose();
		}
		var1.pose().popPose();
	}
	public void select() {
		this.isPressed = !(this.isPressed);
	}
	public void setIsPressed(boolean var1) {
		this.isPressed = var1;
	}
}