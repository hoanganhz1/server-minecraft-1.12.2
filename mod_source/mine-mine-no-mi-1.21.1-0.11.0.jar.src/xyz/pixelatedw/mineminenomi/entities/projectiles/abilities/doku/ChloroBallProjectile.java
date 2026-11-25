package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.abilities.doku.DokuHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.clouds.ChloroBallCloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.doku.DokuParticleEffectDetails;

public class ChloroBallProjectile extends NuProjectileEntity {
	// Fields
	public static final float EXPLOSION_DAMAGE = 25F;
	public static final float EXPLOSION_RANGE = 5F;
	private boolean isDemonMode;

	public ChloroBallProjectile(EntityType<? extends ChloroBallProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.isDemonMode = false;
	}
	public ChloroBallProjectile(Level var1, LivingEntity var2, IAbility var3, boolean var4) {
		super(((EntityType)(ModProjectiles.CHLORO_BALL.get())),var1,var2,var3);
		this.isDemonMode = false;
		this.isDemonMode = var4;
		this.setDamage((var4) ? (30F) : (15F));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		IAbility var2 = ((IAbility)(((java.util.Optional)(this.getParent())).orElse((Object)(null))));
		ChloroBallProjectile var3 = this;
		Consumer<ExplosionComponent> var4 = (l0) -> {
			AbilityExplosion lv1_2 = l0.createExplosion((Entity)(this),this.getOwner(),this.getX(),this.getY(),this.getZ(),5F);
			lv1_2.setStaticDamage(25F);
			lv1_2.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(new CommonExplosionParticleEffect$Details(5D)));
			lv1_2.explode();
		};
		ExplosionComponent.createExplosion(var2,var4);
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.CHLORO_BALL.get())),(Entity)(this),this.getX(),(this.getY()) + (1D),this.getZ());
		ChloroBallCloudEntity var5 = new ChloroBallCloudEntity((Level)(this.level()),(ChloroBallProjectile)(this));
		var5.setLife(100);
		var5.moveTo(this.getX(),(this.getY()) + (1D),this.getZ(),0F,0F);
		AbilityHelper.setDeltaMovement(var5,0D,0D,0D);
		this.level().addFreshEntity((Entity)(var5));
	}
	private void onTickEvent() {
		if (!(this.level().isClientSide())){
			int var1 = 0;
			do{
				if ((var1) < (2)){
					double var2 = (WyHelper.randomDouble()) / (2D);
					double var3 = (WyHelper.randomDouble()) / (2D);
					double var4 = (WyHelper.randomDouble()) / (2D);
					SimpleParticleData var5 = new SimpleParticleData(((ParticleType)(ModParticleTypes.DOKU.get())));
					if (this.isDemonMode){
						var5.setColor(1F,0F,0F);
					}
					var5.setLife(5);
					var5.setSize(1.3F);
					WyHelper.spawnParticles(var5,((ServerLevel)(this.level())),(this.getX()) + (var2),(this.getY()) + (var3),(this.getZ()) + (var4));
					var1++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	public ParticleEffect$Details getPoisonParticles() {
		return (this.isDemonMode) ? (DokuHelper.VENOM_DETAILS) : (ParticleEffect.EMPTY_DETAILS);
	}
	public int getPoisonAmplifier() {
		return (this.isDemonMode) ? (4) : (0);
	}
	public int getPoisonRange() {
		return (this.isDemonMode) ? (8) : (5);
	}
	public boolean isDemonMode() {
		return this.isDemonMode;
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		super.writeSpawnData(var1);
		var1.writeBoolean(this.isDemonMode);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		super.readSpawnData(var1);
		this.isDemonMode = var1.readBoolean();
	}
}