package xyz.pixelatedw.mineminenomi.mixins;

import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.handlers.world.CraftingHandler;

@Mixin(value={CraftingMenu.class})
public class CraftingMenuMixin {
	@Inject(method={"slotChangedCraftingGrid"}, at={@At(value="HEAD")}, cancellable=true)
	private static void mineminenomi$slotChangedCraftingGrid(AbstractContainerMenu var0, Level var1, Player var2, CraftingContainer var3, ResultContainer var4, @Nullable() RecipeHolder<CraftingRecipe> var5, CallbackInfo var6) {
		if (CraftingHandler.onGridChanged(var0,var1,var2,var3,var4,var5)){
			var6.cancel();
			return;
		}else{
			return;
		}
	}
}