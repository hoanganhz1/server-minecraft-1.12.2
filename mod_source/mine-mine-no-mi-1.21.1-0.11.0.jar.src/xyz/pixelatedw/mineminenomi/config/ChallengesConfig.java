package xyz.pixelatedw.mineminenomi.config;

import java.util.Arrays;
import java.util.List;
import xyz.pixelatedw.mineminenomi.api.config.options.BooleanOption;
import xyz.pixelatedw.mineminenomi.api.config.options.IntegerListOption;
import xyz.pixelatedw.mineminenomi.api.config.options.IntegerOption;

public class ChallengesConfig {
	// Fields
	public static final BooleanOption ENABLE_CHALLENGES = new BooleanOption(Boolean.valueOf(true),"Enable Challenges","Enables the challenges menu and activation by players\nDefault: true");
	public static final BooleanOption CHALLENGE_CACHING = new BooleanOption(Boolean.valueOf(true),"Challenge Caching","Saves the player's inventory before starting a challenge and restores it to its pre-challenge format regardless of what happens during the challenge (such as losing or using items)\nDefault: true");
	public static final BooleanOption RETURN_TO_SAFETY = new BooleanOption(Boolean.valueOf(true),"Return to safety","Returns players to their beds or world's spawn point after finishing a challenge\nWhen set to false it'll return them all to the original position the challenge starter was in.\nDefault: true");
	public static final IntegerOption ARENA_CONSTRUCTION_SPEED = new IntegerOption(Integer.valueOf(50),Integer.valueOf(10),Integer.valueOf(100),"Arena Construction Speed","Defines the speed at which the arena generates\nDo note that a faster speed implies more blocks being placed per tick, which might affect performance on servers\nLower speeds will mean the server will remain stable throughout arena generation however the time it takes to fully generate it will increase\nDefault: 50");
	public static final IntegerListOption DORIKI_REWARD_POOL;
	public static final IntegerListOption BELLY_REWARD_POOL;
	public static final IntegerListOption HAKI_REWARD_POOL;

	static  {
		Integer[] var0 = new Integer[]{Integer.valueOf(1000),Integer.valueOf(4000),Integer.valueOf(0)};
		DORIKI_REWARD_POOL = new IntegerListOption(Arrays.asList(var0),"Doriki Reward Pool","Doriki reward pool for all 3 difficulties for challenge\nDefault: [1000, 4000, 0]");
		Integer[] var1 = new Integer[]{Integer.valueOf(5000),Integer.valueOf(15000),Integer.valueOf(0)};
		BELLY_REWARD_POOL = new IntegerListOption(Arrays.asList(var1),"Belly Reward Pool","Belly reward pool for all 3 difficulties for challenge\nDefault: [5000, 15000, 0]");
		Integer[] var2 = new Integer[]{Integer.valueOf(10),Integer.valueOf(40),Integer.valueOf(0)};
		HAKI_REWARD_POOL = new IntegerListOption(Arrays.asList(var2),"Haki Reward Pool","Haki reward pool for all 3 difficulties for challenge\nDefault: [10, 40, 0]");
	}
}