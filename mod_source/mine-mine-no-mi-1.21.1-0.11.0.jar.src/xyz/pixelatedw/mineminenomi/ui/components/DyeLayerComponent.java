package xyz.pixelatedw.mineminenomi.ui.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.CSetDyeLayerPacket;
import xyz.pixelatedw.mineminenomi.ui.widgets.SimpleButton;

public class DyeLayerComponent implements Renderable, GuiEventListener, NarratableEntry {
	// Fields
	private Minecraft minecraft;
	private CraftingScreen parentScreen;
	private List<GuiEventListener> buttons;
	private List<Renderable> renderable;
	private boolean isVisible;
	private int layer;
	private int maxLayers;
	private int lastLeftPos;
	private int posX;
	private int posY;

	public DyeLayerComponent(Minecraft var1, CraftingScreen var2, int var3, int var4) {
		this.buttons = new ArrayList();
		this.renderable = new ArrayList();
		this.isVisible = false;
		this.maxLayers = 2;
		this.minecraft = var1;
		this.parentScreen = var2;
		this.posX = var3;
		this.posY = var4;
		this.lastLeftPos = var2.getGuiLeft();
		this.init();
	}
	public void init() {
		this.buttons.clear();
		this.renderable.clear();
		SimpleButton var1 = new SimpleButton((this.posX) + (18),this.posY,10,8,(Component)(Component.literal("▷")),(Button.OnPress)(((net.minecraft.client.gui.components.Button.OnPress)(this::forwardEvent))));
		var1.setTextOnly();
		this.addWidget(var1);
		SimpleButton var2 = new SimpleButton((this.posX) + (5),this.posY,10,8,(Component)(Component.literal("◁")),(Button.OnPress)(((net.minecraft.client.gui.components.Button.OnPress)(this::backwardEvent))));
		var2.setTextOnly();
		this.addWidget(var2);
	}
	private void forwardEvent(Button var1) {
		this.layer = Mth.clamp((this.layer) + (1),0,(this.maxLayers) - (1));
		ModNetwork.sendToServer(new CSetDyeLayerPacket(this.layer));
	}
	private void backwardEvent(Button var1) {
		this.layer = Mth.clamp((this.layer) - (1),0,(this.maxLayers) - (1));
		ModNetwork.sendToServer(new CSetDyeLayerPacket(this.layer));
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		if (!(this.isVisible)){
			return;
		}else{
			if ((this.lastLeftPos) != (this.parentScreen.getGuiLeft())){
				this.lastLeftPos = this.parentScreen.getGuiLeft();
				this.posX = this.lastLeftPos;
				this.init();
			}
			int var5 = Math.min(this.layer,(this.maxLayers) - (1));
			int var6 = (var5) + (1);
			String var7 = "" + var6;
			MutableComponent var8 = Component.literal(var7);
			int var9 = (this.posX) - ((this.minecraft.font.width((FormattedText)(var8))) / (2));
			var1.drawString(this.minecraft.font,(Component)(var8),(var9) + (16),(this.posY) - (10),4210752,false);
			Iterator var10 = this.renderable.iterator();
			do{
				if (var10.hasNext()){
					((Renderable)(var10.next())).render(var1,var2,var3,var4);
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
					if (((GuiEventListener)(var4.next())).mouseClicked(var1,var2,var3)){
						return true;
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
	public void setLayer(int var1) {
		this.layer = var1;
	}
	public void setMaxLayers(int var1) {
		this.maxLayers = var1;
	}
	public void setVisible(boolean var1) {
		this.isVisible = var1;
	}
}