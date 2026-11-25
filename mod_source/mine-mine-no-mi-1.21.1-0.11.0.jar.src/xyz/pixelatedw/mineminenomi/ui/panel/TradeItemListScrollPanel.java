package xyz.pixelatedw.mineminenomi.ui.panel;

import com.mojang.blaze3d.vertex.Tesselator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.TradeEntry;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.ui.screens.TraderScreen;

public class TradeItemListScrollPanel extends ScrollPanel {
	// Fields
	private TraderScreen parent;
	private List<TradeEntry> entries;
	private static final int ENTRY_HEIGHT = 24;

	public TradeItemListScrollPanel(TraderScreen var1, List<TradeEntry> var2) {
		super(var1.getMinecraft(),215,140,((var1.height) / (2)) - (50),((var1.width) / (2)) - (109));
		this.entries = new ArrayList();
		this.parent = var1;
		this.entries = var2;
		Iterator var3 = this.entries.iterator();
		do{
			if (var3.hasNext()){
				TradeEntry var4 = ((TradeEntry)(var3.next()));
				if (((Boolean)(var4.itemStack().getOrDefault(ModDataComponents.IS_CLONE,Boolean.valueOf(false)))).booleanValue()){
					String var5 = String.valueOf((Object)(ChatFormatting.RESET));
					String var6 = var4.itemStack().getHoverName().getString();
					String var7 = var5 + var6 + " (Replica)";
					var4.itemStack().set(DataComponents.CUSTOM_NAME,Component.literal(var7));
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public boolean mouseReleased(double var1, double var2, int var3) {
		return true;
	}
	protected int getContentHeight() {
		return ((this.entries.size()) * (24)) + (2);
	}
	protected int getScrollAmount() {
		return 10;
	}
	protected void drawPanel(GuiGraphics var1, int var2, int var3, Tesselator var4, int var5, int var6) {
		Iterator var7 = this.entries.iterator();
		do{
			if (var7.hasNext()){
				TradeEntry var8 = ((TradeEntry)(var7.next()));
				int var9 = var3;
				int var10 = (((this.parent.width) / (2)) - (109)) + (40);
				var1.renderItem(var8.itemStack(),(var10) - (30),(var9) - (1));
				if (((this.parent.getSelectedStack()) != (null)) && (var8.equals(this.parent.getSelectedStack()))){
					this.drawGradientRect(var1,this.left,(var9) - (4),this.right,((var9) + (24)) - (4),1437248170,581610154);
				}
				this.parent.drawSizedString(var1,var8.itemStack().getHoverName(),(var10) + (50),(var9) + (4),0.8F,-1);
				int var11 = var8.getPrice();
				String var12 = "" + var11;
				this.parent.drawSizedString(var1,(Component)(Component.literal(var12)),(var10) + (122),(var9) + (4),0.8F,-1);
				var3 = (var3) + (24);
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public TradeEntry findStackEntry(int var1, int var2) {
		double var3 = (double)(((float)((var2) - (this.top))) + (this.scrollDistance));
		int var4 = (((var1) >= (this.left)) && (((var2) >= (this.top)) && (((var1) < (((this.left) + (this.width)) - (5))) && ((var2) < ((this.top) + (this.height)))))) ? (1) : (0);
		if ((!((var3) <= (0D))) && ((var4) != (0))){
			int var5 = (int)((var3) / (24D));
			if ((var5) >= (this.entries.size())){
				return null;
			}else{
				TradeEntry var6 = ((TradeEntry)(this.entries.get(var5)));
				if (((var6) != (null)) && (((var1) >= (this.left)) && (((var1) <= (this.right)) && ((var2) <= (this.bottom))))){
					return var6;
				}else{
					return null;
				}
			}
		}else{
			return null;
		}
	}
	public boolean mouseClicked(double var1, double var2, int var3) {
		TradeEntry var4 = this.findStackEntry((int)(var1),(int)(var2));
		if ((((((var1) >= ((double)(this.left))) && (((var2) >= ((double)(this.top))) && (((var1) < ((double)(((this.left) + (this.width)) - (5)))) && ((var2) < ((double)((this.top) + (this.height))))))) ? (1) : (0)) != (0)) && ((var4) != (null))){
			this.parent.setSelectedStack(var4);
			this.parent.setWantedAmount(1);
		}
		return super.mouseClicked(var1,var2,var3);
	}
	public void removeEntry(ItemStack var1) {
		int var2 = 0;
		do{
			if ((var2) < (this.entries.size())){
				if ((((TradeEntry)(this.entries.get(var2))).itemStack().getItem()) == (var1.getItem())){
					this.entries.remove(var2);
				}
				var2++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public NarratableEntry.NarrationPriority narrationPriority() {
		return NarratableEntry.NarrationPriority.NONE;
	}
	public void updateNarration(NarrationElementOutput var1) {
	}
}