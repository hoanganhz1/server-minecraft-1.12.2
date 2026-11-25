package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku;

import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class HydraProjectile extends NuHorizontalLightningEntity {
	// Fields
	private boolean isDemonMode;

	public HydraProjectile(EntityType<? extends HydraProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.isDemonMode = false;
	}
	public HydraProjectile(Level var1, LivingEntity var2, IAbility var3, boolean var4) {
		super(((EntityType)(ModProjectiles.HYDRA.get())),var2,64F,(var4) ? (10F) : (8F),var3);
		this.isDemonMode = false;
		this.setDamage((var4) ? (60F) : (30F));
		this.setEntityCollisionSize(1D);
		this.setMaxLife((var4) ? (40) : (30));
		this.setSize(0.1F);
		this.setSegments(1);
		this.setPassThroughEntities();
		this.isDemonMode = var4;
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			var3.addEffect(new MobEffectInstance((Holder)(ModEffects.DOKU_POISON),400,(this.isDemonMode) ? (2) : (1)));
			var3.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,400,(this.isDemonMode) ? (2) : (1)));
			var3.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,400,(this.isDemonMode) ? (2) : (1)));
			var3.addEffect(new MobEffectInstance(MobEffects.CONFUSION,400,(this.isDemonMode) ? (2) : (1)));
		}
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		super.writeSpawnData(var1);
		var1.writeBoolean(this.isDemonMode);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		super.readSpawnData(var1);
		this.isDemonMode = var1.readBoolean();
	}
	public boolean isDemonMode() {
		return this.isDemonMode;
	}
}