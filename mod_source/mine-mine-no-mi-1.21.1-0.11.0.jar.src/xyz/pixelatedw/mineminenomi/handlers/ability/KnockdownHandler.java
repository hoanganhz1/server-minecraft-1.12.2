package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.KnockdownAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class KnockdownHandler {
	// Fields
	private static final int KNOCKDOWN_TIMER = 1800;

	public static boolean tryKnockdown(LivingEntity var0, LivingEntity var1, float var2) {
		if (((var0) != (null)) && ((var0.isAlive()) && (((var1) != (null)) && (var1.isAlive())))){
			if (((((var1.getHealth()) - (var2)) <= (0F)) ? (1) : (0)) == (0)){
				return false;
			}else{
				Function<AbilityData, KnockdownAbility> var3 = (l0) -> {
			return ((KnockdownAbility)(l0.getPassiveAbility(((AbilityCore)(KnockdownAbility.INSTANCE.get())))));
		};
				Function<KnockdownAbility, Boolean> var4 = (l0) -> {
			return Boolean.valueOf((!(l0.isAbilityPaused())) && (!(l0.isAbilityDisabled())));
		};
				if (!(((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var3)))).map((Function)(var4)).orElse(Boolean.valueOf(false)))).booleanValue())){
					return false;
				}else{
					var1.addEffect(new MobEffectInstance((Holder)(ModEffects.UNCONSCIOUS),1800,1));
					var1.setHealth(5F);
					var1.clearFire();
					return true;
				}
			}
		}else{
			return false;
		}
	}
}