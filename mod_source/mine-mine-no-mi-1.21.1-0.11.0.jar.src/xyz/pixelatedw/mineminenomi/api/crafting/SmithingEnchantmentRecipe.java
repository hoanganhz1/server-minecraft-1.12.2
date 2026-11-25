package xyz.pixelatedw.mineminenomi.api.crafting;

import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModRecipes;

public class SmithingEnchantmentRecipe implements SmithingRecipe {
	// Fields
	final Ingredient base;
	final Ingredient addition;
	final int additionAmount;
	final Holder<Enchantment> enchantment;
	final int enchantmentMaxLevel;

	public SmithingEnchantmentRecipe(Ingredient var1, Ingredient var2, int var3, Holder<Enchantment> var4, int var5) {
		this.base = var1;
		this.addition = var2;
		this.additionAmount = var3;
		this.enchantment = var4;
		this.enchantmentMaxLevel = var5;
	}
	public boolean matches(SmithingRecipeInput var1, Level var2) {
		return (this.base.test(var1.getItem(1))) && ((this.addition.test(var1.getItem(2))) && ((var1.getItem(2).getCount()) >= (this.additionAmount)));
	}
	public ItemStack assemble(SmithingRecipeInput var1, HolderLookup.Provider var2) {
		ItemStack var3 = var1.getItem(1).copy();
		if ((var3.getEnchantmentLevel(this.enchantment)) > (0)){
			return var3;
		}else{
			int var4 = Math.min((int)(Math.floor((double)((var1.getItem(2).getCount()) / (this.additionAmount)))),this.enchantmentMaxLevel);
			var3.enchant(this.enchantment,var4);
			return var3;
		}
	}
	public ItemStack getResultItem(HolderLookup.Provider var1) {
		return ItemStack.EMPTY;
	}
	public boolean isTemplateIngredient(ItemStack var1) {
		return false;
	}
	public boolean isBaseIngredient(ItemStack var1) {
		return (this.base.test(var1)) && ((var1.getEnchantmentLevel(this.enchantment)) <= (0));
	}
	public boolean isAdditionIngredient(ItemStack var1) {
		return (this.addition.test(var1)) && ((var1.getCount()) >= (this.additionAmount));
	}
	public int getAdditionalAmount(Container var1) {
		return (Math.min((int)(Math.floor((double)((var1.getItem(2).getCount()) / (this.additionAmount)))),this.enchantmentMaxLevel)) * (this.additionAmount);
	}
	public RecipeSerializer<?> getSerializer() {
		return (RecipeSerializer<?>) (RecipeSerializer) (((RecipeSerializer)(ModRecipes.SMITHING_ENCHANTMENT.get())));
	}
	public boolean isIncomplete() {
		Ingredient[] var1 = new Ingredient[]{this.base,this.addition};
		return Stream.of(var1).anyMatch((Predicate)(((Predicate<Ingredient>)(Ingredient::hasNoItems))));
	}
}