package xyz.pixelatedw.mineminenomi.init;

import com.google.common.base.Predicates;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;

public class ModEntityPredicates {
	// Fields
	public static final Predicate<Entity> ENTITY_EXISTS;
	public static final Predicate<Entity> IS_ALIVE_AND_SURVIVAL;
	public static final Predicate<Entity> IS_ENTITY_HARMLESS;
	public static final Predicate<Entity> IS_INVISIBLE;
	public static final Predicate<Entity> TARGET_HAS_ISSUED_BOUNTY;

	public static Predicate<Entity> hasTargetWithinDistance(LivingEntity var0, float var1) {
		return (Predicate<Entity>) (Predicate) (((java.util.function.Predicate)(canSee(var0))).and((Predicate)(EntitySelector.withinDistance(var0.position().x,var0.position().y,var0.position().z,(double)(var1)))));
	}
	public static Predicate<Entity> getFriendlyFactions(LivingEntity var0) {
		return (Predicate<Entity>) (Predicate) (getEnemyFactions(var0).negate());
	}
	static  {
		Predicate<Entity> var0 = (l0) -> {
			return (l0) != (null);
		};
		ENTITY_EXISTS = var0;
		IS_ALIVE_AND_SURVIVAL = (Predicate) (ENTITY_EXISTS.and(EntitySelector.NO_CREATIVE_OR_SPECTATOR).and(EntitySelector.ENTITY_STILL_ALIVE));
		Predicate<Entity> var1 = (l0) -> {
			MobCategory lv7_1 = l0.getType().getCategory();
			if ((!(lv7_1.equals(MobCategory.WATER_AMBIENT))) && (!(lv7_1.equals(MobCategory.AMBIENT)))){
				if ((!(l0 instanceof Animal)) && ((!(l0 instanceof AmbientCreature)) && (!(l0 instanceof AbstractFish)))){
					if (l0 instanceof Squid){
						return true;
					}else{
						return false;
					}
				}else{
					return true;
				}
			}else{
				return true;
			}
		};
		IS_ENTITY_HARMLESS = var1;
		Predicate<Entity> var2 = Entity::isInvisible;
		IS_INVISIBLE = var2;
		Predicate<Entity> var3 = (l0) -> {
			LivingEntity lv9_4 = null;
			if (l0 instanceof LivingEntity){
				LivingEntity lv9_1 = ((LivingEntity)(l0));
				FactionsWorldData lv9_2 = FactionsWorldData.get();
				EntityStatsData lv9_3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(lv9_1))).orElse((Object)(null))));
				if (((lv9_2) != (null)) && ((lv9_3) != (null))){
					lv9_4 = lv9_1;
					final LivingEntity lv9_4_f1 = lv9_4;
					Function<Faction, Boolean> lv9_5 = (l2_0) -> {
				return Boolean.valueOf(l2_0.canReceiveBounty(lv9_4_f1));
			};
					if (((Boolean)(((java.util.Optional)(lv9_3.getFaction())).map((Function)(lv9_5)).orElse(Boolean.valueOf(false)))).booleanValue()){
						int lv9_6 = ((lv9_1.getCommandSenderWorld().getDifficulty().getId()) <= (Difficulty.NORMAL.getId())) ? (1) : (0);
						if ((lv9_1 instanceof Player) && (((lv9_6) != (0)) && (!(lv9_2.hasIssuedBounty(lv9_1))))){
							return false;
						}else{
							return true;
						}
					}else{
						return true;
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		};
		TARGET_HAS_ISSUED_BOUNTY = var3;
	}
	public static Predicate<Entity> canBeSeenBy(LivingEntity var0) {
		LivingEntity var1 = var0;
		final LivingEntity var1_f1 = var1;
		Predicate<? super Entity> var2 = (l0) -> {
			return var1_f1.hasLineOfSight(l0);
		};
		return (Predicate<Entity>) (Predicate) (((java.util.function.Predicate)(IS_ALIVE_AND_SURVIVAL)).and((Predicate)(var2)));
	}
	public static Predicate<Entity> canSee(LivingEntity var0) {
		LivingEntity var1 = var0;
		final LivingEntity var1_f1 = var1;
		Predicate<? super Entity> var2 = (l0) -> {
			boolean lv2_2 = false;
			if (l0 instanceof LivingEntity){
				LivingEntity lv2_3 = ((LivingEntity)(l0));
				lv2_2 = lv2_3.hasLineOfSight((Entity)(var1_f1));
			}else{
				lv2_2 = false;
			}
			return lv2_2;
		};
		return (Predicate<Entity>) (Predicate) (((java.util.function.Predicate)(IS_ALIVE_AND_SURVIVAL)).and((Predicate)(var2)));
	}
	public static Predicate<Entity> canBeTamed() {
		Predicate<Entity> var0 = (l0) -> {
			if (l0 instanceof LivingEntity){
				return MobsHelper.canBeTamed(((LivingEntity)(l0)));
			}else{
				return false;
			}
		};
		return var0;
	}
	public static Predicate<Entity> isTamedBy(@Nullable() LivingEntity var0) {
		LivingEntity var1 = var0;
		final LivingEntity var1_f1 = var1;
		Predicate<Entity> var2 = (l0) -> {
			if ((var1_f1) == (null)){
				return false;
			}else{
				if (l0 instanceof LivingEntity){
					return MobsHelper.isTamedBy(((LivingEntity)(l0)),var1_f1);
				}else{
					return false;
				}
			}
		};
		return var2;
	}
	public static Predicate<Entity> getEnemyFactions(LivingEntity var0) {
		if ((var0) == (null)){
			return Predicates.alwaysTrue();
		}else{
			LivingEntity var1 = var0;
			final LivingEntity var1_f1 = var1;
			Predicate<Entity> var2 = (l0) -> {
			if ((l0) == (null)){
				return false;
			}else{
				if (l0 instanceof LivingEntity){
					return FactionHelper.isEnemyFactions(var1_f1,((LivingEntity)(l0)));
				}else{
					return true;
				}
			}
		};
			return var2;
		}
	}
}