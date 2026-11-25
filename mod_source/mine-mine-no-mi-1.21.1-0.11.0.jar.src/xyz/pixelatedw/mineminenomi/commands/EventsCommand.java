package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.poi.NTEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.POIEventTarget;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.NPCWorldData;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.world.spawners.AmbushSpawner;
import xyz.pixelatedw.mineminenomi.world.spawners.TraderSpawner;
import xyz.pixelatedw.mineminenomi.world.spawners.TrainerSpawner;

public class EventsCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("events"))).requires((Predicate)(Requires.hasPermission(ModPermissions.EVENTS_COMMAND)))));
		Command var1 = (l0) -> {
			return listEvents(l0,((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		LiteralArgumentBuilder var2 = ((LiteralArgumentBuilder)(var0.then(Commands.literal("list").executes(var1))));
		Command var3 = (l0) -> {
			new TraderSpawner().spawn(((CommandSourceStack)(l0.getSource())).getLevel());
			return 1;
		};
		LiteralArgumentBuilder var4 = ((LiteralArgumentBuilder)(Commands.literal("start").then(Commands.literal("trader").executes(var3))));
		Command var5 = (l0) -> {
			new TrainerSpawner().spawn(((CommandSourceStack)(l0.getSource())).getLevel());
			return 1;
		};
		LiteralArgumentBuilder var6 = ((LiteralArgumentBuilder)(var4.then(Commands.literal("trainer").executes(var5))));
		Command var7 = (l0) -> {
			new AmbushSpawner().spawn(((CommandSourceStack)(l0.getSource())).getLevel());
			return 1;
		};
		LiteralArgumentBuilder var8 = ((LiteralArgumentBuilder)(var6.then(Commands.literal("ambush").executes(var7))));
		Command var9 = (l0) -> {
			return startNotoriousTarget(l0,((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		LiteralArgumentBuilder var10 = ((LiteralArgumentBuilder)(var8.then(Commands.literal("notorious_target").executes(var9))));
		Command var11 = (l0) -> {
			return startCaravan(l0,((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		LiteralArgumentBuilder var12 = ((LiteralArgumentBuilder)(var10.then(Commands.literal("caravan").executes(var11))));
		Command var13 = (l0) -> {
			return startCelestialDragon(l0,((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		var2.then(var12.then(Commands.literal("celestial_dragon").executes(var13)));
		return var0;
	}
	private static int listEvents(CommandContext<CommandSourceStack> var0, ServerPlayer var1) {
		EventsWorldData var2 = EventsWorldData.get();
		Set var3 = var2.getNotoriousTargets();
		Set var4 = var2.getCaravanPOIs();
		Set var5 = var2.getCelestialVisitsPOIs();
		StringBuilder var6 = new StringBuilder();
		int var7 = var3.size();
		String var8 = "§6Targets:§r " + var7 + "\n";
		var6.append(var8);
		Iterator var9 = var3.iterator();
		do{
			if (var9.hasNext()){
				NTEventTarget var10 = ((NTEventTarget)(var9.next()));
				int var11 = (int)(var10.getPosition().x);
				int var12 = (int)(var10.getPosition().y);
				int var13 = (int)(var10.getPosition().z);
				String var14 = "\n " + var11 + " " + var12 + " " + var13;
				var6.append(var14);
				continue;
			}else{
				break;
			}
		} while (true);
		var6.append("\n\n");
		int var10_1 = var4.size();
		String var11_1 = "§6Caravans:§r " + var10_1 + "\n";
		var6.append(var11_1);
		Iterator var12_1 = var4.iterator();
		do{
			if (var12_1.hasNext()){
				POIEventTarget var13_1 = ((POIEventTarget)(var12_1.next()));
				int var14_1 = (int)(var13_1.getPosition().x);
				int var15 = (int)(var13_1.getPosition().y);
				int var16 = (int)(var13_1.getPosition().z);
				String var17 = "\n " + var14_1 + " " + var15 + " " + var16;
				var6.append(var17);
				continue;
			}else{
				break;
			}
		} while (true);
		var6.append("\n\n");
		int var13_2 = var5.size();
		String var14_2 = "§6Visits:§r " + var13_2 + "\n";
		var6.append(var14_2);
		Iterator var15_1 = var5.iterator();
		do{
			if (var15_1.hasNext()){
				POIEventTarget var16_1 = ((POIEventTarget)(var15_1.next()));
				int var17_1 = (int)(var16_1.getPosition().x);
				int var18 = (int)(var16_1.getPosition().y);
				int var19 = (int)(var16_1.getPosition().z);
				String var20 = "\n " + var17_1 + " " + var18 + " " + var19;
				var6.append(var20);
				continue;
			}else{
				break;
			}
		} while (true);
		var6.append("\n\n");
		WyHelper.sendMessage((LivingEntity)(var1),(Component)(Component.literal(var6.toString())),true);
		return 1;
	}
	private static int startNotoriousTarget(CommandContext<CommandSourceStack> var0, ServerPlayer var1) {
		EventsWorldData var2 = EventsWorldData.get();
		Optional var3 = NPCWorldData.get().getRandomTrackedMob(((Faction)(ModFactions.PIRATE.get())));
		if (!(var3.isPresent())){
			return 1;
		}else{
			Optional var4 = var2.findEventPositionAround(var1.serverLevel(),var1.position(),1,10,false);
			if (var4.isPresent()){
				var2.addNotoriousTarget(var1.serverLevel(),((Vec3)(var4.get())),1200L,((TrackedNPC)(var3.get())));
			}
			return 1;
		}
	}
	private static int startCaravan(CommandContext<CommandSourceStack> var0, ServerPlayer var1) {
		EventsWorldData var2 = EventsWorldData.get();
		Vec3 var3 = var1.position().add((double)(WyHelper.randomWithRange(-10,10)),-1D,(double)(WyHelper.randomWithRange(-10,10)));
		var2.addCaravan(var1.serverLevel(),var3,1200L);
		return 1;
	}
	private static int startCelestialDragon(CommandContext<CommandSourceStack> var0, ServerPlayer var1) {
		EventsWorldData var2 = EventsWorldData.get();
		Vec3 var3 = var1.position().add((double)(WyHelper.randomWithRange(-10,10)),0D,(double)(WyHelper.randomWithRange(-10,10)));
		var2.addCelestialVisit(var1.serverLevel(),var3);
		return 1;
	}
}