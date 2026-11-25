package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import xyz.pixelatedw.mineminenomi.api.commands.CurrencyTypeArgument;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nCommands;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class PouchCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		Predicate<CommandSourceStack> var0 = (l0) -> {
			return l0.hasPermission(0);
		};
		LiteralArgumentBuilder var1 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("pouch"))).requires((Predicate)(var0))));
		Command var2 = (l0) -> {
			return createPouch(l0,((Currency)(l0.getArgument("currency",Currency.class))),999999999L);
		};
		var1.then(Commands.literal("ALL").then(Commands.argument("currency",(ArgumentType)(CurrencyTypeArgument.currencyType())).executes(var2)));
		Command var3 = (l0) -> {
			return createPouch(l0,((Currency)(l0.getArgument("currency",Currency.class))),LongArgumentType.getLong(l0,"amount"));
		};
		var1.then(Commands.argument("amount",(ArgumentType)(LongArgumentType.longArg(1L,999999999L))).then(Commands.argument("currency",(ArgumentType)(CurrencyTypeArgument.currencyType())).executes(var3)));
		Command var4 = (l0) -> {
			return takeFromPouch(l0,((CommandSourceStack)(l0.getSource())).getPlayerOrException(),LongArgumentType.getLong(l0,"amount"));
		};
		var1.then(Commands.literal("take").then(Commands.argument("amount",(ArgumentType)(LongArgumentType.longArg(1L,999999999L))).executes(var4)));
		return var1;
	}
	private static int takeFromPouch(CommandContext<CommandSourceStack> var0, ServerPlayer var1, long var2) {
		if ((var2) <= (0L)){
			return 1;
		}else{
			ItemStack var3 = var1.getMainHandItem();
			if (var3.isEmpty()){
				var3 = var1.getOffhandItem();
				if (var3.isEmpty()){
					return 1;
				}
			}
			EntityStatsData var4 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).orElse((Object)(null))));
			if ((var4) == (null)){
				return -1;
			}else{
				long var5 = 0;
				long var6 = 0;
				if ((var3.getItem()) == (ModItems.BELLY_POUCH.get())){
					var5 = ((Long)(var3.get(ModDataComponents.BELLY))).longValue();
					if ((var5) < (var2)){
						var2 = var5;
					}
					var4.alterBelly(var2,StatChangeSource.COMMAND);
					var6 = (var5) - (var2);
					if ((var6) > (0L)){
						var3.set(ModDataComponents.BELLY,Long.valueOf(var6));
					}else{
						var3.shrink(1);
					}
					ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var1),(EntityStatsData)(var4)),(Player)(var1));
				}else{
					if ((var3.getItem()) == (ModItems.EXTOL_POUCH.get())){
						var5 = ((Long)(var3.get(ModDataComponents.EXTOL))).longValue();
						if ((var5) < (var2)){
							var2 = var5;
						}
						var4.alterExtol(var2,StatChangeSource.COMMAND);
						var6 = (var5) - (var2);
						if ((var6) > (0L)){
							var3.set(ModDataComponents.EXTOL,Long.valueOf(var6));
						}else{
							var3.shrink(1);
						}
						ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var1),(EntityStatsData)(var4)),(Player)(var1));
					}else{
						return 1;
					}
				}
				return 1;
			}
		}
	}
	private static int createPouch(CommandContext<CommandSourceStack> var0, Currency var1, long var2) {
		if ((var2) <= (0L)){
			return 1;
		}else{
			try{
				ServerPlayer var3 = ((CommandSourceStack)(var0.getSource())).getPlayerOrException();
				switch (PouchCommand$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$Currency[var1.ordinal()]){
				case 1:
					createExtolPouch(var3,var2);
					break;
				case 2:
					createBellyPouch(var3,var2);
					break;
				default:
					createBellyPouch(var3,var2);
					break;
				}
			}catch(CommandSyntaxException var3_1){
				var3_1.printStackTrace();
			}
			return 1;
		}
	}
	private static void createBellyPouch(ServerPlayer var0, long var1) {
		if (ItemsHelper.hasInventoryFull((LivingEntity)(var0))){
			var0.sendSystemMessage(ModI18nCommands.POUCH_INVENTORY_FULL);
			return;
		}else{
			EntityStatsData var2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var0)))).orElse((Object)(null))));
			if ((var2) == (null)){
				return;
			}else{
				if ((var2.getBelly()) <= (0L)){
					return;
				}else{
					if (((var2.getBelly()) - (var1)) >= (0L)){
						var2.alterBelly(-(var1),StatChangeSource.COMMAND);
					}else{
						var1 = var2.getBelly();
						var2.alterBelly(-(var1),StatChangeSource.COMMAND);
					}
					ItemStack var3 = new ItemStack(((ItemLike)(ModItems.BELLY_POUCH.get())));
					var3.set(ModDataComponents.BELLY,Long.valueOf(var1));
					var0.getInventory().add(var3);
					ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var0)),(Player)(var0));
					return;
				}
			}
		}
	}
	private static void createExtolPouch(ServerPlayer var0, long var1) {
		if (ItemsHelper.hasInventoryFull((LivingEntity)(var0))){
			var0.sendSystemMessage(ModI18nCommands.POUCH_INVENTORY_FULL);
			return;
		}else{
			EntityStatsData var2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var0)))).orElse((Object)(null))));
			if ((var2) == (null)){
				return;
			}else{
				if ((var2.getExtol()) <= (0L)){
					return;
				}else{
					if (((var2.getExtol()) - (var1)) >= (0L)){
						var2.alterExtol(-(var1),StatChangeSource.COMMAND);
					}else{
						var1 = var2.getExtol();
						var2.alterExtol(-(var1),StatChangeSource.COMMAND);
					}
					ItemStack var3 = new ItemStack(((ItemLike)(ModItems.EXTOL_POUCH.get())));
					var3.set(ModDataComponents.EXTOL,Long.valueOf(var1));
					var0.getInventory().add(var3);
					ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var0)),(Player)(var0));
					return;
				}
			}
		}
	}
}