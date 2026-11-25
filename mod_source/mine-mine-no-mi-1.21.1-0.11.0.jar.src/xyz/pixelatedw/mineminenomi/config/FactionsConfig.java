package xyz.pixelatedw.mineminenomi.config;

import xyz.pixelatedw.mineminenomi.api.config.options.BooleanOption;
import xyz.pixelatedw.mineminenomi.api.config.options.IntegerOption;

public class FactionsConfig {
	// Fields
	public static final IntegerOption TIME_BETWEEN_PACKAGE_DROPS = new IntegerOption(Integer.valueOf(15),Integer.valueOf(0),Integer.valueOf(60),"Time Between Package Drops","Time (in minutes) it takes for another package to drop\n0 means no package will spawn\nDefault: 15 (minutes)");
	public static final IntegerOption CREW_BOUNTY_REQUIREMENT = new IntegerOption(Integer.valueOf(0),Integer.valueOf(0),Integer.valueOf(100000),"Bounty Requirement","Bounty Requirement for creating a crew; 0 means no requirement\nDefault: 0");
	public static final BooleanOption WORLD_MESSAGE_ON_CREW_CREATE = new BooleanOption(Boolean.valueOf(false),"World Message on Crew creations","Sends a message to all players when a new crew gets formed\nDefault: false");
	public static final BooleanOption DISABLE_FRIENDLY_FIRE = new BooleanOption(Boolean.valueOf(true),"Disable Friendly Damage","Disabled the friendly damage between crewmates\nDefault: true");
}