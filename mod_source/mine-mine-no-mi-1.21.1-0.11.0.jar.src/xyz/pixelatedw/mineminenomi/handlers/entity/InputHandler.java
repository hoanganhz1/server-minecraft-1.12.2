package xyz.pixelatedw.mineminenomi.handlers.entity;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.handlers.ui.CombatModeHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModKeybindings;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.CRequestSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CChangeAbilityAltModePacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CToggleCombatModePacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CUseAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CSwitchVehicleModePacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CSyncPlayerInputPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenAbilitiesListScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenAbilityTreeScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenChallengesScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenCrewScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenPlayerScreenPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilityTreeScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.ChallengesScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.CrewDetailsScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.PlayerStatsScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.QuestsTrackerScreen;

public class InputHandler {
	public static void handleNewPlayerInput(LocalPlayer var0) {
		EntityStatsData var1 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if (((var1) != (null)) && (!(((var1.getLeftImpulse()) == (var0.xxa)) && (((var1.getForwardImpulse()) == (var0.zza)) && ((var1.isJumping()) == (var0.input.jumping)))))){
			var1.setLeftImpulse(var0.xxa);
			var1.setForwardImpulse(var0.zza);
			var1.setJumping(var0.input.jumping);
			ModNetwork.sendToServer(new CSyncPlayerInputPacket(var0));
		}
	}
	public static void resetVanillaKeybinds() {
		Minecraft var0 = Minecraft.getInstance();
		int var1 = 0;
		do{
			if ((var1) < (var0.options.keyHotbarSlots.length)){
				KeyMapping var2 = var0.options.keyHotbarSlots[var1];
				if ((ModKeybindings.PREVIOUS_INVENTORY_KEYBINDS[var1]) != (-1)){
					var2.setKey(InputConstants.getKey(ModKeybindings.PREVIOUS_INVENTORY_KEYBINDS[var1],0));
				}
				var1++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static void resetCachedKeybinds() {
		CombatModeHandler.abilityBars = ServerConfig.getAbilityBars();
		Arrays.fill(ModKeybindings.PREVIOUS_INVENTORY_KEYBINDS,-1);
	}
	public static void checkKeybindings(AbstractClientPlayer var0, InputConstants.Key var1, int var2, int var3, int var4) {
		Minecraft var5 = Minecraft.getInstance();
		if (WyDebug.isDebug()){
			if (ModKeybindings.TEST.isActiveAndMatches(var1)){

			}
		}
		if (ModKeybindings.VEHICLE_ALT_MODE.isActiveAndMatches(var1)){
			ModNetwork.sendToServer(new CSwitchVehicleModePacket());
		}
		Screen var6 = var5.screen;
		int var7 = (((var6) != (null)) && (var6 instanceof PlayerStatsScreen)) ? (1) : (0);
		int var8 = (((var6) != (null)) && (var6 instanceof AbilitiesListScreen)) ? (1) : (0);
		int var9 = (((var6) != (null)) && (var6 instanceof CrewDetailsScreen)) ? (1) : (0);
		int var10 = (((var6) != (null)) && (var6 instanceof ChallengesScreen)) ? (1) : (0);
		int var11 = (((var6) != (null)) && (var6 instanceof QuestsTrackerScreen)) ? (1) : (0);
		int var12 = (((var6) != (null)) && (var6 instanceof AbilityTreeScreen)) ? (1) : (0);
		int var13 = (((var8) != (0)) || (((var9) != (0)) || (((var10) != (0)) || (((var11) != (0)) || ((var12) != (0)))))) ? (1) : (0);
		if ((var2) == (1)){
			checkScreenActivation(var5,ModKeybindings.OPEN_ABILITIES_MENU,var1,var2,var3,var4,var6,(var8) != (0),(Supplier)(((Supplier<COpenAbilitiesListScreenPacket>)(COpenAbilitiesListScreenPacket::new))));
			checkScreenActivation(var5,ModKeybindings.OPEN_CHALLENGES_MENU,var1,var2,var3,var4,var6,(var10) != (0),(Supplier)(((Supplier<COpenChallengesScreenPacket>)(COpenChallengesScreenPacket::new))));
			Supplier<CRequestSyncQuestDataPacket> var14 = () -> {
			return new CRequestSyncQuestDataPacket(true);
		};
			checkScreenActivation(var5,ModKeybindings.OPEN_QUESTS_MENU,var1,var2,var3,var4,var6,(var11) != (0),var14);
			checkScreenActivation(var5,ModKeybindings.OPEN_CREW_MENU,var1,var2,var3,var4,var6,(var9) != (0),(Supplier)(((Supplier<COpenCrewScreenPacket>)(COpenCrewScreenPacket::new))));
			checkScreenActivation(var5,ModKeybindings.OPEN_ABILITY_TREE_MENU,var1,var2,var3,var4,var6,(var12) != (0),(Supplier)(((Supplier<COpenAbilityTreeScreenPacket>)(COpenAbilityTreeScreenPacket::new))));
			if (((ModKeybindings.OPEN_PLAYER_MENU.isActiveAndMatches(var1)) && (ModKeybindings.OPEN_PLAYER_MENU.consumeClick())) || (!((((var6) != (null)) && (((var7) == (0)) && ((var13) == (0)))) || (!(((var4) == (1)) && ((var3) == (ModKeybindings.OPEN_PLAYER_MENU.getKey().getValue()))))))){
				if ((var7) != (0)){
					var5.setScreen((Screen)(null));
				}else{
					if (((var13) == (0)) && ((var6) != (null))){

					}else{
						ModNetwork.sendToServer(new COpenPlayerScreenPacket());
					}
				}
			}
		}
		if (((var6) == (null)) && ((var2) != (2))){
			AbilityData var14_1 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
			EntityStatsData var15 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var0)))).orElse((Object)(null))));
			if ((var15) == (null)){
				String var16 = var0.getName().toString();
				String var17 = "Entity Capability for player " + var16 + " is null!";
				LogManager.getLogger(InputHandler.class).warn(var17);
				return;
			}else{
				tryUpdateCombatMode(var15,var1);
				tryChangeCombatBar(var15,var14_1,var1);
				if ((var14_1) == (null)){
					String var16_1 = var0.getName().toString();
					String var17_1 = "Ability Capability for player " + var16_1 + " is null!";
					LogManager.getLogger(InputHandler.class).warn(var17_1);
					return;
				}else{
					tryUseAbilitySlot(var0,var14_1,var15,var1);
					return;
				}
			}
		}else{
			return;
		}
	}
	private static <P extends IServerPacket> void checkScreenActivation(Minecraft var0, KeyMapping var1, InputConstants.Key var2, int var3, int var4, int var5, Screen var6, boolean var7, Supplier<P> var8) {
		if (((var1.isActiveAndMatches(var2)) && (var1.consumeClick())) || (!((((var6) != (null)) && (!(var7))) || (!(((var5) == (1)) && ((var4) == (var1.getKey().getValue()))))))){
			if (var7){
				var0.setScreen((Screen)(null));
			}else{
				ModNetwork.sendToServer(((IServerPacket)(var8.get())));
			}
		}
	}
	private static void tryChangeCombatBar(EntityStatsData var0, AbilityData var1, InputConstants.Key var2) {
		int var7 = 0;
		if (var0.isInCombatMode()){
			int var3 = ClientConfig.getAbilityBarsOnScreen();
			int var4 = ServerConfig.getAbilityBars();
			int var5 = Math.min(var4,ModKeybindings.serverMaxBars);
			int var6 = Math.min(var3,var5);
			if ((ModKeybindings.NEXT_COMBAT_BAR.isActiveAndMatches(var2)) && (ModKeybindings.NEXT_COMBAT_BAR.consumeClick())){
				if (((var1.getCombatBarSet()) + (var6)) < (var5)){
					var1.nextCombatBarSet(var6);
				}else{
					var1.setCombatBarSet(0);
				}
			}else{
				if ((ModKeybindings.PREV_COMBAT_BAR.isActiveAndMatches(var2)) && (ModKeybindings.PREV_COMBAT_BAR.consumeClick())){
					if (((var1.getCombatBarSet()) - (var6)) >= (0)){
						var1.prevCombatBarSet(var6);
					}else{
						if ((var5) == (var6)){
							var1.setCombatBarSet(0);
						}else{
							var7 = 0;
							int var8 = var5;
							do{
								if ((var8) > (0)){
									var7 = var8;
									var8 = (var8) - (var6);
									continue;
								}else{
									break;
								}
							} while (true);
							var1.setCombatBarSet((var5) - (var7));
						}
					}
				}else{
					if ((ModKeybindings.LAST_COMBAT_BAR.isActiveAndMatches(var2)) && (ModKeybindings.LAST_COMBAT_BAR.consumeClick())){
						var7 = var1.getLastCombatBarSet();
						var1.setCombatBarSet(var7);
					}
				}
			}
			var7 = 0;
			KeyMapping[] var8_1 = ModKeybindings.COMBAT_BAR_SHORTCUTS;
			int var9 = var8_1.length;
			int var10 = 0;
			do{
				if ((var10) < (var9)){
					KeyMapping var11 = var8_1[var10];
					if ((var11.isActiveAndMatches(var2)) && (var11.consumeClick())){
						int var12 = ((var7) * (var3)) + (1);
						var12 = Mth.clamp(var12,1,(var4) - (1));
						var6 = Math.min(var12,var5);
						var1.setCombatBarSet((var6) - (1));
					}
					var7++;
					var10++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	private static void tryUseAbilitySlot(AbstractClientPlayer var0, AbilityData var1, EntityStatsData var2, InputConstants.Key var3) {
		int var4 = 0;
		do{
			if ((var4) < (ModKeybindings.COMBAT_BAR_KEYS.size())){
				int var5 = (var4) / (8);
				if ((((KeyMapping)(ModKeybindings.COMBAT_BAR_KEYS.get(var4))).isActiveAndMatches(var3)) && (((KeyMapping)(ModKeybindings.COMBAT_BAR_KEYS.get(var4))).consumeClick())){
					int var6 = ((var4) % (8)) + (((var1.getCombatBarSet()) + (var5)) * (8));
					IAbility var7 = var1.getEquippedAbility(var6);
					boolean var8 = false;
					if (((var7) != (null)) && (var7.hasComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))){
						Function<CooldownComponent, Boolean> var9 = (l0) -> {
			return Boolean.valueOf((l0.isOnCooldown()) && ((l0.getCooldown()) > (10F)));
		};
						var8 = ((Boolean)(((java.util.Optional)(var7.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).map((Function)(var9)).get())).booleanValue();
					}
					if ((var2.isInCombatMode()) && ((var7) != (null))){
						if (!(var8)){
							if ((ModKeybindings.ABILITY_ALT_MODE.isDown()) && (var7.hasComponent(((AbilityComponentKey)(ModAbilityComponents.ALT_MODE.get()))))){
								ModNetwork.sendToServer(new CChangeAbilityAltModePacket(var6));
							}else{
								ModNetwork.sendToServer(new CUseAbilityPacket(var6));
							}
						}else{
							var4++;
							continue;
						}
					}else{
						var0.getInventory().selected = (var4) % (8);
					}
					var4++;
					continue;
				}else{
					var4++;
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	private static void tryUpdateCombatMode(EntityStatsData var0, InputConstants.Key var1) {
		if ((ModKeybindings.COMBAT_MODE.isActiveAndMatches(var1)) && (ModKeybindings.COMBAT_MODE.consumeClick())){
			int var3 = 0;
			Minecraft var2 = Minecraft.getInstance();
			var0.setCombatMode(!(var0.isInCombatMode()));
			if (var0.isInCombatMode()){
				var3 = 0;
				do{
					if ((var3) < (var2.options.keyHotbarSlots.length)){
						KeyMapping var4 = var2.options.keyHotbarSlots[var3];
						ModKeybindings.PREVIOUS_INVENTORY_KEYBINDS[var3] = var4.getKey().getValue();
						var4.setKey(InputConstants.UNKNOWN);
						var3++;
						continue;
					}else{
						break;
					}
				} while (true);
			}else{
				var3 = 0;
				do{
					if ((var3) < (var2.options.keyHotbarSlots.length)){
						KeyMapping var4_1 = var2.options.keyHotbarSlots[var3];
						if ((ModKeybindings.PREVIOUS_INVENTORY_KEYBINDS[var3]) == (-1)){
							var4_1.setKey(InputConstants.getKey(var4_1.getDefaultKey().getValue(),0));
						}else{
							var4_1.setKey(InputConstants.getKey(ModKeybindings.PREVIOUS_INVENTORY_KEYBINDS[var3],0));
						}
						var3++;
						continue;
					}else{
						break;
					}
				} while (true);
			}
			KeyMapping.resetMapping();
			ModNetwork.sendToServer(new CToggleCombatModePacket(var0.isInCombatMode()));
		}
	}
	public static boolean isHotbarKeyConflicting(KeyMapping var0) {
		Iterator var1 = ModKeybindings.COMBAT_BAR_KEYS.iterator();
		do{
			if (var1.hasNext()){
				KeyMapping var2 = ((KeyMapping)(var1.next()));
				if ((((java.lang.Object)(var2)).equals((Object)(var0))) || (!((var2.getKey().equals(var0.getKey())) && (var2.getKeyModifier().equals(var0.getKeyModifier()))))){
					continue;
				}else{
					return true;
				}
			}else{
				break;
			}
		} while (true);
		return false;
	}
}