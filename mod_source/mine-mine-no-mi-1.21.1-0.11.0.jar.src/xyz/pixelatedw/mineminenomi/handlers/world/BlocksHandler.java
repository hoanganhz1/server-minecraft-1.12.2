package xyz.pixelatedw.mineminenomi.handlers.world;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.pixelatedw.mineminenomi.abilities.LogiaBlockBypassingAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;

public class BlocksHandler {
	public static boolean tryBlockSuffocationForLogia(LivingEntity var0, DamageSource var1) {
		LivingEntity var6 = null;
		AABB var7 = null;
		if (!(var1.is(DamageTypes.IN_WALL))){
			return false;
		}else{
			Function var2 = (Function) ((Function<AbilityData, Set>) ((l0) -> {
			Class lv1_1 = LogiaBlockBypassingAbility.class;
			Objects.requireNonNull(lv1_1);
			return l0.getPassiveAbilities((Predicate)(((Predicate<IAbility>)(lv1_1::isInstance))));
		}));
			Set var3 = ((Set)(((java.util.Optional)(AbilityData.get(var0).map(var2))).orElse((Object)(Set.of()))));
			if ((var3.size()) <= (0)){
				return false;
			}else{
				float var4 = (var0.getDimensions(Pose.STANDING).width()) * (0.8F);
				AABB var5 = AABB.ofSize(var0.getEyePosition(),(double)(var4),1e-06D,(double)(var4));
				var6 = var0;
				var7 = var5;
				final LivingEntity var6_f1 = var6;
				final AABB var7_f2 = var7;
				Function<BlockPos, BlockState> var8 = (l0) -> {
			BlockState lv2_3 = var6_f1.level().getBlockState(l0);
			return ((((!(lv2_3.isAir())) && ((lv2_3.isSuffocating((BlockGetter)(var6_f1.level()),l0)) && (Shapes.joinIsNotEmpty(lv2_3.getCollisionShape((BlockGetter)(var6_f1.level()),l0).move((double)(l0.getX()),(double)(l0.getY()),(double)(l0.getZ())),Shapes.create(var7_f2),BooleanOp.AND)))) ? (1) : (0)) != (0)) ? (lv2_3) : (Blocks.AIR.defaultBlockState());
		};
				Predicate<BlockState> var9 = (l0) -> {
			return !(l0.isAir());
		};
				BlockState var10 = ((BlockState)(((java.util.Optional)(((java.util.stream.Stream)(((java.util.stream.Stream)(BlockPos.betweenClosedStream(var5))).map((Function)(var8)))).filter((Predicate)(var9)).findFirst())).orElse((Object)(Blocks.AIR.defaultBlockState()))));
				if (var10.isAir()){
					return false;
				}else{
					Iterator var11 = var3.iterator();
					do{
						if (var11.hasNext()){
							IAbility var12 = ((IAbility)(var11.next()));
							if ((var12 instanceof LogiaBlockBypassingAbility) && (((LogiaBlockBypassingAbility)(var12)).canGoThrough(var10))){
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
			}
		}
	}
}