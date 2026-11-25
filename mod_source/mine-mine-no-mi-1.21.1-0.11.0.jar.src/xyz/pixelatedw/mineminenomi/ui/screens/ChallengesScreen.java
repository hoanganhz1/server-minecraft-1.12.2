package xyz.pixelatedw.mineminenomi.ui.screens;

import com.google.common.base.Strings;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;
import xyz.pixelatedw.mineminenomi.api.ui.IEventReceiverScreen;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CDisbandChallengeGroupPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CFetchPossibleInvitesPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CStartChallengePacket;
import xyz.pixelatedw.mineminenomi.ui.events.ChallengesScreenEvent;
import xyz.pixelatedw.mineminenomi.ui.events.ChallengesScreenEvent$PossibleInvites;
import xyz.pixelatedw.mineminenomi.ui.panel.ChallengeGroupSelectorPanel;
import xyz.pixelatedw.mineminenomi.ui.panel.ChallengeInvitesPanel;
import xyz.pixelatedw.mineminenomi.ui.panel.ChallengesListScrollPanel;
import xyz.pixelatedw.mineminenomi.ui.widgets.ArrowButton;
import xyz.pixelatedw.mineminenomi.ui.widgets.GroupMemberButton;
import xyz.pixelatedw.mineminenomi.ui.widgets.PlankButton;

public class ChallengesScreen extends Screen implements IEventReceiverScreen<ChallengesScreenEvent> {
	// Fields
	private static final float MAX_ANIMATION_TICKS = 20F;
	private final LocalPlayer player;
	private final List<Challenge> challenges;
	private final List<ChallengeInvitation> invites;
	private final Map<String, List<Challenge>> challengesMap;
	private final List<String> categories;
	private List<Challenge> displayedChallenges;
	@Nullable() private Challenge selectedChallenge;
	private String selectedChallengeStars;
	private String selectedCategory;
	private int selectedCategoryId;
	private Component selectedCategoryLocalizedName;
	@Nullable() private ResourceLocation selectedCategoryIcon;
	@Nullable() private LivingEntity[] targets;
	private int targetsCount;
	private LivingEntity[] group;
	private GroupMemberButton[] groupMemberButtons;
	private GroupMemberButton groupOwnerButton;
	private ChallengesListScrollPanel challengesListPanel;
	private ChallengeGroupSelectorPanel challengesGroupSelectorPanel;
	private ChallengeInvitesPanel challengeInvitesPanel;
	private LivingEntity dummyBust;
	private int selectedMemberSlot;
	private Checkbox freeCheckbox;
	private boolean startedChallenge;
	private int setupStep;
	private PlankButton startChallengeButton;
	private ArrowButton nextStepButton;
	private ArrowButton prevStepButton;
	private float renderTick;
	private boolean runInfoPanelAnimation;
	private boolean runCategoryChangeAnimation;
	private float infoPanelAnimationTick;
	private float categoryChangeAnimationTick;

