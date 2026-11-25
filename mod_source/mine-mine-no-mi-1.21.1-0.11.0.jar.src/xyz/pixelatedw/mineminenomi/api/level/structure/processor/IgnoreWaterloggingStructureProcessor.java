package xyz.pixelatedw.mineminenomi.api.level.structure.processor;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

public class IgnoreWaterloggingStructureProcessor extends StructureProcessor {
	// Fields
	public static final MapCodec<IgnoreWaterloggingStructureProcessor> CODEC = MapCodec.unit((Supplier)(((Supplier<IgnoreWaterloggingStructureProcessor>)(IgnoreWaterloggingStructureProcessor::new))));

	@Nullable()
	public StructureTemplate.StructureBlockInfo process(LevelReader var1, BlockPos var2, BlockPos var3, StructureTemplate.StructureBlockInfo var4, StructureTemplate.StructureBlockInfo var5, StructurePlaceSettings var6, @Nullable() StructureTemplate var7) {
		return var5;
	}
	protected StructureProcessorType<?> getType() {
		return (StructureProcessorType<?>) (StructureProcessorType) (((StructureProcessorType)(ModStructures.IGNORE_WATERLOGGING_PROCESSOR.get())));
	}
}