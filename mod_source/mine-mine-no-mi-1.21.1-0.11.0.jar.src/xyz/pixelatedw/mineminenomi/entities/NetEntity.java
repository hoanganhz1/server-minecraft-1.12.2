package xyz.pixelatedw.mineminenomi.entities;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.enums.NetType;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.effects.CaughtInNetEffect;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.items.NetItem;

public class NetEntity extends ThrowableProjectile implements IEntityWithComplexSpawn {
	// Fields
	private static final int NET_TIME = 1200;
	private Optional<LivingEntity> thrower;
	private NetItem netItem;
	private NetType type;

	public NetEntity(EntityType<? extends NetEntity> var1, Level var2) {
		super(var1,var2);
		this.thrower = (Optional) (((java.util.Optional)(Optional.empty())));
		this.type = NetType.NORMAL;
	}
	private NetEntity(EntityType<? extends NetEntity> var1, Level var2, @Nullable() LivingEntity var3, NetItem var4) {
		super(var1,var2);
		this.thrower = (Optional) (((java.util.Optional)(Optional.empty())));
		this.type = NetType.NORMAL;
		this.thrower = (Optional) (Optional.ofNullable(var3));
		this.netItem = var4;
		this.type = var4.getNetType();
	}
	public static NetEntity createNormalNet(Level var0, @Nullable() LivingEntity var1) {
		return new NetEntity(((EntityType)(ModEntities.ROPE_NET.get())),var0,var1,((NetItem)(ModItems.ROPE_NET.get())));
	}
	public static NetEntity createKairosekiNet(Level var0, @Nullable() LivingEntity var1) {
		return new NetEntity(((EntityType)(ModEntities.KAIROSEKI_NET.get())),var0,var1,((NetItem)(ModItems.KAIROSEKI_NET.get())));
	}
	public static NetEntity createFromItem(Level var0, @Nullable() LivingEntity var1, NetItem var2) {
		switch (NetEntity$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NetType[var2.getNetType().ordinal()]){
		case 1:

		case 2:

		default:
			NetEntity var4 = createNormalNet(var0,var1);
			return var4;
		}
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
	public void tick() {
		super.tick();
		if (!(this.level().isClientSide)){
			if (this.isOnFire()){
				this.remove(Entity.RemovalReason.DISCARDED);
				return;
			}else{
				Vec3 var1 = new Vec3((this.getBoundingBox().getXsize()) / (2D),(this.getBoundingBox().getYsize()) / (2D),(this.getBoundingBox().getZsize()) / (2D));
				TargetPredicate var2 = new TargetPredicate().selector(this.canHitEntity());
				Entity var3 = ((Entity)(((java.util.Optional)(TargetHelper.getEntitiesInArea((LevelAccessor)(this.level()),(LivingEntity)(null),this.blockPosition(),var1.x,var1.y,var1.z,var2,Entity.class).stream().findAny())).orElse((Object)(null))));
				if ((var3) != (null)){
					this.onHitEntity(new EntityHitResult(var3));
				}else{
					return;
				}
			}
		}
	}
	protected void onHitEntity(EntityHitResult var1) {
		if (!(this.isAlive())){
			return;
		}else{
			if ((!(this.level().isClientSide)) && ((this.netItem) != (null))){
				if (var1.getEntity() instanceof LivingEntity){
					LivingEntity var2 = ((LivingEntity)(var1.getEntity()));
					int var3 = 0;
					switch (NetEntity$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NetType[this.getNetType().ordinal()]){
					case 1:
						var3 = (((var2.getHealth()) < (100F)) || (((double)(var2.getHealth())) < (WyHelper.percentage(40D,(double)(var2.getMaxHealth()))))) ? (1) : (0);
						break;
					case 2:
						var3 = (((var2.getHealth()) < (50F)) || (((double)(var2.getHealth())) < (WyHelper.percentage(20D,(double)(var2.getMaxHealth()))))) ? (1) : (0);
						break;
					}
					if ((var3) == (0)){
						this.destroyAndDropItem(this.blockPosition());
						return;
					}else{
						if ((!(CombatHelper.isLogiaBlocking(var2))) && (!(CombatHelper.isShieldBlocking(var2)))){
							Iterator var4 = var2.getActiveEffects().iterator();
							do{
								if (var4.hasNext()){
									if (((MobEffectInstance)(var4.next())).getEffect() instanceof CaughtInNetEffect){
										this.destroyAndDropItem(this.blockPosition());
										return;
									}else{
										continue;
									}
								}else{
									break;
								}
							} while (true);
							var2.addEffect(new MobEffectInstance(this.netItem.getEffect(),1200,0));
							this.remove(Entity.RemovalReason.DISCARDED);
							return;
						}else{
							this.destroyAndDropItem(this.blockPosition());
							return;
						}
					}
				}else{
					Entity var2_1 = var1.getEntity();
					if (var2_1 instanceof NuProjectileEntity){
						NuProjectileEntity var3_1 = ((NuProjectileEntity)(var2_1));
						if ((this.getNetType()) == (NetType.KAIROSEKI)){
							Function<IAbility, Boolean> var4_1 = (l0) -> {
			return Boolean.valueOf((l0.getCore().getCategory()) == (AbilityCategory.DEVIL_FRUITS));
		};
							if (((Boolean)(((java.util.Optional)(var3_1.getParent())).map((Function)(var4_1)).orElse(Boolean.valueOf(false)))).booleanValue()){
								var3_1.remove(Entity.RemovalReason.DISCARDED);
								return;
							}
						}
						if ((var3_1.getDamage()) > (5F)){
							this.remove(Entity.RemovalReason.DISCARDED);
						}else{
							return;
						}
					}
					return;
				}
			}else{
				return;
			}
		}
	}
	protected void onHitBlock(BlockHitResult var1) {
		super.onHitBlock(var1);
		if ((!(this.level().isClientSide)) && ((this.netItem) != (null))){
			this.destroyAndDropItem(var1.getBlockPos());
			return;
		}else{
			return;
		}
	}
	protected boolean canHitEntity(Entity var1) {
		if (var1 instanceof NuProjectileEntity){
			return true;
		}else{
			return super.canHitEntity(var1);
		}
	}
	private Predicate<Entity> canHitEntity() {
		NetEntity var1 = this;
		Predicate<Entity> var2 = (l0) -> {
			if ((l0) == (this)){
				return false;
			}else{
				return true;
			}
		};
		return var2;
	}
	private void destroyAndDropItem(BlockPos var1) {
		int var2 = 1;
		if (this.thrower.isPresent()){
			Object var3 = this.thrower.get();
			if (var3 instanceof Player){
				var2 = (!(((Player)(var3)).getAbilities().instabuild)) ? (1) : (0);
			}
		}
		if ((var2) != (0)){
			ItemEntity var3_1 = new ItemEntity(this.level(),(double)(var1.getX()),(double)((var1.getY()) + (1)),(double)(var1.getZ()),this.netItem.getDefaultInstance());
			this.level().addFreshEntity((Entity)(var3_1));
		}
		this.remove(Entity.RemovalReason.DISCARDED);
	}
	public boolean canBeCollidedWith() {
		return true;
	}
	public double getDefaultGravity() {
		return 0.03D;
	}
	public NetType getNetType() {
		return this.type;
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		var1.writeInt(this.type.ordinal());
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		int var2 = var1.readInt();
		this.type = NetType.values()[var2];
	}
}