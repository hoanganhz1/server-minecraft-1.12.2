package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.EventHooks;
import xyz.pixelatedw.mineminenomi.abilities.EmptyHandsAbility;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.ArtOfWeatherHelper;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.blocks.TrapAbilityBlock;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class ItemsHandler {
	public static void removeImbuingTag(@Nullable() ItemStack var0) {
		if (((var0) != (null)) && ((!(var0.isEmpty())) && (var0.has(ModDataComponents.IMBUING)))){
			var0.remove(ModDataComponents.IMBUING);
		}
	}
	public static void handleImbuingTagging(LivingEntity var0) {
		ItemStack var1 = var0.getMainHandItem();
		if (!(ItemsHelper.isImbuable(var1))){
			return;
		}else{
			if (!(var1.isEmpty())){
				if ((HakiHelper.hasImbuingActive(var0)) && (!(var1.has(ModDataComponents.IMBUING)))){
					var1.set(ModDataComponents.IMBUING,Boolean.valueOf(true));
				}else{
					if ((var1.has(ModDataComponents.IMBUING)) && (!(HakiHelper.hasImbuingActive(var0)))){
						var1.remove(ModDataComponents.IMBUING);
					}else{
						return;
					}
				}
			}
			return;
		}
	}
	public static void handleEnmaCurse(Player var0) {
		ItemStack var1 = var0.getItemBySlot(EquipmentSlot.MAINHAND);
		if ((var1.getItem()) != (ModWeapons.ENMA.get())){
			return;
		}else{
			if (var1.has(ModDataComponents.IS_CLONE)){
				return;
			}else{
				HakiData var2 = ((HakiData)(((java.util.Optional)(HakiData.get((LivingEntity)(var0)))).orElse((Object)(null))));
				AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).orElse((Object)(null))));
				if (((var2) != (null)) && ((var3) != (null))){
					float var4 = var2.getBusoshokuHakiExp();
					if ((var4) < (20F)){
						var0.hurt(var0.damageSources().wither(),var0.getMaxHealth());
					}else{
						if (((var4) >= (20F)) && ((var4) < (30F))){
							var0.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,200,1,false,false));
							var0.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,200,1,false,false));
							if (!(var0.hasEffect(MobEffects.WITHER))){
								var0.addEffect(new MobEffectInstance(MobEffects.WITHER,100,1,false,false));
							}else{
								return;
							}
						}else{
							if (((var4) >= (30F)) && (!(HakiHelper.hasImbuingActive((LivingEntity)(var0))))){
								var2.alterHakiOveruse(2);
								var0.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,100,1,false,false));
								var0.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,100,1,false,false));
								if (!(var0.hasEffect(MobEffects.WITHER))){
									var0.addEffect(new MobEffectInstance(MobEffects.WITHER,100,2,false,false));
								}else{
									return;
								}
							}else{
								return;
							}
						}
					}
					return;
				}else{
					return;
				}
			}
		}
	}
	public static boolean handleEmptyHandsPickup(Player var0, ItemEntity var1) {
		if (!(var0.getMainHandItem().isEmpty())){
			return false;
		}else{
			Function<AbilityData, EmptyHandsAbility> var2 = (l0) -> {
			return ((EmptyHandsAbility)(l0.getPassiveAbility(((AbilityCore)(EmptyHandsAbility.INSTANCE.get())))));
		};
			Function<EmptyHandsAbility, Boolean> var3 = (l0) -> {
			return Boolean.valueOf(!(l0.isPaused()));
		};
			if (!(((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).map((Function)(var2)))).map((Function)(var3)).orElse(Boolean.valueOf(false)))).booleanValue())){
				return false;
			}else{
				ItemStack var4 = var1.getItem();
				Item var5 = var4.getItem();
				int var6 = var4.getCount();
				int var7 = ItemsHelper.getFreeOrSameSlot(var0,var4);
				if ((var7) < (0)){
					return true;
				}else{
					ItemStack var8 = var4.copy();
					if (((!(var1.hasPickUpDelay())) && (!(((var1.getOwner()) != (null)) && ((((var1.lifespan) - (var1.getAge())) > (200)) && (!(var1.getOwner().getUUID().equals(var0.getUUID()))))))) && (!(((var6) > (0)) && (!(var0.getInventory().add(var7,var4)))))){
						var8.setCount((var8.getCount()) - (var1.getItem().getCount()));
						EventHooks.fireItemPickupPost(var1,var0,var8);
						var0.take((Entity)(var1),var6);
						if (var4.isEmpty()){
							var1.discard();
							var4.setCount(var6);
						}
						var0.awardStat(Stats.ITEM_PICKED_UP.get(var5),var6);
						var0.onItemPickup(var1);
						return true;
					}else{
						return false;
					}
				}
			}
		}
	}
	public static void handleRandomizedFruitsTooltip(ItemStack var0, List<Component> var1) {
		if (var0.getItem() instanceof AkumaNoMiItem){
			var1.add(0,ModI18n.ITEM_GENERIC_FRUIT);
			int var2 = 0;
			do{
				if ((var2) < (var1.size())){
					if (((Component)(var1.get(var2))).toString().contains("item.color")){
						var1.remove(var2);
						break;
					}else{
						var2++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
	}
	public static void insideTrapBlock(LivingEntity var0) {
		BlockPos var1 = var0.blockPosition().above((int)(var0.getEyeHeight()));
		BlockState var2 = var0.level().getBlockState(var1);
		if (var2.isAir()){
			return;
		}else{
			Block var3 = var2.getBlock();
			if (var3 instanceof TrapAbilityBlock){
				TrapAbilityBlock var4 = ((TrapAbilityBlock)(var3));
				if (var4.canWalkOn(var0)){
					var0.setPos(var0.getX(),(var0.getY()) + (1D),var0.getZ());
					var0.fallDistance = 0F;
					var0.setOnGround(true);
				}else{
					if (var0 instanceof Player){
						Player var5 = ((Player)(var0));
						if (!(var5.isCreative())){
							var5.getAbilities().flying = false;
						}
					}
					if ((var4.getPotionEffect()) != (null)){
						var0.addEffect(var4.getPotionEffect());
					}
					DamageSource var5_1 = var4.getDamageSource(var0.level());
					if ((var5_1) == (null)){
						var5_1 = var0.damageSources().inWall();
					}
					var0.hurt(var5_1,(float)(var4.getDamageDealt()));
				}
			}
			return;
		}
	}
	public static void cuckShield(LivingEntity var0, LivingEntity var1) {
		if (HakiHelper.hasAdvancedBusoActive(var1)){
			Item var2 = var0.getMainHandItem().getItem();
			Item var3 = var0.getOffhandItem().getItem();
			if (var0 instanceof Player){
				Player var4 = ((Player)(var0));
				if (((Math.random()) > (0.5D)) && (!((!(((java.lang.Object)(var2)).equals((Object)(Items.SHIELD)))) && (!(((java.lang.Object)(var3)).equals((Object)(Items.SHIELD))))))){
					var4.getCooldowns().addCooldown(Items.SHIELD,100);
					var0.stopUsingItem();
					var0.level().broadcastEntityEvent((Entity)(var1),(byte)(30));
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	public static void kairosekiTooltip(ItemStack var0, List<Component> var1) {
		if (var0.is(ModTags$Items.KAIROSEKI)){
			String var2 = String.valueOf((Object)(ChatFormatting.YELLOW));
			String var3 = ModI18n.ITEM_KAIROSEKI_ITEM.getString();
			String var4 = var2 + var3;
			MutableComponent var5 = Component.literal(var4);
			if (!(var1.contains((Object)(var5)))){
				((java.util.List)(var1)).add((Object)(Component.empty()));
				((java.util.List)(var1)).add((Object)(var5));
			}else{
				return;
			}
		}
	}
	public static void dyeableTooltip(ItemStack var0, List<Component> var1) {
		MultiChannelItemColor var2 = ((MultiChannelItemColor)(var0.get(ModDataComponents.MULTI_CHANNEL_DYES)));
		if (((var2) != (null)) && ((var2.getMaxLayers()) > (0))){
			Component var3 = ((Component)(var1.get(0)));
			int var4 = var2.getMaxLayers();
			String var5 = " \uD83C\uDFA8" + var4;
			MutableComponent var6 = Component.literal(var5);
			if (!(var3.contains((Component)(var6)))){
				((java.util.List)(var1)).set(0,(Object)(var3.copy().append((Component)(var6))));
			}else{
				return;
			}
		}
	}
	public static void weatherBallChargesTooltip(ItemStack var0, List<Component> var1) {
		if (!(ItemsHelper.isClimaTact(var0))){
			return;
		}else{
			((java.util.List)(var1)).add(1,(Object)(Component.literal(ArtOfWeatherHelper.getChargesString(var0))));
			return;
		}
	}
}