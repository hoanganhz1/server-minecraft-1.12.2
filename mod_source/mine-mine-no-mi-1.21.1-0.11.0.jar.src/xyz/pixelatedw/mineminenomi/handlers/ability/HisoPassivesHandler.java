package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.data.entity.HisoLookoutData;
import xyz.pixelatedw.mineminenomi.data.entity.HisoLookoutData$HisoLookoutTarget;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class HisoPassivesHandler {
	public static void lookoutMarking(Animal var0) {
		if (!(var0.hasEffect((Holder)(ModEffects.ANIMAL_LOOKOUT)))){
			return;
		}else{
			HisoLookoutData var1 = ((HisoLookoutData)(((java.util.Optional)(HisoLookoutData.get(var0))).orElse((Object)(null))));
			if ((var1) == (null)){
				return;
			}else{
				Predicate<Entity> var2 = (l0) -> {
			return (l0 instanceof Player) || (l0 instanceof NotoriousEntity);
		};
				TargetPredicate var3 = new TargetPredicate().selector(var2);
				List var4 = TargetHelper.getEntitiesInArea((LevelAccessor)(var0.level()),(LivingEntity)(var0),var0.position(),10D,var3,LivingEntity.class);
				var4.remove(var0);
				Iterator var5 = var4.iterator();
				do{
					if (var5.hasNext()){
						var1.registerSeenTarget(((LivingEntity)(var5.next())));
						continue;
					}else{
						break;
					}
				} while (true);
				return;
			}
		}
	}
	public static void lookoutCleanup(Animal var0) {
		HisoLookoutData var1 = ((HisoLookoutData)(((java.util.Optional)(HisoLookoutData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			Iterator var2 = var1.getSeenTargets().entrySet().iterator();
			do{
				if (var2.hasNext()){
					Map.Entry var3 = ((Map.Entry)(var2.next()));
					if ((((HisoLookoutData$HisoLookoutTarget)(var3.getValue())).passedMinutes(var0.level().getGameTime())) > (20L)){
						var1.removeSeenTarget(((UUID)(var3.getKey())));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
}