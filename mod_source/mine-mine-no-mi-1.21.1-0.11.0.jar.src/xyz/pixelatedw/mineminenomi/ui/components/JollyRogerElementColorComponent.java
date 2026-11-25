package xyz.pixelatedw.mineminenomi.ui.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.ui.screens.JollyRogerEditorScreen;

public class JollyRogerElementColorComponent implements Renderable, GuiEventListener, NarratableEntry {
	// Fields
	private final JollyRogerEditorScreen parentScreen;
	private boolean isVisible;
	private List<GuiEventListener> buttons;
	private List<Renderable> renderable;
	private int posX;
	private int posY;
	private ExtendedSlider redSlider;
	private ExtendedSlider greenSlider;
	private ExtendedSlider blueSlider;

	public JollyRogerElementColorComponent(JollyRogerEditorScreen var1, int var2, int var3) {
		this.isVisible = true;
		this.buttons = new ArrayList();
		this.renderable = new ArrayList();
		this.parentScreen = var1;
		this.posX = var2;
		this.posY = var3;
		this.init(var2,var3);
	}
	public void init(int var1, int var2) {
		this.buttons.clear();
		this.renderable.clear();
		var1 = (var1) - ((this.parentScreen.isSmallScreen()) ? (0) : (70));
		var2 = (var2) + ((this.parentScreen.isSmallScreen()) ? (30) : (0));
		int var3 = (this.parentScreen.isSmallScreen()) ? (15) : (20);
		this.redSlider = new ExtendedSlider(var1,var2,50,var3,(Component)(Component.empty()),(Component)(Component.empty()),0D,255D,255D,true);
		this.addWidget(this.redSlider);
		var2 = (var2) + ((17) + ((this.parentScreen.isSmallScreen()) ? (0) : (5)));
		this.greenSlider = new ExtendedSlider(var1,var2,50,var3,(Component)(Component.empty()),(Component)(Component.empty()),0D,255D,255D,true);
		this.addWidget(this.greenSlider);
		var2 = (var2) + ((17) + ((this.parentScreen.isSmallScreen()) ? (0) : (5)));
		this.blueSlider = new ExtendedSlider(var1,var2,50,var3,(Component)(Component.empty()),(Component)(Component.empty()),0D,255D,255D,true);
		this.addWidget(this.blueSlider);
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		if (!(this.isVisible)){
			return;
		}else{
			Component var5 = ModI18n.GUI_RED;
			int var6 = ((this.posX) - (5)) - (this.parentScreen.getMinecraft().font.width((FormattedText)(var5)));
			RendererHelper.drawStringWithBorder(this.parentScreen.getMinecraft().font,var1,var5,var6,(this.posY) + (34),16777215);
			Component var7 = ModI18n.GUI_GREEN;
			var6 = ((this.posX) - (5)) - (this.parentScreen.getMinecraft().font.width((FormattedText)(var7)));
			RendererHelper.drawStringWithBorder(this.parentScreen.getMinecraft().font,var1,ModI18n.GUI_GREEN,var6,(this.posY) + (50),16777215);
			Component var8 = ModI18n.GUI_BLUE;
			var6 = ((this.posX) - (5)) - (this.parentScreen.getMinecraft().font.width((FormattedText)(var8)));
			RendererHelper.drawStringWithBorder(this.parentScreen.getMinecraft().font,var1,ModI18n.GUI_BLUE,var6,(this.posY) + (67),16777215);
			Iterator var9 = this.renderable.iterator();
			do{
				if (var9.hasNext()){
					((Renderable)(var9.next())).render(var1,var2,var3,var4);
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public boolean mouseClicked(double var1, double var2, int var3) {
		if (!(this.isVisible)){
			return false;
		}else{
			Iterator var4 = this.buttons.iterator();
			do{
				if (var4.hasNext()){
					GuiEventListener var5 = ((GuiEventListener)(var4.next()));
					if (var5.mouseClicked(var1,var2,var3)){
						if (var5 instanceof AbstractSliderButton){
							this.parentScreen.setFocused(var5);
							if ((var3) == (0)){
								this.parentScreen.setDragging(true);
							}else{
								return false;
							}
						}
						return false;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return false;
		}
	}
	public boolean isMouseOver(double var1, double var2) {
		if ((!(this.redSlider.isFocused())) && ((!(this.greenSlider.isFocused())) && (!(this.blueSlider.isFocused())))){
			return false;
		}else{
			return true;
		}
	}
	public void setVisible(boolean var1) {
		this.isVisible = var1;
	}
	public void setRGBValues(@Nullable() JollyRogerElement var1) {
		if ((var1) != (null)){
			this.redSlider.setValue((double)(var1.getRed()));
			this.greenSlider.setValue((double)(var1.getGreen()));
			this.blueSlider.setValue((double)(var1.getBlue()));
		}else{
			this.redSlider.setValue(255D);
			this.greenSlider.setValue(255D);
			this.blueSlider.setValue(255D);
		}
	}
	public int getRed() {
		return this.redSlider.getValueInt();
	}
	public int getGreen() {
		return this.greenSlider.getValueInt();
	}
	public int getBlue() {
		return this.blueSlider.getValueInt();
	}
	public <T extends GuiEventListener & Renderable> void addWidget(T var1) {
		((java.util.List)(this.buttons)).add((Object)(var1));
		this.renderable.add(((Renderable)(var1)));
	}
	public NarratableEntry.NarrationPriority narrationPriority() {
		return NarratableEntry.NarrationPriority.NONE;
	}
	public void setFocused(boolean var1) {
	}
	public boolean isFocused() {
		return false;
	}
	public void updateNarration(NarrationElementOutput var1) {
	}
}