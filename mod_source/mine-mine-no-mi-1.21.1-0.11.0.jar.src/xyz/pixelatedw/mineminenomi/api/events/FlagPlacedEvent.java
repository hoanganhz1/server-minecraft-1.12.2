package xyz.pixelatedw.mineminenomi.api.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;

public class FlagPlacedEvent extends Event implements ICancellableEvent {
	// Fields
	private Player placer;
	private BlockState state;
	private BlockPos pos;
	private FlagBlockEntity flagTile;

	public FlagPlacedEvent(Player var1, BlockState var2, BlockPos var3, FlagBlockEntity var4) {
		this.placer = var1;
		this.state = var2;
		this.pos = var3;
		this.flagTile = var4;
	}
	public Player getPlacer() {
		return this.placer;
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