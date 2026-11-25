package xyz.pixelatedw.mineminenomi.abilities.mera;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class MeraHelper {
	public static Result canUseMeraAbilities(LivingEntity var0, IAbility var1) {
		Function<AbilityData, DaiEnkaiAbility> var2 = (l0) -> {
			return ((DaiEnkaiAbility)(l0.getEquippedAbility(((AbilityCore)(DaiEnkaiAbility.INSTANCE.get())))));
		};
		DaiEnkaiAbility var3 = ((DaiEnkaiAbility)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var2)).orElse(null)));
		if (((var3) != (null)) && (!((!(var3.isCharging())) && (!(var3.isContinuous()))))){
			return Result.fail(ModI18nAbilities.MESSAGE_NEED_NOT_DAI_ENKAI);
		}else{
			return Result.success();
		}
	}
}