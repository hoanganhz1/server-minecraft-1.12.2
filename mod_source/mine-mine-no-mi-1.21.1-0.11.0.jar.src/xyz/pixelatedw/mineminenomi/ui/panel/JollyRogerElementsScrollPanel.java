package xyz.pixelatedw.mineminenomi.ui.panel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement$LayerType;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.ui.screens.JollyRogerEditorScreen;

public class JollyRogerElementsScrollPanel extends ScrollPanel {
	// Fields
	private static final int ENTRY_WIDTH = 64;
	private static final int ENTRY_HEIGHT = 64;
	private static final JollyRogerElementsScrollPanel$Entry REMOVE_ELEMENT = new JollyRogerElementsScrollPanel$Entry((JollyRogerElement)(null));
	private final Player player;
	private final Crew crew;
	private final JollyRogerEditorScreen parent;
	private final int elementsPerRow;
	private List<JollyRogerElementsScrollPanel$Entry> entries;
	private int rows;

	public JollyRogerElementsScrollPanel(JollyRogerEditorScreen var1, int var2, int var3) {
		super(var1.getMinecraft(),var2,var1.height,0,(var1.width) - (var2));
		this.parent = var1;
		this.player = var1.getPlayer();
		this.crew = var1.getCrew();
		this.elementsPerRow = var3;
		this.setEntries(this.parent.getSelectedLayerType());
	}
	public void setEntries(JollyRogerElement$LayerType var1) {
		this.entries = new ArrayList();
		this.entries.add(REMOVE_ELEMENT);
		List var2 = this.parent.getListFromType(var1);
		JollyRogerElementsScrollPanel var3 = this;
		Predicate<JollyRogerElement> var4 = (l0) -> {
			return l0.canUse(this.player,this.crew);
		};
		((java.util.List)(this.entries)).addAll((Collection)(((java.util.stream.Stream)(var2.stream())).filter((Predicate)(var4)).map((Function)(((Function<JollyRogerElement, JollyRogerElementsScrollPanel$Entry>)(JollyRogerElementsScrollPanel$Entry::new)))).toList()));
		this.rows = (int)(Math.ceil((double)(((float)(this.entries.size())) / ((float)(this.elementsPerRow)))));
		this.scrollDistance = 0F;
	}
	protected void drawGradientRect(GuiGraphics var1, int var2, int var3, int var4, int var5, int var6, int var7) {
		super.drawGradientRect(var1,var2,var3,var4,var5,var6,var7);
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		super.render(var1,var2,var3,var4);
		if (this.isMouseOver((double)(var2),(double)(var3))){
			JollyRogerElementsScrollPanel$Entry var5 = this.findEntry(var2,var3);
			if ((var5) != (null)){

			}
		}
	}
	protected void drawPanel(GuiGraphics var1, int var2, int var3, Tesselator var4, int var5, int var6) {
		int var7 = (var3) + (4);
		int var8 = 0;
		JollyRogerElementsScrollPanel$Entry var9 = this.findEntry(var5,var6);
		int var10 = 0;
		LOOP_1:
		do{
			if ((var10) < (this.rows)){
				int var11 = (this.left) + (16);
				Iterator var12 = this.entries.stream().skip((long)(var8)).limit((long)(this.elementsPerRow)).toList().iterator();
				do{
					if (var12.hasNext()){
						int var20 = 0;
						JollyRogerElementsScrollPanel$Entry var13 = ((JollyRogerElementsScrollPanel$Entry)(var12.next()));
						int var14 = 0;
						int var15 = 0;
						float var16 = 0.2F;
						int var17 = 16777215;
						if ((((java.lang.Object)(var13)).equals((Object)(var9))) && (!(var9.isLocked))){
							var14 = 6;
							var15 = 2;
							var16 = 0.25F;
							var17 = 7203230;
						}
						if ((var13.element) != (null)){
							TexturedRectUI var18 = new TexturedRectUI(var13.element.getTexture());
							var18.setScale(var16);
							var18.draw(var1.pose(),(float)(((var11) - (1)) - (var14)),(float)(((var7) - (4)) - (var15)));
							Component var19 = var13.element.getLocalizedName();
							var20 = (var11) - ((this.parent.getMinecraft().font.width((FormattedText)(var19))) / (2));
							RendererHelper.drawStringWithBorder(this.parent.getMinecraft().font,var1,var19,(var20) + (25),(var7) + (42),var17);
						}else{
							if (((java.lang.Object)(var13)).equals((Object)(REMOVE_ELEMENT))){
								float var18_1 = (var16) * (0.8F);
								TexturedRectUI var19_1 = new TexturedRectUI(ModResources.EXPIRED);
								var19_1.setScale(var18_1);
								var19_1.draw(var1.pose(),(float)(((var11) + (3)) - (var14)),(float)((var7) - (var15)));
								Component var20_1 = ModI18n.GUI_REMOVE;
								var20 = (var11) - ((this.parent.getMinecraft().font.width((FormattedText)(var20_1))) / (2));
								RendererHelper.drawStringWithBorder(this.parent.getMinecraft().font,var1,var20_1,(var20) + (24),(var7) + (42),var17);
							}
						}
						var11 = (var11) + (64);
						var8++;
						continue;
					}else{
						break;
					}
				} while (true);
				var7 = (var7) + (64);
				var10++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
	}
	private JollyRogerElementsScrollPanel$Entry findEntry(int var1, int var2) {
		if ((var1) < (this.left)){
			return null;
		}else{
			double var3 = (double)((var1) - (this.left));
			double var4 = (double)(((float)((var2) - (this.top))) + (this.scrollDistance));
			if ((!((var3) <= (0D))) && (!((var4) <= (0D)))){
				int var5 = (int)((var3) / (64D));
				int var6 = (int)((var4) / (64D));
				int var7 = (var5) + ((var6) * (this.elementsPerRow));
				if (((var6) <= ((this.entries.size()) / (this.elementsPerRow))) && (((var5) < (this.elementsPerRow)) && ((var7) < (this.entries.size())))){
					JollyRogerElementsScrollPanel$Entry var8 = ((JollyRogerElementsScrollPanel$Entry)(this.entries.get(var7)));
					if ((var8) != (null)){
						return var8;
					}else{
						return null;
					}
				}else{
					return null;
				}
			}else{
				return null;
			}
		}
	}
	public boolean mouseClicked(double var1, double var2, int var3) {
		JollyRogerElementsScrollPanel$Entry var4 = this.findEntry((int)(var1),(int)(var2));
		if ((var4) != (null)){
			if ((var4.element) != (null)){
				this.parent.updateJollyRoger(var4.element.copy());
			}else{
				if (((java.lang.Object)(var4)).equals((Object)(REMOVE_ELEMENT))){
					this.parent.updateJollyRoger((JollyRogerElement)(null));
				}else{
					return true;
				}
			}
			return true;
		}else{
			return super.mouseClicked(var1,var2,var3);
		}
	}
	public boolean mouseScrolled(double var1, double var2, double var3, double var4) {
		if ((this.getContentHeight()) < (this.height)){
			return false;
		}else{
			return super.mouseScrolled(var1,var2,var3,var4);
		}
	}
	protected int getContentHeight() {
		return (((this.entries.size()) / (this.elementsPerRow)) * (64)) + (64);
	}
	protected int getScrollAmount() {
		return 64;
	}
	public NarratableEntry.NarrationPriority narrationPriority() {
		return NarratableEntry.NarrationPriority.NONE;
	}
	public void updateNarration(NarrationElementOutput var1) {
	}
}