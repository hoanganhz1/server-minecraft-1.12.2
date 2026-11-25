package xyz.pixelatedw.mineminenomi.api.util;

import net.minecraft.core.BlockPos;

@Deprecated()
public class HashBlockPos extends BlockPos {
	// Fields
	private int hash;

	@Deprecated()
	public HashBlockPos(int var1, int var2, int var3) {
		this(var1,var2,var3,(((var2) * (31)) + (var3)) + (var1));
	}
	@Deprecated()
	public HashBlockPos(int var1, int var2, int var3, int var4) {
		super(var1,var2,var3);
		this.hash = var4;
	}
	@Deprecated()
	public HashBlockPos(BlockPos var1, int var2) {
		this(var1.getX(),var1.getY(),var1.getZ(),var2);
	}
	@Deprecated()
	public static HashBlockPos of(BlockPos var0) {
		return new HashBlockPos(var0.getX(),var0.getY(),var0.getZ());
	}
	@Deprecated()
	public int hashCode() {
		return this.hash;
	}
}