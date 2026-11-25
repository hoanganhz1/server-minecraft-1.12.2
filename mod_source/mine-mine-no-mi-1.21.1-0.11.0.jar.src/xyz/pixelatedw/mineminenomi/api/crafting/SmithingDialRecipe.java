package xyz.pixelatedw.mineminenomi.api.crafting;

import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModRecipes;

public class SmithingDialRecipe implements SmithingRecipe {
	// Fields
	final Ingredient base;
	final Ingredient addition;
	final int additionAmount;
	final ItemStack result;

	public SmithingDialRecipe(Ingredient var1, Ingredient var2, int var3, ItemStack var4) {
		this.base = var1;
		this.addition = var2;
		this.additionAmount = var3;
		this.result = var4;
	}
	public boolean matches(SmithingRecipeInput var1, Level var2) {
		return (this.base.test(var1.getItem(1))) && ((this.addition.test(var1.getItem(2))) && ((var1.getItem(2).getCount()) >= (this.additionAmount)));
	}
	public ItemStack assemble(SmithingRecipeInput var1, HolderLookup.Provider var2) {
		return this.result.copy();
	}
	public ItemStack getResultItem(HolderLookup.Provider var1) {
		return this.result;
	}
	public boolean isTemplateIngredient(ItemStack var1) {
		return false;
	}
	public boolean isBaseIngredient(ItemStack var1) {
		return this.base.test(var1);
	}
	public boolean isAdditionIngredient(ItemStack var1) {
		return (this.addition.test(var1)) && ((var1.getCount()) >= (this.additionAmount));
	}
	public int getAdditionalAmount() {
		return this.additionAmount;
	}
	public RecipeSerializer<?> getSerializer() {
		return (RecipeSerializer<?>) (RecipeSerializer) (((RecipeSerializer)(ModRecipes.SMITHING_DIAL.get())));
	}
	public boolean isIncomplete() {
		Ingredient[] var1 = new Ingredient[]{this.base,this.addition};
		return Stream.of(var1).anyMatch((Predicate)(((Predicate<Ingredient>)(Ingredient::hasNoItems))));
	}
}