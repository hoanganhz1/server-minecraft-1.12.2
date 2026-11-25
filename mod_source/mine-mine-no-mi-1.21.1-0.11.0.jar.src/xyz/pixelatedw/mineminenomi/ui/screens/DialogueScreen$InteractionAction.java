package xyz.pixelatedw.mineminenomi.ui.screens;

import javax.annotation.Nullable;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

public class DialogueScreen$InteractionAction {
	// Fields
	@Nullable() private Tooltip tooltip;
	private Component title;
	private Button.OnPress event;
	 boolean active;

	public DialogueScreen$InteractionAction(Component var1, Button.OnPress var2) {
		this(var1,var2,null);
	}
	public DialogueScreen$InteractionAction(Component var1, Button.OnPress var2, @Nullable() Tooltip var3) {
		this.active = true;
		this.title = var1;
		this.event = var2;
		this.tooltip = var3;
	}
	public Component getTitle() {
		return this.title;
	}
	public Button.OnPress getEvent() {
		return this.event;
	}
	public void setTooltip(@Nullable() Tooltip var1) {
		this.tooltip = var1;
	}
	@Nullable()
	public Tooltip getTooltip() {
		return this.tooltip;
	}
	public void disable() {
		this.active = false;
	}
	public boolean isDisabled() {
		return !(this.active);
	}
}