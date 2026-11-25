package xyz.pixelatedw.mineminenomi.blocks;

import com.mojang.serialization.MapCodec;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.api.events.FlagDestroyedEvent;
import xyz.pixelatedw.mineminenomi.api.events.FlagPlacedEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.BlocksHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.FlagData;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.items.FlagItem;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSetFlagOnFirePacket;

public class FlagBlock extends BaseEntityBlock {
	// Fields
	public static final MapCodec<FlagBlock> CODEC = simpleCodec((Function)(((Function<BlockBehaviour.Properties, FlagBlock>)(FlagBlock::new))));
	private static final int[] SCALE_XZ;
	private static final int[] SCALE_Y;
	public static final DirectionProperty FACING;
	public static final EnumProperty<CanvasSize> SIZE;

	public FlagBlock() {
		this(BlockBehaviour.Properties.of().strength(0.2F).noOcclusion().isSuffocating(BlocksHelper::never));
	}
	public FlagBlock(BlockBehaviour.Properties var1) {
		super(var1);
		this.registerDefaultState(((BlockState)(((BlockState)(((BlockState)(this.stateDefinition.any())).setValue((Property)(FACING),(Comparable)(Direction.NORTH)))).setValue((Property)(SIZE),(Comparable)(CanvasSize.SMALL)))));
	}
	public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		return Shapes.block();
	}
	public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
		if ((var4 instanceof EntityCollisionContext) && (((EntityCollisionContext)(var4)).getEntity() instanceof Projectile)){
			return var1.getShape(var2,var3);
		}else{
			return Shapes.empty();
		}
	}
	public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
		return var2.getBlockState(var3.offset(((Direction)(var1.getValue((Property)(FACING)))).getOpposite().getNormal())).isSolid();
	}
	@Nullable()
	public BlockState getStateForPlacement(BlockPlaceContext var1) {
		BlockState var2 = this.defaultBlockState();
		BlockPos var3 = var1.getClickedPos();
		Direction[] var4 = var1.getNearestLookingDirections();
		var2 = ((BlockState)(var2.setValue((Property)(SIZE),(Comparable)(((CanvasSize)(var1.getItemInHand().getOrDefault(ModDataComponents.CANVAS_SIZE,CanvasSize.SMALL)))))));
		Direction[] var5 = var4;
		int var6 = var5.length;
		int var7 = 0;
		do{
			if ((var7) < (var6)){
				Direction var8 = var5[var7];
				if (var8.getAxis().isHorizontal()){
					Direction var9 = var8.getOpposite();
					var2 = ((BlockState)(var2.setValue((Property)(FACING),(Comparable)(var9))));
					if ((var1.getLevel().getBlockState(var3.offset(((Direction)(var2.getValue((Property)(FACING)))).getOpposite().getNormal())).getBlock()) == (ModBlocks.FLAG.get())){
						return null;
					}else{
						if (var2.canSurvive((LevelReader)(var1.getLevel()),var3)){
							return var2;
						}
					}
				}
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
		return null;
	}
	public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
		return new FlagBlockEntity(var1,var2);
	}
	public BlockState playerWillDestroy(Level var1, BlockPos var2, BlockState var3, Player var4) {
		this.breakFlag((LevelAccessor)(var1),var2,(LivingEntity)(var4));
		return Blocks.AIR.defaultBlockState();
	}
	@Deprecated()
	public List<ItemStack> getDrops(BlockState var1, LootParams.Builder var2) {
		return new ArrayList();
	}
	public void onProjectileHit(Level var1, BlockState var2, BlockHitResult var3, Projectile var4) {
		if (var1.isClientSide){
			return;
		}else{
			boolean var5 = var4.isOnFire();
			boolean var6 = false;
			if (var4 instanceof NuProjectileEntity){
				NuProjectileEntity var7 = ((NuProjectileEntity)(var4));
				if (var7.getParent().isPresent()){
					var5 = (var4.isOnFire()) || (((IAbility)(var7.getParent().get())).getCore().getSourceElement().equals(SourceElement.FIRE));
					var6 = ((IAbility)(var7.getParent().get())).getCore().getSourceElement().equals(SourceElement.WATER);
				}
			}
			if ((!(var5)) && (!(var6))){

			}else{
				BlockEntity var7_1 = var1.getBlockEntity(var3.getBlockPos());
				if (((var7_1) != (null)) && (var7_1 instanceof FlagBlockEntity)){
					FlagBlockEntity var8 = ((FlagBlockEntity)(var7_1));
					if (var8.isSub()){
						var8 = var8.getMaster();
					}
					BlockPos var9 = var8.getBlockPos();
					if (var5){
						var8.setOnFire(true);
						var8.setLastAttacker((Entity)(var4));
						ModNetwork.sendToAllAroundDistance(new SSetFlagOnFirePacket(var9,true),((ServerLevel)(var1)),var3.getLocation(),256);
					}else{
						if (var6){
							var8.setOnFire(false);
							ModNetwork.sendToAllAroundDistance(new SSetFlagOnFirePacket(var9,false),((ServerLevel)(var1)),var3.getLocation(),256);
						}else{
							return;
						}
					}
				}else{
					return;
				}
			}
			return;
		}
	}
	public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, @Nullable() LivingEntity var4, ItemStack var5) {
		BlockEntity var6 = var1.getBlockEntity(var2);
		if (((var6) != (null)) && (var6 instanceof FlagBlockEntity)){
			FlagBlockEntity var7 = ((FlagBlockEntity)(var6));
			var7.setOwner(var4);
			if ((var5.has(ModDataComponents.FLAG_DATA)) && (((FlagData)(var5.get(ModDataComponents.FLAG_DATA))).jollyRoger().isPresent())){
				var7.setJollyRoger(((JollyRoger)(((FlagData)(var5.get(ModDataComponents.FLAG_DATA))).jollyRoger().get())));
			}
			boolean var8 = false;
			if (((var4) != (null)) && (var4 instanceof Player)){
				Player var9 = ((Player)(var4));
				var8 = var9.getAbilities().instabuild;
			}
			placeSubs((LevelAccessor)(var1),var2,var3,var8);
			if (((var4) != (null)) && (var4 instanceof Player)){
				Player var9_1 = ((Player)(var4));
				if (((FlagPlacedEvent)(NeoForge.EVENT_BUS.post((Event)(new FlagPlacedEvent(var9_1,var3,var2,var7))))).isCanceled()){
					var1.setBlock(var2,Blocks.AIR.defaultBlockState(),3);
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	public static void placeSubs(LevelAccessor var0, BlockPos var1, BlockState var2, boolean var3) {
		FlagBlockEntity var4 = ((FlagBlockEntity)(var0.getBlockEntity(var1)));
		Direction var5 = ((Direction)(var2.getValue((Property)(FACING))));
		CanvasSize var6 = ((CanvasSize)(var2.getValue((Property)(SIZE))));
		if ((var6.ordinal()) > (CanvasSize.SMALL.ordinal())){
			int var7 = SCALE_XZ[var6.ordinal()];
			int var8 = SCALE_Y[var6.ordinal()];
			int var9 = 0;
			LOOP_1:
			do{
				if ((var9) < (var8)){
					int var10 = 0;
					do{
						if ((var10) < (var7)){
							if (((var9) == (0)) && ((var10) == (0))){
								var10++;
								continue;
							}else{
								BlockPos var11 = var1.relative(var5,var10).offset(0,-(var9),0);
								if ((var0.getBlockState(var11).isAir()) && (var0.setBlock(var11,((Block)(ModBlocks.FLAG.get())).defaultBlockState(),3))){
									FlagBlockEntity var12 = ((FlagBlockEntity)(var0.getBlockEntity(var11)));
									if ((var12) != (null)){
										var12.setMaster(var4);
										var4.addSub(var11);
										var10++;
										continue;
									}else{
										var10++;
										continue;
									}
								}else{
									var4.breakAllBlocks(var3);
									return;
								}
							}
						}else{
							break;
						}
					} while (true);
					var9++;
					continue LOOP_1;
				}else{
					break;
				}
			} while (true);
		}
	}
	private void breakFlag(LevelAccessor var1, BlockPos var2, @Nullable() LivingEntity var3) {
		BlockEntity var4 = var1.getBlockEntity(var2);
		if (((var4) != (null)) && (var4 instanceof FlagBlockEntity)){
			FlagBlockEntity var5 = ((FlagBlockEntity)(var4));
			if (var5.isSub()){
				var5 = var5.getMaster();
			}
			if ((var5) == (null)){
				return;
			}else{
				FlagBlockEntity var6 = var5.getMaster();
				BlockState var7 = var1.getBlockState(var6.getBlockPos());
				if (var7.isAir()){
					return;
				}else{
					ItemStack var8 = new ItemStack(((ItemLike)(ModBlocks.FLAG.get())));
					if (var7.hasProperty((Property)(SIZE))){
						FlagItem.setCanvasSize(var8,((CanvasSize)(var7.getValue((Property)(SIZE)))));
					}
					boolean var9 = false;
					if (((var3) != (null)) && (var3 instanceof Player)){
						var9 = ((Player)(var3)).getAbilities().instabuild;
					}
					if (var1 instanceof Level){
						Level var10 = ((Level)(var1));
						if (!(var9)){
							var1.addFreshEntity((Entity)(new ItemEntity(var10,(double)(var2.getX()),(double)(var2.getY()),(double)(var2.getZ()),var8)));
						}
					}
					var6.breakAllBlocks(true);
					NeoForge.EVENT_BUS.post((Event)(new FlagDestroyedEvent(var7,var2,var6.getLastAttacker(),var6)));
				}
			}
		}
	}
	@Nullable()
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
		return (BlockEntityTicker<T>) (BlockEntityTicker) (createTickerHelper((BlockEntityType)(var3),(BlockEntityType)(((BlockEntityType)(ModBlockEntities.FLAG.get()))),(BlockEntityTicker)(((net.minecraft.world.level.block.entity.BlockEntityTicker)(((net.minecraft.world.level.block.entity.BlockEntityTicker)(FlagBlockEntity::tick)))))));
	}
	public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
		return true;
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean skipRendering(BlockState var1, BlockState var2, Direction var3) {
		return true;
	}
	public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
		int var7 = 0;
		if ((var4.getBlockEntity(var5)) != (null)){
			BlockEntity var8 = var4.getBlockEntity(var5);
			if (var8 instanceof FlagBlockEntity){
				FlagBlockEntity var9 = ((FlagBlockEntity)(var8));
				if (var9.isSub()){
					var7 = 1;
				}
			}
		}else{
			var7 = 0;
		}
		if (((var7) == (0)) && (((var2.getOpposite()) == (var1.getValue((Property)(FACING)))) && (!(var1.canSurvive((LevelReader)(var4),var5))))){
			this.breakFlag(var4,var5,(LivingEntity)(null));
			return Blocks.AIR.defaultBlockState();
		}else{
			return var1;
		}
	}
	public BlockState rotate(BlockState var1, Rotation var2) {
		return ((BlockState)(var1.setValue((Property)(FACING),(Comparable)(var2.rotate(((Direction)(var1.getValue((Property)(FACING)))))))));
	}
	public BlockState mirror(BlockState var1, Mirror var2) {
		return var1.rotate(var2.getRotation(((Direction)(var1.getValue((Property)(FACING))))));
	}
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
		Property[] var2 = new Property[]{FACING};
		var1.add(var2);
		Property[] var3 = new Property[]{SIZE};
		var1.add(var3);
	}
	public boolean isPathfindable(BlockState var1, PathComputationType var2) {
		return false;
	}
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return (MapCodec<? extends BaseEntityBlock>) (MapCodec) (CODEC);
	}
	static  {
		int[] var0 = new int[]{1,3,6,9};
		SCALE_XZ = var0;
		int[] var1 = new int[]{1,2,4,6};
		SCALE_Y = var1;
		FACING = HorizontalDirectionalBlock.FACING;
		SIZE = (EnumProperty) (BlocksHelper.CANVAS_SIZE);
	}
}