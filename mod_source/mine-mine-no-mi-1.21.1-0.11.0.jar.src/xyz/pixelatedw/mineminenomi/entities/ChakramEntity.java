package xyz.pixelatedw.mineminenomi.entities;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ChakramItem;

public class ChakramEntity extends AbstractArrow implements IEntityWithComplexSpawn {
	// Fields
	private ItemStack itemStack;
	private float attackDamage;
	private boolean dealtDamage;
	public int clientSideReturnTickCount;

	public ChakramEntity(EntityType<?> var1, Level var2) {
		super(var1,var2);
	}
	public ChakramEntity(LivingEntity var1, ItemStack var2) {
		super(((EntityType)(ModEntities.CHAKRAM.get())),var1,var1.level(),var2,var2);
		this.itemStack = var2.copy();
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
	}
	public void tick() {
		if ((this.inGroundTime) > (4)){
			this.dealtDamage = true;
		}
		Entity var1 = this.getOwner();
		if (((!(this.dealtDamage)) && (!(this.isNoPhysics()))) || ((var1) == (null))){

		}else{
			if (!(this.isAcceptibleReturnOwner())){
				if (((this.level()) != (null)) && ((!(this.level().isClientSide)) && ((this.pickup) == (AbstractArrow.Pickup.ALLOWED)))){
					this.spawnAtLocation(this.getPickupItem(),0.1F);
				}
				this.remove(Entity.RemovalReason.DISCARDED);
			}else{
				if ((var1 instanceof Mob) && (((this.distanceToSqr(var1)) < (3.5D)) && ((this.tickCount) > (10)))){
					this.remove(Entity.RemovalReason.DISCARDED);
				}
				this.setNoPhysics(true);
				Vec3 var2 = new Vec3((var1.getX()) - (this.getX()),(var1.getEyeY()) - (this.getY()),(var1.getZ()) - (this.getZ()));
				this.setPosRaw(this.getX(),(this.getY()) + ((var2.y) * (0.015D)),this.getZ());
				if (((this.level()) != (null)) && (this.level().isClientSide)){
					this.yOld = this.getY();
				}
				double var3 = 0.1D;
				AbilityHelper.setDeltaMovement((Entity)(this),this.getDeltaMovement().scale(0.95D).add(var2.normalize().scale(var3)));
				this.clientSideReturnTickCount = (this.clientSideReturnTickCount) + (1);
			}
		}
		if ((!(this.isNoGravity())) && (!(this.isNoPhysics()))){
			Vec3 var2_1 = this.getDeltaMovement();
			this.setDeltaMovement(var2_1.x,(var2_1.y) + (0.03999999910593033D),var2_1.z);
		}
		super.tick();
	}
	private boolean isAcceptibleReturnOwner() {
		Entity var1 = this.getOwner();
		if (((var1) != (null)) && (var1.isAlive())){
			if ((var1 instanceof ServerPlayer) && (!(var1.isSpectator()))){
				return true;
			}else{
				return !(var1.isSpectator());
			}
		}else{
			return false;
		}
	}
	public ItemStack getPickupItem() {
		return this.itemStack.copy();
	}
	public ItemStack getItemUsed() {
		return this.itemStack;
	}
	public void setAttackDamage(float var1) {
		this.attackDamage = var1;
	}
	@Nullable()
	protected EntityHitResult findHitEntity(Vec3 var1, Vec3 var2) {
		return (EntityHitResult) ((this.dealtDamage) ? (null) : (super.findHitEntity(var1,var2)));
	}
	protected void onHitEntity(EntityHitResult var1) {
		Entity var2 = var1.getEntity();
		float var3 = this.attackDamage;
		Entity var4 = this.getOwner();
		DamageSource var5 = this.damageSources().trident((Entity)(this),(Entity)(((var4) == (null)) ? (this) : (var4)));
		if ((var2 instanceof LivingEntity) && (!(var2.level().isClientSide))){
			var3 = EnchantmentHelper.modifyDamage(((ServerLevel)(var2.level())),this.getWeaponItem(),var2,var5,var3);
		}
		this.dealtDamage = true;
		SoundEvent var6 = SoundEvents.TRIDENT_HIT;
		if (var2.hurt(var5,var3)){
			if ((var2.getType()) == (EntityType.ENDERMAN)){
				return;
			}else{
				Level var7 = this.level();
				if (var7 instanceof ServerLevel){
					EnchantmentHelper.doPostAttackEffectsWithItemSource(((ServerLevel)(var7)),var2,var5,this.getWeaponItem());
				}
				if (var2 instanceof LivingEntity){
					LivingEntity var8 = ((LivingEntity)(var2));
					this.doKnockback(var8,var5);
					this.doPostHurtEffects(var8);
				}
			}
		}
		AbilityHelper.setDeltaMovement((Entity)(this),this.getDeltaMovement().multiply(-0.01D,-0.1D,-0.01D));
		this.playSound(var6,1F,1.5F);
	}
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.TRIDENT_HIT_GROUND;
	}
	public void playerTouch(Player var1) {
		Entity var2 = this.getOwner();
		if (((var2) != (null)) && ((var2.getUUID()) != (var1.getUUID()))){

		}else{
			super.playerTouch(var1);
		}
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		if (var1.contains("Item",10)){
			this.itemStack = ((ItemStack)(ItemStack.parse((HolderLookup.Provider)(this.registryAccess()),(Tag)(var1.getCompound("Item"))).orElse(this.getDefaultPickupItem())));
		}
		this.dealtDamage = var1.getBoolean("DealtDamage");
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.put("Item",this.itemStack.save((HolderLookup.Provider)(this.registryAccess())));
		var1.putBoolean("DealtDamage",this.dealtDamage);
	}
	public void tickDespawn() {
		if ((this.pickup) != (AbstractArrow.Pickup.ALLOWED)){
			super.tickDespawn();
		}
	}
	protected float getWaterInertia() {
		return 0.99F;
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean shouldRender(double var1, double var2, double var3) {
		return true;
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		var1.writeInt(((this.getOwner()) != (null)) ? (this.getOwner().getId()) : (-1));
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		int var2 = var1.readInt();
		if ((var2) >= (0)){
			this.setOwner(this.level().getEntity(var2));
		}
	}
	protected ItemStack getDefaultPickupItem() {
		return ((ChakramItem)(ModWeapons.CHAKRAM.get())).getDefaultInstance();
	}
}