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
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.commands.FactionArgument;
import xyz.pixelatedw.mineminenomi.api.commands.FightingStyleArgument;
import xyz.pixelatedw.mineminenomi.api.commands.RaceArgument;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.triggers.SelectFactionTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SelectRaceTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SelectStyleTrigger;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class ChangeCharacterCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("change_character"))).requires((Predicate)(Requires.hasPermission(ModPermissions.CHANGE_CHARACTER_COMMAND)))));
		Command var1 = (l0) -> {
			return changeFaction(l0,EntityArgument.getPlayer(l0,"target"),FactionArgument.getFaction(l0,"value"));
		};
		LiteralArgumentBuilder var2 = ((LiteralArgumentBuilder)(var0.then(Commands.literal("faction").then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).then(Commands.argument("value",(ArgumentType)(FactionArgument.faction())).executes(var1))))));
		Command var3 = (l0) -> {
			return changeRace(l0,EntityArgument.getPlayer(l0,"target"),RaceArgument.getRace(l0,"value"));
		};
		LiteralArgumentBuilder var4 = ((LiteralArgumentBuilder)(var2.then(Commands.literal("race").then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).then(Commands.argument("value",(ArgumentType)(RaceArgument.race())).executes(var3))))));
		Command var5 = (l0) -> {
			return changeStyle(l0,EntityArgument.getPlayer(l0,"target"),FightingStyleArgument.getFightingStyle(l0,"value"));
		};
		var4.then(Commands.literal("style").then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).then(Commands.argument("value",(ArgumentType)(FightingStyleArgument.fightingStyle())).executes(var5))));
		return var0;
	}
	private static int changeFaction(CommandContext<CommandSourceStack> var0, ServerPlayer var1, Faction var2) throws CommandSyntaxException {
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return -1;
		}else{
			var3.setFaction(var2);
			((SelectFactionTrigger)(ModAdvancements.SELECT_FACTION.get())).trigger(var1,var2.getRegistryName());
			ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var1)),(Player)(var1));
			CommandSourceStack var4 = ((CommandSourceStack)(var0.getSource()));
			ServerPlayer var5 = var1;
			Faction var6 = var2;
			final ServerPlayer var5_f1 = var5;
			final Faction var6_f2 = var6;
			Supplier<Component> var7 = () -> {
			String lv4_2 = String.valueOf((Object)(ChatFormatting.GREEN));
			String lv4_3 = var5_f1.getDisplayName().getString();
			String lv4_4 = String.valueOf((Object)(var6_f2.getRegistryName()));
			String lv4_5 = lv4_2 + "Changed " + lv4_3 + "'s faction to " + lv4_4;
			return Component.literal(lv4_5);
		};
			var4.sendSuccess(var7,true);
			return 1;
		}
	}
	private static int changeRace(CommandContext<CommandSourceStack> var0, ServerPlayer var1, Race var2) throws CommandSyntaxException {
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return -1;
		}else{
			if (var2.isSubRace()){
				Race var4 = var2;
				final Race var4_f1 = var4;
				Predicate<Race> var5 = (l0) -> {
			Iterator lv5_2 = l0.getSubRaces().iterator();
			do{
				if (lv5_2.hasNext()){
					if (((java.lang.Object)(((Race)(((Supplier)(lv5_2.next())).get())))).equals((Object)(var4_f1))){
						return true;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return false;
		};
				Optional var6 = WyRegistry.RACES.stream().filter(var5).findFirst();
				if (var6.isPresent()){
					var3.setRace(((Race)(var6.get())));
					var3.setSubRace(var2);
				}
			}else{
				var3.setRace(var2);
				var3.setSubRace((Race)(null));
				if (var2.hasSubRaces()){
					int var4_1 = var1.getRandom().nextInt(var2.getSubRaces().size());
					var3.setSubRace(((Race)(((Supplier)(var2.getSubRaces().get(var4_1))).get())));
				}
			}
			((SelectRaceTrigger)(ModAdvancements.SELECT_RACE.get())).trigger(var1,var2.getRegistryName());
			ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var1)),(Player)(var1));
			ProgressionHandler.checkAllForNewUnlocks((Player)(var1),true);
			CommandSourceStack var4_2 = ((CommandSourceStack)(var0.getSource()));
			ServerPlayer var5_1 = var1;
			Race var6_1 = var2;
			final ServerPlayer var5_1_f1 = var5_1;
			final Race var6_1_f2 = var6_1;
			Supplier<Component> var7 = () -> {
			String lv6_2 = String.valueOf((Object)(ChatFormatting.GREEN));
			String lv6_3 = var5_1_f1.getDisplayName().getString();
			String lv6_4 = String.valueOf((Object)(var6_1_f2.getRegistryName()));
			String lv6_5 = lv6_2 + "Changed " + lv6_3 + "'s race to " + lv6_4;
			return Component.literal(lv6_5);
		};
			var4_2.sendSuccess(var7,true);
			return 1;
		}
	}
	private static int changeStyle(CommandContext<CommandSourceStack> var0, ServerPlayer var1, FightingStyle var2) throws CommandSyntaxException {
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return -1;
		}else{
			var3.setFightingStyle(var2);
			((SelectStyleTrigger)(ModAdvancements.SELECT_STYLE.get())).trigger(var1,var2.getRegistryName());
			ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var1)),(Player)(var1));
			CommandSourceStack var4 = ((CommandSourceStack)(var0.getSource()));
			ServerPlayer var5 = var1;
			FightingStyle var6 = var2;
			final ServerPlayer var5_f1 = var5;
			final FightingStyle var6_f2 = var6;
			Supplier<Component> var7 = () -> {
			String lv7_2 = String.valueOf((Object)(ChatFormatting.GREEN));
			String lv7_3 = var5_f1.getDisplayName().getString();
			String lv7_4 = String.valueOf((Object)(var6_f2.getRegistryName()));
			String lv7_5 = lv7_2 + "Changed " + lv7_3 + "'s fighting style to " + lv7_4;
			return Component.literal(lv7_5);
		};
			var4.sendSuccess(var7,true);
			return 1;
		}
	}
}