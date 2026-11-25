package xyz.pixelatedw.mineminenomi.ui.screens.dialogues;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CBarkeeperBuyRumPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CBarkeeperListNewsPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CBarkeeperListRumorsPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.DialogueScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.DialogueScreen$InteractionAction;

public class BarkeeperDialogueScreen extends DialogueScreen {
	public BarkeeperDialogueScreen(LivingEntity var1) {
		super(var1);
	}
	public void setupDialogue() {
		Object[] var1 = new Object[]{Integer.valueOf(100)};
		MutableComponent var2 = Component.translatable(ModI18n.GUI_BUY_RUM_COST,var1);
		Object[] var3 = new Object[]{Integer.valueOf(1000)};
		MutableComponent var4 = Component.translatable(ModI18n.GUI_RUMORS_WITH_PRICE,var3);
		this.addInteraction(new DialogueScreen$InteractionAction(ModI18n.GUI_NEWS,(Button.OnPress)(((net.minecraft.client.gui.components.Button.OnPress)(this::listNews)))));
		this.addInteraction(new DialogueScreen$InteractionAction((Component)(var4),(Button.OnPress)(((net.minecraft.client.gui.components.Button.OnPress)(this::listRumors)))));
		this.addInteraction(new DialogueScreen$InteractionAction((Component)(var2),(Button.OnPress)(((net.minecraft.client.gui.components.Button.OnPress)(this::buyRum)))));
		BarkeeperDialogueScreen var5 = this;
		Button.OnPress var6 = (l0) -> {
			this.onClose();
		};
		this.addInteraction(new DialogueScreen$InteractionAction(ModI18n.GUI_QUIT,var6));
	}
	private void listNews(Button var1) {
		ModNetwork.sendToServer(new CBarkeeperListNewsPacket());
	}
	private void listRumors(Button var1) {
		ModNetwork.sendToServer(new CBarkeeperListRumorsPacket(this.getTarget().getId()));
	}
	private void buyRum(Button var1) {
		ModNetwork.sendToServer(new CBarkeeperBuyRumPacket());
	}
}