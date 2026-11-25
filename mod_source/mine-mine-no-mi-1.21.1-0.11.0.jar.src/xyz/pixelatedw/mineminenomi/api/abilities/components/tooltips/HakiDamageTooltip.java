package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class HakiDamageTooltip {
	// Fields
	public static final int ORDER = 500;
	private float baseDamage;
	private float percentage;
	private boolean isPunch;

	public static void generate(AbilityTooltip var0, Consumer<HakiDamageTooltip> var1) {
		HakiDamageTooltip var2 = new HakiDamageTooltip();
		var1.accept(var2);
		float var3 = var2.percentage;
		if ((var2.baseDamage) > (0F)){
			var3 = (var3) - (var2.baseDamage);
		}
		if ((var3) > (0F)){
			if ((var2.baseDamage) > (0F)){
				var0.add(500,new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_BASE_DAMAGE),var2.baseDamage).buildComponent());
			}
			Component var4 = (var2.isPunch) ? (ModI18nAbilities.DESCRIPTION_STAT_NAME_BONUS_PUNCH_DAMAGE) : (ModI18nAbilities.DESCRIPTION_STAT_NAME_BONUS_DAMAGE);
			var0.add(500,new AbilityStat$Builder((Component)(var4),(var3) * (100F)).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_PERCENT).buildComponent());
		}
	}
	public HakiDamageTooltip setPunch() {
		this.isPunch = true;
		return this;
	}
	public HakiDamageTooltip setBaseDamage(float var1) {
		this.baseDamage = var1;
		return this;
	}
	public HakiDamageTooltip damage(float var1) {
		this.percentage = var1;
		return this;
	}
}