package xyz.pixelatedw.mineminenomi.ui.panel;

import com.mojang.blaze3d.vertex.Tesselator;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.crew.Crew$Member;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CKickFromCrewPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.CrewDetailsScreen;
import xyz.pixelatedw.mineminenomi.ui.widgets.SimpleButton;

public class CrewMembersScrollPanel extends ScrollPanel {
	// Fields
	private static final int ENTRY_HEIGHT = 20;
	private static final Component KICK_BUTTON_TEXT = Component.literal("X");
	private final CrewDetailsScreen parent;
	private final List<Crew$Member> displayedMembers;
	private final SimpleButton[] listButtons;

	public CrewMembersScrollPanel(CrewDetailsScreen var1, List<Crew$Member> var2) {
		super(var1.getMinecraft(),(var1.width) / (3),(var1.height) - (100),50,((var1.width) - ((var1.width) / (3))) - (10));
		Crew$Member var7 = null;
		this.parent = var1;
		this.displayedMembers = var2;
		this.listButtons = new SimpleButton[this.displayedMembers.size()];
		if (this.parent.isClientCaptain()){
			int var3 = 0;
			do{
				if ((var3) < (this.displayedMembers.size())){
					Crew$Member var4 = ((Crew$Member)(this.displayedMembers.get(var3)));
					if (((var4) != (null)) && (!(var4.isCaptain()))){
						int var5 = var3;
						CrewMembersScrollPanel var6 = this;
						var7 = var4;
						int var8 = var5;
						final Crew$Member var7_f1 = var7;
						final int var8_f1 = var8;
						Button.OnPress var9 = (l0) -> {
			ModNetwork.sendToServer(new CKickFromCrewPacket(var7_f1.getUUID()));
			this.displayedMembers.remove(var8_f1);
		};
						SimpleButton var10 = new SimpleButton(0,0,10,10,KICK_BUTTON_TEXT,var9);
						this.listButtons[var3] = var10;
					}
					var3++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	protected void drawPanel(GuiGraphics var1, int var2, int var3, Tesselator var4, int var5, int var6) {
		int var7 = var3;
		int var8 = ((this.parent.width) - ((this.parent.width) / (3))) + (20);
		int var9 = 0;
		int var10 = 0;
		do{
			if ((var10) < (this.displayedMembers.size())){
				Crew$Member var11 = ((Crew$Member)(this.displayedMembers.get(var10)));
				if ((var11) == (null)){

				}else{
					String var14 = null;
					String var12 = var11.getUsername();
					if ((var12.length()) >= (20)){
						String var13 = ModI18n.CREW_CAPTAIN.getString();
						var14 = " (" + var13 + ")";
					}
					String var13_1 = var12;
					String var14_1 = (var11.isCaptain()) ? (var14) : ("");
					String var15 = var13_1 + var14_1;
					var12 = var15;
					RendererHelper.drawStringWithBorder(this.parent.getMinecraft().font,var1,var12,var8,((var7) + (1)) + (var9),-1);
					SimpleButton var16 = this.listButtons[var10];
					if ((!(var11.isCaptain())) && ((var16) != (null))){
						var16.setPosition((var8) - (20),(var7) + (var9));
						var16.setHoverTextColor(16711680);
						var16.render(var1,var5,var6,0F);
					}
					var9 = (var9) + (20);
				}
				var10++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	private Crew$Member findEntry(int var1, int var2) {
		double var3 = (double)(((float)((var2) - (this.top))) + (this.scrollDistance));
		if ((var3) <= (0D)){
			return null;
		}else{
			int var4 = (int)((var3) / (20D));
			if ((var4) >= (this.displayedMembers.size())){
				return null;
			}else{
				Crew$Member var5 = ((Crew$Member)(this.displayedMembers.get(var4)));
				if ((var5) != (null)){
					return var5;
				}else{
					return null;
				}
			}
		}
	}
	public boolean mouseClicked(double var1, double var2, int var3) {
		SimpleButton[] var4 = this.listButtons;
		int var5 = var4.length;
		int var6 = 0;
		do{
			if ((var6) < (var5)){
				SimpleButton var7 = var4[var6];
				if (((var7) == (null)) || (!(var7.mouseClicked(var1,var2,var3)))){
					var6++;
					continue;
				}else{
					this.setFocused((GuiEventListener)(var7));
					if ((var3) == (0)){
						this.setDragging(true);
					}
					return true;
				}
			}else{
				break;
			}
		} while (true);
		return super.mouseClicked(var1,var2,var3);
	}
	public boolean mouseScrolled(double var1, double var2, double var3, double var4) {
		if ((this.getContentHeight()) < (this.height)){
			return false;
		}else{
			return super.mouseScrolled(var1,var2,var3,var4);
		}
	}
	public boolean mouseReleased(double var1, double var2, int var3) {
		return false;
	}
	public boolean isMouseOver(double var1, double var2) {
		return ((var1) >= ((double)(this.left))) && (((var2) >= ((double)(this.top))) && (((var1) < ((double)(((this.left) + (this.width)) - (5)))) && ((var2) < ((double)((this.top) + (this.height))))));
	}
	protected int getContentHeight() {
		return ((this.displayedMembers.size()) * (20)) - (2);
	}
	protected int getScrollAmount() {
		return 12;
	}
	public NarratableEntry.NarrationPriority narrationPriority() {
		return NarratableEntry.NarrationPriority.NONE;
	}
	public void updateNarration(NarrationElementOutput var1) {
	}
}