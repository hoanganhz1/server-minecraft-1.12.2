package xyz.pixelatedw.mineminenomi.abilities;

import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IOnEquipEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class EmptyHandsAbility extends PassiveAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<EmptyHandsAbility>> INSTANCE;

	public EmptyHandsAbility(AbilityCore<EmptyHandsAbility> var1) {
		super((AbilityCore)(var1));
		this.addEquipEvent((PassiveAbility$IOnEquipEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IOnEquipEvent)(this::onEquipEvent))));
	}
	private void onEquipEvent(LivingEntity var1, IAbility var2) {
		if (((var1) != (null)) && (!(var1.level().isClientSide))){
			this.pauseTickComponent.setPause(var1,true);
		}
	}
	private static boolean canUnlock(LivingEntity var0) {
		if (var0 instanceof Player){
			Player var1 = ((Player)(var0));
			if ((((QuestData)(((java.util.Optional)(QuestData.get(var1))).orElse((Object)(null))))) == (null)){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("When active items picked from ground will never be put in the user's main hand."),(Object)(null)),ImmutablePair.of((Object)("If there's space in the inventory it will be put there, othewise it won't get picked up at all."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(EmptyHandsAbility::new)))).addDescriptionLine(lv1_3).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(EmptyHandsAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("empty_hands","Empty Hands",var0));
	}
}