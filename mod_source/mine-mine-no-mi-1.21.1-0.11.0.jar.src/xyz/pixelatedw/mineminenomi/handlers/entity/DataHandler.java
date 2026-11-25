package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class DataHandler {
	public static void initData(Entity var0) {
		if (var0 instanceof LivingEntity){
			LivingEntity var1 = ((LivingEntity)(var0));
			EntityStatsData.getOrCreate(var1);
			AbilityData.getOrCreate(var1);
		}
	}
}