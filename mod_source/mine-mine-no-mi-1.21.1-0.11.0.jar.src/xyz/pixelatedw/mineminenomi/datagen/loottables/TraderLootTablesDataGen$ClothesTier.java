package xyz.pixelatedw.mineminenomi.datagen.loottables;

public enum TraderLootTablesDataGen$ClothesTier {
	// Fields
	TRASH(100, 50F, 100F),
	NAMED(30, 2000F, 3000F),
	CAPE(50, 4000F, 5000F),
	RARE(20, 5000F, 7000F);
	private int weight;
	private float minPrice;
	private float maxPrice;

	private TraderLootTablesDataGen$ClothesTier(int var3, float var4, float var5) {
		this.weight = var3;
		this.minPrice = var4;
		this.maxPrice = var5;
	}
	public int getWeight() {
		return this.weight;
	}
	public float getMinPrice() {
		return this.minPrice;
	}
	public float getMaxPrice() {
		return this.maxPrice;
	}
}