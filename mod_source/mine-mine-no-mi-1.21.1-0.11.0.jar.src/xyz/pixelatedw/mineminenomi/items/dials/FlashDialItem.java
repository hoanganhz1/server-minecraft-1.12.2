package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class FlashDialItem extends BlockItem {
	public FlashDialItem(Block var1) {
		super(var1,new Item.Properties().stacksTo(16));
	}
}