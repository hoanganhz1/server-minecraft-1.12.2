package xyz.pixelatedw.mineminenomi.data.entity;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.events.entity.EntityCarryEvent;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SCarryEntityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SLeashPlayerPacket;

public class CarryData implements INBTSerializable<CompoundTag> {
	// Fields
	private LivingEntity owner;
	private @Nullable() LivingEntity carryTarget;
	private @Nullable() LivingEntity carrier;
	private @Nullable() LivingEntity leashHolder;

	public static CarryData createCapability(IAttachmentHolder var0) {
		return new CarryData(((LivingEntity)(var0)));
	}
	public static Optional<CarryData> get(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.CARRY))){
			var0.setData(ModAttachments.CARRY,new CarryData(var0));
		}
		return (Optional<CarryData>) (Optional) (var0.getExistingData(ModAttachments.CARRY));
	}
	public static CarryData getOrCreate(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.CARRY))){
			var0.setData(ModAttachments.CARRY,new CarryData(var0));
		}
		return ((CarryData)(var0.getData(ModAttachments.CARRY)));
	}
	private CarryData(LivingEntity var1) {
		this.owner = var1;
	}
	public void startCarrying(@Nullable() LivingEntity var1) {
		Level var2 = this.owner.level();
		if ((var2) == (null)){
			return;
		}else{
			if ((var1) == (null)){
				this.stopCarrying();
				return;
			}else{
				if (var1.isPassenger()){
					var1.stopRiding();
				}
				this.carryTarget = var1;
				CarryData var3 = this;
				Consumer<CarryData> var4 = (l0) -> {
			l0.setCarrier(this.owner);
		};
				((java.util.Optional)(get(var1))).ifPresent((Consumer)(var4));
				EntityCarryEvent var5 = new EntityCarryEvent(this.owner,var1,true);
				NeoForge.EVENT_BUS.post((Event)(var5));
				if (!(var2.isClientSide)){
					LivingEntity var6 = this.owner;
					if (var6 instanceof Player){
						Player var7 = ((Player)(var6));
						ModNetwork.sendTo(new SCarryEntityPacket((LivingEntity)(var1)),var7);
					}else{
						return;
					}
				}
				return;
			}
		}
	}
	public void stopCarrying() {
		Level var1 = this.owner.level();
		if ((var1) == (null)){
			return;
		}else{
			if (((EntityCarryEvent)(NeoForge.EVENT_BUS.post((Event)(new EntityCarryEvent(this.owner,this.carryTarget,false))))).isCanceled()){
				return;
			}else{
				if ((this.carryTarget) != (null)){
					Consumer<CarryData> var2 = (l0) -> {
			l0.setCarrier((LivingEntity)(null));
		};
					((java.util.Optional)(get(this.carryTarget))).ifPresent((Consumer)(var2));
				}
				this.carryTarget = null;
				this.owner.removeEffect((Holder)(ModEffects.CARRYING));
				if (!(var1.isClientSide)){
					LivingEntity var2_1 = this.owner;
					if (var2_1 instanceof Player){
						Player var3 = ((Player)(var2_1));
						ModNetwork.sendTo(new SCarryEntityPacket((LivingEntity)(null)),var3);
					}else{
						return;
					}
				}
				return;
			}
		}
	}
	@Nullable()
	public @Nullable() LivingEntity getCarry() {
		return this.carryTarget;
	}
	public boolean isCarrying() {
		return ((this.carryTarget) != (null)) && (this.carryTarget.isAlive());
	}
	public void setCarrier(@Nullable() LivingEntity var1) {
		this.carrier = var1;
	}
	@Nullable()
	public @Nullable() LivingEntity getCarrier() {
		return this.carrier;
	}
	public boolean isCarried() {
		return ((this.carrier) != (null)) && (this.carrier.isAlive());
	}
	public void setLeashedTo(LivingEntity var1) {
		Level var2 = this.owner.level();
		if ((var2) == (null)){
			return;
		}else{
			this.leashHolder = var1;
			if (this.owner.isPassenger()){
				this.owner.stopRiding();
			}
			if ((!(var2.isClientSide)) && (var1 instanceof Player)){
				Player var3 = ((Player)(var1));
				LivingEntity var4 = this.owner;
				if (var4 instanceof Player){
					Player var5 = ((Player)(var4));
					ModNetwork.sendToAllTrackingAndSelf(new SLeashPlayerPacket((Player)(var5),(Player)(var3)),(LivingEntity)(var3));
				}else{
					return;
				}
			}
			return;
		}
	}
	public void dropLeash() {
		Level var1 = this.owner.level();
		if ((var1) == (null)){
			return;
		}else{
			Vec3 var2 = this.leashHolder.position().add(0D,(double)(this.leashHolder.getEyeHeight()),0D);
			this.leashHolder = null;
			ItemEntity var3 = new ItemEntity(this.owner.level(),var2.x,var2.y,var2.z,Items.LEAD.getDefaultInstance());
			var3.setDefaultPickUpDelay();
			this.owner.level().addFreshEntity((Entity)(var3));
			if (!(var1.isClientSide)){
				LivingEntity var4 = this.owner;
				if (var4 instanceof Player){
					Player var5 = ((Player)(var4));
					ModNetwork.sendToAllTrackingAndSelf(new SLeashPlayerPacket((Player)(var5),(Player)(null)),(LivingEntity)(var5));
				}else{
					return;
				}
			}
			return;
		}
	}
	@Nullable()
	public @Nullable() LivingEntity getLeashHolder() {
		return this.leashHolder;
	}
	public boolean isLeashed() {
		return (this.leashHolder) != (null);
	}
	public boolean canBeLeashed(LivingEntity var1) {
		return (this.leashHolder) == (null);
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		return new CompoundTag();
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
	}
}