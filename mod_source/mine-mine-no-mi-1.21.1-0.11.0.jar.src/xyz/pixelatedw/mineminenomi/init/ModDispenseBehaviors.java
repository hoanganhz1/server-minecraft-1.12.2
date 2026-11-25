package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DispenserBlock;

public class ModDispenseBehaviors {
	public static void init() {
		DispenserBlock.registerProjectileBehavior(((ItemLike)(ModItems.ROPE_NET.get())));
		DispenserBlock.registerProjectileBehavior(((ItemLike)(ModItems.KAIROSEKI_NET.get())));
	}
}