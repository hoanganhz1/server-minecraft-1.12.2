package xyz.pixelatedw.mineminenomi.items.armors;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.ColaFuelAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModMaterials;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateColaAmountPacket;

public class ColaBackpackItem extends Mod3DArmorItem {
	// Fields
	private static final int COLA_REFILL_TICKS = 20;

	public ColaBackpackItem() {
		super(ModMaterials.COLA_BACKPACK_MATERIAL,ArmorItem.Type.CHESTPLATE);
	}
	public void inventoryTick(ItemStack var1, Level var2, Entity var3, int var4, boolean var5) {
		if ((!(var2.isClientSide)) && (var3 instanceof Player)){
			Player var6 = ((Player)(var3));
			if (!(ItemsHelper.hasItemInSlot((LivingEntity)(var6),EquipmentSlot.CHEST,(Item)(this)))){
				return;
			}else{
				AbilityData var7 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var6)))).orElse((Object)(null))));
				EntityStatsData var8 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var6)))).orElse((Object)(null))));
				if (((var7) != (null)) && ((var8) != (null))){
					if ((var7.hasPassiveAbility(((AbilityCore)(ColaFuelAbility.INSTANCE.get())))) && (((var6.tickCount) % (20)) == (0))){
						int var9 = this.getColaSlot(var6);
						int var10 = this.getUltraColaSlot(var6);
						if (((var9) != (-1)) && (((var8.getCola()) + (25)) <= (var8.getMaxCola()))){
							var8.alterCola(25);
							var6.getInventory().removeItem(var9,1);
							var6.addItem(new ItemStack(((ItemLike)(ModItems.EMPTY_COLA.get()))));
						}else{
							if (((var10) != (-1)) && (((var8.getCola()) + (100)) <= (var8.getMaxCola()))){
								var8.alterCola(100);
								var6.getInventory().removeItem(var10,1);
								var6.addItem(new ItemStack(((ItemLike)(ModItems.EMPTY_ULTRA_COLA.get()))));
							}
						}
						ModNetwork.sendTo(new SUpdateColaAmountPacket((LivingEntity)(var6)),var6);
					}
					return;
				}else{
					return;
				}
			}
		}else{
			return;
		}
	}
	public int getColaSlot(Player var1) {
		if (!(var1.getInventory().contains(new ItemStack(((ItemLike)(ModItems.COLA.get())))))){
			return -1;
		}else{
			int var2 = 0;
			do{
				if ((var2) < (var1.getInventory().getContainerSize())){
					ItemStack var3 = var1.getInventory().getItem(var2);
					if ((!(var3.isEmpty())) && ((var3.getItem()) == (ModItems.COLA.get()))){
						return var2;
					}else{
						var2++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return -1;
		}
	}
	public int getUltraColaSlot(Player var1) {
		if (!(var1.getInventory().contains(new ItemStack(((ItemLike)(ModItems.ULTRA_COLA.get())))))){
			return -1;
		}else{
			int var2 = 0;
			do{
				if ((var2) < (var1.getInventory().getContainerSize())){
					ItemStack var3 = var1.getInventory().getItem(var2);
					if ((!(var3.isEmpty())) && ((var3.getItem()) == (ModItems.ULTRA_COLA.get()))){
						return var2;
					}else{
						var2++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return -1;
		}
	}
}