package xyz.pixelatedw.mineminenomi.handlers.world;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.DFEncyclopediaItem;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.randfruit.SSetFruitSeedsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.randfruit.SSetRandomizedFruitsPacket;

public class RandomizedFruitsHandler$Common {
	// Fields
	public static long SEED;

	public static void setRandomizationSeed(Player var0) {
		ModNetwork.sendTo(new SSetRandomizedFruitsPacket(ServerConfig.getRandomizedFruits()),var0);
		if (ServerConfig.getRandomizedFruits()){
			HashMap var1 = new HashMap();
			long var2 = new Random(((ServerLevel)(var0.level())).getSeed()).nextLong();
			Iterator var3 = ModValues.DEVIL_FRUITS.iterator();
			do{
				if (var3.hasNext()){
					AkumaNoMiItem var4 = ((AkumaNoMiItem)(var3.next()));
					ResourceLocation var5 = BuiltInRegistries.ITEM.getKey(var4);
					if ((var5) == (null)){
						continue;
					}else{
						int var6 = var5.hashCode();
						long var7 = (var2) + ((long)(var6));
						var1.put(Integer.valueOf(var6),Long.valueOf(var7));
						continue;
					}
				}else{
					break;
				}
			} while (true);
			SEED = var2;
			ModNetwork.sendTo(new SSetFruitSeedsPacket(var1,var2),var0);
		}
	}
	public static void handleClueUsage(Player var0, ItemStack var1) {
		if ((!(var1.isEmpty())) && (((var1.getItem()) == (Items.PAPER)) && ((!(var1.has(ModDataComponents.FRUIT_CLUE))) && (!(var0.level().isClientSide))))){
			if (!(ServerConfig.getRandomizedFruits())){
				var0.displayClientMessage((Component)(ModI18n.SYSTEM_MESSAGE_RANDOMIZED_FRUITS),false);
				return;
			}else{
				DFEncyclopediaEntry var2 = ((DFEncyclopediaEntry)(var1.get(ModDataComponents.FRUIT_CLUE)));
				ResourceLocation var3 = var2.getDevilFruit().getRegistryKey();
				if (DFEncyclopediaItem.updateEncyclopediae(var0,var3,var2)){
					var1.shrink(1);
					Item var4 = ((Item)(BuiltInRegistries.ITEM.get(var3)));
					Object[] var5 = new Object[]{var4.getDescription().getString()};
					var0.displayClientMessage((Component)(Component.translatable(ModI18n.ITEM_MESSAGE_GAINED_FRUIT_CLUE,var5)),false);
				}
				return;
			}
		}else{
			return;
		}
	}
}