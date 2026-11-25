package xyz.pixelatedw.mineminenomi.api.entities.charactercreator;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class ICharacterCreatorEntry$1 implements ICharacterCreatorEntry {
	// Fields
	private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","random");
	private static final CharacterCreatorSelectionInfo SELECTION_INFO = new CharacterCreatorSelectionInfo(ModResources.RANDOM,0);

	ICharacterCreatorEntry$1() {
	}
	public Component getLabel() {
		return ModI18n.GUI_RANDOM;
	}
	public CharacterCreatorSelectionInfo getSelectionInfo() {
		return SELECTION_INFO;
	}
	public ResourceLocation getRegistryName() {
		return ID;
	}
	public boolean isInBook() {
		return true;
	}
	public int getBookOrder() {
		return 0;
	}
}