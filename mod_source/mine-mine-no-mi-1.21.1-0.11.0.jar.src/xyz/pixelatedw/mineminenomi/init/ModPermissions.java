package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.server.permission.PermissionAPI;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent;
import net.neoforged.neoforge.server.permission.nodes.PermissionDynamicContext;
import net.neoforged.neoforge.server.permission.nodes.PermissionDynamicContextKey;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import net.neoforged.neoforge.server.permission.nodes.PermissionType;
import net.neoforged.neoforge.server.permission.nodes.PermissionTypes;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class ModPermissions {
	// Fields
	public static final PermissionNode<Boolean> REMOVE_DF_COMMAND = createBoolNode("command.removedf",2);
	public static final PermissionNode<Boolean> REMOVE_DF_COMMAND_SELF = createBoolNode("command.removedf.self",2);
	public static final PermissionNode<Boolean> ABILITY_PROTECTION_COMMAND = createBoolNode("command.ability_protection",3);
	public static final PermissionNode<Boolean> CHECK_FRUITS_COMMAND_LIST = createBoolNode("command.check_fruits.list",2);
	public static final PermissionNode<Boolean> CHECK_FRUITS_COMMAND_HISTORY = createBoolNode("command.check_fruits.history",2);
	public static final PermissionNode<Boolean> CHECK_PLAYER_COMMAND = createBoolNode("command.check_player",2);
	public static final PermissionNode<Boolean> ISSUE_BOUNTY_COMMAND = createBoolNode("command.issue_bounty",2);
	public static final PermissionNode<Boolean> ABILITY_COMMAND = createBoolNode("command.ability",2);
	public static final PermissionNode<Boolean> ABILITY_AWAKEN_COMMAND = createBoolNode("command.ability.awaken",2);
	public static final PermissionNode<Boolean> ABILITY_COMMAND_RESET_COOLDOWN = createBoolNode("command.ability.reset_cooldown",2);
	public static final PermissionNode<Boolean> BELLY_COMMAND = createBoolNode("command.belly",2);
	public static final PermissionNode<Boolean> EXTOL_COMMAND = createBoolNode("command.extol",2);
	public static final PermissionNode<Boolean> BOUNTY_COMMAND = createBoolNode("command.bounty",2);
	public static final PermissionNode<Boolean> DORIKI_COMMAND = createBoolNode("command.doriki",2);
	public static final PermissionNode<Boolean> HAKIEXP_COMMAND = createBoolNode("command.hakiexp",2);
	public static final PermissionNode<Boolean> LOYALTY_COMMAND = createBoolNode("command.loyalty",2);
	public static final PermissionNode<Boolean> QUEST_COMMAND = createBoolNode("command.quest",2);
	public static final PermissionNode<Boolean> CHALLENGE_COMMAND = createBoolNode("command.challenge",2);
	public static final PermissionNode<Boolean> CHANGE_CHARACTER_COMMAND = createBoolNode("command.change_character",2);
	public static final PermissionNode<Boolean> EVENTS_COMMAND = createBoolNode("command.events",2);
	public static final PermissionNode<Boolean> POINTS_COMMAND = createBoolNode("command.points",2);
	public static final PermissionNode<Boolean> BUSTER_CALL_ITEM = createBoolNode("item.buster_call",2);

	public static void registerPermissions(PermissionGatherEvent.Nodes var0) {
		PermissionNode[] var1 = new PermissionNode[]{REMOVE_DF_COMMAND};
		var0.addNodes(var1);
		PermissionNode[] var2 = new PermissionNode[]{REMOVE_DF_COMMAND_SELF};
		var0.addNodes(var2);
		PermissionNode[] var3 = new PermissionNode[]{ABILITY_PROTECTION_COMMAND};
		var0.addNodes(var3);
		PermissionNode[] var4 = new PermissionNode[]{CHECK_FRUITS_COMMAND_LIST};
		var0.addNodes(var4);
		PermissionNode[] var5 = new PermissionNode[]{CHECK_FRUITS_COMMAND_HISTORY};
		var0.addNodes(var5);
		PermissionNode[] var6 = new PermissionNode[]{CHECK_PLAYER_COMMAND};
		var0.addNodes(var6);
		PermissionNode[] var7 = new PermissionNode[]{ISSUE_BOUNTY_COMMAND};
		var0.addNodes(var7);
		PermissionNode[] var8 = new PermissionNode[]{ABILITY_COMMAND};
		var0.addNodes(var8);
		PermissionNode[] var9 = new PermissionNode[]{ABILITY_COMMAND_RESET_COOLDOWN};
		var0.addNodes(var9);
		PermissionNode[] var10 = new PermissionNode[]{BELLY_COMMAND};
		var0.addNodes(var10);
		PermissionNode[] var11 = new PermissionNode[]{EXTOL_COMMAND};
		var0.addNodes(var11);
		PermissionNode[] var12 = new PermissionNode[]{BOUNTY_COMMAND};
		var0.addNodes(var12);
		PermissionNode[] var13 = new PermissionNode[]{DORIKI_COMMAND};
		var0.addNodes(var13);
		PermissionNode[] var14 = new PermissionNode[]{HAKIEXP_COMMAND};
		var0.addNodes(var14);
		PermissionNode[] var15 = new PermissionNode[]{LOYALTY_COMMAND};
		var0.addNodes(var15);
		PermissionNode[] var16 = new PermissionNode[]{QUEST_COMMAND};
		var0.addNodes(var16);
		PermissionNode[] var17 = new PermissionNode[]{CHALLENGE_COMMAND};
		var0.addNodes(var17);
		PermissionNode[] var18 = new PermissionNode[]{CHANGE_CHARACTER_COMMAND};
		var0.addNodes(var18);
		PermissionNode[] var19 = new PermissionNode[]{EVENTS_COMMAND};
		var0.addNodes(var19);
		PermissionNode[] var20 = new PermissionNode[]{POINTS_COMMAND};
		var0.addNodes(var20);
		PermissionNode[] var21 = new PermissionNode[]{BUSTER_CALL_ITEM};
		var0.addNodes(var21);
	}
	public static boolean hasPermission(Player var0, PermissionNode<Boolean> var1) {
		if (var0 instanceof ServerPlayer){
			ServerPlayer var2 = ((ServerPlayer)(var0));
			if (!(ServerConfig.hasPermissionsEnabled())){
				return false;
			}else{
				return ((Boolean)(PermissionAPI.getPermission((ServerPlayer)(var2),(PermissionNode)(var1),(PermissionDynamicContext[])(new PermissionDynamicContext[0])))).booleanValue();
			}
		}else{
			return false;
		}
	}
	private static PermissionNode<Boolean> createBoolNode(String var0, int var1) {
		int var2 = var1;
		final int var2_f1 = var2;
		PermissionNode.PermissionResolver var3 = (l0, l1, l2) -> {
			return Boolean.valueOf(((l0) != (null)) && (l0.hasPermissions(var2_f1)));
		};
		return new PermissionNode("mineminenomi",var0,PermissionTypes.BOOLEAN,var3,new PermissionDynamicContextKey[0]);
	}
}