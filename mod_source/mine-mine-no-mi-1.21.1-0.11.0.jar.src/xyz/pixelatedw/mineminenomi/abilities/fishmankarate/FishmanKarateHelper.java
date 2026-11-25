package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.abilities.sui.FreeSwimmingAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;

public class FishmanKarateHelper {
	public static boolean isInWater(LivingEntity var0) {
		boolean var1 = var0.isInWater();
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return var1;
		}else{
			FreeSwimmingAbility var3 = ((FreeSwimmingAbility)(var2.getEquippedAbility(((AbilityCore)(FreeSwimmingAbility.INSTANCE.get())))));
			if (((var3) != (null)) && ((var3.isContinuous()) && (var3.isSwimming()))){
				return true;
			}else{
				return var1;
			}
		}
	}
}