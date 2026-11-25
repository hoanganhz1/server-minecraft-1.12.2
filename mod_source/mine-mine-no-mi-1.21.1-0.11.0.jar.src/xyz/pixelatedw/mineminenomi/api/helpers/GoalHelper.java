package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensing;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.IGoalMemoriesEntity;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class GoalHelper {
	// Fields
	private static final TargetingConditions VISIBLE_TARGET_IN_RANGE = TargetingConditions.forCombat().range(16D);
	private static final TargetingConditions ALIVE_TARGET = TargetingConditions.forCombat().ignoreInvisibilityTesting();
	private static final TargetPredicate NEARBY_TARGETS_PREDICATE = new TargetPredicate().testEnemyFaction().testSimpleInView();

	public static boolean canMove(LivingEntity var0) {
		AttributeInstance var1 = var0.getAttribute(ModAttributes.JUMP_HEIGHT);
		if (((var1) != (null)) && ((var1.getValue()) <= (0D))){
			return false;
		}else{
			AttributeInstance var2 = var0.getAttribute(Attributes.MOVEMENT_SPEED);
			if (((var2) != (null)) && ((var2.getValue()) <= (0D))){
				return false;
			}else{
				return true;
			}
		}
	}
	public static boolean shouldMove(Mob var0) {
		if (!(canMove((LivingEntity)(var0)))){
			return false;
		}else{
			if (hasAliveTarget(var0)){
				return true;
			}else{
				if ((var0.getNavigation().getPath()) != (null)){
					return true;
				}else{
					return false;
				}
			}
		}
	}
	@Deprecated()
	public static void startGCD(LivingEntity var0) {
		AbilityData.get(var0).ifPresent((Consumer)(((Consumer<AbilityData>)(AbilityData::startGCD))));
	}
	@Deprecated()
	public static boolean isGCDActive(LivingEntity var0) {
		return ((Boolean)(AbilityData.get(var0).map((Function)(((Function<AbilityData, Boolean>)(AbilityData::isOnGCD)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public static List<LivingEntity> getNearbyVisibleEntities(LivingEntity var0, double var1, @Nullable() Predicate<Entity> var2) {
		TargetPredicate var3 = new TargetPredicate().selector(var2);
		LivingEntity var4 = var0;
		final LivingEntity var4_f1 = var4;
		Predicate<LivingEntity> var5 = (l0) -> {
			return ((l0) != (var4_f1)) && (VISIBLE_TARGET_IN_RANGE.test(var4_f1,l0));
		};
		return ((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(TargetHelper.getEntitiesInArea((LevelAccessor)(var0.level()),var0,var0.blockPosition(),var1,var3,LivingEntity.class).stream())).filter((Predicate)(var5)))).collect((Collector)(Collectors.toList()))));
	}
	public static boolean hasAliveOwner(TamableAnimal var0) {
		LivingEntity var1 = var0.getOwner();
		if (((var1) != (null)) && (var1.isAlive())){
			return true;
		}else{
			return false;
		}
	}
	public static boolean hasAliveTarget(Mob var0) {
		LivingEntity var1 = var0.getTarget();
		if (((var1) != (null)) && (var1.isAlive())){
			return ALIVE_TARGET.test((LivingEntity)(var0),var1);
		}else{
			return false;
		}
	}
	public static boolean isAtSameHeight(LivingEntity var0, LivingEntity var1, double var2) {
		return (Math.abs(var0.position().subtract(var1.position()).y)) < ((1D) + (var2));
	}
	public static boolean isOutsideDistance(LivingEntity var0, LivingEntity var1, double var2) {
		return (var0.distanceToSqr((Entity)(var1))) > ((var2) * (var2));
	}
	public static boolean isWithinDistance(LivingEntity var0, LivingEntity var1, double var2) {
		return (var0.distanceToSqr((Entity)(var1))) <= ((var2) * (var2));
	}
	public static boolean isWithinMeleeAttackRange(LivingEntity var0, LivingEntity var1, float var2) {
		double var3 = var0.distanceToSqr(var1.getX(),var1.getY(),var1.getZ());
		double var4 = (double)(((((var0.getBbWidth()) * (2F)) * (var0.getBbWidth())) * (2F)) + (var1.getBbWidth()));
		var4 = (var4) + ((double)(var2));
		return (var3) <= (var4);
	}
	public static boolean isWithinMeleeAttackRange(LivingEntity var0, LivingEntity var1) {
		double var2 = var0.distanceToSqr(var1.getX(),var1.getY(),var1.getZ());
		return (var2) <= ((double)(((((var0.getBbWidth()) * (2F)) * (var0.getBbWidth())) * (2F)) + (var1.getBbWidth())));
	}
	@Deprecated()
	public static <E extends LivingEntity & IGoalMemoriesEntity> boolean isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(E var0, LivingEntity var1, double var2) {
		Optional var3 = ((IGoalMemoriesEntity)(var0)).getMemory(MemoryModuleType.ATTACK_TARGET);
		if (!(var3.isPresent())){
			return false;
		}else{
			double var4 = var0.distanceToSqr(((LivingEntity)(var3.get())).position());
			return (var0.distanceToSqr(var1.position())) > ((var4) + ((var2) * (var2)));
		}
	}
	public static boolean canSee(Mob var0, Entity var1) {
		return var0.getSensing().hasLineOfSight(var1);
	}
	public static boolean isVisibleFrom(Mob var0, Entity var1, Vec3 var2) {
		Vec3 var3 = new Vec3(var1.getX(),var1.getEyeY(),var1.getZ());
		if (((var1.level()) == (var0.level())) && (!((var3.distanceToSqr(var2)) > (16384D)))){
			return (var0.level().clip(new ClipContext(var2,var3,ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,(Entity)(var0))).getType()) == (HitResult.Type.MISS);
		}else{
			return false;
		}
	}
	public static boolean isEntityInView(LivingEntity var0, Entity var1) {
		return TargetHelper.isEntityInView((Entity)(var0),var1,60F);
	}
	public static LivingEntity getNearestTarget(LivingEntity var0, Optional<LivingEntity> var1, LivingEntity var2) {
		return (!(var1.isPresent())) ? (var2) : (getTargetNearestMe(var0,((LivingEntity)(var1.get())),var2));
	}
	public static LivingEntity getTargetNearestMe(LivingEntity var0, LivingEntity var1, LivingEntity var2) {
		Vec3 var3 = var1.position();
		Vec3 var4 = var2.position();
		return ((var0.distanceToSqr(var3)) < (var0.distanceToSqr(var4))) ? (var1) : (var2);
	}
	public static void lookAt(Mob var0, Vec3 var1) {
		var0.lookAt(EntityAnchorArgument.Anchor.EYES,var1);
	}
	public static void lookAtEachOther(Mob var0, Mob var1) {
		lookAtEntity(var0,(Entity)(var1));
		lookAtEntity(var1,(Entity)(var0));
	}
	public static void lookAtEntity(Mob var0, @Nullable() Entity var1) {
		if ((var1) == (null)){
			return;
		}else{
			var0.lookAt(EntityAnchorArgument.Anchor.EYES,var1.position().add(0D,(double)(var1.getEyeHeight()),0D));
			return;
		}
	}
	public static boolean hasEnoughTargetsAround(Mob var0, int var1, float var2) {
		List var3 = TargetHelper.getEntitiesInArea((LevelAccessor)(var0.level()),(LivingEntity)(var0),(double)(var2),NEARBY_TARGETS_PREDICATE,LivingEntity.class);
		int var4 = 0;
		Iterator var5 = var3.iterator();
		do{
			if (var5.hasNext()){
				LivingEntity var6 = ((LivingEntity)(var5.next()));
				if (((var6) != (null)) && ((var6.isAlive()) && (((var0.distanceToSqr((Entity)(var6))) <= ((double)((var2) * (var2)))) && (canSee(var0,(Entity)(var6)))))){
					var4++;
				}
				if ((var4) >= (var1)){
					return true;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return false;
	}
	public static boolean hasHealthAbovePercentage(LivingEntity var0, double var1) {
		return ((double)(var0.getHealth())) >= (WyHelper.percentage(var1,(double)(var0.getMaxHealth())));
	}
	public static boolean hasHealthUnderPercentage(LivingEntity var0, double var1) {
		return ((double)(var0.getHealth())) < (WyHelper.percentage(var1,(double)(var0.getMaxHealth())));
	}
	public static boolean hasSolidBlockAbove(LivingEntity var0) {
		if (var0 instanceof Mob){
			int var1 = (int)(Math.min(((Mob)(var0)).getTarget().position().subtract(var0.position()).y,5D));
			if ((var1) > (1)){
				int var2 = 1;
				do{
					if ((var2) < (var1)){
						BlockPos var3 = var0.blockPosition().above(var2);
						BlockState var4 = var0.level().getBlockState(var3);
						if ((!(var4.isAir())) && (var4.isFaceSturdy((BlockGetter)(var0.level()),var3,Direction.DOWN))){
							return true;
						}else{
							var2++;
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}else{
				return false;
			}
			return false;
		}else{
			BlockPos var1_1 = var0.blockPosition().above();
			BlockState var2_1 = var0.level().getBlockState(var1_1);
			if ((!(var2_1.isAir())) && (var2_1.isFaceSturdy((BlockGetter)(var0.level()),var1_1,Direction.DOWN))){
				return true;
			}else{
				return false;
			}
		}
	}
	public static boolean hasBlockInFace(LivingEntity var0) {
		Vec3 var1 = var0.position().add(0D,(double)(var0.getEyeHeight()),0D);
		Direction var2 = var0.getDirection();
		Vec3 var3 = var1.add((double)(var2.getStepX()),0D,(double)(var2.getStepZ()));
		BlockHitResult var4 = var0.level().clip(new ClipContext(var1,var3,ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,(Entity)(var0)));
		if (((var4) != (null)) && ((var4.getType()) != (HitResult.Type.MISS))){
			return true;
		}else{
			return false;
		}
	}
	public static int getFrontWallHeight(LivingEntity var0) {
		Vec3 var1 = var0.getEyePosition(1F);
		Vec3 var2 = var1.add(var0.getLookAngle());
		BlockHitResult var3 = var0.level().clip(new ClipContext(var1,var2,ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,(Entity)(var0)));
		if (((var3) != (null)) && ((var3.getType()) != (HitResult.Type.MISS))){
			Direction var4 = var3.getDirection().getOpposite();
			int var5 = 0;
			int var6 = 0;
			do{
				if ((var6) < (4)){
					BlockPos var7 = var3.getBlockPos().above(var6);
					if (var0.level().getBlockState(var7).isFaceSturdy((BlockGetter)(var0.level()),var7,var4)){
						var5++;
					}
					var6++;
					continue;
				}else{
					break;
				}
			} while (true);
			return var5;
		}else{
			return 0;
		}
	}
}