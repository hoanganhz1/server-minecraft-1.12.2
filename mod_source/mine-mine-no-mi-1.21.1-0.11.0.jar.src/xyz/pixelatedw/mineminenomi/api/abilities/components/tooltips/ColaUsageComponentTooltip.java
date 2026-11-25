package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ColaUsageComponent;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ColaUsageComponentTooltip {
	// Fields
	private float cola;

	public static void generate(AbilityTooltip var0, Consumer<ColaUsageComponentTooltip> var1) {
		ColaUsageComponentTooltip var2 = new ColaUsageComponentTooltip();
		var1.accept(var2);
		ColaUsageComponent var3 = ((ColaUsageComponent)(var0.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.COLA_USAGE.get()))).orElse(null)));
		float var4 = var2.cola;
		if ((var3) != (null)){
			var4 = (float)(Math.round(var3.getColaBonusManager().applyBonus(var4)));
		}
		var0.set(((AbilityComponentKey)(ModAbilityComponents.COLA_USAGE.get())),new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_COLA),var4).buildComponent());
	}
	public ColaUsageComponentTooltip cola(float var1) {
		this.cola = var1;
		return this;
	}
}