package xyz.pixelatedw.mineminenomi.api.protection;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import org.jetbrains.annotations.Nullable;

public class ProtectedArea$RestorationEntry {
	// Fields
	private final long timestamp;
	private final GlobalPos pos;
	private final BlockState block;
	private final @Nullable() CompoundTag nbt;

	public ProtectedArea$RestorationEntry(Level var1, BlockPos var2) {
		this(var1.getGameTime(),GlobalPos.of(var1.dimension(),var2),var1.getBlockState(var2),getBlockEntityTag((HolderLookup.Provider)(var1.registryAccess()),var1.getBlockEntity(var2)));
	}
	public ProtectedArea$RestorationEntry(long var1, GlobalPos var2, BlockState var3, @Nullable() CompoundTag var4) {
		this.timestamp = var1;
		this.pos = var2;
		this.block = var3;
		this.nbt = var4;
	}
	@Deprecated()
	public ProtectedArea$RestorationEntry(long var1, BlockSnapshot var2) {
		this.timestamp = var1;
		this.pos = GlobalPos.of(Level.OVERWORLD,var2.getPos());
		this.block = var2.getState();
		this.nbt = var2.getTag();
	}
	@Nullable()
	private static @Nullable() CompoundTag getBlockEntityTag(HolderLookup.Provider var0, @Nullable() BlockEntity var1) {
		return (CompoundTag) (((var1) == (null)) ? (null) : (var1.saveWithFullMetadata(var0)));
	}
	public long getTimestamp() {
		return this.timestamp;
	}
	public GlobalPos getPos() {
		return this.pos;
	}
	public BlockState getBlock() {
		return this.block;
	}
	@Nullable()
	public @Nullable() CompoundTag getTag() {
		return this.nbt;
	}
}