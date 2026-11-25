package xyz.pixelatedw.mineminenomi.mixins;

import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.handlers.world.StructuresHandler;

@Mixin(value={Structure.class})
public class StructureMixin {
	@Inject(method={"generate"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$generate(RegistryAccess var1, ChunkGenerator var2, BiomeSource var3, RandomState var4, StructureTemplateManager var5, long var6, ChunkPos var7, int var8, LevelHeightAccessor var9, Predicate<Holder<Biome>> var10, CallbackInfoReturnable<StructureStart> var11) {
		Structure var12 = ((Structure)(this));
		Registry var13 = ((Registry)(var1.registry(Registries.STRUCTURE).get()));
		WorldgenRandom var14 = new WorldgenRandom((RandomSource)(new LegacyRandomSource(0L)));
		var14.setLargeFeatureSeed(var6,var7.x,var7.z);
		Optional var15 = var13.getHolder(var13.getId(var12));
		if ((var15.isPresent()) && (StructuresHandler.isStructureBlocked((RandomSource)(var14),((Holder)(var15.get()))))){
			var11.setReturnValue(StructureStart.INVALID_START);
		}
	}
}