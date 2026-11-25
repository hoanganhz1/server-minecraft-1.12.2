package xyz.pixelatedw.mineminenomi.config;

import xyz.pixelatedw.mineminenomi.api.config.options.BooleanOption;
import xyz.pixelatedw.mineminenomi.api.config.options.DoubleOption;
import xyz.pixelatedw.mineminenomi.api.config.options.EnumOption;
import xyz.pixelatedw.mineminenomi.api.config.options.IntegerOption;

public class WorldEventsConfig {
	// Fields
	public static final BooleanOption SPAWN_WORLD_HUMANOIDS = new BooleanOption(Boolean.valueOf(true),"World Humanoids Spawns","Allows random aggressive faction humans to spawn in the world (Marines, Pirates, Bandits)\nDefault: true");
	public static final BooleanOption SPAWN_WORLD_ANIMALS = new BooleanOption(Boolean.valueOf(true),"World Animals Spawns","Allows random animal NPCs to spawn in the world (Dugongs, Bananwanis, Gorillas etc.)\nDefault: true");
	public static final BooleanOption SPAWN_NOTORIOUS_TARGETS = new BooleanOption(Boolean.valueOf(true),"Spawn Notorious Targets","Allows Notorious Target events to spawn in the world\nDefault: true");
	public static final BooleanOption SPAWN_CARAVANS = new BooleanOption(Boolean.valueOf(true),"Spawn Caravans","Allows Caravan events to spawn in the world\nDefault: true");
	public static final BooleanOption SPAWN_CELESTIAL_VISITS = new BooleanOption(Boolean.valueOf(true),"Spawn Celestial Visits","Allows Celestial Visit events to spawn in the world\nDefault: true");
	public static final IntegerOption TIME_BETWEEN_TRADER_SPAWNS = new IntegerOption(Integer.valueOf(2),Integer.valueOf(0),Integer.valueOf(30),"Time Between Trader Spawns","Determines the time (in minutes) between two spawn attempts\nDefault: 2 (minutes)");
	public static final IntegerOption SPAWN_CHANCE_TRADER = new IntegerOption(Integer.valueOf(1),Integer.valueOf(0),Integer.valueOf(100),"Chance for Trader Spawns","Determines the % chance for a trader to spawn\nDefault: 1");
	public static final IntegerOption TIME_BETWEEN_TRAINER_SPAWNS = new IntegerOption(Integer.valueOf(2),Integer.valueOf(0),Integer.valueOf(30),"Time Between Trainer Spawns","Determines the time (in minutes) between two spawn attempts\nDefault: 2 (minutes)");
	public static final IntegerOption SPAWN_CHANCE_TRAINER = new IntegerOption(Integer.valueOf(15),Integer.valueOf(0),Integer.valueOf(100),"Chance for Trainer Spawns","Determines the % chance for a trainer to spawn\nDefault: 15");
	public static final IntegerOption TIME_BETWEEN_AMBUSH_SPAWNS = new IntegerOption(Integer.valueOf(15),Integer.valueOf(0),Integer.valueOf(30),"Time Between Ambush Spawns","Determines the time (in minutes) between two spawn attempts\nDefault: 15 (minutes)");
	public static final IntegerOption SPAWN_CHANCE_AMBUSH = new IntegerOption(Integer.valueOf(15),Integer.valueOf(0),Integer.valueOf(100),"Chance for Ambush Spawns","Determines the % chance for an ambush to spawn\nDefault: 15");
	public static final DoubleOption GRUNT_SPAWN_CHANCE = new DoubleOption(Double.valueOf(100D),Double.valueOf(0D),Double.valueOf(100D),"Grunts Spawn Chance","Chance for grunts to spawn in world\nDefault: 100");
	public static final DoubleOption BRUTE_SPAWN_CHANCE = new DoubleOption(Double.valueOf(100D),Double.valueOf(0D),Double.valueOf(100D),"Brutes Spawn Chance","Chance for brutes to spawn in world\nDefault: 100");
	public static final DoubleOption SNIPER_SPAWN_CHANCE = new DoubleOption(Double.valueOf(100D),Double.valueOf(0D),Double.valueOf(100D),"Snipers Spawn Chance","Chance for snipers to spawn in world\nDefault: 100");
	public static final DoubleOption CAPTAIN_SPAWN_CHANCE = new DoubleOption(Double.valueOf(100D),Double.valueOf(0D),Double.valueOf(100D),"Captains Spawn Chance","Chance for captains to spawn in world\nDefault: 100");
	public static final DoubleOption PACIFISTA_SPAWN_CHANCE = new DoubleOption(Double.valueOf(100D),Double.valueOf(0D),Double.valueOf(100D),"Pacifistas Spawn Chance","Chance for pacifistas to spawn in world\nDefault: 100");
	public static final DoubleOption NEW_WORLD_START_RADIUS = new DoubleOption(Double.valueOf(0D),Double.valueOf(0D),Double.valueOf(5.9999968e+07D),"New World Start Radius","The radius (in blocks) after which the new world start after its toggled\nNote that 0 doesn't mean its disabled but rather that the entire world is affected.\nDefault: 0");
	public static final EnumOption<ServerConfig$NewWorldMode> NEW_WORLD_MODE = new EnumOption(ServerConfig$NewWorldMode.NORMAL,ServerConfig$NewWorldMode.values(),"New World Mode","Defines under which conditions the new world mode can be enabled\n NORMAL activates it once the Ender Dragon has been defeated\n START activates it from the start of the world\nDefault: NORMAL");
}