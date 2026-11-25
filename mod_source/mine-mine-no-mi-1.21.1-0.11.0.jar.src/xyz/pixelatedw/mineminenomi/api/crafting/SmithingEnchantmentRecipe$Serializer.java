package xyz.pixelatedw.mineminenomi.api.crafting;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;

public class SmithingEnchantmentRecipe$Serializer implements RecipeSerializer<SmithingEnchantmentRecipe> {
	// Fields
	private static final MapCodec<SmithingEnchantmentRecipe> CODEC;
	public static final StreamCodec<RegistryFriendlyByteBuf, SmithingEnchantmentRecipe> STREAM_CODEC;

	public MapCodec<SmithingEnchantmentRecipe> codec() {
		return CODEC;
	}
	public StreamCodec<RegistryFriendlyByteBuf, SmithingEnchantmentRecipe> streamCodec() {
		return STREAM_CODEC;
	}
	private static SmithingEnchantmentRecipe fromNetwork(RegistryFriendlyByteBuf var0) {
		Ingredient var1 = ((Ingredient)(Ingredient.CONTENTS_STREAM_CODEC.decode(var0)));
		Ingredient var2 = ((Ingredient)(Ingredient.CONTENTS_STREAM_CODEC.decode(var0)));
		int var3 = var0.readInt();
		Holder var4 = ((Holder)(Enchantment.STREAM_CODEC.decode(var0)));
		int var5 = var0.readInt();
		return new SmithingEnchantmentRecipe(var1,var2,var3,var4,var5);
	}
	private static void toNetwork(RegistryFriendlyByteBuf var0, SmithingEnchantmentRecipe var1) {
		Ingredient.CONTENTS_STREAM_CODEC.encode(var0,var1.base);
		Ingredient.CONTENTS_STREAM_CODEC.encode(var0,var1.addition);
		var0.writeInt(var1.additionAmount);
		Enchantment.STREAM_CODEC.encode(var0,var1.enchantment);
		var0.writeInt(var1.enchantmentMaxLevel);
	}
	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}