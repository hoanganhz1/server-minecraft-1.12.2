package xyz.pixelatedw.mineminenomi.api.blockgen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;

public class BlockQueue {
	// Fields
	public static final int INITIAL_SIZE = 8192;
	private static Map<Level, BlockQueue> queueMap;
	private List<BlockQueue$BlockQueueData> queue;
	private final Supplier<Boolean> queueAction;
	private final ServerLevel world;
	private boolean isRunning;
	private int speed;
	private int blocksPerTick;

	public void add(BlockQueue$BlockQueueData var1) {
		this.queue.add(var1);
	}
	public void add(BlockPos var1, BlockState var2) {
		this.add(new BlockQueue$BlockQueueData(var1,var2,0));
	}
	public void add(BlockPos var1, BlockState var2, int var3) {
		this.add(new BlockQueue$BlockQueueData(var1,var2,var3));
	}
	public void addAll(Set<BlockPos> var1, BlockState var2) {
		BlockState var3 = var2;
		final BlockState var3_f1 = var3;
		Function<BlockPos, BlockQueue$BlockQueueData> var4 = (l0) -> {
			return new BlockQueue$BlockQueueData(l0,var3_f1,0);
		};
		List var5 = var1.stream().map(var4).toList();
		this.queue.addAll((Collection)(var5));
	}
	public BlockQueue(ServerLevel var1) {
		this(var1,8192);
	}
	public BlockQueue(ServerLevel var1, int var2) {
		this.isRunning = false;
		this.speed = 50;
		this.blocksPerTick = 0;
		this.world = var1;
		this.queue = new ArrayList((Math.round(((float)(var2)) / (0.75F))) + (1));
		BlockQueue var3 = this;
		ServerLevel var4 = var1;
		final ServerLevel var4_f1 = var4;
		Supplier<Boolean> var5 = () -> {
			int lv2_3 = 1;
			int lv2_4 = ((this.blocksPerTick) > (0)) ? (1) : (0);
			long lv2_5 = System.currentTimeMillis();
			int lv2_6 = 0;
			Iterator lv2_7 = this.queue.iterator();
			do{
				if ((((lv2_3) == (0)) && ((lv2_4) == (0))) || (!(lv2_7.hasNext()))){
					break;
				}else{
					BlockQueue$BlockQueueData lv2_8 = ((BlockQueue$BlockQueueData)(lv2_7.next()));
					BlockQueue$BlockQueueData lv2_9 = lv2_8;
					if ((lv2_8) != (null)){
						lv2_3 = (((System.currentTimeMillis()) - (lv2_5)) < ((long)(this.speed))) ? (1) : (0);
						if ((this.blocksPerTick) > (0)){
							lv2_4 = ((lv2_6) < (this.blocksPerTick)) ? (1) : (0);
							lv2_3 = lv2_4;
						}
						if (((((lv2_9.canPlaceCheck) == (null)) || (lv2_9.canPlaceCheck.check((LevelAccessor)(var4_f1),lv2_9.pos,lv2_9.state))) ? (1) : (0)) == (0)){
							lv2_7.remove();
							continue;
						}else{
							BlockState lv2_10 = var4_f1.getBlockState(lv2_9.pos);
							if (NuWorld.setBlockState((Level)(var4_f1),lv2_9.pos,lv2_9.state,lv2_9.flag,lv2_9.protectionRule)){
								if ((lv2_9.afterPlaceEvent) != (null)){
									lv2_9.afterPlaceEvent.placed(lv2_9.pos,lv2_10,lv2_9.state);
								}
								if ((lv2_9.tileEntity) != (null)){
									BlockEntity lv2_11 = var4_f1.getBlockEntity(lv2_9.pos);
									lv2_9.tileEntity.accept(lv2_11);
								}
								lv2_6++;
							}
							lv2_7.remove();
							continue;
						}
					}else{
						break;
					}
				}
			} while (true);
			return Boolean.valueOf(true);
		};
		this.queueAction = var5;
	}
	public BlockQueue setSpeed(int var1) {
		this.speed = var1;
		return this;
	}
	public BlockQueue setBlocksPerTick(int var1) {
		this.blocksPerTick = var1;
		return this;
	}
	public static BlockQueue getLevelQueue(ServerLevel var0) {
		if (!(queueMap.containsKey(var0))){
			BlockQueue var1 = new BlockQueue(var0);
			((java.util.Map)(queueMap)).put((Object)(var0),(Object)(var1));
			return var1;
		}else{
			return ((BlockQueue)(queueMap.get(var0)));
		}
	}
	public void start() {
		Function<BlockQueue$BlockQueueData, Double> var1 = (l0) -> {
			return Double.valueOf(l0.distance);
		};
		((java.util.List)(this.queue)).sort((Comparator)(Comparator.comparing((Function)(var1))));
		this.isRunning = true;
	}
	public void stop() {
		this.isRunning = false;
	}
	public boolean isRunning() {
		return this.isRunning;
	}
	public boolean isDone() {
		return (this.isRunning) && ((this.queue.size()) <= (0));
	}
	public boolean hasNext() {
		return (this.queue.size()) > (0);
	}
	public int getQueueSize() {
		return this.queue.size();
	}
	public void clearQueue() {
		this.queue.clear();
	}
	public void tick() {
		if ((this.isRunning) && (this.hasNext())){
			this.queueAction.get();
		}
	}
	static  {
		queueMap = new ConcurrentHashMap();
	}
}