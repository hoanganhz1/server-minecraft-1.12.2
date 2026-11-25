package xyz.pixelatedw.mineminenomi.handlers.world;

import com.mojang.datafixers.util.Pair;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import xyz.pixelatedw.mineminenomi.api.OneFruitEntry;
import xyz.pixelatedw.mineminenomi.api.OneFruitEntry$Status;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.helpers.DevilFruitHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.data.world.UnloadFruitTimeStampsData;
import xyz.pixelatedw.mineminenomi.entities.AkumaNoMiEntity;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;

public class OneFruitPerWorldHandler {
	// Fields
	private static final Pair<ResourceLocation, ResourceLocation> OFFHAND_TEXTURE = Pair.of(InventoryMenu.BLOCK_ATLAS,InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);

	public static void breakLeaves(Player var0, Level var1, BlockPos var2, boolean var3) {
		int var4 = ((var0.getItemInHand(var0.getUsedItemHand()).getItem()) == (Items.SHEARS)) ? (1) : (0);
		if (((ServerConfig.getDevilFruitDropsFromLeavesChance()) > (0D)) && (((var4) == (0)) && (((var1.getRandom().nextDouble()) * (100D)) < (ServerConfig.getDevilFruitDropsFromLeavesChance())))){
			AkumaNoMiItem var5 = ((AkumaNoMiItem)(ModValues.DEVIL_FRUITS.get(WyHelper.randomWithRange(0,(ModValues.DEVIL_FRUITS.size()) - (1)))));
			AkumaNoMiItem var6 = DevilFruitHelper.findAvailableOneFruit(var1,var5);
			if ((var6) != (null)){
				BlockPos var7 = new BlockPos(var2.getX(),var2.getY(),var2.getZ());
				String var8 = var0.getDisplayName().getString();
				String var9 = "Dropped from leaves. Sheared by " + var8;
				if ((((!(var3)) || (OneFruitWorldData.get().dropOneFruit(var6,(LivingEntity)(var0),var0.level().dimension().location(),var7,var9))) ? (1) : (0)) != (0)){
					ItemStack var10 = var6.getDefaultInstance();
					var1.addFreshEntity((Entity)(new ItemEntity(var1,(double)(var2.getX()),(double)(var2.getY()),(double)(var2.getZ()),var10)));
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	public static boolean cancelMouseClick(Screen var0) {
		if (var0 instanceof AbstractContainerScreen){
			AbstractContainerScreen var1 = ((AbstractContainerScreen)(var0));
			if (!(var1.getMenu().getCarried().getItem() instanceof AkumaNoMiItem)){
				return false;
			}else{
				Slot var2 = var1.getSlotUnderMouse();
				if ((var2) == (null)){
					return false;
				}else{
					if (((var2.getNoItemIcon()) != (null)) && (var2.getNoItemIcon().equals(OFFHAND_TEXTURE))){
						return true;
					}else{
						if ((!(var0 instanceof CreativeModeInventoryScreen)) && (!(var0 instanceof InventoryScreen))){
							int var3 = var1.getMenu().slots.size();
							if (((var3) - (var2.index)) > (35)){
								return true;
							}else{
								return false;
							}
						}else{
							return false;
						}
					}
				}
			}
		}else{
			return false;
		}
	}
	public static boolean cancelMouseReleaseEvent(Screen var0) {
		if (var0 instanceof AbstractContainerScreen){
			AbstractContainerScreen var1 = ((AbstractContainerScreen)(var0));
			if (!(var1.getMenu().getCarried().getItem() instanceof AkumaNoMiItem)){
				return false;
			}else{
				Slot var2 = var1.getSlotUnderMouse();
				if ((var2) == (null)){
					return false;
				}else{
					if (((var2.getNoItemIcon()) != (null)) && (var2.getNoItemIcon().equals(OFFHAND_TEXTURE))){
						return true;
					}else{
						if ((!(var0 instanceof CreativeModeInventoryScreen)) && (!(var0 instanceof InventoryScreen))){
							int var3 = var1.getMenu().slots.size();
							if (((var3) - (var2.index)) > (35)){
								return true;
							}else{
								return false;
							}
						}else{
							return false;
						}
					}
				}
			}
		}else{
			return false;
		}
	}
	public static void containerClose(InventoryMenu var0, Player var1) {
		int var2 = (var0.slots.size()) - (var1.getInventory().items.size());
		int var3 = 0;
		do{
			if ((var3) < (var2)){
				Slot var4 = ((Slot)(var0.slots.get(var3)));
				if ((var4.hasItem()) && (var4.getItem().getItem() instanceof AkumaNoMiItem)){
					var1.drop(var4.getItem().copy(),true);
					var4.remove(1);
				}
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		dropFruitsFromNearbyContainers(var1);
	}
	public static void playerDeath(ServerPlayer var0) {
		DevilFruitData var1 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			OneFruitWorldData var2 = OneFruitWorldData.get();
			if (!(DevilFruitHelper.respawnDevilFruit((LivingEntity)(var0),var1))){
				if (((var1.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI)) && (!(((MorphInfo)(ModMorphs.YOMI_SKELETON.get())).isActive((LivingEntity)(var0))))) || (!((DevilFruitHelper.canDevilFruitRespawn()) && (var1.getDevilFruit().isPresent())))){

				}else{
					var2.lostOneFruit(((ResourceLocation)(var1.getDevilFruit().get())),(LivingEntity)(var0),"User's death");
				}
				if ((var1.hasYamiPower()) && (DevilFruitHelper.canDevilFruitRespawn())){
					var2.lostOneFruit(BuiltInRegistries.ITEM.getKey(((Item)(ModFruits.YAMI_YAMI_NO_MI.get()))),(LivingEntity)(var0),"User died");
				}
				ArrayList var3 = new ArrayList();
				var3.addAll((Collection)(var0.getInventory().items));
				var3.addAll((Collection)(var0.getInventory().offhand));
				Iterator var4 = var3.iterator();
				do{
					if (var4.hasNext()){
						ItemStack var5 = ((ItemStack)(var4.next()));
						if ((var5) != (null)){
							Item var6 = var5.getItem();
							if (var6 instanceof AkumaNoMiItem){
								AkumaNoMiItem var7 = ((AkumaNoMiItem)(var6));
								if (var2.isFruitInUse(BuiltInRegistries.ITEM.getKey(var5.getItem()))){
									var5.shrink(var5.getCount());
									continue;
								}else{
									if (!(var0.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY))){
										BlockPos var8 = new BlockPos(var0.getBlockX(),var0.getBlockY(),var0.getBlockZ());
										var2.dropOneFruit(var7,(LivingEntity)(var0),var0.level().dimension().location(),var8,(String)(null));
										continue;
									}else{
										continue;
									}
								}
							}else{
								continue;
							}
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}
			return;
		}
	}
	public static void playerLogin(ServerPlayer var0) {
		OneFruitWorldData var1 = OneFruitWorldData.get();
		DevilFruitData var2 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if (((var2) != (null)) && ((var3) != (null))){
			if ((ServerConfig.getDaysForInactivity()) > (0)){
				Iterator var4 = var1.getOneFruitEntries().iterator();
				do{
					if (var4.hasNext()){
						OneFruitEntry var5 = ((OneFruitEntry)(var4.next()));
						long var6 = WyHelper.getDaysSince(var5.getLastUpdate());
						if ((var6) >= ((long)(ServerConfig.getDaysForInactivity()))){
							long var7 = var6;
							String var8 = "Inactivity for " + var7 + " days";
							var1.lostOneFruit(var5.getKey(),(LivingEntity)(null),var8);
						}
						int var7_1 = ((var5.getOwner().isPresent()) && (!(((UUID)(var5.getOwner().get())).equals(var0.getUUID())))) ? (1) : (0);
						int var8_1 = ((!(var5.getOwner().isPresent())) && ((var5.getStatus()) == (OneFruitEntry$Status.LOST))) ? (1) : (0);
						if ((((var7_1) == (0)) && ((var8_1) == (0))) || (!((var2.getDevilFruit().isPresent()) && (var5.getKey().equals(var2.getDevilFruit().get()))))){
							continue;
						}else{
							var2.removeDevilFruit();
							var3.clearUnlockedAbilities(AbilityCategory.DEVIL_FRUITS.isCorePartofCategory());
							var3.clearPassiveAbilities(AbilityCategory.DEVIL_FRUITS.isAbilityPartofCategory());
							var3.clearEquippedAbilities(AbilityCategory.DEVIL_FRUITS.isAbilityPartofCategory());
							ModNetwork.sendTo(new SSyncDevilFruitPacket((LivingEntity)(var0),(DevilFruitData)(var2)),(Player)(var0));
							var0.sendSystemMessage((Component)(Component.translatable(ModI18n.SYSTEM_MESSAGE_OFPW_INACTIVITY.getString())));
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}
			checkPlayerInventory((Player)(var0));
			return;
		}else{
			return;
		}
	}
	public static void playerLogout(Player var0) {
		DevilFruitData var1 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			OneFruitWorldData var2 = OneFruitWorldData.get();
			ArrayList var3 = new ArrayList();
			if (var1.hasAnyDevilFruit()){
				var3.add(((ResourceLocation)(var1.getDevilFruit().get())));
			}
			if (var1.hasYamiPower()){
				var3.add(BuiltInRegistries.ITEM.getKey(((Item)(ModFruits.YAMI_YAMI_NO_MI.get()))));
			}
			Iterator var4 = var0.getInventory().items.iterator();
			do{
				if (var4.hasNext()){
					ItemStack var5 = ((ItemStack)(var4.next()));
					if (var5.getItem() instanceof AkumaNoMiItem){
						var3.add(BuiltInRegistries.ITEM.getKey(var5.getItem()));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			if (!(((java.util.List)(var3)).isEmpty())){
				Iterator var5_1 = var3.iterator();
				do{
					if (var5_1.hasNext()){
						var2.forceUpdateOneFruit(((ResourceLocation)(var5_1.next())));
						continue;
					}else{
						break;
					}
				} while (true);
			}
			return;
		}
	}
	public static boolean cancelPickUpExtended(Player var0, ItemStack var1) {
		int var3 = 0;
		if (((var1.getItem()) == (Items.SHULKER_BOX)) && (var1.has(DataComponents.BLOCK_ENTITY_DATA))){
			ListTag var2 = ((CustomData)(var1.get(DataComponents.BLOCK_ENTITY_DATA))).getUnsafe().getList("Items",10);
			var3 = 0;
			do{
				if ((var3) < (var2.size())){
					String var4 = var2.getCompound(var3).getString("id");
					Item var5 = ((Item)(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(var4))));
					if (((var5) == (null)) || (!(var5 instanceof AkumaNoMiItem))){

					}else{
						var2.remove(var3);
					}
					var3++;
					continue;
				}else{
					break;
				}
			} while (true);
		}else{
			Item var2_1 = var1.getItem();
			if (var2_1 instanceof AkumaNoMiItem){
				AkumaNoMiItem var3_1 = ((AkumaNoMiItem)(var2_1));
				DevilFruitData var4_1 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var0)))).orElse((Object)(null))));
				if ((var4_1) == (null)){
					return false;
				}else{
					Predicate<ItemStack> var5_1 = (l0) -> {
			return l0.getItem() instanceof AkumaNoMiItem;
		};
					var3 = ItemsHelper.getAllInventoryItems((LivingEntity)(var0)).stream().filter(var5_1).toList().size();
					int var7 = ((var4_1.hasDevilFruit(ModFruits.YAMI_YAMI_NO_MI)) && ((ServerConfig.isYamiPowerEnabled()) && ((var3) == (0)))) ? (1) : (0);
					if ((var4_1.hasAnyDevilFruit()) && ((ServerConfig.getUnableToPickDFAsUser()) && ((var7) == (0)))){
						return true;
					}else{
						if (DevilFruitHelper.hasDFLimitInInventory(var0)){
							return true;
						}else{
							return !(OneFruitWorldData.get().inventoryOneFruit(var3_1,(LivingEntity)(var0),"Picked up from ground"));
						}
					}
				}
			}else{
				checkPlayerInventory(var0);
			}
		}
		return false;
	}
	public static boolean cancelPickUpSimple(Player var0, ItemStack var1) {
		Item var2 = var1.getItem();
		if (var2 instanceof AkumaNoMiItem){
			AkumaNoMiItem var3 = ((AkumaNoMiItem)(var2));
			OneFruitWorldData var4 = OneFruitWorldData.get();
			checkPlayerInventory(var0);
			return !(var4.inventoryOneFruit(var3,(LivingEntity)(var0),"Picked up from ground"));
		}else{
			return false;
		}
	}
	public static boolean cancelDrop(AkumaNoMiEntity var0) {
		Entity var1 = var0.getOwner();
		Player var2 = null;
		if (var1 instanceof Player){
			var2 = ((Player)(var1));
		}
		OneFruitWorldData var3 = OneFruitWorldData.get();
		BlockPos var4 = new BlockPos(var0.getBlockX(),var0.getBlockY(),var0.getBlockZ());
		AkumaNoMiItem var5 = ((AkumaNoMiItem)(var0.getItem().getItem()));
		String var6 = var2.getDisplayName().getString();
		String var7 = "by " + var6;
		String var8 = ((var2) == (null)) ? ("") : (var7);
		String var9 = "Fruit got dropped " + var8;
		return !(var3.dropOneFruit(var5,(LivingEntity)(var2),var0.level().dimension().location(),var4,var9,true));
	}
	public static void expire(AkumaNoMiItem var0) {
		String var1 = String.valueOf((Object)(var0.getRegistryKey()));
		String var2 = var1 + " has expired";
		WyDebug.debug(var2);
		OneFruitWorldData.get().lostOneFruit(BuiltInRegistries.ITEM.getKey(var0),(LivingEntity)(null),"Item Entity expired on ground");
	}
	public static void unloadInstant(AkumaNoMiItem var0) {
		OneFruitWorldData var1 = OneFruitWorldData.get();
		String var2 = String.valueOf((Object)(var0.getRegistryKey()));
		String var3 = "Instant removed " + var2;
		WyDebug.debug(var3);
		var1.lostOneFruit(var0.getRegistryKey(),(LivingEntity)(null),"Unloaded by Chunk");
	}
	public static void unloadRealistic(AkumaNoMiItem var0) {
		UnloadFruitTimeStampsData var1 = UnloadFruitTimeStampsData.get();
		String var2 = String.valueOf((Object)(var0.getRegistryKey()));
		String var3 = "Set unload time for fruit " + var2;
		WyDebug.debug(var3);
		var1.addUnloadTime(var0.getRegistryKey(),Instant.now().plus((TemporalAmount)(Duration.ofMinutes(1L))).toEpochMilli());
	}
	public static boolean cleanup(AkumaNoMiEntity var0) {
		OneFruitWorldData var1 = OneFruitWorldData.get();
		AkumaNoMiItem var2 = ((AkumaNoMiItem)(var0.getItem().getItem()));
		OneFruitEntry var3 = var1.getOneFruitEntry(var2.getRegistryKey());
		if (((var3) != (null)) && ((var3.getStatus()) == (OneFruitEntry$Status.LOST))){
			String var4 = String.valueOf((Object)(var2.getRegistryKey()));
			String var5 = var4 + " destroyed";
			WyDebug.debug(var5);
			return true;
		}else{
			return false;
		}
	}
	public static void tick() {
		UnloadFruitTimeStampsData var0 = UnloadFruitTimeStampsData.get();
		ArrayList var1 = new ArrayList();
		List var2 = var1;
		final List var2_f1 = var2;
		BiConsumer<String, Long> var3 = (l0, l1) -> {
			ResourceLocation lv2_3 = ResourceLocation.tryParse(l0);
			if ((lv2_3) == (null)){
				return;
			}else{
				if ((Instant.now().toEpochMilli()) > (l1.longValue())){
					OneFruitWorldData lv2_4 = OneFruitWorldData.get();
					String lv2_5 = String.valueOf((Object)(lv2_3));
					String lv2_6 = "Unloaded fruit " + lv2_5;
					WyDebug.debug(lv2_6);
					var2_f1.add(lv2_3);
					lv2_4.lostOneFruit(lv2_3,(LivingEntity)(null),"Unloaded by Chunk");
				}
				return;
			}
		};
		((java.util.Map)(var0.getUnloadTimes())).forEach((BiConsumer)(var3));
		Objects.requireNonNull(var0);
		var1.forEach((Consumer)(((Consumer<ResourceLocation>)(var0::removeUnloadTime))));
	}
	public static void dropFruitsFromNearbyContainers(Player var0) {
		Iterator var1 = WyHelper.getNearbyTileEntities((Entity)(var0),40).iterator();
		LOOP_1:
		do{
			if (var1.hasNext()){
				BlockPos var2 = ((BlockPos)(var1.next()));
				BlockEntity var3 = var0.level().getBlockEntity(var2);
				if (var3 instanceof Container){
					Container var4 = ((Container)(var3));
					int var5 = 0;
					do{
						if ((var5) < (var4.getContainerSize())){
							ItemStack var6 = var4.getItem(var5);
							if (((var6) != (null)) && (var6.getItem() instanceof AkumaNoMiItem)){
								var0.drop(var6.copy(),false);
								var6.shrink(var6.getCount());
							}
							var5++;
							continue;
						}else{
							continue LOOP_1;
						}
					} while (true);
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public static void checkPlayerInventory(Player var0) {
		OneFruitWorldData var1 = OneFruitWorldData.get();
		DevilFruitData var2 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			ArrayList var3 = new ArrayList();
			var3.addAll((Collection)(var0.getInventory().items));
			var3.addAll((Collection)(var0.getInventory().offhand));
			int var4 = 0;
			Iterator var5 = var3.iterator();
			do{
				if (var5.hasNext()){
					ItemStack var6 = ((ItemStack)(var5.next()));
					if (((var6) != (null)) && (var6.getItem() instanceof AkumaNoMiItem)){
						ResourceLocation var7 = BuiltInRegistries.ITEM.getKey(var6.getItem());
						var4++;
						if (var1.isFruitInUse(var7)){
							var6.shrink(var6.getCount());
							continue;
						}else{
							if (var1.isFruitDuped(var7,var0.getUUID())){
								var6.shrink(var6.getCount());
								continue;
							}else{
								if ((var2.hasAnyDevilFruit()) && (ServerConfig.getUnableToPickDFAsUser())){
									if ((var2.hasDevilFruit(((AkumaNoMiItem)(ModFruits.YAMI_YAMI_NO_MI.get())))) && ((ServerConfig.isYamiPowerEnabled()) && ((var4) == (1)))){
										continue;
									}else{
										if (!(var1.isFruitDuped(var7,var0.getUUID()))){
											var1.lostOneFruit(var7,(LivingEntity)(null),"Cannot pick up extra fruits");
										}
										var6.shrink(var6.getCount());
										continue;
									}
								}else{
									if ((var4) > (ServerConfig.getInventoryLimitForFruits())){
										if (!(var1.isFruitDuped(var7,var0.getUUID()))){
											int var8 = ServerConfig.getInventoryLimitForFruits();
											String var9 = "Cannot pick up more than " + var8 + " fruits";
											var1.lostOneFruit(var7,(LivingEntity)(null),var9);
										}
										var6.shrink(var6.getCount());
										continue;
									}else{
										continue;
									}
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
}