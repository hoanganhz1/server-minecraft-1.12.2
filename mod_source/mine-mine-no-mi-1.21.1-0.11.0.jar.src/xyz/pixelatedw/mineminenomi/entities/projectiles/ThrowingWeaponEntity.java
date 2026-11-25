package xyz.pixelatedw.mineminenomi.entities.projectiles;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.event.EventHooks;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class ThrowingWeaponEntity extends NuProjectileEntity {
	// Fields
	private ItemStack itemStack;
	private boolean isStuck;
	private float zRot;

	public ThrowingWeaponEntity(EntityType<? extends ThrowingWeaponEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.itemStack = ItemStack.EMPTY;
	}
	public ThrowingWeaponEntity(Level var1, LivingEntity var2) {
		this(var1,var2,var2.getMainHandItem().copy());
	}
	public ThrowingWeaponEntity(Level var1, LivingEntity var2, ItemStack var3) {
		super(((EntityType)(ModEntities.THROWING_WEAPON.get())),var1,var2,(IAbility)(null),SourceElement.NONE,SourceHakiNature.IMBUING,new SourceType[]{SourceType.BLUNT});
		this.itemStack = ItemStack.EMPTY;
		this.itemStack = var3;
		this.setDamage((ItemsHelper.getItemDamage(this.itemStack)) * (1.2F));
		this.setMaxLife(1200);
		this.setPassThroughEntities();
		this.setPassThroughBlocks();
		this.setEntityCollisionSize(1.5D,1.5D,1.5D);
		this.addBlockHitEvent(100,(NuProjectileEntity$IOnHitBlockEvent)(((xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitBlockEvent)(this::onBlockImpactEvent))));
	}
	public void tick() {
		ThrowingWeaponEntity var1 = this;
		final ThrowingWeaponEntity var1_f1 = var1;
		Predicate<Entity> var2 = (l0) -> {
			return var1_f1.canHitEntity(l0);
		};
		HitResult var3 = ProjectileUtil.getHitResultOnMoveVector((Entity)(this),var2);
		boolean var4 = false;
		if ((var3.getType()) == (HitResult.Type.BLOCK)){
			BlockPos var5 = ((BlockHitResult)(var3)).getBlockPos();
			BlockState var6 = this.level().getBlockState(var5);
			if (var6.is(Blocks.NETHER_PORTAL)){
				this.setAsInsidePortal(((Portal)(var6.getBlock())),var5);
				var4 = true;
			}else{
				if (var6.is(Blocks.END_GATEWAY)){
					if ((this.level().getBlockEntity(var5) instanceof TheEndGatewayBlockEntity) && (this.canUsePortal(true))){
						this.setAsInsidePortal(((Portal)(var6.getBlock())),var5);
					}
					var4 = true;
				}
			}
		}
		if ((var3.getType()) == (HitResult.Type.ENTITY)){
			this.setPos(this.getX(),this.getY(),this.getZ());
			AbilityHelper.setDeltaMovement((Entity)(this),0D,0D,0D);
		}
		if (((var3.getType()) != (HitResult.Type.MISS)) && ((!(var4)) && (!(EventHooks.onProjectileImpact((Projectile)(this),var3))))){
			this.onHit(var3);
		}
		BlockPos var5_1 = this.blockPosition();
		BlockState var6_1 = this.level().getBlockState(var5_1);
		if (!(var6_1.isAir())){
			VoxelShape var7 = var6_1.getCollisionShape((BlockGetter)(this.level()),var5_1);
			if (!(var7.isEmpty())){
				Vec3 var8 = this.position();
				Iterator var9 = var7.toAabbs().iterator();
				do{
					if (var9.hasNext()){
						if (((AABB)(var9.next())).move(var5_1).contains(var8)){
							this.setPos(this.getX(),(this.getY()) + (1D),this.getZ());
							this.isStuck = true;
							break;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}
		}
		if (!(this.isStuck)){
			this.zRot = (float)(((this.tickCount) * (50)) % (360));
			Vec3 var7_1 = this.getDeltaMovement();
			double var8_1 = var7_1.x;
			double var9_1 = var7_1.y;
			double var10 = var7_1.z;
			float var11 = Mth.sqrt((float)(var7_1.horizontalDistanceSqr()));
			double var12 = (this.getX()) + (var8_1);
			double var13 = (this.getY()) + (var9_1);
			double var14 = (this.getZ()) + (var10);
			this.setYRot((float)((Mth.atan2(var8_1,var10)) * (57.2957763671875D)));
			this.setXRot((float)((Mth.atan2(var9_1,(double)(var11))) * (57.2957763671875D)));
			this.setXRot(lerpRotation(this.xRotO,this.getXRot()));
			this.setYRot(lerpRotation(this.yRotO,this.getYRot()));
			AbilityHelper.setDeltaMovement((Entity)(this),var7_1.scale(0.9900000095367432D));
			Vec3 var15 = this.getDeltaMovement();
			AbilityHelper.setDeltaMovement((Entity)(this),var15.x,(var15.y) - (this.getGravity()),var15.z);
			this.setPos(var12,var13,var14);
		}else{
			this.tickDespawn();
			this.zRot = -170F;
			if ((!(this.level().isClientSide)) && (this.level().noCollision(new AABB(this.position(),this.position()).inflate(0.5D)))){
				this.isStuck = false;
				Vec3 var7_2 = this.getDeltaMovement();
				AbilityHelper.setDeltaMovement((Entity)(this),var7_2.multiply((double)((this.random.nextFloat()) * (0.2F)),(double)((this.random.nextFloat()) * (0.2F)),(double)((this.random.nextFloat()) * (0.2F))));
			}
			if (!(this.level().isClientSide)){
				AABB var7_3 = new AABB(this.blockPosition()).inflate(1.5D,1.5D,1.5D);
				Iterator var8_2 = TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),(LivingEntity)(null),var7_3,TargetPredicate.EVERYTHING,LivingEntity.class).iterator();
				do{
					if (var8_2.hasNext()){
						LivingEntity var9_2 = ((LivingEntity)(var8_2.next()));
						if ((((((this.getOwner()) != (null)) && ((this.getOwner().getUUID()) == (var9_2.getUUID()))) ? (1) : (0)) != (0)) && (ItemsHelper.giveItemStackToEntity(var9_2,this.itemStack,EquipmentSlot.MAINHAND))){
							this.discard();
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}else{
				return;
			}
		}
	}
	private void onBlockImpactEvent(BlockHitResult var1) {
		this.isStuck = true;
	}
	public void kill() {
		if (this.isStuck){
			super.kill();
		}else{
			this.isStuck = true;
		}
	}
	public ItemStack getItem() {
		return this.itemStack;
	}
	public double getDefaultGravity() {
		return 0.05000000074505806D;
	}
	public float getRotation() {
		return this.zRot;
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		super.writeSpawnData(var1);
		ItemStack.STREAM_CODEC.encode(var1,this.itemStack);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		super.readSpawnData(var1);
		this.itemStack = ((ItemStack)(ItemStack.STREAM_CODEC.decode(var1)));
	}
}