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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ChargeComponentTooltip {
	// Fields
	private float min;
	private float max;

	public static void generate(AbilityTooltip var0, Consumer<ChargeComponentTooltip> var1) {
		ChargeComponentTooltip var2 = new ChargeComponentTooltip();
		var1.accept(var2);
		ChargeComponent var3 = ((ChargeComponent)(var0.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))).orElse(null)));
		float var4 = (float)(Math.round((var2.min) / (20F)));
		float var5 = (float)(Math.round((var2.max) / (20F)));
		AbilityStat$Builder var6 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_CHARGE),var4,var5).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS);
		if ((var3) != (null)){
			float var7 = (float)(Math.round(((var3.getMaxChargeBonusManager().applyBonus(var4)) - (var4)) / (20F)));
			float var8 = (float)(Math.round(((var3.getMaxChargeBonusManager().applyBonus(var5)) - (var5)) / (20F)));
			var6.withBonus(var7,var8,AbilityStat$AbilityStatType.from((var7) + (var8),true));
		}
		var0.set(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get())),var6.buildComponent());
	}
	public ChargeComponentTooltip charge(float var1) {
		return this.charge(var1,var1);
	}
	public ChargeComponentTooltip charge(float var1, float var2) {
		this.min = var1;
		this.max = var2;
		return this;
	}
}