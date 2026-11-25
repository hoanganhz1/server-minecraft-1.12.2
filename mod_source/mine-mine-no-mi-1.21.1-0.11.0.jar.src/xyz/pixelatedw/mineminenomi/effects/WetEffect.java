package xyz.pixelatedw.mineminenomi.effects;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class WetEffect extends BaseEffect {
	public WetEffect() {
		super(MobEffectCategory.NEUTRAL,0);
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		if (var1.isAlive()){
			MobEffectInstance var3 = var1.getEffect((Holder)(ModEffects.WET));
			if ((var3) == (null)){
				return false;
			}else{
				DevilFruitData var4 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var1))).orElse((Object)(null))));
				if ((var4) == (null)){
					return false;
				}else{
					if (var4.hasDevilFruit(ModFruits.SUNA_SUNA_NO_MI)){
						Predicate<IAbility> var5 = (l0) -> {
			return (l0.getCore().getCategory()) == (AbilityCategory.DEVIL_FRUITS);
		};
						AbilityHelper.disableAbilities(var1,var3.getDuration(),var5);
					}else{
						return true;
					}
				}
			}
		}
		return true;
	}
	public boolean isRemoveable() {
		return false;
	}
}