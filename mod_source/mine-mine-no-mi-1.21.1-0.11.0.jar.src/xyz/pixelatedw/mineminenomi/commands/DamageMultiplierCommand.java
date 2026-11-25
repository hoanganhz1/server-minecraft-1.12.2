package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
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
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nCommands;

public class DamageMultiplierCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		Predicate<CommandSourceStack> var0 = (l0) -> {
			return l0.hasPermission(2);
		};
		LiteralArgumentBuilder var1 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("damagem"))).requires((Predicate)(var0))));
		Command var2 = (l0) -> {
			return applyMultiplier(l0,DoubleArgumentType.getDouble(l0,"multiplier"),EntityArgument.getPlayer(l0,"target"));
		};
		var1.then(Commands.argument("multiplier",(ArgumentType)(DoubleArgumentType.doubleArg(0D,10D))).then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).executes(var2)));
		return var1;
	}
	private static int applyMultiplier(CommandContext<CommandSourceStack> var0, double var1, ServerPlayer var2) {
		double var3 = var1;
		ServerPlayer var4 = var2;
		CommandContext<CommandSourceStack> var5 = var0;
		final double var3_f1 = var3;
		final ServerPlayer var4_f2 = var4;
		final CommandContext<CommandSourceStack> var5_f3 = var5;
		Consumer<EntityStatsData> var6 = (l0) -> {
			l0.setDamageMultiplier(var3_f1);
			Object[] lv4_4 = new Object[]{Double.valueOf(l0.getDamageMultiplier()),var4_f2.getDisplayName().getString()};
			MutableComponent lv4_5 = Component.translatable(ModI18nCommands.DAMAGE_MULTIPLIER_SET,lv4_4);
			CommandSourceStack lv4_6 = ((CommandSourceStack)(var5_f3.getSource()));
			MutableComponent lv4_7 = lv4_5;
			final MutableComponent lv4_7_f1 = lv4_7;
			Supplier<Component> lv4_8 = () -> {
				return lv4_7_f1;
			};
			lv4_6.sendSuccess(lv4_8,true);
		};
		((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).ifPresent((Consumer)(var6));
		return 1;
	}
}