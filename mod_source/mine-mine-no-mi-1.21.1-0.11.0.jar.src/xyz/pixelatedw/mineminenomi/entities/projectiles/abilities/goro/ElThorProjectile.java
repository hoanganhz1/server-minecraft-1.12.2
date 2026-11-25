package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuVerticalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElThorProjectile extends NuVerticalLightningEntity {
	// Fields
	public static final float EXPLOSION_SIZE = 20F;
	public static final float DAMAGE = 50F;
	private float multiplier;

	public ElThorProjectile(EntityType<? extends ElThorProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.multiplier = 1F;
	}
	public ElThorProjectile(Level var1, LivingEntity var2, double var3, double var4, double var5, float var6, float var7, IAbility var8) {
		super(((EntityType)(ModProjectiles.EL_THOR.get())),var2,var3,var4,var5,(var6) * (var7),8F,var8);
		this.multiplier = 1F;
		this.multiplier = var7;
		this.setDamage((50F) * (this.multiplier));
		this.setSize((2F) * (this.multiplier));
		this.setLightningMovement(false);
		this.setMaxLife(100);
		this.setTargetTimeToReset(60);
		this.setSegments(1);
		this.setPassThroughBlocks();
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
	}
	private void blockHitEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		ElThorProjectile var3 = this;
		BlockHitResult var4 = var1;
		final BlockHitResult var4_f1 = var4;
		Consumer<ExplosionComponent> var5 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var4_f1.getBlockPos().getX()),(double)(var4_f1.getBlockPos().getY()),(double)(var4_f1.getBlockPos().getZ()),(20F) * (this.multiplier));
			lv1_3.setSmokeParticles((ParticleEffect)(null));
			lv1_3.explode();
		};
		ExplosionComponent.createExplosion(var2,var5);
	}
}