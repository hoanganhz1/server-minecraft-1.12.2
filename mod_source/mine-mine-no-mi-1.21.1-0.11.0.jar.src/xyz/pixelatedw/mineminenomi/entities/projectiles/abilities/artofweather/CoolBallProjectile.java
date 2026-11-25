package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class CoolBallProjectile extends WeatherBallProjectile {
	public CoolBallProjectile(EntityType<? extends CoolBallProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public CoolBallProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.COOL_BALL.get())),var1,var2,var3);
		this.setDamage(0F);
	}
}