package xyz.pixelatedw.mineminenomi.ui.screens.dialogues;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.DoctorTrainerEntity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nInteractions;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CDoctorHealPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CTrainerResetTreePacket;
import xyz.pixelatedw.mineminenomi.ui.screens.DialogueScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.DialogueScreen$InteractionAction;
import xyz.pixelatedw.mineminenomi.ui.screens.TrainerTrialsListScreen;

public class TrainerDialogueScreen extends DialogueScreen {
	// Fields
	private final List<TrainerDialogueScreen$QuestEntry> entries;
	private final boolean isInCombat;

	public TrainerDialogueScreen(LivingEntity var1, List<TrainerDialogueScreen$QuestEntry> var2, boolean var3) {
		super(var1);
		this.entries = var2;
		this.isInCombat = var3;
	}
	public void setupDialogue() {
		TrainerDialogueScreen var1 = this;
		Button.OnPress var2 = var1::openTrialsMenu;
		this.addInteraction(new DialogueScreen$InteractionAction(ModI18n.GUI_TRIALS,var2,(Tooltip)(null)));
		int var3 = ((Integer)(EntityStatsData.get((LivingEntity)(this.getPlayer())).map((Function)(((Function<EntityStatsData, Integer>)(EntityStatsData::getSpentTrainingPoints)))).orElse(Integer.valueOf(0)))).intValue();
		int var4 = (var3) * (50);
		Object[] var5 = new Object[]{Integer.valueOf(var4)};
		TrainerDialogueScreen var6 = this;
		Button.OnPress var7 = var6::resetTree;
		DialogueScreen$InteractionAction var8 = new DialogueScreen$InteractionAction((Component)(Component.translatable(ModI18n.GUI_RESET_TREE,var5)),var7,(Tooltip)(null));
		if ((var3) == (0)){
			var8.disable();
		}
		this.addInteraction(var8);
		if (this.getTarget() instanceof DoctorTrainerEntity){
			int var9 = ((int)((this.getPlayer().getMaxHealth()) - (this.getPlayer().getHealth()))) * (10);
			if ((this.getPlayer().getMaxHealth()) == (this.getPlayer().getHealth())){
				var9 = 0;
			}
			Object[] var10 = new Object[]{Integer.valueOf(var9)};
			DialogueScreen$InteractionAction var11 = new DialogueScreen$InteractionAction((Component)(Component.translatable(ModI18n.GUI_HEAL_COST,var10)),(Button.OnPress)(((net.minecraft.client.gui.components.Button.OnPress)(this::heal))));
			if (this.isInCombat){
				var11.setTooltip(Tooltip.create(ModI18n.GUI_COMBAT_CANNOT_USE));
				var11.disable();
			}else{
				if ((this.getPlayer().getMaxHealth()) == (this.getPlayer().getHealth())){
					var11.setTooltip(Tooltip.create(ModI18nInteractions.ALREADY_MAX_HEALTH_MESSAGE));
					var11.disable();
				}
			}
			this.addInteraction(var11);
		}
		TrainerDialogueScreen var9_1 = this;
		Button.OnPress var10_1 = (l0) -> {
			this.onClose();
		};
		this.addInteraction(new DialogueScreen$InteractionAction(ModI18n.GUI_QUIT,var10_1));
	}
	private void heal(Button var1) {
		ModNetwork.sendToServer(new CDoctorHealPacket(this.entity.getId()));
	}
	private void resetTree(Button var1) {
		ModNetwork.sendToServer(new CTrainerResetTreePacket(this.entity.getId()));
	}
	private void openTrialsMenu(Button var1) {
		boolean var2 = false;
		int var3 = 0;
		do{
			if ((var3) <= ((this.entries.size()) - (1))){
				if ((((TrainerDialogueScreen$QuestEntry)(this.entries.get(var3)))) != (null)){
					var2 = true;
					break;
				}else{
					var3++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		if (var2){
			this.minecraft.setScreen((Screen)(new TrainerTrialsListScreen((Screen)(this),this.getTarget(),this.entries,this.isInCombat)));
		}else{
			this.setMessage(ModI18n.TRAINER_NO_TRIALS_AVAILABLE);
		}
	}
}