	public ChallengesScreen(List<Challenge> var1, List<ChallengeInvitation> var2) {
		super((Component)(Component.empty()));
		this.categories = new ArrayList();
		this.targets = new LivingEntity[4];
		this.group = new LivingEntity[3];
		this.groupMemberButtons = new GroupMemberButton[3];
		this.startedChallenge = false;
		this.infoPanelAnimationTick = 0F;
		this.categoryChangeAnimationTick = 0F;
		this.player = Minecraft.getInstance().player;
		this.challenges = var1;
		this.invites = var2;
		Comparator<? super Challenge> var3 = (l0, l1) -> {
			return (l1.getCore().getOrder()) - (l0.getCore().getOrder());
		};
		((java.util.List)(this.challenges)).sort((Comparator)(var3));
		this.challengesMap = (Map) (((Map)(((java.util.stream.Stream)(var1.stream())).collect((Collector)(Collectors.groupingBy((Function)(((Function<Challenge, String>)(Challenge::getCategory)))))))));
		this.categories.add("");
		((java.util.List)(this.categories)).addAll((Collection)(this.challengesMap.keySet()));
		this.selectedCategoryId = 0;
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		this.renderBackground(var1,var2,var3,var4);
		int var5 = ((this.width) - (256)) / (2);
		int var6 = ((this.height) - (256)) / (2);
		if ((this.runCategoryChangeAnimation) && ((this.categoryChangeAnimationTick) < (20F))){
			this.categoryChangeAnimationTick = (this.categoryChangeAnimationTick) + ((2.2F) * (this.minecraft.getTimer().getGameTimeDeltaTicks()));
			this.categoryChangeAnimationTick = Math.min(this.categoryChangeAnimationTick,20F);
		}
		if ((this.runInfoPanelAnimation) && ((this.infoPanelAnimationTick) < (20F))){
			this.infoPanelAnimationTick = (this.infoPanelAnimationTick) + ((2F) * (this.minecraft.getTimer().getGameTimeDeltaTicks()));
			this.infoPanelAnimationTick = Math.min(this.infoPanelAnimationTick,20F);
		}
		float var7 = EasingFunctionHelper.easeOutBack(Float.valueOf((this.infoPanelAnimationTick) / (20F)));
		float var8 = EasingFunctionHelper.easeOutCubic(Float.valueOf((this.categoryChangeAnimationTick) / (20F)));
		var1.pose().pushPose();
		var1.pose().translate((float)((var5) - (210)),((float)((var6) - (122))) - ((var8) * (4F)),0F);
		var1.pose().translate(128F,128F,0F);
		RendererHelper.drawStringWithBorder(this.font,var1,this.selectedCategoryLocalizedName,40,15,-1);
		if ((this.selectedCategoryIcon) != (null)){
			var1.pose().scale(0.15F,0.15F,0F);
			var1.blit(this.selectedCategoryIcon,0,0,0,0,256,256);
		}
		var1.pose().translate(-128F,-128F,0F);
		var1.pose().popPose();
		var6 = (var6) + (25);
		if (((this.selectedChallenge) != (null)) && ((var7) > (0F))){
			int var11 = 0;
			var7 = (var7) * (320F);
			var1.pose().pushPose();
			var1.pose().translate(((float)((var5) + (115))) - (var7),(float)((var6) - (246)),0F);
			var1.pose().translate(256F,256F,0F);
			var1.pose().scale(1.25F,1.25F,0F);
			var1.blit(ModResources.BOARD,0,0,0,0,256,256);
			var1.blit(ModResources.BLANK_NEW,0,0,0,0,256,256);
			var1.pose().translate(-256F,-256F,0F);
			var1.pose().popPose();
			int var9 = ((var5) + (450)) - ((int)(var7));
			if ((this.setupStep) == (0)){
				var1.pose().pushPose();
				if ((this.targetsCount) == (1)){
					if ((this.targets[0]) != (null)){
						RendererHelper.drawLivingBust(this.targets[0],var1,(var9) + (5),(var6) + (200),40,-30,10,false);
					}
				}else{
					if ((this.targetsCount) == (2)){
						if ((this.targets[0]) != (null)){
							RendererHelper.drawLivingBust(this.targets[0],var1,(var9) - (25),(var6) + (180),35,-30,10,false);
						}
						if ((this.targets[1]) != (null)){
							RendererHelper.drawLivingBust(this.targets[1],var1,(var9) + (25),(var6) + (180),35,-30,10,false);
						}
					}else{
						if ((this.targets[0]) != (null)){
							RendererHelper.drawLivingBust(this.targets[0],var1,(var9) - (25),(var6) + (150),30,-30,10,false);
						}
						if ((this.targets[1]) != (null)){
							RendererHelper.drawLivingBust(this.targets[1],var1,(var9) + (25),(var6) + (150),30,-30,10,false);
						}
						if ((this.targets[2]) != (null)){
							RendererHelper.drawLivingBust(this.targets[2],var1,(var9) - (25),(var6) + (200),30,-30,10,false);
						}
						if ((this.targets[3]) != (null)){
							RendererHelper.drawLivingBust(this.targets[3],var1,(var9) + (25),(var6) + (200),30,-30,10,false);
						}
					}
				}
				var1.pose().popPose();
				String var10 = "";
				var11 = this.selectedChallenge.getBonusPoints();
				if ((var11) > (0)){
					int var12 = var11;
					String var13 = " +" + var12;
					var10 = var13;
				}
				MutableComponent var12_1 = this.selectedChallenge.getCore().getLocalizedTitle().copy().append((Component)(Component.literal(var10).setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW))));
				RendererHelper.drawStringWithBorder(this.font,var1,(Component)(var12_1),(var9) - (30),(var6) + (35),-1);
				RendererHelper.drawStringWithBorder(this.font,var1,this.selectedChallenge.getCore().getLocalizedObjective(),(var9) - (20),(var6) + (50),-1);
				String var13_1 = WyHelper.formatTimeMMSS((long)((this.selectedChallenge.getCore().getTimeLimit()) * (60)));
				String var14 = ModI18n.GUI_TIME_LIMIT.getString();
				String var15 = var13_1;
				String var16 = var14 + ": " + var15;
				String var17 = var16;
				RendererHelper.drawStringWithBorder(this.font,var1,var17,(var9) + (60),(var6) + (90),-1);
				String var18 = WyHelper.formatTimeMMSS((long)(this.selectedChallenge.getBestTimeTick()));
				String var19 = ModI18n.GUI_PERSONAL_BEST.getString();
				String var20 = var18;
				String var21 = var19 + ": " + var20;
				String var22 = var21;
				RendererHelper.drawStringWithBorder(this.font,var1,var22,(var9) + (60),(var6) + (105),-1);
				String var23 = ModI18n.GUI_DIFFICULTY.getString();
				String var24 = this.selectedChallengeStars;
				String var25 = var23 + ": " + var24;
				String var26 = var25;
				RendererHelper.drawStringWithBorder(this.font,var1,var26,(var9) + (60),(var6) + (120),-1);
				if ((this.selectedChallenge.getCore().getDifficulty()) != (ChallengeDifficulty.STANDARD)){
					RendererHelper.drawStringWithBorder(this.font,var1,ModI18n.GUI_RESTRICTIONS,(var9) + (60),(var6) + (135),-1);
					RendererHelper.drawStringWithBorder(this.font,var1,"  - §cNo Food§r",(var9) + (60),(var6) + (145),-1);
					RendererHelper.drawStringWithBorder(this.font,var1,"  - §cNo Potions§r",(var9) + (60),(var6) + (155),-1);
				}
			}else{
				if ((this.setupStep) == (1)){
					this.groupOwnerButton.setX((var9) - (30));
					this.groupOwnerButton.setY((var6) + (40));
					this.groupMemberButtons[0].setX((var9) + (40));
					this.groupMemberButtons[0].setY((var6) + (40));
					this.groupMemberButtons[1].setX((var9) - (30));
					this.groupMemberButtons[1].setY((var6) + (95));
					this.groupMemberButtons[2].setX((var9) + (40));
					this.groupMemberButtons[2].setY((var6) + (95));
					GroupMemberButton[] var10_1 = this.groupMemberButtons;
					var11 = var10_1.length;
					int var12_2 = 0;
					do{
						if ((var12_2) < (var11)){
							GroupMemberButton var13_2 = var10_1[var12_2];
							if ((var13_2) != (null)){
								var13_2.render(var1,var2,var3,var4);
							}
							var12_2++;
							continue;
						}else{
							break;
						}
					} while (true);
					this.groupOwnerButton.render(var1,var2,var3,var4);
					this.freeCheckbox.setX((var9) - (40));
					this.freeCheckbox.setY((var6) + (160));
				}else{
					if (((this.setupStep) == (2)) && (((this.selectedMemberSlot) >= (0)) && ((this.challengesGroupSelectorPanel) != (null)))){
						this.challengesGroupSelectorPanel.render(var1,var2,var3,var4);
					}
				}
			}
		}else{
			if (((this.selectedChallenge) == (null)) && ((var7) > (0F))){
				var7 = (var7) * (320F);
				var1.pose().pushPose();
				var1.pose().translate(((float)((var5) + (115))) - (var7),(float)((var6) - (246)),0F);
				var1.pose().translate(256F,256F,0F);
				var1.pose().scale(1.25F,1.25F,0F);
				var1.blit(ModResources.BOARD,0,0,0,0,256,256);
				var1.blit(ModResources.BLANK_NEW,0,0,0,0,256,256);
				var1.pose().translate(-256F,-256F,0F);
				var1.pose().popPose();
				int var9_1 = ((var5) + (450)) - ((int)(var7));
				if ((this.challengeInvitesPanel) != (null)){
					this.challengeInvitesPanel.render(var1,var2,var3,var4);
				}
			}
		}
		Iterator var9_2 = this.renderables.iterator();
		do{
			if (var9_2.hasNext()){
				((Renderable)(var9_2.next())).render(var1,var2,var3,var4);
				continue;
			}else{
				break;
			}
		} while (true);
		this.renderTick = (this.renderTick) + ((1F) * (this.minecraft.getTimer().getGameTimeDeltaTicks()));
	}
	public void init() {
		int var1 = ((this.width) - (256)) / (2);
		int var2 = ((this.height) - (256)) / (2);
		this.renderTick = 0F;
		this.clearWidgets();
		this.dummyBust = ((LivingEntity)(EntityType.VILLAGER.create((Level)(this.minecraft.level))));
		ChallengesScreen var3 = this;
		Button.OnPress var4 = (l0) -> {
			if (((this.selectedCategoryId) - (1)) >= (0)){
				this.selectedCategoryId = (this.selectedCategoryId) - (1);
				this.updateSelectedCategory();
			}else{
				this.selectedCategoryId = (this.categories.size()) - (1);
				this.updateSelectedCategory();
			}
		};
		ArrowButton var5 = new ArrowButton((var1) - (110),(var2) + (10),30,20,var4);
		var5.setWoodTexture();
		this.addRenderableWidget((GuiEventListener)(var5));
		ChallengesScreen var6 = this;
		Button.OnPress var7 = (l0) -> {
			if (((this.selectedCategoryId) + (1)) < (this.categories.size())){
				this.selectedCategoryId = (this.selectedCategoryId) + (1);
				this.updateSelectedCategory();
			}else{
				this.selectedCategoryId = 0;
				this.updateSelectedCategory();
			}
		};
		ArrowButton var8 = new ArrowButton((var1) + (70),(var2) + (10),30,20,var7);
		var8.setWoodTexture();
		var8.setFlipped();
		this.addRenderableWidget((GuiEventListener)(var8));
		if ((this.invites.size()) > (0)){
			Object[] var9 = new Object[]{Integer.valueOf(this.invites.size())};
			MutableComponent var10 = Component.translatable(ModI18n.GUI_INVITES_AMOUNT,var9);
			ChallengesScreen var11 = this;
			Button.OnPress var12 = (l0) -> {
			this.showInvitesScreen();
		};
			this.addRenderableWidget((GuiEventListener)(new PlankButton((var1) + (120),(var2) + (8),100,25,(Component)(var10),var12)));
		}
		ChallengesScreen var9_1 = this;
		Button.OnPress var10_1 = (l0) -> {
			if ((this.setupStep) == (1)){
				this.showChallengeInfoStep();
			}else{
				if ((this.setupStep) == (2)){
					this.showGroupStep();
				}else{
					return;
				}
			}
		};
		this.prevStepButton = new ArrowButton((var1) + (90),(var2) + (220),50,30,var10_1);
		this.prevStepButton.setWoodTexture();
		this.prevStepButton.visible = false;
		this.addRenderableWidget((GuiEventListener)(this.prevStepButton));
		ChallengesScreen var11_1 = this;
		Button.OnPress var12_1 = (l0) -> {
			if ((this.setupStep) == (0)){
				this.showGroupStep();
			}
		};
		this.nextStepButton = new ArrowButton((var1) + (170),(var2) + (220),50,30,var12_1);
		this.nextStepButton.setWoodTexture();
		this.nextStepButton.setFlipped();
		this.nextStepButton.visible = false;
		this.addRenderableWidget((GuiEventListener)(this.nextStepButton));
		ChallengesScreen var13 = this;
		Button.OnPress var14 = (l0) -> {
			this.startedChallenge = true;
			int lv7_2 = (((this.freeCheckbox) != (null)) && (this.freeCheckbox.selected())) ? (1) : (0);
			ModNetwork.sendToServer(new CStartChallengePacket(this.selectedChallenge.getCore().getRegistryKey(),(lv7_2) != (0)));
			this.minecraft.setScreen((Screen)(null));
		};
		this.startChallengeButton = new PlankButton((var1) + (250),(var2) + (220),70,30,ModI18n.GUI_START,var14);
		this.startChallengeButton.visible = false;
		this.addRenderableWidget((GuiEventListener)(this.startChallengeButton));
		this.freeCheckbox = Checkbox.builder(ModI18n.GUI_TRAINING,this.font).pos((var1) + (90),(var2) + (185)).maxWidth(60).selected(false).build();
		Tooltip var15 = Tooltip.create(ModI18nChallenges.MESSAGE_TRAINING_INFO);
		this.freeCheckbox.setTooltip(var15);
		this.freeCheckbox.visible = false;
		this.addRenderableWidget((GuiEventListener)(this.freeCheckbox));
		this.updateSelectedCategory();
	}
	private void showInvitesScreen() {
		this.startInfoPanelAnimation();
		this.selectedChallenge = null;
		this.nextStepButton.visible = false;
		this.prevStepButton.visible = false;
		this.freeCheckbox.visible = false;
		this.startChallengeButton.visible = false;
		this.removeGroupMemberElements();
		this.removeWidget((GuiEventListener)(this.challengeInvitesPanel));
		this.challengeInvitesPanel = new ChallengeInvitesPanel(this,(Player)(this.player),this.invites);
		this.addRenderableWidget((GuiEventListener)(this.challengeInvitesPanel));
	}
	private void showChallengeInfoStep() {
		this.startInfoPanelAnimation();
		this.setupStep = 0;
		this.startChallengeButton.visible = false;
		this.nextStepButton.visible = true;
		this.prevStepButton.visible = false;
		this.freeCheckbox.visible = false;
		this.removeGroupMemberElements();
		this.removeWidget((GuiEventListener)(this.challengeInvitesPanel));
		this.selectedMemberSlot = -1;
	}
	public void showGroupStep() {
		this.startInfoPanelAnimation();
		this.setupStep = 1;
		this.startChallengeButton.visible = true;
		this.nextStepButton.visible = false;
		this.prevStepButton.visible = true;
		this.freeCheckbox.visible = true;
		this.removeWidget((GuiEventListener)(this.challengesGroupSelectorPanel));
		this.createGroupButtons();
	}
	private void showGroupSelectionStep() {
		this.startInfoPanelAnimation();
		this.setupStep = 2;
		this.startChallengeButton.visible = false;
		this.nextStepButton.visible = false;
		this.prevStepButton.visible = true;
		this.freeCheckbox.visible = false;
		this.removeGroupMemberElements();
	}
	private void createGroupButtons() {
		int var1 = ((this.width) - (256)) / (2);
		int var2 = ((this.height) - (256)) / (2);
		this.removeGroupMemberElements();
		this.selectedMemberSlot = -1;
		Button.OnPress var3 = (l0) -> {
		};
		this.groupOwnerButton = new GroupMemberButton((var1) + (100),(var2) + (65),45,45,(LivingEntity)(this.player),this.dummyBust,var3);
		this.groupOwnerButton.active = false;
		this.addRenderableWidget((GuiEventListener)(this.groupOwnerButton));
		Button.OnPress var4 = (l0) -> {
			ModNetwork.sendToServer(new CFetchPossibleInvitesPacket(0));
		};
		GroupMemberButton var5 = new GroupMemberButton((var1) + (170),(var2) + (65),45,45,this.group[0],this.dummyBust,var4);
		this.groupMemberButtons[0] = var5;
		this.addRenderableWidget((GuiEventListener)(var5));
		Button.OnPress var6 = (l0) -> {
			ModNetwork.sendToServer(new CFetchPossibleInvitesPacket(1));
		};
		GroupMemberButton var7 = new GroupMemberButton((var1) + (100),(var2) + (120),45,45,this.group[1],this.dummyBust,var6);
		this.groupMemberButtons[1] = var7;
		this.addRenderableWidget((GuiEventListener)(var7));
		Button.OnPress var8 = (l0) -> {
			ModNetwork.sendToServer(new CFetchPossibleInvitesPacket(2));
		};
		GroupMemberButton var9 = new GroupMemberButton((var1) + (170),(var2) + (120),45,45,this.group[2],this.dummyBust,var8);
		this.groupMemberButtons[2] = var9;
		this.addRenderableWidget((GuiEventListener)(var9));
		this.freeCheckbox.visible = (this.selectedChallenge.getCore().getDifficulty()) != (ChallengeDifficulty.STANDARD);
	}
	public void removeGroupMemberElements() {
		this.removeWidget((GuiEventListener)(this.groupOwnerButton));
		GroupMemberButton[] var1 = this.groupMemberButtons;
		int var2 = var1.length;
		int var3 = 0;
		do{
			if ((var3) < (var2)){
				this.removeWidget((GuiEventListener)(var1[var3]));
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void setGroupMember(int var1, LivingEntity var2) {
		this.group[var1] = var2;
		this.showGroupStep();
	}
	public void setGroupMember(LivingEntity var1) {
		this.group[this.selectedMemberSlot] = var1;
		this.showGroupStep();
	}
	public int getSelectedMemberSlot() {
		return this.selectedMemberSlot;
	}
	public LivingEntity[] getGroup() {
		return this.group;
	}
	public void handleEvent(ChallengesScreenEvent var1) {
		if ((var1.getPossibleInvites()) != (null)){
			this.openNearbyGroupSelector(var1.getPossibleInvites().getSlot(),var1.getPossibleInvites().getIds());
		}
	}
	public ChallengesScreenEvent decode(HolderLookup.Provider var1, CompoundTag var2) {
		ChallengesScreenEvent var3 = new ChallengesScreenEvent();
		var3.deserializeNBT(var1,var2);
		return var3;
	}
	private void openNearbyGroupSelector(int var1, int[] var2) {
		this.showGroupSelectionStep();
		this.selectedMemberSlot = var1;
		this.removeWidget((GuiEventListener)(this.challengesGroupSelectorPanel));
		this.challengesGroupSelectorPanel = new ChallengeGroupSelectorPanel(this,(Player)(this.player),var2);
		this.addRenderableWidget((GuiEventListener)(this.challengesGroupSelectorPanel));
	}
	private String createDifficultyStars() {
		if ((this.selectedChallenge) == (null)){
			return "";
		}else{
			int var1 = this.selectedChallenge.getCore().getDifficultyStars();
			boolean var2 = ClientConfig.isSimpleDisplaysEnabled();
			StringBuilder var3 = new StringBuilder();
			if (var2){
				int var4 = (var1) / (10);
				ChatFormatting var5 = ChatFormatting.RESET;
				if ((var4) == (0)){
					var5 = ChatFormatting.YELLOW;
				}else{
					if ((var4) == (1)){
						var5 = ChatFormatting.RED;
					}else{
						if ((var4) == (2)){
							var5 = ChatFormatting.DARK_PURPLE;
						}
					}
				}
				String var6 = var5.toString();
				int var7 = var1;
				int var8 = ModValues.MAX_DIFFICULTY;
				String var9 = var6 + var7 + " / " + var8;
				var3.append(var9);
			}else{
				String[] var4_1 = new String[]{"☆","☆","☆","☆","☆","☆","☆","☆","☆","☆"};
				String[] var5_1 = var4_1;
				int var6_1 = ((var1) - (1)) / (var5_1.length);
				int var7_1 = 0;
				do{
					if ((var7_1) < (ModValues.MAX_DIFFICULTY)){
						int var8_1 = (var7_1) % (var5_1.length);
						int var9_1 = (var7_1) / (var5_1.length);
						if ((var7_1) < (var1)){
							ChatFormatting var10 = ChatFormatting.RESET;
							if ((var9_1) == (0)){
								var10 = ChatFormatting.YELLOW;
							}else{
								if ((var9_1) == (1)){
									var10 = ChatFormatting.RED;
								}else{
									if ((var9_1) == (2)){
										var10 = ChatFormatting.DARK_PURPLE;
									}
								}
							}
							String var11 = var10.toString();
							String var12 = ((var9_1) < (var6_1)) ? ("☆") : ("★");
							String var13 = var11 + var12 + "§r";
							var5_1[var8_1] = var13;
						}
						var7_1++;
						continue;
					}else{
						break;
					}
				} while (true);
				String[] var8_2 = var5_1;
				int var8_1 = var8_2.length;
				int var9_1 = 0;
				do{
					if ((var9_1) < (var8_1)){
						var3.append(var8_2[var9_1]);
						var9_1++;
						continue;
					}else{
						break;
					}
				} while (true);
			}
			return var3.toString();
		}
	}
	@Nullable()
	public Challenge getSelectedChallenge() {
		return this.selectedChallenge;
	}
	public void setSelectedChallenge(@Nullable() Challenge var1) {
		this.selectedChallenge = var1;
		this.selectedChallengeStars = this.createDifficultyStars();
		this.setupStep = 0;
		this.updateTargets();
		this.removeGroupMemberElements();
		this.startChallengeButton.visible = false;
		this.nextStepButton.visible = (this.selectedChallenge) != (null);
		this.prevStepButton.visible = false;
		this.freeCheckbox.visible = false;
		this.selectedMemberSlot = -1;
		Arrays.fill((Object[])(this.group),(Object)(null));
	}
	@Nullable()
	private void updateTargets() {
		if ((this.selectedChallenge) == (null)){
			return;
		}else{
			this.targets = new LivingEntity[4];
			int var1 = 0;
			LivingEntity[] var2 = this.selectedChallenge.getCore().getTargetShowcase((Level)(this.minecraft.level));
			int var3 = var2.length;
			int var4 = 0;
			do{
				if ((var4) < (var3)){
					LivingEntity var5 = var2[var4];
					if ((var1) > (3)){
						break;
					}else{
						if ((var5) == (null)){

						}else{
							this.targets[var1] = var5;
							var1++;
						}
						var4++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			this.targetsCount = var1;
			return;
		}
	}
	public void updateSelectedCategory() {
		this.selectedCategory = ((String)(this.categories.get(this.selectedCategoryId)));
		this.setSelectedChallenge((Challenge)(null));
		this.runInfoPanelAnimation = false;
		this.infoPanelAnimationTick = 0F;
		this.runCategoryChangeAnimation = true;
		this.categoryChangeAnimationTick = 0F;
		if (Strings.isNullOrEmpty(this.selectedCategory)){
			this.displayedChallenges = (List) (this.challenges);
			this.selectedCategoryLocalizedName = ModI18n.GUI_ALL;
			this.selectedCategoryIcon = null;
		}else{
			this.displayedChallenges = (List) (((List)(this.challengesMap.get(this.selectedCategory))));
			this.selectedCategoryLocalizedName = Component.translatable(this.selectedCategory);
			String var1 = this.selectedCategory.replace((CharSequence)("challenge.category."),(CharSequence)("")).replace((CharSequence)("crew.name."),(CharSequence)(""));
			String var2 = WyHelper.getResourceName(var1);
			String var3 = "textures/gui/challenges/" + var2 + ".png";
			this.selectedCategoryIcon = ResourceLocation.fromNamespaceAndPath("mineminenomi",var3);
		}
		this.removeWidget((GuiEventListener)(this.challengesListPanel));
		this.challengesListPanel = new ChallengesListScrollPanel(this,this.displayedChallenges);
		this.addRenderableWidget((GuiEventListener)(this.challengesListPanel));
	}
	public void renderEntityBust(@Nullable() LivingEntity var1, GuiGraphics var2, int var3, int var4) {
		if ((var1) != (null)){
			RendererHelper.drawLivingBust(var1,var2,var3,var4,30,-30,10,false);
			String var5 = this.getEntityName(var1);
			RendererHelper.drawStringWithBorder(this.font,var2,var5,(var3) - ((this.font.width(var5)) / (2)),(var4) - (30),-1);
		}else{
			RendererHelper.drawLivingBust(this.dummyBust,var2,var3,var4,30,-30,10,true);
			String var5_1 = ModI18n.GUI_SELECT_ONE.getString();
			RendererHelper.drawStringWithBorder(this.font,var2,var5_1,(var3) - ((this.font.width(var5_1)) / (2)),(var4) - (30),-1);
		}
	}
	private String getEntityName(LivingEntity var1) {
		if (var1 instanceof Player){
			return ((Player)(var1)).getGameProfile().getName();
		}else{
			return var1.getDisplayName().getString();
		}
	}
	public void startInfoPanelAnimation() {
		this.runInfoPanelAnimation = true;
		this.infoPanelAnimationTick = 0F;
	}
	public LivingEntity getDummyBust() {
		return this.dummyBust;
	}
	public boolean isInfoPanelAnimationComplete() {
		return ((this.selectedChallenge) != (null)) && ((this.runInfoPanelAnimation) && ((this.infoPanelAnimationTick) >= (20F)));
	}
	public void onClose() {
		if (!(this.startedChallenge)){
			ModNetwork.sendToServer(new CDisbandChallengeGroupPacket());
		}
		super.onClose();
	}
	public boolean isPauseScreen() {
		return false;
	}
}