package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.abilities.FlyAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SunaFlyAbility extends FlyAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SunaFlyAbility>> INSTANCE;

	public SunaFlyAbility(AbilityCore<SunaFlyAbility> var1) {
		super((AbilityCore)(var1));
	}
	public Result canUse(LivingEntity var1) {
		if (SunaHelper.isWet(var1)){
			return Result.fail(null);
		}else{
			return super.canUse(var1);
		}
	}
	public Supplier<ParticleEffect<?>> getParticleEffects() {
		return ModParticleEffects.SUNA_FLY;
	}
	public int getHeightDifference(Player var1) {
		return 30;
	}
	public float getSpeed(Player var1) {
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		if ((var2) == (null)){
			return 0F;
		}else{
			GrandeSablesAbility var3 = ((GrandeSablesAbility)(var2.getEquippedAbility(((AbilityCore)(GrandeSablesAbility.INSTANCE.get())))));
			BarjanAbility var4 = ((BarjanAbility)(var2.getEquippedAbility(((AbilityCore)(BarjanAbility.INSTANCE.get())))));
			if ((((var3) != (null)) && (var3.isContinuous())) || (((var4) != (null)) && (var4.isContinuous()))){
				return 0.8F;
			}else{
				float var5 = 0.2432F;
				if (SunaHelper.isFruitBoosted((LivingEntity)(var1))){
					var5 = (var5) * (2F);
				}
				if (!(WyHelper.isInCombat((LivingEntity)(var1)))){
					var5 = (var5) + ((var5) * (0.3F));
				}
				return var5;
			}
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to fly, cannot be used while the user is wet"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SunaFlyAbility::new)))).addDescriptionLine(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("suna_special_fly","Suna Special Fly",var0));
	}
}