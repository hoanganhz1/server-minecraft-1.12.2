package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.commands.ChallengeArgument;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncChallengeDataPacket;

public class ChallengeCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("challenge"))).requires((Predicate)(Requires.hasPermission(ModPermissions.CHALLENGE_COMMAND)))));
		Command var1 = (l0) -> {
			return startChallenge(l0,ChallengeArgument.getChallenge(l0,"challenge"),((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		RequiredArgumentBuilder var2 = ((RequiredArgumentBuilder)(Commands.argument("challenge",(ArgumentType)(ChallengeArgument.challenge())).executes(var1)));
		Command var3 = (l0) -> {
			return startChallenge(l0,ChallengeArgument.getChallenge(l0,"challenge"),EntityArgument.getPlayer(l0,"target"));
		};
		LiteralArgumentBuilder var4 = ((LiteralArgumentBuilder)(var0.then(Commands.literal("start").then(var2.then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).executes(var3))))));
		Command var5 = (l0) -> {
			return finishChallenge(l0,ChallengeArgument.getChallenge(l0,"challenge"),((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		RequiredArgumentBuilder var6 = ((RequiredArgumentBuilder)(Commands.argument("challenge",(ArgumentType)(ChallengeArgument.challenge())).executes(var5)));
		Command var7 = (l0) -> {
			return finishChallenge(l0,ChallengeArgument.getChallenge(l0,"challenge"),EntityArgument.getPlayer(l0,"target"));
		};
		LiteralArgumentBuilder var8 = ((LiteralArgumentBuilder)(var4.then(Commands.literal("finish").then(var6.then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).executes(var7))))));
		Command var9 = (l0) -> {
			return giveChallenge(l0,ChallengeArgument.getChallenge(l0,"challenge"),((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		RequiredArgumentBuilder var10 = ((RequiredArgumentBuilder)(Commands.argument("challenge",(ArgumentType)(ChallengeArgument.challenge())).executes(var9)));
		Command var11 = (l0) -> {
			return giveChallenge(l0,ChallengeArgument.getChallenge(l0,"challenge"),EntityArgument.getPlayer(l0,"target"));
		};
		LiteralArgumentBuilder var12 = ((LiteralArgumentBuilder)(Commands.literal("give").then(var10.then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).executes(var11)))));
		Command var13 = (l0) -> {
			return giveAllChallenges(l0,EntityArgument.getPlayer(l0,"target"));
		};
		LiteralArgumentBuilder var14 = ((LiteralArgumentBuilder)(var8.then(var12.then(Commands.literal("ALL").then(Commands.argument("target",(ArgumentType)(EntityArgument.players())).executes(var13))))));
		Command var15 = (l0) -> {
			return unfinishChallenge(l0,ChallengeArgument.getChallenge(l0,"challenge"),((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		RequiredArgumentBuilder var16 = ((RequiredArgumentBuilder)(Commands.argument("challenge",(ArgumentType)(ChallengeArgument.challenge())).executes(var15)));
		Command var17 = (l0) -> {
			return unfinishChallenge(l0,ChallengeArgument.getChallenge(l0,"challenge"),EntityArgument.getPlayer(l0,"target"));
		};
		LiteralArgumentBuilder var18 = ((LiteralArgumentBuilder)(var14.then(Commands.literal("unfinish").then(var16.then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).executes(var17))))));
		Command var19 = (l0) -> {
			return removeChallenge(l0,ChallengeArgument.getChallenge(l0,"challenge"),((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		RequiredArgumentBuilder var20 = ((RequiredArgumentBuilder)(Commands.argument("challenge",(ArgumentType)(ChallengeArgument.challenge())).executes(var19)));
		Command var21 = (l0) -> {
			return removeChallenge(l0,ChallengeArgument.getChallenge(l0,"challenge"),EntityArgument.getPlayer(l0,"target"));
		};
		var18.then(Commands.literal("remove").then(var20.then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).executes(var21))));
		return var0;
	}
	private static int startChallenge(CommandContext<CommandSourceStack> var0, ChallengeCore<?> var1, ServerPlayer var2) {
		ChallengeCore<?> var3 = var1;
		ServerPlayer var4 = var2;
		final ChallengeCore<?> var3_f1 = var3;
		final ServerPlayer var4_f2 = var4;
		Consumer<ChallengeData> var5 = (l0) -> {
			if (!((l0.getChallenge(var3_f1)) != (null))){
				Object[] lv12_3 = new Object[]{var3_f1.getLocalizedTitle()};
				var4_f2.sendSystemMessage((Component)(Component.translatable(ModI18nChallenges.MESSAGE_NOT_UNLOCKED,lv12_3)));
			}else{
				ChallengesWorldData.get().startChallenge(var4_f2,(List)(null),var3_f1,false);
			}
		};
		((java.util.Optional)(ChallengeData.get((Player)(var2)))).ifPresent((Consumer)(var5));
		return 1;
	}
	private static int unfinishChallenge(CommandContext<CommandSourceStack> var0, ChallengeCore<?> var1, ServerPlayer var2) {
		ChallengeCore<?> var3 = var1;
		ServerPlayer var4 = var2;
		final ChallengeCore<?> var3_f1 = var3;
		final ServerPlayer var4_f2 = var4;
		Consumer<ChallengeData> var5 = (l0) -> {
			Challenge lv13_3 = l0.getChallenge(var3_f1);
			if ((lv13_3) != (null)){
				lv13_3.setComplete((Player)(var4_f2),false);
				lv13_3.resetBestTime();
				ModNetwork.sendTo(new SSyncChallengeDataPacket((Player)(var4_f2),(ChallengeData)(l0)),(Player)(var4_f2));
			}else{
				var4_f2.sendSystemMessage(ModI18nChallenges.MESSAGE_NOT_FINISHED);
			}
		};
		((java.util.Optional)(ChallengeData.get((Player)(var2)))).ifPresent((Consumer)(var5));
		return 1;
	}
	private static int finishChallenge(CommandContext<CommandSourceStack> var0, ChallengeCore<?> var1, ServerPlayer var2) {
		ChallengeCore<?> var3 = var1;
		ServerPlayer var4 = var2;
		final ChallengeCore<?> var3_f1 = var3;
		final ServerPlayer var4_f2 = var4;
		Consumer<ChallengeData> var5 = (l0) -> {
			Challenge lv14_3 = l0.getChallenge(var3_f1);
			if (!((lv14_3) != (null))){
				Object[] lv14_4 = new Object[]{var3_f1.getLocalizedTitle()};
				var4_f2.sendSystemMessage((Component)(Component.translatable(ModI18nChallenges.MESSAGE_NOT_UNLOCKED,lv14_4)));
			}else{
				lv14_3.setComplete((Player)(var4_f2),true);
				ModNetwork.sendTo(new SSyncChallengeDataPacket((Player)(var4_f2),(ChallengeData)(l0)),(Player)(var4_f2));
			}
		};
		((java.util.Optional)(ChallengeData.get((Player)(var2)))).ifPresent((Consumer)(var5));
		return 1;
	}
	private static int giveAllChallenges(CommandContext<CommandSourceStack> var0, ServerPlayer var1) {
		ServerPlayer var2 = var1;
		final ServerPlayer var2_f1 = var2;
		Consumer<ChallengeData> var3 = (l0) -> {
			Iterator lv15_2 = WyRegistry.CHALLENGES.iterator();
			do{
				if (lv15_2.hasNext()){
					ChallengeCore lv15_3 = ((ChallengeCore)(lv15_2.next()));
					if (!(l0.hasChallenge(lv15_3))){
						l0.addChallenge(lv15_3);
						ModNetwork.sendTo(new SSyncChallengeDataPacket((Player)(var2_f1),(ChallengeData)(l0)),(Player)(var2_f1));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		};
		((java.util.Optional)(ChallengeData.get((Player)(var1)))).ifPresent((Consumer)(var3));
		return 1;
	}
	private static int giveChallenge(CommandContext<CommandSourceStack> var0, ChallengeCore<?> var1, ServerPlayer var2) {
		ChallengeCore<?> var3 = var1;
		ServerPlayer var4 = var2;
		final ChallengeCore<?> var3_f1 = var3;
		final ServerPlayer var4_f2 = var4;
		Consumer<ChallengeData> var5 = (l0) -> {
			if (!(l0.hasChallenge(var3_f1))){
				l0.addChallenge(var3_f1);
				ModNetwork.sendTo(new SSyncChallengeDataPacket((Player)(var4_f2),(ChallengeData)(l0)),(Player)(var4_f2));
			}else{
				Object[] lv16_3 = new Object[]{var3_f1.getLocalizedTitle().getString()};
				var4_f2.sendSystemMessage((Component)(Component.translatable(ModI18nChallenges.MESSAGE_ALREADY_UNLOCKED,lv16_3)));
			}
		};
		((java.util.Optional)(ChallengeData.get((Player)(var2)))).ifPresent((Consumer)(var5));
		return 1;
	}
	private static int removeChallenge(CommandContext<CommandSourceStack> var0, ChallengeCore<?> var1, ServerPlayer var2) {
		ChallengeCore<?> var3 = var1;
		ServerPlayer var4 = var2;
		final ChallengeCore<?> var3_f1 = var3;
		final ServerPlayer var4_f2 = var4;
		Consumer<ChallengeData> var5 = (l0) -> {
			if (l0.hasChallenge(var3_f1)){
				l0.removeChallenge(var3_f1);
				ModNetwork.sendTo(new SSyncChallengeDataPacket((Player)(var4_f2),(ChallengeData)(l0)),(Player)(var4_f2));
			}else{
				Object[] lv17_3 = new Object[]{var3_f1.getLocalizedTitle()};
				var4_f2.sendSystemMessage((Component)(Component.translatable(ModI18nChallenges.MESSAGE_NOT_UNLOCKED,lv17_3)));
			}
		};
		((java.util.Optional)(ChallengeData.get((Player)(var2)))).ifPresent((Consumer)(var5));
		return 1;
	}
}