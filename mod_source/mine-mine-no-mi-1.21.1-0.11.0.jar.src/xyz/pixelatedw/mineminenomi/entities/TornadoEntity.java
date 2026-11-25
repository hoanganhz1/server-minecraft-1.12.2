package xyz.pixelatedw.mineminenomi.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.suna.SablesNewParticleEffect$Details;

public class TornadoEntity extends Entity implements IEntityWithComplexSpawn {
	// Fields
	private LivingEntity owner;
	private SablesNewParticleEffect$Details details;
	private int maxLife;
	private float size;
	private float speed;
	private Vec3 vector;
	private List<BlockState> states;

	public TornadoEntity(Level var1, LivingEntity var2) {
		super(((EntityType)(ModEntities.TORNADO.get())),var1);
		this.details = new SablesNewParticleEffect$Details();
		this.maxLife = 60;
		this.size = 1F;
		this.speed = 1F;
		this.vector = null;
		this.states = new ArrayList();
		this.owner = var2;
	}
	public TornadoEntity(EntityType<?> var1, Level var2) {
		super(var1,var2);
		this.details = new SablesNewParticleEffect$Details();
		this.maxLife = 60;
		this.size = 1F;
		this.speed = 1F;
		this.vector = null;
		this.states = new ArrayList();
	}
	public void setMaxLife(int var1) {
		this.maxLife = var1;
	}
	public void setSize(float var1) {
		this.size = var1;
	}
	public void setSpeed(float var1) {
		this.speed = var1;
	}
	public int getMaxLife() {
		return this.maxLife;
	}
	public float getSize() {
		return this.size;
	}
	public float getSpeed() {
		return this.speed;
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
	public void tick() {
		super.tick();
		if (!(this.level().isClientSide)){
			if (((this.maxLife) > (0)) && ((this.tickCount) >= (this.maxLife))){
				this.discard();
				return;
			}else{
				if ((this.owner) == (null)){
					this.discard();
					return;
				}else{
					if (((this.tickCount) % (40)) == (0)){
						this.details.setSize(this.size);
						WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.SABLES_NEW.get())),(Entity)(this),this.getX(),this.getY(),this.getZ(),(ParticleEffect$Details)(this.details));
					}
					AABB var1 = new AABB(this.blockPosition()).inflate((double)((this.size) / (2F)),(double)(this.size),(double)((this.size) / (2F)));
					TornadoEntity var2 = this;
					Predicate<Entity> var3 = (l0) -> {
			return (l0) != (this.owner);
		};
					Iterator var4 = this.level().getEntitiesOfClass(Entity.class,var1,var3).iterator();
					do{
						if (var4.hasNext()){
							Entity var5 = ((Entity)(var4.next()));
							double var6_1 = (var5.getDeltaMovement().x) + (((this.getX()) - (var5.getX())) / (25D));
							double var7_1 = (var5.getDeltaMovement().y) + ((((this.getY()) + ((double)((this.size) / (2F)))) - (var5.getY())) / (25D));
							double var8_1 = (var5.getDeltaMovement().z) + (((this.getZ()) - (var5.getZ())) / (25D));
							AbilityHelper.setDeltaMovement(var5,var6_1,var7_1,var8_1);
							if ((this.distanceTo(var5)) < (2F)){
								var5.hurt(this.damageSources().flyIntoWall(),(this.getSize()) / (3F));
							}
							if (var5 instanceof FallingBlockEntity){
								FallingBlockEntity var9 = ((FallingBlockEntity)(var5));
								this.states.add(var9.getBlockState());
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					double var6 = 0.0;
					double var7 = 0.0;
					double var8 = 0.0;
					if ((ProtectedAreasData.get(((ServerLevel)(this.level()))).getProtectedArea((int)(this.getX()),(int)(this.getY()),(int)(this.getZ())).isPresent()) && (ServerConfig.isAbilityGriefingEnabled())){
						BlockPos.MutableBlockPos var5_1 = new BlockPos.MutableBlockPos();
						var6 = var1.minX;
						LOOP_2:
						do{
							if ((var6) < (var1.maxX)){
								var7 = var1.minY;
								LOOP_1:
								do{
									if ((var7) < (var1.maxY)){
										var8 = var1.minZ;
										do{
											if ((var8) < (var1.maxZ)){
												var5_1.set(var6,var7,var8);
												BlockState var9_1 = this.level().getBlockState((BlockPos)(var5_1));
												int var10 = ((this.level().getBlockState(var5_1.above()).getBlock()) == (Blocks.AIR)) ? (1) : (0);
												if ((!(var9_1.isAir())) && ((((double)(this.random.nextFloat())) > (0.9995D)) && ((!(DefaultProtectionRules.CORE_FOLIAGE_ORE.isBanned(var9_1))) && ((var10) != (0))))){
													FallingBlockEntity var11 = FallingBlockEntity.fall(this.level(),(BlockPos)(var5_1),var9_1);
													AbilityHelper.setDeltaMovement((Entity)(var11),0D,(WyHelper.randomDouble()) * (2D),0D);
													var11.dropItem = false;
													var11.time = 1;
													this.level().addFreshEntity((Entity)(var11));
													NuWorld.setBlockState(this.level(),(BlockPos)(var5_1),Blocks.AIR.defaultBlockState(),2,(BlockProtectionRule)(null));
												}
												var8 = (var8) + (1D);
												continue;
											}else{
												break;
											}
										} while (true);
										var7 = (var7) + (1D);
										continue LOOP_1;
									}else{
										break;
									}
								} while (true);
								var6 = (var6) + (1D);
								continue LOOP_2;
							}else{
								break;
							}
						} while (true);
					}
					if ((this.vector) != (null)){
						this.noPhysics = true;
						Vec3 var5_2 = this.position().subtract(this.vector).add(0D,-1D,0D);
						double var6_1 = 20D;
						double var7_1 = 0.4D;
						double var8_1 = Math.min(var7_1,(-(var5_2.x)) / (var6_1));
						double var9_2 = Math.min(var7_1,(-(var5_2.z)) / (var6_1));
						this.move(MoverType.SELF,new Vec3(var8_1,0D,var9_2));
					}
					if ((this.isInWaterOrRain()) && (((this.tickCount) % (20)) == (0))){
						this.discard();
					}else{
						return;
					}
				}
			}
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean isColliding(BlockPos var1, BlockState var2) {
		return false;
	}
	public EntityDimensions getDimensions(Pose var1) {
		return this.getType().getDimensions().scale((this.size) / (2F));
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
		this.maxLife = var1.getInt("maxLife");
		this.size = var1.getFloat("size");
		this.speed = var1.getFloat("speed");
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
		var1.putInt("maxLife",this.maxLife);
		var1.putFloat("size",this.size);
		var1.putFloat("speed",this.speed);
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		var1.writeFloat(this.size);
		var1.writeFloat(this.speed);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		this.size = var1.readFloat();
		this.speed = var1.readFloat();
	}
	public List<BlockState> getBlocksUsed() {
		return this.states;
	}
	public void setVector(Vec3 var1) {
		this.vector = var1;
	}
}