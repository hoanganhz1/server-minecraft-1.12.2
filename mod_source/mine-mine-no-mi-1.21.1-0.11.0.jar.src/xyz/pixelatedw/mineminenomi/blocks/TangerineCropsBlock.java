package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.CommonHooks;
import xyz.pixelatedw.mineminenomi.api.helpers.BlocksHelper;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class TangerineCropsBlock extends DoublePlantBlock implements BonemealableBlock {
	// Fields
	public static final IntegerProperty AGE = BlocksHelper.AGE_4;

	public TangerineCropsBlock() {
		super(BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
	}
	public ItemStack getCloneItemStack(LevelReader var1, BlockPos var2, BlockState var3) {
		return new ItemStack(((ItemLike)(ModItems.TANGERINE.get())));
	}
	protected boolean mayPlaceOn(BlockState var1, BlockGetter var2, BlockPos var3) {
		return var1.is(Blocks.FARMLAND);
	}
	protected ItemInteractionResult useItemOn(ItemStack var1, BlockState var2, Level var3, BlockPos var4, Player var5, InteractionHand var6, BlockHitResult var7) {
		return (((((((Integer)(var2.getValue((Property)(AGE)))).intValue()) == (this.getMaxAge())) ? (1) : (0)) == (0)) && (var1.is(Items.BONE_MEAL))) ? (ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION) : (super.useItemOn(var1,var2,var3,var4,var5,var6,var7));
	}
	public InteractionResult useWithoutItem(BlockState var1, Level var2, BlockPos var3, Player var4, BlockHitResult var5) {
		int var6 = ((((Integer)(var1.getValue((Property)(AGE)))).intValue()) == (this.getMaxAge())) ? (1) : (0);
		ItemStack var7 = var4.getItemInHand(var4.getUsedItemHand());
		if (((var6) != (0)) && (var7.isEmpty())){
			BlockState var8 = var2.getBlockState(var3.below());
			BlockState var9 = var2.getBlockState(var3.above());
			this.dropFruit(var2,var3,var1);
			if ((!(var8.isAir())) && ((var8.getBlock()) == (ModBlocks.TANGERINE_CROP.get()))){
				this.dropFruit(var2,var3.below(),var8);
			}else{
				if ((!(var9.isAir())) && ((var9.getBlock()) == (ModBlocks.TANGERINE_CROP.get()))){
					this.dropFruit(var2,var3.above(),var9);
				}
			}
			return InteractionResult.sidedSuccess(var2.isClientSide);
		}else{
			return super.useWithoutItem(var1,var2,var3,var4,var5);
		}
	}
	private void dropFruit(Level var1, BlockPos var2, BlockState var3) {
		int var4 = (1) + (var1.random.nextInt(2));
		popResource(var1,var2,new ItemStack(((ItemLike)(ModItems.TANGERINE.get())),var4));
		var1.playSound(((Player)(null)),var2,SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES,SoundSource.BLOCKS,1F,(1.2F) + ((var1.random.nextFloat()) * (0.4F)));
		var1.setBlock(var2,((BlockState)(var3.setValue((Property)(AGE),(Comparable)(Integer.valueOf(2))))),2);
	}
	public int getMaxAge() {
		return 4;
	}
	public boolean isRandomlyTicking(BlockState var1) {
		return (((Integer)(var1.getValue((Property)(AGE)))).intValue()) < (this.getMaxAge());
	}
	public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, RandomSource var4) {
		if (!(var2.isAreaLoaded(var3,1))){
			return;
		}else{
			int var5 = ((Integer)(var1.getValue((Property)(AGE)))).intValue();
			int var6 = ((var4.nextInt(25)) == (0)) ? (1) : (0);
			if (((var5) < (this.getMaxAge())) && (((var2.getRawBrightness(var3.above(),0)) >= (9)) && (CommonHooks.canCropGrow((Level)(var2),var3,var1,(var6) != (0))))){
				BlockState var7 = var2.getBlockState(var3.below());
				BlockState var8 = var2.getBlockState(var3.above());
				this.grow((Level)(var2),var3,var1);
				if ((!(var7.isAir())) && ((var7.getBlock()) == (ModBlocks.TANGERINE_CROP.get()))){
					this.grow((Level)(var2),var3.below(),var7);
				}else{
					if ((!(var8.isAir())) && ((var8.getBlock()) == (ModBlocks.TANGERINE_CROP.get()))){
						this.grow((Level)(var2),var3.above(),var8);
					}
				}
				CommonHooks.fireCropGrowPost((Level)(var2),var3,var1);
			}
			return;
		}
	}
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
		super.createBlockStateDefinition(var1);
		Property[] var2 = new Property[]{AGE};
		var1.add(var2);
	}
	public boolean isValidBonemealTarget(LevelReader var1, BlockPos var2, BlockState var3) {
		return (((Integer)(var3.getValue((Property)(AGE)))).intValue()) < (this.getMaxAge());
	}
	public boolean isBonemealSuccess(Level var1, RandomSource var2, BlockPos var3, BlockState var4) {
		return true;
	}
	public void performBonemeal(ServerLevel var1, RandomSource var2, BlockPos var3, BlockState var4) {
		BlockState var5 = var1.getBlockState(var3.below());
		BlockState var6 = var1.getBlockState(var3.above());
		this.grow((Level)(var1),var3,var4);
		if ((!(var5.isAir())) && ((var5.getBlock()) == (ModBlocks.TANGERINE_CROP.get()))){
			this.grow((Level)(var1),var3.below(),var5);
		}else{
			if ((!(var6.isAir())) && ((var6.getBlock()) == (ModBlocks.TANGERINE_CROP.get()))){
				this.grow((Level)(var1),var3.above(),var6);
			}else{
				return;
			}
		}
	}
	private void grow(Level var1, BlockPos var2, BlockState var3) {
		int var4 = Math.min(this.getMaxAge(),(((Integer)(var3.getValue((Property)(AGE)))).intValue()) + (1));
		var1.setBlock(var2,((BlockState)(var3.setValue((Property)(AGE),(Comparable)(Integer.valueOf(var4))))),2);
	}
}