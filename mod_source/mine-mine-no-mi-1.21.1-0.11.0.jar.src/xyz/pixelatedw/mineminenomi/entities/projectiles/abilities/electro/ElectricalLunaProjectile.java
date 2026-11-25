package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalLunaAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElectricalLunaProjectile extends NuProjectileEntity {
	// Fields
	private ElectricalLunaAbility ability;

	public ElectricalLunaProjectile(EntityType<? extends ElectricalLunaProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public ElectricalLunaProjectile(Level var1, LivingEntity var2, ElectricalLunaAbility var3) {
		super(((EntityType)(ModProjectiles.ELECTRICAL_LUNA.get())),var1,var2,(IAbility)(var3));
		this.ability = var3;
		this.setMaxLife(20);
		this.setPassThroughEntities();
		this.addTickEvent(100,(NuProjectileEntity$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnTickEvent)(this::onTickEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockHitEvent))));
	}
	private void onBlockHitEvent(BlockHitResult var1) {
		if (((this.getOwner()) != (null)) && (!(this.getParent().isEmpty()))){
			ElectricalLunaProjectile var2 = this;
			Consumer<RangeComponent> var3 = (l0) -> {
			int lv1_2 = 6;
			Iterator lv1_3 = l0.getTargetsInArea(this.getOwner(),(float)(lv1_2)).iterator();
			LOOP_1:
			do{
				if (lv1_3.hasNext()){
					((LivingEntity)(lv1_3.next())).addEffect(new MobEffectInstance((Holder)(ModEffects.PARALYSIS),30,0));
					int lv1_4 = 16;
					int lv1_5 = 0;
					do{
						if ((lv1_5) < (lv1_4)){
							float lv1_6 = (float)(WyHelper.randomWithRange(3,lv1_2));
							ElectroVisualProjectile lv1_7 = new ElectroVisualProjectile(this.level(),this.getOwner(),((IAbility)(this.getParent().get())),lv1_6);
							this.level().addFreshEntity((Entity)(lv1_7));
							lv1_5++;
							continue;
						}else{
							continue LOOP_1;
						}
					} while (true);
				}else{
					break;
				}
			} while (true);
		};
			((java.util.Optional)(this.ability.getComponent(((AbilityComponentKey)(ModAbilityComponents.RANGE.get()))))).ifPresent((Consumer)(var3));
			return;
		}else{
			return;
		}
	}
	private void onTickEvent() {
		if ((this.tickCount) < (0)){
			return;
		}else{
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.ELECTRICAL_LUNA.get())),(Entity)(this),this.getX(),this.getY(),this.getZ());
			return;
		}
	}
}