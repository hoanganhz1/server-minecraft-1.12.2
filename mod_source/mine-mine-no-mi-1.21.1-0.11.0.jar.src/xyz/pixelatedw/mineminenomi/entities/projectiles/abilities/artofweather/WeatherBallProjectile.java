package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.WeatherCloudTempo;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.clouds.WeatherCloudEntity;

public class WeatherBallProjectile extends NuProjectileEntity {
	// Fields
	protected static final EntityDimensions WEATHER_CLOUD_SIZE = EntityDimensions.fixed(40F,4F);
	protected Item weaponUsed;

	public WeatherBallProjectile(EntityType<? extends WeatherBallProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public WeatherBallProjectile(EntityType<? extends WeatherBallProjectile> var1, Level var2, LivingEntity var3, IAbility var4) {
		super((EntityType)(var1),var2,var3,var4);
		this.setMaxLife(300);
	}
	public void tick() {
		super.tick();
		AbilityHelper.setDeltaMovement((Entity)(this),(this.getDeltaMovement().x) / (1.5D),this.getDeltaMovement().y,(this.getDeltaMovement().z) / (1.5D));
		if ((this.tickCount) < (200)){
			this.getDeltaMovement().add(0D,0.35D,0D);
		}else{
			AbilityHelper.setDeltaMovement((Entity)(this),0D,0D,0D);
		}
		if ((!(this.level().isClientSide)) && (((this.getOwner()) != (null)) && ((this.tickCount) >= (50)))){
			Optional var1 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),(LivingEntity)(null),this.position(),10D,4D,10D,TargetPredicate.EVERYTHING,WeatherCloudEntity.class).stream().findFirst();
			if (var1.isPresent()){
				((WeatherCloudEntity)(var1.get())).addWeatherBall(this);
				if (this instanceof ThunderBallProjectile){
					((WeatherCloudEntity)(var1.get())).setLife((this.getLife()) + (200));
				}
				this.discard();
				return;
			}else{
				if ((this.tickCount) < (150)){
					return;
				}else{
					if (this instanceof CoolBallProjectile){
						List var2 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),(LivingEntity)(null),this.position(),6D,4D,6D,TargetPredicate.EVERYTHING,HeatBallProjectile.class);
						AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(this.getOwner()))).orElse((Object)(null))));
						if ((var3) == (null)){
							return;
						}else{
							WeatherCloudTempo var4 = ((WeatherCloudTempo)(var3.getPassiveAbility(((AbilityCore)(WeatherCloudTempo.INSTANCE.get())))));
							if (((var4) != (null)) && ((var4.canUse(this.getOwner()).isSuccess()) && ((var2.size()) > (0)))){
								var4.use(this.getOwner());
								WeatherCloudEntity var5 = new WeatherCloudEntity((Level)(this.level()),(LivingEntity)(this.getOwner()));
								var5.setSize(WEATHER_CLOUD_SIZE);
								var5.moveTo(this.getX(),(this.getY()) + (1D),this.getZ(),0F,0F);
								AbilityHelper.setDeltaMovement(var5,0D,0D,0D);
								this.level().addFreshEntity((Entity)(var5));
								var2.forEach((Consumer)(((Consumer<HeatBallProjectile>)(Entity::discard))));
								this.discard();
							}else{
								return;
							}
						}
					}
					return;
				}
			}
		}else{
			return;
		}
	}
	public Item getWeaponUsed() {
		return this.weaponUsed;
	}
}