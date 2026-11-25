package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.api.damagesources.BaseDamageSource;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class KagePassivesHandler {
	public static void tryBurningInSun(LivingEntity var0) {
		if (((Boolean)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::hasShadow)))).orElse(Boolean.valueOf(true)))).booleanValue()){
			return;
		}else{
			if (!(var0.level().isDay())){
				return;
			}else{
				if ((((((java.lang.Object)(var0.getMainHandItem().getItem())).equals(ModWeapons.UMBRELLA.get())) || (((java.lang.Object)(var0.getOffhandItem().getItem())).equals(ModWeapons.UMBRELLA.get()))) ? (1) : (0)) != (0)){
					return;
				}else{
					BlockPos var1 = BlockPos.containing(var0.getX(),var0.getEyeY(),var0.getZ());
					if (var0.level().canSeeSky(var1)){
						var0.igniteForSeconds(2F);
						if (((var0.tickCount) % (10)) == (0)){
							var0.hurt((DamageSource)(ModDamageSources.getInstance((LevelAccessor)(var0.level())).sunIncineration()),5F);
						}else{
							return;
						}
					}
					return;
				}
			}
		}
	}
}