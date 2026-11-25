package xyz.pixelatedw.mineminenomi.entities.vehicles;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class UnicycleEntity extends Entity {
	// Fields
	private static final float MOVE_SPEED = 0.25F;
	private static final float BACKWARDS_MOVE_SPEED = -0.25F;
	private static final float YAW_AIM_SPEED = 1F;
	private static final float MOVEMENT_FRICTION = 0.55F;
	private static final float ROTATION_FRICTION = 0.55F;
	private static final float GRAVITY = -0.098F;
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

	public UnicycleEntity(Level var1) {
		this(((EntityType)(ModEntities.UNICYCLE.get())),var1);
	}
	public UnicycleEntity(EntityType<?> var1, Level var2) {
		super(var1,var2);
		this.blocksBuilding = true;
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
	public float maxUpStep() {
		return 1F;
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
		return new Vec3(0D,0.25D,0D);
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
		this.tickLerp();
		if ((this.cooldown) > (0F)){
			this.cooldown = (this.cooldown) - (1F);
		}
		if ((this.getHurtTime()) > (0)){
			this.setHurtTime((this.getHurtTime()) - (1));
		}
		if ((this.getDamage()) > (0F)){
			this.setDamage((this.getDamage()) - (1F));
		}
		double var1 = (this.isNoGravity()) ? (0D) : (-0.09799999743700027D);
		Vec3 var2 = this.getDeltaMovement();
		AbilityHelper.setDeltaMovement(this,(var2.x) * (0.550000011920929D),(var2.y) + (var1),(var2.z) * (0.550000011920929D));
		if (this.isControlledByLocalInstance()){
			this.deltaYaw = (this.deltaYaw) * (0.55F);
			this.deltaPitch = (this.deltaPitch) * (0.55F);
			if ((this.getControllingPassenger()) != (null)){
				this.controlVehicle();
			}
		}
		this.move(MoverType.SELF,this.getDeltaMovement());
		if (!(this.onGround())){
			Iterator var3 = Lists.newArrayList((Iterable)(this.level().getEntities((Entity)(this),this.getBoundingBox().move(0D,-1D,0D)))).iterator();
			do{
				if (var3.hasNext()){
					Entity var4 = ((Entity)(var3.next()));
					var4.hurt(this.damageSources().anvil(var4),20F);
					continue;
				}else{
					break;
				}
			} while (true);
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
				if ((var2.zza) > (0F)){
					var3 = 0.25F;
				}else{
					if ((var2.zza) < (0F)){
						var3 = -0.25F;
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
	public void positionRider(Entity var1, Entity.MoveFunction var2) {
		if (this.hasPassenger(var1)){
			float var3 = (float)((this.isRemoved()) ? (0.009999999776482582D) : (this.getPassengerAttachmentPoint(var1,this.getDimensions(Pose.STANDING),1F).y));
			var1.setPos(this.getX(),(this.getY()) + ((double)(var3)),this.getZ());
		}
	}
	public Vec3 getDismountLocationForPassenger(LivingEntity var1) {
		Vec3 var2 = this.getLookAngle().reverse();
		return var1.position().add(0D,0.5D,0D).add(var2);
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
				if (((var5_1) == (0)) && (!((this.getDamage()) > (20F)))){

				}else{
					if (((var5_1) == (0)) && (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS))){
						this.spawnAtLocation(((Item)(ModItems.UNICYCLE.get())).asItem().getDefaultInstance());
					}
					this.remove(Entity.RemovalReason.KILLED);
				}
				return true;
			}else{
				return true;
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
		return ((Item)(ModItems.UNICYCLE.get())).getDefaultInstance();
	}
	@OnlyIn(value=Dist.CLIENT)
	public void onPassengerTurned(Entity var1) {
		this.clampRotation(var1);
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
	protected void readAdditionalSaveData(CompoundTag var1) {
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
	}
}