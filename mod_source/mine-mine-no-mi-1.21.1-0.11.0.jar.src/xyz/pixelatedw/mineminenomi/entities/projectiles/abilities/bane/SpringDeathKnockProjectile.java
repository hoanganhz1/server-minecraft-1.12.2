package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bane;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class SpringDeathKnockProjectile extends NuHorizontalLightningEntity {
	public SpringDeathKnockProjectile(EntityType<? extends SpringDeathKnockProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public SpringDeathKnockProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.SPRING_DEATH_KNOCK.get())),var2,15F,2F,var3);
		this.setRetracting();
		this.setSize(0.05F);
		this.setColor(168,168,168,255);
		this.setOneTimeHit();
		this.setDamage(20F);
		this.setFist();
	}
}