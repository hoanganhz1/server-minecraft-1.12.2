package xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.TempoAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public abstract class ChargedTempoAbility extends TempoAbility {
	public ChargedTempoAbility(AbilityCore<? extends ChargedTempoAbility> var1) {
		super((AbilityCore)(var1));
	}
	public abstract WeatherBallKind[] getTempoOrder();
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		String var3 = this.getTempoOrder()[0].toString();
		String var4 = this.getTempoOrder()[1].toString();
		String var5 = this.getTempoOrder()[2].toString();
		Object[] var6 = new Object[]{var3,var4,var5};
		var2.add(2000,(Component)(Component.translatable(ModI18nAbilities.DESCRIPTION_CHARGED_TEMPO_COMBINATION,var6)));
	}
}