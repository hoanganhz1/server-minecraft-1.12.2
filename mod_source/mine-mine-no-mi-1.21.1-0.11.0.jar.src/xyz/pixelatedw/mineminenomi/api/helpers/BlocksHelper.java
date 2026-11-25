package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;

public class BlocksHelper {
	// Fields
	public static final EnumProperty<CanvasSize> CANVAS_SIZE = EnumProperty.create("canvas_size",CanvasSize.class);
	public static final IntegerProperty AGE_4 = IntegerProperty.create("age",0,4);

	public static boolean always(BlockState var0, BlockGetter var1, BlockPos var2) {
		return true;
	}
	public static boolean never(BlockState var0, BlockGetter var1, BlockPos var2) {
		return false;
	}
	public static boolean never(BlockState var0, BlockGetter var1, BlockPos var2, EntityType<?> var3) {
		return false;
	}
	public static boolean always(BlockState var0, BlockGetter var1, BlockPos var2, EntityType<?> var3) {
		return true;
	}
}