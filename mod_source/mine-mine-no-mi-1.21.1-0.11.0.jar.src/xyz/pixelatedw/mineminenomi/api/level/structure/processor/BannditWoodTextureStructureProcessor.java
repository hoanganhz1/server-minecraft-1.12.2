package xyz.pixelatedw.mineminenomi.api.level.structure.processor;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

public class BannditWoodTextureStructureProcessor extends StructureProcessor {
	// Fields
	public static final MapCodec<BannditWoodTextureStructureProcessor> CODEC = MapCodec.unit((Supplier)(((Supplier<BannditWoodTextureStructureProcessor>)(BannditWoodTextureStructureProcessor::new))));
	private static final Block[] LOG_BLOCK;
	private static final Block[] FENCE_BLOCK;
	private static final Block[] SLABS_BLOCK;

	@Nullable()
	public StructureTemplate.StructureBlockInfo process(LevelReader var1, BlockPos var2, BlockPos var3, StructureTemplate.StructureBlockInfo var4, StructureTemplate.StructureBlockInfo var5, StructurePlaceSettings var6, @Nullable() StructureTemplate var7) {
		BlockPos var8 = var5.pos();
		RandomSource var9 = RandomSource.create(Mth.getSeed((Vec3i)(var8)));
		if ((var4.state().getBlock()) == (Blocks.DARK_OAK_LOG)){
			BlockState var10 = LOG_BLOCK[var9.nextInt(LOG_BLOCK.length)].defaultBlockState();
			return new StructureTemplate.StructureBlockInfo(var8,var10,var5.nbt());
		}else{
			if ((var4.state().getBlock()) == (Blocks.DARK_OAK_SLAB)){
				BlockState var10_1 = SLABS_BLOCK[var9.nextInt(SLABS_BLOCK.length)].defaultBlockState();
				return new StructureTemplate.StructureBlockInfo(var8,var10_1,var5.nbt());
			}else{
				if ((var4.state().getBlock()) == (Blocks.DARK_OAK_FENCE)){
					BlockState var10_2 = FENCE_BLOCK[var9.nextInt(FENCE_BLOCK.length)].defaultBlockState();
					return new StructureTemplate.StructureBlockInfo(var8,var10_2,var5.nbt());
				}else{
					return var5;
				}
			}
		}
	}
	protected StructureProcessorType<?> getType() {
		return (StructureProcessorType<?>) (StructureProcessorType) (((StructureProcessorType)(ModStructures.BANDIT_WOOD_TEXTURE_PROCESSOR.get())));
	}
	static  {
		Block[] var0 = new Block[]{Blocks.DARK_OAK_LOG,Blocks.STRIPPED_DARK_OAK_LOG,Blocks.SPRUCE_LOG,Blocks.STRIPPED_SPRUCE_LOG};
		LOG_BLOCK = var0;
		Block[] var1 = new Block[]{Blocks.DARK_OAK_FENCE,Blocks.SPRUCE_FENCE};
		FENCE_BLOCK = var1;
		Block[] var2 = new Block[]{Blocks.DARK_OAK_SLAB,Blocks.SPRUCE_SLAB};
		SLABS_BLOCK = var2;
	}
}