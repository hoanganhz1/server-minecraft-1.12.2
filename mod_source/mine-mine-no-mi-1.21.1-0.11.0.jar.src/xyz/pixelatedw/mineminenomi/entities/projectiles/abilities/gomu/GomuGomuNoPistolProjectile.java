package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class GomuGomuNoPistolProjectile extends NuHorizontalLightningEntity {
	public GomuGomuNoPistolProjectile(EntityType<? extends GomuGomuNoPistolProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public GomuGomuNoPistolProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.GOMU_GOMU_NO_PISTOL.get())),var2,80F,8F,var3);
		this.setRetracting();
		this.setSize(0.025F);
		this.setOneTimeHit();
		this.setDepth(1);
		this.setPassThroughEntities(false);
		this.setDamage(6F);
		this.setFist();
		this.setAlwaysDamage(false);
	}
}