package xyz.pixelatedw.mineminenomi.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.items.VivreCardItem;

public class VivreCardEntity extends Entity {
	// Fields
	private @Nullable() LivingEntity owner;
	private UUID ownerUUID;
	private final double speedLimit = 0.001D;

	public VivreCardEntity(EntityType<? extends VivreCardEntity> var1, Level var2) {
		super(var1,var2);
	}
	public VivreCardEntity(Level var1) {
		this(((EntityType)(ModEntities.VIVRE_CARD.get())),var1);
	}
	public void setOwner(UUID var1) {
		this.ownerUUID = var1;
		ServerLevel var2 = ((ServerLevel)(this.level()));
		this.owner = ((LivingEntity)(var2.getEntity(this.ownerUUID)));
	}
	public void tick() {
		super.tick();
		if (this.level().isClientSide){
			return;
		}else{
			if (!(this.onGround())){
				this.move(MoverType.SELF,new Vec3(0D,-0.1D,0D));
			}
			if (((this.ownerUUID) != (null)) && (!(this.isOnFire()))){
				if ((this.owner) == (null)){
					return;
				}else{
					if ((this.owner.getHealth()) <= (0F)){
						WyHelper.spawnParticles((ParticleOptions)(ParticleTypes.FLAME),((ServerLevel)(this.level())),this.getX(),(this.getY()) + (0.3D),this.getZ());
						this.remove(Entity.RemovalReason.DISCARDED);
					}
					double var1 = (this.getX()) - (this.owner.getX());
					double var2 = (this.getZ()) - (this.owner.getZ());
					if ((var1) > (0D)){
						Objects.requireNonNull(this);
						if ((var1) > (0.001D)){
							Objects.requireNonNull(this);
							var1 = 0.001D;
						}
					}else{
						if ((var1) < (0D)){
							Objects.requireNonNull(this);
							if ((var1) < (-(0.001D))){
								Objects.requireNonNull(this);
								var1 = -(0.001D);
							}
						}
					}
					if ((var2) > (0D)){
						Objects.requireNonNull(this);
						if ((var2) > (0.001D)){
							Objects.requireNonNull(this);
							var2 = 0.001D;
						}
					}else{
						if ((var2) < (0D)){
							Objects.requireNonNull(this);
							if ((var2) < (-(0.001D))){
								Objects.requireNonNull(this);
								var2 = -(0.001D);
							}
						}
					}
					this.move(MoverType.SELF,new Vec3(-(var1),0D,-(var2)));
					this.lookAt(EntityAnchorArgument.Anchor.EYES,this.owner.position());
					if ((this.tickCount) > (40)){
						double var3 = 0.5D;
						AABB var4 = new AABB(this.blockPosition()).inflate(var3,var3,var3);
						ArrayList var5 = new ArrayList();
						var5.addAll((Collection)(((net.minecraft.world.level.Level)(this.level())).getEntitiesOfClass((Class)(Player.class),(AABB)(var4),(Predicate)(EntitySelector.NO_SPECTATORS.and(EntitySelector.ENTITY_STILL_ALIVE)))));
						Iterator var6 = var5.iterator();
						if (var6.hasNext()){
							Player var7 = ((Player)(var6.next()));
							ItemStack var8 = new ItemStack(((ItemLike)(ModItems.VIVRE_CARD.get())));
							((VivreCardItem)(var8.getItem())).setOwner(var8,this.owner);
							var7.addItem(var8);
							this.remove(Entity.RemovalReason.DISCARDED);
						}else{
							return;
						}
					}
					return;
				}
			}else{
				this.remove(Entity.RemovalReason.DISCARDED);
				return;
			}
		}
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
		var1.putString("OwnerUUID",this.ownerUUID.toString());
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
		if (var1.contains("OwnerUUID",8)){
			this.setOwner(UUID.fromString(var1.getString("OwnerUUID")));
		}
	}
}