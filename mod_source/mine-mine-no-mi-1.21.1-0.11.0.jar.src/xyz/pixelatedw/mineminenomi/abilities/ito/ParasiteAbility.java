package xyz.pixelatedw.mineminenomi.abilities.ito;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.BlackKnightEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ParasiteAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 600;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ParasiteAbility>> INSTANCE;
	private final RangeComponent rangeComponent;

	public ParasiteAbility(AbilityCore<ParasiteAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	public static void inflictParasiteStun(LivingEntity var0, LivingEntity var1) {
		if (var1 instanceof BlackKnightEntity){
			return;
		}else{
			if (CombatHelper.isTargetBlocking(var0,var1)){
				return;
			}else{
				double var2 = ((Double)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue();
				double var3 = ((Double)(EntityStatsData.get(var1).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue();
				if ((var2) < (var3)){
					return;
				}else{
					if ((var2) == (var3)){
						var1.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,100,0,false,false));
						var1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,100,2,false,false));
					}else{
						var1.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,300,0,false,false));
						var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),300,0,false,false));
					}
					return;
				}
			}
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("By attaching your strings to nearby enemies, they get completely immobilized"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			SourceType[] lv2_4 = new SourceType[]{SourceType.PHYSICAL};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ParasiteAbility::new)))).addDescriptionLine(lv2_3).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("parasite","Parasite",var0));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		Function<AbilityData, TorikagoAbility> var3 = (l0) -> {
			return ((TorikagoAbility)(l0.getEquippedAbility(((AbilityCore)(TorikagoAbility.INSTANCE.get())))));
		};
		TorikagoAbility var4 = ((TorikagoAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)).orElse(null)));
		if (((var4) != (null)) && (var4.isPositionInTorikago(var1.blockPosition()))){
			BlockPos var5 = var4.getCenter();
			Iterator var6 = this.rangeComponent.getTargetsInArea(var1,var5,60F).iterator();
			do{
				if (var6.hasNext()){
					LivingEntity var7 = ((LivingEntity)(var6.next()));
					if (var4.isPositionInTorikago(var7.blockPosition())){
						inflictParasiteStun(var1,var7);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}else{
			EntityHitResult var5_1 = WyHelper.rayTraceEntities((Entity)(var1),32D);
			if ((var5_1.getType().equals(HitResult.Type.ENTITY)) && (var5_1.getEntity() instanceof LivingEntity)){
				inflictParasiteStun(var1,((LivingEntity)(var5_1.getEntity())));
			}
		}
		this.cooldownComponent.startCooldown(var1,600F);
	}
}