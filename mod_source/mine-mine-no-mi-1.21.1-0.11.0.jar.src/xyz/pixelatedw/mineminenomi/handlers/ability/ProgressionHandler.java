package xyz.pixelatedw.mineminenomi.handlers.ability;

import com.google.common.collect.ImmutableCollection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCoreUnlockWrapper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.triggers.UnlockAbilityTrigger;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncHakiDataPacket;

public class ProgressionHandler {
	private static boolean checkCategoryForNewUnlocks(LivingEntity var0, AbilityCategory var1) {
		LivingEntity var2 = var0;
		final LivingEntity var2_f1 = var2;
		Function var3 = (Function) ((Function<AbilityCore, Boolean>) ((l0) -> {
			return Boolean.valueOf(AbilityHelper.checkAndUnlockAbility(var2_f1,l0));
		}));
		Predicate<Boolean> var4 = (Boolean l0) -> {
			return l0.booleanValue();
		};
		return (((Long)(((java.util.stream.Stream)(((java.util.stream.Stream)(ModValues.abilityCategoryMap.get(var1).stream().map(var3))).filter((Predicate)(var4)))).collect((Collector)(Collectors.counting())))).longValue()) != (0L);
	}
	public static void checkForPossibleFruitAbilities(LivingEntity var0) {
		Item var1 = ((Item)(DevilFruitData.get(var0).map((Function)(((Function<DevilFruitData, Item>)(DevilFruitData::getDevilFruitItem)))).orElse(Items.AIR)));
		ItemStack var2 = new ItemStack((ItemLike)(var1));
		if (!(var2.isEmpty())){
			Iterator var3 = ((AkumaNoMiItem)(var2.getItem())).getAbilities().iterator();
			do{
				if (var3.hasNext()){
					AbilityHelper.checkAndUnlockAbility(var0,((AbilityCore)(var3.next())));
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	public static void checkAllForNewUnlocks(Player var0, boolean var1) {
		Player var2 = var0;
		final Player var2_f1 = var2;
		Function var3 = (Function) ((Function<AbilityCore, Boolean>) ((l0) -> {
			return Boolean.valueOf(AbilityHelper.checkAndUnlockAbility((LivingEntity)(var2_f1),l0));
		}));
		Predicate<Boolean> var4 = (Boolean l0) -> {
			return l0.booleanValue();
		};
		long var5 = ((Long)(((java.util.stream.Stream)(((java.util.stream.Stream)(WyRegistry.ABILITIES.stream().map(var3))).filter((Predicate)(var4)))).collect((Collector)(Collectors.counting())))).longValue();
		if ((var1) && ((var5) != (0L))){
			ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var0)),var0);
		}
	}
	public static void checkForDevilFruitUnlocks(Player var0) {
		if (checkCategoryForNewUnlocks((LivingEntity)(var0),AbilityCategory.DEVIL_FRUITS)){
			ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var0)),var0);
		}
	}
	public static void checkForEquipmentUnlocks(LivingEntity var0) {
		if ((checkCategoryForNewUnlocks(var0,AbilityCategory.EQUIPMENT)) && (var0 instanceof ServerPlayer)){
			ServerPlayer var1 = ((ServerPlayer)(var0));
			ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var1)),(Player)(var1));
		}
	}
	public static void checkForFactionUnlocks(Player var0) {
		if (checkCategoryForNewUnlocks((LivingEntity)(var0),AbilityCategory.FACTION)){
			ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var0)),var0);
		}
	}
	public static void checkForStyleUnlocks(Player var0) {
		if (checkCategoryForNewUnlocks((LivingEntity)(var0),AbilityCategory.STYLE)){
			ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var0)),var0);
		}
	}
	public static void checkForRacialUnlocks(Player var0) {
		if (checkCategoryForNewUnlocks((LivingEntity)(var0),AbilityCategory.RACIAL)){
			ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var0)),var0);
		}
	}
	public static void checkForHakiUnlocks(Player var0) {
		if (checkCategoryForNewUnlocks((LivingEntity)(var0),AbilityCategory.HAKI)){
			ModNetwork.sendTo(new SSyncHakiDataPacket((LivingEntity)(var0)),var0);
			ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var0)),var0);
		}
	}
	public static void forceAbilityUnlockAdvancementChecks(ServerPlayer var0) {
		AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			Iterator var2 = var1.getUnlockedAbilities().iterator();
			do{
				if (var2.hasNext()){
					AbilityCoreUnlockWrapper var3 = ((AbilityCoreUnlockWrapper)(var2.next()));
					((UnlockAbilityTrigger)(ModAdvancements.UNLOCK_ABILITY.get())).trigger(var0,var3.getAbilityCore());
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
}