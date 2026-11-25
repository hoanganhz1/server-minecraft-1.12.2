package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.neoforged.neoforge.event.EventHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;

public class YagaraBullEntity extends TamableAnimal implements IEntityWithComplexSpawn {
	// Fields
	private static final EntityDataAccessor<Boolean> IS_SADDLED = SynchedEntityData.defineId(YagaraBullEntity.class,EntityDataSerializers.BOOLEAN);
	private static final double TAME_CHANCE = 0.35D;
	private static final Item[] SADDLES;
	private YagaraBullEntity$Status status;
	private YagaraBullEntity$Status oldStatus;
	private double waterLevel;
	private int textureId;

	public YagaraBullEntity(EntityType<? extends YagaraBullEntity> var1, Level var2) {
		super(var1,var2);
		this.getNavigation().setCanFloat(true);
	}
	protected void registerGoals() {
		this.goalSelector.addGoal(0,(Goal)(new FloatGoal((Mob)(this))));
		this.goalSelector.addGoal(0,(Goal)(new BreedGoal((Animal)(this),1D)));
		this.goalSelector.addGoal(1,(Goal)(new RandomStrollGoal((PathfinderMob)(this),1D)));
		this.goalSelector.addGoal(1,(Goal)(new RandomSwimmingGoal((PathfinderMob)(this),1.5D,20)));
		this.goalSelector.addGoal(1,(Goal)(new PanicGoal((PathfinderMob)(this),1.25D)));
		this.goalSelector.addGoal(2,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(2,(Goal)(new RandomLookAroundGoal((Mob)(this))));
		this.goalSelector.addGoal(3,(Goal)(new TemptGoal((PathfinderMob)(this),1.25D,(Predicate)(Ingredient.of(ModTags$Items.YAGARA_FOOD)),true)));
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		super.defineSynchedData(var1);
		var1.define(IS_SADDLED,Boolean.valueOf(false));
	}
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED,0.20000000298023224D).add(Attributes.FOLLOW_RANGE,40D).add(Attributes.MAX_HEALTH,30D);
	}
	public boolean isPushedByFluid() {
		return false;
	}
	public void tick() {
		this.oldStatus = this.status;
		this.status = this.getStatus();
		super.tick();
		if (this.isControlledByLocalInstance()){
			double var1 = (this.isNoGravity()) ? (0D) : (-0.004999999888241291D);
			double var2 = 0D;
			float var3 = 0.05F;
			if ((this.status) == (YagaraBullEntity$Status.IN_WATER)){
				var2 = ((this.waterLevel) - (this.getY())) / ((double)(this.getBbHeight()));
				var3 = (float)(this.getAttributeValue(NeoForgeMod.SWIM_SPEED));
			}else{
				if ((this.status) == (YagaraBullEntity$Status.UNDER_WATER)){
					var2 = 0.009999999776482582D;
					var3 = 0.45F;
				}else{
					if ((this.status) == (YagaraBullEntity$Status.IN_AIR)){
						var3 = 0.9F;
					}
				}
			}
			Vec3 var4 = this.getDeltaMovement();
			AbilityHelper.setDeltaMovement((Entity)(this),(var4.x) * ((double)(var3)),(var4.y) + (var1),(var4.z) * ((double)(var3)));
			if ((var2) > (0D)){
				Vec3 var5 = this.getDeltaMovement();
				AbilityHelper.setDeltaMovement((Entity)(this),var5.x,((var5.y) + ((var2) * (0.06153846016296973D))) * (0.75D),var5.z);
			}
			this.move(MoverType.SELF,this.getDeltaMovement());
		}
	}
	public void positionRider(Entity var1, Entity.MoveFunction var2) {
		if (this.hasPassenger(var1)){
			Vec3 var3 = this.getPassengerRidingPosition(var1).yRot(((-(this.getYRot())) * (0.017453292F)) - (1.5707964F));
			var1.setPos((this.getX()) + (var3.x),(this.getY()) + (var3.y()),(this.getZ()) + (var3.z));
		}
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
	public void travel(Vec3 var1) {
		if (this.isAlive()){
			if ((this.isVehicle()) && (this.isSaddled())){
				LivingEntity var2 = this.getControllingPassenger();
				this.setYRot(var2.getYRot());
				this.yRotO = this.getYRot();
				this.setXRot((var2.getXRot()) * (0.5F));
				this.setRot(this.getYRot(),this.getXRot());
				this.yBodyRot = this.getYRot();
				this.yHeadRot = this.yBodyRot;
				float var3 = (var2.xxa) * (0.3F);
				float var4 = var2.zza;
				if ((var4) <= (0F)){
					var4 = (var4) * (0.25F);
				}
				if (this.isControlledByLocalInstance()){
					this.setSpeed((float)(this.getAttributeValue(Attributes.MOVEMENT_SPEED)));
					super.travel(new Vec3((double)(var3),var1.y,(double)(var4)));
				}else{
					if (var2 instanceof Player){
						AbilityHelper.setDeltaMovement((Entity)(this),Vec3.ZERO);
					}
				}
				this.calculateEntityAnimation(false);
			}else{
				super.travel(var1);
			}
		}
	}
	@Nullable()
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable() SpawnGroupData var4) {
		if ((var4) == (null)){
			var4 = new AgeableMob.AgeableMobGroupData(0.2F);
		}
		this.textureId = this.random.nextInt(MobsHelper.YAGARA_BULL_TEXTURES.length);
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateRandomHealth());
		this.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue(this.generateRandomSwimSpeed());
		this.setHealth((float)(this.getAttribute(Attributes.MAX_HEALTH).getValue()));
		return super.finalizeSpawn(var1,var2,var3,var4);
	}
	public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
		YagaraBullEntity var3 = ((YagaraBullEntity)(((EntityType)(ModMobs.YAGARA_BULL.get())).create((Level)(var1))));
		this.setOffspringAttributes(var2,var3);
		return var3;
	}
	protected void setOffspringAttributes(AgeableMob var1, YagaraBullEntity var2) {
		double var3 = ((this.getAttributeBaseValue(Attributes.MAX_HEALTH)) + (var1.getAttributeBaseValue(Attributes.MAX_HEALTH))) + (this.generateRandomHealth());
		var2.getAttribute(Attributes.MAX_HEALTH).setBaseValue((var3) / (3D));
		double var4 = ((this.getAttributeBaseValue(NeoForgeMod.SWIM_SPEED)) + (var1.getAttributeBaseValue(NeoForgeMod.SWIM_SPEED))) + (this.generateRandomSwimSpeed());
		var2.getAttribute(NeoForgeMod.SWIM_SPEED).setBaseValue((var4) / (3D));
	}
	protected double generateRandomHealth() {
		return (30D) + ((this.random.nextDouble()) * (10D));
	}
	protected double generateRandomSwimSpeed() {
		return (1.1D) + ((this.random.nextDouble()) * (0.15D));
	}
	protected boolean isImmobile() {
		return (super.isImmobile()) && ((this.isVehicle()) && (this.isSaddled()));
	}
	public boolean isFood(ItemStack var1) {
		return var1.is(ModTags$Items.YAGARA_FOOD);
	}
	public InteractionResult mobInteract(Player var1, InteractionHand var2) {
		if ((!(var1.level().isClientSide)) && ((var2) == (InteractionHand.MAIN_HAND))){
			if ((this.isTame()) && ((var1) == (this.getOwner()))){
				ItemStack var3 = var1.getMainHandItem();
				if (!(var3.isEmpty())){
					Item var4 = var3.getItem();
					Objects.requireNonNull(var4);
					boolean var5 = Arrays.stream(SADDLES).anyMatch((Predicate)(((Predicate<Item>)(var4::equals))));
					if ((this.isFood(var3)) && ((this.getHealth()) < (this.getMaxHealth()))){
						var3.shrink(1);
						this.heal(4F);
						this.spawnHeartParticles();
						return InteractionResult.SUCCESS;
					}else{
						if ((var5) && (!(this.isSaddled()))){
							this.setSaddled(true);
							this.level().playSound(((Player)(null)),this.blockPosition(),SoundEvents.HORSE_SADDLE,SoundSource.PLAYERS,0.5F,1F);
							var3.shrink(1);
						}
					}
				}else{
					var1.startRiding((Entity)(this));
				}
			}else{
				if (!(this.isTame())){
					ItemStack var3_1 = var1.getMainHandItem();
					if ((!(var3_1.isEmpty())) && (var3_1.is(ModTags$Items.YAGARA_FOOD))){
						var3_1.shrink(1);
						if (((this.random.nextDouble()) < (0.35D)) && (!(EventHooks.onAnimalTame((Animal)(this),var1)))){
							this.spawnHeartParticles();
							this.tame(var1);
						}
					}
				}
			}
			return InteractionResult.FAIL;
		}else{
			return InteractionResult.FAIL;
		}
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.putInt("Texture",this.getTextureId());
		var1.putBoolean("IsSaddled",((Boolean)(this.entityData.get(IS_SADDLED))).booleanValue());
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		this.textureId = var1.getInt("Texture");
		this.entityData.set(IS_SADDLED,Boolean.valueOf(var1.getBoolean("IsSaddled")));
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		var1.writeInt(this.textureId);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		this.textureId = var1.readInt();
	}
	@Nullable()
	public LivingEntity getControllingPassenger() {
		if (this.isSaddled()){
			Entity var1 = this.getFirstPassenger();
			if (var1 instanceof LivingEntity){
				return ((LivingEntity)(var1));
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	public boolean canBeCollidedWith() {
		return true;
	}
	public boolean isPushable() {
		return true;
	}
	public Vec3 getPassengerRidingPosition(Entity var1) {
		return new Vec3(0D,0.6D,0D);
	}
	public boolean removeWhenFarAway(double var1) {
		return (!(this.isTame())) && ((var1) > (1024D));
	}
	public void spawnHeartParticles() {
		int var1 = 0;
		do{
			if ((var1) < (5)){
				double var2 = (WyHelper.randomDouble()) / (2D);
				double var3 = (WyHelper.randomDouble()) / (2D);
				double var4 = (WyHelper.randomDouble()) / (2D);
				((ServerLevel)(this.level())).sendParticles((ParticleOptions)(ParticleTypes.HEART),(this.getX()) + (var2),((this.getY()) + (1D)) + (var3),(this.getZ()) + (var4),1,0D,0D,0D,0D);
				var1++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void setSaddled(boolean var1) {
		this.entityData.set(IS_SADDLED,Boolean.valueOf(var1));
	}
	public boolean isSaddled() {
		return ((Boolean)(this.entityData.get(IS_SADDLED))).booleanValue();
	}
	public int getTextureId() {
		return this.textureId;
	}
	@Nullable()
	private YagaraBullEntity$Status isUnderwater() {
		AABB var1 = this.getBoundingBox();
		double var2 = (var1.maxY) + (0.002D);
		int var3 = Mth.floor(var1.minX);
		int var4 = Mth.ceil(var1.maxX);
		int var5 = Mth.floor(var1.minY);
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
								if ((var14.is(FluidTags.WATER)) && ((var2) < ((double)(((float)(var10.getY())) + (var14.getHeight((BlockGetter)(this.level()),(BlockPos)(var10))))))){
									if (!(var14.isSource())){
										return YagaraBullEntity$Status.UNDER_FLOWING_WATER;
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
		return (var9) ? (YagaraBullEntity$Status.UNDER_WATER) : (null);
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
		this.waterLevel = 5e-324D;
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
								if (var13.is(FluidTags.WATER)){
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
	private YagaraBullEntity$Status getStatus() {
		YagaraBullEntity$Status var1 = this.isUnderwater();
		if ((var1) != (null)){
			this.waterLevel = this.getBoundingBox().maxY;
			return var1;
		}else{
			if (this.checkInWater()){
				return YagaraBullEntity$Status.IN_WATER;
			}else{
				if ((this.getGroundFriction()) > (0F)){
					return YagaraBullEntity$Status.ON_LAND;
				}else{
					return YagaraBullEntity$Status.IN_AIR;
				}
			}
		}
	}
	public static boolean checkSpawnRules(EntityType<YagaraBullEntity> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, RandomSource var4) {
		return var1.getBlockState(var3.below()).getFluidState().is(FluidTags.WATER);
	}
	static  {
		Item[] var0 = new Item[]{Items.SADDLE};
		SADDLES = var0;
	}
}