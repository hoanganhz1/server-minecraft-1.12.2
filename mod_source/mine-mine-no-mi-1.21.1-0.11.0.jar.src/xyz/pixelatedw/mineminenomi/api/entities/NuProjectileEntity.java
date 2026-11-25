package xyz.pixelatedw.mineminenomi.api.entities;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.ComponentNotRegisteredException;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.BaseDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.events.ProjectileShootEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateProjBlockCollisionBox;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateProjEntityCollisionBox;

public class NuProjectileEntity extends ThrowableProjectile implements IEntityWithComplexSpawn {
	// Fields
	private int life;
	private int maxLife;
	private float damage;
	private int knockback;
	private float gravity;
	private boolean canPassThroughEntities;
	private boolean canPassThroughBlocks;
	protected DamageSource damageSource;
	protected IDamageSourceHandler damageSourceHandler;
	private Optional<IAbility> parent;
	private SourceElement sourceElement;
	private SourceHakiNature sourceHakiNature;
	private Set<SourceType> sourceType;
	private LivingEntity self;
	private UUID ownerUUID;
	private AABB entityCollisionBox;
	private AABB blockCollisionBox;
	private final Predicate<Entity> canCollideWithEntityPredicate;
	public final TargetPredicate targetsCheck;
	private final PriorityEventPool<NuProjectileEntity$IOnHitEntityEvent> onHitEntityEvents;
	private final PriorityEventPool<NuProjectileEntity$IOnHitBlockEvent> onHitBlockEvents;
	private final PriorityEventPool<NuProjectileEntity$IOnTickEvent> onTickEvents;

