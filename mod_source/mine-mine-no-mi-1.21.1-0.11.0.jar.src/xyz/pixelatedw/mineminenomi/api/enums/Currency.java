package xyz.pixelatedw.mineminenomi.api.enums;

public enum Currency {
	// Fields
	BELLY,
	EXTOL;
	public static final long BELLY_TO_EXTOL = 10000L;

	private Currency() {
	}
	public static long getExtolFromBelly(long var0) {
		return (var0) * (10000L);
	}
	public static long getBellyFromExtol(long var0) {
		if ((var0) < (10000L)){
			return -1L;
		}else{
			return (var0) / (10000L);
		}
	}
}