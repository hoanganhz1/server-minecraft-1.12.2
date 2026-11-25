package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class SwordsmanPassivesHandler$Client {
	// Fields
	private static final Component SWORDSMAN_TOOLTIP = ModI18n.ITEM_SWORDSMAN_BONUS.copy().setStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN));

	public static void swordsmanBonusTooltip(Player var0, ItemStack var1, List<Component> var2) {
		if (!(ItemsHelper.isSword(var1))){
			return;
		}else{
			if (!(((Boolean)(EntityStatsData.get((LivingEntity)(var0)).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isSwordsman)))).orElse(Boolean.valueOf(false)))).booleanValue())){
				return;
			}else{
				if (!(var2.contains((Object)(SWORDSMAN_TOOLTIP)))){
					((java.util.List)(var2)).add((Object)(Component.empty()));
					var2.add(SWORDSMAN_TOOLTIP);
				}
				return;
			}
		}
	}
}