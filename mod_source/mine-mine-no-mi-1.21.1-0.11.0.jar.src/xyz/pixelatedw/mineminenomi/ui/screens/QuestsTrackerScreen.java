package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.EnchantmentNames;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.quest.CAbandonQuestPacket;
import xyz.pixelatedw.mineminenomi.ui.widgets.BigArrowButton;
import xyz.pixelatedw.mineminenomi.ui.widgets.PlankButton;

public class QuestsTrackerScreen extends Screen {
	// Fields
	private Player player;
	private int questIndex;
	private List<String> hiddenTexts;
	private Quest currentQuest;
	private Quest[] availableQuests;
	private List<Objective> availableObjectives;
	private int availableQuestsCount;

	public QuestsTrackerScreen(Player var1, Quest[] var2) {
		super((Component)(Component.empty()));
		this.questIndex = 0;
		this.hiddenTexts = new ArrayList();
		this.currentQuest = null;
		this.availableObjectives = new ArrayList();
		this.player = var1;
		this.questIndex = 0;
		this.availableQuests = var2;
		this.availableQuestsCount = this.availableQuests.length;
		this.currentQuest = this.availableQuests[0];
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		int var14 = 0;
		this.renderBackground(var1,var2,var3,var4);
		RenderSystem.setShaderColor(1F,1F,1F,1F);
		int var5 = (this.width) / (2);
		int var6 = (this.height) / (2);
		var1.pose().pushPose();
		float var7 = 1.1F;
		var1.pose().translate((float)((var5) - (35)),(float)((var6) + (10)),0F);
		var1.pose().translate(256F,256F,0F);
		var1.pose().scale((var7) * (1.5F),(var7) * (1.4F),0F);
		var1.pose().translate(-256F,-256F,0F);
		var1.blit(ModResources.BLANK,0,0,0,0,256,256);
		var1.pose().translate(-30F,50F,0F);
		var1.pose().translate(256F,256F,0F);
		var1.pose().scale((var7) * (0.7F),(var7) * (0.9F),0F);
		var1.pose().translate(-256F,-256F,0F);
		var1.pose().popPose();
		String var8 = ((this.currentQuest) != (null)) ? (this.currentQuest.getCore().getTitle().getString()) : ("None");
		double var9 = ((this.currentQuest) != (null)) ? ((double)((this.currentQuest.getProgress()) * (100F))) : (-1D);
		if ((this.currentQuest) != (null)){
			var1.pose().pushPose();
			var1.pose().translate((float)((var5) + (150)),(float)((var6) - (110)),0F);
			int var10 = (this.questIndex) + (1);
			int var11 = this.availableQuestsCount;
			String var12 = "" + var10 + "/" + var11;
			String var13 = var12;
			RendererHelper.drawStringWithBorder(this.font,var1,var13,0,0,-1);
			var1.pose().popPose();
			var1.pose().pushPose();
			var7 = 1.4F;
			var1.pose().translate((float)((var5) + (100)),(float)((var6) + (10)),0F);
			var1.pose().translate(256F,256F,0F);
			var1.pose().scale(var7,var7,0F);
			var1.pose().translate(-256F,-256F,0F);
			RendererHelper.drawStringWithBorder(this.font,var1,var8,(-(this.font.width(var8))) / (2),0,-1);
			var1.pose().popPose();
			if ((var9) != (-1D)){
				var14 = 16777215;
				if (this.currentQuest.isComplete()){
					var14 = 65365;
				}
				Object[] var15 = new Object[]{Double.valueOf(var9)};
				String var16 = String.valueOf((Object)(ChatFormatting.BOLD));
				String var17 = ModI18n.GUI_QUEST_PROGRESS.getString();
				String var18 = String.format("%.1f",var15);
				String var19 = var16 + var17 + " : " + var18 + "%";
				String var20 = var19;
				RendererHelper.drawStringWithBorder(this.font,var1,var20,(var5) - (140),(var6) - (65),var14);
			}
			var1.pose().pushPose();
			var14 = -20;
			Iterator var15_1 = this.availableObjectives.iterator();
			LOOP_1:
			do{
				if (var15_1.hasNext()){
					Objective var16_1 = ((Objective)(var15_1.next()));
					String var17_1 = var16_1.getLocalizedTitle().getString();
					double var18_1 = (double)(((var16_1.getProgress()) / (var16_1.getMaxProgress())) * (100F));
					String var19_1 = "";
					var14 = (var14) + (20);
					int var20_1 = 16777215;
					if (var16_1.isComplete()){
						var20_1 = 65280;
					}
					if (var16_1.isLocked()){
						var20_1 = 5263440;
					}else{
						String var21 = String.format("%.1f",new Object[]{Double.valueOf(var18_1)});
						String var22 = "- " + var21 + "%";
						var19_1 = var22;
					}
					if ((var16_1.isLocked()) && (var16_1.isHidden())){
						RendererHelper.drawStringWithBorder(this.font,var1,"• ",(var5) - (130),((var6) - (45)) + (var14),var20_1);
						List var21_1 = ((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.availableObjectives.stream())).filter((Predicate)(((java.util.function.Predicate)(((Predicate<Objective>)(Objective::isHidden)))))))).collect((Collector)(Collectors.toList()))));
						if ((var21_1.size()) > (0)){
							RendererHelper.drawStringWithBorder(this.font,var1,((String)(this.hiddenTexts.get(var21_1.indexOf(var16_1)))),(var5) - (123),((var6) - (45)) + (var14),var20_1);
							continue;
						}else{
							continue;
						}
					}else{
						String var21_2 = (var16_1.isOptional()) ? ("(Optional) ") : ("");
						String var22_1 = var21_2;
						String var23 = var17_1;
						String var24 = var19_1;
						String var25 = "• " + var22_1 + var23 + " " + var24;
						var17_1 = var25;
						List var26 = this.font.split((FormattedText)(Component.literal(var17_1)),280);
						int var27 = 0;
						do{
							if ((var27) < (var26.size())){
								RendererHelper.drawStringWithBorder(this.font,var1,((FormattedCharSequence)(var26.get(var27))),(var5) - (130),(((var6) - (45)) + (var14)) + ((var27) * (12)),var20_1);
								var27++;
								continue;
							}else{
								break;
							}
						} while (true);
						var14 = (var14) + ((var26.size()) * (8));
						continue LOOP_1;
					}
				}else{
					break;
				}
			} while (true);
			if ((this.availableQuestsCount) <= (0)){
				RendererHelper.drawStringWithBorder(this.font,var1,ModI18n.TRAINER_NO_OBJECTIVES_LEFT,(var5) - (120),((var6) - (20)) + (var14),-1);
			}
			var1.pose().popPose();
		}
		Iterator var10_1 = this.renderables.iterator();
		do{
			if (var10_1.hasNext()){
				((Renderable)(var10_1.next())).render(var1,var2,var3,var4);
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void init() {
		this.renderables.clear();
		this.updateSelectedQuest();
		int var1 = ((this.width) - (256)) / (2);
		int var2 = ((this.height) - (256)) / (2);
		QuestsTrackerScreen var3 = this;
		Button.OnPress var4 = (l0) -> {
			this.questIndex = ((this.questIndex) + (1)) % (this.availableQuests.length);
			this.updateSelectedQuest();
		};
		BigArrowButton var5 = new BigArrowButton((var1) + (285),(var2) + (70),24,100,var4);
		if ((this.availableQuestsCount) <= (1)){
			var5.visible = false;
		}
		var5.setFlipped();
		this.addRenderableWidget((GuiEventListener)(var5));
		QuestsTrackerScreen var6 = this;
		Button.OnPress var7 = (l0) -> {
			this.questIndex = Math.floorMod((this.questIndex) - (1),this.availableQuests.length);
			this.updateSelectedQuest();
		};
		BigArrowButton var8 = new BigArrowButton((var1) - (55),(var2) + (70),24,100,var7);
		if ((this.availableQuestsCount) <= (1)){
			var8.visible = false;
		}
		this.addRenderableWidget((GuiEventListener)(var8));
		QuestsTrackerScreen var9 = this;
		Button.OnPress var10 = (l0) -> {
			this.player.closeContainer();
			if ((this.currentQuest) != (null)){
				ModNetwork.sendToServer(new CAbandonQuestPacket(this.currentQuest.getCore()));
			}
		};
		this.addRenderableWidget((GuiEventListener)(new PlankButton((var1) - (40),(var2) + (210),80,30,ModI18n.GUI_QUEST_ABANDON,var10)));
	}
	public void updateSelectedQuest() {
		if ((this.questIndex) < (0)){
			return;
		}else{
			this.currentQuest = this.availableQuests[this.questIndex];
			if ((this.currentQuest) != (null)){
				Iterator var1 = this.currentQuest.getObjectives().iterator();
				LOOP_1:
				do{
					if (var1.hasNext()){
						Objective var2 = ((Objective)(var1.next()));
						if (var2.isHidden()){
							StringBuilder var3 = new StringBuilder();
							var3.append("§k");
							String var4 = var2.getLocalizedTitle().getString();
							int var5 = 0;
							do{
								if ((var5) < ((var4.length()) * (2))){
									String var6 = EnchantmentNames.getInstance().getRandomName(this.font,(var4.length()) * (2)).getString();
									var3.append(var6);
									var5 = (var5) + (var6.length());
									continue;
								}else{
									break;
								}
							} while (true);
							var3.append("§r");
							this.hiddenTexts.add(var3.toString());
							continue LOOP_1;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				this.availableObjectives = (List) (((List)(((java.util.stream.Stream)(this.currentQuest.getObjectives().stream().limit(5L))).collect((Collector)(Collectors.toList())))));
			}
			return;
		}
	}
	public boolean isPauseScreen() {
		return true;
	}
}