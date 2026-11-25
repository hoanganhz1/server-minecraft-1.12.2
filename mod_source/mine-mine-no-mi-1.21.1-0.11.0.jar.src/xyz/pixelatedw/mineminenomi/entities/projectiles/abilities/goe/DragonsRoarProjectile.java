package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goe;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.IFlexibleSizeProjectile;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class DragonsRoarProjectile extends NuProjectileEntity implements IFlexibleSizeProjectile {
	// Fields
	private float size;

	public DragonsRoarProjectile(EntityType<? extends DragonsRoarProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.size = 1F;
	}
	public DragonsRoarProjectile(Level var1, LivingEntity var2, float var3, Ability var4) {
		super(((EntityType)(ModProjectiles.DRAGONS_ROAR.get())),var1,var2,(IAbility)(var4));
		this.size = 1F;
		this.setDamage(10F);
		this.setMaxLife(30);
		this.setPassThroughEntities();
		this.setArmorPiercing(1F);
		this.setSize(var3);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		DragonsRoarProjectile var3 = this;
		BlockHitResult var4 = var1;
		final BlockHitResult var4_f1 = var4;
		Consumer<ExplosionComponent> var5 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var4_f1.getBlockPos().getX()),(double)(var4_f1.getBlockPos().getY()),(double)(var4_f1.getBlockPos().getZ()),(this.getSize()) / (2F));
			lv1_3.setStaticBlockResistance(1.35F);
			lv1_3.setProtectOwnerFromFalling(true);
			lv1_3.setExplosionSound(true);
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(new CommonExplosionParticleEffect$Details((double)(this.getSize()))));
			lv1_3.setStaticDamage(0F);
			lv1_3.explode();
		};
		ExplosionComponent.createExplosion(var2,var5);
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		super.writeSpawnData(var1);
		var1.writeFloat(this.size);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		super.readSpawnData(var1);
		this.setSize(var1.readFloat());
	}
	public void setSize(float var1) {
		this.size = Mth.clamp(var1,1F,50F);
		this.setEntityCollisionSize((double)((var1) / (2F)),(double)((var1) / (2F)),(double)((var1) / (2F)));
	}
	public float getSize() {
		return this.size;
	}
}