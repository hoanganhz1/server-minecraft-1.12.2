package xyz.pixelatedw.mineminenomi.api.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModRecipes;

public class MultiChannelDyeRecipe extends CustomRecipe {
	// Fields
	private int layer;

	public MultiChannelDyeRecipe(CraftingBookCategory var1) {
		super(var1);
		this.layer = 0;
	}
	public boolean matches(CraftingInput var1, Level var2) {
		ItemStack var3 = ItemStack.EMPTY;
		ArrayList var4 = Lists.newArrayList();
		int var5 = 0;
		do{
			if ((var5) < (var1.size())){
				ItemStack var6 = var1.getItem(var5);
				if (!(var6.isEmpty())){
					if ((((MultiChannelItemColor)(var6.get(ModDataComponents.MULTI_CHANNEL_DYES)))) != (null)){
						if (!(var3.isEmpty())){
							return false;
						}else{
							var3 = var6;
						}
					}else{
						if (!(var6.getItem() instanceof DyeItem)){
							return false;
						}else{
							var4.add(var6);
						}
					}
				}
				var5++;
				continue;
			}else{
				break;
			}
		} while (true);
		return (!(var3.isEmpty())) && (!(((java.util.List)(var4)).isEmpty()));
	}
	public ItemStack assemble(CraftingInput var1, HolderLookup.Provider var2) {
		ArrayList var3 = Lists.newArrayList();
		ItemStack var4 = ItemStack.EMPTY;
		int var5 = 0;
		do{
			if ((var5) < (var1.size())){
				ItemStack var6 = var1.getItem(var5);
				if (!(var6.isEmpty())){
					Item var7 = var6.getItem();
					MultiChannelItemColor var8 = ((MultiChannelItemColor)(var6.get(ModDataComponents.MULTI_CHANNEL_DYES)));
					if ((var8) != (null)){
						this.layer = Math.min(this.layer,(var8.colors().length) - (1));
						if (!(var4.isEmpty())){
							return ItemStack.EMPTY;
						}else{
							var4 = var6.copy();
						}
					}else{
						if (!(var7 instanceof DyeItem)){
							return ItemStack.EMPTY;
						}else{
							var3.add(((DyeItem)(var7)));
						}
					}
				}
				var5++;
				continue;
			}else{
				break;
			}
		} while (true);
		return ((!(var4.isEmpty())) && (!(((java.util.List)(var3)).isEmpty()))) ? (MultiChannelItemColor.dyeArmor(var4,this.layer,(List)(var3))) : (ItemStack.EMPTY);
	}
	public boolean canCraftInDimensions(int var1, int var2) {
		return ((var1) * (var2)) >= (2);
	}
	public RecipeSerializer<?> getSerializer() {
		return (RecipeSerializer<?>) (RecipeSerializer) (((RecipeSerializer)(ModRecipes.MULTI_CHANNEL_ARMOR_DYE.get())));
	}
	public void setLayer(int var1) {
		this.layer = var1;
	}
	public int getLayer() {
		return this.layer;
	}
}