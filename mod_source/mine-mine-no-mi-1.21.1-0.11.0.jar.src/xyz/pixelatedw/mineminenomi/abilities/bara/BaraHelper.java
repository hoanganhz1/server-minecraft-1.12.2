package xyz.pixelatedw.mineminenomi.abilities.bara;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class BaraHelper {
	public static Result hasLimbs(LivingEntity var0, IAbility var1) {
		if (var0.hasEffect((Holder)(ModEffects.NO_HANDS))){
			return Result.fail(ModI18nAbilities.MESSAGE_NO_LIMBS);
		}else{
			return Result.success();
		}
	}
}