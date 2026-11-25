package xyz.pixelatedw.mineminenomi.api.level.structure.placement;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Function9;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Vec3i;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

public class ExtendedRandomSpreadStructurePlacement extends RandomSpreadStructurePlacement {
	// Fields
	public static final MapCodec<ExtendedRandomSpreadStructurePlacement> CODEC;
	private final int spacing;
	private final int separation;
	private final RandomSpreadType spreadType;
	private final List<ExtendedRandomSpreadStructurePlacement$SuperExclusionZone> exclusionZones;
	private final int minDistanceFromWorldCenter;

	
	public ExtendedRandomSpreadStructurePlacement(Vec3i var1, StructurePlacement.FrequencyReductionMethod var2, float var3, int var4, int var5, int var6, RandomSpreadType var7, List<ExtendedRandomSpreadStructurePlacement$SuperExclusionZone> var8, int var9) {
		super(var1,var2,var3,var4,Optional.empty(),var5,var6,var7);
		this.spacing = var5;
		this.separation = var6;
		this.spreadType = var7;
		this.exclusionZones = var8;
		this.minDistanceFromWorldCenter = var9;
	}
	public ExtendedRandomSpreadStructurePlacement(int var1, int var2, RandomSpreadType var3, int var4) {
		this(Vec3i.ZERO,StructurePlacement.FrequencyReductionMethod.DEFAULT,1F,var4,var1,var2,var3,List.of(),0);
	}
	public int spacing() {
		return this.spacing;
	}
	public int separation() {
		return this.separation;
	}
	public RandomSpreadType spreadType() {
		return this.spreadType;
	}
	public int minDistanceFromWorldCenter() {
		return this.minDistanceFromWorldCenter;
	}
	public List<ExtendedRandomSpreadStructurePlacement$SuperExclusionZone> exclusionZones() {
		return this.exclusionZones;
	}
	public ChunkPos getPotentialStructureChunk(long var1, int var2, int var3) {
		int var4 = Math.floorDiv(var2,this.spacing);
		int var5 = Math.floorDiv(var3,this.spacing);
		WorldgenRandom var6 = new WorldgenRandom((RandomSource)(new LegacyRandomSource(0L)));
		var6.setLargeFeatureWithSalt(var1,var4,var5,this.salt());
		int var7 = (this.spacing) - (this.separation);
		int var8 = this.spreadType.evaluate((RandomSource)(var6),var7);
		int var9 = this.spreadType.evaluate((RandomSource)(var6),var7);
		return new ChunkPos(((var4) * (this.spacing)) + (var8),((var5) * (this.spacing)) + (var9));
	}
	protected boolean isPlacementChunk(ChunkGeneratorStructureState var1, int var2, int var3) {
		if ((this.minDistanceFromWorldCenter) > (0)){
			int var4 = (var2) * (16);
			int var5 = (var3) * (16);
			if ((((var4) * (var4)) + ((var5) * (var5))) < ((this.minDistanceFromWorldCenter) * (this.minDistanceFromWorldCenter))){
				return false;
			}
		}
		ChunkPos var4_1 = this.getPotentialStructureChunk(var1.getLevelSeed(),var2,var3);
		return ((var4_1.x) == (var2)) && ((var4_1.z) == (var3));
	}
	public boolean isStructureChunk(ChunkGeneratorStructureState var1, int var2, int var3) {
		if (!(super.isStructureChunk(var1,var2,var3))){
			return false;
		}else{
			Iterator var4 = this.exclusionZones.iterator();
			do{
				if (var4.hasNext()){
					if (((ExtendedRandomSpreadStructurePlacement$SuperExclusionZone)(var4.next())).isPlacementForbidden(var1,var2,var3)){
						return false;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return true;
		}
	}
	public StructurePlacementType<?> type() {
		return (StructurePlacementType<?>) (StructurePlacementType) (((StructurePlacementType)(ModStructures.EXTENDED_RANDOM_SPREAD_PLACEMENT.get())));
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return l0.group((App)(Vec3i.offsetCodec(16).optionalFieldOf("locate_offset",Vec3i.ZERO).forGetter((Function)(((Function<ExtendedRandomSpreadStructurePlacement, Vec3i>)(StructurePlacement::locateOffset))))),(App)(StructurePlacement.FrequencyReductionMethod.CODEC.optionalFieldOf("frequency_reduction_method",StructurePlacement.FrequencyReductionMethod.DEFAULT).forGetter((Function)(((Function<ExtendedRandomSpreadStructurePlacement, StructurePlacement.FrequencyReductionMethod>)(StructurePlacement::frequencyReductionMethod))))),(App)(Codec.floatRange(0F,1F).optionalFieldOf("frequency",Float.valueOf(1F)).forGetter((Function)(((Function<ExtendedRandomSpreadStructurePlacement, Float>)(StructurePlacement::frequency))))),(App)(ExtraCodecs.NON_NEGATIVE_INT.fieldOf("salt").forGetter((Function)(((Function<ExtendedRandomSpreadStructurePlacement, Integer>)(StructurePlacement::salt))))),(App)(Codec.intRange(0,4096).fieldOf("spacing").forGetter((Function)(((Function<ExtendedRandomSpreadStructurePlacement, Integer>)(ExtendedRandomSpreadStructurePlacement::spacing))))),(App)(Codec.intRange(0,4096).fieldOf("separation").forGetter((Function)(((Function<ExtendedRandomSpreadStructurePlacement, Integer>)(ExtendedRandomSpreadStructurePlacement::separation))))),(App)(RandomSpreadType.CODEC.optionalFieldOf("spread_type",RandomSpreadType.LINEAR).forGetter((Function)(((Function<ExtendedRandomSpreadStructurePlacement, RandomSpreadType>)(ExtendedRandomSpreadStructurePlacement::spreadType))))),(App)(((com.mojang.serialization.Codec)(ExtendedRandomSpreadStructurePlacement$SuperExclusionZone.CODEC.listOf())).optionalFieldOf((String)("exclusion_zones"),(Object)(List.of())).forGetter((Function)(((Function<ExtendedRandomSpreadStructurePlacement, List>)(ExtendedRandomSpreadStructurePlacement::exclusionZones))))),(App)(Codec.intRange(0,1048576).optionalFieldOf("min_distance_from_world_center",Integer.valueOf(0)).forGetter((Function)(((Function<ExtendedRandomSpreadStructurePlacement, Integer>)(ExtendedRandomSpreadStructurePlacement::minDistanceFromWorldCenter)))))).apply((Applicative)(l0),(Function9)(((com.mojang.datafixers.util.Function9)(ExtendedRandomSpreadStructurePlacement::new))));
		};
		CODEC = (MapCodec) (RecordCodecBuilder.mapCodec(var0).validate((Function)(((Function<ExtendedRandomSpreadStructurePlacement, DataResult>)(ExtendedRandomSpreadStructurePlacement::validate)))));
	}
private static DataResult<ExtendedRandomSpreadStructurePlacement> validate(ExtendedRandomSpreadStructurePlacement var0) {
		if ((var0.spacing) <= (var0.separation)){
			Supplier<String> var1 = () -> {
			return "Spacing has to be larger than separation";
		};
			return (DataResult<ExtendedRandomSpreadStructurePlacement>) (DataResult) (DataResult.error((Supplier)(var1)));
		}else{
			return DataResult.success(var0);
		}
	}}