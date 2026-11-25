package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import xyz.pixelatedw.mineminenomi.abilities.jiki.JikiHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class GenocideRaidEffectEntity extends Entity implements IEntityWithComplexSpawn {
	// Fields
	private LivingEntity owner;
	private LivingEntity target;
	private List<ItemStack> magneticItems;

	public GenocideRaidEffectEntity(EntityType<? extends GenocideRaidEffectEntity> var1, Level var2) {
		super(var1,var2);
		this.magneticItems = new ArrayList();
		this.setNoGravity(true);
	}
	public GenocideRaidEffectEntity(Level var1) {
		super(((EntityType)(ModProjectiles.GENOCIDE_RAID_EFFECT.get())),var1);
		this.magneticItems = new ArrayList();
		this.setNoGravity(true);
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
	public void tick() {
		if (!(this.level().isClientSide)){
			if ((this.tickCount) > (100)){
				this.kill();
				return;
			}else{
				if ((this.owner) == (null)){
					this.discard();
					return;
				}else{
					if (((this.target) != (null)) && (this.target.isAlive())){
						this.teleportTo(this.target.getX(),(this.target.getY()) + (1D),this.target.getZ());
					}else{
						this.kill();
						return;
					}
				}
			}
		}
		super.tick();
	}
	public void kill() {
		if ((this.owner) != (null)){
			ItemStack var1 = ((ItemStack)(this.magneticItems.get(0)));
			ItemsHelper.itemBreakParticles(this.level(),this.position(),20,var1);
			this.level().playSound((Player)(null),this.blockPosition(),SoundEvents.ITEM_BREAK,this.getSoundSource(),0.5F,1F);
			JikiHelper.dropComponentItems(this.level(),this.position(),this.magneticItems);
		}
		super.kill();
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
	}
	public void setOwner(LivingEntity var1) {
		this.owner = var1;
	}
	public void setTarget(LivingEntity var1) {
		this.target = var1;
	}
	public LivingEntity getTarget() {
		return this.target;
	}
	public LivingEntity getOwner() {
		return this.owner;
	}
	public void setItemsUsed(List<ItemStack> var1) {
		Collections.shuffle(var1);
		this.magneticItems = var1;
	}
	public List<ItemStack> getItemsUsed() {
		return this.magneticItems;
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		var1.writeInt(((this.target) != (null)) ? (this.target.getId()) : (-1));
		var1.writeInt(this.magneticItems.size());
		Iterator var2 = this.magneticItems.iterator();
		do{
			if (var2.hasNext()){
				ItemStack var3 = ((ItemStack)(var2.next()));
				ItemStack.STREAM_CODEC.encode(var1,var3);
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		int var2 = var1.readInt();
		if ((var2) >= (0)){
			Entity var3 = this.level().getEntity(var2);
			if (((var3) != (null)) && (var3 instanceof LivingEntity)){
				this.target = ((LivingEntity)(var3));
			}
		}
		int var3_1 = var1.readInt();
		this.magneticItems.clear();
		int var4 = 0;
		do{
			if ((var4) < (var3_1)){
				ItemStack var5 = ((ItemStack)(ItemStack.STREAM_CODEC.decode(var1)));
				this.magneticItems.add(var5);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}