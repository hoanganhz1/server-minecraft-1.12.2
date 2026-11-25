package xyz.pixelatedw.mineminenomi.api.helpers;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public class TargetHelper {
	public static <T extends Entity> List<T> getEntitiesAroundCircle(LivingEntity var0, Vec3 var1, double var2, double var3, @Nullable() TargetPredicate var4, Class<? extends T>... var5) {
		AABB var6 = new AABB(var1,var1.add(0D,1D,0D)).inflate(var2,var2,var2);
		ArrayList var7 = new ArrayList();
		Class<? extends T>[] var8 = var5;
		int var9 = var8.length;
		int var10 = 0;
		LOOP_1:
		do{
			if ((var10) < (var9)){
				Class<? extends T> var11 = var8[var10];
				Iterator var12 = var0.level().getEntitiesOfClass(var11,var6).iterator();
				do{
					if (var12.hasNext()){
						Entity var13 = ((Entity)(var12.next()));
						double var14 = var13.distanceToSqr(var1);
						double var15 = ((var2) - (var3)) * ((var2) - (var3));
						if ((((var4) != (null)) && (var4.test(var0,var13))) || (!((var14) >= (var15)))){
							continue;
						}else{
							var7.add(var13);
							continue;
						}
					}else{
						break;
					}
				} while (true);
				var10++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
		return var7;
	}
	public static boolean isEntityInView(Entity var0, Entity var1, float var2) {
		double var3 = (var1.getX()) - (var0.getX());
		double var4 = (var1.getZ()) - (var0.getZ());
		do{
			if ((((var3) * (var3)) + ((var4) * (var4))) < (0.0001D)){
				var3 = ((Math.random()) - (Math.random())) * (0.01D);
				var4 = ((Math.random()) - (Math.random())) * (0.01D);
				continue;
			}else{
				break;
			}
		} while (true);
		float var5 = (((float)(((Math.atan2(var4,var3)) * (180D)) / (3.141592653589793D))) - (var0.getYRot())) - (90F);
		do{
			if ((var5) < (-180F)){
				var5 = (var5) + (360F);
				continue;
			}else{
				break;
			}
		} while (true);
		do{
			if ((var5) >= (180F)){
				var5 = (var5) - (360F);
				continue;
			}else{
				break;
			}
		} while (true);
		return ((var5) < (var2)) && ((var5) > (-(var2)));
	}
	public static <T extends Player> List<T> getNearbyPlayers(LevelAccessor var0, Vec3 var1, double var2, @Nullable() java.util.function.Predicate<Entity> var3) {
		if ((var3) == (null)){
			var3 = Predicates.alwaysTrue();
		}
		java.util.function.Predicate var4 = ModEntityPredicates.IS_ALIVE_AND_SURVIVAL.and(var3);
		Vec3 var5 = var1;
		double var6 = var2;
		final Vec3 var5_f1 = var5;
		final double var6_f2 = var6;
		java.util.function.Predicate<Player> var7 = (l0) -> {
			double lv1_3 = l0.distanceToSqr(var5_f1.x,var5_f1.y,var5_f1.z);
			double lv1_4 = (var6_f2) * (var6_f2);
			return ((lv1_4) < (0D)) || ((lv1_3) < (lv1_4));
		};
		return ((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(((java.util.stream.Stream)(var0.players().stream())).filter((java.util.function.Predicate)(var4)))).filter((java.util.function.Predicate)(var7)))).collect((Collector)(Collectors.toList()))));
	}
	public static <T extends Entity> List<T> getEntitiesInArea(LevelAccessor var0, LivingEntity var1, double var2, @Nullable() TargetPredicate var3, Class<T> var4) {
		return (List<T>) (List) (getEntitiesInArea((LevelAccessor)(var0),(LivingEntity)(var1),(BlockPos)(var1.blockPosition()),var2,var2,var2,(TargetPredicate)(var3),(Class)(var4)));
	}
	public static <T extends Entity> List<T> getEntitiesInArea(LevelAccessor var0, @Nullable() LivingEntity var1, Vec3 var2, double var3, @Nullable() TargetPredicate var4, Class<T> var5) {
		return (List<T>) (List) (getEntitiesInArea((LevelAccessor)(var0),(LivingEntity)(var1),(Vec3)(var2),var3,var3,var3,(TargetPredicate)(var4),(Class)(var5)));
	}
	@Deprecated()
	public static <T extends Entity> List<T> getEntitiesInArea(LevelAccessor var0, @Nullable() LivingEntity var1, BlockPos var2, double var3, @Nullable() TargetPredicate var4, Class<T> var5) {
		return (List<T>) (List) (getEntitiesInArea((LevelAccessor)(var0),(LivingEntity)(var1),(BlockPos)(var2),var3,var3,var3,(TargetPredicate)(var4),(Class)(var5)));
	}
	public static <T extends Entity> List<T> getEntitiesInArea(LevelAccessor var0, @Nullable() LivingEntity var1, Vec3 var2, double var3, double var4, double var5, @Nullable() TargetPredicate var6, Class<T> var7) {
		return (List<T>) (List) (getEntitiesInArea((LevelAccessor)(var0),(LivingEntity)(var1),(AABB)(new AABB(var2,var2.add(0D,1D,0D)).inflate(var3,var4,var5)),(TargetPredicate)(var6),(Class)(var7)));
	}
	@Deprecated()
	public static <T extends Entity> List<T> getEntitiesInArea(LevelAccessor var0, @Nullable() LivingEntity var1, BlockPos var2, double var3, double var4, double var5, @Nullable() TargetPredicate var6, Class<T> var7) {
		Vec3 var8 = Vec3.atCenterOf((Vec3i)(var2));
		return (List<T>) (List) (getEntitiesInArea((LevelAccessor)(var0),(LivingEntity)(var1),(AABB)(new AABB(var8,var8.add(0D,1D,0D)).inflate(var3,var4,var5)),(TargetPredicate)(var6),(Class)(var7)));
	}
	public static <T extends Entity> List<T> getEntitiesInArea(LevelAccessor var0, @Nullable() LivingEntity var1, AABB var2, @Nullable() TargetPredicate var3, Class<T> var4) {
		if ((var3) == (null)){
			var3 = TargetPredicate.DEFAULT_AREA_CHECK;
		}
		ArrayList var5 = new ArrayList();
		TargetPredicate var6 = var3;
		List var7 = var5;
		TargetPredicate var8 = var6;
		LivingEntity var9 = var1;
		final List var7_f1 = var7;
		final TargetPredicate var8_f2 = var8;
		final LivingEntity var9_f3 = var9;
		java.util.function.Predicate<Entity> var10 = (l0) -> {
			return (!(var7_f1.contains(l0))) && (var8_f2.test(var9_f3,l0));
		};
		Objects.requireNonNull(var5);
		var0.getEntitiesOfClass(var4,var2).stream().filter(var10).forEach((Consumer)(((Consumer<Entity>)(var5::add))));
		return var5;
	}
	public static <T extends LivingEntity> List<T> getEntitiesInLine(LivingEntity var0, float var1, float var2, @Nullable() TargetPredicate var3, Class<T> var4) {
		return (List<T>) (List) (getEntitiesInLine((LivingEntity)(var0),var1,var2,var2,var2,(TargetPredicate)(var3),(Class)(var4)));
	}
	@Deprecated()
	public static <T extends LivingEntity> List<T> getEntitiesInLine(LivingEntity var0, BlockPos var1, float var2, float var3, @Nullable() TargetPredicate var4, Class<T> var5) {
		return (List<T>) (List) (getEntitiesInLine((LivingEntity)(var0),(BlockPos)(var1),var2,var3,var3,var3,(TargetPredicate)(var4),(Class)(var5)));
	}
	public static <T extends LivingEntity> List<T> getEntitiesInLine(LivingEntity var0, float var1, float var2, float var3, float var4, @Nullable() TargetPredicate var5, Class<T> var6) {
		return (List<T>) (List) (getEntitiesInLine((LivingEntity)(var0),(BlockPos)(var0.blockPosition()),var1,var2,var3,var4,(TargetPredicate)(var5),(Class)(var6)));
	}
	@Deprecated()
	public static <T extends LivingEntity> List<T> getEntitiesInLine(LivingEntity var0, BlockPos var1, float var2, float var3, float var4, float var5, @Nullable() TargetPredicate var6, Class<T> var7) {
		List var17 = null;
		TargetPredicate var18 = null;
		LivingEntity var19 = null;
		if ((var6) == (null)){
			var6 = TargetPredicate.DEFAULT_LINE_CHECK;
		}
		ArrayList var8 = new ArrayList();
		Vec3 var9 = var0.getLookAngle().normalize();
		Vec3 var10 = var0.position().add(0D,(double)(var0.getEyeHeight()),0D);
		double var11 = 0D;
		do{
			if ((var11) < ((double)(var2))){
				var11 = (var11) + (var9.length());
				var10 = var10.add(var9.scale(var9.length()));
				double var12 = ((double)(var3)) / (2D);
				double var13 = ((double)(var4)) / (2D);
				double var14 = ((double)(var5)) / (2D);
				AABB var15 = new AABB((var10.x) - (var12),(var10.y) - (var13),(var10.z) - (var14),(var10.x) + (var12),(var10.y) + (var13),(var10.z) + (var14));
				TargetPredicate var16 = var6;
				var17 = var8;
				var18 = var16;
				var19 = var0;
				final List var17_f1 = var17;
				final TargetPredicate var18_f2 = var18;
				final LivingEntity var19_f3 = var19;
				java.util.function.Predicate<LivingEntity> var20 = (l0) -> {
			return (!(var17_f1.contains(l0))) && (var18_f2.test(var19_f3,(Entity)(l0)));
		};
				Objects.requireNonNull(var8);
				var0.level().getEntitiesOfClass(var7,var15).stream().filter(var20).forEach((Consumer)(((Consumer<LivingEntity>)(var8::add))));
				continue;
			}else{
				break;
			}
		} while (true);
		((java.util.List)(var8)).sort((Comparator)(closestComparator(var0.position().add(0D,(double)(var0.getEyeHeight()),0D))));
		return var8;
	}
	public static Comparator<Entity> closestComparator(Vec3 var0) {
		Vec3 var1 = var0;
		final Vec3 var1_f1 = var1;
		Comparator<Entity> var2 = (l0, l1) -> {
			return (int)((l0.distanceToSqr(var1_f1)) - (l1.distanceToSqr(var1_f1)));
		};
		return var2;
	}
}