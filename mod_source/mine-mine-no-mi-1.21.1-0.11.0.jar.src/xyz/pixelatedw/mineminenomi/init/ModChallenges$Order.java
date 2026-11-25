package xyz.pixelatedw.mineminenomi.init;

import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;

public class ModChallenges$Order {
	// Fields
	private static int idx;
	public static final int ALVIDA;
	public static final int HIGUMA;
	public static final int MORGAN;
	public static final int MOHJI_RICHIE;
	public static final int CABAJI;
	public static final int BUGGY;
	public static final int SHAM_BUCHI;
	public static final int JANGO;
	public static final int KURO;
	public static final int PEARL;
	public static final int GIN;
	public static final int DON_KRIEG;
	public static final int CHEW;
	public static final int KUROOBI;
	public static final int HATCHAN;
	public static final int ARLONG;
	public static final int MR_5_MISS_VALENTINE;
	public static final int MR_4_MISS_MERRY_CHRISTMAS;
	public static final int MR_3;
	public static final int MR_2;
	public static final int MISS_DOUBLEFINGER;
	public static final int MR_1;
	public static final int MR_0;

	private static int orderIndex() {
		int var0 = idx;
		int var1 = var0;
		String var2 = "Challenges Order Index: " + var1;
		WyDebug.debug(var2);
		idx = (idx) + (ChallengeDifficulty.values().length);
		return var0;
	}
	static  {
		idx = 0;
		ALVIDA = orderIndex();
		HIGUMA = orderIndex();
		MORGAN = orderIndex();
		MOHJI_RICHIE = orderIndex();
		CABAJI = orderIndex();
		BUGGY = orderIndex();
		SHAM_BUCHI = orderIndex();
		JANGO = orderIndex();
		KURO = orderIndex();
		PEARL = orderIndex();
		GIN = orderIndex();
		DON_KRIEG = orderIndex();
		CHEW = orderIndex();
		KUROOBI = orderIndex();
		HATCHAN = orderIndex();
		ARLONG = orderIndex();
		MR_5_MISS_VALENTINE = orderIndex();
		MR_4_MISS_MERRY_CHRISTMAS = orderIndex();
		MR_3 = orderIndex();
		MR_2 = orderIndex();
		MISS_DOUBLEFINGER = orderIndex();
		MR_1 = orderIndex();
		MR_0 = orderIndex();
	}
}