package xyz.pixelatedw.mineminenomi.entities.clouds;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.RainTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.ThunderboltTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.ThunderstormTempo;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.CloudEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.CoolBallProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.ThunderBallProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.WeatherBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WeatherCloudEntity extends CloudEntity {
	// Fields
	private static final int PARTICLE_RATE = 5;
	private static final int TICK_RATE = 50;
	private List<WeatherBallProjectile> weatherBalls;
	private boolean charged;
	private boolean superCharged;
	private int particleTick;
	private int updateTick;
	private AbilityData props;

	public WeatherCloudEntity(EntityType<? extends WeatherCloudEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.weatherBalls = new ArrayList();
		this.charged = false;
		this.superCharged = false;
		this.particleTick = 5;
		this.updateTick = 50;
	}
	public WeatherCloudEntity(Level var1, LivingEntity var2) {
		super(((EntityType)(ModEntities.WEATHER_CLOUD.get())),var1,var2);
		this.weatherBalls = new ArrayList();
		this.charged = false;
		this.superCharged = false;
		this.particleTick = 5;
		this.updateTick = 50;
		this.setOwner(var2);
		this.setLife(200);
	}
	public void tick() {
		super.tick();
		if (!(this.level().isClientSide)){
			int var1 = (this.particleTick) - (1);
			this.particleTick = var1;
			if ((var1) <= (0)){
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.WEATHER_CLOUD.get())),(Entity)(this),this.getX(),this.getY(),this.getZ());
				if ((!(this.charged)) && (!(this.superCharged))){

				}else{
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.CHARGED_WEATHER_CLOUD.get())),(Entity)(this),this.getX(),this.getY(),this.getZ());
				}
				this.particleTick = 5;
			}
			int var2 = (this.updateTick) - (1);
			this.updateTick = var2;
			if ((var2) > (0)){
				return;
			}else{
				this.updateTick = 50;
				if ((this.props) == (null)){
					this.props = ((AbilityData)(((java.util.Optional)(AbilityData.get(this.getOwner()))).orElse((Object)(null))));
				}
				if (((this.getLife()) > (0)) && (((this.getOwner()) != (null)) && ((this.props) != (null)))){
					this.setLife((this.getLife()) - (1));
					Class var3 = ThunderBallProjectile.class;
					Objects.requireNonNull(var3);
					Class var4 = ThunderBallProjectile.class;
					Objects.requireNonNull(var4);
					List var5 = ((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.weatherBalls.stream())).filter((Predicate)(((java.util.function.Predicate)(((Predicate<WeatherBallProjectile>)(var3::isInstance)))))).map((Function)(((Function<WeatherBallProjectile, ThunderBallProjectile>)(var4::cast)))))).collect((Collector)(Collectors.toList()))));
					Class var6 = CoolBallProjectile.class;
					Objects.requireNonNull(var6);
					Class var7 = CoolBallProjectile.class;
					Objects.requireNonNull(var7);
					List var8 = ((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.weatherBalls.stream())).filter((Predicate)(((java.util.function.Predicate)(((Predicate<WeatherBallProjectile>)(var6::isInstance)))))).map((Function)(((Function<WeatherBallProjectile, CoolBallProjectile>)(var7::cast)))))).collect((Collector)(Collectors.toList()))));
					int var9 = var5.size();
					int var10 = var8.size();
					ThunderstormTempo var11 = ((ThunderstormTempo)(this.props.getPassiveAbility(((AbilityCore)(ThunderstormTempo.INSTANCE.get())))));
					if (((((var11) != (null)) && ((var11.canUse(this.getOwner()).isSuccess()) && ((var9) >= (5)))) ? (1) : (0)) != (0)){
						this.superCharged = true;
						var11.useTempo(this.getOwner(),this);
						return;
					}else{
						ThunderboltTempo var12 = ((ThunderboltTempo)(this.props.getPassiveAbility(((AbilityCore)(ThunderboltTempo.INSTANCE.get())))));
						if (((((var12) != (null)) && ((var12.canUse(this.getOwner()).isSuccess()) && ((var9) > (0)))) ? (1) : (0)) != (0)){
							this.charged = true;
							var12.useTempo(this.getOwner(),this);
							return;
						}else{
							RainTempo var13 = ((RainTempo)(this.props.getEquippedOrPassiveAbility(((AbilityCore)(RainTempo.INSTANCE.get())))));
							if (((((var13) != (null)) && ((var13.canUse(this.getOwner()).isSuccess()) && ((var10) >= (3)))) ? (1) : (0)) != (0)){
								var13.use(this.getOwner());
								return;
							}else{
								return;
							}
						}
					}
				}else{
					this.discard();
					return;
				}
			}
		}else{
			return;
		}
	}
	public boolean isCharged() {
		return this.charged;
	}
	public boolean isSuperCharged() {
		return this.superCharged;
	}
	public void addWeatherBall(WeatherBallProjectile var1) {
		this.weatherBalls.add(var1);
	}
}