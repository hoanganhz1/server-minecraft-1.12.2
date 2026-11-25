package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.kage;

import net.minecraft.core.Holder;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BlackBoxProjectile extends NuProjectileEntity {
	public BlackBoxProjectile(EntityType<BlackBoxProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public BlackBoxProjectile(Level var1, LivingEntity var2, IAbility var3) {
		super(((EntityType)(ModProjectiles.BLACK_BOX.get())),var1,var2,var3);
		this.setDamage(5F);
		this.setMaxLife(15);
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
	}
	private void onEntityImpactEvent(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			MobEffectInstance var4 = new MobEffectInstance((Holder)(ModEffects.BLACK_BOX),80,0,false,false);
			var3.addEffect(var4);
			LivingEntity var5 = this.getOwner();
			if (var5 instanceof ServerPlayer){
				((ServerPlayer)(var5)).connection.send((Packet)(new ClientboundUpdateMobEffectPacket(var3.getId(),var4,true)));
			}else{
				return;
			}
		}
	}
}