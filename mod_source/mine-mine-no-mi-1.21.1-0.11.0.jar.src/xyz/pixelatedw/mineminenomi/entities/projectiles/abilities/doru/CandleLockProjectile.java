package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doru;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleChampionAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleLockAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CandleLockProjectile extends NuProjectileEntity {
	// Fields
	private boolean isChampionActive;

	public CandleLockProjectile(EntityType<? extends CandleLockProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.isChampionActive = false;
	}
	public CandleLockProjectile(Level var1, LivingEntity var2, CandleLockAbility var3) {
		super(((EntityType)(ModProjectiles.CANDLE_LOCK.get())),var1,var2,(IAbility)(var3));
		this.isChampionActive = false;
		this.setDamage(8F);
		this.setMaxLife(20);
		this.setPassThroughEntities();
		super.setPhysical();
		this.setEntityCollisionSize(3D);
		Function<AbilityData, CandleChampionAbility> var4 = (l0) -> {
			return ((CandleChampionAbility)(l0.getEquippedAbility(((AbilityCore)(CandleChampionAbility.INSTANCE.get())))));
		};
		this.isChampionActive = ((Boolean)(((java.util.Optional)(AbilityData.get(var2))).map((Function)(var4)).map((Function)(((Function<CandleChampionAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue();
		if (this.isChampionActive){
			this.setEntityCollisionSize(5D,3D,5D);
		}
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::entityHitEvent))));
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::tickEvent))));
	}
	private void entityHitEvent(EntityHitResult var1) {
		if ((this.getOwner()) == (null)){
			return;
		}else{
			Entity var2 = var1.getEntity();
			if (var2 instanceof LivingEntity){
				LivingEntity var3 = ((LivingEntity)(var2));
				int var4 = 200;
				int var5 = 1;
				if (DoruHelper.hasColorPalette(this.getOwner())){
					var5 = 2;
				}
				if (this.isChampionActive){
					var4 = 300;
				}
				var3.addEffect(new MobEffectInstance((Holder)(ModEffects.CANDLE_LOCK),var4,var5));
			}
			return;
		}
	}
	private void tickEvent() {
		BlockPos var1 = null;
		int var2 = 1;
		do{
			if (!(this.level().getBlockState(this.blockPosition().below(var2)).isAir())){
				var1 = this.blockPosition().below(var2);
				break;
			}else{
				if ((var2) > (5)){
					break;
				}else{
					var2++;
					continue;
				}
			}
		} while (true);
		if ((var1) == (null)){
			return;
		}else{
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.CANDLE_LOCK.get())),(Entity)(this),(double)(var1.getX()),(double)(var1.getY()),(double)(var1.getZ()));
			return;
		}
	}
}