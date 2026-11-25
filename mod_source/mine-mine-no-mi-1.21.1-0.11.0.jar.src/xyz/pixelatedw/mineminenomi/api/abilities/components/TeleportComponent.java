package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class TeleportComponent extends AbilityComponent<IAbility> {
	// Fields
	private final PriorityEventPool<TeleportComponent$ITeleportEvent> teleportEvents;
	private boolean stopIfHit;
	private boolean blockYGain;
	private BlockPos location;
	private float teleportDistance;

	public TeleportComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.TELEPORT.get())),var1);
		this.teleportEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public TeleportComponent setStopIfHit(boolean var1) {
		this.stopIfHit = var1;
		return this;
	}
	public TeleportComponent setBlockYGain(boolean var1) {
		this.blockYGain = true;
		return this;
	}
	public BlockHitResult getTeleportHit(LivingEntity var1, float var2, boolean var3) {
		Vec3 var4 = var1.position().add(0D,(double)(var1.getEyeHeight()),0D);
		Vec3 var5 = var4.add(var1.getLookAngle().scale((double)(var2)));
		if (var1 instanceof Mob){
			Mob var6 = ((Mob)(var1));
			if (((var6.getTarget()) != (null)) && ((var6.getTarget().isAlive()) && ((var1.distanceTo((Entity)(var6.getTarget()))) < (var2)))){
				var5 = var6.getTarget().position();
			}
		}
		BlockHitResult var6_1 = var1.level().clip(new ClipContext(var4,var5,ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,(Entity)(var1)));
		if ((var3) && ((var6_1.getType()) != (HitResult.Type.MISS))){
			BlockPos var7 = (var6_1.getDirection().equals(Direction.DOWN)) ? (var6_1.getBlockPos().below(2)) : ((BlockPos)(var6_1.getBlockPos().offset(var6_1.getDirection().getNormal())));
			if ((var7.getY()) > (var1.level().getMaxBuildHeight())){
				var7 = var7.offset(0,(var1.level().getMaxBuildHeight()) - (var7.getY()),0);
			}
			Direction var8 = Direction.getNearest((float)(var7.getX()),(float)(var7.getY()),(float)(var7.getZ()));
			var6_1 = new BlockHitResult(Vec3.atCenterOf((Vec3i)(var7)),var8,var7,false);
		}
		return var6_1;
	}
	@Nullable()
	public BlockPos calculateDestination(LivingEntity var1, float var2) {
		return this.calculateDestination(var1,var2,(var2) / (2F));
	}
	@Nullable()
	public BlockPos calculateDestination(LivingEntity var1, float var2, float var3) {
		this.ensureIsRegistered();
		if (var1.level().isClientSide){
			return null;
		}else{
			this.location = var1.blockPosition();
			if (!(var1.isAlive())){
				return null;
			}else{
				BlockHitResult var4 = this.getTeleportHit(var1,var2,false);
				int var5 = ((var4.getType()) == (HitResult.Type.MISS)) ? (1) : (0);
				double var7 = 0.0;
				if (this.stopIfHit){
					Vec3 var6 = var1.position();
					var7 = 0D;
					do{
						if ((var7) < (1D)){
							double var8 = Mth.lerp(var7,var6.x(),(double)(var4.getBlockPos().getX()));
							double var9 = Mth.lerp(var7,var6.y(),(double)(var4.getBlockPos().getY()));
							BlockPos var10 = BlockPos.containing(var8,var9,Mth.lerp(var7,var6.z(),(double)(var4.getBlockPos().getZ())));
							List var11 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,var10,(double)(var1.getBbWidth()),(double)(var1.getBbHeight()),(double)(var1.getBbWidth()),TargetPredicate.EVERYTHING,Projectile.class);
							if (!(var11.isEmpty())){
								((java.util.List)(var11)).sort((Comparator)(TargetHelper.closestComparator(var6)));
								var3 = Mth.sqrt((float)(((Projectile)(var11.get(0))).distanceToSqr(var6)));
								break;
							}else{
								var7 = (var7) + (0.13D);
								continue;
							}
						}else{
							break;
						}
					} while (true);
				}
				BlockPos var6_1 = null;
				if ((var5) != (0)){
					this.teleportDistance = var3;
					var6_1 = this.getTeleportHit(var1,var3,true).getBlockPos();
				}else{
					this.teleportDistance = var2;
					var6_1 = WyHelper.getClearPositionForPlayer(var1,var4.getBlockPos());
					if ((var6_1) == (null)){
						var6_1 = this.getTeleportHit(var1,var3,true).getBlockPos();
					}
				}
				if ((var6_1) == (null)){
					return null;
				}else{
					if (this.blockYGain){
						double var7_1 = AbilityHelper.getDifferenceToFloor((Entity)(var1));
						if (((var7_1) > (1D)) && (((double)(var6_1.getY())) > (var1.getY()))){
							var6_1 = var6_1.atY((int)(var1.getY()));
						}
					}
					this.location = var6_1;
					return var6_1;
				}
			}
		}
	}
	public boolean teleport(LivingEntity var1, float var2, float var3) {
		this.calculateDestination(var1,var2,var3);
		return this.teleport(var1);
	}
	public boolean teleport(LivingEntity var1) {
		this.ensureIsRegistered();
		if ((this.location) == (null)){
			return false;
		}else{
			var1.stopRiding();
			var1.teleportTo((double)(this.location.getX()),(double)(this.location.getY()),(double)(this.location.getZ()));
			TeleportComponent var2 = this;
			LivingEntity var3 = var1;
			final LivingEntity var3_f1 = var3;
			Consumer<TeleportComponent$ITeleportEvent> var4 = (l0) -> {
			l0.fireTeleportEvent(var3_f1,this.location,this.getAbility());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.teleportEvents)).dispatch((Consumer)(var4));
			return true;
		}
	}
	public BlockPos getTeleportLocation() {
		return this.location;
	}
	public float getTeleportDistance() {
		return this.teleportDistance;
	}
}