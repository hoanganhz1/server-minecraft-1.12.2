package xyz.pixelatedw.mineminenomi.ui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.CharacterCreatorSelectionInfo;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.ICharacterCreatorEntry;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.CFinishCCPacket;
import xyz.pixelatedw.mineminenomi.ui.widgets.AbilitySlotButton;
import xyz.pixelatedw.mineminenomi.ui.widgets.ArrowButton;
import xyz.pixelatedw.mineminenomi.ui.widgets.BigArrowButton;
import xyz.pixelatedw.mineminenomi.ui.widgets.BookSignSideButton;
import xyz.pixelatedw.mineminenomi.ui.widgets.SimpleButton;

public class CharacterCreatorScreen extends Screen {
	// Fields
	private AbstractClientPlayer player;
	private ResourceLocation icon;
	private Component label;
	private Component subRaceLabel;
	private List<? extends AbilityCore<?>> topAbilities;
	private List<? extends AbilityCore<?>> bottomAbilities;
	private Faction[] factions;
	private Race[] races;
	private FightingStyle[] styles;
	private Race[] subRaces;
	private CharacterCreatorScreen$Page page;
	private int maxOpt;
	private int renderTick;
	private int subRaceId;
	private boolean hasSubRaces;
	private int[] options;
	private BookSignSideButton factionButton;
	private BookSignSideButton raceButton;
	private BookSignSideButton styleButton;
	private BigArrowButton previousSubRaceButton;
	private BigArrowButton nextSubRaceButton;
	private final List<AbilitySlotButton> abilitySlots;
	private final boolean hasRandomizedRace;
	private final boolean allowSubRaceSelect;

