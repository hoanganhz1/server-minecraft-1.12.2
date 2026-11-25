package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class VenomRoadProjectile extends NuHorizontalLightningEntity {
	// Fields
	private boolean isDemonMode;
	private Vec3 startPos;

	public VenomRoadProjectile(EntityType<? extends VenomRoadProjectile> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.isDemonMode = false;
	}
	public VenomRoadProjectile(Level var1, LivingEntity var2, IAbility var3, boolean var4) {
		super(((EntityType)(ModProjectiles.VENOM_ROAD.get())),var2,64F,(var4) ? (10F) : (8F),var3);
		this.isDemonMode = false;
		this.setDamage((var4) ? (15F) : (8F));
		this.noCulling = true;
		this.setMaxLife((var4) ? (40) : (30));
		this.setSize(0.1F);
		this.setSegments(1);
		this.setFadeTime(-1);
		this.setColor(255,255,255,100);
		this.setPassThroughEntities();
		this.isDemonMode = var4;
		this.startPos = var2.position();
		this.addEntityHitEvent(100,(NuProjectileEntity$IOnHitEntityEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent)(this::onEntityImpactEvent))));
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		LivingEntity var4 = null;
		LivingEntity var2 = this.getOwner();
		if (((var2) != (null)) && (!(AbilityUseConditions.canUseMomentumAbilities(var2).isFail()))){
			BlockPos var3 = var1.getBlockPos();
			if (var2.level().getBlockState(var3).isAir()){
				return;
			}else{
				if ((var2.getVehicle()) != (null)){
					var2.stopRiding();
				}
				var2.stopRiding();
				var2.teleportTo((double)(var3.getX()),(double)(var3.getY()),(double)(var3.getZ()));
				var2.fallDistance = 0F;
				var4 = var2;
				final LivingEntity var4_f1 = var4;
				Consumer<IAbility> var5 = (l0) -> {
			LivingEntity lv2_2 = var4_f1;
			final LivingEntity lv2_2_f1 = lv2_2;
			Consumer<CooldownComponent> lv2_3 = (l2_0) -> {
				l2_0.startCooldown(lv2_2_f1,80F);
			};
			((java.util.Optional)(l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(lv2_3));
		};
				((java.util.Optional)(this.getParent())).ifPresent((Consumer)(var5));
				return;
			}
		}else{
			return;
		}
	}
	public void tick() {
		if (((this.getLife()) <= (0)) && ((this.getLife()) > (-1200))){
			this.setLife((this.getLife()) - (1));
			AbilityHelper.setDeltaMovement((Entity)(this),0D,0D,0D);
			return;
		}else{
			super.tick();
			return;
		}
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
		var1.writeDouble(((this.startPos) != (null)) ? (this.startPos.x) : (0D));
		var1.writeDouble(((this.startPos) != (null)) ? (this.startPos.y) : (0D));
		var1.writeDouble(((this.startPos) != (null)) ? (this.startPos.z) : (0D));
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		super.readSpawnData(var1);
		this.isDemonMode = var1.readBoolean();
		this.startPos = new Vec3(var1.readDouble(),var1.readDouble(),var1.readDouble());
	}
	public Vec3 getStartPos() {
		return this.startPos;
	}
	public boolean isDemonMode() {
		return this.isDemonMode;
	}
}