package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ito;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class StringPillarProjectile extends NuProjectileEntity {
	public StringPillarProjectile(EntityType<? extends StringPillarProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public StringPillarProjectile(Level var1, LivingEntity var2, Ability var3) {
		super(((EntityType)(ModProjectiles.STRING_PILLAR.get())),var1,var2,(IAbility)(var3));
		this.setDamage(15F);
		this.setPassThroughEntities();
		this.setArmorPiercing(0.25F);
		this.setEntityCollisionSize(0.75D,5D,0.75D);
	}
	public void tick() {
		super.tick();
		this.setXRot(90F);
	}
	public void onProjectileCollision(NuProjectileEntity var1, NuProjectileEntity var2) {
		if (var2 instanceof StringPillarProjectile){
			return;
		}else{
			super.onProjectileCollision(var1,var2);
			return;
		}
	}
}