package xyz.pixelatedw.mineminenomi.api.abilities.components;

public class HitTriggerComponent$EventReduce {
	// Fields
	public int fails;
	public int hits;

	HitTriggerComponent$EventReduce() {
	}
	public String toString() {
		int var1 = this.fails;
		int var2 = this.hits;
		String var3 = "fails: " + var1 + ", hits: " + var2;
		return var3;
	}
}