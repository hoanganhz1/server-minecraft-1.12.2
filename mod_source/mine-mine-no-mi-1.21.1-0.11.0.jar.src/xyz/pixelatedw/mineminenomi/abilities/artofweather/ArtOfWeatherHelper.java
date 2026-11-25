package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import java.awt.Color;
import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public class ArtOfWeatherHelper {
	// Fields
	public static final Color AOW_LIGHTNING_COLOR = new Color(253,208,35,150);
	private static final int CHARGE_SLOTS = 3;

	public static String getChargesString(ItemStack var0) {
		WeatherBallKind[] var1 = ((WeatherBallKind[])(var0.get(ModDataComponents.WEATHER_BALL_CHARGES)));
		if ((var1) == (null)){
			var1 = new WeatherBallKind[3];
		}
		String[] var2 = new String[3];
		int var3 = 0;
		do{
			if ((var3) < (var1.length)){
				WeatherBallKind var4 = var1[var3];
				if ((var4) == (null)){
					var2[var3] = "§r";
				}else{
					switch (ArtOfWeatherHelper$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$WeatherBallKind[var4.ordinal()]){
					case 1:

					case 2:

					case 3:

					default:
						var2[var3] = "§r";
					}
				}
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		StringBuilder var4_1 = new StringBuilder();
		int var5 = var1.length;
		int var6 = 0;
		do{
			if ((var6) < (var5)){
				var4_1.append(String.format("%s█§r",new Object[]{var2[var6]}));
				if ((var6) < ((var5) - (1))){
					var4_1.append("━");
				}
				var6++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var4_1.toString();
	}
	public static WeatherBallKind[] getCharges(ItemStack var0) {
		return ((WeatherBallKind[])(var0.get(ModDataComponents.WEATHER_BALL_CHARGES)));
	}
	public static boolean chargeWeatherBall(ItemStack var0, WeatherBallKind var1) {
		if (!(ItemsHelper.isClimaTact(var0))){
			return false;
		}else{
			WeatherBallKind[] var2 = ((WeatherBallKind[])(var0.get(ModDataComponents.WEATHER_BALL_CHARGES)));
			if ((var2) == (null)){
				var2 = new WeatherBallKind[3];
			}
			int var3 = 0;
			do{
				if ((var3) < (3)){
					if ((var2[var3]) == (null)){
						var2[var3] = var1;
						break;
					}else{
						var3++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			var0.set(ModDataComponents.WEATHER_BALL_CHARGES,var2);
			return true;
		}
	}
	public static void emptyCharges(ItemStack var0) {
		var0.set(ModDataComponents.WEATHER_BALL_CHARGES,new WeatherBallKind[3]);
	}
}