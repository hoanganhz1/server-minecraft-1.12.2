package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class GoRogueCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = Commands.literal("gorogue");
		Predicate<CommandSourceStack> var1 = (l0) -> {
			return l0.hasPermission(2);
		};
		RequiredArgumentBuilder var2 = ((RequiredArgumentBuilder)(((com.mojang.brigadier.builder.RequiredArgumentBuilder)(Commands.argument("target",(ArgumentType)(EntityArgument.player())))).requires((Predicate)(var1))));
		Command var3 = (l0) -> {
			return goRogue(l0,EntityArgument.getPlayer(l0,"target"));
		};
		LiteralArgumentBuilder var4 = ((LiteralArgumentBuilder)(var0.then(var2.executes(var3))));
		Predicate<CommandSourceStack> var5 = (l0) -> {
			return l0.hasPermission(0);
		};
		LiteralArgumentBuilder var6 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(var4)).requires((Predicate)(var5))));
		Command var7 = (l0) -> {
			return goRogue(l0,((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		var6.executes(var7);
		return var0;
	}
	private static int goRogue(CommandContext<CommandSourceStack> var0, ServerPlayer var1) {
		ServerPlayer var2 = var1;
		CommandContext<CommandSourceStack> var3 = var0;
		final ServerPlayer var2_f1 = var2;
		final CommandContext<CommandSourceStack> var3_f2 = var3;
		Consumer<EntityStatsData> var4 = (l0) -> {
			boolean lv6_3 = l0.isRogue();
			l0.setRogue(!(lv6_3));
			String lv6_4 = (lv6_3) ? ("disabled") : ("enabled");
			String lv6_5 = var2_f1.getDisplayName().getString();
			String lv6_6 = "§2Rogue mode " + lv6_4 + " for " + lv6_5 + "§r";
			MutableComponent lv6_7 = Component.literal(lv6_6);
			var2_f1.displayClientMessage((Component)(lv6_7),false);
			if (!(((CommandSourceStack)(var3_f2.getSource())).getEntity().equals(var2_f1))){
				CommandSourceStack lv6_8 = ((CommandSourceStack)(var3_f2.getSource()));
				MutableComponent lv6_9 = lv6_7;
				final MutableComponent lv6_9_f1 = lv6_9;
				Supplier<Component> lv6_10 = () -> {
				return lv6_9_f1;
			};
				lv6_8.sendSuccess(lv6_10,false);
			}
		};
		((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).ifPresent((Consumer)(var4));
		return 1;
	}
}