package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$AbilityStatType;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class RangeComponentTooltip {
	// Fields
	private float min;
	private float max;
	private RangeComponent$RangeType type;

	public static void generate(AbilityTooltip var0, Consumer<RangeComponentTooltip> var1) {
		RangeComponentTooltip var2 = new RangeComponentTooltip();
		var1.accept(var2);
		RangeComponent var3 = ((RangeComponent)(var0.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.RANGE.get()))).orElse(null)));
		AbilityStat$Builder var4 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_RANGE),var2.min,var2.max);
		if ((var3) != (null)){
			float var5 = (var3.getBonusManager().applyBonus(var3.getPreviousRange())) - (var3.getPreviousRange());
			var4.withBonus(var5,((var5) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((var5) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL)));
		}
		switch (RangeComponentTooltip$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$components$RangeComponent$RangeType[var2.type.ordinal()]){
		case 1:

		case 2:

		case 3:

		default:
			Component var5_1 = ModI18nAbilities.DESCRIPTION_STAT_UNIT_BLOCKS;
			var0.set(((AbilityComponentKey)(ModAbilityComponents.RANGE.get())),var4.withUnit(var5_1).buildComponent());
			return;
		}
	}
	public RangeComponentTooltip range(float var1, RangeComponent$RangeType var2) {
		return this.range(var1,var1,var2);
	}
	public RangeComponentTooltip range(float var1, float var2, RangeComponent$RangeType var3) {
		this.min = var1;
		this.max = var2;
		this.type = var3;
		return this;
	}
}