package xyz.pixelatedw.mineminenomi.ui.screens;

import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.ui.IEventReceiverScreen;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.ui.events.TrialsScreenEvent;
import xyz.pixelatedw.mineminenomi.ui.panel.TrainerTrialsScrollPanel;
import xyz.pixelatedw.mineminenomi.ui.screens.dialogues.TrainerDialogueScreen$QuestEntry;
import xyz.pixelatedw.mineminenomi.ui.widgets.FactionButton;

public class TrainerTrialsListScreen extends Screen implements IEventReceiverScreen<TrialsScreenEvent> {
	// Fields
	private final Screen previousScreen;
	private final LivingEntity trainer;
	private final List<TrainerDialogueScreen$QuestEntry> entries;
	private final boolean isInCombat;
	private TrainerTrialsScrollPanel questsPanel;

	public TrainerTrialsListScreen(Screen var1, LivingEntity var2, List<TrainerDialogueScreen$QuestEntry> var3, boolean var4) {
		super((Component)(Component.empty()));
		this.previousScreen = var1;
		this.trainer = var2;
		this.entries = var3;
		this.isInCombat = var4;
	}
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		super.render(var1,var2,var3,var4);
	}
	public void init() {
		int var1 = (this.width) / (2);
		int var2 = (this.height) / (2);
		this.questsPanel = new TrainerTrialsScrollPanel(this,this.entries);
		this.addRenderableWidget((GuiEventListener)(this.questsPanel));
		this.setFocused((GuiEventListener)(this.questsPanel));
		this.addRenderableWidget((GuiEventListener)(new FactionButton(80,(this.height) - (40),100,20,ModI18n.GUI_CANCEL,(Button.OnPress)(((net.minecraft.client.gui.components.Button.OnPress)(this::goToPrevious))))));
	}
	public void goToPrevious(Button var1) {
		this.minecraft.setScreen(this.previousScreen);
	}
	public void handleEvent(TrialsScreenEvent var1) {
		if ((var1.getQuestId()) != (null)){
			this.questsPanel.removeEntry(var1.getQuestId());
		}
	}
	public TrialsScreenEvent decode(HolderLookup.Provider var1, CompoundTag var2) {
		TrialsScreenEvent var3 = new TrialsScreenEvent();
		var3.deserializeNBT(var1,var2);
		return var3;
	}
	public LivingEntity getTrainer() {
		return this.trainer;
	}
}