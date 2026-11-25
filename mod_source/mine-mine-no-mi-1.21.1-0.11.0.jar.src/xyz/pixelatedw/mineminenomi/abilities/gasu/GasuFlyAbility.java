package xyz.pixelatedw.mineminenomi.abilities.gasu;

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
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GasuFlyAbility extends FlyAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GasuFlyAbility>> INSTANCE;

	public GasuFlyAbility(AbilityCore<GasuFlyAbility> var1) {
		super((AbilityCore)(var1));
	}
	public Supplier<ParticleEffect<?>> getParticleEffects() {
		return ModParticleEffects.GASU_FLY;
	}
	public int getHeightDifference(Player var1) {
		return 40;
	}
	public float getSpeed(Player var1) {
		float var2 = 0.2875F;
		if (!(WyHelper.isInCombat((LivingEntity)(var1)))){
			var2 = (var2) + ((var2) * (0.3F));
		}
		return var2;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to fly"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GasuFlyAbility::new)))).addDescriptionLine(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gasu_special_fly","Gasu Special Fly",var0));
	}
}