package xyz.pixelatedw.mineminenomi.api.abilities;

public enum AbilityStat$AbilityStatType {
	// Fields
	NEUTRAL(""),
	BUFF("§a"),
	DEBUFF("§c");
	private String color;

	private AbilityStat$AbilityStatType(String var3) {
		this.color = var3;
	}
	public String getColorCode() {
		return this.color;
	}
	public static AbilityStat$AbilityStatType from(float var0, boolean var1) {
		if (var1){
			return ((var0) > (0F)) ? (BUFF) : (((var0) < (0F)) ? (DEBUFF) : (NEUTRAL));
		}else{
			return ((var0) < (0F)) ? (BUFF) : (((var0) > (0F)) ? (DEBUFF) : (NEUTRAL));
		}
	}
}