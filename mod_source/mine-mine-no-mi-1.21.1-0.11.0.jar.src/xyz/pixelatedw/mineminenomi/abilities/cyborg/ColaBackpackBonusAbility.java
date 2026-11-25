package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IOnRemoveEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateColaAmountPacket;

public class ColaBackpackBonusAbility extends PassiveAbility {
	// Fields
	public static final int EXTRA_COLA = 500;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ColaBackpackBonusAbility>> INSTANCE;

	public ColaBackpackBonusAbility(AbilityCore<ColaBackpackBonusAbility> var1) {
		super((AbilityCore)(var1));
		this.addRemoveEvent((PassiveAbility$IOnRemoveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IOnRemoveEvent)(this::removeEvent))));
	}
	private static boolean canUnlock(LivingEntity var0) {
		if (ItemsHelper.hasItemInSlot(var0,EquipmentSlot.CHEST,((Item)(ModArmors.COLA_BACKPACK.get())))){
			return true;
		}else{
			return false;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.EQUIPMENT,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ColaBackpackBonusAbility::new)))).setHidden().setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(ColaBackpackBonusAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("cola_backpack_bonus","Cola Backpack Bonus",var0));
	}

	private void removeEvent(LivingEntity var1, IAbility var2) {
		if (var1 instanceof ServerPlayer){
			ServerPlayer var3 = ((ServerPlayer)(var1));
			LivingEntity var4 = var1;
			ServerPlayer var5 = var3;
			final LivingEntity var4_f1 = var4;
			final ServerPlayer var5_f2 = var5;
			Consumer<EntityStatsData> var6 = (l0) -> {
			l0.setCola(Mth.clamp(l0.getCola(),0,(l0.getMaxCola()) - (500)));
			ModNetwork.sendTo(new SUpdateColaAmountPacket(var4_f1),(Player)(var5_f2));
		};
			((java.util.Optional)(EntityStatsData.get((LivingEntity)(var3)))).ifPresent((Consumer)(var6));
		}
	}}