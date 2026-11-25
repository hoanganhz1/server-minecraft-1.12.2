package xyz.pixelatedw.mineminenomi.api.ui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

public class FlickeringString {
	// Fields
	private long tick;
	private Component message;
	private int flicker;
	private boolean isVisible;
	private Minecraft mc;

	public FlickeringString(Component var1, int var2) {
		this.isVisible = true;
		this.mc = Minecraft.getInstance();
		this.message = var1;
		this.flicker = var2;
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		this.tick = (long)(((float)(this.tick)) + ((1F) + (var4)));
		if (((this.tick) % ((long)(this.flicker))) == (0L)){
			this.isVisible = !(this.isVisible);
		}else{
			Object var5 = (this.isVisible) ? (this.message) : (Component.empty());
			RendererHelper.drawStringWithBorder(this.mc.font,var1,(Component)(var5),var2,var3,Color.WHITE.getRGB());
		}
	}
}