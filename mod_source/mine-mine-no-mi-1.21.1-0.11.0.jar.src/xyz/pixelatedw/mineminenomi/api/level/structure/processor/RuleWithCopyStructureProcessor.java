package xyz.pixelatedw.mineminenomi.api.level.structure.processor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

public class RuleWithCopyStructureProcessor extends StructureProcessor {
	// Fields
	public static final MapCodec<RuleWithCopyStructureProcessor> CODEC;
	private final ImmutableList<ProcessorRule> rules;

	public RuleWithCopyStructureProcessor(List<? extends ProcessorRule> var1) {
		this.rules = (ImmutableList) (ImmutableList.copyOf(var1));
	}
	@Nullable()
	public StructureTemplate.@Nullable() StructureBlockInfo process(LevelReader var1, BlockPos var2, BlockPos var3, StructureTemplate.StructureBlockInfo var4, StructureTemplate.StructureBlockInfo var5, StructurePlaceSettings var6, @Nullable() StructureTemplate var7) {
		RandomSource var8 = RandomSource.create(Mth.getSeed((Vec3i)(var5.pos())));
		BlockState var9 = var1.getBlockState(var5.pos());
		UnmodifiableIterator var10 = this.rules.iterator();
		do{
			if (var10.hasNext()){
				ProcessorRule var11 = ((ProcessorRule)(var10.next()));
				if (var11.test(var5.state(),var9,var4.pos(),var5.pos(),var3,var8)){
					BlockState var12 = var11.getOutputState();
					Iterator var13 = var4.state().getValues().entrySet().iterator();
					do{
						if (var13.hasNext()){
							Map.Entry var14 = ((Map.Entry)(var13.next()));
							if (var12.hasProperty(((Property)(var14.getKey())))){
								var12 = copyProperty(var4.state(),var12,((Property)(var14.getKey())));
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					CompoundTag var14_1 = var11.getOutputTag(var8,var5.nbt());
					return new StructureTemplate.StructureBlockInfo(var5.pos(),var12,var14_1);
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var5;
	}
	private static <T extends Comparable<T>> BlockState copyProperty(BlockState var0, BlockState var1, Property<T> var2) {
		return ((BlockState)(((net.minecraft.world.level.block.state.BlockState)(var1)).setValue((Property)(var2),(Comparable)(((net.minecraft.world.level.block.state.BlockState)(var0)).getValue((Property)(var2))))));
	}
	protected StructureProcessorType<?> getType() {
		return (StructureProcessorType<?>) (StructureProcessorType) (((StructureProcessorType)(ModStructures.RULE_WITH_COPY_PROCESSOR.get())));
	}
	static  {
		Function<List, RuleWithCopyStructureProcessor> var0 = RuleWithCopyStructureProcessor::new;
		Function var1 = (Function) ((Function<RuleWithCopyStructureProcessor, List>) ((l0) -> {
			return l0.rules;
		}));
		CODEC = (MapCodec) (ProcessorRule.CODEC.listOf().fieldOf("rules").xmap(var0,var1));
	}
}