	public NuProjectileEntity(EntityType<? extends ThrowableProjectile> var1, Level var2) {
		super(var1,var2);
		this.life = 0;
		this.maxLife = 64;
		this.damage = 1F;
		this.gravity = 0F;
		this.canPassThroughEntities = false;
		this.canPassThroughBlocks = false;
		this.parent = (Optional) (((java.util.Optional)(Optional.empty())));
		this.sourceElement = SourceElement.NONE;
		this.sourceHakiNature = SourceHakiNature.UNKNOWN;
		this.sourceType = new HashSet();
		this.ownerUUID = ModValues.NIL_UUID;
		this.entityCollisionBox = this.getDefaultBoundingBox();
		this.blockCollisionBox = this.getDefaultBoundingBox();
		NuProjectileEntity var3 = this;
		Predicate<Entity> var4 = (l0) -> {
			if (((l0) != (this)) && (((l0) != (this.getOwner())) && ((!(l0 instanceof ExperienceOrb)) && (!(l0 instanceof ItemEntity))))){
				if (l0 instanceof LivingEntity){
					return this.hasLineOfSight((Entity)(((LivingEntity)(l0))));
				}else{
					if (l0 instanceof Projectile){
						Projectile lv1_2 = ((Projectile)(l0));
						if (((lv1_2.getOwner()) != (null)) && ((lv1_2.getOwner()) == (this.getOwner()))){
							return false;
						}else{
							return true;
						}
					}else{
						return true;
					}
				}
			}else{
				return false;
			}
		};
		this.canCollideWithEntityPredicate = var4;
		this.targetsCheck = new TargetPredicate().selector(ModEntityPredicates.IS_ALIVE_AND_SURVIVAL.and(this.canCollideWithEntityPredicate));
		this.onHitEntityEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onHitBlockEvents = (PriorityEventPool) (new PriorityEventPool());
		this.onTickEvents = (PriorityEventPool) (new PriorityEventPool());
		this.setMaxLife(64);
	}
	private NuProjectileEntity(EntityType<? extends ThrowableProjectile> var1, Level var2, double var3, double var4, double var5) {
		this(var1,var2);
		this.setPos(var3,var4,var5);
	}
	public NuProjectileEntity(EntityType<? extends ThrowableProjectile> var1, Level var2, LivingEntity var3) {
		this(var1,var2,var3.getX(),(var3.getEyeY()) - (0.10000000149011612D),var3.getZ());
		this.setOwner((Entity)(var3));
		this.damageSource = ModDamageSources.getInstance((LevelAccessor)(var2)).projectile(this,(Entity)(var3));
		this.damageSourceHandler = ((IDamageSourceHandler)(this.damageSource));
	}
	public NuProjectileEntity(EntityType<? extends ThrowableProjectile> var1, Level var2, LivingEntity var3, @Nullable() IAbility var4, SourceElement var5, SourceHakiNature var6, SourceType... var7) {
		this(var1,var2,var3);
		this.setOwner((Entity)(var3));
		this.sourceElement = var5;
		this.sourceHakiNature = var6;
		this.sourceType = Sets.newHashSet(var7);
		this.damageSource = ((var4) != (null)) ? (ModDamageSources.getInstance((LevelAccessor)(var2)).ability((Entity)(this),(Entity)(var3),var4.getCore())) : (ModDamageSources.getInstance((LevelAccessor)(var2)).projectile(this,(Entity)(var3)));
		this.damageSourceHandler = ((IDamageSourceHandler)(this.damageSource));
		this.parent = (Optional) (Optional.ofNullable(var4));
	}
	public NuProjectileEntity(EntityType<? extends ThrowableProjectile> var1, Level var2, LivingEntity var3, IAbility var4) {
		this(var1,var2,var3,var4,var4.getCore().getSourceElement(),var4.getCore().getSourceHakiNature(),var4.getCore().getSourceTypesArray());
	}
	public NuProjectileEntity addEntityHitEvent(int var1, NuProjectileEntity$IOnHitEntityEvent var2) {
		this.onHitEntityEvents.addEvent(var1,var2);
		return this;
	}
	public NuProjectileEntity addBlockHitEvent(int var1, NuProjectileEntity$IOnHitBlockEvent var2) {
		this.onHitBlockEvents.addEvent(var1,var2);
		return this;
	}
	public void clearBlockHitEvents() {
		this.onHitBlockEvents.clearEvents();
	}
	public void clearBlockHitEvents(int var1) {
		this.onHitBlockEvents.clearEvents(var1);
	}
	public NuProjectileEntity addTickEvent(int var1, NuProjectileEntity$IOnTickEvent var2) {
		this.onTickEvents.addEvent(var1,var2);
		return this;
	}
	public boolean canBeCollidedWith() {
		return false;
	}
	private AABB getDefaultBoundingBox() {
		AABB var1 = super.makeBoundingBox();
		if ((var1) != (null)){
			return var1;
		}else{
			return this.getType().getDimensions().makeBoundingBox(this.position());
		}
	}
	protected AABB makeBoundingBox() {
		if ((this.entityCollisionBox) == (null)){
			this.entityCollisionBox = this.getDefaultBoundingBox();
		}
		return new AABB((this.getX()) - ((this.entityCollisionBox.getXsize()) / (2D)),(this.getY()) - ((this.entityCollisionBox.getYsize()) / (2D)),(this.getZ()) - ((this.entityCollisionBox.getZsize()) / (2D)),(this.getX()) + ((this.entityCollisionBox.getXsize()) / (2D)),(this.getY()) + ((this.entityCollisionBox.getYsize()) / (2D)),(this.getZ()) + ((this.entityCollisionBox.getZsize()) / (2D)));
	}
	public void shootFromRotation(Entity var1, float var2, float var3, float var4, float var5, float var6) {
		var6 = Math.max(var6,0F);
		if (((ProjectileShootEvent)(NeoForge.EVENT_BUS.post((Event)(new ProjectileShootEvent(this,var5,var6))))).isCanceled()){
			return;
		}else{
			float var7 = (-(Mth.sin((var3) * (0.017453292F)))) * (Mth.cos((var2) * (0.017453292F)));
			float var8 = -(Mth.sin(((var2) + (var4)) * (0.017453292F)));
			this.shoot((double)(var7),(double)(var8),(double)((Mth.cos((var3) * (0.017453292F))) * (Mth.cos((var2) * (0.017453292F)))),var5,var6);
			return;
		}
	}
	public boolean updateInWaterStateAndDoFluidPushing() {
		if (this.isInWater()){
			this.doWaterSplashEffect();
		}
		return false;
	}
	public boolean tickDespawn() {
		if (!(this.level().isClientSide)){
			if ((this.getMaxLife()) < (0)){
				return false;
			}else{
				if ((this.getLife()) <= (0)){
					this.setLife(this.getMaxLife());
					this.remove(Entity.RemovalReason.DISCARDED);
					return true;
				}else{
					this.setLife((this.getLife()) - (1));
				}
			}
		}
		return false;
	}
	public void tick() {
		super.tick();
		if (!(this.level().isClientSide)){
			if ((!(this.tickDespawn())) && (!(this.shouldRemoveInProtectedArea()))){
				if (((this.damageSource) != (null)) && (((this.damageSourceHandler) != (null)) && (this.level().isAreaLoaded(this.blockPosition(),1)))){
					if (!(this.isAlive())){
						return;
					}else{
						this.checkEntityCollision();
					}
				}else{
					this.discard();
					return;
				}
			}else{
				return;
			}
		}
		this.onTickEvents.dispatch((Consumer)(((Consumer<NuProjectileEntity$IOnTickEvent>)(NuProjectileEntity$IOnTickEvent::onTick))));
	}
	private void checkEntityCollision() {
		AABB var1 = this.makeBoundingBox();
		AABB var2 = var1.expandTowards(this.getDeltaMovement());
		Set var3 = ((Set)(((java.util.stream.Stream)(this.level().getPartEntities().stream().map((Function)(((Function<PartEntity, Entity>)(PartEntity::getParent)))))).collect((Collector)(Collectors.toSet()))));
		Iterator var4 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),this.getOwner(),var2,this.targetsCheck,Entity.class).iterator();
		do{
			if (var4.hasNext()){
				Entity var5 = ((Entity)(var4.next()));
				if (var3.contains(var5)){
					continue;
				}else{
					this.onHitEntity(new EntityHitResult(var5));
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public void onHit(HitResult var1) {
		if (this.level().isClientSide){
			return;
		}else{
			try{
				HitResult.Type var2 = var1.getType();
				if ((var2) == (HitResult.Type.ENTITY)){
					this.onHitEntity(((EntityHitResult)(var1)));
				}else{
					if ((var2) == (HitResult.Type.BLOCK)){
						BlockHitResult var3 = ((BlockHitResult)(var1));
						this.onHitBlock(var3);
						BlockPos var4 = var3.getBlockPos();
						this.level().gameEvent((Holder)(GameEvent.PROJECTILE_LAND),var4,GameEvent.Context.of((Entity)(this),this.level().getBlockState(var4)));
						if (!(this.canPassThroughBlocks)){
							this.remove(Entity.RemovalReason.KILLED);
						}else{
							return;
						}
					}else{
						return;
					}
				}
			}catch(ComponentNotRegisteredException var2_1){
				var2_1.printStackTrace();
				this.remove(Entity.RemovalReason.DISCARDED);
			}
			return;
		}
	}
	public void onProjectileCollision(NuProjectileEntity var1, NuProjectileEntity var2) {
		if ((var1.getOwner() instanceof LivingEntity) && (var2.getOwner() instanceof LivingEntity)){
			float var3 = var1.getDamage();
			float var4 = var2.getDamage();
			int var5 = ((var3) >= (var4)) ? (1) : (0);
			int var6 = ((var4) >= (var3)) ? (1) : (0);
			if ((var5) != (0)){
				var2.discard();
			}
			if ((var6) != (0)){
				var1.discard();
			}
			return;
		}else{
			return;
		}
	}
	public boolean shouldRemoveInProtectedArea() {
		Optional var1 = ProtectedAreasData.get(((ServerLevel)(this.level()))).getProtectedArea((int)(this.getX()),(int)(this.getY()),(int)(this.getZ()));
		if (var1.isPresent()){
			AbilityCore var2 = ((AbilityCore)(this.parent.map((Function)(((Function<IAbility, AbilityCore>)(IAbility::getCore)))).orElse(null)));
			if ((var2) == (null)){
				return false;
			}else{
				if (!(((ProtectedArea)(var1.get())).canUseAbility(var2))){
					this.remove(Entity.RemovalReason.DISCARDED);
					return true;
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	}
	public boolean canHurtInProtectedArea(EntityHitResult var1) {
		Optional var2 = ProtectedAreasData.get(((ServerLevel)(this.level()))).getProtectedArea((int)(this.getX()),(int)(this.getY()),(int)(this.getZ()));
		if (var2.isPresent()){
			if (((var1.getEntity()) != (null)) && (((var1.getEntity() instanceof Player) && (((ProtectedArea)(var2.get())).canHurtPlayers())) || ((!(var1.getEntity() instanceof Player)) && (((ProtectedArea)(var2.get())).canHurtEntities())))){
				return true;
			}else{
				AbilityCore var3 = ((AbilityCore)(this.parent.map((Function)(((Function<IAbility, AbilityCore>)(IAbility::getCore)))).orElse(null)));
				if ((var3) == (null)){
					return false;
				}else{
					if (ServerConfig.isAbilityProtectionWhitelisted(var3)){
						return true;
					}else{
						return false;
					}
				}
			}
		}else{
			return true;
		}
	}
	public void onHitEntity(EntityHitResult var1) {
		if ((this.canHurtInProtectedArea(var1)) && (((this.damageSource) != (null)) && ((this.damageSourceHandler) != (null)))){
			Entity var2 = var1.getEntity();
			if (var2 instanceof NuProjectileEntity){
				this.onProjectileCollision(this,((NuProjectileEntity)(var2)));
			}
			this.triggerEntityHitEvents(var1);
			this.level().gameEvent((Holder)(GameEvent.PROJECTILE_LAND),var1.getLocation(),GameEvent.Context.of((Entity)(this),((BlockState)(null))));
			Entity var3 = var1.getEntity();
			if (var3 instanceof LivingEntity){
				LivingEntity var4 = ((LivingEntity)(var3));
				this.damageSource = (this.parent.isPresent()) ? (ModDamageSources.getInstance((LevelAccessor)(this.level())).ability((Entity)(this),(Entity)(this.getOwner()),((IAbility)(this.parent.get())).getCore())) : (ModDamageSources.getInstance((LevelAccessor)(this.level())).projectile(this,(Entity)(this.getOwner())));
				this.damageSourceHandler = ((IDamageSourceHandler)(this.damageSource));
				var4.hurt(this.damageSource,this.damage);
				if ((this.knockback) > (0)){
					double var5 = Math.max(0D,(1D) - (var4.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
					Vec3 var6 = this.getDeltaMovement().multiply(1D,0D,1D).normalize().scale((((double)(this.knockback)) * (0.6D)) * (var5));
					if ((var6.lengthSqr()) > (0D)){
						var4.push(var6.x,0.1D,var6.z);
					}
				}
			}
			this.defaultOnHitBlock(var1);
			if ((!(this.canPassThroughEntities)) && ((this.tickCount) > (2))){
				this.remove(Entity.RemovalReason.KILLED);
			}
			return;
		}else{
			this.kill();
			return;
		}
	}
	public void defaultOnHitBlock(EntityHitResult var1) {
		Optional var2 = ProtectedAreasData.get(((ServerLevel)(this.level()))).getProtectedArea(this.getBlockX(),this.getBlockY(),this.getBlockZ());
		if ((var2.isPresent()) && (!(((ProtectedArea)(var2.get())).canDestroyBlocks()))){
			return;
		}else{
			Direction var3 = Direction.getNearest(var1.getLocation().x,var1.getLocation().y,var1.getLocation().z);
			this.onHitBlock(new BlockHitResult(var1.getLocation(),var3,var1.getEntity().blockPosition(),false));
			return;
		}
	}
	public void onHitBlock(BlockHitResult var1) {
		Level var2 = this.level();
		if (var2 instanceof ServerLevel){
			ServerLevel var3 = ((ServerLevel)(var2));
			this.triggerBlockHitEvents(var1);
			super.onHitBlock(var1);
			return;
		}else{
			return;
		}
	}
	public void triggerEntityHitEvents(EntityHitResult var1) {
		if ((this.onHitEntityEvents.countEventsInPool()) > (0L)){
			EntityHitResult var2 = var1;
			final EntityHitResult var2_f1 = var2;
			Consumer<NuProjectileEntity$IOnHitEntityEvent> var3 = (l0) -> {
			l0.onHitEntity(var2_f1);
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.onHitEntityEvents)).dispatch((Consumer)(var3));
		}
	}
	public void triggerBlockHitEvents(BlockHitResult var1) {
		if ((this.onHitBlockEvents.countEventsInPool()) > (0L)){
			BlockHitResult var2 = var1;
			final BlockHitResult var2_f1 = var2;
			Consumer<NuProjectileEntity$IOnHitBlockEvent> var3 = (l0) -> {
			l0.onHitBlock(var2_f1);
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.onHitBlockEvents)).dispatch((Consumer)(var3));
		}
	}
	public boolean hasLineOfSight(Entity var1) {
		if ((var1.level()) != (this.level())){
			return false;
		}else{
			Vec3 var2 = new Vec3(this.getX(),this.getEyeY(),this.getZ());
			Vec3 var3 = new Vec3(var1.getX(),var1.getEyeY(),var1.getZ());
			double var4 = this.getEntityCollisionBox().getXsize();
			double var5 = this.getEntityCollisionBox().getYsize();
			double var6 = this.getEntityCollisionBox().getZsize();
			double var7 = Math.sqrt((((var4) * (var4)) + ((var5) * (var5))) + ((var6) * (var6)));
			if ((var3.distanceTo(var2)) > (var7)){
				return false;
			}else{
				return (this.level().clip(new ClipContext(var2,var3,ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,(Entity)(this))).getType()) == (HitResult.Type.MISS);
			}
		}
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		int var2 = ((this.getOwner()) != (null)) ? (1) : (0);
		var1.writeBoolean((var2) != (0));
		if (((this.self) != (null)) && ((var2) != (0))){
			var1.writeInt(this.self.getId());
		}
		var1.writeInt(this.maxLife);
		var1.writeInt(this.sourceHakiNature.ordinal());
		var1.writeInt(this.sourceElement.ordinal());
		var1.writeInt(this.sourceType.size());
		Iterator var3 = this.sourceType.iterator();
		do{
			if (var3.hasNext()){
				var1.writeInt(((SourceType)(var3.next())).ordinal());
				continue;
			}else{
				break;
			}
		} while (true);
		var1.writeDouble(this.entityCollisionBox.getXsize());
		var1.writeDouble(this.entityCollisionBox.getYsize());
		var1.writeDouble(this.entityCollisionBox.getZsize());
		var1.writeDouble(this.blockCollisionBox.getXsize());
		var1.writeDouble(this.blockCollisionBox.getYsize());
		var1.writeDouble(this.blockCollisionBox.getZsize());
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		try{
			if (!(var1.readBoolean())){
				return;
			}else{
				int var2 = var1.readInt();
				Entity var3 = this.level().getEntity(var2);
				if (var3 instanceof LivingEntity){
					LivingEntity var4 = ((LivingEntity)(var3));
					this.self = var4;
					this.setOwner((Entity)(var4));
				}
				this.setMaxLife(var1.readInt());
				this.sourceHakiNature = SourceHakiNature.values()[var1.readInt()];
				this.sourceElement = SourceElement.values()[var1.readInt()];
				int var4_1 = var1.readInt();
				this.sourceType = new HashSet();
				int var5 = 0;
				do{
					if ((var5) < (var4_1)){
						this.sourceType.add(SourceType.values()[var1.readInt()]);
						var5++;
						continue;
					}else{
						break;
					}
				} while (true);
				this.setEntityCollisionSize(var1.readDouble(),var1.readDouble(),var1.readDouble());
				this.setBlockCollisionSize(var1.readDouble(),var1.readDouble(),var1.readDouble());
			}
		}catch(Exception var2_1){
			var2_1.printStackTrace();
		}
	}
	public void setOwner(@Nullable() Entity var1) {
		super.setOwner(var1);
		if ((var1) != (null)){
			this.ownerUUID = var1.getUUID();
		}
	}
	@Nullable()
	public @Nullable() LivingEntity getOwner() {
		if (((this.ownerUUID) != (null)) && (((this.self) == (null)) && ((this.ownerUUID) != (null)))){
			Level var1 = this.level();
			if (var1 instanceof ServerLevel){
				Entity var2 = ((ServerLevel)(var1)).getEntity(this.ownerUUID);
				if (var2 instanceof LivingEntity){
					this.self = ((LivingEntity)(var2));
				}
			}
		}
		return ((LivingEntity)(super.getOwner()));
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
	public boolean ignoreExplosion(Explosion var1) {
		return true;
	}
	public double getDefaultGravity() {
		return (double)(this.gravity);
	}
	public void setGravity(float var1) {
		this.gravity = var1;
	}
	public int getLife() {
		return this.life;
	}
	public void setLife(int var1) {
		this.life = var1;
	}
	public int getMaxLife() {
		return this.maxLife;
	}
	public void setMaxLife(int var1) {
		this.maxLife = var1;
		this.setLife(this.maxLife);
	}
	public boolean isPhysical() {
		return this.sourceType.contains(SourceType.PHYSICAL);
	}
	public void setPhysical() {
		if (this.sourceType.contains(SourceType.PHYSICAL)){
			return;
		}else{
			this.sourceType.add(SourceType.PHYSICAL);
			this.sourceType.remove(SourceType.UNKNOWN);
			return;
		}
	}
	public void setFist() {
		if (this.sourceType.contains(SourceType.FIST)){
			return;
		}else{
			this.sourceType.add(SourceType.FIST);
			this.sourceType.remove(SourceType.UNKNOWN);
			return;
		}
	}
	public void setProjectile() {
		if (this.sourceType.contains(SourceType.PROJECTILE)){
			return;
		}else{
			this.sourceType.add(SourceType.PROJECTILE);
			this.sourceType.remove(SourceType.UNKNOWN);
			return;
		}
	}
	public void setDamage(float var1) {
		this.damage = var1;
	}
	public void setArmorPiercing(float var1) {
		if (this.level().isClientSide){
			return;
		}else{
			this.damageSourceHandler.addAbilityPiercing(var1,((AbilityCore)(this.getParent().map((Function)(((Function<IAbility, AbilityCore>)(IAbility::getCore)))).orElse(null))));
			return;
		}
	}
	public float getDamage() {
		return this.damage;
	}
	public void setKnockback(int var1) {
		this.knockback = var1;
	}
	public DamageSource getDamageSource() {
		return this.damageSource;
	}
	public SourceElement getSourceElement() {
		return this.sourceElement;
	}
	public SourceHakiNature getSourceHakiNature() {
		return this.sourceHakiNature;
	}
	public Set<SourceType> getSourceTypes() {
		return this.sourceType;
	}
	public void setPassThroughEntities() {
		this.setPassThroughEntities(true);
	}
	public void setPassThroughEntities(boolean var1) {
		this.canPassThroughEntities = var1;
	}
	public boolean canPassThroughEntities() {
		return this.canPassThroughEntities;
	}
	public void setPassThroughBlocks() {
		this.setPassThroughBlocks(true);
	}
	public void setPassThroughBlocks(boolean var1) {
		this.canPassThroughBlocks = var1;
	}
	public boolean canPassThroughBlocks() {
		return this.canPassThroughBlocks;
	}
	public AABB getBlockCollisionBox() {
		return this.blockCollisionBox;
	}
	public AABB getEntityCollisionBox() {
		return this.entityCollisionBox;
	}
	public void setEntityCollisionSize(double var1) {
		this.setEntityCollisionSize(var1,var1,var1);
	}
	public void setEntityCollisionSize(double var1, double var2, double var3) {
		this.entityCollisionBox = new AABB((-(var1)) / (2D),(-(var2)) / (2D),(-(var3)) / (2D),(var1) / (2D),(var2) / (2D),(var3) / (2D));
	}
	public void setEntityCollisionSizeAndUpdate(double var1) {
		this.setEntityCollisionSizeAndUpdate(var1,var1,var1);
	}
	public void setEntityCollisionSizeAndUpdate(double var1, double var2, double var3) {
		this.setEntityCollisionSize(var1,var2,var3);
		if ((!(this.level().isClientSide)) && ((this.getOwner()) != (null))){
			ModNetwork.sendToAllTracking(new SUpdateProjEntityCollisionBox(this.getId(),this.entityCollisionBox.getXsize(),this.entityCollisionBox.getYsize(),this.entityCollisionBox.getZsize()),(Entity)(this));
		}
	}
	public void setBlockCollisionSize(double var1) {
		this.setBlockCollisionSize(var1,var1,var1);
	}
	public void setBlockCollisionSize(double var1, double var2, double var3) {
		this.blockCollisionBox = new AABB((-(var1)) / (2D),(-(var2)) / (2D),(-(var3)) / (2D),(var1) / (2D),(var2) / (2D),(var3) / (2D));
	}
	public void setBlockCollisionSizeAndUpdate(double var1) {
		this.setBlockCollisionSizeAndUpdate(var1,var1,var1);
	}
	public void setBlockCollisionSizeAndUpdate(double var1, double var2, double var3) {
		this.setBlockCollisionSize(var1,var2,var3);
		if ((!(this.level().isClientSide)) && ((this.getOwner()) != (null))){
			ModNetwork.sendToAllTracking(new SUpdateProjBlockCollisionBox(super.getId(),this.blockCollisionBox.getXsize(),this.blockCollisionBox.getYsize(),this.blockCollisionBox.getZsize()),(Entity)(this));
		}
	}
	public boolean isAffectedByHardening() {
		return ((this.getHakiNature()) == (SourceHakiNature.HARDENING)) || ((this.getHakiNature()) == (SourceHakiNature.SPECIAL));
	}
	public boolean isAffectedByImbuing() {
		return ((this.getHakiNature()) == (SourceHakiNature.IMBUING)) || ((this.getHakiNature()) == (SourceHakiNature.SPECIAL));
	}
	public SourceHakiNature getHakiNature() {
		return this.sourceHakiNature;
	}
	public void setUnavoidable() {
		this.damageSourceHandler.setUnavoidable();
	}
	public boolean requiresCompiledRenderChunk() {
		return false;
	}
	public Optional<IAbility> getParent() {
		return this.parent;
	}
}