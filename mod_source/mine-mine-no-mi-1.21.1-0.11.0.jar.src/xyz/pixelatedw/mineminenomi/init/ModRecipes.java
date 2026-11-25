package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.crafting.MultiChannelDyeRecipe;
import xyz.pixelatedw.mineminenomi.api.crafting.SmithingDialRecipe;
import xyz.pixelatedw.mineminenomi.api.crafting.SmithingDialRecipe$Serializer;
import xyz.pixelatedw.mineminenomi.api.crafting.SmithingEnchantmentRecipe;
import xyz.pixelatedw.mineminenomi.api.crafting.SmithingEnchantmentRecipe$Serializer;

public class ModRecipes {
	// Fields
	public static final Supplier<RecipeSerializer<MultiChannelDyeRecipe>> MULTI_CHANNEL_ARMOR_DYE;
	public static final Supplier<RecipeSerializer<SmithingDialRecipe>> SMITHING_DIAL;
	public static final Supplier<RecipeSerializer<SmithingEnchantmentRecipe>> SMITHING_ENCHANTMENT;

	public static void init() {
	}
	static  {
		Supplier<RecipeSerializer> var0 = () -> {
			return new SimpleCraftingRecipeSerializer((SimpleCraftingRecipeSerializer.Factory)(((net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer.Factory)(MultiChannelDyeRecipe::new))));
		};
		MULTI_CHANNEL_ARMOR_DYE = ModRegistry.registerRecipeSerializer("crafting_special_multi_channel_dye",var0);
		SMITHING_DIAL = ModRegistry.registerRecipeSerializer("smithing_dial",(Supplier)(((Supplier<RecipeSerializer>)(SmithingDialRecipe$Serializer::new))));
		SMITHING_ENCHANTMENT = ModRegistry.registerRecipeSerializer("smithing_enchantment",(Supplier)(((Supplier<RecipeSerializer>)(SmithingEnchantmentRecipe$Serializer::new))));
	}
}