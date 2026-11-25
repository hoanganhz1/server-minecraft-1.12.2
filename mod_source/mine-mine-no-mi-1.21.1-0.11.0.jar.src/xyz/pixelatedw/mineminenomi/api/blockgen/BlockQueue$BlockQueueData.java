package xyz.pixelatedw.mineminenomi.api.blockgen;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;

public class BlockQueue$BlockQueueData {
	// Fields
	final BlockPos pos;
	final BlockState state;
	final int flag;
	 Consumer<BlockEntity> tileEntity;
	 BlockProtectionRule protectionRule;
	 double distance;
	 BlockQueue$ICanPlaceBlock canPlaceCheck;
	 IAfterPlaceBlockEvent afterPlaceEvent;

	public BlockQueue$BlockQueueData(BlockPos var1, BlockState var2, int var3) {
		this.tileEntity = null;
		this.protectionRule = null;
		this.canPlaceCheck = null;
		this.afterPlaceEvent = null;
		this.pos = var1;
		this.state = var2;
		this.flag = var3;
	}
	public BlockQueue$BlockQueueData setTileEntity(Consumer<BlockEntity> var1) {
		this.tileEntity = var1;
		return this;
	}
	public BlockQueue$BlockQueueData setCanPlaceCheck(BlockQueue$ICanPlaceBlock var1) {
		this.canPlaceCheck = var1;
		return this;
	}
	public BlockQueue$BlockQueueData setAfterPlaceEvent(IAfterPlaceBlockEvent var1) {
		this.afterPlaceEvent = var1;
		return this;
	}
	public BlockQueue$BlockQueueData setProtectionRule(BlockProtectionRule var1) {
		this.protectionRule = var1;
		return this;
	}
	public BlockQueue$BlockQueueData setDistance(double var1) {
		this.distance = var1;
		return this;
	}
	public int hashCode() {
		return this.pos.hashCode();
	}
}