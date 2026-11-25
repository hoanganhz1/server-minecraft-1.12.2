package xyz.pixelatedw.mineminenomi.blocks;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.SlashDamageImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class StringWallBlock extends Block {
	// Fields
	protected static final VoxelShape COLLISION_SHAPE = Block.box(1D,0D,1D,15D,15D,15D);
	protected static final VoxelShape OUTLINE_SHAPE = Block.box(1D,0D,1D,15D,16D,15D);

	public StringWallBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).noOcclusion().noLootTable().strength(-1F,10000F));
	}
	public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		if (var4 instanceof EntityCollisionContext){
			Entity var5 = ((EntityCollisionContext)(var4)).getEntity();
			if (var5 instanceof Player){
				Player var6 = ((Player)(var5));
				Function<DevilFruitData, Boolean> var7 = (l0) -> {
			return Boolean.valueOf(l0.hasDevilFruit(ModFruits.BARA_BARA_NO_MI));
		};
				boolean var8 = ((Boolean)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var6)))).map((Function)(var7)).orElse(Boolean.valueOf(false)))).booleanValue();
				Function<AbilityData, Boolean> var9 = (l0) -> {
			return Boolean.valueOf(l0.hasUnlockedAbility(((AbilityCore)(SlashDamageImmunityAbility.INSTANCE.get()))));
		};
				boolean var10 = ((Boolean)(((java.util.Optional)(AbilityData.get((LivingEntity)(var6)))).map((Function)(var9)).orElse(Boolean.valueOf(false)))).booleanValue();
				if ((!(var8)) && (!(var10))){
					return null;

				}else{
					return Shapes.empty();
				}
			}
		}
		return COLLISION_SHAPE;
	}
	public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		return OUTLINE_SHAPE;
	}
	public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
		return true;
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean skipRendering(BlockState var1, BlockState var2, Direction var3) {
		return (var2.getBlock()) == (this);
	}
	public int getLightBlock(BlockState var1, BlockGetter var2, BlockPos var3) {
		return 0;
	}
	public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
		if (var4 instanceof Player){
			Player var5 = ((Player)(var4));
			Function<DevilFruitData, Boolean> var6 = (l0) -> {
			return Boolean.valueOf(l0.hasDevilFruit(ModFruits.BARA_BARA_NO_MI));
		};
			boolean var7 = ((Boolean)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var5)))).map((Function)(var6)).orElse(Boolean.valueOf(false)))).booleanValue();
			Function<AbilityData, Boolean> var8 = (l0) -> {
			return Boolean.valueOf(l0.hasUnlockedAbility(((AbilityCore)(SlashDamageImmunityAbility.INSTANCE.get()))));
		};
			boolean var9 = ((Boolean)(((java.util.Optional)(AbilityData.get((LivingEntity)(var5)))).map((Function)(var8)).orElse(Boolean.valueOf(false)))).booleanValue();
			if ((!(var7)) && (!(var9))){

			}else{
				return;
			}
		}
		var4.hurt(var4.damageSources().magic(),1F);
	}
}