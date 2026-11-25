package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.abilities.jiki.JikiHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class DamnedPunkProjectile extends NuHorizontalLightningEntity {
	// Fields
	public List<ItemStack> magneticItems;

	public DamnedPunkProjectile(EntityType<? extends DamnedPunkProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.magneticItems = new ArrayList();
	}
	public DamnedPunkProjectile(Level var1, LivingEntity var2, List<ItemStack> var3, IAbility var4) {
		super(((EntityType)(ModProjectiles.DAMNED_PUNK.get())),var2,64F,7F,var4);
		this.magneticItems = new ArrayList();
		this.magneticItems = var3;
		super.setColor(new Color(11693284));
		super.setDamage(70F);
		super.setSize(0.1F);
		super.setAngle(100);
		super.setMaxLife(7);
		super.setTargetTimeToReset(30);
		super.setBranches(1);
		super.setSegments(1);
		super.fadeAtMaxLength(false);
		super.setFollowOwner();
		super.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::blockHitEvent))));
	}
	public void remove(Entity.RemovalReason var1) {
		JikiHelper.dropComponentItems(this.level(),new Vec3(this.getCurrentX(),this.getCurrentY(),this.getCurrentZ()),this.magneticItems);
		super.remove(var1);
	}
	private void blockHitEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		DamnedPunkProjectile var3 = this;
		BlockHitResult var4 = var1;
		final BlockHitResult var4_f1 = var4;
		Consumer<ExplosionComponent> var5 = (l0) -> {
			AbilityExplosion lv1_3 = l0.createExplosion((Entity)(this),this.getOwner(),(double)(var4_f1.getBlockPos().getX()),(double)(var4_f1.getBlockPos().getY()),(double)(var4_f1.getBlockPos().getZ()),5F);
			lv1_3.disableExplosionKnockback();
			lv1_3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION5));
			lv1_3.explode();
		};
		ExplosionComponent.createExplosion(var2,var5);
	}
}