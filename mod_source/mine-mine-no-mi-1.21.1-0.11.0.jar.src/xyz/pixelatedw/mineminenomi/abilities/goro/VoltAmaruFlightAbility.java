package xyz.pixelatedw.mineminenomi.abilities.goro;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.PropelledFlightAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class VoltAmaruFlightAbility extends PropelledFlightAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<VoltAmaruFlightAbility>> INSTANCE;

	public VoltAmaruFlightAbility(AbilityCore<VoltAmaruFlightAbility> var1) {
		super((AbilityCore)(var1));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::canFly))));
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::onDuringPassive))));
	}
	private void onDuringPassive(LivingEntity var1) {
		if (var1 instanceof Player){
			Player var2 = ((Player)(var1));
			if ((!(this.isRecovering)) && (((AbilityHelper.getDifferenceToFloor((Entity)(var1))) < (5D)) && (var2.getAbilities().flying))){
				AbilityHelper.setDeltaMovement((Entity)(var1),var1.getDeltaMovement().add(0D,1D,0D).multiply(1D,0.25D,1D));
			}
			return;
		}else{
			return;
		}
	}
	public float getMaxSpeed(LivingEntity var1) {
		return (var1.isSprinting()) ? (0.5592841F) : (0.37202382F);
	}
	protected float getAcceleration(LivingEntity var1) {
		return (var1.isSprinting()) ? (0.003994886F) : (0.002657313F);
	}
	protected int getHeightDifference(LivingEntity var1) {
		return 128;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(VoltAmaruFlightAbility::new)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("volt_amaru_flight","Volt Amaru Flight",var0));
	}
	private Result canFly(LivingEntity var1, IAbility var2) {
		Function<AbilityData, VoltAmaruAbility> var3 = (l0) -> {
			return ((VoltAmaruAbility)(l0.getEquippedAbility(((AbilityCore)(VoltAmaruAbility.INSTANCE.get())))));
		};
		Function<VoltAmaruAbility, Boolean> var4 = (l0) -> {
			return Boolean.valueOf(l0.isContinuous());
		};
		if (((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)))).map((Function)(var4)).orElse(Boolean.valueOf(false)))).booleanValue()){
			return Result.success();
		}else{
			return Result.fail(null);
		}
	}
}