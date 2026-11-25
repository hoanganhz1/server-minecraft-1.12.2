package xyz.pixelatedw.mineminenomi.api.crafting;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.Enchantment;

public class SmithingEnchantmentRecipeBuilder {
	// Fields
	private final Ingredient base;
	private final Ingredient addition;
	private final int additionAmount;
	private final RecipeCategory category;
	private final Holder<Enchantment> enchantment;
	private final int enchantmentMaxLevel;
	private final Map<String, Criterion<?>> criteria;

	public SmithingEnchantmentRecipeBuilder(RecipeCategory var1, Ingredient var2, Ingredient var3, int var4, Holder<Enchantment> var5, int var6) {
		this.criteria = new LinkedHashMap();
		this.category = var1;
		this.base = var2;
		this.addition = var3;
		this.additionAmount = var4;
		this.enchantment = var5;
		this.enchantmentMaxLevel = var6;
	}
	public static SmithingEnchantmentRecipeBuilder smithing(Ingredient var0, Ingredient var1, int var2, RecipeCategory var3, Holder<Enchantment> var4, int var5) {
		return new SmithingEnchantmentRecipeBuilder(var3,var0,var1,var2,var4,var5);
	}
	public SmithingEnchantmentRecipeBuilder unlocks(String var1, Criterion<?> var2) {
		this.criteria.put(var1,var2);
		return this;
	}
	public void save(RecipeOutput var1, ResourceLocation var2) {
		this.ensureValid(var2);
		Advancement.Builder var3 = var1.advancement().parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe",RecipeUnlockedTrigger.unlocked(var2)).rewards(AdvancementRewards.Builder.recipe(var2)).requirements(AdvancementRequirements.Strategy.OR);
		Objects.requireNonNull(var3);
		this.criteria.forEach((BiConsumer)(((BiConsumer<String, Criterion>)(var3::addCriterion))));
		SmithingEnchantmentRecipe var4 = new SmithingEnchantmentRecipe(this.base,this.addition,this.additionAmount,this.enchantment,this.enchantmentMaxLevel);
		String var5 = this.category.getFolderName();
		String var6 = "recipes/" + var5 + "/";
		var1.accept(var2,(Recipe)(var4),var3.build(var2.withPrefix(var6)));
	}
	private void ensureValid(ResourceLocation var1) {
		if (this.criteria.isEmpty()){
			String var2 = String.valueOf((Object)(var1));
			String var3 = "No way of obtaining recipe " + var2;
			throw new IllegalStateException((String)(var3));
		}else{
			return;
		}
	}
}