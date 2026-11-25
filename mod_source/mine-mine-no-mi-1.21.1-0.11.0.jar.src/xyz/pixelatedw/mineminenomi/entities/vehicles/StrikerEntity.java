package xyz.pixelatedw.mineminenomi.entities.vehicles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncStrikerCrewPacket;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class StrikerEntity extends Entity {
	// Fields
	private static final EntityDataAccessor<Float> FUEL = SynchedEntityData.defineId(StrikerEntity.class,EntityDataSerializers.FLOAT);
	private static final float MAX_FUEL = 100F;
	private static final float SPEED = 0.07F;
	private static final float BACKWARDS_SPEED = -0.014F;
	private static final float TURN_SPEED = 0.5F;
	private static final byte HURT_EVENT = 100;
	private float deltaRotation;
	private int lerpSteps;
	private double lerpX;
	private double lerpY;
	private double lerpZ;
	private double lerpYRot;
	private double lerpXRot;
	private double waterLevel;
	private StrikerEntity$Status status;
	private StrikerEntity$Status oldStatus;
	private double lastYd;
	private float invFriction;
	private float landFriction;
	@Nullable() private UUID lastRiderUUID;
	@Nullable() private Player lastRider;
	private float damage;
	private int hurtTime;
	@Nullable() private Crew crew;

	public StrikerEntity(Level var1) {
		this(((EntityType)(ModEntities.STRIKER.get())),var1);
	}
	public StrikerEntity(EntityType<?> var1, Level var2) {
		super(var1,var2);
		this.blocksBuilding = true;
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		var1.define(FUEL,Float.valueOf(0F));
	}
	public boolean canCollideWith(Entity var1) {
		return canVehicleCollide((Entity)(this),var1);
	}
	public static boolean canVehicleCollide(Entity var0, Entity var1) {
		return ((var1.canBeCollidedWith()) || (var1.isPushable())) && (!(var0.isPassengerOfSameVehicle(var1)));
	}
	public boolean canBeCollidedWith() {
		return true;
	}
	public boolean isPushable() {
		return true;
	}
	public boolean isPickable() {
		return this.isAlive();
	}
	public boolean shouldRiderSit() {
		if (((this.getControllingPassenger()) != (null)) && (this.isMeraUser(this.getControllingPassenger()))){
			return false;
		}else{
			return true;
		}
	}
	protected Vec3 getPassengerAttachmentPoint(Entity var1, EntityDimensions var2, float var3) {
		if (((this.getControllingPassenger()) != (null)) && (this.isMeraUser(this.getControllingPassenger()))){
			return new Vec3(0D,0.3D,0D);
		}else{
			return new Vec3(0D,-0.15D,0D);
		}
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
		this.oldStatus = this.status;
		this.status = this.getStatus();
		if (((this.getControllingPassenger()) != (null)) && (!(((this.lastRiderUUID) != (null)) && (this.lastRiderUUID.equals(this.getControllingPassenger().getUUID()))))){
			this.lastRiderUUID = this.getControllingPassenger().getUUID();
		}
		if (!(this.level().isClientSide)){
			this.consumeFuel();
		}
		if ((this.getHurtTime()) > (0)){
			this.setHurtTime((this.getHurtTime()) - (1));
		}
		if ((this.getDamage()) > (0F)){
			this.setDamage((this.getDamage()) - (1F));
		}
		super.tick();
		this.tickLerp();
		if (this.isControlledByLocalInstance()){
			this.invFriction = 0.05F;
			double var1 = (this.isNoGravity()) ? (0D) : (-0.03999999910593033D);
			double var2 = 0D;
			if (((this.oldStatus) == (StrikerEntity$Status.IN_AIR)) && (((this.status) != (StrikerEntity$Status.IN_AIR)) && ((this.status) != (StrikerEntity$Status.ON_LAND)))){
				this.waterLevel = this.getY(1D);
				this.setPos(this.getX(),((double)((this.getWaterLevelAbove()) - (this.getBbHeight()))) + (0.101D),this.getZ());
				this.setDeltaMovement(this.getDeltaMovement().multiply(1D,0D,1D));
				this.lastYd = 0D;
				this.status = StrikerEntity$Status.IN_WATER;
			}else{
				if ((this.status) == (StrikerEntity$Status.IN_WATER)){
					var2 = ((this.waterLevel) - (this.getY())) / ((double)(this.getBbHeight()));
					this.invFriction = 0.9F;
				}else{
					if ((this.status) == (StrikerEntity$Status.UNDER_FLOWING_WATER)){
						var1 = -0.0007D;
						this.invFriction = 0.9F;
					}else{
						if ((this.status) == (StrikerEntity$Status.UNDER_WATER)){
							var2 = 0.009999999776482582D;
							this.invFriction = 0.45F;
						}else{
							if ((this.status) == (StrikerEntity$Status.IN_AIR)){
								this.invFriction = 0.9F;
							}else{
								if ((this.status) == (StrikerEntity$Status.ON_LAND)){
									this.invFriction = this.landFriction;
									if (this.getControllingPassenger() instanceof Player){
										this.landFriction = (this.landFriction) / (2F);
									}
								}
							}
						}
					}
				}
			}
			Vec3 var3 = this.getDeltaMovement();
			this.setDeltaMovement((var3.x) * ((double)(this.invFriction)),(var3.y) + (var1),(var3.z) * ((double)(this.invFriction)));
			this.deltaRotation = (this.deltaRotation) * (this.invFriction);
			if ((var2) > (0D)){
				Vec3 var4 = this.getDeltaMovement();
				this.setDeltaMovement(var4.x,((var4.y) + ((var2) * (0.06153846016296973D))) * (0.75D),var4.z);
			}
			if (((this.getControllingPassenger()) != (null)) && (this.hasFuel(this.getControllingPassenger()))){
				this.controlVehicle();
			}
			this.move(MoverType.SELF,this.getDeltaMovement());
		}else{
			AbilityHelper.setDeltaMovement(this,Vec3.ZERO);
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
	private void consumeFuel() {
		LivingEntity var1 = this.getControllingPassenger();
		if (((var1) != (null)) && (var1 instanceof Player)){
			Player var2 = ((Player)(var1));
			if (this.isMeraUser((LivingEntity)(var2))){
				return;
			}else{
				float var3 = 0F;
				if ((var2.zza) > (0F)){
					var3 = 0.07F;
				}else{
					if ((var2.zza) < (0F)){
						var3 = -0.014F;
					}
				}
				float var4 = (this.getFuel()) - (Math.abs((var3) / (4F)));
				var4 = Mth.clamp(var4,0F,100F);
				this.setFuel(var4);
			}
		}
	}
	public void positionRider(Entity var1, Entity.MoveFunction var2) {
		if (this.hasPassenger(var1)){
			float var3 = 0F;
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
			var1.setYRot((var1.getYRot()) + (this.deltaRotation));
			var1.setYHeadRot((var1.getYHeadRot()) + (this.deltaRotation));
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
	private StrikerEntity$Status getStatus() {
		StrikerEntity$Status var1 = this.isUnderwater();
		if ((var1) != (null)){
			this.waterLevel = this.getBoundingBox().maxY;
			return var1;
		}else{
			if (this.checkInWater()){
				return StrikerEntity$Status.IN_WATER;
			}else{
				float var2 = this.getGroundFriction();
				if ((var2) > (0F)){
					this.landFriction = var2;
					return StrikerEntity$Status.ON_LAND;
				}else{
					return StrikerEntity$Status.IN_AIR;
				}
			}
		}
	}
	private void controlVehicle() {
		if (this.isVehicle()){
			LivingEntity var1 = this.getControllingPassenger();
			if (((var1) != (null)) && (var1 instanceof Player)){
				Player var2 = ((Player)(var1));
				if ((var2.xxa) > (0F)){
					this.deltaRotation = (this.deltaRotation) - (0.5F);
				}else{
					if ((var2.xxa) < (0F)){
						this.deltaRotation = (this.deltaRotation) + (0.5F);
					}
				}
				this.setYRot((this.getYRot()) + (this.deltaRotation));
				float var3 = 0F;
				if ((var2.zza) > (0F)){
					var3 = 0.07F;
				}else{
					if ((var2.zza) < (0F)){
						var3 = -0.014F;
					}
				}
				AbilityHelper.setDeltaMovement(this,this.getDeltaMovement().add((double)((Mth.sin((-(this.getYRot())) * (0.017453292F))) * (var3)),0D,(double)((Mth.cos((this.getYRot()) * (0.017453292F))) * (var3))));
				if (this.isMeraUser((LivingEntity)(var2))){
					int var4 = 0;
					do{
						if ((var4) < (5)){
							double var5 = (WyHelper.randomDouble()) / (2.5D);
							double var6 = (WyHelper.randomDouble()) / (4D);
							double var7 = (WyHelper.randomDouble()) / (2.5D);
							SimpleParticleData var8 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
							var8.setLife(5);
							var8.setSize(3F);
							var2.level().addParticle((ParticleOptions)(var8),(this.getX()) + (var5),((this.getY()) + (0.5D)) + (var6),(this.getZ()) + (var7),0D,0D,0D);
							var4++;
							continue;
						}else{
							break;
						}
					} while (true);
				}
				if ((var3) > (0F)){
					double var4_1 = (Mth.wrapDegrees(((double)(this.getYRot())) * (0.017453292519943295D))) + (0.8D);
					double var5_1 = 1.5D;
					double var6_1 = ((var5_1) * (Math.cos(var4_1))) - ((var5_1) * (Math.sin(var4_1)));
					double var7_1 = ((var5_1) * (Math.cos(var4_1))) + ((var5_1) * (Math.sin(var4_1)));
					int var8_1 = 0;
					do{
						if ((var8_1) < (20)){
							double var9 = ((WyHelper.randomDouble()) / (2.5D)) - (var6_1);
							double var10 = (WyHelper.randomDouble()) / (2.5D);
							double var11 = ((WyHelper.randomDouble()) / (2.5D)) - (var7_1);
							SimpleParticleData var12 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MERA.get())));
							var12.setLife(10);
							var12.setSize(4F);
							var12.setMotion(-(this.getDeltaMovement().x),-(this.getDeltaMovement().y),-(this.getDeltaMovement().z));
							var2.level().addParticle((ParticleOptions)(var12),(this.getX()) + (var9),((this.getY()) + (0.5D)) + (var10),(this.getZ()) + (var11),0D,0D,0D);
							var8_1++;
							continue;
						}else{
							break;
						}
					} while (true);
				}else{
					return;
				}
			}else{
				return;
			}
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
		ItemStack var3 = var1.getMainHandItem();
		if (var2.equals(InteractionHand.OFF_HAND)){
			return InteractionResult.PASS;
		}else{
			if (var1.isCrouching()){
				WyHelper.sendMessage((LivingEntity)(var1),this.getFuelText());
				return InteractionResult.CONSUME;
			}else{
				if (((var3) != (null)) && ((!(var3.isEmpty())) && ((this.getFuel()) < (100F)))){
					float var4 = ModTags$Items.STRIKER_FUEL.getValue(var3.getItem());
					if (!(var1.getAbilities().instabuild)){
						var3.shrink(1);
					}
					this.setFuel((this.getFuel()) + (var4));
					WyHelper.sendMessage((LivingEntity)(var1),this.getFuelText());
					return InteractionResult.CONSUME;
				}else{
					if (this.level().isClientSide){
						return InteractionResult.SUCCESS;
					}else{
						if (var1.startRiding((Entity)(this))){
							Crew var4_1 = FactionsWorldData.get().getCrewWithMember(var1.getUUID());
							if ((var4_1) != (null)){
								this.crew = var4_1;
								ModNetwork.sendToAllTracking(new SSyncStrikerCrewPacket((StrikerEntity)(this),(Crew)(var4_1)),this);
							}
							return InteractionResult.CONSUME;
						}else{
							return InteractionResult.PASS;
						}
					}
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
				if (((var5_1) == (0)) && (!((this.getDamage()) > (30F)))){

				}else{
					if (((var5_1) == (0)) && (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS))){
						this.spawnAtLocation(((Item)(ModItems.STRIKER.get())).getDefaultInstance());
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
	private boolean isMeraUser(LivingEntity var1) {
		Function<DevilFruitData, Boolean> var2 = (l0) -> {
			return Boolean.valueOf(l0.hasDevilFruit(((AkumaNoMiItem)(ModFruits.MERA_MERA_NO_MI.get()))));
		};
		if (((Boolean)(((java.util.Optional)(DevilFruitData.get(var1))).map((Function)(var2)).orElse(Boolean.valueOf(false)))).booleanValue()){
			return true;
		}else{
			return false;
		}
	}
	private Component getFuelText() {
		Object[] var1 = new Object[1];
		String var2 = String.format("%.2f",new Object[]{Float.valueOf(this.getFuel())});
		String var3 = var2 + "%";
		var1[0] = var3;
		return Component.translatable(ModI18n.INFO_FUEL_LEFT,var1);
	}
	private boolean hasFuel(LivingEntity var1) {
		return (this.isMeraUser(var1)) || ((this.getFuel()) > (0F));
	}
	public float getGroundFriction() {
		AABB var1 = this.getBoundingBox();
		AABB var2 = new AABB(var1.minX,(var1.minY) - (0.001D),var1.minZ,var1.maxX,var1.minY,var1.maxZ);
		int var3 = (Mth.floor(var2.minX)) - (1);
		int var4 = (Mth.ceil(var2.maxX)) + (1);
		int var5 = (Mth.floor(var2.minY)) - (1);
		int var6 = (Mth.ceil(var2.maxY)) + (1);
		int var7 = (Mth.floor(var2.minZ)) - (1);
		int var8 = (Mth.ceil(var2.maxZ)) + (1);
		VoxelShape var9 = Shapes.create(var2);
		float var10 = 0F;
		int var11 = 0;
		BlockPos.MutableBlockPos var12 = new BlockPos.MutableBlockPos();
		int var13 = var3;
		LOOP_1:
		do{
			if ((var13) < (var4)){
				int var14 = var7;
				do{
					if ((var14) < (var8)){
						int var15 = ((((var13) == (var3)) || ((var13) == ((var4) - (1)))) ? (1) : (0)) + ((((var14) == (var7)) || ((var14) == ((var8) - (1)))) ? (1) : (0));
						if ((var15) != (2)){
							int var16 = var5;
							do{
								if ((var16) < (var6)){
									if (((var15) > (0)) && (!(((var16) != (var5)) && ((var16) != ((var6) - (1)))))){
										var16++;
										continue;
									}else{
										var12.set(var13,var16,var14);
										BlockState var17 = this.level().getBlockState((BlockPos)(var12));
										if ((!(var17.getBlock() instanceof WaterlilyBlock)) && (Shapes.joinIsNotEmpty(var17.getCollisionShape((BlockGetter)(this.level()),(BlockPos)(var12)).move((double)(var13),(double)(var16),(double)(var14)),var9,BooleanOp.AND))){
											var10 = (var10) + (var17.getFriction((LevelReader)(this.level()),(BlockPos)(var12),(Entity)(this)));
											var11++;
											var16++;
											continue;
										}else{
											var16++;
											continue;
										}
									}
								}else{
									break;
								}
							} while (true);
						}
						var14++;
						continue;
					}else{
						break;
					}
				} while (true);
				var13++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
		return (var10) / ((float)(var11));
	}
	public float getWaterLevelAbove() {
		AABB var1 = this.getBoundingBox();
		int var2 = Mth.floor(var1.minX);
		int var3 = Mth.ceil(var1.maxX);
		int var4 = Mth.floor(var1.maxY);
		int var5 = Mth.ceil((var1.maxY) - (this.lastYd));
		int var6 = Mth.floor(var1.minZ);
		int var7 = Mth.ceil(var1.maxZ);
		BlockPos.MutableBlockPos var8 = new BlockPos.MutableBlockPos();
		int var9 = var4;
		do{
			if ((var9) < (var5)){
				float var10 = 0F;
				int var11 = var2;
				LOOP_1:
				do{
					if ((var11) < (var3)){
						int var12 = var6;
						do{
							if ((var12) < (var7)){
								var8.set(var11,var9,var12);
								FluidState var13 = this.level().getFluidState((BlockPos)(var8));
								if (var13.is((Fluid)(Fluids.WATER))){
									var10 = Math.max(var10,var13.getHeight((BlockGetter)(this.level()),(BlockPos)(var8)));
								}
								if ((var10) >= (1F)){

								}else{
									var12++;
									continue;
								}
								var9++;
								continue;
							}else{
								break;
							}
						} while (true);
						var11++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				if ((var10) < (1F)){
					return ((float)(var8.getY())) + (var10);
				}else{
					var9++;
					continue;
				}
			}else{
				return (float)((var5) + (1));
			}
		} while (true);
	}
	private boolean checkInWater() {
		AABB var1 = this.getBoundingBox();
		int var2 = Mth.floor(var1.minX);
		int var3 = Mth.ceil(var1.maxX);
		int var4 = Mth.floor(var1.minY);
		int var5 = Mth.ceil((var1.minY) + (0.001D));
		int var6 = Mth.floor(var1.minZ);
		int var7 = Mth.ceil(var1.maxZ);
		int var8 = 0;
		this.waterLevel = -1.7976931348623157e+308D;
		BlockPos.MutableBlockPos var9 = new BlockPos.MutableBlockPos();
		int var10 = var2;
		LOOP_2:
		do{
			if ((var10) < (var3)){
				int var11 = var4;
				LOOP_1:
				do{
					if ((var11) < (var5)){
						int var12 = var6;
						do{
							if ((var12) < (var7)){
								var9.set(var10,var11,var12);
								FluidState var13 = this.level().getFluidState((BlockPos)(var9));
								if (var13.is((Fluid)(Fluids.WATER))){
									float var14 = ((float)(var11)) + (var13.getHeight((BlockGetter)(this.level()),(BlockPos)(var9)));
									this.waterLevel = Math.max((double)(var14),this.waterLevel);
									var8 = (var8) | (((var1.minY) < ((double)(var14))) ? (1) : (0));
								}
								var12++;
								continue;
							}else{
								break;
							}
						} while (true);
						var11++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				var10++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
		return (((var8) & 1) != 0);
	}
	@Nullable()
	private StrikerEntity$Status isUnderwater() {
		AABB var1 = this.getBoundingBox();
		double var2 = (var1.maxY) + (0.001D);
		int var3 = Mth.floor(var1.minX);
		int var4 = Mth.ceil(var1.maxX);
		int var5 = Mth.floor(var1.maxY);
		int var6 = Mth.ceil(var2);
		int var7 = Mth.floor(var1.minZ);
		int var8 = Mth.ceil(var1.maxZ);
		boolean var9 = false;
		BlockPos.MutableBlockPos var10 = new BlockPos.MutableBlockPos();
		int var11 = var3;
		LOOP_2:
		do{
			if ((var11) < (var4)){
				int var12 = var5;
				LOOP_1:
				do{
					if ((var12) < (var6)){
						int var13 = var7;
						do{
							if ((var13) < (var8)){
								var10.set(var11,var12,var13);
								FluidState var14 = this.level().getFluidState((BlockPos)(var10));
								if ((var14.is((Fluid)(Fluids.WATER))) && ((var2) < ((double)(((float)(var10.getY())) + (var14.getHeight((BlockGetter)(this.level()),(BlockPos)(var10))))))){
									if (!(var14.isSource())){
										return StrikerEntity$Status.UNDER_FLOWING_WATER;
									}else{
										var9 = true;
									}
								}
								var13++;
								continue;
							}else{
								break;
							}
						} while (true);
						var12++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				var11++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
		return (var9) ? (StrikerEntity$Status.UNDER_WATER) : (null);
	}
	public void push(Entity var1) {
		if (var1 instanceof StrikerEntity){
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
	protected void clampRotation(Entity var1) {
		var1.setYBodyRot(this.getYRot());
		float var2 = Mth.wrapDegrees((var1.getYRot()) - (this.getYRot()));
		float var3 = Mth.clamp(var2,-105F,105F);
		var1.yRotO = (var1.yRotO) + ((var3) - (var2));
		var1.setYRot(((var1.getYRot()) + (var3)) - (var2));
		var1.setYHeadRot(var1.getYRot());
	}
	protected void checkInsideBlocks() {
		AABB var1 = this.getBoundingBox();
		BlockPos var2 = BlockPos.containing((var1.minX) + (0.001D),(var1.minY) + (0.001D),(var1.minZ) + (0.001D));
		BlockPos var3 = BlockPos.containing((var1.maxX) - (0.001D),(var1.maxY) - (0.001D),(var1.maxZ) - (0.001D));
		BlockPos.MutableBlockPos var4 = new BlockPos.MutableBlockPos();
		if (this.level().hasChunksAt(var2,var3)){
			int var5 = var2.getX();
			LOOP_2:
			do{
				if ((var5) <= (var3.getX())){
					int var6 = var2.getY();
					LOOP_1:
					do{
						if ((var6) <= (var3.getY())){
							int var7 = var2.getZ();
							do{
								if ((var7) <= (var3.getZ())){
									var4.set(var5,var6,var7);
									if ((this.level().getBlockState((BlockPos)(var4)).getBlock()) == (Blocks.LILY_PAD)){
										this.level().destroyBlock((BlockPos)(var4),true,(Entity)(this));
									}
									var7++;
									continue;
								}else{
									break;
								}
							} while (true);
							var6++;
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					var5++;
					continue LOOP_2;
				}else{
					break;
				}
			} while (true);
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void onPassengerTurned(Entity var1) {
		this.clampRotation(var1);
	}
	public ItemStack getPickedResult(HitResult var1) {
		return ((Item)(ModItems.STRIKER.get())).asItem().getDefaultInstance();
	}
	@OnlyIn(value=Dist.CLIENT)
	public AABB getBoundingBoxForCulling() {
		AABB var1 = super.getBoundingBoxForCulling();
		AABB var2 = var1.inflate(0D,3D,0D);
		return var2;
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
		this.setFuel(var1.getFloat("fuel"));
		if (var1.hasUUID("lastRiderId")){
			this.lastRiderUUID = var1.getUUID("lastRiderId");
		}
		if (var1.contains("crew",10)){
			Crew var2 = Crew.from(var1.getCompound("crew"));
			if ((var2) != (null)){
				this.crew = var2;
			}else{
				return;
			}
		}
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
		var1.putFloat("fuel",this.getFuel());
		if ((this.getLastRiderId()) != (null)){
			var1.putUUID("lastRiderId",this.getLastRiderId());
		}
		if ((this.crew) != (null)){
			var1.put("crew",(Tag)(this.crew.write()));
		}
	}
	@Nullable()
	public UUID getLastRiderId() {
		return this.lastRiderUUID;
	}
	@Nullable()
	public Player getLastRider() {
		if (((this.lastRider) == (null)) && ((this.lastRiderUUID) != (null))){
			this.lastRider = this.level().getPlayerByUUID(this.lastRiderUUID);
		}
		return this.lastRider;
	}
	public void setFuel(float var1) {
		this.entityData.set(FUEL,Float.valueOf(var1));
	}
	public float getFuel() {
		return ((Float)(this.entityData.get(FUEL))).floatValue();
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
	@Nullable()
	public Crew getCrew() {
		return this.crew;
	}
	public void setCrew(Crew var1) {
		this.crew = var1;
	}
}