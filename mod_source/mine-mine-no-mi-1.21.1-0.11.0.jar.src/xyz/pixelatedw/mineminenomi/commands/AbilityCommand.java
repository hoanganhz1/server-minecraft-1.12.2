package xyz.pixelatedw.mineminenomi.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.commands.AbilityArgument;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityGroups;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;

public class AbilityCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		PermissionNode[] var0 = new PermissionNode[]{ModPermissions.ABILITY_COMMAND_RESET_COOLDOWN,ModPermissions.ABILITY_COMMAND};
		LiteralArgumentBuilder var1 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("ability"))).requires((Predicate)(Requires.hasEitherPermission(var0)))));
		LiteralArgumentBuilder var2 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("give"))).requires((Predicate)(Requires.hasPermission(ModPermissions.ABILITY_COMMAND)))));
		Command var3 = (l0) -> {
			return addAbility(l0,AbilityArgument.getAbility(l0,"ability"),EntityArgument.getPlayers(l0,"targets"));
		};
		RequiredArgumentBuilder var4 = ((RequiredArgumentBuilder)(Commands.argument("ability",(ArgumentType)(AbilityArgument.ability())).then(Commands.argument("targets",(ArgumentType)(EntityArgument.players())).executes(var3))));
		Command var5 = (l0) -> {
			return addAbility(l0,AbilityArgument.getAbility(l0,"ability"),getDefaultCollection(l0));
		};
		LiteralArgumentBuilder var6 = ((LiteralArgumentBuilder)(var1.then(var2.then(var4.executes(var5)))));
		LiteralArgumentBuilder var7 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("remove"))).requires((Predicate)(Requires.hasPermission(ModPermissions.ABILITY_COMMAND)))));
		Command var8 = (l0) -> {
			return removeAbility(l0,AbilityArgument.getAbility(l0,"ability"),EntityArgument.getPlayers(l0,"targets"));
		};
		RequiredArgumentBuilder var9 = ((RequiredArgumentBuilder)(Commands.argument("ability",(ArgumentType)(new AbilityArgument())).then(Commands.argument("targets",(ArgumentType)(EntityArgument.players())).executes(var8))));
		Command var10 = (l0) -> {
			return removeAbility(l0,AbilityArgument.getAbility(l0,"ability"),getDefaultCollection(l0));
		};
		LiteralArgumentBuilder var11 = ((LiteralArgumentBuilder)(var6.then(var7.then(var9.executes(var10)))));
		LiteralArgumentBuilder var12 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("unlock_group"))).requires((Predicate)(Requires.hasPermission(ModPermissions.ABILITY_COMMAND)))));
		Command var13 = (l0) -> {
			return abilityGroup(l0,ResourceLocationArgument.getId(l0,"group"),1,EntityArgument.getPlayers(l0,"targets"));
		};
		RequiredArgumentBuilder var14 = ((RequiredArgumentBuilder)(((com.mojang.brigadier.builder.RequiredArgumentBuilder)(Commands.argument("group",(ArgumentType)(ResourceLocationArgument.id())))).suggests((SuggestionProvider)(Suggests.allAbilityGroups())).then(Commands.argument("targets",(ArgumentType)(EntityArgument.players())).executes(var13))));
		Command var15 = (l0) -> {
			return abilityGroup(l0,ResourceLocationArgument.getId(l0,"group"),1,getDefaultCollection(l0));
		};
		LiteralArgumentBuilder var16 = ((LiteralArgumentBuilder)(var11.then(var12.then(var14.executes(var15)))));
		LiteralArgumentBuilder var17 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("lock_group"))).requires((Predicate)(Requires.hasPermission(ModPermissions.ABILITY_COMMAND)))));
		Command var18 = (l0) -> {
			return abilityGroup(l0,ResourceLocationArgument.getId(l0,"group"),-1,EntityArgument.getPlayers(l0,"targets"));
		};
		RequiredArgumentBuilder var19 = ((RequiredArgumentBuilder)(((com.mojang.brigadier.builder.RequiredArgumentBuilder)(Commands.argument("group",(ArgumentType)(ResourceLocationArgument.id())))).suggests((SuggestionProvider)(Suggests.allAbilityGroups())).then(Commands.argument("targets",(ArgumentType)(EntityArgument.players())).executes(var18))));
		Command var20 = (l0) -> {
			return abilityGroup(l0,ResourceLocationArgument.getId(l0,"group"),-1,getDefaultCollection(l0));
		};
		LiteralArgumentBuilder var21 = ((LiteralArgumentBuilder)(var16.then(var17.then(var19.executes(var20)))));
		Command var22 = (l0) -> {
			return resetCooldown(l0,EntityArgument.getPlayers(l0,"targets"));
		};
		LiteralArgumentBuilder var23 = ((LiteralArgumentBuilder)(Commands.literal("reset_cooldown").then(Commands.argument("targets",(ArgumentType)(EntityArgument.players())).executes(var22))));
		Command var24 = (l0) -> {
			return resetCooldown(l0,getDefaultCollection(l0));
		};
		var21.then(var23.executes(var24));
		Predicate<CommandSourceStack> var25 = (l0) -> {
			return ServerConfig.hasAwakeningsEnabled();
		};
		LiteralArgumentBuilder var26 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("awaken"))).requires((Predicate)(((java.util.function.Predicate)(Requires.hasPermission(ModPermissions.ABILITY_AWAKEN_COMMAND))).and((Predicate)(var25))))));
		Command var27 = (l0) -> {
			return awakeFruit(l0,EntityArgument.getPlayers(l0,"targets"));
		};
		LiteralArgumentBuilder var28 = ((LiteralArgumentBuilder)(var26.then(Commands.argument("targets",(ArgumentType)(EntityArgument.players())).executes(var27))));
		Command var29 = (l0) -> {
			return awakeFruit(l0,getDefaultCollection(l0));
		};
		var1.then(var28.executes(var29));
		return var1;
	}
	private static int awakeFruit(CommandContext<CommandSourceStack> var0, Collection<ServerPlayer> var1) {
		ServerPlayer var4 = null;
		Iterator<ServerPlayer> var2 = var1.iterator();
		do{
			if (var2.hasNext()){
				ServerPlayer var3 = ((ServerPlayer)(var2.next()));
				var4 = var3;
				final ServerPlayer var4_f1 = var4;
				Consumer<DevilFruitData> var5 = (l0) -> {
			l0.setAwakenedFruit(!(l0.hasAwakenedFruit()));
			ProgressionHandler.checkForPossibleFruitAbilities((LivingEntity)(var4_f1));
			ModNetwork.sendTo(new SSyncDevilFruitPacket((LivingEntity)(var4_f1)),(Player)(var4_f1));
		};
				((java.util.Optional)(DevilFruitData.get((LivingEntity)(var3)))).ifPresent((Consumer)(var5));
				continue;
			}else{
				break;
			}
		} while (true);
		return 1;
	}
	private static int abilityGroup(CommandContext<CommandSourceStack> var0, ResourceLocation var1, int var2, Collection<ServerPlayer> var3) {
		ResourceLocation var6 = null;
		ServerPlayer var8 = null;
		Iterator<ServerPlayer> var4 = var3.iterator();
		do{
			if (var4.hasNext()){
				ServerPlayer var5 = ((ServerPlayer)(var4.next()));
				var6 = var1;
				int var7 = var2;
				var8 = var5;
				final ResourceLocation var6_f1 = var6;
				final int var7_f1 = var7;
				final ServerPlayer var8_f2 = var8;
				Consumer<AbilityData> var9 = (l0) -> {
			Iterator lv15_4 = AbilityGroups.getAbilities(var6_f1).iterator();
			do{
				if (lv15_4.hasNext()){
					AbilityCore lv15_5 = ((AbilityCore)(((Supplier)(lv15_4.next())).get()));
					if (AbilityHelper.verifyIfAbilityIsBanned(lv15_5)){
						continue;
					}else{
						if ((var7_f1) == (1)){
							l0.addUnlockedAbility(lv15_5,AbilityUnlock.COMMAND);
							continue;
						}else{
							l0.removeUnlockedAbility(lv15_5);
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
			ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var8_f2)),(Player)(var8_f2));
		};
				((java.util.Optional)(AbilityData.get((LivingEntity)(var5)))).ifPresent((Consumer)(var9));
				continue;
			}else{
				break;
			}
		} while (true);
		return 1;
	}
	private static Collection<ServerPlayer> getDefaultCollection(CommandContext<CommandSourceStack> var0) throws CommandSyntaxException {
		ServerPlayer[] var1 = new ServerPlayer[]{((CommandSourceStack)(var0.getSource())).getPlayerOrException()};
		return Lists.newArrayList(var1);
	}
	private static int resetCooldown(CommandContext<CommandSourceStack> var0, Collection<ServerPlayer> var1) {
		Iterator<ServerPlayer> var2 = var1.iterator();
		do{
			if (var2.hasNext()){
				ServerPlayer var3 = ((ServerPlayer)(var2.next()));
				ServerPlayer var4 = var3;
				final ServerPlayer var4_f1 = var4;
				Consumer<AbilityData> var5 = (l0) -> {
			ServerPlayer lv18_4 = null;
			ServerPlayer lv18_6 = null;
			IAbility lv18_7 = null;
			Iterator lv18_2 = l0.getEquippedAbilities().iterator();
			do{
				if (lv18_2.hasNext()){
					IAbility lv18_3 = ((IAbility)(lv18_2.next()));
					if ((lv18_3) == (null)){
						continue;
					}else{
						lv18_4 = var4_f1;
						final ServerPlayer lv18_4_f1 = lv18_4;
						Consumer<CooldownComponent> lv18_5 = (l2_0) -> {
				l2_0.stopCooldown((LivingEntity)(lv18_4_f1));
			};
						((java.util.Optional)(lv18_3.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(lv18_5));
						lv18_6 = var4_f1;
						lv18_7 = lv18_3;
						final ServerPlayer lv18_6_f2 = lv18_6;
						final IAbility lv18_7_f3 = lv18_7;
						Consumer<StackComponent> lv18_8 = (l2_0) -> {
				l2_0.revertStacksToDefault((LivingEntity)(lv18_6_f2),lv18_7_f3);
			};
						((java.util.Optional)(lv18_3.getComponent(((AbilityComponentKey)(ModAbilityComponents.STACK.get()))))).ifPresent((Consumer)(lv18_8));
						continue;
					}
				}else{
					break;
				}
			} while (true);
		};
				((java.util.Optional)(AbilityData.get((LivingEntity)(var3)))).ifPresent((Consumer)(var5));
				Consumer<HakiData> var6 = (l0) -> {
			l0.setHakiOveruse(0);
		};
				((java.util.Optional)(HakiData.get((LivingEntity)(var3)))).ifPresent((Consumer)(var6));
				continue;
			}else{
				break;
			}
		} while (true);
		return 1;
	}
	private static int addAbility(CommandContext<CommandSourceStack> var0, AbilityCore<?> var1, Collection<ServerPlayer> var2) {
		AbilityCore<?> var5 = null;
		ServerPlayer var6 = null;
		if (AbilityHelper.verifyIfAbilityIsBanned(var1)){
			return -1;
		}else{
			Iterator<ServerPlayer> var3 = var2.iterator();
			do{
				if (var3.hasNext()){
					ServerPlayer var4 = ((ServerPlayer)(var3.next()));
					var5 = var1;
					var6 = var4;
					final AbilityCore<?> var5_f1 = var5;
					final ServerPlayer var6_f2 = var6;
					Consumer<AbilityData> var7 = (l0) -> {
			l0.addUnlockedAbility(var5_f1,AbilityUnlock.COMMAND);
			if (WyDebug.isDebug()){
				String lv20_3 = String.valueOf((Object)(ChatFormatting.GREEN));
				String lv20_4 = String.valueOf((Object)(ChatFormatting.ITALIC));
				String lv20_5 = var5_f1.getLocalizedName().getString();
				String lv20_6 = var6_f2.getName().getString();
				String lv20_7 = lv20_3 + lv20_4 + "[DEBUG] " + lv20_5 + " unlocked for " + lv20_6;
				var6_f2.displayClientMessage((Component)(Component.literal(lv20_7)),false);
			}
			ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var6_f2)),(Player)(var6_f2));
		};
					((java.util.Optional)(AbilityData.get((LivingEntity)(var4)))).ifPresent((Consumer)(var7));
					continue;
				}else{
					break;
				}
			} while (true);
			return 1;
		}
	}
	private static int removeAbility(CommandContext<CommandSourceStack> var0, AbilityCore<?> var1, Collection<ServerPlayer> var2) {
		AbilityCore<?> var5 = null;
		ServerPlayer var6 = null;
		Iterator<ServerPlayer> var3 = var2.iterator();
		do{
			if (var3.hasNext()){
				ServerPlayer var4 = ((ServerPlayer)(var3.next()));
				var5 = var1;
				var6 = var4;
				final AbilityCore<?> var5_f1 = var5;
				final ServerPlayer var6_f2 = var6;
				Consumer<AbilityData> var7 = (l0) -> {
			l0.removeUnlockedAbility(var5_f1);
			if (WyDebug.isDebug()){
				String lv21_3 = String.valueOf((Object)(ChatFormatting.GREEN));
				String lv21_4 = String.valueOf((Object)(ChatFormatting.ITALIC));
				String lv21_5 = var5_f1.getLocalizedName().getString();
				String lv21_6 = var6_f2.getName().getString();
				String lv21_7 = lv21_3 + lv21_4 + "[DEBUG] " + lv21_5 + " removed for " + lv21_6;
				var6_f2.displayClientMessage((Component)(Component.literal(lv21_7)),false);
			}
			ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var6_f2)),(Player)(var6_f2));
		};
				((java.util.Optional)(AbilityData.get((LivingEntity)(var4)))).ifPresent((Consumer)(var7));
				continue;
			}else{
				break;
			}
		} while (true);
		return 1;
	}
}