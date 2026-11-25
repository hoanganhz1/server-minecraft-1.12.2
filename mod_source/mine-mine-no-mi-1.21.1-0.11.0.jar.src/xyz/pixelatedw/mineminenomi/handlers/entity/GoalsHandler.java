package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NBTGoal;
import xyz.pixelatedw.mineminenomi.api.enums.NBTGoalType;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.NBTGoalsData;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;

public class GoalsHandler {
	public static void parseNBTGoals(Mob var0) {
		NBTGoalsData var1 = ((NBTGoalsData)(((java.util.Optional)(NBTGoalsData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			Iterator var2 = var1.getGoals().iterator();
			do{
				if (var2.hasNext()){
					NBTGoal var3 = ((NBTGoal)(var2.next()));
					AbilityWrapperGoal var4 = ((AbilityWrapperGoal)(((java.util.function.BiFunction)(var3.type().getWrapperFactory())).apply((Object)(var0),(Object)(var3.abilityId()))));
					var0.goalSelector.addGoal(var3.priority(),(Goal)(var4));
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static void addNewCreeperGoals(Creeper var0) {
		Predicate<LivingEntity> var1 = (l0) -> {
			return (((MorphInfo)(ModMorphs.LEOPARD_HEAVY.get())).isActive(l0)) || (((MorphInfo)(ModMorphs.LEOPARD_WALK.get())).isActive(l0));
		};
		var0.goalSelector.addGoal(3,(Goal)(new AvoidEntityGoal((PathfinderMob)(var0),Player.class,12F,1.2D,1.75D,var1)));
	}
	public static void addNewIronGolemGoals(IronGolem var0) {
		Predicate<LivingEntity> var1 = (l0) -> {
			int lv2_1 = 0;
			if (l0 instanceof OPEntity){
				OPEntity lv2_2 = ((OPEntity)(l0));
				OPEntity lv2_3 = lv2_2;
				if ((!(lv2_3.getEntityStats().isBandit())) && (!(lv2_3.getEntityStats().isPirate()))){
					lv2_1 = 0;
				}else{
					lv2_1 = 1;
				}
			}else{
				lv2_1 = 0;
			}
			return (((lv2_1) & 1) != 0);
		};
		var0.targetSelector.addGoal(3,(Goal)(new NearestAttackableTargetGoal((Mob)(var0),Mob.class,5,false,false,var1)));
	}
}