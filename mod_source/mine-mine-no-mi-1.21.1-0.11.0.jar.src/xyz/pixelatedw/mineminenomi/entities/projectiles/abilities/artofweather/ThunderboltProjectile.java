package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather;

import java.awt.Color;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.ArtOfWeatherHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuVerticalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class ThunderboltProjectile extends NuVerticalLightningEntity {
	// Fields
	public static final float DAMAGE = 40F;

	public ThunderboltProjectile(EntityType<? extends ThunderboltProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public ThunderboltProjectile(Level var1, LivingEntity var2, double var3, double var4, double var5, float var6, IAbility var7) {
		super(((EntityType)(ModProjectiles.THUNDERBOLT.get())),var2,var3,var4,var5,var6,25F,var7);
		this.setAngle(30);
		this.setBranches(1);
		this.setSegments(15);
		this.setColor(ArtOfWeatherHelper.AOW_LIGHTNING_COLOR);
		this.setSize(0.2F);
		this.setDamage(40F);
		this.setMaxLife(10);
	}
}