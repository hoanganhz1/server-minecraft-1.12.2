package xyz.pixelatedw.mineminenomi.containers;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;

public class WhiteWalkieStorageContainer$2 extends Slot {
	// Fields
	final WhiteWalkieEntity val$whiteWalkie;

	WhiteWalkieStorageContainer$2(WhiteWalkieStorageContainer var0, Container var1, int var2, int var3, int var4, WhiteWalkieEntity var5) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: constructor prefix has escaping local or control flow */ }
	public boolean mayPlace(ItemStack var1) {
		return ((var1.getItem()) == (Items.SADDLE)) && ((!(this.hasItem())) && (this.val$whiteWalkie.isTame()));
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean isActive() {
		return this.val$whiteWalkie.isTame();
	}
}