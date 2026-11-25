package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.api.commands.QuestArgument;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;

public class QuestCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("quest"))).requires((Predicate)(Requires.hasPermission(ModPermissions.QUEST_COMMAND)))));
		Command var1 = (l0) -> {
			return resetQuestCooldown(l0,QuestArgument.getQuest(l0,"quest"),EntityArgument.getPlayer(l0,"target"));
		};
		RequiredArgumentBuilder var2 = ((RequiredArgumentBuilder)(Commands.argument("quest",(ArgumentType)(QuestArgument.quest())).then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).executes(var1))));
		Command var3 = (l0) -> {
			return resetQuestCooldown(l0,QuestArgument.getQuest(l0,"quest"),((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		LiteralArgumentBuilder var4 = ((LiteralArgumentBuilder)(var0.then(Commands.literal("reset_cooldown").then(var2.executes(var3)))));
		Command var5 = (l0) -> {
			return finishQuest(l0,QuestArgument.getQuest(l0,"quest"),EntityArgument.getPlayer(l0,"target"));
		};
		RequiredArgumentBuilder var6 = ((RequiredArgumentBuilder)(Commands.argument("quest",(ArgumentType)(QuestArgument.quest())).then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).executes(var5))));
		Command var7 = (l0) -> {
			return finishQuest(l0,QuestArgument.getQuest(l0,"quest"),((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		LiteralArgumentBuilder var8 = ((LiteralArgumentBuilder)(var4.then(Commands.literal("finish").then(var6.executes(var7)))));
		Command var9 = (l0) -> {
			return giveQuest(l0,QuestArgument.getQuest(l0,"quest"),EntityArgument.getPlayer(l0,"target"));
		};
		RequiredArgumentBuilder var10 = ((RequiredArgumentBuilder)(Commands.argument("quest",(ArgumentType)(QuestArgument.quest())).then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).executes(var9))));
		Command var11 = (l0) -> {
			return giveQuest(l0,QuestArgument.getQuest(l0,"quest"),((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		LiteralArgumentBuilder var12 = ((LiteralArgumentBuilder)(var8.then(Commands.literal("give").then(var10.executes(var11)))));
		Command var13 = (l0) -> {
			return unfinishQuest(l0,QuestArgument.getQuest(l0,"quest"),EntityArgument.getPlayer(l0,"target"));
		};
		RequiredArgumentBuilder var14 = ((RequiredArgumentBuilder)(Commands.argument("quest",(ArgumentType)(QuestArgument.quest())).then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).executes(var13))));
		Command var15 = (l0) -> {
			return unfinishQuest(l0,QuestArgument.getQuest(l0,"quest"),((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		LiteralArgumentBuilder var16 = ((LiteralArgumentBuilder)(var12.then(Commands.literal("unfinish").then(var14.executes(var15)))));
		Command var17 = (l0) -> {
			return removeQuest(l0,QuestArgument.getQuest(l0,"quest"),EntityArgument.getPlayer(l0,"target"));
		};
		RequiredArgumentBuilder var18 = ((RequiredArgumentBuilder)(Commands.argument("quest",(ArgumentType)(QuestArgument.quest())).then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).executes(var17))));
		Command var19 = (l0) -> {
			return removeQuest(l0,QuestArgument.getQuest(l0,"quest"),((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		var16.then(Commands.literal("remove").then(var18.executes(var19)));
		return var0;
	}
	private static int resetQuestCooldown(CommandContext<CommandSourceStack> var0, QuestId<? extends Quest> var1, ServerPlayer var2) {
		QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get((Player)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return -1;
		}else{
			var3.setQuestCooldown(var1,0D);
			ModNetwork.sendTo(new SSyncQuestDataPacket((Player)(var2)),(Player)(var2));
			return 1;
		}
	}
	private static int unfinishQuest(CommandContext<CommandSourceStack> var0, QuestId<? extends Quest> var1, ServerPlayer var2) {
		QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get((Player)(var2)))).orElse((Object)(null))));
		AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if (((var3) != (null)) && ((var4) != (null))){
			if (var3.hasFinishedQuest(var1)){
				var3.removeFinishedQuest(var1);
				ProgressionHandler.checkForStyleUnlocks((Player)(var2));
				ModNetwork.sendTo(new SSyncQuestDataPacket((Player)(var2)),(Player)(var2));
				ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var2)),(Player)(var2));
			}else{
				var2.displayClientMessage((Component)(Component.literal("You haven't finished this quest!")),false);
			}
			return 1;
		}else{
			return -1;
		}
	}
	private static int finishQuest(CommandContext<CommandSourceStack> var0, QuestId<? extends Quest> var1, ServerPlayer var2) {
		QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get((Player)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return -1;
		}else{
			if (var3.hasInProgressQuest(var1)){
				if (var3.getInProgressQuest(var1).tryComplete((Player)(var2),true)){
					ModNetwork.sendTo(new SSyncQuestDataPacket((Player)(var2)),(Player)(var2));
				}else{
					return 1;
				}
			}else{
				if (!(var3.hasInProgressQuest(var1))){
					var2.displayClientMessage((Component)(Component.literal("You don't have this quest!")),false);
				}else{
					return 1;
				}
			}
			return 1;
		}
	}
	private static int giveQuest(CommandContext<CommandSourceStack> var0, QuestId<? extends Quest> var1, ServerPlayer var2) {
		QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get((Player)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return -1;
		}else{
			if (!(var3.hasInProgressQuest(var1))){
				var3.addInProgressQuest(var1);
				ModNetwork.sendTo(new SSyncQuestDataPacket((Player)(var2)),(Player)(var2));
			}else{
				var2.displayClientMessage((Component)(Component.literal("You aleady have this quest!")),false);
			}
			return 1;
		}
	}
	private static int removeQuest(CommandContext<CommandSourceStack> var0, QuestId<? extends Quest> var1, ServerPlayer var2) {
		QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get((Player)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return -1;
		}else{
			if (var3.hasInProgressQuest(var1)){
				var3.removeInProgressQuest(var1);
				var3.removeFinishedQuest(var1);
				ModNetwork.sendTo(new SSyncQuestDataPacket((Player)(var2)),(Player)(var2));
			}else{
				var2.displayClientMessage((Component)(Component.literal("You don't have this quest!")),false);
			}
			return 1;
		}
	}
}