	public CharacterCreatorScreen(boolean var1, boolean var2) {
		super((Component)(Component.empty()));
		this.icon = ModResources.RANDOM;
		this.label = ModI18n.GUI_RANDOM;
		this.subRaceLabel = Component.empty();
		this.topAbilities = (List<? extends AbilityCore<?>>) (new ArrayList());
		this.bottomAbilities = (List<? extends AbilityCore<?>>) (new ArrayList());
		this.page = CharacterCreatorScreen$Page.FACTION;
		this.renderTick = 0;
		this.subRaceId = 0;
		this.hasSubRaces = false;
		this.options = new int[3];
		this.abilitySlots = Lists.newArrayList();
		this.hasRandomizedRace = var1;
		this.allowSubRaceSelect = var2;
		IntFunction<Faction[]> var3 = (int l0) -> {
			return new Faction[l0];
		};
		this.factions = ((Faction[])(WyRegistry.FACTIONS.stream().filter((Predicate)(((Predicate<Faction>)(ICharacterCreatorEntry::isInBook)))).sorted((Comparator)(((Comparator<Faction>)(ICharacterCreatorEntry::compareTo)))).toArray((IntFunction)(var3))));
		IntFunction<Race[]> var4 = (int l0) -> {
			return new Race[l0];
		};
		this.races = ((Race[])(WyRegistry.RACES.stream().filter((Predicate)(((Predicate<Race>)(Race::isMainRace)))).filter((Predicate)(((Predicate<Race>)(ICharacterCreatorEntry::isInBook)))).sorted((Comparator)(((Comparator<Race>)(ICharacterCreatorEntry::compareTo)))).toArray((IntFunction)(var4))));
		IntFunction<FightingStyle[]> var5 = (int l0) -> {
			return new FightingStyle[l0];
		};
		this.styles = ((FightingStyle[])(WyRegistry.FIGHTING_STYLES.stream().filter((Predicate)(((Predicate<FightingStyle>)(ICharacterCreatorEntry::isInBook)))).sorted((Comparator)(((Comparator<FightingStyle>)(ICharacterCreatorEntry::compareTo)))).toArray((IntFunction)(var5))));
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		this.renderBackground(var1,var2,var3,var4);
		int var5 = (this.width) / (2);
		int var6 = (this.height) / (2);
		var1.pose().pushPose();
		var1.pose().translate((float)((var5) - (120)),(float)((var6) - (80)),0F);
		var1.pose().translate(128F,128F,0F);
		var1.pose().scale(1.2F,1.2F,1.2F);
		var1.pose().translate(-128F,-128F,0F);
		var1.blit(ModResources.BOOK,0,0,0,0,256,256);
		var1.pose().popPose();
		var5 = (var5) + (65);
		var6 = (var6) - (20);
		if ((!(this.allowSubRaceSelect)) && (((this.renderTick) % (150)) == (0))){
			this.increaseSubRace();
		}
		this.drawCategoryIcon(var1,this.icon,(var5) - (115),(var6) - (120),0.7F);
		this.drawCategoryLabel(var1,this.label,(var5) + (75),(var6) - (5),1.5F);
		this.drawCategoryLabel(var1,this.subRaceLabel,(var5) + (22),(var6) - (42),1.1F);
		Iterator var7 = this.abilitySlots.iterator();
		do{
			if (var7.hasNext()){
				((AbilitySlotButton)(var7.next())).render(var1,var2,var3,var4);
				continue;
			}else{
				break;
			}
		} while (true);
		Iterator var8 = this.renderables.iterator();
		do{
			if (var8.hasNext()){
				((Renderable)(var8.next())).render(var1,var2,var3,var4);
				continue;
			}else{
				break;
			}
		} while (true);
		this.renderTick = (this.renderTick) + (1);
	}
	public void init() {
		this.player = this.minecraft.player;
		int var1 = ((this.width) - (256)) / (2);
		int var2 = ((this.height) - (256)) / (2);
		var1 = (var1) + (65);
		var2 = (var2) - (40);
		var1 = (var1) - (65);
		var2 = (var2) + (5);
		CharacterCreatorScreen var3 = this;
		Button.OnPress var4 = (l0) -> {
			this.page = CharacterCreatorScreen$Page.FACTION;
			this.resetButtonState();
			this.factionButton.setIsPressed(true);
			this.tryToggleSubRaceButtons();
		};
		this.factionButton = new BookSignSideButton((var1) - (110),(var2) + (65),110,35,ModI18n.GUI_FACTION,var4);
		this.addRenderableWidget((GuiEventListener)(this.factionButton));
		var2 = (var2) + (45);
		CharacterCreatorScreen var5 = this;
		Button.OnPress var6 = (l0) -> {
			this.page = CharacterCreatorScreen$Page.RACE;
			this.resetButtonState();
			this.raceButton.setIsPressed(true);
			this.tryToggleSubRaceButtons();
		};
		this.raceButton = new BookSignSideButton((var1) - (110),(var2) + (65),110,35,ModI18n.GUI_RACE,var6);
		if (!(this.hasRandomizedRace)){
			this.addRenderableWidget((GuiEventListener)(this.raceButton));
			var2 = (var2) + (45);
		}
		CharacterCreatorScreen var7 = this;
		Button.OnPress var8 = (l0) -> {
			this.page = CharacterCreatorScreen$Page.FIGHTING_STYLE;
			this.resetButtonState();
			this.styleButton.setIsPressed(true);
			this.tryToggleSubRaceButtons();
		};
		this.styleButton = new BookSignSideButton((var1) - (110),(var2) + (65),110,35,ModI18n.GUI_STYLE,var8);
		this.addRenderableWidget((GuiEventListener)(this.styleButton));
		if (this.hasRandomizedRace){
			var2 = (var2) + (45);
		}
		var1 = (var1) + (65);
		var2 = (var2) - (95);
		var1 = (var1) + (30);
		var2 = (var2) + (130);
		CharacterCreatorScreen var9 = this;
		Button.OnPress var10 = (l0) -> {
			this.decreaseSelectedPage();
			this.tryToggleSubRaceButtons();
		};
		this.addRenderableWidget((GuiEventListener)(new ArrowButton((var1) + (60),(var2) + (80),35,15,var10)));
		var1 = (var1) - (90);
		CharacterCreatorScreen var11 = this;
		Button.OnPress var12 = (l0) -> {
			this.increaseSelectedPage();
			this.tryToggleSubRaceButtons();
		};
		ArrowButton var13 = new ArrowButton((var1) + (215),(var2) + (80),35,15,var12);
		var13.setFlipped();
		this.addRenderableWidget((GuiEventListener)(var13));
		var1 = (var1) + (30);
		var2 = (var2) - (110);
		CharacterCreatorScreen var14 = this;
		Button.OnPress var15 = (l0) -> {
			this.decreaseSubRace();
		};
		this.previousSubRaceButton = new BigArrowButton((var1) + (110),(var2) + (80),20,100,var15);
		this.previousSubRaceButton.visible = false;
		this.addRenderableWidget((GuiEventListener)(this.previousSubRaceButton));
		CharacterCreatorScreen var16 = this;
		Button.OnPress var17 = (l0) -> {
			this.increaseSubRace();
		};
		this.nextSubRaceButton = new BigArrowButton((var1) + (208),(var2) + (80),20,100,var17);
		this.nextSubRaceButton.setFlipped();
		this.nextSubRaceButton.visible = false;
		this.addRenderableWidget((GuiEventListener)(this.nextSubRaceButton));
		var1 = (var1) - (115);
		var2 = (var2) + (30);
		CharacterCreatorScreen var18 = this;
		Button.OnPress var19 = (l0) -> {
			this.completeCreation();
		};
		SimpleButton var20 = new SimpleButton((var1) + (115),(var2) + (175),70,30,ModI18n.GUI_FINISH,var19);
		var20.setTextOnly();
		var20.setFontSize(1.5F);
		this.addRenderableWidget((GuiEventListener)(var20));
		this.createAbilitySlotButtons();
	}
	private void tryToggleSubRaceButtons() {
		this.previousSubRaceButton.visible = false;
		this.nextSubRaceButton.visible = false;
		if (((this.page) == (CharacterCreatorScreen$Page.RACE)) && ((this.hasSubRaces) && (this.allowSubRaceSelect))){
			this.previousSubRaceButton.visible = true;
			this.nextSubRaceButton.visible = true;
		}
	}
	private void resetButtonState() {
		this.factionButton.setIsPressed(false);
		this.raceButton.setIsPressed(false);
		this.styleButton.setIsPressed(false);
		this.updateSelection();
	}
	private void completeCreation() {
		Minecraft.getInstance().setScreen((Screen)(null));
		ModNetwork.sendToServer(new CFinishCCPacket(this.getSelectedFactionId(),this.getSelectedRaceId(),this.getSelectedStyleId(),this.subRaceId));
	}
	public void tick() {
		switch (this.page.ordinal()){
		default:
			throw new MatchException((String)(null),(Throwable)(null));
		case 0:

		case 2:
			this.maxOpt = (this.styles.length) + (1);
			return;
		case 1:

		}
	}
	private void drawCategory(GuiGraphics var1, String var2, int var3, int var4, float var5) {
		this.drawCategoryLabel(var1,(Component)(Component.literal(var2)),var3,var4,var5);
	}
	private void drawCategoryIcon(GuiGraphics var1, ResourceLocation var2, int var3, int var4, float var5) {
		var1.pose().pushPose();
		var1.pose().translate((float)(var3),(float)(var4),2F);
		var1.pose().translate(128F,128F,0F);
		var1.pose().scale(var5,var5,1F);
		var1.pose().translate(-128F,-128F,0F);
		var1.blit(this.icon,0,0,0,0,256,256);
		var1.pose().popPose();
	}
	private void drawCategoryLabel(GuiGraphics var1, Component var2, int var3, int var4, float var5) {
		var1.pose().pushPose();
		var1.pose().translate((float)(var3),(float)(var4),2F);
		var1.pose().translate(128F,128F,0F);
		var1.pose().scale(var5,var5,1F);
		var1.pose().translate(-128F,-128F,0F);
		RendererHelper.drawStringWithBorder(this.font,var1,var2,(-(this.font.width((FormattedText)(var2)))) / (2),0,-1);
		var1.pose().popPose();
	}
	public int getSelectedFactionId() {
		return this.options[CharacterCreatorScreen$Page.FACTION.ordinal()];
	}
	public int getSelectedRaceId() {
		return this.options[CharacterCreatorScreen$Page.RACE.ordinal()];
	}
	public int getSelectedStyleId() {
		return this.options[CharacterCreatorScreen$Page.FIGHTING_STYLE.ordinal()];
	}
	public int getSelectedId() {
		return this.options[this.page.ordinal()];
	}
	public void increaseSelectedPage() {
		if (((this.options[this.page.ordinal()]) + (1)) < (this.maxOpt)){
			int var1 = this.page.ordinal();
			int[] var2 = this.options;
			var2[var1] = (var2[var1]) + (1);
		}else{
			this.options[this.page.ordinal()] = 0;
		}
		this.updateSelection();
	}
	public void decreaseSelectedPage() {
		if (((this.options[this.page.ordinal()]) - (1)) > (-1)){
			int var1 = this.page.ordinal();
			int[] var2 = this.options;
			var2[var1] = (var2[var1]) - (1);
		}else{
			this.options[this.page.ordinal()] = (this.maxOpt) - (1);
		}
		this.updateSelection();
	}
	private void updateSelection() {
		CharacterCreatorSelectionInfo var1 = ICharacterCreatorEntry.RANDOM.getSelectionInfo();
		Component var2 = ModI18n.GUI_RANDOM;
		MutableComponent var3 = Component.empty();
		this.hasSubRaces = false;
		if ((this.getSelectedId()) > (0)){
			switch (this.page.ordinal()){
			case 0:
				Faction var4 = this.factions[(this.getSelectedId()) - (1)];
				var1 = var4.getSelectionInfo();
				var2 = var4.getLabel();
				break;
			case 2:
				FightingStyle var5 = this.styles[(this.getSelectedId()) - (1)];
				var1 = var5.getSelectionInfo();
				var2 = var5.getLabel();
				break;
			case 1:
				Race var6 = this.races[(this.getSelectedId()) - (1)];
				if (var6.hasSubRaces()){
					Comparator<Race> var7 = (l0, l1) -> {
			return (l0.getBookOrder()) - (l1.getBookOrder());
		};
					IntFunction<Race[]> var8 = (int l0) -> {
			return new Race[l0];
		};
					this.subRaces = ((Race[])(var6.getSubRaces().stream().map((Function)(((Function<Supplier<Race>, Race>)(Supplier::get)))).filter((Predicate)(((Predicate<Race>)(ICharacterCreatorEntry::isInBook)))).sorted(var7).toArray((IntFunction)(var8))));
					if ((this.subRaces.length) > (0)){
						this.hasSubRaces = true;
					}
					var3 = Component.literal(this.subRaces[this.subRaceId].getLabel().getString().replace((CharSequence)(var6.getLabel().getString()),(CharSequence)("")));
				}
				var1 = var6.getSelectionInfo();
				var2 = var6.getLabel();
				break;
			default:
				String var7_1 = String.valueOf((Object)(this.page));
				String var8_1 = "Unexpected value: " + var7_1;
				throw new IllegalArgumentException((String)(var8_1));
			}
		}
		this.icon = var1.getIcon();
		this.label = var2;
		this.subRaceLabel = var3;
		this.topAbilities = (List) (var1.getTopAbilities().stream().map((Function)(((Function<Supplier<AbilityCore>, AbilityCore>)(Supplier::get)))).toList());
		this.bottomAbilities = (List) (var1.getBottomAbilities().stream().map((Function)(((Function<Supplier<AbilityCore>, AbilityCore>)(Supplier::get)))).toList());
		this.createAbilitySlotButtons();
	}
	private void createAbilitySlotButtons() {
		int var1 = ((this.width) - (256)) / (2);
		int var2 = ((this.height) - (256)) / (2);
		this.abilitySlots.clear();
		int var3 = 0;
		int var4 = 0;
		int var5 = 0;
		Iterator var6 = this.topAbilities.iterator();
		do{
			if (var6.hasNext()){
				AbilityCore var7 = ((AbilityCore)(var6.next()));
				if (((var3) % (3)) == (0)){
					var4 = 0;
					var5 = (var5) + (30);
				}else{
					var4 = (var4) + (38);
				}
				IAbility var8 = var7.createAbility();
				Button.OnPress var9 = (l0) -> {
		};
				AbilitySlotButton var10 = new AbilitySlotButton(var8,((var1) + (20)) + (var4),((var2) + (10)) + (var5),22,22,this.player,var9);
				this.abilitySlots.add(var10);
				this.addWidget((GuiEventListener)(var10));
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		var3 = 0;
		var4 = 0;
		var5 = 0;
		Iterator var7_1 = this.bottomAbilities.iterator();
		do{
			if (var7_1.hasNext()){
				AbilityCore var8_1 = ((AbilityCore)(var7_1.next()));
				if (((var3) % (3)) == (0)){
					var4 = 0;
					var5 = (var5) + (30);
				}else{
					var4 = (var4) + (38);
				}
				IAbility var9_1 = var8_1.createAbility();
				Button.OnPress var10_1 = (l0) -> {
		};
				AbilitySlotButton var11 = new AbilitySlotButton(var9_1,((var1) + (20)) + (var4),((var2) + (100)) + (var5),22,22,this.player,var10_1);
				this.abilitySlots.add(var11);
				this.addWidget((GuiEventListener)(var11));
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void increaseSubRace() {
		if ((this.subRaceId) == ((this.subRaces.length) - (1))){
			this.subRaceId = 0;
		}else{
			this.subRaceId = (this.subRaceId) + (1);
		}
		this.updateSubRaceSelection();
	}
	public void decreaseSubRace() {
		if ((this.subRaceId) == (0)){
			this.subRaceId = (this.subRaces.length) - (1);
		}else{
			this.subRaceId = (this.subRaceId) - (1);
		}
		this.updateSubRaceSelection();
	}
	private void updateSubRaceSelection() {
		this.icon = this.subRaces[this.subRaceId].getSelectionInfo().getIcon();
		this.subRaceLabel = Component.literal(this.subRaces[this.subRaceId].getLabel().getString().replace((CharSequence)(this.label.getString()),(CharSequence)("")));
	}
}