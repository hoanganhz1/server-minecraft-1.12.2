package xyz.pixelatedw.mineminenomi.commands;

import com.google.common.base.Strings;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.UsernameCache;
import xyz.pixelatedw.mineminenomi.api.OneFruitEntry;
import xyz.pixelatedw.mineminenomi.api.OneFruitEntry$HistoryEntry;
import xyz.pixelatedw.mineminenomi.api.OneFruitEntry$Status;
import xyz.pixelatedw.mineminenomi.api.commands.FruitArgument;
import xyz.pixelatedw.mineminenomi.config.GeneralConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nCommands;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class CheckFruitsCommand {
	// Fields
	private static final Comparator<OneFruitEntry> COMPARE_ENTRIES_ALPHABETICALLY;
	private static final Comparator<Item> COMPARE_FRUITS_ALPHABETICALLY;

	
	private static String getOwnerName(OneFruitEntry var0, Level var1) {
		String var2 = "System";
		if (var0.getOwner().isPresent()){
			if ((var1.getPlayerByUUID(((UUID)(var0.getOwner().get())))) != (null)){
				var2 = var1.getPlayerByUUID(((UUID)(var0.getOwner().get()))).getDisplayName().getString();
			}else{
				if ((UsernameCache.getLastKnownUsername(((UUID)(var0.getOwner().get())))) != (null)){
					var2 = UsernameCache.getLastKnownUsername(((UUID)(var0.getOwner().get())));
				}else{
					var2 = "Unknown Player Name";
				}
			}
		}
		return var2;
	}
	private static String getOwnerName(OneFruitEntry$HistoryEntry var0, Level var1) {
		String var2 = "System";
		if (var0.getOwner().isPresent()){
			if ((var1.getPlayerByUUID(((UUID)(var0.getOwner().get())))) != (null)){
				var2 = var1.getPlayerByUUID(((UUID)(var0.getOwner().get()))).getDisplayName().getString();
			}else{
				if ((UsernameCache.getLastKnownUsername(((UUID)(var0.getOwner().get())))) != (null)){
					var2 = UsernameCache.getLastKnownUsername(((UUID)(var0.getOwner().get())));
				}else{
					var2 = "Unknown Player Name";
				}
			}
		}
		return var2;
	}
	private static String getStatusColor(OneFruitEntry var0) {
		return getStatusColor(var0.getStatus());
	}
	private static String getStatusColor(OneFruitEntry$HistoryEntry var0) {
		return getStatusColor(var0.getStatus());
	}
	private static String getStatusColor(OneFruitEntry$Status var0) {
		String var1 = var0.name();
		if ((var0) == (OneFruitEntry$Status.LOST)){
			String var2 = var0.name();
			String var3 = "§c" + var2 + "§r";
			var1 = var3;
		}else{
			if ((var0) == (OneFruitEntry$Status.DROPPED)){
				String var2_1 = var0.name();
				String var3_1 = "§d" + var2_1 + "§r";
				var1 = var3_1;
			}else{
				if (((var0) != (OneFruitEntry$Status.IN_USE)) && ((var0) != (OneFruitEntry$Status.INVENTORY))){

				}else{
					String var2_2 = var0.name();
					String var3_2 = "§a" + var2_2 + "§r";
					var1 = var3_2;
				}
			}
		}
		return var1;
	}
	private static Component getClickableFruitLocation(OneFruitEntry var0) {
		return buildTeleportComponent(var0.getDimensionLocation(),var0.getBlockPos());
	}
	private static Component getClickableFruitLocation(OneFruitEntry$HistoryEntry var0) {
		return buildTeleportComponent(var0.getDimensionLocation(),var0.getBlockPos());
	}
	private static Component buildTeleportComponent(Optional<ResourceLocation> var0, Optional<BlockPos> var1) {
		if ((var0.isPresent()) && (var1.isPresent())){
			ResourceLocation var2 = ((ResourceLocation)(var0.get()));
			BlockPos var3 = ((BlockPos)(var1.get()));
			String var4 = var2.toString();
			int var5 = var3.getX();
			int var6 = var3.getY();
			int var7 = var3.getZ();
			String var8 = "/execute in " + var4 + " run tp " + var5 + " " + var6 + " " + var7;
			String var9 = var2.toString();
			String var10 = var3.toShortString();
			String var11 = "Teleport to " + var9 + " at " + var10;
			return Component.literal(" | [Teleport to fruit]").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,var8)).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,Component.literal(var11))));
		}else{
			return Component.literal("");
		}
	}
	static  {
		Comparator<OneFruitEntry> var0 = (l0, l1) -> {
			Function<Item, String> lv20_2 = (l2_0) -> {
				return l2_0.getDescription().getString();
			};
			String lv20_3 = ((String)(((java.util.Optional)(l0.getItemFromKey())).map((Function)(lv20_2)).orElse(l0.getKey().toString())));
			Function<Item, String> lv20_4 = (l2_0) -> {
				return l2_0.getDescription().getString();
			};
			return lv20_3.compareToIgnoreCase(((String)(((java.util.Optional)(l1.getItemFromKey())).map((Function)(lv20_4)).orElse(l1.getKey().toString()))));
		};
		COMPARE_ENTRIES_ALPHABETICALLY = var0;
		Comparator<Item> var1 = (l0, l1) -> {
			String lv21_2 = l0.getDescription().getString();
			return lv21_2.compareToIgnoreCase(l1.getDescription().getString());
		};
		COMPARE_FRUITS_ALPHABETICALLY = var1;
	}
