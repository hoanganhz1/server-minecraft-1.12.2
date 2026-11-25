package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue$BlockQueueData;
import xyz.pixelatedw.mineminenomi.api.blockgen.IAfterPlaceBlockEvent;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class BlockTrackerComponent extends AbilityComponent<IAbility> {
	// Fields
	private Set<BlockPos> positions;
	private Set<BlockPos> queuePositions;
	private BlockQueue blockQueue;
	private boolean runBlockQueueUntilFinished;

	public BlockTrackerComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.BLOCK_TRACKER.get())),var1);
		this.positions = new HashSet();
		this.queuePositions = new HashSet();
	}
	protected void doTick(LivingEntity var1) {
		Level var2 = var1.level();
		if (((var2) != (null)) && (!(var2.isClientSide))){
			if (((this.blockQueue) != (null)) && ((this.blockQueue.hasNext()) && (this.runBlockQueueUntilFinished))){
				this.tickBlockQueue(var1.level());
				if (!(this.blockQueue.hasNext())){
					this.runBlockQueueUntilFinished = false;
				}else{
					return;
				}
			}
			return;
		}else{
			return;
		}
	}
	public void addBlockPos(BlockPos var1) {
		this.positions.add(var1);
	}
	public void addPositions(Set<BlockPos> var1) {
		((java.util.Set)(this.positions)).addAll((Collection)(var1));
	}
	public void clearPositions() {
		this.positions.clear();
	}
	public Set<BlockPos> getPositions() {
		return this.positions;
	}
	@Nullable()
	public BlockQueue getQueue() {
		return this.blockQueue;
	}
	public void initQueue(Level var1, int var2) {
		this.initQueue(var1,var2,8192);
	}
	public void initQueue(Level var1, int var2, int var3) {
		if (var1 instanceof ServerLevel){
			ServerLevel var4 = ((ServerLevel)(var1));
			if ((this.blockQueue) == (null)){
				this.blockQueue = new BlockQueue(var4,var3);
			}
			this.blockQueue.clearQueue();
			this.blockQueue.setBlocksPerTick(var2);
			this.queuePositions.clear();
			return;
		}else{
			return;
		}
	}
	public void addBlockToQueue(BlockPos var1, BlockState var2, int var3) {
		this.addBlockToQueue(var1,var2,var3,(BlockProtectionRule)(null),(IAfterPlaceBlockEvent)(null));
	}
	public BlockQueue$BlockQueueData addBlockToQueue(BlockPos var1, BlockState var2, int var3, @Nullable() BlockProtectionRule var4, @Nullable() IAfterPlaceBlockEvent var5) {
		this.ensureQueueIsInitialized();
		BlockQueue$BlockQueueData var6 = new BlockQueue$BlockQueueData(var1,var2,var3);
		var6.setProtectionRule(var4);
		if ((var5) != (null)){
			BlockTrackerComponent var7 = this;
			BlockPos var8 = var1;
			IAfterPlaceBlockEvent var9 = var5;
			final BlockPos var8_f1 = var8;
			final IAfterPlaceBlockEvent var9_f2 = var9;
			IAfterPlaceBlockEvent var10 = (l0, l1, l2) -> {
			this.queuePositions.add(var8_f1);
			var9_f2.placed(l0,l1,l2);
		};
			var6.setAfterPlaceEvent(var10);
		}
		this.blockQueue.add(var6);
		return var6;
	}
	public void runBlockQueueUntilFinished() {
		this.runBlockQueueUntilFinished = true;
	}
	public void tickBlockQueue(Level var1) {
		if (var1.isClientSide){
			return;
		}else{
			this.ensureQueueIsInitialized();
			if ((this.blockQueue) != (null)){
				this.blockQueue.tick();
			}
			return;
		}
	}
	public List<BlockPos> getNearbyBlockPositions(BlockPos var1, LevelAccessor var2, int var3, @Nullable() TagKey<Block> var4) {
		return (List<BlockPos>) (List) (((xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent)(this)).getNearbyBlockPositions((BlockPos)(var1),(LevelAccessor)(var2),var3,var3,(TagKey)(var4)));
	}
	public List<BlockPos> getNearbyBlockPositions(BlockPos var1, LevelAccessor var2, int var3, int var4, @Nullable() TagKey<Block> var5) {
		ArrayList var6 = new ArrayList();
		BlockPos.MutableBlockPos var7 = new BlockPos.MutableBlockPos();
		int var8 = -(var3);
		LOOP_2:
		do{
			if ((var8) <= (var3)){
				int var9 = -(var4);
				LOOP_1:
				do{
					if ((var9) <= (var4)){
						int var10 = -(var3);
						do{
							if ((var10) <= (var3)){
								var7.set((var1.getX()) + (var8),(var1.getY()) + (var9),(var1.getZ()) + (var10));
								BlockState var11 = var2.getBlockState((BlockPos)(var7));
								if ((var11.isAir()) || (((var5) != (null)) && (!(var11.is(var5))))){

								}else{
									var6.add(var7.immutable());
								}
								var10++;
								continue;
							}else{
								break;
							}
						} while (true);
						var9++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				var8++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
		return var6;
	}
	public void ensureQueueIsInitialized() {
		if ((((this.blockQueue) != (null)) ? (1) : (0)) == (0)){
			throw new RuntimeException((String)("Block Queue was not initialized!"));
		}else{
			return;
		}
	}
}