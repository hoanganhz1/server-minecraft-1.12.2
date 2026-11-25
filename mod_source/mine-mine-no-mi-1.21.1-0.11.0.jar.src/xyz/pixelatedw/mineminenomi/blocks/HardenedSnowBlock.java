package xyz.pixelatedw.mineminenomi.blocks;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class HardenedSnowBlock extends Block {
	public HardenedSnowBlock() {
		super(BlockBehaviour.Properties.of().forceSolidOn().noCollission().dynamicShape().strength(8F,4F).sound(SoundType.SNOW));
	}
	public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		if (var4 instanceof EntityCollisionContext){
			Entity var5 = ((EntityCollisionContext)(var4)).getEntity();
			if (var5 instanceof LivingEntity){
				LivingEntity var6 = ((LivingEntity)(var5));
				Function<DevilFruitData, Boolean> var7 = (l0) -> {
			return Boolean.valueOf(l0.hasDevilFruit(ModFruits.YUKI_YUKI_NO_MI));
		};
				if (((Boolean)(((java.util.Optional)(DevilFruitData.get(var6))).map((Function)(var7)).orElse(Boolean.valueOf(false)))).booleanValue()){
					return Shapes.empty();
				}
			}
		}
		return Shapes.block();
	}
	public int getFireSpreadSpeed(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
		return 5;
	}
	public int getFlammability(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
		return 60;
	}
}