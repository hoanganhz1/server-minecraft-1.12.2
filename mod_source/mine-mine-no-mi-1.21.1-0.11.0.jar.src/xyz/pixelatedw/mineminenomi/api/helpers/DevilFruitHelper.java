package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.config.GeneralConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class DevilFruitHelper {
	// Fields
	private static final double REINCARNATION_RANGE = 30D;

	public static boolean hasDFLimitInInventory(Player var0) {
		if (!(ServerConfig.hasOneFruitPerWorldExtendedLogic())){
			return false;
		}else{
			DevilFruitData var1 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var0)))).orElse((Object)(null))));
			Predicate<ItemStack> var2 = (l0) -> {
			return l0.getItem() instanceof AkumaNoMiItem;
		};
			int var3 = ItemsHelper.getAllInventoryItems((LivingEntity)(var0)).stream().filter(var2).toList().size();
			if ((var1) != (null)){
				int var4 = ((var1.hasDevilFruit(ModFruits.YAMI_YAMI_NO_MI)) && ((ServerConfig.isYamiPowerEnabled()) && ((var3) == (0)))) ? (1) : (0);
				if ((var1.hasAnyDevilFruit()) && ((ServerConfig.getUnableToPickDFAsUser()) && ((var4) == (0)))){
					return true;
				}
			}
			return (var3) >= (ServerConfig.getInventoryLimitForFruits());
		}
	}
	@Nullable()
	public static AkumaNoMiItem findAvailableOneFruit(Level var0, @Nullable() Item var1) {
		OneFruitWorldData var6 = null;
		if (var1 instanceof AkumaNoMiItem){
			AkumaNoMiItem var2 = ((AkumaNoMiItem)(var1));
			if (!(ServerConfig.hasOneFruitPerWorldSimpleLogic())){
				return var2;
			}else{
				OneFruitWorldData var3 = OneFruitWorldData.get();
				int var4 = var2.getTier();
				if (((!(var3.isFruitInWorld(var2.getRegistryKey()))) ? (1) : (0)) != (0)){
					return var2;
				}else{
					int var5 = var4;
					var6 = var3;
					final int var5_f1 = var5;
					final OneFruitWorldData var6_f1 = var6;
					Predicate<AkumaNoMiItem> var7 = (l0) -> {
			return ((l0.getTier()) == (var5_f1)) && (!(var6_f1.isFruitInWorld(l0.getRegistryKey())));
		};
					List var8 = WyHelper.shuffle((Collection)(ModValues.DEVIL_FRUITS)).stream().filter(var7).toList();
					if (!(var8.isEmpty())){
						AkumaNoMiItem var9 = ((AkumaNoMiItem)(var8.get(0)));
						return var9;
					}else{
						return null;
					}
				}
			}
		}else{
			return null;
		}
	}
	public static boolean canDevilFruitRespawn() {
		return !(((Boolean)(GeneralConfig.DEVIL_FRUIT_KEEP.get())).booleanValue());
	}
	public static boolean respawnDevilFruit(LivingEntity var0, DevilFruitData var1) {
		if (!(canDevilFruitRespawn())){
			return false;
		}else{
			if ((ChallengesWorldData.get().getInProgressChallengeFor(var0)) != (null)){
				return false;
			}else{
				if (!(var1.hasAnyDevilFruit())){
					return false;
				}else{
					if ((((var1.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI)) && (!(((MorphInfo)(ModMorphs.YOMI_SKELETON.get())).isActive(var0)))) ? (1) : (0)) != (0)){
						return false;
					}else{
						double var2 = (double)(WyHelper.randomWithRange(1,100));
						OneFruitWorldData var3 = OneFruitWorldData.get();
						List var4 = TargetHelper.getEntitiesInArea((LevelAccessor)(var0.level()),var0,30D,(TargetPredicate)(null),ItemEntity.class);
						var4.removeIf((Predicate)(((Predicate<ItemEntity>)((l0) -> {
			return !(l0.getItem().is(ModTags$Items.FRUIT_REINCARNATION));
		}))));
						if ((!(var4.isEmpty())) && ((var2) <= (ServerConfig.getChanceForDroppedAppleReincarnation()))){
							AkumaNoMiItem var5 = ((AkumaNoMiItem)(var1.getDevilFruitItem()));
							if (ServerConfig.getRandomizedFruits()){
								var5 = var5.getReverseShiftedFruit();
							}
							((ItemEntity)(var4.get(0))).setItem(new ItemStack((ItemLike)(var5)));
							BlockPos var6 = new BlockPos(var0.getBlockX(),var0.getBlockY(),var0.getBlockZ());
							String var7 = var0.getDisplayName().getString();
							String var8 = "Reincarnated when " + var7 + " died";
							var3.dropOneFruit(var5,(LivingEntity)(null),var0.level().dimension().location(),var6,var8,true);
							var0.level().playSound((Player)(null),((ItemEntity)(var4.get(0))).blockPosition(),((SoundEvent)(ModSounds.FRUIT_REINCARNATION.get())),SoundSource.PLAYERS,3F,1F);
							return true;
						}else{
							List var5_1 = TargetHelper.getNearbyPlayers((LevelAccessor)(var0.level()),var0.position(),30D,(Predicate)(null));
							var5_1.removeIf((Predicate)(((Predicate<Player>)((l0) -> {
			return !(hasFruitReincarnationInInventory((LivingEntity)(l0)));
		}))));
							if ((!(var5_1.isEmpty())) && (((var2) <= (ServerConfig.getChanceForInventoryAppleReincarnation())) && (setFruitInInv((Container)(((Player)(var5_1.get(0))).getInventory()),((LivingEntity)(var5_1.get(0))),var0,var0.level(),((ResourceLocation)(var1.getDevilFruit().get())))))){
								var0.level().playSound((Player)(null),((Player)(var5_1.get(0))).blockPosition(),((SoundEvent)(ModSounds.FRUIT_REINCARNATION.get())),SoundSource.PLAYERS,3F,1F);
								return true;
							}else{
								if (!(ServerConfig.hasOneFruitPerWorldExtendedLogic())){
									List var6_1 = TargetHelper.getEntitiesInArea((LevelAccessor)(var0.level()),var0,30D,(TargetPredicate)(null),Villager.class);
									var6_1.removeIf((Predicate)(((Predicate<Villager>)((l0) -> {
			return !(hasFruitReincarnationInInventory((LivingEntity)(l0)));
		}))));
									if ((!(var6_1.isEmpty())) && (((var2) <= (ServerConfig.getChanceForInventoryAppleReincarnation())) && (setFruitInInv((Container)(((Villager)(var6_1.get(0))).getInventory()),((LivingEntity)(var6_1.get(0))),var0,var0.level(),((ResourceLocation)(var1.getDevilFruit().get())))))){
										var0.level().playSound((Player)(null),((Villager)(var6_1.get(0))).blockPosition(),((SoundEvent)(ModSounds.FRUIT_REINCARNATION.get())),SoundSource.PLAYERS,3F,1F);
										return true;
									}else{
										List var7_1 = WyHelper.getNearbyBlocks((Entity)(var0),30);
										if ((!(var7_1.isEmpty())) && ((var2) <= (ServerConfig.getChanceForChestAppleReincarnation()))){
											Iterator var8_1 = var7_1.iterator();
											do{
												if (var8_1.hasNext()){
													BlockPos var9 = ((BlockPos)(var8_1.next()));
													BlockState var10 = var0.level().getBlockState(var9);
													if ((var10.getBlock() instanceof ChestBlock) && (setFruitInInv(ChestBlock.getContainer(((ChestBlock)(var10.getBlock())),var10,var0.level(),var9,false),(LivingEntity)(null),var0,var0.level(),((ResourceLocation)(var1.getDevilFruit().get()))))){
														var0.level().playSound((Player)(null),var9,((SoundEvent)(ModSounds.FRUIT_REINCARNATION.get())),SoundSource.PLAYERS,3F,1F);
														return true;
													}else{
														continue;
													}
												}else{
													break;
												}
											} while (true);
										}else{
											return false;
										}
									}
								}
								return false;
							}
						}
					}
				}
			}
		}
	}
	@Nullable()
	public static AkumaNoMiItem getDevilFruitItem(ResourceLocation var0) {
		Item var1 = ((Item)(BuiltInRegistries.ITEM.get(var0)));
		if (var1 instanceof AkumaNoMiItem){
			return ((AkumaNoMiItem)(var1));
		}else{
			return null;
		}
	}
	private static boolean setFruitInInv(@Nullable() Container var0, @Nullable() LivingEntity var1, LivingEntity var2, Level var3, ResourceLocation var4) {
		if ((var0) == (null)){
			return false;
		}else{
			int var5 = -1;
			int var6 = 0;
			do{
				if ((var6) < (var0.getContainerSize())){
					if (var0.getItem(var6).is(ModTags$Items.FRUIT_REINCARNATION)){
						var5 = var6;
						break;
					}else{
						var6++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			if ((var5) != (-1)){
				AkumaNoMiItem var7 = getDevilFruitItem(var4);
				if (ServerConfig.getRandomizedFruits()){
					var7 = var7.getReverseShiftedFruit();
				}
				var0.setItem(var5,new ItemStack((ItemLike)(var7)));
				String var8 = var2.getDisplayName().getString();
				String var9 = "Reincarnated in " + var8 + "'s inventory";
				OneFruitWorldData.get().inventoryOneFruit(var7,var1,var9,true);
				return true;
			}else{
				return false;
			}
		}
	}
	private static boolean hasFruitReincarnationInInventory(LivingEntity var0) {
		Iterator var1 = ItemsHelper.getAllInventoryItems(var0).iterator();
		do{
			if (var1.hasNext()){
				if (((ItemStack)(var1.next())).is(ModTags$Items.FRUIT_REINCARNATION)){
					return true;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return false;
	}
}