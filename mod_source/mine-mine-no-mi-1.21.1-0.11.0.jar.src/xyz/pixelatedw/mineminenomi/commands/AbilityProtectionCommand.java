package xyz.pixelatedw.mineminenomi.commands;

import com.google.common.base.Strings;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.util.DBlockPos;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.handlers.entity.PatreonHandler;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SViewProtectionPacket;

public class AbilityProtectionCommand {
	// Fields
	private static final int MAX_SIZE = 59999999;
	private static final String RESET = "§r";

	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("abilityprotection"))).requires((Predicate)(Requires.hasPermission(ModPermissions.ABILITY_PROTECTION_COMMAND)))));
		Command var1 = (l0) -> {
			return createProtection(l0,IntegerArgumentType.getInteger(l0,"size"),StringArgumentType.getString(l0,"label"));
		};
		LiteralArgumentBuilder var2 = ((LiteralArgumentBuilder)(var0.then(Commands.literal("new").then(Commands.argument("size",(ArgumentType)(IntegerArgumentType.integer(1,59999999))).then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).executes(var1))))));
		Command var3 = (l0) -> {
			return resizeProtection(l0,StringArgumentType.getString(l0,"label"),IntegerArgumentType.getInteger(l0,"size"));
		};
		LiteralArgumentBuilder var4 = ((LiteralArgumentBuilder)(var2.then(Commands.literal("resize").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("size",(ArgumentType)(IntegerArgumentType.integer(1,59999999))).executes(var3))))));
		Command var5 = (l0) -> {
			return renameProtection(l0,StringArgumentType.getString(l0,"label"),StringArgumentType.getString(l0,"new_label"));
		};
		LiteralArgumentBuilder var6 = ((LiteralArgumentBuilder)(var4.then(Commands.literal("rename").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("new_label",(ArgumentType)(StringArgumentType.string())).executes(var5))))));
		Command var7 = (l0) -> {
			return printAreaInfo(l0,StringArgumentType.getString(l0,"label"));
		};
		LiteralArgumentBuilder var8 = ((LiteralArgumentBuilder)(var6.then(Commands.literal("info").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).executes(var7)))));
		Command var9 = (l0) -> {
			return changeBlockDestructionProp(l0,StringArgumentType.getString(l0,"label"),BoolArgumentType.getBool(l0,"state"));
		};
		LiteralArgumentBuilder var10 = ((LiteralArgumentBuilder)(Commands.literal("props").then(Commands.literal("block_destruction").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("state",(ArgumentType)(BoolArgumentType.bool())).executes(var9))))));
		Command var11 = (l0) -> {
			return changeEntityDamageProp(l0,StringArgumentType.getString(l0,"label"),BoolArgumentType.getBool(l0,"state"));
		};
		LiteralArgumentBuilder var12 = ((LiteralArgumentBuilder)(var10.then(Commands.literal("entity_damage").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("state",(ArgumentType)(BoolArgumentType.bool())).executes(var11))))));
		Command var13 = (l0) -> {
			return changePlayerDamageProp(l0,StringArgumentType.getString(l0,"label"),BoolArgumentType.getBool(l0,"state"));
		};
		LiteralArgumentBuilder var14 = ((LiteralArgumentBuilder)(var12.then(Commands.literal("player_damage").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("state",(ArgumentType)(BoolArgumentType.bool())).executes(var13))))));
		Command var15 = (l0) -> {
			return changeBlockRestorationProp(l0,StringArgumentType.getString(l0,"label"),BoolArgumentType.getBool(l0,"state"));
		};
		LiteralArgumentBuilder var16 = ((LiteralArgumentBuilder)(var14.then(Commands.literal("block_restoration").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("state",(ArgumentType)(BoolArgumentType.bool())).executes(var15))))));
		Command var17 = (l0) -> {
			return changeAbilitiesUsageProp(l0,StringArgumentType.getString(l0,"label"),BoolArgumentType.getBool(l0,"state"));
		};
		LiteralArgumentBuilder var18 = ((LiteralArgumentBuilder)(var16.then(Commands.literal("abilities_use").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("state",(ArgumentType)(BoolArgumentType.bool())).executes(var17))))));
		Command var19 = (l0) -> {
			return changeStatLossProp(l0,StringArgumentType.getString(l0,"label"),BoolArgumentType.getBool(l0,"state"));
		};
		LiteralArgumentBuilder var20 = ((LiteralArgumentBuilder)(var18.then(Commands.literal("stat_loss").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("state",(ArgumentType)(BoolArgumentType.bool())).executes(var19))))));
		Command var21 = (l0) -> {
			return changeDeathProp(l0,StringArgumentType.getString(l0,"label"),BoolArgumentType.getBool(l0,"state"));
		};
		LiteralArgumentBuilder var22 = ((LiteralArgumentBuilder)(var20.then(Commands.literal("death").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("state",(ArgumentType)(BoolArgumentType.bool())).executes(var21))))));
		Command var23 = (l0) -> {
			return changeMobSpawnsProp(l0,StringArgumentType.getString(l0,"label"),BoolArgumentType.getBool(l0,"state"));
		};
		LiteralArgumentBuilder var24 = ((LiteralArgumentBuilder)(var22.then(Commands.literal("mob_spawns").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("state",(ArgumentType)(BoolArgumentType.bool())).executes(var23))))));
		Command var25 = (l0) -> {
			return changeUnconsciousTimeProp(l0,StringArgumentType.getString(l0,"label"),IntegerArgumentType.getInteger(l0,"time"));
		};
		LiteralArgumentBuilder var26 = ((LiteralArgumentBuilder)(var24.then(Commands.literal("unconscious_time").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("time",(ArgumentType)(IntegerArgumentType.integer(0,1200))).executes(var25))))));
		Command var27 = (l0) -> {
			return changeRestorationIntervalProp(l0,StringArgumentType.getString(l0,"label"),IntegerArgumentType.getInteger(l0,"interval"));
		};
		LiteralArgumentBuilder var28 = ((LiteralArgumentBuilder)(var26.then(Commands.literal("restoration_interval").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("interval",(ArgumentType)(IntegerArgumentType.integer(0,1200))).executes(var27))))));
		Command var29 = (l0) -> {
			return changeRestorationAmountProp(l0,StringArgumentType.getString(l0,"label"),IntegerArgumentType.getInteger(l0,"amount"));
		};
		LiteralArgumentBuilder var30 = ((LiteralArgumentBuilder)(var28.then(Commands.literal("restoration_amount").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("amount",(ArgumentType)(IntegerArgumentType.integer(1,500))).executes(var29))))));
		Command var31 = (l0) -> {
			return changeRestorationDistanceProp(l0,StringArgumentType.getString(l0,"label"),IntegerArgumentType.getInteger(l0,"amount"));
		};
		LiteralArgumentBuilder var32 = ((LiteralArgumentBuilder)(var30.then(Commands.literal("restoration_distance").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).then(Commands.argument("amount",(ArgumentType)(IntegerArgumentType.integer(0,1000))).executes(var31))))));
		Command var33 = (l0) -> {
			return changeDisableProp(l0,StringArgumentType.getString(l0,"label"));
		};
		LiteralArgumentBuilder var34 = ((LiteralArgumentBuilder)(var8.then(var32.then(Commands.literal("toggle").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).executes(var33))))));
		Command var35 = (l0) -> {
			return viewProtection(l0,BoolArgumentType.getBool(l0,"state"));
		};
		LiteralArgumentBuilder var36 = ((LiteralArgumentBuilder)(((LiteralArgumentBuilder)(var34.then(Commands.literal("view").then(Commands.argument("state",(ArgumentType)(BoolArgumentType.bool())).executes(var35))))).then(Commands.literal("list").executes((Command)(((com.mojang.brigadier.Command)(AbilityProtectionCommand::listProtections)))))));
		Command var37 = (l0) -> {
			return deleteProtection(l0,StringArgumentType.getString(l0,"label"));
		};
		var36.then(Commands.literal("remove").then(Commands.argument("label",(ArgumentType)(StringArgumentType.string())).executes(var37)));
		return var0;
	}
	private static int printAreaInfo(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
		ProtectedArea var2 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel()).getProtectedArea(var1);
		MutableComponent var3 = Component.literal("");
		String var4 = (var2.isDisabled()) ? (" (Disabled)") : ("");
		String var5 = var2.getLabel();
		String var6 = var4;
		String var7 = "Label: " + var5 + var6 + "\n";
		var3.append(var7);
		int var8 = var2.getSize();
		String var9 = "Size: " + var8 + "\n";
		var3.append(var9);
		var3.append("Can Destroy Blocks: ");
		addInfoOption(var3,var2.canDestroyBlocks());
		var3.append("Can Restore Blocks: ");
		addInfoOption(var3,var2.canRestoreBlocks());
		int var10 = var2.getRestoreAmount();
		String var11 = "Restoration Amount: " + var10 + "\n";
		var3.append(var11);
		int var12 = var2.getRestoreInterval();
		String var13 = "Restoration Interval (ticks): " + var12 + "\n";
		var3.append(var13);
		var3.append("Can Hurt Entities: ");
		addInfoOption(var3,var2.canHurtEntities());
		var3.append("Can Hurt Players: ");
		addInfoOption(var3,var2.canHurtPlayers());
		var3.append("Can Use Abilities: ");
		addInfoOption(var3,var2.canAbilitiesBeUsed());
		var3.append("Can Lose Stats: ");
		addInfoOption(var3,var2.canLoseStats());
		var3.append("Can Die: ");
		addInfoOption(var3,var2.canDie());
		var3.append("Can Mobs Spawn: ");
		addInfoOption(var3,var2.canMobsSpawn());
		int var14 = var2.getUnconsciousTime();
		String var15 = "Unconscious Time (ticks): " + var14 + "\n";
		var3.append(var15);
		if ((!(WyDebug.isDebug())) && (PatreonHandler.isReleaseBuild())){

		}else{
			int var16 = var2.getRestorationMap().size();
			String var17 = "Restoration Queue: " + var16;
			var3.append(var17);
		}
		CommandSourceStack var16_1 = ((CommandSourceStack)(var0.getSource()));
		MutableComponent var17_1 = var3;
		final MutableComponent var17_1_f1 = var17_1;
		Supplier<Component> var18 = () -> {
			return var17_1_f1;
		};
		var16_1.sendSuccess(var18,true);
		return 1;
	}
	private static MutableComponent addInfoOption(MutableComponent var0, boolean var1) {
		Style var2 = Style.EMPTY.withColor((var1) ? (ChatFormatting.GREEN) : (ChatFormatting.RED));
		Style var3 = Style.EMPTY.withColor((!(var1)) ? (ChatFormatting.GREEN) : (ChatFormatting.RED));
		var0.append((Component)(Component.literal("[true]").withStyle(var2)));
		var0.append(String.format("%s/",new Object[]{"§r"}));
		var0.append((Component)(Component.literal("[false]").withStyle(var3)));
		var0.append(String.format("%s\n",new Object[]{"§r"}));
		return var0;
	}
	private static int changeRestorationAmountProp(CommandContext<CommandSourceStack> var0, String var1, int var2) {
		ProtectedAreasData var3 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		var3.getProtectedArea(var1).setRestoreAmount(var2);
		var3.setDirty();
		return 1;
	}
	private static int changeRestorationDistanceProp(CommandContext<CommandSourceStack> var0, String var1, int var2) {
		ProtectedAreasData var3 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		var3.getProtectedArea(var1).setRestoreDistance(var2);
		var3.setDirty();
		return 1;
	}
	private static int changeRestorationIntervalProp(CommandContext<CommandSourceStack> var0, String var1, int var2) {
		ProtectedAreasData var3 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		var3.getProtectedArea(var1).setRestoreInterval(var2);
		var3.setDirty();
		return 1;
	}
	private static int changeAbilitiesUsageProp(CommandContext<CommandSourceStack> var0, String var1, boolean var2) {
		ProtectedAreasData var3 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		var3.getProtectedArea(var1).setAbilitiesUsage(var2);
		var3.setDirty();
		return 1;
	}
	private static int changeBlockDestructionProp(CommandContext<CommandSourceStack> var0, String var1, boolean var2) {
		ProtectedAreasData var3 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		var3.getProtectedArea(var1).setBlockDestruction(var2);
		var3.setDirty();
		return 1;
	}
	private static int changeEntityDamageProp(CommandContext<CommandSourceStack> var0, String var1, boolean var2) {
		ProtectedAreasData var3 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		var3.getProtectedArea(var1).setEntityDamage(var2);
		var3.setDirty();
		return 1;
	}
	private static int changePlayerDamageProp(CommandContext<CommandSourceStack> var0, String var1, boolean var2) {
		ProtectedAreasData var3 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		var3.getProtectedArea(var1).setPlayerDamage(var2);
		var3.setDirty();
		return 1;
	}
	private static int changeBlockRestorationProp(CommandContext<CommandSourceStack> var0, String var1, boolean var2) {
		ProtectedAreasData var3 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		var3.getProtectedArea(var1).setBlockRestoration(var2);
		var3.setDirty();
		return 1;
	}
	private static int changeStatLossProp(CommandContext<CommandSourceStack> var0, String var1, boolean var2) {
		ProtectedAreasData var3 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		var3.getProtectedArea(var1).setStatLoss(var2);
		var3.setDirty();
		return 1;
	}
	private static int changeDeathProp(CommandContext<CommandSourceStack> var0, String var1, boolean var2) {
		ProtectedAreasData var3 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		var3.getProtectedArea(var1).setDeath(var2);
		var3.setDirty();
		return 1;
	}
	private static int changeMobSpawnsProp(CommandContext<CommandSourceStack> var0, String var1, boolean var2) {
		ProtectedAreasData var3 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		var3.getProtectedArea(var1).setMobSpawns(var2);
		var3.setDirty();
		return 1;
	}
	private static int changeUnconsciousTimeProp(CommandContext<CommandSourceStack> var0, String var1, int var2) {
		ProtectedAreasData var3 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		var3.getProtectedArea(var1).setUnconsciousTime(var2);
		var3.setDirty();
		return 1;
	}
	private static int changeDisableProp(CommandContext<CommandSourceStack> var0, String var1) {
		ProtectedAreasData var2 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		ProtectedArea var3 = var2.getProtectedArea(var1);
		var3.setDisabled(!(var3.isDisabled()));
		var2.setDirty();
		ModNetwork.sendTo(SViewProtectionPacket.update(((CommandSourceStack)(var0.getSource())).getLevel()),(Player)(((CommandSourceStack)(var0.getSource())).getPlayer()));
		return 1;
	}
	private static int resizeProtection(CommandContext<CommandSourceStack> var0, String var1, int var2) throws CommandSyntaxException {
		ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel()).resizeRestrictedArea(var1,var2);
		ModNetwork.sendTo(SViewProtectionPacket.update(((CommandSourceStack)(var0.getSource())).getLevel()),(Player)(((CommandSourceStack)(var0.getSource())).getPlayer()));
		return 1;
	}
	private static int renameProtection(CommandContext<CommandSourceStack> var0, String var1, String var2) throws CommandSyntaxException {
		ProtectedAreasData var3 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		ProtectedArea var4 = var3.getProtectedArea(var1);
		var3.removeRestrictedArea(var1);
		var4.setLabel(var2);
		var3.addRestrictedArea(var4);
		return 1;
	}
	private static int deleteProtection(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
		ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel()).removeRestrictedArea(var1);
		return 1;
	}
	private static int listProtections(CommandContext<CommandSourceStack> var0) throws CommandSyntaxException {
		ProtectedAreasData var1 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		MutableComponent var2 = Component.literal("Protection Sites: \n\n").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
		int var3 = 1;
		Iterator var4 = var1.getAllRestrictions().values().iterator();
		do{
			if (var4.hasNext()){
				ProtectedArea var5 = ((ProtectedArea)(var4.next()));
				int var6 = var5.getCenter().getX();
				int var7 = var5.getCenter().getY();
				int var8 = var5.getCenter().getZ();
				String var9 = "";
				if (!(Strings.isNullOrEmpty(var5.getLabel()))){
					String var10 = var5.getLabel();
					String var11 = "(" + var10 + ")";
					var9 = var11;
				}
				int var10_1 = var6;
				int var11_1 = var7;
				int var12 = var8;
				String var13 = "/tp @p " + var10_1 + " " + var11_1 + " " + var12;
				Style var14 = Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,var13));
				int var15 = var3;
				String var16 = var9;
				int var17 = var6;
				int var18 = var7;
				int var19 = var8;
				String var20 = "" + var15 + ". " + var16 + " " + var17 + ", " + var18 + ", " + var19 + "\n";
				var2.append((Component)(Component.literal(var20).setStyle(var14.withColor((((var3) % (2)) == (0)) ? (ChatFormatting.GREEN) : (ChatFormatting.DARK_GREEN)))));
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		CommandSourceStack var5_1 = ((CommandSourceStack)(var0.getSource()));
		MutableComponent var6_1 = var2;
		final MutableComponent var6_1_f1 = var6_1;
		Supplier<Component> var7_1 = () -> {
			return var6_1_f1;
		};
		var5_1.sendSuccess(var7_1,true);
		return 1;
	}
	private static int viewProtection(CommandContext<CommandSourceStack> var0, boolean var1) throws CommandSyntaxException {
		ServerPlayer var2 = ((CommandSourceStack)(var0.getSource())).getPlayerOrException();
		ModNetwork.sendTo(new SViewProtectionPacket(((CommandSourceStack)(var0.getSource())).getLevel(),var1),(Player)(var2));
		return 1;
	}
	private static int createProtection(CommandContext<CommandSourceStack> var0, int var1, String var2) throws CommandSyntaxException {
		ServerLevel var3 = ((CommandSourceStack)(var0.getSource())).getLevel();
		ProtectedAreasData var4 = ProtectedAreasData.get(((CommandSourceStack)(var0.getSource())).getLevel());
		if ((var4.getProtectedArea(var2)) != (null)){
			((CommandSourceStack)(var0.getSource())).sendFailure((Component)(Component.literal("Protection with this name already exists!")));
			return -1;
		}else{
			Vec3 var5 = ((CommandSourceStack)(var0.getSource())).getPosition();
			DBlockPos var6 = new DBlockPos((int)(var5.x),(int)(var5.y),(int)(var5.z),var3.dimension());
			var4.addRestrictedArea((BlockPos)(var6),var1,var2);
			StringBuilder var7 = new StringBuilder();
			String var8 = var2;
			int var9 = var6.getX();
			int var10 = var6.getY();
			int var11 = var6.getZ();
			int var12 = var1;
			String var13 = "Created an ability protection zone named " + var8 + " at " + var9 + " " + var10 + " " + var11 + " of size " + var12;
			var7.append(var13);
			CommandSourceStack var14 = ((CommandSourceStack)(var0.getSource()));
			StringBuilder var15 = var7;
			final StringBuilder var15_f1 = var15;
			Supplier<Component> var16 = () -> {
			return Component.literal(var15_f1.toString());
		};
			var14.sendSuccess(var16,true);
			return 1;
		}
	}
}