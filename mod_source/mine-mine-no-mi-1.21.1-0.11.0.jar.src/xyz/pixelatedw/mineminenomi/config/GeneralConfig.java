package xyz.pixelatedw.mineminenomi.config;

import xyz.pixelatedw.mineminenomi.api.config.options.BooleanOption;
import xyz.pixelatedw.mineminenomi.api.config.options.DoubleOption;
import xyz.pixelatedw.mineminenomi.api.config.options.IntegerOption;

public class GeneralConfig {
	// Fields
	public static final BooleanOption EXTRA_HEARTS = new BooleanOption(Boolean.valueOf(true),"Extra Hearts","Allows players to receive extra hearts based on their doriki\nDefault: true");
	public static final BooleanOption MOB_REWARDS = new BooleanOption(Boolean.valueOf(true),"Mob Rewards","Allows mobs to reward doriki, bounty or items\nDefault: true");
	public static final BooleanOption MINIMUM_DORIKI_PER_KILL = new BooleanOption(Boolean.valueOf(false),"Minimum Doriki per Kill","Guarantees a minimum of 1 doriki per kill\n If used together with a Haki Exp Multiplier with a multiplier less than <1.0 it will convert it to chances\nDefault: false");
	public static final BooleanOption DESTROY_SPAWNER = new BooleanOption(Boolean.valueOf(true),"Destroy Spawner","Destroys the spawner after all its spawns are killed\nDefault: true");
	public static final BooleanOption DESTROY_WATER = new BooleanOption(Boolean.valueOf(false),"Destroy Water","Allows big explosions to destroy water \nDefault: false");
	public static final BooleanOption RACE_RANDOMIZER = new BooleanOption(Boolean.valueOf(false),"Race Randomizer","Randomizes the player's race at spawn (making the player unable to choose a race themselves) \nDefault: false");
	public static final BooleanOption ALLOW_SUB_RACE_SELECT = new BooleanOption(Boolean.valueOf(true),"Allow Sub Race Select","Allow players to choose their specific Sub Race, if that race has any \nDefault: true");
	public static final BooleanOption NATIVE_HAKI = new BooleanOption(Boolean.valueOf(true),"Native Haki","Allows vanilla and other modded NPCs to use Busoshoku Haki, has no visual effect on their model however and its purely mechanical \nDefault: true");
	public static final BooleanOption PUBLIC_REMOVEDF = new BooleanOption(Boolean.valueOf(false),"Public /removedf","Allows non-OP users to use /removedf command ON THEMSELVES! \nDefault: false");
	public static final BooleanOption PUBLIC_CHECK_FRUITS = new BooleanOption(Boolean.valueOf(false),"Public /check_fruits","Allows non-OP users to use /check_fruits command. Note that this is only for the listing of fruits, the history subcommand with all of its functionality will not be affected by this config.\nDefault: false");
	public static final BooleanOption DESPAWN_WITH_NAMETAG = new BooleanOption(Boolean.valueOf(false),"Despawn NPCs with Nametags","Normally despawns traders and trainers even if they're nametagged \nDefault: false");
	public static final IntegerOption DORIKI_LIMIT = new IntegerOption(Integer.valueOf(10000),Integer.valueOf(0),Integer.valueOf(100000),"Doriki Limit","Sets a new limit for maximum doriki a player may obtain\nDefault: 10000");
	public static final IntegerOption HAKI_EXP_LIMIT = new IntegerOption(Integer.valueOf(100),Integer.valueOf(0),Integer.valueOf(300),"Haki Exp Limit","Sets a new limit for maximum haki exp a player may obtain\nDefault: 100");
	public static final IntegerOption TRAINING_POINT_LIMIT = new IntegerOption(Integer.valueOf(100),Integer.valueOf(0),Integer.valueOf(999),"Training Point Limit","Sets a new limit for maximum training points a player may obtain\nDefault: 100");
	public static final IntegerOption HEALTH_GAIN_FREQUENCY = new IntegerOption(Integer.valueOf(40),Integer.valueOf(40),Integer.valueOf(100),"Health Gain Frequency","Defines at what doriki intervals an extra +1 HP is gained\nDefault: 40");
	public static final DoubleOption DORIKI_REWARD_MULTIPLIER = new DoubleOption(Double.valueOf(1D),Double.valueOf(0D),Double.valueOf(10D),"Doriki Reward Multiplier","Multiplies any doriki gained from non-player kills by this amount (commands not included)\nDefault: 1");
	public static final DoubleOption BELLY_REWARD_MULTIPLIER = new DoubleOption(Double.valueOf(1D),Double.valueOf(0D),Double.valueOf(10D),"Belly Reward Multiplier","Multiplies any belly gained from non-player kills by this amount (commands not included)\nDefault: 1");
	public static final DoubleOption BOUNTY_REWARD_MULTIPLIER = new DoubleOption(Double.valueOf(1D),Double.valueOf(0D),Double.valueOf(10D),"Bounty Reward Multiplier","Multiplies any bounty gained from non-player kills by this amount (commands not included)\nDefault: 1");
	public static final DoubleOption HAKI_EXP_MULTIPLIER = new DoubleOption(Double.valueOf(1D),Double.valueOf(0D),Double.valueOf(10D),"Haki Exp Multiplier","Multiplies any haki gained from non-player kills by this amount (commands not included)\nDefault: 1");
	public static final DoubleOption LOYALTY_MULTIPLIER = new DoubleOption(Double.valueOf(1D),Double.valueOf(0D),Double.valueOf(10D),"Loyalty Multiplier","Multiplies any loyalty gained by this amount (commands not included)\nDefault: 1");
	public static final BooleanOption RACE_KEEP = new BooleanOption(Boolean.valueOf(true),"Keep Race",(String)(null));
	public static final BooleanOption FACTION_KEEP = new BooleanOption(Boolean.valueOf(true),"Keep Faction",(String)(null));
	public static final BooleanOption FIGHTING_STYLE_KEEP = new BooleanOption(Boolean.valueOf(true),"Keep Fighting Style",(String)(null));
	public static final BooleanOption DEVIL_FRUIT_KEEP = new BooleanOption(Boolean.valueOf(false),"Keep Devil Fruit",(String)(null));
	public static final IntegerOption DORIKI_KEEP_PERCENTAGE = new IntegerOption(Integer.valueOf(77),Integer.valueOf(0),Integer.valueOf(100),"Percentage of Doriki kept after death","Percentage of doriki to keep after death\nDefault: 33");
	public static final IntegerOption BOUNTY_KEEP_PERCENTAGE = new IntegerOption(Integer.valueOf(77),Integer.valueOf(0),Integer.valueOf(100),"Percentage of Bounty kept after death","Percentage of bounty to keep after death\nDefault: 33");
	public static final IntegerOption BELLY_KEEP_PERCENTAGE = new IntegerOption(Integer.valueOf(77),Integer.valueOf(0),Integer.valueOf(100),"Percentage of Belly kept after death","Percentage of belly to keep after death\nDefault: 33");
	public static final IntegerOption HAKI_EXP_KEEP_PERCENTAGE = new IntegerOption(Integer.valueOf(77),Integer.valueOf(0),Integer.valueOf(100),"Percentage of Haki Exp kept after death","Percentage of haki exp to keep after death\nDefault: 33");
	public static final IntegerOption LOYALTY_KEEP_PERCENTAGE = new IntegerOption(Integer.valueOf(50),Integer.valueOf(0),Integer.valueOf(100),"Percentage of Loyalty kept after death","Percentage of faction loyalty to keep after death\nDefault: 50");
	public static final DoubleOption TRAINING_POINTS_KEEP_PERCENTAGE = new DoubleOption(Double.valueOf(80D),Double.valueOf(0D),Double.valueOf(100D),"Percentage of Training Points kept after resets","Percentage of training points to keep after resets\nDefault: 80");
	public static final BooleanOption ENABLE_TRIALS = new BooleanOption(Boolean.valueOf(true),"Enable Trials","Allows quests to be accepted / completed\nDefault: true");
	public static final BooleanOption ENABLE_STYLES_PROGRESSION = new BooleanOption(Boolean.valueOf(true),"Enable Fighting Style Progression","Allows quests to reward players with abilities, otherwise all abilities will be unlocked from the beginning\nDefault: true");
}