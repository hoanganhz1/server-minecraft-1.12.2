package xyz.pixelatedw.mineminenomi.handlers.world;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.DFEncyclopediaItem;

public class RandomizedFruitsHandler$Client {
	// Fields
	public static boolean HAS_RANDOMIZED_FRUIT;
	public static boolean DIRTY;
	public static HashMap<Integer, Long> FRUIT_SEEDS;

	public static void handleHiddenFruitName(Player var0, ItemStack var1, List<Component> var2) {
		if ((HAS_RANDOMIZED_FRUIT) && (!(var1.isEmpty()))){
			Item var3 = var1.getItem();
			if (var3 instanceof AkumaNoMiItem){
				AkumaNoMiItem var4 = ((AkumaNoMiItem)(var3));
				boolean var5 = ((Boolean)(var1.get(ModDataComponents.FRUIT_DECIPHERED))).booleanValue();
				var2.clear();
				if (var5){
					((java.util.List)(var2)).add((Object)(var4.getShiftedFruit().getDevilFruitName().copy().withStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))));
				}else{
					Iterator var6 = ItemsHelper.getInventoryItems((LivingEntity)(var0)).iterator();
					do{
						if (var6.hasNext()){
							ItemStack var7 = ((ItemStack)(var6.next()));
							if ((var7.isEmpty()) || (!(var7.getItem() instanceof DFEncyclopediaItem))){
								continue;
							}else{
								Optional var8 = DFEncyclopediaItem.getEntry(var7,var4.getShiftedFruit());
								if ((var8.isPresent()) && (((DFEncyclopediaEntry)(var8.get())).isComplete())){
									var1.set(ModDataComponents.FRUIT_DECIPHERED,Boolean.valueOf(true));
									continue;
								}else{
									continue;
								}
							}
						}else{
							break;
						}
					} while (true);
					var2.add(ModI18n.ITEM_GENERIC_FRUIT);
				}
			}else{
				return;
			}
		}
	}
	static  {
		HAS_RANDOMIZED_FRUIT = false;
		DIRTY = false;
		FRUIT_SEEDS = (HashMap) (new HashMap());
	}
}