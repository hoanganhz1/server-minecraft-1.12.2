package xyz.pixelatedw.mineminenomi.entities.vehicles;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.IVehicleAltMode;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.CannonBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class CannonEntity extends Entity implements IVehicleAltMode {
	// Fields
	private static final EntityDataAccessor<Byte> MODE = SynchedEntityData.defineId(CannonEntity.class,EntityDataSerializers.BYTE);
	private static final float MOVE_SPEED = 0.05F;
	private static final float BACKWARDS_MOVE_SPEED = -0.03F;
	private static final float YAW_AIM_SPEED = 1F;
	private static final float PITCH_AIM_SPEED = 1F;
	private static final float MOVEMENT_FRICTION = 0.1F;
	private static final float ROTATION_FRICTION = 0.1F;
	private static final float GRAVITY = -0.4F;
	private static final float DEFAULT_COOLDOWN = 10F;
	private static final byte HURT_EVENT = 100;
	private float deltaYaw;
	private float deltaPitch;
	private int lerpSteps;
	private double lerpX;
	private double lerpY;
	private double lerpZ;
	private double lerpYRot;
	private double lerpXRot;
	private float cooldown;
	private float damage;
	private int hurtTime;

	public CannonEntity(Level var1) {
		this(((EntityType)(ModEntities.CANNON.get())),var1);
	}
	public CannonEntity(EntityType<?> var1, Level var2) {
		super(var1,var2);
		this.blocksBuilding = true;
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		var1.define(MODE,Byte.valueOf((byte)(0)));
	}
	public boolean canCollideWith(Entity var1) {
		return canVehicleCollide((Entity)(this),var1);
	}
	public static boolean canVehicleCollide(Entity var0, Entity var1) {
		if ((var1.getY()) < (var0.getY())){
			return false;
		}else{
			return ((var1.canBeCollidedWith()) || (var1.isPushable())) && (!(var0.isPassengerOfSameVehicle(var1)));
		}
	}
	public boolean canBeCollidedWith() {
		return true;
	}
	public boolean isPushable() {
		return false;
	}
	public boolean isPickable() {
		return this.isAlive();
	}
	public boolean shouldRiderSit() {
		return false;
	}
	protected Vec3 getPassengerAttachmentPoint(Entity var1, EntityDimensions var2, float var3) {
		return new Vec3(0D,0D,0D);
	}
	public Direction getMotionDirection() {
		return this.getDirection().getClockWise();
	}
	private void tickLerp() {
		if (this.isControlledByLocalInstance()){
			this.lerpSteps = 0;
			this.syncPacketPositionCodec(this.getX(),this.getY(),this.getZ());
		}
		if ((this.lerpSteps) > (0)){
			double var1 = (this.getX()) + (((this.lerpX) - (this.getX())) / ((double)(this.lerpSteps)));
			double var2 = (this.getY()) + (((this.lerpY) - (this.getY())) / ((double)(this.lerpSteps)));
			double var3 = (this.getZ()) + (((this.lerpZ) - (this.getZ())) / ((double)(this.lerpSteps)));
			double var4 = Mth.wrapDegrees((this.lerpYRot) - ((double)(this.getYRot())));
			this.setYRot((float)(((double)(this.getYRot())) + ((var4) / ((double)(this.lerpSteps)))));
			this.setXRot((float)(((double)(this.getXRot())) + (((this.lerpXRot) - ((double)(this.getXRot()))) / ((double)(this.lerpSteps)))));
			this.lerpSteps = (this.lerpSteps) - (1);
			this.setPos(var1,var2,var3);
			this.setRot(this.getYRot(),this.getXRot());
		}
	}
	public void tick() {
		super.tick();
		Level var1 = super.level();
		if ((var1) == (null)){
			return;
		}else{
			this.tickLerp();
			if ((this.cooldown) > (0F)){
				this.cooldown = (this.cooldown) - (1F);
			}
			LivingEntity var2 = this.getControllingPassenger();
			if ((!(var1.isClientSide)) && (((var2) != (null)) && (var2 instanceof LivingEntity))){
				LivingEntity var3 = ((LivingEntity)(var2));
				if (this.canShoot(var3)){
					this.shootCannonball(var3);
				}
			}
			if ((this.getHurtTime()) > (0)){
				this.setHurtTime((this.getHurtTime()) - (1));
			}
			if ((this.getDamage()) > (0F)){
				this.setDamage((this.getDamage()) - (1F));
			}
			double var3_1 = (this.isNoGravity()) ? (0D) : (-0.4000000059604645D);
			Vec3 var4 = this.getDeltaMovement();
			AbilityHelper.setDeltaMovement(this,(var4.x) * (0.10000000149011612D),(var4.y) + (var3_1),(var4.z) * (0.10000000149011612D));
			if (this.isControlledByLocalInstance()){
				this.deltaYaw = (this.deltaYaw) * (0.1F);
				this.deltaPitch = (this.deltaPitch) * (0.1F);
				if ((this.getControllingPassenger()) != (null)){
					this.controlVehicle();
				}
			}
			this.move(MoverType.SELF,this.getDeltaMovement());
			if (!(this.onGround())){
				Iterator var5 = Lists.newArrayList((Iterable)(var1.getEntities((Entity)(this),this.getBoundingBox().move(0D,-1D,0D)))).iterator();
				do{
					if (var5.hasNext()){
						Entity var6 = ((Entity)(var5.next()));
						var6.hurt(this.damageSources().anvil(var6),20F);
						continue;
					}else{
						break;
					}
				} while (true);
			}
			return;
		}
	}
	private void controlVehicle() {
		if (this.isVehicle()){
			LivingEntity var1 = this.getControllingPassenger();
			if (((var1) != (null)) && (var1 instanceof LivingEntity)){
				LivingEntity var2 = ((LivingEntity)(var1));
				if ((var2.xxa) > (0F)){
					this.deltaYaw = (this.deltaYaw) - (1F);
				}else{
					if ((var2.xxa) < (0F)){
						this.deltaYaw = (this.deltaYaw) + (1F);
					}
				}
				float var3 = 0F;
				if (this.isMoveMode()){
					if ((var2.zza) > (0F)){
						var3 = 0.05F;
					}else{
						if ((var2.zza) < (0F)){
							var3 = -0.03F;
						}
					}
				}else{
					if (this.isAimMode()){
						if (((var2.zza) > (0F)) && ((this.getXRot()) > (-50F))){
							this.deltaPitch = (this.deltaPitch) - (1F);
						}else{
							if (((var2.zza) < (0F)) && ((this.getXRot()) < (0F))){
								this.deltaPitch = (this.deltaPitch) + (1F);
							}
						}
					}
				}
				this.setYRot((this.getYRot()) + (this.deltaYaw));
				this.setXRot((this.getXRot()) + (this.deltaPitch));
				AbilityHelper.setDeltaMovement(this,this.getDeltaMovement().add((double)((Mth.sin((-(this.getYRot())) * (0.017453292F))) * (var3)),0D,(double)((Mth.cos((this.getYRot()) * (0.017453292F))) * (var3))));
			}else{
				return;
			}
		}
	}
	public boolean canShoot(LivingEntity var1) {
		if (this.isVehicle()){
			boolean var2 = false;
			ItemStack var4 = ItemStack.EMPTY;
			int var5 = 0;
			ItemStack var6 = ItemStack.EMPTY;
			int var7 = 0;
			boolean var8 = false;
			if (var1 instanceof Player){
				var8 = ((Player)(var1)).getAbilities().instabuild;
			}else{
				var8 = true;
			}
			if (((var1.swingTime) == (1)) && ((this.cooldown) <= (0F))){
				if (var8){
					var2 = true;
				}else{
					List var9 = ItemsHelper.getAllInventoryItems(var1);
					Iterator var10 = var9.iterator();
					do{
						if (var10.hasNext()){
							ItemStack var11 = ((ItemStack)(var10.next()));
							if (((java.lang.Object)(var11.getItem())).equals(ModItems.CANNON_BALL.get())){
								var4 = var11;
								var5 = (var5) + (var11.getCount());
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					if (((var5) > (0)) && (!(var4.isEmpty()))){
						Iterator var11_1 = var9.iterator();
						do{
							if (var11_1.hasNext()){
								ItemStack var12 = ((ItemStack)(var11_1.next()));
								if (((java.lang.Object)(var12.getItem())).equals((Object)(Items.GUNPOWDER))){
									var6 = var12;
									var7 = (var7) + (var12.getCount());
									continue;
								}else{
									continue;
								}
							}else{
								break;
							}
						} while (true);
						if (((var7) > (0)) && (!(var6.isEmpty()))){
							if (((var5) > (0)) && (((var7) > (0)) && ((!(var4.isEmpty())) && (!(var6.isEmpty()))))){
								var2 = true;
							}
						}else{
							var1.sendSystemMessage(ModI18n.INFO_NEEDS_GUNPOWDER_LOADED);
							return false;
						}
					}else{
						var1.sendSystemMessage(ModI18n.INFO_NEEDS_CANNONBALL_LOADED);
						return false;
					}
				}
			}
			if (var2){
				if (!(var8)){
					var4.shrink(1);
					var6.shrink(1);
				}
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	public void shootCannonball(LivingEntity var1) {
		CannonBallProjectile var2 = new CannonBallProjectile((Level)(this.level()),(LivingEntity)(var1));
		var2.setPos(this.position().x,(this.position().y) + (0.25D),this.position().z);
		var2.setDamage(30F);
		var2.setMaxLife(180);
		var2.setGravity(0.04F);
		this.level().addFreshEntity((Entity)(var2));
		var2.shootFromRotation((Entity)(this),this.getXRot(),this.getYRot(),0F,3F,0F);
		var1.level().playSound((Player)(null),this.blockPosition(),((SoundEvent)(ModSounds.GENERIC_EXPLOSION.get())),SoundSource.PLAYERS,2.5F,(0.75F) + ((var1.getRandom().nextFloat()) / (4F)));
		this.cooldown = 10F;
		Vec3 var3 = this.getLookAngle().scale(4D);
		int var4 = 0;
		do{
			if ((var4) < (10)){
				double var5 = ((WyHelper.randomDouble()) / (2D)) + (var3.x);
				double var6 = (WyHelper.randomDouble()) / (2D);
				double var7 = ((WyHelper.randomDouble()) / (2D)) + (var3.z);
				((ServerLevel)(this.level())).sendParticles((ParticleOptions)(ParticleTypes.POOF),((double)(this.blockPosition().getX())) + (var5),((double)((this.blockPosition().getY()) + (1))) + (var6),((double)(this.blockPosition().getZ())) + (var7),1,0D,0D,0D,0D);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void positionRider(Entity var1, Entity.MoveFunction var2) {
		if (this.hasPassenger(var1)){
			float var3 = -1F;
			float var4 = (float)((this.isRemoved()) ? (0.009999999776482582D) : (this.getPassengerAttachmentPoint(var1,this.getDimensions(Pose.STANDING),1F).y));
			if ((this.getPassengers().size()) > (1)){
				if ((this.getPassengers().indexOf(var1)) == (0)){
					var3 = 0.2F;
				}else{
					var3 = -0.6F;
				}
				if (var1 instanceof Animal){
					var3 = (float)(((double)(var3)) + (0.2D));
				}
			}
			Vec3 var5 = new Vec3((double)(var3),0D,0D).yRot(((-(this.getYRot())) * (0.017453292F)) - (1.5707964F));
			var2.accept(var1,(this.getX()) + (var5.x),(this.getY()) + ((double)(var4)),(this.getZ()) + (var5.z));
			var1.setPos((this.getX()) + (var5.x),(this.getY()) + ((double)(var4)),(this.getZ()) + (var5.z));
			var1.setYRot((var1.getYRot()) + (this.deltaYaw));
			var1.setYHeadRot((var1.getYHeadRot()) + (this.deltaYaw));
			this.clampRotation(var1);
			if (var1 instanceof Animal){
				Animal var6 = ((Animal)(var1));
				if ((this.getPassengers().size()) > (1)){
					int var7 = (((var1.getId()) % (2)) == (0)) ? (90) : (270);
					var1.setYBodyRot((var6.yBodyRot) + ((float)(var7)));
					var1.setYHeadRot((var1.getYHeadRot()) + ((float)(var7)));
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	public Vec3 getDismountLocationForPassenger(LivingEntity var1) {
		Vec3 var2 = this.getLookAngle().reverse();
		return var1.position().add(0D,0.75D,0D).add(var2);
	}
	public void changeVehicleMode() {
		if (this.isAimMode()){
			this.setMoveMode();
		}else{
			this.setAimMode();
		}
	}
	@Nullable()
	public LivingEntity getControllingPassenger() {
		Entity var1 = this.getFirstPassenger();
		if (var1 instanceof LivingEntity){
			return ((LivingEntity)(var1));
		}else{
			return null;
		}
	}
	public InteractionResult interact(Player var1, InteractionHand var2) {
		if (var2.equals(InteractionHand.OFF_HAND)){
			return InteractionResult.PASS;
		}else{
			if (this.level().isClientSide){
				return InteractionResult.SUCCESS;
			}else{
				if (var1.startRiding((Entity)(this))){
					this.cooldown = 10F;
					return InteractionResult.CONSUME;
				}else{
					return InteractionResult.PASS;
				}
			}
		}
	}
	public boolean hurt(DamageSource var1, float var2) {
		if (this.isInvulnerableTo(var1)){
			return false;
		}else{
			if ((!(this.level().isClientSide)) && (!(this.isRemoved()))){
				this.level().broadcastEntityEvent((Entity)(this),(byte)(100));
				this.setDamage((this.getDamage()) + ((var2) * (10F)));
				this.markHurt();
				Entity var3 = var1.getEntity();
				int var4 = 0;
				if (var3 instanceof Player){
					Player var5 = ((Player)(var3));
					if (var5.getAbilities().instabuild){
						var4 = 1;
					}
				}else{
					var4 = 0;
				}
				int var5_1 = var4;
				if (((var5_1) == (0)) && (!((this.getDamage()) > (50F)))){

				}else{
					if (((var5_1) == (0)) && (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS))){
						this.spawnAtLocation(((Item)(ModItems.CANNON.get())).getDefaultInstance());
					}
					this.remove(Entity.RemovalReason.KILLED);
				}
				return true;
			}else{
				return true;
			}
		}
	}
	public void onAboveBubbleCol(boolean var1) {
	}
	public void push(Entity var1) {
		if (var1 instanceof CannonEntity){
			if ((var1.getBoundingBox().minY) < (this.getBoundingBox().maxY)){
				super.push(var1);
			}else{
				return;
			}
		}else{
			if ((var1.getBoundingBox().minY) <= (this.getBoundingBox().minY)){
				super.push(var1);
			}else{
				return;
			}
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void handleEntityEvent(byte var1) {
		switch (var1){
		case 100:
			this.hurtTime = 10;
		default:
			super.handleEntityEvent(var1);
			return;
		}
	}
	protected void clampRotation(Entity var1) {
		var1.setYBodyRot(this.getYRot());
		float var2 = Mth.wrapDegrees((var1.getYRot()) - (this.getYRot()));
		float var3 = Mth.clamp(var2,-105F,105F);
		var1.yRotO = (var1.yRotO) + ((var3) - (var2));
		var1.setYRot(((var1.getYRot()) + (var3)) - (var2));
		var1.setYHeadRot(var1.getYRot());
	}
	public ItemStack getPickedResult(HitResult var1) {
		return ((Item)(ModItems.CANNON.get())).getDefaultInstance();
	}
	@OnlyIn(value=Dist.CLIENT)
	public void onPassengerTurned(Entity var1) {
		this.clampRotation(var1);
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
		this.setModeByte(var1.getByte("mode"));
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
		var1.putByte("mode",this.getModeByte());
	}
	public void setAimMode() {
		this.setMode(CannonEntity$Mode.AIM);
	}
	public void setMoveMode() {
		this.setMode(CannonEntity$Mode.MOVE);
	}
	private void setMode(CannonEntity$Mode var1) {
		this.setModeByte((byte)(var1.ordinal()));
	}
	private void setModeByte(byte var1) {
		this.entityData.set(MODE,Byte.valueOf(var1));
	}
	public boolean isAimMode() {
		return this.getMode().equals(CannonEntity$Mode.AIM);
	}
	public boolean isMoveMode() {
		return this.getMode().equals(CannonEntity$Mode.MOVE);
	}
	private byte getModeByte() {
		return ((Byte)(this.entityData.get(MODE))).byteValue();
	}
	public CannonEntity$Mode getMode() {
		return CannonEntity$Mode.values()[((Byte)(this.entityData.get(MODE))).byteValue()];
	}
	public float getCooldown() {
		return this.cooldown;
	}
	public float getDamage() {
		return this.damage;
	}
	public void setDamage(float var1) {
		this.damage = var1;
	}
	public int getHurtTime() {
		return this.hurtTime;
	}
	public void setHurtTime(int var1) {
		this.hurtTime = var1;
	}
}