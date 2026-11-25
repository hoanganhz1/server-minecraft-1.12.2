package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ContinuousComponentTooltip {
	// Fields
	private float min;
	private float max;

	public ContinuousComponentTooltip() {
		this.min = -1F;
		this.max = -1F;
	}
	public static void generate(AbilityTooltip var0, Consumer<ContinuousComponentTooltip> var1) {
		ContinuousComponentTooltip var2 = new ContinuousComponentTooltip();
		var1.accept(var2);
		float var3 = var2.min;
		float var4 = var2.max;
		float var5 = 0.0F;
		float var6 = 0.0F;
		if (((var3) == (var4)) && ((var3) == (-1F))){
			var5 = (1.0F/0.0F);
			var6 = (1.0F/0.0F);
		}else{
			var5 = (float)(Math.round((var3) / (20F)));
			var6 = (float)(Math.round((var4) / (20F)));
		}
		var0.set(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get())),new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_CONTINUE),var5,var6).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS).buildComponent());
	}
	public ContinuousComponentTooltip holdTime(float var1) {
		return this.holdTime(var1,var1);
	}
	public ContinuousComponentTooltip holdTime(float var1, float var2) {
		this.min = var1;
		this.max = var2;
		return this;
	}
}