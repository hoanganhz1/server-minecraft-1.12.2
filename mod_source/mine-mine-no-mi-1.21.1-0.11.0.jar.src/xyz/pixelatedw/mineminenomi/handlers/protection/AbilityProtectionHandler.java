package xyz.pixelatedw.mineminenomi.handlers.protection;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea$RestorationEntry;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class AbilityProtectionHandler {
	public static boolean shouldMobSpawnInProtectionArea(Mob var0) {
		MobSpawnType var1 = var0.getSpawnType();
		if (((var1) != (MobSpawnType.COMMAND)) && ((var1) != (MobSpawnType.SPAWN_EGG))){
			ProtectedAreasData var2 = ProtectedAreasData.get(((ServerLevel)(var0.level())));
			if ((var2) != (null)){
				Optional var3 = var2.getProtectedArea((int)(var0.getX()),(int)(var0.getY()),(int)(var0.getZ()));
				if (var3.isPresent()){
					return ((ProtectedArea)(var3.get())).canMobsSpawn();
				}else{
					return true;
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	public static boolean shouldHurtEntityInProtectionArea(LivingEntity var0, DamageSource var1) {
		ProtectedAreasData var2 = ProtectedAreasData.get(((ServerLevel)(var0.level())));
		if ((var2) != (null)){
			Optional var3 = var2.getProtectedArea((int)(var0.getX()),(int)(var0.getY()),(int)(var0.getZ()));
			if (var3.isPresent()){
				if (var0 instanceof Player){
					return ((ProtectedArea)(var3.get())).canHurtPlayers();
				}else{
					return ((ProtectedArea)(var3.get())).canHurtEntities();
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	public static boolean shouldLoseStatsInProtectionArea(LivingEntity var0) {
		Level var1 = var0.level();
		if (var1 instanceof ServerLevel){
			ProtectedAreasData var2 = ProtectedAreasData.get(((ServerLevel)(var1)));
			BlockPos var3 = var0.blockPosition();
			Optional var4 = var2.getProtectedArea(var3.getX(),var3.getY(),var3.getZ());
			if ((var4.isPresent()) && (!(((ProtectedArea)(var4.get())).canLoseStats()))){
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	public static boolean shouldDieInProtectedArea(LivingEntity var0) {
		Level var1 = var0.level();
		if (var1 instanceof ServerLevel){
			ProtectedAreasData var2 = ProtectedAreasData.get(((ServerLevel)(var1)));
			if ((var2) != (null)){
				Optional var3 = var2.getProtectedArea(var0.blockPosition().getX(),var0.blockPosition().getY(),var0.blockPosition().getZ());
				if ((var3.isPresent()) && (!(((ProtectedArea)(var3.get())).canDie()))){
					var0.addEffect(new MobEffectInstance((Holder)(ModEffects.UNCONSCIOUS),((ProtectedArea)(var3.get())).getUnconsciousTime(),0,false,false));
					var0.setHealth(5F);
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	public static final void handleExplosions(ServerLevel var0, Explosion var1, List<BlockPos> var2, List<Entity> var3) {
		ServerLevel var11 = null;
		ServerLevel var7 = null;
		Optional var8 = null;
		ProtectedAreasData var4 = ProtectedAreasData.get(var0);
		Vec3 var5 = var1.center();
		Optional var6 = var4.getProtectedArea((int)(var5.x),(int)(var5.y),(int)(var5.z));
		if (var6.isPresent()){
			if (!(((ProtectedArea)(var6.get())).canDestroyBlocks())){
				var2.clear();
			}else{
				if (((ProtectedArea)(var6.get())).canRestoreBlocks()){
					var7 = var0;
					var8 = var6;
					final ServerLevel var7_f1 = var7;
					final Optional var8_f2 = var8;
					Predicate<BlockPos> var9 = (l0) -> {
			return (!(var7_f1.getBlockState(l0).isAir())) && (((ProtectedArea)(var8_f2.get())).isInside((Level)(var7_f1),l0.getX(),l0.getY(),l0.getZ()));
		};
					Function<BlockPos, BlockPos> var10 = (l0) -> {
			return l0;
		};
					var11 = var0;
					final ServerLevel var11_f3 = var11;
					Function<BlockPos, ProtectedArea$RestorationEntry> var12 = (l0) -> {
			return new ProtectedArea$RestorationEntry((Level)(var11_f3),(BlockPos)(l0));
		};
					Map var13 = ((Map)(((java.util.stream.Stream)(((java.util.stream.Stream)(var2.stream())).filter((Predicate)(var9)).distinct())).collect((Collector)(Collectors.toMap((Function)(var10),(Function)(var12))))));
					((ProtectedArea)(var6.get())).queueForRestoration(var13);
				}
			}
			if (!(((ProtectedArea)(var6.get())).canHurtEntities())){
				var3.removeIf((Predicate)(((Predicate<Entity>)((l0) -> {
			return !(l0 instanceof Player);
		}))));
			}
			if (!(((ProtectedArea)(var6.get())).canHurtPlayers())){
				Class var7_1 = Player.class;
				Objects.requireNonNull(var7_1);
				var3.removeIf((Predicate)(((Predicate<Entity>)(var7_1::isInstance))));
			}else{
				return;
			}
		}
	}
}