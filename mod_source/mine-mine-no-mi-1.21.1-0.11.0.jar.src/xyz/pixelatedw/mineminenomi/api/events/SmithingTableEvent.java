package xyz.pixelatedw.mineminenomi.api.events;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.neoforged.bus.api.Event;
import org.jetbrains.annotations.Nullable;

public class SmithingTableEvent extends Event {
	// Fields
	private Player player;
	private RecipeHolder<SmithingRecipe> recipe;
	private Container container;
	private ItemStack baseSlot;
	private ItemStack additionSlot;
	private ItemStack resultSlot;
	private int baseQuantity;

	public SmithingTableEvent(Container var1, Player var2, RecipeHolder<SmithingRecipe> var3, @Nullable() ItemStack var4, @Nullable() ItemStack var5) {
		this.baseSlot = ItemStack.EMPTY;
		this.additionSlot = ItemStack.EMPTY;
		this.resultSlot = ItemStack.EMPTY;
		this.container = var1;
		this.player = var2;
		this.recipe = var3;
		this.baseSlot = ((var4) != (null)) ? (var4) : (ItemStack.EMPTY);
		this.additionSlot = ((var5) != (null)) ? (var5) : (ItemStack.EMPTY);
	}
	public Container getContainer() {
		return this.container;
	}
	public RecipeHolder<SmithingRecipe> getRecipe() {
		return this.recipe;
	}
	public Player getPlayer() {
		return this.player;
	}
	public ItemStack getBaseSlot() {
		return this.baseSlot;
	}
	public ItemStack getAdditionSlot() {
		return this.additionSlot;
	}
	public ItemStack getResultSlot() {
		return this.resultSlot;
	}
	public int getBaseQuantity() {
		return this.baseQuantity;
	}
	public void setBaseSlot(@Nullable() ItemStack var1) {
		this.baseSlot = var1;
	}
	public void setAdditionSlot(@Nullable() ItemStack var1) {
		this.additionSlot = var1;
	}
}