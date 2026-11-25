package xyz.pixelatedw.mineminenomi.handlers.world;

import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.Structure;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModTags$Structures;

public class StructuresHandler {
	public static boolean isStructureBlocked(RandomSource var0, Holder<Structure> var1) {
		if (isBanned(var1,ModTags$Structures.IS_CAMP,var0,ServerConfig.getChanceForCampsSpawn())){
			return true;
		}else{
			if (isBanned(var1,ModTags$Structures.IS_SMALL_SHIP,var0,ServerConfig.getChanceForSmallShipsSpawn())){
				return true;
			}else{
				if (isBanned(var1,ModTags$Structures.IS_MEDIUM_SHIP,var0,ServerConfig.getChanceForMediumShipsSpawn())){
					return true;
				}else{
					if (isBanned(var1,ModTags$Structures.IS_LARGE_SHIP,var0,ServerConfig.getChanceForLargeShipsSpawn())){
						return true;
					}else{
						if (isBanned(var1,ModTags$Structures.IS_SMALL_BASE,var0,ServerConfig.getChanceForSmallBasesSpawn())){
							return true;
						}else{
							if (isBanned(var1,ModTags$Structures.IS_LARGE_BASE,var0,ServerConfig.getChanceForLargeBasesSpawn())){
								return true;
							}else{
								if (isBanned(var1,ModTags$Structures.IS_WATCH_TOWER,var0,ServerConfig.getChanceForWatchTowersSpawn())){
									return true;
								}else{
									if (isBanned(var1,ModTags$Structures.IS_TRAINING_STRUCTURE,var0,ServerConfig.getChanceForTrainingStructureSpawn())){
										return true;
									}else{
										if (isBanned(var1,ModTags$Structures.IS_SKY_ISLAND,var0,ServerConfig.getChanceForSkyIslandSpawn())){
											return true;
										}else{
											return false;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	private static boolean isBanned(Holder<Structure> var0, TagKey<Structure> var1, RandomSource var2, double var3) {
		return (var0.is(var1)) && (((double)(var2.nextInt(100))) >= (var3));
	}
}