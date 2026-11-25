package xyz.pixelatedw.mineminenomi.api.blockgen;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;

public class RandomBlockPlacer implements IBlockPlacer {
	// Fields
	private int[] size;
	private boolean hollow;
	private int thickness;
	private WeightedList<BlockState> states;
	private int flag;
	private boolean coordsOnly;
	private @Nullable() BlockProtectionRule rule;
	private RandomSource rand;
	private @Nullable() BlockQueue blockQueue;
	private Set<BlockPos> placed;

	public RandomBlockPlacer() {
		int[] var1 = new int[]{0,0,0};
		this.size = var1;
		this.hollow = false;
		this.thickness = 1;
		this.states = (WeightedList) (new WeightedList(new Object[0]));
		this.flag = 3;
		this.coordsOnly = false;
		this.rule = null;
		this.rand = new SingleThreadedRandomSource(ThreadLocalRandom.current().nextLong());
		this.blockQueue = null;
		this.placed = new HashSet();
	}
	public RandomBlockPlacer setSize(int var1) {
		return this.setSize(var1,var1,var1);
	}
	public RandomBlockPlacer setSize(int var1, int var2) {
		return this.setSize(var1,var2,var1);
	}
	public RandomBlockPlacer setSize(int var1, int var2, int var3) {
		int[] var4 = new int[]{var1,var2,var3};
		this.size = var4;
		return this;
	}
	public RandomBlockPlacer setHollow() {
		this.hollow = true;
		return this;
	}
	public RandomBlockPlacer setThickness(int var1) {
		this.thickness = var1;
		return this;
	}
	public RandomBlockPlacer addBlock(BlockState var1, int var2) {
		this.states.addEntry(var1,var2);
		return this;
	}
	public RandomBlockPlacer setFlag(int var1) {
		this.flag = var1;
		return this;
	}
	public RandomBlockPlacer setCoordsOnly() {
		this.coordsOnly = true;
		return this;
	}
	public RandomBlockPlacer setRule(@Nullable() BlockProtectionRule var1) {
		this.rule = var1;
		return this;
	}
	public RandomBlockPlacer setBlockQueue(@Nullable() BlockQueue var1) {
		this.blockQueue = var1;
		return this;
	}
	public int getSizeX() {
		return this.size[0];
	}
	public int getSizeY() {
		return this.size[1];
	}
	public int getSizeZ() {
		return this.size[2];
	}
	public boolean isHollow() {
		return this.hollow;
	}
	public int getThickness() {
		return this.thickness;
	}
	public BlockState getState() {
		BlockState var1 = ((BlockState)(this.states.pick(this.rand)));
		if ((var1) == (null)){
			return Blocks.AIR.defaultBlockState();
		}else{
			return var1;
		}
	}
	public int getFlag() {
		return this.flag;
	}
	public boolean onlyCoords() {
		return this.coordsOnly;
	}
	@Nullable()
	public @Nullable() BlockProtectionRule getRule() {
		return this.rule;
	}
	public void generate(Level var1, BlockPos var2, IBlockGenerator var3) {
		var3.generate(this,var1,var2);
	}
	public void placeNext(Level var1, BlockPos var2, BlockState var3, int var4, @Nullable() BlockProtectionRule var5) {
		if ((this.blockQueue) != (null)){
			BlockQueue$BlockQueueData var6 = new BlockQueue$BlockQueueData(var2,this.getState(),this.getFlag());
			RandomBlockPlacer var7 = this;
			IAfterPlaceBlockEvent var8 = (l0, l1, l2) -> {
			this.placed.add(l0);
		};
			var6.setAfterPlaceEvent(var8);
			this.blockQueue.add(var6);
			return;
		}else{
			if (NuWorld.setBlockState(var1,var2,var3,var4,var5)){
				this.placed.add(var2);
			}
			return;
		}
	}
	public int countPlaced() {
		return this.placed.size();
	}
	public Set<BlockPos> getPlacedPositions() {
		return new HashSet((Collection)(this.placed));
	}
}