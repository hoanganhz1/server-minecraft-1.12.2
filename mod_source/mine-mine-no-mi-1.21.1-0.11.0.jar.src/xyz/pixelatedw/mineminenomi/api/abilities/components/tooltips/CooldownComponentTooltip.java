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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class CooldownComponentTooltip {
	// Fields
	private float min;
	private float max;

	public static void generate(AbilityTooltip var0, Consumer<CooldownComponentTooltip> var1) {
		CooldownComponentTooltip var2 = new CooldownComponentTooltip();
		var1.accept(var2);
		CooldownComponent var3 = ((CooldownComponent)(var0.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))).orElse(null)));
		float var4 = var2.min;
		float var5 = var2.max;
		float var6 = (float)(Math.round((var4) / (20F)));
		float var7 = (float)(Math.round((var5) / (20F)));
		AbilityStat$Builder var8 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_COOLDOWN),var6,var7).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS);
		if ((var3) != (null)){
			float var9 = (float)(Math.round(((var3.getBonusManager().applyBonus(var4)) - (var4)) / (20F)));
			float var10 = (float)(Math.round(((var3.getBonusManager().applyBonus(var5)) - (var5)) / (20F)));
			var8.withBonus(var9,var10,AbilityStat$AbilityStatType.from((var9) + (var10),true));
		}
		var0.set(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get())),var8.buildComponent());
	}
	public CooldownComponentTooltip cooldown(float var1) {
		return this.cooldown(var1,var1);
	}
	public CooldownComponentTooltip cooldown(float var1, float var2) {
		this.min = var1;
		this.max = var2;
		return this;
	}
}