package xyz.pixelatedw.mineminenomi.items.weapons;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruArtsPickaxeAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ItemSpawnComponent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class DoruPickaxeItem extends PickaxeItem {
	// Fields
	private final Supplier<AbilityCore<DoruDoruArtsPickaxeAbility>> coreSupplier;

	public DoruPickaxeItem(Tier var1, int var2, float var3) {
		super(var1,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of((float)(var2),var3).build()));
		this.coreSupplier = DoruDoruArtsPickaxeAbility.INSTANCE;
	}
	public void inventoryTick(ItemStack var1, Level var2, Entity var3, int var4, boolean var5) {
		super.inventoryTick(var1,var2,var3,var4,var5);
		if (var3 instanceof Player){
			Player var6 = ((Player)(var3));
			if ((!(var3.level().isClientSide)) && ((this.coreSupplier) != (null))){
				AbilityData var7 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var6)))).orElse((Object)(null))));
				if ((var7) == (null)){
					return;
				}else{
					boolean var8 = true;
					Iterator var9 = var7.getEquippedAbilities().iterator();
					do{
						if (var9.hasNext()){
							IAbility var10 = ((IAbility)(var9.next()));
							if ((var10) == (null)){
								continue;
							}else{
								Optional var11 = var10.getComponent(((AbilityComponentKey)(ModAbilityComponents.ITEM_SPAWN.get())));
								DoruPickaxeItem var12 = this;
								Function<ItemSpawnComponent, Boolean> var13 = (l0) -> {
			return Boolean.valueOf((l0.getAbility().getCore().equals(this.coreSupplier.get())) && (l0.isActive()));
		};
								if (((Boolean)(((java.util.Optional)(var11)).map((Function)(var13)).orElse(Boolean.valueOf(false)))).booleanValue()){
									var8 = false;
									continue;
								}else{
									continue;
								}
							}
						}else{
							break;
						}
					} while (true);
					if (var8){
						var6.getInventory().removeItem(var1);
					}else{
						return;
					}
				}
			}else{
				return;
			}
		}
	}
	public float getDestroySpeed(ItemStack var1, BlockState var2) {
		if (((var1.getItem()) == (ModWeapons.DORU_PICKAXE.get())) && ((var2.getBlock()) == (ModBlocks.WAX.get()))){
			return 999F;
		}else{
			return super.getDestroySpeed(var1,var2);
		}
	}
	public boolean isEnchantable(ItemStack var1) {
		return false;
	}
}