package xyz.pixelatedw.mineminenomi.ui.panel;

import com.mojang.blaze3d.vertex.Tesselator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.entities.ITrainer;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.quest.CUpdateQuestStatePacket;
import xyz.pixelatedw.mineminenomi.ui.screens.TrainerTrialsListScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.dialogues.TrainerDialogueScreen$QuestEntry;
import xyz.pixelatedw.mineminenomi.ui.widgets.ScrollButton;

public class TrainerTrialsScrollPanel extends ScrollPanel {
	// Fields
	private static final int ENTRY_HEIGHT = 75;
	private final TrainerTrialsListScreen parent;
	private final List<TrainerDialogueScreen$QuestEntry> entries;
	private ScrollButton[] listButtons;

	public TrainerTrialsScrollPanel(TrainerTrialsListScreen var1, List<TrainerDialogueScreen$QuestEntry> var2) {
		super(var1.getMinecraft(),(var1.width) / (3),(var1.height) - (80),20,25);
		this.parent = var1;
		this.entries = var2;
		this.updateButtonsList();
	}
	protected void drawBackground(GuiGraphics var1, Tesselator var2, float var3) {
	}
	protected void drawPanel(GuiGraphics var1, int var2, int var3, Tesselator var4, int var5, int var6) {
		Minecraft var7 = this.parent.getMinecraft();
		if ((var7) == (null)){
			return;
		}else{
			QuestData var8 = ((QuestData)(((java.util.Optional)(QuestData.get((Player)(var7.player)))).orElse((Object)(null))));
			if ((var8) == (null)){
				return;
			}else{
				int var9 = var3;
				int var10 = 32;
				int var11 = 0;
				int var12 = 0;
				do{
					if ((var12) < (this.entries.size())){
						TrainerDialogueScreen$QuestEntry var13 = ((TrainerDialogueScreen$QuestEntry)(this.entries.get(var12)));
						if ((var13) == (null)){

						}else{
							int var14 = -1;
							ScrollButton var15 = this.listButtons[var12];
							var15.active = (var13.isCompleted()) || ((!(var13.isLocked())) && (!(var8.isQuestOnCooldown(var13.getQuestId()))));
							if (var13.isCompleted()){
								var14 = -16711936;
							}
							var15.setPosition(var10,(var9) + (var11));
							var15.setFGColor(var14);
							var15.render(var1,var5,var6,0F);
							if (var13.getQuestId().isRepeatable()){
								int var16 = (int)(Math.round(Math.ceil(Math.max(0D,((var8.getQuestCooldown(var13.getQuestId())) - (((double)(System.currentTimeMillis())) / (50D))) / (20D)))));
								if ((var16) > (0)){
									int var17 = var16;
									String var18 = ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS.getString();
									String var19 = "" + var17 + " " + var18;
									String var20 = var19;
									int var21 = 4;
									int var22 = ((((var15.getX()) + (var15.getWidth())) - (var21)) - (var7.font.width(var20))) - (4);
									int var23 = ((var15.getY()) + (12)) + (var21);
									int var24 = (((long)(var16)) > (0L)) ? (-1) : (-5592406);
									var1.drawString(var7.font,var20,var22,var23,var24,true);
								}
							}
							var11 = (var11) + (75);
						}
						var12++;
						continue;
					}else{
						break;
					}
				} while (true);
				return;
			}
		}
	}
	public boolean mouseClicked(double var1, double var2, int var3) {
		ScrollButton[] var4 = this.listButtons;
		int var5 = var4.length;
		int var6 = 0;
		do{
			if ((var6) < (var5)){
				ScrollButton var7 = var4[var6];
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
		return true;
	}
	@Nullable()
	private TrainerDialogueScreen$QuestEntry findQuestEntry(int var1, int var2) {
		double var3 = (double)(((float)((var2) - (this.top))) + (this.scrollDistance));
		int var4 = (((var1) >= (this.left)) && (((var2) >= (this.top)) && (((var1) < (((this.left) + (this.width)) - (5))) && ((var2) < ((this.top) + (this.height)))))) ? (1) : (0);
		if ((!((var3) <= (0D))) && ((var4) != (0))){
			int var5 = (int)((var3) / (206.25D));
			if ((var5) >= (this.entries.size())){
				return null;
			}else{
				TrainerDialogueScreen$QuestEntry var6 = ((TrainerDialogueScreen$QuestEntry)(this.entries.get(var5)));
				if ((var6) != (null)){
					return var6;
				}else{
					return null;
				}
			}
		}else{
			return null;
		}
	}
	private void updateButtonsList() {
		TrainerDialogueScreen$QuestEntry var4 = null;
		LivingEntity var5 = null;
		if ((this.parent) == (null)){
			return;
		}else{
			LivingEntity var1 = this.parent.getTrainer();
			if (((var1) != (null)) && (var1 instanceof ITrainer)){
				this.listButtons = new ScrollButton[this.entries.size()];
				int var2 = 0;
				do{
					if ((var2) < (this.entries.size())){
						TrainerDialogueScreen$QuestEntry var3 = ((TrainerDialogueScreen$QuestEntry)(this.entries.get(var2)));
						if ((var3) != (null)){
							var4 = var3;
							var5 = var1;
							final TrainerDialogueScreen$QuestEntry var4_f1 = var4;
							final LivingEntity var5_f2 = var5;
							Button.OnPress var6 = (l0) -> {
			if (!(var4_f1.isLocked())){
				ModNetwork.sendToServer(new CUpdateQuestStatePacket(var4_f1.getQuestId(),var5_f2));
			}
		};
							ScrollButton var7 = new ScrollButton(0,0,200,75,var3.getQuestId().getTitle(),var6);
							this.listButtons[var2] = var7;
						}
						var2++;
						continue;
					}else{
						break;
					}
				} while (true);
				return;
			}else{
				return;
			}
		}
	}
	protected int getContentHeight() {
		return ((this.entries.size()) * (75)) - (2);
	}
	public void removeEntry(QuestId<?> var1) {
		this.entries.removeIf((Predicate)(((Predicate<TrainerDialogueScreen$QuestEntry>)((l0) -> {
			return ((java.lang.Object)(l0.getQuestId())).equals((Object)(var1));
		}))));
		this.updateButtonsList();
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