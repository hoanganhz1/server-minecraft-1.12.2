package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class KujaBowItem extends BowItem {
	// Fields
	private static final int DEFAULT_BOW_COLOR = 1349907;

	public KujaBowItem() { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: constructor prefix has escaping local or control flow */ }
	public AbstractArrow customArrow(AbstractArrow var1, ItemStack var2, ItemStack var3) {
		var1.setBaseDamage((var1.getBaseDamage()) * (2D));
		return var1;
	}
	public int getDefaultProjectileRange() {
		return 32;
	}
}