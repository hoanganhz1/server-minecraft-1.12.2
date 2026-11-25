package xyz.pixelatedw.mineminenomi.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class DorikiCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("doriki"))).requires((Predicate)(Requires.hasPermission(ModPermissions.DORIKI_COMMAND)))));
		int var1 = -(ServerConfig.getDorikiLimit());
		int var2 = ServerConfig.getDorikiLimit();
		Command var3 = (l0) -> {
			return alterDoriki(l0,IntegerArgumentType.getInteger(l0,"amount"),getDefaultCollection(l0));
		};
		RequiredArgumentBuilder var4 = ((RequiredArgumentBuilder)(Commands.argument("amount",(ArgumentType)(IntegerArgumentType.integer(var1,var2))).executes(var3)));
		Command var5 = (l0) -> {
			return alterDoriki(l0,IntegerArgumentType.getInteger(l0,"amount"),EntityArgument.getPlayers(l0,"targets"));
		};
		var0.then(var4.then(Commands.argument("targets",(ArgumentType)(EntityArgument.entities())).executes(var5)));
		return var0;
	}
	private static Collection<ServerPlayer> getDefaultCollection(CommandContext<CommandSourceStack> var0) throws CommandSyntaxException {
		ServerPlayer[] var1 = new ServerPlayer[]{((CommandSourceStack)(var0.getSource())).getPlayerOrException()};
		return Lists.newArrayList(var1);
	}
	private static int alterDoriki(CommandContext<CommandSourceStack> var0, int var1, Collection<? extends Entity> var2) throws CommandSyntaxException {
		LivingEntity var10 = null;
		Iterator<? extends Entity> var3 = var2.iterator();
		do{
			if (var3.hasNext()){
				Entity var4 = ((Entity)(var3.next()));
				if (var4 instanceof LivingEntity){
					LivingEntity var5 = ((LivingEntity)(var4));
					int var6 = var1;
					final int var6_f1 = var6;
					Function<EntityStatsData, Boolean> var7 = (l0) -> {
			return Boolean.valueOf(l0.alterDoriki((double)(var6_f1),StatChangeSource.COMMAND));
		};
					if (((Boolean)(((java.util.Optional)(EntityStatsData.get(var5))).map((Function)(var7)).orElse(Boolean.valueOf(false)))).booleanValue()){
						if (var5 instanceof ServerPlayer){
							ServerPlayer var8 = ((ServerPlayer)(var5));
							ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var8)),(Player)(var8));
						}
						CommandSourceStack var8_1 = ((CommandSourceStack)(var0.getSource()));
						int var9 = var1;
						var10 = var5;
						final int var9_f1 = var9;
						final LivingEntity var10_f1 = var10;
						Supplier<Component> var11 = () -> {
			String lv4_2 = String.valueOf((Object)(ChatFormatting.GREEN));
			String lv4_3 = ((var9_f1) > (0)) ? ("+") : ("");
			int lv4_4 = var9_f1;
			String lv4_5 = var10_f1.getDisplayName().getString();
			String lv4_6 = lv4_2 + lv4_3 + lv4_4 + " doriki for " + lv4_5;
			return Component.literal(lv4_6);
		};
						var8_1.sendSuccess(var11,true);
						continue;
					}else{
						continue;
					}
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