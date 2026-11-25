package xyz.pixelatedw.mineminenomi.items;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.triggers.DFEncyclopediaCompletionTrigger;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler$Client;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenEncyclopediaScreenPacket;

public class DFEncyclopediaItem extends Item {
	// Fields
	private static final Component CONFIG_ERROR = ModI18n.SYSTEM_MESSAGE_RANDOMIZED_FRUITS.copy().withStyle(ChatFormatting.RED);

	public DFEncyclopediaItem() {
		super(new Item.Properties().stacksTo(1));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if (var1.isClientSide){
			return InteractionResultHolder.pass(var4);
		}else{
			if (!(ServerConfig.getRandomizedFruits())){
				var2.displayClientMessage((Component)(ModI18n.SYSTEM_MESSAGE_RANDOMIZED_FRUITS),true);
				return InteractionResultHolder.fail(var4);
			}else{
				ModNetwork.sendTo(new SOpenEncyclopediaScreenPacket(var4),var2);
				return InteractionResultHolder.success(var4);
			}
		}
	}
	public void appendHoverText(ItemStack var1, Item.TooltipContext var2, List<Component> var3, TooltipFlag var4) {
		if ((!(ServerConfig.getRandomizedFruits())) && (!(RandomizedFruitsHandler$Client.HAS_RANDOMIZED_FRUIT))){
			var3.add(CONFIG_ERROR);
		}else{
			int var5 = ((HashMap)(var1.get(ModDataComponents.ENCYCLOPEDIA_UNLOCKS))).size();
			Object[] var6 = new Object[]{Integer.valueOf(var5)};
			((java.util.List)(var3)).add((Object)(Component.translatable(ModI18n.ITEM_DF_ENCYCLOPEDIA_ENTRIES,var6).withStyle(ChatFormatting.GRAY)));
			float var7 = getCompletion(var1);
			if ((var7) > (0F)){
				Object[] var8 = new Object[1];
				String var9 = String.format("%.2f",new Object[]{Float.valueOf((var7) * (100F))});
				String var10 = var9 + "%";
				var8[0] = var10;
				((java.util.List)(var3)).add((Object)(Component.translatable(ModI18n.ITEM_DF_ENCYCLOPEDIA_COMPLETION,var8).withStyle(ChatFormatting.GRAY)));
			}else{
				return;
			}
		}
	}
	public static void addFruitClues(ItemStack var0, ResourceLocation var1, DFEncyclopediaEntry var2) {
		if ((!(var0.isEmpty())) && ((var0.getItem()) == (ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()))){
			HashMap var3 = ((HashMap)(var0.get(ModDataComponents.ENCYCLOPEDIA_UNLOCKS)));
			var3.put(var1,var2);
			var0.set(ModDataComponents.ENCYCLOPEDIA_UNLOCKS,var3);
			return;
		}else{
			WyDebug.error("Trying to add clues to a non-encyclopedia item");
			return;
		}
	}
	public static Collection<DFEncyclopediaEntry> getEntries(ItemStack var0) {
		if ((!(var0.isEmpty())) && (((var0.getItem()) == (ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get())) && (var0.has(ModDataComponents.ENCYCLOPEDIA_UNLOCKS)))){
			return (Collection<DFEncyclopediaEntry>) (Collection) (((HashMap)(var0.get(ModDataComponents.ENCYCLOPEDIA_UNLOCKS))).values());
		}else{
			return Sets.newHashSet();
		}
	}
	public static Optional<DFEncyclopediaEntry> getEntry(ItemStack var0, AkumaNoMiItem var1) {
		if ((!(var0.isEmpty())) && (((var0.getItem()) == (ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get())) && (var0.has(ModDataComponents.ENCYCLOPEDIA_UNLOCKS)))){
			HashMap var2 = ((HashMap)(var0.get(ModDataComponents.ENCYCLOPEDIA_UNLOCKS)));
			ResourceLocation var3 = var1.getRegistryKey();
			if (var2.containsKey(var3)){
				DFEncyclopediaEntry var4 = ((DFEncyclopediaEntry)(var2.get(var3)));
				var4.setDevilFruit(var1);
				return (Optional<DFEncyclopediaEntry>) (Optional) (Optional.of(var4));
			}else{
				return (Optional<DFEncyclopediaEntry>) (Optional) (Optional.empty());
			}
		}else{
			return (Optional<DFEncyclopediaEntry>) (Optional) (Optional.empty());
		}
	}
	public static float getCompletion(ItemStack var0) {
		if ((!(var0.isEmpty())) && (((var0.getItem()) == (ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get())) && (var0.has(ModDataComponents.ENCYCLOPEDIA_UNLOCKS)))){
			int var1 = 0;
			Iterator var2 = ((HashMap)(var0.get(ModDataComponents.ENCYCLOPEDIA_UNLOCKS))).values().iterator();
			do{
				if (var2.hasNext()){
					var1 = (var1) + (((DFEncyclopediaEntry)(var2.next())).getCompletion());
					continue;
				}else{
					break;
				}
			} while (true);
			return ((float)(var1)) / ((float)((ModValues.DEVIL_FRUITS.size()) * (3)));
		}else{
			return -1F;
		}
	}
	public static boolean updateEncyclopediae(Player var0, ResourceLocation var1, DFEncyclopediaEntry var2) {
		ResourceLocation var7 = null;
		DFEncyclopediaEntry var8 = null;
		ServerPlayer var9 = null;
		if (var0 instanceof ServerPlayer){
			ServerPlayer var3 = ((ServerPlayer)(var0));
			List var4 = ItemsHelper.getAllInventoryItems((LivingEntity)(var0));
			Predicate<ItemStack> var5 = (l0) -> {
			return (l0.getItem()) == (ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get());
		};
			List var6 = ((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(var4.stream())).filter((Predicate)(var5)))).collect((Collector)(Collectors.toList()))));
			if ((var6.size()) <= (0)){
				return false;
			}else{
				var7 = var1;
				var8 = var2;
				var9 = var3;
				final ResourceLocation var7_f1 = var7;
				final DFEncyclopediaEntry var8_f2 = var8;
				final ServerPlayer var9_f3 = var9;
				Consumer<ItemStack> var10 = (l0) -> {
			addFruitClues(l0,var7_f1,var8_f2);
			((DFEncyclopediaCompletionTrigger)(ModAdvancements.ENCYCLOPEDIA_COMPLETION.get())).trigger(var9_f3,l0);
		};
				((java.lang.Iterable)(var6)).forEach((Consumer)(var10));
				return true;
			}
		}else{
			return false;
		}
	}
}