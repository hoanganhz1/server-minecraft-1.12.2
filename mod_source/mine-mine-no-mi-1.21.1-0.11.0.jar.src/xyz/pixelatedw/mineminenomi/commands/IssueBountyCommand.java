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
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.server.permission.PermissionAPI;
import net.neoforged.neoforge.server.permission.nodes.PermissionDynamicContext;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.events.entity.IssueBountyEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.IssueBountyEvent$Result;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nCommands;

public class IssueBountyCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = Commands.literal("issuebounty");
		Command var1 = (l0) -> {
			return issueBounty(l0,EntityArgument.getPlayer(l0,"target"),LongArgumentType.getLong(l0,"bounty"));
		};
		var0.then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).then(Commands.argument("bounty",(ArgumentType)(LongArgumentType.longArg())).executes(var1)));
		return var0;
	}
	private static int issueBounty(CommandContext<CommandSourceStack> var0, ServerPlayer var1, long var2) {
		ServerPlayer var7 = null;
		try{
			ServerPlayer var3 = ((CommandSourceStack)(var0.getSource())).getPlayerOrException();
			EntityStatsData var4 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var3)))).orElse((Object)(null))));
			EntityStatsData var5 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).orElse((Object)(null))));
			if (((var4) != (null)) && ((var5) != (null))){
				IssueBountyEvent var6 = ((IssueBountyEvent)(NeoForge.EVENT_BUS.post((Event)(new IssueBountyEvent((Player)(var1),var2,(Player)(var3))))));
				if ((!(((Boolean)(PermissionAPI.getPermission(var3,ModPermissions.ISSUE_BOUNTY_COMMAND,new PermissionDynamicContext[0]))).booleanValue())) && (!((var4.isMarine()) && ((((var6.getResult()) == (IssueBountyEvent$Result.DEFAULT)) && (var4.hasRank(MarineRank.CAPTAIN))) || ((var6.getResult()) == (IssueBountyEvent$Result.ALLOW)))))){
					var1.sendSystemMessage(ModI18nCommands.ISSUEBOUNTY_REQUIREMENTS);
				}else{
					var7 = var1;
					final ServerPlayer var7_f1 = var7;
					Function<Faction, Boolean> var8 = (l0) -> {
			return Boolean.valueOf(l0.canReceiveBounty((LivingEntity)(var7_f1)));
		};
					if (((Boolean)(((java.util.Optional)(var5.getFaction())).map((Function)(var8)).orElse(Boolean.valueOf(false)))).booleanValue()){
						if ((var2) <= (var5.getBounty())){
							var1.sendSystemMessage(ModI18nCommands.ISSUEBOUNTY_ONLY_UP);
							return 1;
						}else{
							long var9 = (var2) - (var5.getBounty());
							if ((var4.getBelly()) <= (var9)){
								var1.sendSystemMessage(ModI18nCommands.ISSUEBOUNTY_NOT_ENOUGH_BELLY);
								return 1;
							}else{
								var4.setBelly((var4.getBelly()) - (var9));
								var5.setBounty(var2);
							}
						}
					}else{
						var1.sendSystemMessage(ModI18nCommands.ISSUEBOUNTY_TARGET_REQUIREMENTS);
					}
				}
			}else{
				return -1;
			}
		}catch(CommandSyntaxException var3_1){
			var3_1.printStackTrace();
		}
		return 1;
	}
}