public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = Commands.literal("check_fruits");
		Predicate<CommandSourceStack> var1 = (l0) -> {
			return l0.hasPermission((((Boolean)(GeneralConfig.PUBLIC_CHECK_FRUITS.get())).booleanValue()) ? (0) : (2));
		};
		LiteralArgumentBuilder var2 = ((LiteralArgumentBuilder)(((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(var0)).requires((Predicate)(var1)))).executes((Command)(((com.mojang.brigadier.Command)(CheckFruitsCommand::checkFruitsInWorld))))));
		Predicate<CommandSourceStack> var3 = (l0) -> {
			return l0.hasPermission((((Boolean)(GeneralConfig.PUBLIC_CHECK_FRUITS.get())).booleanValue()) ? (0) : (2));
		};
		LiteralArgumentBuilder var4 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("fruit"))).requires((Predicate)(var3))));
		Command var5 = (l0) -> {
			return checkFruitInWorld(l0,FruitArgument.getFruit(l0,"fruit"));
		};
		LiteralArgumentBuilder var6 = ((LiteralArgumentBuilder)(var2.then(var4.then(Commands.argument("fruit",(ArgumentType)(FruitArgument.fruit())).executes(var5)))));
		Predicate<CommandSourceStack> var7 = (l0) -> {
			return l0.hasPermission((((Boolean)(GeneralConfig.PUBLIC_CHECK_FRUITS.get())).booleanValue()) ? (0) : (2));
		};
		LiteralArgumentBuilder var8 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(var6)).then((ArgumentBuilder)(((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("list"))).requires((Predicate)(var7)))).executes((Command)(((com.mojang.brigadier.Command)(CheckFruitsCommand::checkFruitsInWorld))))))));
		Predicate<CommandSourceStack> var9 = (l0) -> {
			return l0.hasPermission(2);
		};
		LiteralArgumentBuilder var10 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("history"))).requires((Predicate)(var9))));
		Command var11 = (l0) -> {
			return exportFruitHistory(l0,FruitArgument.getFruit(l0,"fruit"));
		};
		RequiredArgumentBuilder var12 = ((RequiredArgumentBuilder)(Commands.argument("fruit",(ArgumentType)(FruitArgument.fruit())).then(Commands.literal("export").executes(var11))));
		Command var13 = (l0) -> {
			return fruitHistory(l0,FruitArgument.getFruit(l0,"fruit"),IntegerArgumentType.getInteger(l0,"page"));
		};
		RequiredArgumentBuilder var14 = ((RequiredArgumentBuilder)(var12.then(Commands.argument("page",(ArgumentType)(IntegerArgumentType.integer())).executes(var13))));
		Command var15 = (l0) -> {
			return fruitHistory(l0,FruitArgument.getFruit(l0,"fruit"),-1);
		};
		var8.then(var10.then(var14.executes(var15)));
		return var0;
	}
	private static int checkFruitInWorld(CommandContext<CommandSourceStack> var0, AkumaNoMiItem var1) {
		MutableComponent var14 = null;
		if (!(ServerConfig.hasOneFruitPerWorldSimpleLogic())){
			((CommandSourceStack)(var0.getSource())).sendFailure(ModI18nCommands.CHECK_FRUIT_OFPW_ONLY);
			return 0;
		}else{
			ServerLevel var2 = ((CommandSourceStack)(var0.getSource())).getLevel();
			OneFruitEntry var3 = OneFruitWorldData.get().getOneFruitEntry(var1.getRegistryKey());
			if ((var3) == (null)){
				CommandSourceStack var4 = ((CommandSourceStack)(var0.getSource()));
				String var5 = var1.getDescription().getString();
				String var6 = "No entry found for " + var5;
				var4.sendFailure((Component)(Component.literal(var6)));
				return 0;
			}else{
				Item var4_1 = ((Item)(BuiltInRegistries.ITEM.get(var3.getKey())));
				if ((var4_1) == (null)){
					return 0;
				}else{
					String var5_1 = var4_1.getDescription().getString();
					String var6_1 = getOwnerName(var3,(Level)(var2));
					String var7 = getStatusColor(var3);
					MutableComponent var8 = Component.literal("===============================================\n");
					var8.append("§l§6Fruit Name | Player Name | Status \n");
					String var9 = var5_1;
					String var10 = var6_1;
					String var11 = var7;
					String var12 = var9 + " | " + var10 + " | " + var11;
					var8.append(var12);
					var8.append(getClickableFruitLocation(var3));
					var8.append("\n");
					var8.append("===============================================");
					CommandSourceStack var13 = ((CommandSourceStack)(var0.getSource()));
					var14 = var8;
					final MutableComponent var14_f1 = var14;
					Supplier<Component> var15 = () -> {
			return var14_f1;
		};
					var13.sendSuccess(var15,false);
					return 0;
				}
			}
		}
	}
	private static int exportFruitHistory(CommandContext<CommandSourceStack> var0, AkumaNoMiItem var1) {
		AkumaNoMiItem var17_2 = null;
		if (!(ServerConfig.hasOneFruitPerWorldSimpleLogic())){
			((CommandSourceStack)(var0.getSource())).sendFailure(ModI18nCommands.CHECK_FRUIT_OFPW_ONLY);
			return 0;
		}else{
			ServerLevel var2 = ((CommandSourceStack)(var0.getSource())).getLevel();
			OneFruitWorldData var3 = OneFruitWorldData.get();
			AkumaNoMiItem var4 = var1;
			final AkumaNoMiItem var4_f1 = var4;
			Predicate<OneFruitEntry> var5 = (l0) -> {
			return l0.getKey().toString().equals(var4_f1.getRegistryKey().toString());
		};
			Optional var6 = var3.getOneFruitEntries().stream().filter(var5).findFirst();
			if (var6.isPresent()){
				StringBuilder var7 = new StringBuilder();
				List var8 = ((OneFruitEntry)(var6.get())).getHistory();
				int var9 = 0;
				LOOP_1:
				do{
					if ((var9) < (var8.size())){
						OneFruitEntry$HistoryEntry var10 = ((OneFruitEntry$HistoryEntry)(var8.get(var9)));
						OneFruitEntry$HistoryEntry var11 = ((OneFruitEntry$HistoryEntry)(var8.get(Mth.clamp((var9) - (1),0,var8.size()))));
						String var12 = "";
						String var13 = getOwnerName(var10,(Level)(var2));
						if ((var10) == (var11)){
							var12 = var10.getStatus().name();
						}else{
							String var14 = var11.getStatus().name();
							String var15 = var10.getStatus().name();
							String var16 = var14 + " → " + var15;
							var12 = var16;
						}
						String var14_1 = var10.getDate().toString();
						String var15_1 = var14_1 + ":\n";
						var7.append(var15_1);
						String var16_1 = var10.getStatusMessage();
						String var17 = " due to: " + var16_1;
						do{
							String var18 = var12;
							String var19 = var13;
							String var20 = (!(Strings.isNullOrEmpty(var10.getStatusMessage()))) ? (var17) : ("");
							String var21 = var18 + " by " + var19 + var20 + "\n\n";
							var7.append(var21);
							var9++;
							continue LOOP_1;
						} while (true);
					}else{
						break;
					}
				} while (true);
				try{
					String var10_2 = System.getProperty("user.dir");
					File var11_1 = new File((String)(var10_2),(String)("onefruit-exports"));
					if (!(var11_1.exists())){
						var11_1.mkdir();
					}
					String var12_1 = String.valueOf((Object)(var1));
					String var13_1 = var12_1 + ".txt";
					File var14_2 = new File((File)(var11_1),(String)(var13_1));
					if (var14_2.exists()){
						var14_2.delete();
					}
					var14_2.createNewFile();
					PrintWriter var15_2 = new PrintWriter((Writer)(new FileWriter((File)(var14_2),true)));
					try{
						var15_2.append((CharSequence)(var7.toString()));
					}catch(Throwable var16_2){
						try{
							var15_2.close();
						}catch(Throwable var17_1){
							var16_2.addSuppressed(var17_1);
						}
						throw var16_2;
					}
					var15_2.close();
					CommandSourceStack var16_3 = ((CommandSourceStack)(var0.getSource()));
					var17_2 = var1;
					final AkumaNoMiItem var17_2_f1 = var17_2;
					Supplier<Component> var18_1 = () -> {
			String lv12_1 = String.valueOf((Object)(var17_2_f1));
			String lv12_2 = "Exported file as " + lv12_1 + ".txt";
			return Component.literal(lv12_2);
		};
					var16_3.sendSuccess(var18_1,false);
				}catch(Exception var10_3){
					var10_3.printStackTrace();
					CommandSourceStack var10_1 = ((CommandSourceStack)(var0.getSource()));
					Supplier<Component> var11_2 = () -> {
			return ModI18nCommands.CHECK_FRUIT_ERROR_EXPORTING;
		};
					var10_1.sendSuccess(var11_2,false);
				}
			}
			return 1;
		}
	}
	private static int fruitHistory(CommandContext<CommandSourceStack> var0, AkumaNoMiItem var1, int var2) {
		AkumaNoMiItem var10_1 = null;
		MutableComponent var16_1 = null;
		if (!(ServerConfig.hasOneFruitPerWorldSimpleLogic())){
			((CommandSourceStack)(var0.getSource())).sendFailure(ModI18nCommands.CHECK_FRUIT_OFPW_ONLY);
			return 0;
		}else{
			ServerLevel var3 = ((CommandSourceStack)(var0.getSource())).getLevel();
			OneFruitWorldData var4 = OneFruitWorldData.get();
			AkumaNoMiItem var5 = var1;
			final AkumaNoMiItem var5_f1 = var5;
			Predicate<OneFruitEntry> var6 = (l0) -> {
			return l0.getKey().toString().equals(var5_f1.getRegistryKey().toString());
		};
			Optional var7 = var4.getOneFruitEntries().stream().filter(var6).findFirst();
			MutableComponent var8 = Component.literal("");
			if (var7.isPresent()){
				int var9 = 100;
				List var10 = ((OneFruitEntry)(var7.get())).getHistory();
				if ((var2) >= (0)){
					var10 = var10.stream().skip((long)((var9) * (var2))).limit((long)(var9)).toList();
				}
				var8.append((Component)(Component.literal("===============================================\n")));
				String var11 = String.valueOf((Object)(((OneFruitEntry)(var7.get())).getKey()));
				int var12 = (var2) + (1);
				String var13 = "§l§6" + var11 + "'s History - Page " + var12 + ":§r \n";
				var8.append((Component)(Component.literal(var13)));
				int var14 = 0;
				LOOP_1:
				do{
					if ((var14) < (var10.size())){
						OneFruitEntry$HistoryEntry var15 = ((OneFruitEntry$HistoryEntry)(var10.get(var14)));
						OneFruitEntry$HistoryEntry var16 = ((OneFruitEntry$HistoryEntry)(var10.get(Mth.clamp((var14) - (1),0,var10.size()))));
						String var17 = "";
						String var18 = getOwnerName(var15,(Level)(var3));
						if ((var15) == (var16)){
							var17 = getStatusColor(var15);
						}else{
							String var19 = getStatusColor(var16);
							String var20 = getStatusColor(var15);
							String var21 = var19 + " → " + var20;
							var17 = var21;
						}
						String var19_1 = var15.getDate().toString();
						String var20_1 = var19_1 + ":\n";
						var8.append((Component)(Component.literal(var20_1)));
						String var21_1 = var15.getStatusMessage();
						String var22 = " due to: " + var21_1;
						do{
							String var23 = var17;
							String var24 = var18;
							String var25 = (!(Strings.isNullOrEmpty(var15.getStatusMessage()))) ? (var22) : ("");
							String var26 = var23 + " by " + var24 + var25;
							var8.append((Component)(Component.literal(var26)));
							var8.append(getClickableFruitLocation(var15));
							var8.append("\n");
							var14++;
							continue LOOP_1;
						} while (true);
					}else{
						break;
					}
				} while (true);
				var8.append((Component)(Component.literal("===============================================")));
				CommandSourceStack var15_1 = ((CommandSourceStack)(var0.getSource()));
				var16_1 = var8;
				final MutableComponent var16_1_f1 = var16_1;
				Supplier<Component> var17_1 = () -> {
			return var16_1_f1;
		};
				var15_1.sendSuccess(var17_1,false);
			}else{
				CommandSourceStack var9_1 = ((CommandSourceStack)(var0.getSource()));
				var10_1 = var1;
				final AkumaNoMiItem var10_1_f2 = var10_1;
				Supplier<Component> var11_1 = () -> {
			String lv15_1 = var10_1_f2.getRegistryKey().toString();
			String lv15_2 = "No history found for " + lv15_1;
			return Component.literal(lv15_2);
		};
				var9_1.sendSuccess(var11_1,false);
			}
			return 1;
		}
	}
	private static int checkFruitsInWorld(CommandContext<CommandSourceStack> var0) {
		if (!(ServerConfig.hasOneFruitPerWorldSimpleLogic())){
			((CommandSourceStack)(var0.getSource())).sendFailure(ModI18nCommands.CHECK_FRUIT_OFPW_ONLY);
			return 0;
		}else{
			ServerLevel var1 = ((CommandSourceStack)(var0.getSource())).getLevel();
			OneFruitWorldData var2 = OneFruitWorldData.get();
			MutableComponent var3 = Component.literal("");
			List var4 = var2.getOneFruitEntries().stream().map((Function)(((Function<OneFruitEntry, ResourceLocation>)(OneFruitEntry::getKey)))).toList();
			List var5 = var2.getOneFruitEntries().stream().sorted(COMPARE_ENTRIES_ALPHABETICALLY).toList();
			var3.append("===============================================\n");
			var3.append("§l§6Fruit Name | Player Name | Status §r\n");
			Iterator var6 = var5.iterator();
			do{
				if (var6.hasNext()){
					OneFruitEntry var7 = ((OneFruitEntry)(var6.next()));
					Function<Item, String> var8 = (l0) -> {
			return l0.getDescription().getString();
		};
					String var9 = ((String)(((java.util.Optional)(var7.getItemFromKey())).map((Function)(var8)).orElse(var7.getKey().toString())));
					String var10 = getOwnerName(var7,(Level)(var1));
					String var11 = getStatusColor(var7);
					String var12 = var9;
					String var13 = var10;
					String var14 = var11;
					String var15 = var12 + " | " + var13 + " | " + var14;
					var3.append(var15);
					var3.append(getClickableFruitLocation(var7));
					var3.append("\n");
					continue;
				}else{
					break;
				}
			} while (true);
			var3.append("\n");
			Iterator var7_1 = ModValues.DEVIL_FRUITS.stream().sorted(COMPARE_FRUITS_ALPHABETICALLY).toList().iterator();
			do{
				if (var7_1.hasNext()){
					AkumaNoMiItem var8_1 = ((AkumaNoMiItem)(var7_1.next()));
					if (!(var4.contains(var8_1.getRegistryKey()))){
						String var9_1 = var8_1.getDescription().getString();
						String var10_1 = var9_1 + " | §5NEVER_FOUND§r\n";
						var3.append(var10_1);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			var3.append("===============================================");
			CommandSourceStack var8_2 = ((CommandSourceStack)(var0.getSource()));
			MutableComponent var9_2 = var3;
			final MutableComponent var9_2_f1 = var9_2;
			Supplier<Component> var10_2 = () -> {
			return var9_2_f1;
		};
			var8_2.sendSuccess(var10_2,false);
			return 1;
		}
	}}