package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class PunchDamageTooltip {
	// Fields
	public static final int ORDER = 500;
	private float percentage;
	private float min;
	private float max;

	public static void generate(AbilityTooltip var0, Consumer<PunchDamageTooltip> var1) {
		PunchDamageTooltip var2 = new PunchDamageTooltip();
		var1.accept(var2);
		float var3 = (var2.min) * (0.4F);
		float var4 = (var2.max) * (0.4F);
		if ((var4) > (0F)){
			var0.set(500,new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_PUNCH_DAMAGE),var3,var4).buildComponent());
		}
		if ((var2.percentage) > (0F)){
			var0.set(500,new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_PUNCH_DAMAGE),(var2.percentage) * (100F)).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_PERCENT).buildComponent());
		}
	}
	public PunchDamageTooltip damage(float var1) {
		return this.damage(var1,var1);
	}
	public PunchDamageTooltip damage(float var1, float var2) {
		this.min = var1;
		this.max = var2;
		return this;
	}
	public PunchDamageTooltip damagePercentage(float var1) {
		this.percentage = var1;
		return this;
	}
}