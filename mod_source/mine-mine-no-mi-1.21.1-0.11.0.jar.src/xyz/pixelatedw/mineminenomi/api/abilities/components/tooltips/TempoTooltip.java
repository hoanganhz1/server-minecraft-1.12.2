package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.Arrays;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class TempoTooltip {
	// Fields
	private boolean requiresCloud;
	private WeatherBallKind[] balls;

	public static void generate(AbilityTooltip var0, Consumer<TempoTooltip> var1) {
		TempoTooltip var2 = new TempoTooltip();
		var1.accept(var2);
		WeatherBallKind var3 = null;
		boolean var4 = true;
		WeatherBallKind[] var5 = var2.balls;
		int var6 = var5.length;
		int var7 = 0;
		do{
			if ((var7) < (var6)){
				WeatherBallKind var8 = var5[var7];
				if ((var3) == (null)){
					var3 = var8;
				}else{
					if (!(var8.equals(var3))){
						var4 = false;
						break;
					}
				}
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
		MutableComponent var8_1 = null;
		if (var4){
			Object[] var9 = new Object[]{Integer.valueOf(var2.balls.length),var2.balls[0].toString()};
			var8_1 = Component.translatable(ModI18nAbilities.DESCRIPTION_TEMPO_SAME_TYPE_COMBINATION,var9);
		}
		if ((var2.balls.length) == (2)){
			Object[] var9_1 = new Object[]{var2.balls[0].toString(),var2.balls[1].toString()};
			var8_1 = Component.translatable(ModI18nAbilities.DESCRIPTION_TEMPO_COMBINATION_2,var9_1);
		}else{
			if ((var2.balls.length) == (3)){
				Object[] var9_2 = new Object[]{var2.balls[0].toString(),var2.balls[1].toString(),var2.balls[2].toString()};
				var8_1 = Component.translatable(ModI18nAbilities.DESCRIPTION_TEMPO_COMBINATION_3,var9_2);
			}
		}
		if (((var8_1) != (null)) && (var2.requiresCloud)){
			var8_1.append("\n");
			var8_1.append(ModI18nAbilities.DESCRIPTION_TEMPO_REQUIRES_WEATHER_CLOUD);
		}
		var0.set(1900,(Component)(var8_1));
	}
	public TempoTooltip requiresCloud() {
		this.requiresCloud = true;
		return this;
	}
	public TempoTooltip requiredBalls(int var1, WeatherBallKind var2) {
		this.balls = new WeatherBallKind[var1];
		Arrays.fill((Object[])(this.balls),(Object)(var2));
		return this;
	}
	public TempoTooltip requiredBalls(WeatherBallKind... var1) {
		this.balls = var1;
		return this;
	}
}