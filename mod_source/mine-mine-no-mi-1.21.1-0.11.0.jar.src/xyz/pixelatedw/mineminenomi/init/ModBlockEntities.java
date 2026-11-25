package xyz.pixelatedw.mineminenomi.init;

import com.mojang.datafixers.types.Type;
import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.CustomSpawnerBlockEntity;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.PoneglyphBlockEntity;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.WantedPosterBlockEntity;

public class ModBlockEntities {
	// Fields
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CustomSpawnerBlockEntity>> CUSTOM_SPAWNER;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WantedPosterBlockEntity>> WANTED_POSTER;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PoneglyphBlockEntity>> PONEGLYPH;
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FlagBlockEntity>> FLAG;

	public static void init() {
	}
	static  {
		Supplier<BlockEntityType> var0 = () -> {
			BlockEntityType.BlockEntitySupplier lv1_0 = CustomSpawnerBlockEntity::new;
			Block[] lv1_1 = new Block[]{((Block)(ModBlocks.CUSTOM_SPAWNER.get()))};
			return BlockEntityType.Builder.of(lv1_0,lv1_1).build((Type)(null));
		};
		CUSTOM_SPAWNER = (DeferredHolder) (ModRegistry.registerTileEntity("custom_spawner",(Supplier)(var0)));
		Supplier<BlockEntityType> var1 = () -> {
			BlockEntityType.BlockEntitySupplier lv2_0 = WantedPosterBlockEntity::new;
			Block[] lv2_1 = new Block[]{((Block)(ModBlocks.WANTED_POSTER.get()))};
			return BlockEntityType.Builder.of(lv2_0,lv2_1).build((Type)(null));
		};
		WANTED_POSTER = (DeferredHolder) (ModRegistry.registerTileEntity("wanted_poster",(Supplier)(var1)));
		Supplier<BlockEntityType> var2 = () -> {
			BlockEntityType.BlockEntitySupplier lv3_0 = PoneglyphBlockEntity::new;
			Block[] lv3_1 = new Block[]{((Block)(ModBlocks.PONEGLYPH.get()))};
			return BlockEntityType.Builder.of(lv3_0,lv3_1).build((Type)(null));
		};
		PONEGLYPH = (DeferredHolder) (ModRegistry.registerTileEntity("poneglyph",(Supplier)(var2)));
		Supplier<BlockEntityType> var3 = () -> {
			BlockEntityType.BlockEntitySupplier lv4_0 = FlagBlockEntity::new;
			Block[] lv4_1 = new Block[]{((Block)(ModBlocks.FLAG.get()))};
			return BlockEntityType.Builder.of(lv4_0,lv4_1).build((Type)(null));
		};
		FLAG = (DeferredHolder) (ModRegistry.registerTileEntity("flag",(Supplier)(var3)));
	}
}