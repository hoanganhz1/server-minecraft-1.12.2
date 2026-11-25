package xyz.pixelatedw.mineminenomi.abilities.saraaxolotl;

import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class HeartRegenAbility extends PassiveAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<HeartRegenAbility>> INSTANCE;
	private int ticksWithoutHeart;

	public HeartRegenAbility(AbilityCore<HeartRegenAbility> var1) {
		super((AbilityCore)(var1));
		this.ticksWithoutHeart = 0;
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::duringPassiveEvent))));
	}
	private void duringPassiveEvent(LivingEntity var1) {
		EntityStatsData var2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			if (!(var2.hasHeart())){
				this.ticksWithoutHeart = (this.ticksWithoutHeart) + (1);
				if ((this.ticksWithoutHeart) == (100)){
					var2.setHeart(true);
					this.ticksWithoutHeart = 0;
				}else{
					return;
				}
			}
			return;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HeartRegenAbility::new)))).setHidden().build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("heart_regen","Heart Regen",var0));
	}
}