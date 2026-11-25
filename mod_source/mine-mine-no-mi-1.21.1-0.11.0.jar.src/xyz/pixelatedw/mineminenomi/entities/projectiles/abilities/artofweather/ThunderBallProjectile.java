package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class ThunderBallProjectile extends WeatherBallProjectile {
	public ThunderBallProjectile(EntityType<? extends ThunderBallProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public ThunderBallProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.THUNDER_BALL.get())),var1,var2,var3);
		this.setDamage(2F);
	}
}