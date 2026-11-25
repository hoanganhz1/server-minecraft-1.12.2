package xyz.pixelatedw.mineminenomi.datagen.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModTags$Biomes;

public class ModBiomeTagsProvider extends TagsProvider<Biome> {
	public ModBiomeTagsProvider(PackOutput var1, CompletableFuture<HolderLookup.Provider> var2, @Nullable() ExistingFileHelper var3) {
		super(var1,Registries.BIOME,var2,"mineminenomi",var3);
	}
	protected void addTags(HolderLookup.Provider var1) {
		ResourceKey[] var2 = new ResourceKey[]{Biomes.MANGROVE_SWAMP,Biomes.SWAMP};
		this.tag(ModTags$Biomes.IS_SWAMP).add(var2);
		ResourceKey[] var3 = new ResourceKey[]{Biomes.DESERT,Biomes.STONY_SHORE};
		this.tag(ModTags$Biomes.CAN_SPAWN_DUGONGS).addTag(BiomeTags.IS_BEACH).add(var3);
		TagKey[] var4 = new TagKey[]{ModTags$Biomes.CAN_SPAWN_DUGONGS,BiomeTags.IS_FOREST,Tags.Biomes.IS_PLAINS,BiomeTags.IS_BADLANDS,BiomeTags.IS_SAVANNA,BiomeTags.IS_TAIGA,ModTags$Biomes.IS_SWAMP};
		this.tag(ModTags$Biomes.CAN_SPAWN_WANDERING_DUGONG).addTags(var4);
		TagKey[] var5 = new TagKey[]{BiomeTags.IS_FOREST,Tags.Biomes.IS_PLAINS,BiomeTags.IS_HILL,BiomeTags.IS_SAVANNA,BiomeTags.IS_TAIGA};
		this.tag(ModTags$Biomes.CAN_SPAWN_BIG_DUCK).addTags(var5).add(Biomes.DESERT);
		TagKey[] var6 = new TagKey[]{BiomeTags.IS_FOREST,Tags.Biomes.IS_PLAINS,BiomeTags.IS_HILL,BiomeTags.IS_SAVANNA,BiomeTags.IS_TAIGA};
		this.tag(ModTags$Biomes.CAN_SPAWN_DEN_DEN_MUSHI).addTags(var6);
		TagKey[] var7 = new TagKey[]{BiomeTags.IS_FOREST,BiomeTags.IS_JUNGLE};
		this.tag(ModTags$Biomes.CAN_SPAWN_HUMANDRILL).addTags(var7);
		TagKey[] var8 = new TagKey[]{BiomeTags.IS_FOREST};
		this.tag(ModTags$Biomes.CAN_SPAWN_GORILLAS).addTags(var8);
		ResourceKey[] var9 = new ResourceKey[]{Biomes.GROVE,Biomes.FROZEN_PEAKS,Biomes.SNOWY_SLOPES,Biomes.SNOWY_TAIGA,Biomes.SNOWY_PLAINS,Biomes.ICE_SPIKES};
		this.tag(ModTags$Biomes.CAN_SPAWN_LAPAHN).add(var9);
		ResourceKey[] var10 = new ResourceKey[]{Biomes.FROZEN_PEAKS,Biomes.SNOWY_SLOPES,Biomes.SNOWY_TAIGA,Biomes.SNOWY_PLAINS,Biomes.ICE_SPIKES};
		this.tag(ModTags$Biomes.CAN_SPAWN_WHITE_WALKIE).add(var10);
		ResourceKey[] var11 = new ResourceKey[]{Biomes.DESERT,Biomes.BADLANDS};
		this.tag(ModTags$Biomes.CAN_SPAWN_BANANAWANI).addTag(BiomeTags.IS_BEACH).add(var11);
		TagKey[] var12 = new TagKey[]{BiomeTags.IS_FOREST,Tags.Biomes.IS_PLAINS,BiomeTags.IS_HILL,BiomeTags.IS_SAVANNA,BiomeTags.IS_TAIGA,BiomeTags.IS_BEACH,BiomeTags.IS_JUNGLE,BiomeTags.IS_MOUNTAIN};
		this.tag(ModTags$Biomes.CAN_SPAWN_HUMANS).addTags(var12);
		ResourceKey[] var13 = new ResourceKey[]{Biomes.OCEAN,Biomes.DEEP_OCEAN,Biomes.LUKEWARM_OCEAN,Biomes.DEEP_LUKEWARM_OCEAN,Biomes.WARM_OCEAN};
		this.tag(ModTags$Biomes.CAN_SPAWN_FISH).add(var13);
		ResourceKey[] var14 = new ResourceKey[]{Biomes.DEEP_OCEAN,Biomes.DEEP_LUKEWARM_OCEAN};
		this.tag(ModTags$Biomes.CAN_SPAWN_FIGHTING_FISH).add(var14);
		this.tag(ModTags$Biomes.CAN_SPAWN_FLYING_FISH).addTag(ModTags$Biomes.IS_SWAMP);
		TagKey[] var15 = new TagKey[]{BiomeTags.IS_SAVANNA,BiomeTags.IS_HILL,BiomeTags.IS_BADLANDS,Tags.Biomes.IS_PLAINS};
		this.tag(ModTags$Biomes.HAS_STRUCTURE_CAMP).addTags(var15);
		TagKey[] var16 = new TagKey[]{BiomeTags.IS_FOREST,BiomeTags.IS_TAIGA,BiomeTags.IS_SAVANNA,Tags.Biomes.IS_PLAINS};
		this.tag(ModTags$Biomes.HAS_STRUCTURE_SMALL_BASE).addTags(var16);
		TagKey[] var17 = new TagKey[]{BiomeTags.IS_FOREST,BiomeTags.IS_TAIGA,BiomeTags.IS_SAVANNA,Tags.Biomes.IS_PLAINS};
		this.tag(ModTags$Biomes.HAS_STRUCTURE_LARGE_BASE).addTags(var17);
		TagKey[] var18 = new TagKey[]{BiomeTags.IS_SAVANNA,BiomeTags.IS_HILL,BiomeTags.IS_BADLANDS,Tags.Biomes.IS_PLAINS};
		this.tag(ModTags$Biomes.HAS_STRUCTURE_WATCH_TOWER).addTags(var18);
		TagKey[] var19 = new TagKey[]{BiomeTags.IS_OCEAN,BiomeTags.IS_BEACH};
		this.tag(ModTags$Biomes.HAS_STRUCTURE_SKY_ISLAND).addTags(var19).add(Biomes.STONY_SHORE);
		TagKey[] var20 = new TagKey[]{BiomeTags.IS_FOREST,BiomeTags.IS_TAIGA,BiomeTags.IS_SAVANNA,Tags.Biomes.IS_PLAINS};
		this.tag(ModTags$Biomes.HAS_STRUCTURE_TRAINER).addTags(var20);
		TagKey[] var21 = new TagKey[]{BiomeTags.IS_OCEAN};
		this.tag(ModTags$Biomes.HAS_STRUCTURE_SMALL_SHIP).addTags(var21);
		TagKey[] var22 = new TagKey[]{BiomeTags.IS_OCEAN,BiomeTags.IS_DEEP_OCEAN};
		this.tag(ModTags$Biomes.HAS_STRUCTURE_MEDIUM_SHIP).addTags(var22);
		TagKey[] var23 = new TagKey[]{BiomeTags.IS_DEEP_OCEAN};
		this.tag(ModTags$Biomes.HAS_STRUCTURE_BIG_SHIP).addTags(var23);
		TagKey[] var24 = new TagKey[]{ModTags$Biomes.HAS_STRUCTURE_MEDIUM_SHIP,Tags.Biomes.IS_DESERT};
		this.tag(ModTags$Biomes.HAS_STRUCTURE_GHOST_SHIP).addTags(var24);
		TagKey[] var25 = new TagKey[]{BiomeTags.IS_BEACH,BiomeTags.IS_OCEAN};
		this.tag(ModTags$Biomes.HAS_KAIROSEKI_ORE).addTags(var25).add(Biomes.STONY_SHORE);
	}
}