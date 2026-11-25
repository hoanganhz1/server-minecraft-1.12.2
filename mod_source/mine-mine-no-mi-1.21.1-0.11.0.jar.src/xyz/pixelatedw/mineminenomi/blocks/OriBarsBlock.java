package xyz.pixelatedw.mineminenomi.blocks;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class OriBarsBlock extends IronBarsBlock {
	public OriBarsBlock() {
		super(BlockBehaviour.Properties.of().strength(50F));
	}
	public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		if (var4 instanceof EntityCollisionContext){
			Entity var5 = ((EntityCollisionContext)(var4)).getEntity();
			if (var5 instanceof Player){
				Player var6 = ((Player)(var5));
				Function<DevilFruitData, Boolean> var7 = (l0) -> {
			return Boolean.valueOf(l0.hasDevilFruit(ModFruits.ORI_ORI_NO_MI));
		};
				if (((Boolean)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var6)))).map((Function)(var7)).orElse(Boolean.valueOf(false)))).booleanValue()){
					return Shapes.empty();
				}
			}
			if ((this.getAABBIndex(var1)) == (15)){
				return Shapes.block();
			}
		}
		return this.collisionShapeByIndex[this.getAABBIndex(var1)];
	}
}