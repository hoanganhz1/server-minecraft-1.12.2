package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ItemSpawnComponent;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCharacterCreatorScreenPacket;

public class ValidationHandler {
	public static void validateAllItemsForComponentChecks(LivingEntity var0) {
		AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			Iterator var2 = ItemsHelper.getAllInventoryItems(var0).iterator();
			do{
				if (var2.hasNext()){
					ItemStack var3 = ((ItemStack)(var2.next()));
					if (var3.has(ModDataComponents.SPAWNED_BY_ABILITY)){
						AbilityCore var4 = AbilityCore.get(((ResourceLocation)(var3.get(ModDataComponents.SPAWNED_BY_ABILITY))));
						if ((var4) == (null)){
							continue;
						}else{
							IAbility var5 = var1.getEquippedAbility(var4);
							if ((var5) == (null)){
								return;
							}else{
								if ((var5.hasComponent(((AbilityComponentKey)(ModAbilityComponents.ITEM_SPAWN.get())))) && (!(((ItemSpawnComponent)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.ITEM_SPAWN.get()))).get())).isActive()))){
									var3.shrink(var3.getMaxStackSize());
									continue;
								}else{
									continue;
								}
							}
						}
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static void forceSelectionScreen(Player var0) {
		if (ServerConfig.isForcedSelectionEnabled()){
			Function<EntityStatsData, Boolean> var1 = (l0) -> {
			return Boolean.valueOf((l0.hasRace()) && ((l0.hasFaction()) && (l0.hasFightingStyle())));
		};
			if (!(((Boolean)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var0)))).map((Function)(var1)).orElse(Boolean.valueOf(false)))).booleanValue())){
				boolean var2 = ServerConfig.getRaceRandomizer();
				boolean var3 = ServerConfig.getAllowSubRaceSelect();
				ModNetwork.sendTo(new SOpenCharacterCreatorScreenPacket(var2,var3),var0);
			}else{
				return;
			}
		}
	}
	public static void validateFaction(Player var0) {
		FactionsWorldData var1 = FactionsWorldData.get();
		EntityStatsData var2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			if (!(var2.isPirate())){
				Crew var3 = var1.getCrewWithMember(var0.getUUID());
				if ((var3) != (null)){
					var1.removeCrewMember(var0.level(),var3,var0.getUUID());
				}else{
					return;
				}
			}
			return;
		}
	}
}