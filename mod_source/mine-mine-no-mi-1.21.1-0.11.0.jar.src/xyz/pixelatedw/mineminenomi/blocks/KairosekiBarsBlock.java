package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class KairosekiBarsBlock extends IronBarsBlock {
	public KairosekiBarsBlock() {
		super(BlockBehaviour.Properties.of().strength(5F,6F));
	}
}