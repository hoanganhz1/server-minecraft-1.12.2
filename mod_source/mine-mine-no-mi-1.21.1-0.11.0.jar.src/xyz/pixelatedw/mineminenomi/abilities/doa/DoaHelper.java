package xyz.pixelatedw.mineminenomi.abilities.doa;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;

public class DoaHelper {
	public static boolean isInsideDoor(LivingEntity var0) {
		Function<AbilityData, AirDoorAbility> var1 = (l0) -> {
			return ((AirDoorAbility)(l0.getEquippedAbility(((AbilityCore)(AirDoorAbility.INSTANCE.get())))));
		};
		Function<AirDoorAbility, Boolean> var2 = (l0) -> {
			return Boolean.valueOf(l0.isContinuous());
		};
		return ((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var1)))).map((Function)(var2)).orElse(Boolean.valueOf(false)))).booleanValue();
	}
}