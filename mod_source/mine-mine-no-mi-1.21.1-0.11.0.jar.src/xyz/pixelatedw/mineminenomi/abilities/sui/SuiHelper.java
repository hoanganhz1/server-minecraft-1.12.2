package xyz.pixelatedw.mineminenomi.abilities.sui;

import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class SuiHelper {
	public static Result requiresFreeSwimming(LivingEntity var0, IAbility var1) {
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return Result.fail(null);
		}else{
			FreeSwimmingAbility var3 = ((FreeSwimmingAbility)(var2.getEquippedAbility(((AbilityCore)(FreeSwimmingAbility.INSTANCE.get())))));
			if (((var3) != (null)) && ((var3.isContinuous()) && (var3.isSwimming()))){
				return Result.success();
			}else{
				Object[] var4 = new Object[]{((AbilityCore)(FreeSwimmingAbility.INSTANCE.get())).getLocalizedName().getString()};
				return Result.fail((Component)(Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var4)));
			}
		}
	}
}