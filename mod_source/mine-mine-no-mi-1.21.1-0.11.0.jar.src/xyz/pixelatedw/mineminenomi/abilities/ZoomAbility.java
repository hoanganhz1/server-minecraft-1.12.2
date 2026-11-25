package xyz.pixelatedw.mineminenomi.abilities;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.extensions.IPlayerExtension;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ZoomAbility extends Ability {
	// Fields
	public static final Supplier<AbilityCore<ZoomAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;

	public ZoomAbility(AbilityCore<ZoomAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuousEvent)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuousEvent))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::canStartAbility))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
		this.addTickEvent((Ability$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent)(this::onTickEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void onTickEvent(LivingEntity var1, IAbility var2) {
		if ((this.isContinuous()) && (!(ItemsHelper.hasItemInSlot(var1,EquipmentSlot.HEAD,((Item)(ModArmors.SNIPER_GOGGLES.get())))))){
			this.continuousComponent.stopContinuity(var1);
		}
	}
	private void startContinuousEvent(LivingEntity var1, IAbility var2) {
		if (var1 instanceof IPlayerExtension){
			((IPlayerExtension)(var1)).setScoping(true);
		}
	}
	private void endContinuousEvent(LivingEntity var1, IAbility var2) {
		if (var1 instanceof IPlayerExtension){
			((IPlayerExtension)(var1)).setScoping(false);
		}
	}
	private Result canStartAbility(LivingEntity var1, IAbility var2) {
		if (!(ItemsHelper.hasItemInSlot(var1,EquipmentSlot.HEAD,((Item)(ModArmors.SNIPER_GOGGLES.get()))))){
			return Result.fail(ModI18nAbilities.MESSAGE_NEED_SNIPER_GOGGLES);
		}else{
			return Result.success();
		}
	}
	private static boolean canUnlock(LivingEntity var0) {
		if (ItemsHelper.hasItemInSlot(var0,EquipmentSlot.HEAD,((Item)(ModArmors.SNIPER_GOGGLES.get())))){
			return true;
		}else{
			return false;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Zooms into the direction the user is looking, massively reduces inaccuracy when shooting projectiles."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.EQUIPMENT,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ZoomAbility::new)))).addDescriptionLine(lv1_3).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(ZoomAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = ModRegistry.registerAbility("zoom","Zoom",var0);
	}
}