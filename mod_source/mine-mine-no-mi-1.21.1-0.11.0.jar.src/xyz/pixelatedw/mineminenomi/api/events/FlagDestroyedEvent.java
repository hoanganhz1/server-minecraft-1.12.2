package xyz.pixelatedw.mineminenomi.api.events;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;

public class FlagDestroyedEvent extends Event {
	// Fields
	@Nullable() private Entity destroyer;
	private BlockState state;
	private BlockPos pos;
	private FlagBlockEntity flagTile;

	public FlagDestroyedEvent(BlockState var1, BlockPos var2, @Nullable() Entity var3, FlagBlockEntity var4) {
		this.destroyer = var3;
		this.state = var1;
		this.pos = var2;
		this.flagTile = var4;
	}
	@Nullable()
	public Entity getDestroyer() {
		return this.destroyer;
	}
	public BlockState getState() {
		return this.state;
	}
	public BlockPos getPos() {
		return this.pos;
	}
	public FlagBlockEntity getTile() {
		return this.flagTile;
	}
}