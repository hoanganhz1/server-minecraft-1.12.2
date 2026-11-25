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
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

public class ThrownSpearEntity extends AbstractArrow implements IEntityWithComplexSpawn {
	// Fields
	private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData.defineId(ThrownSpearEntity.class,EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ThrownSpearEntity.class,EntityDataSerializers.BOOLEAN);
	private ItemStack itemStack;
	private float attackDamage;
	private boolean dealtDamage;
	public int clientSideReturnTickCount;

	public ThrownSpearEntity(EntityType<?> var1, Level var2) {
		super(var1,var2);
	}
	public ThrownSpearEntity(Player var1, ItemStack var2) {
		super(((EntityType)(ModEntities.THROWN_SPEAR.get())),(LivingEntity)(var1),var1.level(),var2,var2);
		this.itemStack = var2.copy();
		this.entityData.set(ID_LOYALTY,Byte.valueOf(this.getLoyaltyFromItem(var2)));
		this.entityData.set(ID_FOIL,Boolean.valueOf(var2.hasFoil()));
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
		var1.define(ID_LOYALTY,Byte.valueOf((byte)(0)));
		var1.define(ID_FOIL,Boolean.valueOf(false));
	}
	public void tick() {
		if ((this.inGroundTime) > (4)){
			this.dealtDamage = true;
		}
		Entity var1 = this.getOwner();
		if (((!(this.dealtDamage)) && (!(this.isNoPhysics()))) || ((var1) == (null))){

		}else{
			byte var2 = ((Byte)(this.entityData.get(ID_LOYALTY))).byteValue();
			if (((var2) > (0)) && (!(this.isAcceptibleReturnOwner()))){
				if ((!(this.level().isClientSide)) && ((this.pickup) == (AbstractArrow.Pickup.ALLOWED))){
					this.spawnAtLocation(this.getPickupItem(),0.1F);
				}
				this.remove(Entity.RemovalReason.DISCARDED);
			}else{
				if ((var2) > (0)){
					this.setNoPhysics(true);
					Vec3 var3 = new Vec3((var1.getX()) - (this.getX()),(var1.getEyeY()) - (this.getY()),(var1.getZ()) - (this.getZ()));
					this.setPosRaw(this.getX(),(this.getY()) + (((var3.y) * (0.015D)) * ((double)(var2))),this.getZ());
					if (this.level().isClientSide){
						this.yOld = this.getY();
					}
					double var4 = (0.05D) * ((double)(var2));
					AbilityHelper.setDeltaMovement((Entity)(this),this.getDeltaMovement().scale(0.95D).add(var3.normalize().scale(var4)));
					if ((this.clientSideReturnTickCount) == (0)){
						this.playSound(SoundEvents.TRIDENT_RETURN,10F,1F);
					}
					this.clientSideReturnTickCount = (this.clientSideReturnTickCount) + (1);
				}
			}
		}
		super.tick();
	}
	private boolean isAcceptibleReturnOwner() {
		Entity var1 = this.getOwner();
		if (((var1) != (null)) && (var1.isAlive())){
			return (!(var1 instanceof ServerPlayer)) || (!(var1.isSpectator()));
		}else{
			return false;
		}
	}
	public ItemStack getPickupItem() {
		return this.itemStack.copy();
	}
	protected ItemStack getDefaultPickupItem() {
		return this.itemStack;
	}
	public ItemStack getItemUsed() {
		return this.itemStack;
	}
	public void setAttackDamage(float var1) {
		this.attackDamage = var1;
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean isFoil() {
		return ((Boolean)(this.entityData.get(ID_FOIL))).booleanValue();
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
		Level var6 = this.level();
		if (var6 instanceof ServerLevel){
			var3 = (var3) + (EnchantmentHelper.modifyDamage(((ServerLevel)(var6)),this.getWeaponItem(),var2,var5,var3));
		}
		this.dealtDamage = true;
		SoundEvent var7 = SoundEvents.TRIDENT_HIT;
		if (var2.hurt(var5,var3)){
			if ((var2.getType()) == (EntityType.ENDERMAN)){
				return;
			}else{
				Level var8 = this.level();
				if (var8 instanceof ServerLevel){
					EnchantmentHelper.doPostAttackEffectsWithItemSource(((ServerLevel)(var8)),var2,var5,this.getWeaponItem());
				}
				if (var2 instanceof LivingEntity){
					LivingEntity var9 = ((LivingEntity)(var2));
					this.doKnockback(var9,var5);
					this.doPostHurtEffects(var9);
				}
			}
		}
		AbilityHelper.setDeltaMovement((Entity)(this),this.getDeltaMovement().multiply(-0.01D,-0.1D,-0.01D));
		this.playSound(SoundEvents.TRIDENT_HIT,1F,1F);
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
			this.itemStack = ((ItemStack)(ItemStack.parse((HolderLookup.Provider)(this.registryAccess()),(Tag)(var1.getCompound("Item"))).get()));
		}
		this.dealtDamage = var1.getBoolean("DealtDamage");
		this.entityData.set(ID_LOYALTY,Byte.valueOf(this.getLoyaltyFromItem(this.itemStack)));
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.put("Item",this.itemStack.save((HolderLookup.Provider)(this.registryAccess())));
		var1.putBoolean("DealtDamage",this.dealtDamage);
	}
	public void tickDespawn() {
		byte var1 = ((Byte)(this.entityData.get(ID_LOYALTY))).byteValue();
		if (((this.pickup) == (AbstractArrow.Pickup.ALLOWED)) && ((var1) > (0))){

		}else{
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
	private byte getLoyaltyFromItem(ItemStack var1) {
		Level var2 = this.level();
		if (var2 instanceof ServerLevel){
			ServerLevel var3 = ((ServerLevel)(var2));
			return (byte)(Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(var3,var1,(Entity)(this)),0,127));
		}else{
			return (byte) (0);
		}
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		ItemStack.STREAM_CODEC.encode(var1,this.itemStack);
		var1.writeInt(((this.getOwner()) != (null)) ? (this.getOwner().getId()) : (-1));
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		this.itemStack = ((ItemStack)(ItemStack.STREAM_CODEC.decode(var1)));
		int var2 = var1.readInt();
		if ((var2) >= (0)){
			this.setOwner(this.level().getEntity(var2));
		}
	}
}