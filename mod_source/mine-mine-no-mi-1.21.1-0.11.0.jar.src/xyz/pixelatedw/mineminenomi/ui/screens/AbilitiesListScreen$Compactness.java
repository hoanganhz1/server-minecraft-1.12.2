package xyz.pixelatedw.mineminenomi.ui.screens;

import java.util.Enumeration;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public enum AbilitiesListScreen$Compactness implements Enumeration<AbilitiesListScreen$Compactness> {
	// Fields
	SPATIOUS(ModI18n.COMPACTNESS_S_LABEL, ModI18n.COMPACTNESS_S_SHORT_LABEL, 1.25F, 16, 1F, 0, 4, 0, 0),
	COMPACT(ModI18n.COMPACTNESS_C_LABEL, ModI18n.COMPACTNESS_C_SHORT_LABEL, 1.05F, 16, 1F, 0, 4, 0, 0),
	MINIMAL(ModI18n.COMPACTNESS_M_LABEL, ModI18n.COMPACTNESS_M_SHORT_LABEL, 0.9F, 12, 0.8F, 5, 2, 5, 2);
	private final float spacing;
	private final int iconSize;
	private final float slotScale;
	private final int slotOffsetX;
	private final int slotOffsetY;
	private final int iconOffsetX;
	private final int iconOffsetY;
	final Component label;
	final Component shortLabel;

	private AbilitiesListScreen$Compactness(Component var3, Component var4, float var5, int var6, float var7, int var8, int var9, int var10, int var11) {
		this.spacing = var5;
		this.iconSize = var6;
		this.slotScale = var7;
		this.slotOffsetX = var8;
		this.slotOffsetY = var9;
		this.iconOffsetX = var10;
		this.iconOffsetY = var11;
		this.label = var3;
		this.shortLabel = var4;
	}
	public float getSpacing() {
		return this.spacing;
	}
	public int getIconSize() {
		return this.iconSize;
	}
	public float getSlotScale() {
		return this.slotScale;
	}
	public int getSlotOffsetX() {
		return this.slotOffsetX;
	}
	public int getSlotOffsetY() {
		return this.slotOffsetY;
	}
	public int getIconOffsetX() {
		return this.iconOffsetX;
	}
	public int getIconOffsetY() {
		return this.iconOffsetY;
	}
	public String toString() {
		return WyHelper.capitalize(this.name());
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
	public AbilitiesListScreen$Compactness nextElement() {
		return values()[((this.ordinal()) + (1)) % (values().length)];
	}
}