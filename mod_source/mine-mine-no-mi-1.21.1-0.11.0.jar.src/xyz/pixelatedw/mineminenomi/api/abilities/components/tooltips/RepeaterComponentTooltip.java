package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class RepeaterComponentTooltip {
	// Fields
	private float triggers;
	private float interval;

	public static void generate(AbilityTooltip var0, Consumer<RepeaterComponentTooltip> var1) {
		RepeaterComponentTooltip var2 = new RepeaterComponentTooltip();
		var1.accept(var2);
		var0.set(((AbilityComponentKey)(ModAbilityComponents.REPEATER.get())),new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_REPEATER),var2.triggers).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_PROJECTILES).buildComponent());
	}
	public RepeaterComponentTooltip triggers(int var1, float var2) {
		this.triggers = (float)(var1);
		this.interval = var2;
		return this;
	}
}