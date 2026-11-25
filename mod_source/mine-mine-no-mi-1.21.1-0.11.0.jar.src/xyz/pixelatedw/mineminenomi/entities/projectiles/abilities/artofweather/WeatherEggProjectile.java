package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather;

import java.util.Optional;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.WeatherCloudTempo;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.clouds.WeatherCloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class WeatherEggProjectile extends WeatherBallProjectile {
	public WeatherEggProjectile(EntityType<? extends WeatherEggProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public WeatherEggProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.WEATHER_EGG.get())),var1,var2,var3);
	}
	public void tick() {
		super.tick();
		if ((!(this.level().isClientSide)) && (((this.getOwner()) != (null)) && ((this.tickCount) >= (100)))){
			AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get(this.getOwner()))).orElse((Object)(null))));
			if ((var1) == (null)){
				return;
			}else{
				WeatherCloudTempo var2 = ((WeatherCloudTempo)(var1.getEquippedOrPassiveAbility(((AbilityCore)(WeatherCloudTempo.INSTANCE.get())))));
				if (((((var2) != (null)) && (var2.canUse(this.getOwner()).isSuccess())) ? (1) : (0)) != (0)){
					WeatherCloudEntity var3 = new WeatherCloudEntity((Level)(this.level()),(LivingEntity)(this.getOwner()));
					var3.setSize(WEATHER_CLOUD_SIZE);
					var3.moveTo(this.getX(),(this.getY()) + (1D),this.getZ(),0F,0F);
					AbilityHelper.setDeltaMovement(var3,0D,0D,0D);
					this.level().addFreshEntity((Entity)(var3));
				}
				this.discard();
				return;
			}
		}else{
			return;
		}
	}
}