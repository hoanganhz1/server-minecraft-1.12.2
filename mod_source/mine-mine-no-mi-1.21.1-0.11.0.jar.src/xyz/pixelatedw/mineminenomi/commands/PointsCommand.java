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
import net.neoforged.neoforge.server.command.EnumArgument;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class PointsCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("points"))).requires((Predicate)(Requires.hasPermission(ModPermissions.POINTS_COMMAND)))));
		int var1 = ServerConfig.getTrainingPointLimit();
		Command var2 = (l0) -> {
			return alterTrainingPoints(l0,((TrainingPointType)(l0.getArgument("type",TrainingPointType.class))),IntegerArgumentType.getInteger(l0,"amount"),getDefaultCollection(l0));
		};
		RequiredArgumentBuilder var3 = ((RequiredArgumentBuilder)(Commands.argument("amount",(ArgumentType)(IntegerArgumentType.integer(-(var1),var1))).executes(var2)));
		Command var4 = (l0) -> {
			return alterTrainingPoints(l0,((TrainingPointType)(l0.getArgument("type",TrainingPointType.class))),IntegerArgumentType.getInteger(l0,"amount"),EntityArgument.getPlayers(l0,"targets"));
		};
		var0.then(Commands.argument("type",(ArgumentType)(EnumArgument.enumArgument(TrainingPointType.class))).then(var3.then(Commands.argument("targets",(ArgumentType)(EntityArgument.players())).executes(var4))));
		return var0;
	}
	private static Collection<ServerPlayer> getDefaultCollection(CommandContext<CommandSourceStack> var0) throws CommandSyntaxException {
		ServerPlayer[] var1 = new ServerPlayer[]{((CommandSourceStack)(var0.getSource())).getPlayerOrException()};
		return Lists.newArrayList(var1);
	}
	private static int alterTrainingPoints(CommandContext<CommandSourceStack> var0, TrainingPointType var1, int var2, Collection<ServerPlayer> var3) throws CommandSyntaxException {
		TrainingPointType var10 = null;
		ServerPlayer var9 = null;
		Iterator<ServerPlayer> var4 = var3.iterator();
		do{
			if (var4.hasNext()){
				ServerPlayer var5 = ((ServerPlayer)(var4.next()));
				EntityStatsData var6 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var5)))).orElse((Object)(null))));
				if ((var6) == (null)){
					continue;
				}else{
					var6.alterTrainingPoints(var1,var2);
					ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var5)),(Player)(var5));
					CommandSourceStack var7 = ((CommandSourceStack)(var0.getSource()));
					int var8 = var2;
					var9 = var5;
					var10 = var1;
					final int var8_f1 = var8;
					final ServerPlayer var9_f1 = var9;
					final TrainingPointType var10_f2 = var10;
					Supplier<Component> var11 = () -> {
			String lv3_3 = String.valueOf((Object)(ChatFormatting.GREEN));
			String lv3_4 = ((var8_f1) > (0)) ? ("+") : ("");
			int lv3_5 = var8_f1;
			String lv3_6 = var9_f1.getDisplayName().getString();
			String lv3_7 = var10_f2.name();
			String lv3_8 = lv3_3 + lv3_4 + lv3_5 + " training points for " + lv3_6 + " in " + lv3_7;
			return Component.literal(lv3_8);
		};
					var7.sendSuccess(var11,true);
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return 1;
	}
}