package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class StackComponentTooltip {
	// Fields
	private float min;
	private float max;

	public static void generate(AbilityTooltip var0, Consumer<StackComponentTooltip> var1) {
		StackComponentTooltip var2 = new StackComponentTooltip();
		var1.accept(var2);
		AbilityStat$Builder var3 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_STACKS),var2.min,var2.max);
		var0.set(((AbilityComponentKey)(ModAbilityComponents.STACK.get())),var3.buildComponent());
	}
	public StackComponentTooltip stacks(float var1) {
		return this.stacks(var1,var1);
	}
	public StackComponentTooltip stacks(float var1, float var2) {
		this.min = var1;
		this.max = var2;
		return this;
	}
}