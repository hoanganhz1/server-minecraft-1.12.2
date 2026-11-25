package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility$GuardBreakKind;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility$GuardKind;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility$GuardValue;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class GuardTooltip {
	// Fields
	private GuardAbility$GuardValue guard;
	private boolean canGuardBreak;

	public static void generate(AbilityTooltip var0, Consumer<GuardTooltip> var1) {
		GuardTooltip var2 = new GuardTooltip();
		var1.accept(var2);
		float var3 = var2.guard.getValue();
		if ((var2.guard.getKind()) == (GuardAbility$GuardKind.PERCENTAGE)){
			var3 = (var3) * (100F);
		}
		AbilityStat$Builder var4 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_GUARD),var3);
		if ((var2.guard.getKind()) == (GuardAbility$GuardKind.PERCENTAGE)){
			var4.withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_PERCENT);
		}
		var0.add(var4.buildComponent());
		if (var2.canGuardBreak){
			var0.add(new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_GUARD_BREAK),var2.guard.getBreakLimit()).withUnit(((var2.guard.getBreakKind()) == (GuardAbility$GuardBreakKind.PER_HIT)) ? (ModI18nAbilities.DESCRIPTION_STAT_UNIT_PER_HIT) : (ModI18nAbilities.DESCRIPTION_STAT_UNIT_ACCUMULATED)).buildComponent(2));
		}
	}
	public GuardTooltip guard(GuardAbility$GuardValue var1) {
		this.guard = var1;
		return this;
	}
	public GuardTooltip canBreak(boolean var1) {
		this.canGuardBreak = var1;
		return this;
	}
}