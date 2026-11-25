package xyz.pixelatedw.mineminenomi.api.blockgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;

public interface IBlockPlacer {
	public abstract int getSizeX();
	public abstract int getSizeY();
	public abstract int getSizeZ();
	public abstract boolean isHollow();
	public abstract int getThickness();
	public abstract BlockState getState();
	public abstract int getFlag();
	@Nullable()
	public abstract @Nullable() BlockProtectionRule getRule();
	public abstract void generate(Level var0, BlockPos var1, IBlockGenerator var2);
	public abstract void placeNext(Level var0, BlockPos var1, BlockState var2, int var3, @Nullable() BlockProtectionRule var4);
}