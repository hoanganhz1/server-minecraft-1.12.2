package xyz.pixelatedw.mineminenomi.api.blockgen;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;

public class SimpleBlockPlacer implements IBlockPlacer {
	// Fields
	private int[] size;
	private boolean hollow;
	private int thickness;
	private BlockState state;
	private int flag;
	private boolean coordsOnly;
	private @Nullable() BlockProtectionRule rule;
	private @Nullable() BlockQueue blockQueue;
	private @Nullable() Predicate<BlockPos> posPredicate;
	private @Nullable() IAfterPlaceBlockEvent afterPlaceEvent;
	private @Nullable() IAfterBlockGenerationEvent afterGenerationEvent;
	private Set<BlockPos> placed;

	public SimpleBlockPlacer() {
		int[] var1 = new int[]{0,0,0};
		this.size = var1;
		this.hollow = false;
		this.thickness = 1;
		this.state = Blocks.AIR.defaultBlockState();
		this.flag = 3;
		this.coordsOnly = false;
		this.rule = null;
		this.blockQueue = null;
		this.posPredicate = null;
		this.afterPlaceEvent = null;
		this.afterGenerationEvent = null;
		this.placed = new HashSet();
	}
	public SimpleBlockPlacer setSize(int var1) {
		return this.setSize(var1,var1,var1);
	}
	public SimpleBlockPlacer setSize(int var1, int var2) {
		return this.setSize(var1,var2,var1);
	}
	public SimpleBlockPlacer setSize(int var1, int var2, int var3) {
		int[] var4 = new int[]{var1,var2,var3};
		this.size = var4;
		return this;
	}
	public SimpleBlockPlacer setHollow() {
		this.hollow = true;
		return this;
	}
	public SimpleBlockPlacer setThickness(int var1) {
		this.thickness = var1;
		return this;
	}
	public SimpleBlockPlacer setBlock(BlockState var1) {
		this.state = var1;
		return this;
	}
	public SimpleBlockPlacer setFlag(int var1) {
		this.flag = var1;
		return this;
	}
	public SimpleBlockPlacer setCoordsOnly() {
		this.coordsOnly = true;
		return this;
	}
	public SimpleBlockPlacer setRule(@Nullable() BlockProtectionRule var1) {
		this.rule = var1;
		return this;
	}
	public SimpleBlockPlacer setBlockQueue(@Nullable() BlockQueue var1) {
		this.blockQueue = var1;
		return this;
	}
	public SimpleBlockPlacer setPositionPredicate(@Nullable() Predicate<BlockPos> var1) {
		this.posPredicate = var1;
		return this;
	}
	public SimpleBlockPlacer setAfterGenerationEvent(IAfterBlockGenerationEvent var1) {
		this.afterGenerationEvent = var1;
		return this;
	}
	public SimpleBlockPlacer setAfterPlaceEvent(IAfterPlaceBlockEvent var1) {
		this.afterPlaceEvent = var1;
		return this;
	}
	@Nullable()
	public @Nullable() BlockQueue getBlockQueue() {
		return this.blockQueue;
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
		return this.state;
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
		this.cleanPlacedPositions();
		var3.generate(this,var1,var2);
	}
	public void placeNext(Level var1, BlockPos var2, BlockState var3, int var4, @Nullable() BlockProtectionRule var5) {
		if (((this.posPredicate) != (null)) && (!(this.posPredicate.test(var2)))){
			return;
		}else{
			if ((this.blockQueue) != (null)){
				BlockQueue$BlockQueueData var6 = new BlockQueue$BlockQueueData(var2,this.getState(),this.getFlag());
				SimpleBlockPlacer var7 = this;
				BlockPos var8 = var2;
				final BlockPos var8_f1 = var8;
				IAfterPlaceBlockEvent var9 = (l0, l1, l2) -> {
			this.placed.add(l0);
			if ((this.afterPlaceEvent) != (null)){
				this.afterPlaceEvent.placed(var8_f1,l1,l2);
			}
		};
				var6.setAfterPlaceEvent(var9);
				var6.setProtectionRule(this.getRule());
				this.blockQueue.add(var6);
				return;
			}else{
				BlockState var6_1 = null;
				if ((this.afterPlaceEvent) != (null)){
					var6_1 = var1.getBlockState(var2);
				}
				boolean var7_1 = NuWorld.setBlockState(var1,var2,var3,var4,var5);
				if ((this.afterPlaceEvent) != (null)){
					this.afterPlaceEvent.placed(var2,var6_1,var3);
				}
				if (var7_1){
					this.placed.add(var2);
				}
				return;
			}
		}
	}
	public int countPlaced() {
		return this.placed.size();
	}
	public void cleanPlacedPositions() {
		this.placed.clear();
	}
	public Set<BlockPos> getPlacedPositions() {
		return this.placed;
	}
}