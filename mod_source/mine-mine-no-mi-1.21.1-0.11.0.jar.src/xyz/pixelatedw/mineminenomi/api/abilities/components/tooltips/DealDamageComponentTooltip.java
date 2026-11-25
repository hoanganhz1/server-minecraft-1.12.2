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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class DealDamageComponentTooltip {
	// Fields
	private float percentage;
	private float min;
	private float max;

	public static void generate(AbilityTooltip var0, Consumer<DealDamageComponentTooltip> var1) {
		DealDamageComponentTooltip var2 = new DealDamageComponentTooltip();
		var1.accept(var2);
		DealDamageComponent var3 = ((DealDamageComponent)(var0.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get()))).orElse(null)));
		float var4 = (var2.min) * (0.4F);
		float var5 = (var2.max) * (0.4F);
		AbilityStat$Builder var6 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE),var4,var5);
		if ((var3) != (null)){
			float var7 = (var3.getBonusManager().applyBonus(var4)) - (var4);
			float var8 = (var3.getBonusManager().applyBonus(var5)) - (var5);
			float var9 = (var7) + (var8);
			var6 = var6.withBonus(var7,var8,((var9) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((var9) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL)));
		}
		var0.set(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get())),var6.buildComponent());
		if ((var2.percentage) > (0F)){
			var0.set(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get())),new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE),(var2.percentage) * (100F)).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_PERCENT).buildComponent());
		}
	}
	public DealDamageComponentTooltip damage(float var1) {
		return this.damage(var1,var1);
	}
	public DealDamageComponentTooltip damage(float var1, float var2) {
		this.min = var1;
		this.max = var2;
		return this;
	}
	public DealDamageComponentTooltip damagePercentage(float var1) {
		this.percentage = var1;
		return this;
	}
}