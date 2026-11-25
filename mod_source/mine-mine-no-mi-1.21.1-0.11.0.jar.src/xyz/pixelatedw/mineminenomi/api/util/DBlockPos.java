package xyz.pixelatedw.mineminenomi.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

@Deprecated()
public class DBlockPos extends BlockPos {
	// Fields
	private ResourceKey<Level> dimension;

	@Deprecated()
	public DBlockPos(int var1, int var2, int var3, ResourceKey<Level> var4) {
		super(var1,var2,var3);
		this.dimension = var4;
	}
	@Deprecated()
	public DBlockPos(long var1, ResourceKey<Level> var2) {
		this(getX(var1),getY(var1),getZ(var1),var2);
	}
	@Deprecated()
	public ResourceKey<Level> getDimension() {
		return this.dimension;
	}
}