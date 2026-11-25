package xyz.pixelatedw.mineminenomi.api.abilities;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.init.ModKeybindings;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public interface IDescriptiveAbility {
	public abstract void appendDescription(Player var0, AbilityTooltip var1);
	public default void appendPageInfo(AbilityTooltip var1, Player var2) {
		var1.add(10,AbilityTooltipsHelper.NEW_LINE);
		MutableComponent var3 = ModKeybindings.ABILITY_ALT_MODE.getKey().getDisplayName().copy().withStyle(ChatFormatting.GREEN);
		Object[] var4 = new Object[]{Integer.valueOf((var1.getCurrentPage()) + (1)),Integer.valueOf(var1.getMaxPages()),var3};
		var1.add(10,(Component)(Component.translatable(ModI18nAbilities.DESCRIPTION_ALT_MODE,var4).setStyle(Style.EMPTY.withItalic(Boolean.valueOf(true)))));
	}
}