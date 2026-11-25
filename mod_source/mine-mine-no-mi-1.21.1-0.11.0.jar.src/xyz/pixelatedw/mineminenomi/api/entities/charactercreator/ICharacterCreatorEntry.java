package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface ICharacterCreatorEntry extends Comparable<ICharacterCreatorEntry> {
	// Fields
	public static final ICharacterCreatorEntry RANDOM = new ICharacterCreatorEntry$1();

	public abstract Component getLabel();
	public abstract CharacterCreatorSelectionInfo getSelectionInfo();
	public abstract ResourceLocation getRegistryName();
	public abstract boolean isInBook();
	public abstract int getBookOrder();
	public default int compareTo(ICharacterCreatorEntry var1) {
		return (this.getBookOrder()) - (var1.getBookOrder());
	}
}