package xyz.pixelatedw.mineminenomi.api.level.structure.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public final record ExtendedRandomSpreadStructurePlacement$SuperExclusionZone(Holder<StructureSet> otherSet, int chunkCount) {
	// Fields
	public static final Codec<ExtendedRandomSpreadStructurePlacement$SuperExclusionZone> CODEC;

	boolean isPlacementForbidden(ChunkGeneratorStructureState var1, int var2, int var3) {
		return var1.hasStructureChunkInRange(this.otherSet,var2,var3,this.chunkCount);
	}
	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}