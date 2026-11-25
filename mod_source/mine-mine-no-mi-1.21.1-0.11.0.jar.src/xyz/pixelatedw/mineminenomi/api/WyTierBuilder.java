package xyz.pixelatedw.mineminenomi.api;

import java.util.function.Supplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.SimpleTier;

public class WyTierBuilder {
	// Fields
	private TagKey<Block> incorrectBlockForDrops;
	private final int uses;
	private float speed;
	private float attackDamageBonus;
	private int enchantmentValue;
	private final Supplier<Ingredient> repairIngredient;

	public static Tier trash(int var0, Supplier<Ingredient> var1) {
		return new WyTierBuilder(var0,var1).setBlockTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL).setEnchantmentValue(10).build();
	}
	public static Tier uncommon(int var0, Supplier<Ingredient> var1) {
		return new WyTierBuilder(var0,var1).setBlockTag(BlockTags.INCORRECT_FOR_STONE_TOOL).setEnchantmentValue(14).build();
	}
	public static Tier rare(int var0, Supplier<Ingredient> var1) {
		return new WyTierBuilder(var0,var1).setBlockTag(BlockTags.INCORRECT_FOR_IRON_TOOL).setEnchantmentValue(16).build();
	}
	public static Tier epic(int var0, Supplier<Ingredient> var1) {
		return new WyTierBuilder(var0,var1).setBlockTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL).setEnchantmentValue(20).build();
	}
	public static Tier legendary(int var0, Supplier<Ingredient> var1) {
		return new WyTierBuilder(var0,var1).setBlockTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL).setEnchantmentValue(22).build();
	}
	public WyTierBuilder(int var1, Supplier<Ingredient> var2) {
		this.speed = 0F;
		this.attackDamageBonus = 0F;
		this.enchantmentValue = 14;
		this.uses = var1;
		this.repairIngredient = var2;
	}
	public WyTierBuilder setMiningSpeed(float var1) {
		this.speed = var1;
		return this;
	}
	public WyTierBuilder setEnchantmentValue(int var1) {
		this.enchantmentValue = var1;
		return this;
	}
	public WyTierBuilder setAttackDamageBonus(int var1) {
		this.attackDamageBonus = (float)(var1);
		return this;
	}
	public WyTierBuilder setBlockTag(TagKey<Block> var1) {
		this.incorrectBlockForDrops = var1;
		return this;
	}
	public Tier build() {
		return new SimpleTier(this.incorrectBlockForDrops,this.uses,this.speed,this.attackDamageBonus,this.enchantmentValue,this.repairIngredient);
	}
}