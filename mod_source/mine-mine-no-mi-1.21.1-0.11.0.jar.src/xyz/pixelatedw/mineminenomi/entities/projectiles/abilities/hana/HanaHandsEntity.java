package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class HanaHandsEntity extends Entity implements TraceableEntity, IEntityWithComplexSpawn {
	// Fields
	private int warmupDelayTicks;
	private int lifeTicks;
	private float damage;
	private boolean sentEvent;
	private boolean clientSideAttackStarted;
	private LivingEntity owner;
	private LivingEntity target;
	private UUID casterUuid;
	private IAbility parentAbility;
	private HanaHandsEntity$HandsKind kind;

	public HanaHandsEntity(EntityType<? extends HanaHandsEntity> var1, Level var2) {
		super(var1,var2);
		this.warmupDelayTicks = 30;
		this.lifeTicks = 22;
	}
	public HanaHandsEntity(Level var1, LivingEntity var2, IAbility var3) {
		this(((EntityType)(ModProjectiles.HANA_HANDS.get())),var1);
		this.owner = var2;
		this.parentAbility = var3;
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
	@Nullable()
	public Entity getOwner() {
		if (((this.owner) == (null)) && ((this.casterUuid) != (null))){
			Level var1 = this.level();
			if (var1 instanceof ServerLevel){
				Entity var2 = ((ServerLevel)(var1)).getEntity(this.casterUuid);
				if (var2 instanceof LivingEntity){
					this.owner = ((LivingEntity)(var2));
				}
			}
		}
		return this.owner;
	}
	public void setTarget(LivingEntity var1) {
		this.target = var1;
		this.setPos(var1.getX(),var1.getY(),var1.getZ());
	}
	public LivingEntity getTarget() {
		return this.target;
	}
	public void setWarmup(int var1) {
		this.warmupDelayTicks = var1;
	}
	public void setDamage(float var1) {
		this.damage = var1;
	}
	public void setClutch() {
		this.kind = HanaHandsEntity$HandsKind.CLUTCH;
	}
	public void setSlap() {
		this.kind = HanaHandsEntity$HandsKind.SLAP;
	}
	public void setTwist() {
		this.kind = HanaHandsEntity$HandsKind.TWIST;
	}
	public HanaHandsEntity$HandsKind getHandsType() {
		return this.kind;
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
		var1.putInt("Warmup",this.warmupDelayTicks);
		var1.putInt("Type",this.kind.ordinal());
		if ((this.casterUuid) != (null)){
			var1.putUUID("OwnerUUID",this.casterUuid);
		}
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
		this.warmupDelayTicks = var1.getInt("Warmup");
		this.kind = HanaHandsEntity$HandsKind.values()[var1.getInt("Type")];
		if (var1.hasUUID("OwnerUUID")){
			this.casterUuid = var1.getUUID("OwnerUUID");
		}
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		var1.writeInt(this.kind.ordinal());
		int var2 = (((this.owner) != (null)) && ((this.target) != (null))) ? (1) : (0);
		var1.writeBoolean((var2) != (0));
		if ((var2) != (0)){
			var1.writeInt(this.owner.getId());
			var1.writeInt(this.target.getId());
		}
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		this.kind = HanaHandsEntity$HandsKind.values()[var1.readInt()];
		if (var1.readBoolean()){
			int var2 = var1.readInt();
			Entity var3 = this.level().getEntity(var2);
			if (var3 instanceof LivingEntity){
				LivingEntity var4 = ((LivingEntity)(var3));
				this.owner = var4;
			}
			int var4_1 = var1.readInt();
			Entity var5 = this.level().getEntity(var4_1);
			if (var5 instanceof LivingEntity){
				this.setTarget(((LivingEntity)(var5)));
			}else{
				return;
			}
		}
	}
	public void tick() {
		if (((this.target) != (null)) && (this.target.isAlive())){
			this.moveTo(this.target.getX(),this.target.getY(),this.target.getZ(),this.target.getYRot(),this.target.getXRot());
		}
		super.tick();
		if (this.level().isClientSide){
			if (this.clientSideAttackStarted){
				this.lifeTicks = (this.lifeTicks) - (1);
				if ((this.lifeTicks) == (14)){
					int var1 = 0;
					do{
						if ((var1) < (12)){
							double var2 = (this.getX()) + (((((this.random.nextDouble()) * (2D)) - (1D)) * ((double)(this.getBbWidth()))) * (0.5D));
							double var3 = ((this.getY()) + (0.05D)) + (this.random.nextDouble());
							double var4 = (this.getZ()) + (((((this.random.nextDouble()) * (2D)) - (1D)) * ((double)(this.getBbWidth()))) * (0.5D));
							double var5 = (((this.random.nextDouble()) * (2D)) - (1D)) * (0.3D);
							double var6 = (0.3D) + ((this.random.nextDouble()) * (0.3D));
							double var7 = (((this.random.nextDouble()) * (2D)) - (1D)) * (0.3D);
							this.level().addParticle((ParticleOptions)(ParticleTypes.CRIT),var2,(var3) + (1D),var4,var5,var6,var7);
							var1++;
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
		}else{
			int var1_1 = (this.warmupDelayTicks) - (1);
			this.warmupDelayTicks = var1_1;
			if ((var1_1) < (0)){
				if (((this.warmupDelayTicks) == (-8)) && ((this.target) != (null))){
					this.damage(this.target);
				}
				if (!(this.sentEvent)){
					this.level().broadcastEntityEvent((Entity)(this),(byte)(4));
					this.sentEvent = true;
				}
				int var2_1 = (this.lifeTicks) - (1);
				this.lifeTicks = var2_1;
				if ((var2_1) < (0)){
					this.discard();
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	private void damage(LivingEntity var1) {
		Entity var2 = this.getOwner();
		if ((var1.isAlive()) && ((!(var1.isInvulnerable())) && (((var2) != (null)) && (((var1) != (var2)) && (var2 instanceof LivingEntity))))){
			LivingEntity var3 = ((LivingEntity)(var2));
			HanaHandsEntity var4 = this;
			LivingEntity var5 = var3;
			LivingEntity var6 = var1;
			final LivingEntity var5_f1 = var5;
			final LivingEntity var6_f2 = var6;
			Consumer<DealDamageComponent> var7 = (l0) -> {
			l0.hurtTarget(var5_f1,var6_f2,this.damage);
		};
			((java.util.Optional)(this.parentAbility.getComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get()))))).ifPresent((Consumer)(var7));
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void handleEntityEvent(byte var1) {
		super.handleEntityEvent(var1);
		if ((var1) == (4)){
			this.clientSideAttackStarted = true;
			if (!(this.isSilent())){
				this.level().playLocalSound(this.getX(),this.getY(),this.getZ(),((SoundEvent)(ModSounds.SNAP_SFX.get())),this.getSoundSource(),2F,((this.random.nextFloat()) * (0.2F)) + (0.85F),false);
			}else{
				return;
			}
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public float getAnimationProgress(float var1) {
		if (!(this.clientSideAttackStarted)){
			return 0F;
		}else{
			int var2 = (this.lifeTicks) - (2);
			return ((var2) <= (0)) ? (1F) : ((1F) - ((((float)(var2)) - (var1)) / (20F)));
		}
	}
}