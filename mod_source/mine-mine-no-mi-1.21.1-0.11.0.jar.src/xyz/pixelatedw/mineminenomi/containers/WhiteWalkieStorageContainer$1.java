package xyz.pixelatedw.mineminenomi.containers;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class WhiteWalkieStorageContainer$1 extends Slot {
	// Fields
	final WhiteWalkieStorageContainer this$0;

	WhiteWalkieStorageContainer$1(WhiteWalkieStorageContainer var0, Container var1, int var2, int var3, int var4) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: constructor prefix has escaping local or control flow */ }
	public boolean mayPlace(ItemStack var1) {
		return ((var1.getItem()) == (Items.SADDLE)) && ((!(this.hasItem())) && (this.this$0.whiteWalkie.isTame()));
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean isActive() {
		return this.this$0.whiteWalkie.isTame();
	}
}