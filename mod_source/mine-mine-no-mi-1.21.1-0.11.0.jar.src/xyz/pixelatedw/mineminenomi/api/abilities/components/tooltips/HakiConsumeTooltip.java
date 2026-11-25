package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class HakiConsumeTooltip {
	// Fields
	public static final int ORDER = 505;
	private int perTick;

	public static void generate(AbilityTooltip var0, Consumer<HakiConsumeTooltip> var1) {
		HakiConsumeTooltip var2 = new HakiConsumeTooltip();
		var1.accept(var2);
		if ((var2.perTick) > (0)){
			var0.add(505,new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_BONUS_PUNCH_DAMAGE),(var2.perTick) * (20)).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_PER_SECOND).buildComponent());
		}
	}
	public HakiConsumeTooltip perTick(int var1) {
		this.perTick = var1;
		return this;
	}
}