package xyz.pixelatedw.mineminenomi.ui.panel;

import com.mojang.blaze3d.vertex.Tesselator;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.ui.screens.ChallengesScreen;
import xyz.pixelatedw.mineminenomi.ui.widgets.PlankButton;

public class ChallengesListScrollPanel extends ScrollPanel {
	// Fields
	private static final int ENTRY_HEIGHT = 36;
	private final ChallengesScreen parent;
	private final List<Challenge> displayedChallenges;

	public ChallengesListScrollPanel(ChallengesScreen var1, List<Challenge> var2) {
		super(var1.getMinecraft(),170,210,((var1.height) / (2)) - (90),((var1.width) / (2)) - (245));
		this.parent = var1;
		this.displayedChallenges = var2;
	}
	public boolean mouseReleased(double var1, double var2, int var3) {
		return false;
	}
	protected int getContentHeight() {
		return ((this.displayedChallenges.size()) * (36)) - (2);
	}
	protected int getScrollAmount() {
		return 12;
	}
	protected void drawGradientRect(GuiGraphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
	}
	protected void drawBackground(GuiGraphics var1, Tesselator var2, float var3) {
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		super.render(var1,var2,var3,var4);
	}
	protected void drawPanel(GuiGraphics var1, int var2, int var3, Tesselator var4, int var5, int var6) {
		int var7 = var3;
		int var8 = (((this.parent.width) / (2)) - (280)) + (40);
		int var9 = 0;
		Iterator var10 = this.displayedChallenges.iterator();
		do{
			if (var10.hasNext()){
				Challenge var11 = ((Challenge)(var10.next()));
				Button.OnPress var12 = (l0) -> {
		};
				PlankButton var13 = new PlankButton(var8,(var7) + (var9),160,31,var11.getCore().getLocalizedTitle(),var12);
				if (((this.parent.getSelectedChallenge()) != (null)) && (this.parent.getSelectedChallenge().equals(var11))){
					var13.active = false;
				}
				if (var11.isComplete()){
					var13.setFGColor(Color.YELLOW.getRGB());
				}
				var13.render(var1,var5,var6,0F);
				var9 = (var9) + (36);
				continue;
			}else{
				break;
			}
		} while (true);
	}
	private Challenge findEntry(int var1, int var2) {
		double var3 = (double)(((float)((var2) - (this.top))) + (this.scrollDistance));
		if ((var3) <= (0D)){
			return null;
		}else{
			int var4 = (int)((var3) / (36D));
			if ((var4) >= (this.displayedChallenges.size())){
				return null;
			}else{
				Challenge var5 = ((Challenge)(this.displayedChallenges.get(var4)));
				if ((var5) != (null)){
					return var5;
				}else{
					return null;
				}
			}
		}
	}
	public boolean mouseClicked(double var1, double var2, int var3) {
		Challenge var4 = this.findEntry((int)(var1),(int)(var2));
		if (((var4) != (null)) && ((this.isMouseOver(var1,var2)) && ((var3) == (0)))){
			if (((((this.parent.getSelectedChallenge()) != (null)) && (this.parent.getSelectedChallenge().equals(var4))) ? (1) : (0)) == (0)){
				Minecraft.getInstance().getSoundManager().play((SoundInstance)(SimpleSoundInstance.forUI((Holder)(SoundEvents.UI_BUTTON_CLICK),1F)));
				this.parent.startInfoPanelAnimation();
				this.parent.setSelectedChallenge(var4);
			}
		}
		return super.mouseClicked(var1,var2,var3);
	}
	public boolean isMouseOver(double var1, double var2) {
		return ((var1) >= ((double)(this.left))) && (((var2) >= ((double)(this.top))) && (((var1) < ((double)(((this.left) + (this.width)) - (5)))) && ((var2) < ((double)((this.top) + (this.height))))));
	}
	public NarratableEntry.NarrationPriority narrationPriority() {
		return NarratableEntry.NarrationPriority.NONE;
	}
	public void updateNarration(NarrationElementOutput var1) {
	}
}