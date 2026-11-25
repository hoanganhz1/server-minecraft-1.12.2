package xyz.pixelatedw.mineminenomi.api.enums;

import net.minecraft.ChatFormatting;

public enum FruitType {
	// Fields
	PARAMECIA(ChatFormatting.RED, "Paramecia"),
	LOGIA(ChatFormatting.YELLOW, "Logia"),
	ZOAN(ChatFormatting.GREEN, "Zoan"),
	MYTHICAL_ZOAN(ChatFormatting.AQUA, "Mythical Zoan"),
	ANCIENT_ZOAN(ChatFormatting.DARK_GREEN, "Ancient Zoan"),
	ARTIFICIAL_LOGIA(ChatFormatting.GOLD, "Artificial Logia"),
	ARTIFICIAL_PARAMECIA(ChatFormatting.GOLD, "Artificial Paramecia"),
	ARTIFICIAL_ZOAN(ChatFormatting.GOLD, "Artificial Zoan");
	private ChatFormatting color;
	private String name;

	private FruitType(ChatFormatting var3, String var4) {
		this.color = var3;
		this.name = var4;
	}
	public ChatFormatting getColor() {
		return this.color;
	}
	public String getName() {
		return this.name;
	}
}