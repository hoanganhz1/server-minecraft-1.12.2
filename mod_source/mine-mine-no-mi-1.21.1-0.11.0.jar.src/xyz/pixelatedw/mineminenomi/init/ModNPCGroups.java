package xyz.pixelatedw.mineminenomi.init;

import java.time.Instant;
import java.util.UUID;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;

public class ModNPCGroups {
	// Fields
	public static final String CAPTAIN_MARKER = "CaptainMarker";
	public static final String UNGROUPED;
	public static final String MARINES;
	public static final Crew WORLD_PIRATES;
	private static final UUID BUGGY_PIRATES_ID;
	private static final String BUGGY_PIRATES_NAME;
	public static final Crew BUGGY_PIRATES;
	private static final UUID BLACK_CAT_PIRATES_ID;
	private static final String BLACK_CAT_PIRATES_NAME;
	public static final Crew BLACK_CAT_PIRATES;
	private static final UUID KRIEG_PIRATES_ID;
	private static final String KRIEG_PIRATES_NAME;
	public static final Crew KRIEG_PIRATES;
	private static final UUID ARLONG_PIRATES_ID;
	private static final String ARLONG_PIRATES_NAME;
	public static final Crew ARLONG_PIRATES;
	private static final UUID BAROQUE_WORKS_ID;
	private static final String BAROQUE_WORKS_NAME;
	public static final Crew BAROQUE_WORKS;

	static  {
		UNGROUPED = ModRegistry.registerName("challenge.category.ungrouped","Ungrouped");
		MARINES = ModRegistry.registerName("challenge.category.marines","Marines");
		WORLD_PIRATES = new Crew("Open World Pirates",UUID.fromString("5d32205c-d50b-453a-9b00-25f8a7f3d4a7"),"CaptainMarker",Instant.now().getEpochSecond());
		BUGGY_PIRATES_ID = UUID.fromString("0d565a0b-0daf-4465-b9e9-bb2becb2a45a");
		BUGGY_PIRATES_NAME = ModRegistry.registerName("crew.name.buggy_pirates","Buggy Pirates");
		BUGGY_PIRATES = new Crew(BUGGY_PIRATES_NAME,BUGGY_PIRATES_ID,"CaptainMarker",Instant.now().getEpochSecond());
		BLACK_CAT_PIRATES_ID = UUID.fromString("d9d66778-a3ff-4f31-be73-0c719fc8d8d2");
		BLACK_CAT_PIRATES_NAME = ModRegistry.registerName("crew.name.black_cat_pirates","Black Cat Pirates");
		BLACK_CAT_PIRATES = new Crew(BLACK_CAT_PIRATES_NAME,BLACK_CAT_PIRATES_ID,"CaptainMarker",Instant.now().getEpochSecond());
		KRIEG_PIRATES_ID = UUID.fromString("750f8af7-417a-47d7-b267-148259e7bec8");
		KRIEG_PIRATES_NAME = ModRegistry.registerName("crew.name.krieg_pirates","Krieg Pirates");
		KRIEG_PIRATES = new Crew(KRIEG_PIRATES_NAME,KRIEG_PIRATES_ID,"CaptainMarker",Instant.now().getEpochSecond());
		ARLONG_PIRATES_ID = UUID.fromString("5971ee94-829b-472d-b5dc-31cec6deb294");
		ARLONG_PIRATES_NAME = ModRegistry.registerName("crew.name.arlong_pirates","Arlong Pirates");
		ARLONG_PIRATES = new Crew(ARLONG_PIRATES_NAME,ARLONG_PIRATES_ID,"CaptainMarker",Instant.now().getEpochSecond());
		BAROQUE_WORKS_ID = UUID.fromString("0c2a61f0-f2d6-47ac-931a-aadf5241b24d");
		BAROQUE_WORKS_NAME = ModRegistry.registerName("crew.name.baroque_works","Baroque Works");
		BAROQUE_WORKS = new Crew(BAROQUE_WORKS_NAME,BAROQUE_WORKS_ID,"CaptainMarker",Instant.now().getEpochSecond());
	}
}