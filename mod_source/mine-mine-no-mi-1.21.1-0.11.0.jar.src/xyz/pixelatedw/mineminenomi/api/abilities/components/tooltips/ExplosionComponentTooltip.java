package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ExplosionComponentTooltip {
	// Fields
	private float minRange;
	private float maxRange;
	private float minDamage;
	private float maxDamage;

	public static void generate(AbilityTooltip var0, Consumer<ExplosionComponentTooltip> var1) {
		ExplosionComponentTooltip var2 = new ExplosionComponentTooltip();
		var1.accept(var2);
		int var3 = ((var2.maxDamage) > (0F)) ? (1) : (0);
		int var4 = ((var2.maxRange) > (0F)) ? (1) : (0);
		if (((((var3) != (0)) || ((var4) != (0))) ? (1) : (0)) == (0)){
			return;
		}else{
			LinkedList var5 = new LinkedList();
			String var6 = ModI18nAbilities.DESCRIPTION_STAT_NAME_EXPLOSION.getString();
			String var7 = "§a" + var6 + "§r";
			var5.add(Component.literal(var7));
			if ((var4) != (0)){
				var5.add(new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_RANGE),var2.minRange,var2.maxRange).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_BLOCKS).buildComponent(2));
			}
			if ((var3) != (0)){
				var5.add(new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE),(var2.minDamage) * (0.4F),(var2.maxDamage) * (0.4F)).buildComponent(2));
			}
			var0.set(((AbilityComponentKey)(ModAbilityComponents.EXPLOSION.get())),(Collection)(var5));
			return;
		}
	}
	public ExplosionComponentTooltip damage(float var1) {
		return this.damage(var1,var1);
	}
	public ExplosionComponentTooltip damage(float var1, float var2) {
		this.minDamage = var1;
		this.maxDamage = var1;
		return this;
	}
	public ExplosionComponentTooltip range(float var1) {
		return this.range(var1,var1);
	}
	public ExplosionComponentTooltip range(float var1, float var2) {
		this.minRange = var1;
		this.maxRange = var2;
		return this;
	}
}