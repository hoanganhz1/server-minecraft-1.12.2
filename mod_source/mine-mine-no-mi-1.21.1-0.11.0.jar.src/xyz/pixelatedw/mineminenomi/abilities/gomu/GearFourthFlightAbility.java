package xyz.pixelatedw.mineminenomi.abilities.gomu;

import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GearFourthFlightAbility extends PropelledFlightAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GearFourthFlightAbility>> INSTANCE;

	public GearFourthFlightAbility(AbilityCore<GearFourthFlightAbility> var1) {
		super((AbilityCore)(var1));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::canFly))));
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::onDuringPassive))));
	}
	private void onDuringPassive(LivingEntity var1) {
	}
	public float getMaxSpeed(LivingEntity var1) {
		return (var1.isSprinting()) ? (1.0121026F) : (0.6720425F);
	}
	protected float getAcceleration(LivingEntity var1) {
		return (var1.isSprinting()) ? (0.007229304F) : (0.004800303F);
	}
	protected int getHeightDifference(LivingEntity var1) {
		return 128;
	}
	protected Vec3 getMovementVector(LivingEntity var1) {
		int var2 = (var1.onGround()) ? (1) : (0);
		Vec3 var3 = var1.getLookAngle();
		double var4 = (((var3.x) * ((double)(this.speed))) * ((1D) - ((double)(var2)))) * ((double)(var1.zza));
		double var5 = ((((double)(var2)) * (0.6D)) + ((((var3.y) * ((double)(this.speed))) * ((1D) - ((double)(var2)))) * ((double)(var1.zza)))) + ((Math.cos(((double)(var1.tickCount)) / ((4D) - (((double)(this.speed)) * (1.25D))))) / (5D));
		double var6 = (((var3.z) * ((double)(this.speed))) * ((1D) - ((double)(var2)))) * ((double)(var1.zza));
		return new Vec3(var4,var5,var6);
	}
	private Result canFly(LivingEntity var1, IAbility var2) {
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return Result.fail(null);
		}else{
			GearFourthAbility var4 = ((GearFourthAbility)(var3.getEquippedAbility(((AbilityCore)(GearFourthAbility.INSTANCE.get())))));
			if (((((var4) != (null)) && (var4.isContinuous())) ? (1) : (0)) != (0)){
				return Result.success();
			}else{
				return Result.fail(null);
			}
		}
	}
	public boolean canRenderGauge(Player var1) {
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		if ((var2) == (null)){
			return false;
		}else{
			GearFourthAbility var3 = ((GearFourthAbility)(var2.getEquippedAbility(((AbilityCore)(GearFourthAbility.INSTANCE.get())))));
			return ((var3) != (null)) && (var3.isContinuous());
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GearFourthFlightAbility::new)))).setHidden().build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gear_fourth_flight","Gear Fourth Flight",var0));
	}
}