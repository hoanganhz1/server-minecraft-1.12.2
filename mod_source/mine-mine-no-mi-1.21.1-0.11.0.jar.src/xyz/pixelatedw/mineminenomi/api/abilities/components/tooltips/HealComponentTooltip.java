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
import xyz.pixelatedw.mineminenomi.api.abilities.components.HealComponent;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class HealComponentTooltip {
	// Fields
	private float min;
	private float max;

	public static void generate(AbilityTooltip var0, Consumer<HealComponentTooltip> var1) {
		HealComponentTooltip var2 = new HealComponentTooltip();
		var1.accept(var2);
		HealComponent var3 = ((HealComponent)(var0.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.HEAL.get()))).orElse(null)));
		float var4 = var2.min;
		float var5 = var2.max;
		AbilityStat$Builder var6 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_HEAL),var4,var5);
		if ((var3) != (null)){
			float var7 = (var3.getBonusManager().applyBonus(var4)) - (var4);
			float var8 = (var3.getBonusManager().applyBonus(var5)) - (var5);
			float var9 = (var7) + (var8);
			var6.withBonus(var7,var8,((var9) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((var9) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL)));
		}
		var0.set(((AbilityComponentKey)(ModAbilityComponents.HEAL.get())),var6.buildComponent());
	}
	public HealComponentTooltip heal(float var1) {
		return this.heal(var1,var1);
	}
	public HealComponentTooltip heal(float var1, float var2) {
		this.min = var1;
		this.max = var2;
		return this;
	}
}