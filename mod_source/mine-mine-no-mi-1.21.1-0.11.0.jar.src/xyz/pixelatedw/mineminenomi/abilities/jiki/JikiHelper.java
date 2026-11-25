package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModEnchantments;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class JikiHelper {
	public static Result requiresPunkGibson(LivingEntity var0, IAbility var1) {
		return AbilityUseConditions.requiresMorph(var0,var1,new MorphInfo[]{((MorphInfo)(ModMorphs.PUNK_GIBSON.get()))});
	}
	public static Ability$ICanUseEvent getMetalicItemsCheck(int var0) {
		int var1 = var0;
		final int var1_f1 = var1;
		Ability$ICanUseEvent var2 = (l0, l1) -> {
			if (!(hasEnoughIron(l0,ItemsHelper.getAllInventoryItems(l0),(float)(var1_f1)))){
				return Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_MATERIALS);
			}else{
				return Result.success();
			}
		};
		return var2;
	}
	public static void spawnAttractEffect(Entity var0) {
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.ATTRACT.get())),var0,var0.getX(),var0.getY(),var0.getZ());
	}
	public static List<ItemStack> getMagneticItemsOnPlayer(Player var0) {
		ArrayList var1 = new ArrayList();
		var1.addAll((Collection)(var0.getInventory().items));
		var1.addAll((Collection)(var0.getInventory().offhand));
		var1.addAll((Collection)(var0.getInventory().armor));
		return var1;
	}
	public static List<ItemStack> getMagneticItemsNeeded(LivingEntity var0, List<ItemStack> var1, float var2) {
		Holder.Reference var3 = ((Holder.Reference)(((HolderLookup.RegistryLookup)(var0.registryAccess().lookup(Registries.ENCHANTMENT).get())).get(ModEnchantments.KAIROSEKI).orElse(null)));
		ArrayList var4 = new ArrayList();
		Iterator<ItemStack> var5 = var1.iterator();
		do{
			if (var5.hasNext()){
				ItemStack var6 = ((ItemStack)(var5.next()));
				if ((var2) <= (0F)){
					break;
				}else{
					if (((var6) != (null)) && ((!(var6.isEmpty())) && ((var6.is(ModTags$Items.MAGNETIC)) && ((var6.getEnchantmentLevel((Holder)(var3))) <= (0))))){
						float var7 = ModTags$Items.IRON.getValue(var6.getItem());
						ItemStack var8 = ItemStack.parseOptional((HolderLookup.Provider)(var0.registryAccess()),((CompoundTag)(var6.save((HolderLookup.Provider)(var0.registryAccess())))));
						if (var6.isDamageableItem()){
							var4.add(var8);
							var6.shrink(1);
							var2 = (var2) - (var7);
							continue;
						}else{
							int var9 = var6.getCount();
							int var10 = (int)(Math.ceil((double)((var2) / (var7))));
							if ((var9) < (var10)){
								var4.add(var8);
								var6.shrink(var9);
								var2 = (var2) - (((float)(var9)) * (var7));
								continue;
							}else{
								ItemStack var11 = var6.split(var10);
								var4.add(var11);
								var2 = (var2) - (((float)(var11.getCount())) * (var7));
								continue;
							}
						}
					}else{
						continue;
					}
				}
			}else{
				break;
			}
		} while (true);
		return var4;
	}
	public static boolean hasEnoughIron(LivingEntity var0, List<ItemStack> var1, float var2) {
		return (getIronAmount(var0,var1)) >= (var2);
	}
	public static float getIronAmount(LivingEntity var0, List<ItemStack> var1) {
		Holder.Reference var2 = ((Holder.Reference)(((HolderLookup.RegistryLookup)(var0.registryAccess().lookup(Registries.ENCHANTMENT).get())).get(ModEnchantments.KAIROSEKI).orElse(null)));
		float var3 = 0F;
		Iterator<ItemStack> var4 = var1.iterator();
		do{
			if (var4.hasNext()){
				ItemStack var5 = ((ItemStack)(var4.next()));
				if (((var5) != (null)) && ((!(var5.isEmpty())) && (!(((var5.getEnchantmentLevel((Holder)(var2))) > (0)) || (!(var5.is(ModTags$Items.MAGNETIC))))))){
					var3 = (var3) + ((ModTags$Items.IRON.getValue(var5.getItem())) * ((float)(var5.getCount())));
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var3;
	}
	public static void dropComponentItems(Level var0, Vec3 var1, List<ItemStack> var2) {
		damageMagneticItems(var0,var2);
		Iterator<ItemStack> var3 = var2.iterator();
		LOOP_1:
		do{
			if (var3.hasNext()){
				ItemStack var4 = ((ItemStack)(var3.next()));
				int var5 = (var0.getRandom().nextInt(4)) + (2);
				int var6 = 0;
				do{
					if ((var6) < (var5)){
						ItemStack var7 = var4.split((var4.getCount()) / (var5));
						ItemEntity var8 = new ItemEntity(var0,var1.x(),(var1.y()) + (0.4000000059604645D),var1.z(),var7);
						var8.setPickUpDelay(20);
						var0.addFreshEntity((Entity)(var8));
						var6++;
						continue;
					}else{
						break;
					}
				} while (true);
				ItemEntity var7_1 = new ItemEntity(var0,var1.x(),(var1.y()) + (0.4000000059604645D),var1.z(),var4);
				var7_1.setPickUpDelay(20);
				var0.addFreshEntity((Entity)(var7_1));
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
	}
	public static void damageMagneticItems(Level var0, List<ItemStack> var1) {
		if (((var1) != (null)) && (!(var1.isEmpty()))){
			Iterator<ItemStack> var2 = var1.iterator();
			do{
				if (var2.hasNext()){
					ItemStack var3 = ((ItemStack)(var2.next()));
					if (((var3) != (null)) && (!(var3.isEmpty()))){
						float var4 = ModTags$Items.IRON.getValue(var3.getItem());
						float var5 = Mth.clamp((10F) / (var4),0F,50F);
						if ((((((float)(var0.getRandom().nextInt(100))) + (var0.getRandom().nextFloat())) < (var5)) ? (1) : (0)) == (0)){
							continue;
						}else{
							int var6 = 0;
							int var7 = 0;
							if (var3.isDamageableItem()){
								var6 = var3.getMaxDamage();
								var7 = (int)(WyHelper.clamp((long)(var0.getRandom().nextInt(var6)),50L,(long)(var6)));
								ServerLevel var8 = ((ServerLevel)(var0));
								Consumer<Item> var9 = (l0) -> {
		};
								var3.hurtAndBreak(var7,var8,(ServerPlayer)(null),var9);
								continue;
							}else{
								if ((var4) > (0F)){
									var6 = (1) + ((int)(((float)(var3.getCount())) / (var4)));
									var7 = (int)(WyHelper.clamp((long)(var0.getRandom().nextInt(var6)),1L,(long)(var6)));
									var3.shrink(var7);
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
		}else{
			return;
		}
	}
}