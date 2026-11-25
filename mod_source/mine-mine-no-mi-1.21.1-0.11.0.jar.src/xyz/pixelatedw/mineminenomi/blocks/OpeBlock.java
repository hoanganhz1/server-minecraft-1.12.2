package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class OpeBlock extends Block {
	public OpeBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(3.4028235e+38F).noOcclusion().noCollission().noLootTable());
	}
	public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
		return true;
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean skipRendering(BlockState var1, BlockState var2, Direction var3) {
		return (var2.getBlock()) == (this);
	}
	public int getLightBlock(BlockState var1, BlockGetter var2, BlockPos var3) {
		return 0;
	}
}