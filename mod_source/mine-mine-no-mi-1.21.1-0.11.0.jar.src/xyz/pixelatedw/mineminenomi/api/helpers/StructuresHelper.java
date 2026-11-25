package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.LootTable;
import xyz.pixelatedw.mineminenomi.init.ModTags$Structures;

public class StructuresHelper {
	public static Optional<StructureTemplate> getStructure(ServerLevel var0, ResourceLocation var1) {
		return (Optional<StructureTemplate>) (Optional) (var0.getStructureManager().get(var1));
	}
	public static boolean spawnStructure(ServerLevel var0, ResourceLocation var1, BlockPos var2, StructurePlaceSettings var3) {
		Optional var4 = getStructure(var0,var1);
		if (var4.isEmpty()){
			return false;
		}else{
			BlockState var5 = var0.getBlockState(var2);
			var0.sendBlockUpdated(var2,var5,var5,3);
			((StructureTemplate)(var4.get())).placeInWorld((ServerLevelAccessor)(var0),var2,var2,var3,RandomSource.create(Util.getMillis()),2);
			return true;
		}
	}
	public static void spawnLoot(LevelAccessor var0, BlockPos var1, ResourceKey<LootTable> var2) {
		var0.setBlock(var1,Blocks.AIR.defaultBlockState(),3);
		BlockEntity var3 = var0.getBlockEntity(var1.below());
		if (var3 instanceof ChestBlockEntity){
			((ChestBlockEntity)(var3)).setLootTable(var2,var0.getRandom().nextLong());
		}
	}
	public static boolean isInsideShip(ServerLevel var0, BlockPos var1) {
		return var0.structureManager().getStructureWithPieceAt(var1,ModTags$Structures.IS_SHIP).isValid();
	}
	public static boolean isInsideMarineStructure(ServerPlayer var0) {
		return var0.serverLevel().structureManager().getStructureWithPieceAt(var0.blockPosition(),ModTags$Structures.MARINE).isValid();
	}
	public static boolean isInsideRevoStructure(ServerPlayer var0) {
		return var0.serverLevel().structureManager().getStructureWithPieceAt(var0.blockPosition(),ModTags$Structures.REVOLUTIONARY).isValid();
	}
	@Nullable()
	public static StructureStart getStructureAt(ServerLevel var0, BlockPos var1) {
		Iterator var2 = var0.structureManager().registryAccess().registryOrThrow(Registries.STRUCTURE).entrySet().iterator();
		do{
			if (var2.hasNext()){
				Map.Entry var3 = ((Map.Entry)(var2.next()));
				StructureStart var4 = var0.structureManager().getStructureAt(var1,((Structure)(var3.getValue())));
				if ((var4) != (StructureStart.INVALID_START)){
					return var4;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return null;
	}
}