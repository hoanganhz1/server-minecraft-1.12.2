package xyz.pixelatedw.mineminenomi.mixins;

import javax.annotation.Nullable;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.events.SmithingTableEvent;

@Mixin(value={SmithingMenu.class})
public abstract class SmithingMenuMixin extends ItemCombinerMenu {
	// Fields
	@Nullable() @Shadow() private RecipeHolder<SmithingRecipe> selectedRecipe;

	public SmithingMenuMixin(MenuType<?> var1, int var2, Inventory var3, ContainerLevelAccess var4) {
		super(var1,var2,var3,var4);
	}
	@Inject(method={"onTake"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$onTake(Player var1, ItemStack var2, CallbackInfo var3) {
		NeoForge.EVENT_BUS.post((Event)(new SmithingTableEvent(this.inputSlots,this.player,this.selectedRecipe,this.inputSlots.getItem(1),this.inputSlots.getItem(2))));
	}
}