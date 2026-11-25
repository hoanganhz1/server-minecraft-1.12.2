package xyz.pixelatedw.mineminenomi.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class ExtolCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("extol"))).requires((Predicate)(Requires.hasPermission(ModPermissions.EXTOL_COMMAND)))));
		Command var1 = (l0) -> {
			return alterExtol(l0,LongArgumentType.getLong(l0,"amount"),getDefaultCollection(l0));
		};
		RequiredArgumentBuilder var2 = ((RequiredArgumentBuilder)(Commands.argument("amount",(ArgumentType)(LongArgumentType.longArg(-999999999L,999999999L))).executes(var1)));
		Command var3 = (l0) -> {
			return alterExtol(l0,LongArgumentType.getLong(l0,"amount"),EntityArgument.getPlayers(l0,"targets"));
		};
		var0.then(var2.then(Commands.argument("targets",(ArgumentType)(EntityArgument.players())).executes(var3)));
		return var0;
	}
	private static Collection<ServerPlayer> getDefaultCollection(CommandContext<CommandSourceStack> var0) throws CommandSyntaxException {
		ServerPlayer[] var1 = new ServerPlayer[]{((CommandSourceStack)(var0.getSource())).getPlayerOrException()};
		return Lists.newArrayList(var1);
	}
	private static int alterExtol(CommandContext<CommandSourceStack> var0, long var1, Collection<ServerPlayer> var2) throws CommandSyntaxException {
		ServerPlayer var9 = null;
		Iterator<ServerPlayer> var3 = var2.iterator();
		do{
			if (var3.hasNext()){
				ServerPlayer var4 = ((ServerPlayer)(var3.next()));
				long var5 = var1;
				final long var5_f1 = var5;
				Function<EntityStatsData, Boolean> var6 = (l0) -> {
			return Boolean.valueOf(l0.alterExtol(var5_f1,StatChangeSource.COMMAND));
		};
				if (((Boolean)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var4)))).map((Function)(var6)).orElse(Boolean.valueOf(false)))).booleanValue()){
					ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var4)),(Player)(var4));
					CommandSourceStack var7 = ((CommandSourceStack)(var0.getSource()));
					long var8 = var1;
					var9 = var4;
					final long var8_f1 = var8;
					final ServerPlayer var9_f1 = var9;
					Supplier<Component> var10 = () -> {
			String lv4_2 = String.valueOf((Object)(ChatFormatting.GREEN));
			String lv4_3 = ((var8_f1) > (0L)) ? ("+") : ("");
			long lv4_4 = var8_f1;
			String lv4_5 = var9_f1.getDisplayName().getString();
			String lv4_6 = lv4_2 + lv4_3 + lv4_4 + " extol for " + lv4_5;
			return Component.literal(lv4_6);
		};
					var7.sendSuccess(var10,true);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return 1;
	}
}