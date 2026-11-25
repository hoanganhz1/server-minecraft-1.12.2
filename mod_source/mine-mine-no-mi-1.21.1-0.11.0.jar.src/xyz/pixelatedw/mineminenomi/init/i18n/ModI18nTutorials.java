package xyz.pixelatedw.mineminenomi.init.i18n;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class ModI18nTutorials {
	// Fields
	public static final Component ABILITY_SELECTOR_DRAG_AND_DROP = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.TUTORIAL,"ability_selector.drag_and_drop","§bDrag and Drop§r\n- §6Left click§r and §6drag§r abilities from the list to the lower bar to §6equip§r them.\n- §6Right click§r an ability in the lower bar to §6remove§r it."));
	public static final Component ABILITY_SELECTOR_KEYBIND = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.TUTORIAL,"ability_selector.keybind","§bKeybind§r\n- §6Left click§r on a slot in the lower bar to select it then §6left click§r on an ability from the list to §6equip§r it.\n- §6Hover§r over an ability in the list and §6press the keybind§r for the slot you want to §6equip§r the ability in.\n- §6Right click§r an ability in the lower bar to §6remove§r it."));
	public static final Component ABILITY_SELECTOR_SHOW_TOOLTIPS = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.TUTORIAL,"ability_selector.show_tooltips","§bShow Tooltips§r\n- Hold §6alt§r to temporarily §6show§r ability tooltips.\n"));
	public static final Component ABILITY_SELECTOR_HIDE_TOOLTIPS = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.TUTORIAL,"ability_selector.hide_tooltips","§bHide Tooltips§r\n- Hold §6alt§r to temporarily §6hide§r ability tooltips.\n"));
	public static final Component ABILITY_TREE = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.TUTORIAL,"ability_tree","- §6Left click§r and §6drag§r to move around\n- §6Left click§r on an ability to unlock it (if requirements are met)\n- §6Scroll Wheel§r zoom in/out\n"));

	public static void init() {
	}
}