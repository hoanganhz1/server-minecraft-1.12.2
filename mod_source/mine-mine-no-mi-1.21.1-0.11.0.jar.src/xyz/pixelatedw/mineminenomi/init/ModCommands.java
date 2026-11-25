package xyz.pixelatedw.mineminenomi.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.HashSet;
import java.util.Iterator;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import xyz.pixelatedw.mineminenomi.commands.AbilityCommand;
import xyz.pixelatedw.mineminenomi.commands.AbilityProtectionCommand;
import xyz.pixelatedw.mineminenomi.commands.BellyCommand;
import xyz.pixelatedw.mineminenomi.commands.BountyCommand;
import xyz.pixelatedw.mineminenomi.commands.ChallengeCommand;
import xyz.pixelatedw.mineminenomi.commands.ChangeCharacterCommand;
import xyz.pixelatedw.mineminenomi.commands.CheckFruitsCommand;
import xyz.pixelatedw.mineminenomi.commands.CheckPlayerCommand;
import xyz.pixelatedw.mineminenomi.commands.DamageMultiplierCommand;
import xyz.pixelatedw.mineminenomi.commands.DorikiCommand;
import xyz.pixelatedw.mineminenomi.commands.EventsCommand;
import xyz.pixelatedw.mineminenomi.commands.ExtolCommand;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.commands.GoRogueCommand;
import xyz.pixelatedw.mineminenomi.commands.HakiExpCommand;
import xyz.pixelatedw.mineminenomi.commands.IssueBountyCommand;
import xyz.pixelatedw.mineminenomi.commands.LoyaltyCommand;
import xyz.pixelatedw.mineminenomi.commands.PointsCommand;
import xyz.pixelatedw.mineminenomi.commands.PouchCommand;
import xyz.pixelatedw.mineminenomi.commands.QuestCommand;
import xyz.pixelatedw.mineminenomi.commands.RemoveDFCommand;
import xyz.pixelatedw.mineminenomi.config.SystemConfig;
import xyz.pixelatedw.mineminenomi.handlers.entity.PatreonHandler;

@EventBusSubscriber(modid="mineminenomi")
public class ModCommands {
	@SubscribeEvent()
	public static void registerCommands(RegisterCommandsEvent var0) {
		CommandDispatcher var1 = var0.getDispatcher();
		boolean var2 = ((Boolean)(SystemConfig.MASTER_COMMAND.get())).booleanValue();
		HashSet var3 = new HashSet();
		var3.add(AbilityProtectionCommand.create());
		var3.add(AbilityCommand.create());
		var3.add(DorikiCommand.create());
		var3.add(BountyCommand.create());
		var3.add(BellyCommand.create());
		var3.add(ExtolCommand.create());
		var3.add(IssueBountyCommand.create());
		var3.add(RemoveDFCommand.create());
		var3.add(HakiExpCommand.create());
		var3.add(PouchCommand.create());
		var3.add(CheckPlayerCommand.create());
		var3.add(LoyaltyCommand.create());
		var3.add(GoRogueCommand.create());
		var3.add(ChallengeCommand.create());
		var3.add(DamageMultiplierCommand.create());
		var3.add(CheckFruitsCommand.create());
		var3.add(QuestCommand.create());
		var3.add(ChangeCharacterCommand.create());
		var3.add(EventsCommand.create());
		var3.add(PointsCommand.create());
		int var4 = (!(PatreonHandler.isReleaseBuild())) ? (1) : (0);
		if (((Boolean)(SystemConfig.ENABLE_DEBUG_COMMANDS.get())).booleanValue()){
			var4 = 1;
		}
		if ((var4) != (0)){
			var3.add(FGCommand.create(var0.getBuildContext()));
		}
		Iterator var5 = var3.iterator();
		do{
			if (var5.hasNext()){
				LiteralArgumentBuilder var6 = ((LiteralArgumentBuilder)(var5.next()));
				if (var2){
					var6 = ((LiteralArgumentBuilder)(Commands.literal("mmnm").then((ArgumentBuilder)(var6))));
				}
				var1.register(var6);
				continue;
			}else{
				break;
			}
		} while (true);
	}
}