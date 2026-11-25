package xyz.pixelatedw.mineminenomi.api.crafting;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SmithingDialRecipe$Serializer implements RecipeSerializer<SmithingDialRecipe> {
	// Fields
	private static final MapCodec<SmithingDialRecipe> CODEC;
	public static final StreamCodec<RegistryFriendlyByteBuf, SmithingDialRecipe> STREAM_CODEC;

	public MapCodec<SmithingDialRecipe> codec() {
		return CODEC;
	}
	public StreamCodec<RegistryFriendlyByteBuf, SmithingDialRecipe> streamCodec() {
		return STREAM_CODEC;
	}
	private static SmithingDialRecipe fromNetwork(RegistryFriendlyByteBuf var0) {
		Ingredient var1 = ((Ingredient)(Ingredient.CONTENTS_STREAM_CODEC.decode(var0)));
		Ingredient var2 = ((Ingredient)(Ingredient.CONTENTS_STREAM_CODEC.decode(var0)));
		int var3 = var0.readInt();
		ItemStack var4 = ((ItemStack)(ItemStack.STREAM_CODEC.decode(var0)));
		return new SmithingDialRecipe(var1,var2,var3,var4);
	}
	private static void toNetwork(RegistryFriendlyByteBuf var0, SmithingDialRecipe var1) {
		Ingredient.CONTENTS_STREAM_CODEC.encode(var0,var1.base);
		Ingredient.CONTENTS_STREAM_CODEC.encode(var0,var1.addition);
		var0.writeInt(var1.additionAmount);
		ItemStack.STREAM_CODEC.encode(var0,var1.result);
	}
	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}