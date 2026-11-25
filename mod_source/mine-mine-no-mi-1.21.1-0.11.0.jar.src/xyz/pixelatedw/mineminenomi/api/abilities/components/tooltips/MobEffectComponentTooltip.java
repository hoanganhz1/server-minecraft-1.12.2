package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class MobEffectComponentTooltip {
	// Fields
	private List<Pair<Holder<MobEffect>, Integer>> effects;

	public MobEffectComponentTooltip() {
		this.effects = new ArrayList();
	}
	public static void generate(AbilityTooltip var0, Consumer<MobEffectComponentTooltip> var1) {
		MobEffectComponentTooltip var2 = new MobEffectComponentTooltip();
		var1.accept(var2);
		LinkedList var3 = new LinkedList();
		var3.add(ModI18nAbilities.DESCRIPTION_STAT_NAME_EFFECTS.copy().withStyle(ChatFormatting.GREEN));
		Iterator var4 = var2.effects.iterator();
		do{
			if (var4.hasNext()){
				Pair var5 = ((Pair)(var4.next()));
				var3.add(new AbilityStat$Builder((Component)(((MobEffect)(((Holder)(var5.getKey())).value())).getDisplayName()),((float)(((Integer)(var5.getValue())).intValue())) / (20F)).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS).buildComponent(2));
				continue;
			}else{
				break;
			}
		} while (true);
		var0.set(((AbilityComponentKey)(ModAbilityComponents.MOB_EFFECT.get())),(Collection)(var3));
	}
	public MobEffectComponentTooltip addEffect(Holder<MobEffect> var1, int var2) {
		this.effects.add(Pair.of(var1,Integer.valueOf(var2)));
		return this;
	}
}