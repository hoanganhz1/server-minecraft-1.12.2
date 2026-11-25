package xyz.pixelatedw.mineminenomi.items;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.helpers.DevilFruitHelper;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class AkumaNoMiBoxItem extends Item {
	// Fields
	private static final Set<Item> KEY_SET = ImmutableSet.of(((Item)(ModItems.KEY.get())));
	private Pair<Integer, ResourceKey<LootTable>> tier;

	public AkumaNoMiBoxItem(Pair<Integer, ResourceKey<LootTable>> var1) {
		super(new Item.Properties().stacksTo(1));
		this.tier = var1;
	}
	public int getKeySlot(Player var1) {
		if (!(var1.getInventory().hasAnyOf(KEY_SET))){
			var1.sendSystemMessage(ModI18n.ITEM_MESSAGE_NEED_KEY);
			return -1;
		}else{
			int var2 = 0;
			do{
				if ((var2) < (var1.getInventory().getContainerSize())){
					ItemStack var3 = var1.getInventory().getItem(var2);
					if (((var3) != (null)) && ((!(var3.isEmpty())) && ((var3.getItem()) == (ModItems.KEY.get())))){
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
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if (!(var1.isClientSide)){
			int var5 = this.getKeySlot(var2);
			if ((var5) < (0)){
				return InteractionResultHolder.fail(var4);
			}else{
				var2.getInventory().removeItem(var5,1);
				var2.getInventory().removeItem(var4);
				OneFruitWorldData var6 = OneFruitWorldData.get();
				LootTable var7 = var2.level().getServer().reloadableRegistries().getLootTable(((ResourceKey)(this.tier.getValue())));
				ObjectArrayList var8 = var7.getRandomItems(new LootParams.Builder(((ServerLevel)(var2.level()))).withLuck(var2.getLuck()).withParameter(LootContextParams.THIS_ENTITY,var2).create(LootContextParamSets.EMPTY));
				ItemStack var9 = ItemStack.EMPTY;
				Iterator var10 = var8.iterator();
				do{
					if (var10.hasNext()){
						ItemStack var11 = ((ItemStack)(var10.next()));
						if (((var11) == (null)) || ((!(var11.getItem() instanceof AkumaNoMiItem)) || (!(var6.isFruitAvailable(((AkumaNoMiItem)(var11.getItem()))))))){
							continue;
						}else{
							var9 = var11;
							break;
						}
					}else{
						break;
					}
				} while (true);
				if ((var9) == (null)){
					var2.getInventory().removeItem(var4);
					return InteractionResultHolder.success(var4);
				}else{
					if (!(var9.getItem() instanceof AkumaNoMiItem)){
						var2.getInventory().add(var9);
						return InteractionResultHolder.success(var4);
					}else{
						if (DevilFruitHelper.hasDFLimitInInventory(var2)){
							var2.drop(var9,true);
							return InteractionResultHolder.success(var4);
						}else{
							AkumaNoMiItem var11_1 = ((AkumaNoMiItem)(var9.getItem()));
							var2.getInventory().add(var9);
							String var12 = var4.getDisplayName().getString();
							String var13 = "Obtained from " + var12;
							var6.inventoryOneFruit(var11_1,(LivingEntity)(var2),var13);
							return InteractionResultHolder.success(var4);
						}
					}
				}
			}
		}else{
			return InteractionResultHolder.success(var4);
		}
	}
	public int getTierLevel() {
		return ((Integer)(this.tier.getKey())).intValue();
	}
}