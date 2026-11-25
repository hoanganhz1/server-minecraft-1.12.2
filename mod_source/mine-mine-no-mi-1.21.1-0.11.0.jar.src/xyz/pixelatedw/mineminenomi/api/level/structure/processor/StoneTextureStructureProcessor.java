package xyz.pixelatedw.mineminenomi.api.level.structure.processor;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
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
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

public class StoneTextureStructureProcessor extends StructureProcessor {
	// Fields
	public static final MapCodec<StoneTextureStructureProcessor> CODEC = MapCodec.unit((Supplier)(((Supplier<StoneTextureStructureProcessor>)(StoneTextureStructureProcessor::new))));
	private static final Block[] FULL_BLOCK;
	private static final Block[] WALL_BLOCK;
	private static final Block[] SLAB_BLOCK;

	@Nullable()
	public StructureTemplate.@Nullable() StructureBlockInfo process(LevelReader var1, BlockPos var2, BlockPos var3, StructureTemplate.StructureBlockInfo var4, StructureTemplate.StructureBlockInfo var5, StructurePlaceSettings var6, @Nullable() StructureTemplate var7) {
		BlockPos var8 = var5.pos();
		RandomSource var9 = RandomSource.create(Mth.getSeed((Vec3i)(var8)));
		if ((var4.state().getBlock()) == (Blocks.STONE)){
			BlockState var10 = FULL_BLOCK[var9.nextInt(FULL_BLOCK.length)].defaultBlockState();
			return new StructureTemplate.StructureBlockInfo(var8,var10,var5.nbt());
		}else{
			if ((var4.state().getBlock()) == (Blocks.COBBLESTONE_WALL)){
				if ((((!(var1.getBlockState(var8.below()).isAir())) ? (1) : (0)) != (0)) && (var9.nextBoolean())){
					BlockState var10_1 = SLAB_BLOCK[var9.nextInt(SLAB_BLOCK.length)].defaultBlockState();
					return new StructureTemplate.StructureBlockInfo(var8.below(),var10_1,var5.nbt());
				}else{
					BlockState var10_2 = WALL_BLOCK[var9.nextInt(WALL_BLOCK.length)].defaultBlockState();
					return new StructureTemplate.StructureBlockInfo(var8,var10_2,var5.nbt());
				}
			}else{
				if ((var4.state().getBlock()) == (Blocks.STONE_SLAB)){
					BlockState var10_3 = SLAB_BLOCK[var9.nextInt(SLAB_BLOCK.length)].defaultBlockState();
					return new StructureTemplate.StructureBlockInfo(var8,var10_3,var5.nbt());
				}else{
					return var5;
				}
			}
		}
	}
	protected StructureProcessorType<?> getType() {
		return (StructureProcessorType<?>) (StructureProcessorType) (((StructureProcessorType)(ModStructures.STONE_TEXTURE_PROCESSOR.get())));
	}
	static  {
		Block[] var0 = new Block[]{Blocks.STONE,Blocks.COBBLESTONE,Blocks.ANDESITE};
		FULL_BLOCK = var0;
		Block[] var1 = new Block[]{Blocks.COBBLESTONE_WALL,Blocks.ANDESITE_WALL,Blocks.AIR};
		WALL_BLOCK = var1;
		Block[] var2 = new Block[]{Blocks.STONE_SLAB,Blocks.COBBLESTONE_SLAB,Blocks.ANDESITE_SLAB,Blocks.STONE_PRESSURE_PLATE,Blocks.AIR};
		SLAB_BLOCK = var2;
	}
}