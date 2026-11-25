package xyz.pixelatedw.mineminenomi.ui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.awt.Color;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;

public class AnimatedTextButton extends Button {
	// Fields
	private boolean isMarked;

	public AnimatedTextButton(int var1, int var2, int var3, int var4, Component var5, Button.OnPress var6) {
		super(var1,var2,var3,var4,var5,var6,Button.DEFAULT_NARRATION);
		this.isMarked = false;
	}
	public AnimatedTextButton(int var1, int var2, int var3, int var4, Component var5, Button.OnPress var6, Button.CreateNarration var7) {
		super(var1,var2,var3,var4,var5,var6,var7);
		this.isMarked = false;
	}
	public void renderWidget(GuiGraphics var1, int var2, int var3, float var4) {
		if (this.visible){
			Minecraft var5 = Minecraft.getInstance();
			var1.blitSprite(SPRITES.get(this.active,this.isHoveredOrFocused()),this.getX(),this.getY(),this.getWidth(),this.getHeight());
			RenderSystem.setShaderColor(1F,1F,1F,1F);
			int var6 = 1;
			int var7 = this.getFGColor();
			float var8 = 0F;
			if (this.isHovered()){
				this.isMarked = false;
			}
			if (this.isMarked){
				var6 = 2;
				var7 = Color.YELLOW.getRGB();
				float var9 = (float)((Util.getMillis()) / (40L));
				var8 = ((float)(Math.sin((double)((var9) / (10F))))) / (2F);
			}
			Object var9_1 = this.getMessage();
			int var10 = var5.font.width((FormattedText)(var9_1));
			int var11 = var5.font.width("...");
			if (((var10) > ((this.width) - (6))) && ((var10) > (var11))){
				String var12 = var5.font.substrByWidth((FormattedText)(var9_1),((this.width) - (6)) - (var11)).getString();
				String var13 = var12 + "...";
				var9_1 = Component.literal(var13);
			}
			var1.pose().pushPose();
			var1.pose().translate((float)((this.getX()) + ((this.width) / (2))),(float)((this.getY()) + ((this.height) / (2))),1F);
			var1.pose().mulPose(Axis.ZP.rotation(var8));
			var1.pose().scale((float)(var6),(float)(var6),(float)(var6));
			var1.drawCenteredString(var5.font,(Component)(var9_1),0,-4,var7);
			var1.pose().popPose();
		}
	}
	public void setMarked() {
		this.isMarked = true;
	}
}