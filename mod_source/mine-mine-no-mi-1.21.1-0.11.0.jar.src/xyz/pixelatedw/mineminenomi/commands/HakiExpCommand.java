package xyz.pixelatedw.mineminenomi.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
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
import net.neoforged.neoforge.server.command.EnumArgument;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;

public class HakiExpCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("hakiexp"))).requires((Predicate)(Requires.hasPermission(ModPermissions.HAKIEXP_COMMAND)))));
		int var1 = ServerConfig.getHakiExpLimit();
		Command var2 = (l0) -> {
			return alterHakiExp(l0,((HakiType)(l0.getArgument("type",HakiType.class))),FloatArgumentType.getFloat(l0,"amount"),getDefaultCollection(l0));
		};
		RequiredArgumentBuilder var3 = ((RequiredArgumentBuilder)(Commands.argument("amount",(ArgumentType)(FloatArgumentType.floatArg((float)(-(var1)),(float)(var1)))).executes(var2)));
		Command var4 = (l0) -> {
			return alterHakiExp(l0,((HakiType)(l0.getArgument("type",HakiType.class))),FloatArgumentType.getFloat(l0,"amount"),EntityArgument.getPlayers(l0,"targets"));
		};
		var0.then(Commands.argument("type",(ArgumentType)(EnumArgument.enumArgument(HakiType.class))).then(var3.then(Commands.argument("targets",(ArgumentType)(EntityArgument.players())).executes(var4))));
		return var0;
	}
	private static Collection<ServerPlayer> getDefaultCollection(CommandContext<CommandSourceStack> var0) throws CommandSyntaxException {
		ServerPlayer[] var1 = new ServerPlayer[]{((CommandSourceStack)(var0.getSource())).getPlayerOrException()};
		return Lists.newArrayList(var1);
	}
	private static int alterHakiExp(CommandContext<CommandSourceStack> var0, HakiType var1, float var2, Collection<ServerPlayer> var3) throws CommandSyntaxException {
		ServerPlayer var10 = null;
		Iterator<ServerPlayer> var4 = var3.iterator();
		do{
			if (var4.hasNext()){
				ServerPlayer var5 = ((ServerPlayer)(var4.next()));
				HakiData var6 = ((HakiData)(((java.util.Optional)(HakiData.get((LivingEntity)(var5)))).orElse((Object)(null))));
				if ((var6) == (null)){
					return -1;
				}else{
					boolean var7 = false;
					if ((var1) == (HakiType.BUSOSHOKU)){
						var7 = var6.alterBusoshokuHakiExp(var2,StatChangeSource.COMMAND);
					}else{
						if ((var1) == (HakiType.KENBUNSHOKU)){
							var7 = var6.alterKenbunshokuHakiExp(var2,StatChangeSource.COMMAND);
						}else{
							if ((var1) == (HakiType.HAOSHOKU)){
								var7 = (var7) | (var6.alterBusoshokuHakiExp(var2,StatChangeSource.COMMAND));
								var7 = (var7) | (var6.alterKenbunshokuHakiExp(var2,StatChangeSource.COMMAND));
							}
						}
					}
					if (var7){
						CommandSourceStack var8 = ((CommandSourceStack)(var0.getSource()));
						float var9 = var2;
						var10 = var5;
						final float var9_f1 = var9;
						final ServerPlayer var10_f1 = var10;
						Supplier<Component> var11 = () -> {
			String lv3_2 = String.valueOf((Object)(ChatFormatting.GREEN));
			String lv3_3 = ((var9_f1) > (0F)) ? ("+") : ("");
			float lv3_4 = var9_f1;
			String lv3_5 = var10_f1.getDisplayName().getString();
			String lv3_6 = lv3_2 + lv3_3 + lv3_4 + " haki exp for " + lv3_5;
			return Component.literal(lv3_6);
		};
						var8.sendSuccess(var11,true);
						continue;
					}else{
						continue;
					}
				}
			}else{
				break;
			}
		} while (true);
		return 1;
	}
}