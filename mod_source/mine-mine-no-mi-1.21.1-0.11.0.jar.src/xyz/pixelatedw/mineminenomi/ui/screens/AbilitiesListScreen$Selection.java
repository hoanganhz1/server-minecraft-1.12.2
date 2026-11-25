package xyz.pixelatedw.mineminenomi.ui.screens;

import java.util.Enumeration;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public enum AbilitiesListScreen$Selection implements Enumeration<AbilitiesListScreen$Selection> {
	// Fields
	DRAG_AND_DROP(ModI18n.SELECTION_DD_LABEL, ModI18n.SELECTION_DD_SHORT_LABEL),
	KEYBIND(ModI18n.SELECTION_KB_LABEL, ModI18n.SELECTION_KB_SHORT_LABEL);
	final Component label;
	final Component shortLabel;

	private AbilitiesListScreen$Selection(Component var3, Component var4) {
		this.label = var3;
		this.shortLabel = var4;
	}
	public Component getShortNotation() {
		return this.shortLabel;
	}
	public Component getLabel() {
		return this.label;
	}
	public boolean hasMoreElements() {
		return true;
	}
	public AbilitiesListScreen$Selection nextElement() {
		return values()[((this.ordinal()) + (1)) % (values().length)];
	}
}