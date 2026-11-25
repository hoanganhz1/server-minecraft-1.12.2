package xyz.pixelatedw.mineminenomi.abilities.kira;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class BrilliantPunkAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 10;
	private static final int COOLDOWN = 200;
	private static final float RANGE = 1.6F;
	private static final float DAMAGE = 25F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BrilliantPunkAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final AnimationComponent animationComponent;
	private final HitTrackerComponent hitTrackerComponent;

	public BrilliantPunkAbility(AbilityCore<BrilliantPunkAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.rangeComponent,this.dealDamageComponent,this.animationComponent,this.hitTrackerComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(this::requiresDiamondBody))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,10F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.hitTrackerComponent.clearHits();
		this.animationComponent.start(var1,ModAnimations.TACKLE);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.isAlive()){
			Vec3 var3 = var1.getLookAngle();
			Vec3 var4 = var3.multiply(2.3D,0D,2.3D);
			var1.move(MoverType.SELF,var4);
			Iterator var5 = this.rangeComponent.getTargetsInLine(var1,1F,1.6F).iterator();
			do{
				if (var5.hasNext()){
					LivingEntity var6 = ((LivingEntity)(var5.next()));
					if (this.hitTrackerComponent.canHit((Entity)(var6))){
						this.dealDamageComponent.hurtTarget(var1,var6,25F);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			Iterator var6_1 = this.hitTrackerComponent.getHits().iterator();
			do{
				if (var6_1.hasNext()){
					((Entity)(var6_1.next())).teleportTo((var1.getX()) + (var3.x),var1.getY(),(var1.getZ()) + (var3.z));
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,200F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[]{ImmutablePair.of((Object)("The user rams into the target with their diamond body."),(Object)(null))};
			Component[] lv3_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			AbilityDescriptionLine$IDescriptionLine[] lv3_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),RangeComponent.getTooltip(1.6F,RangeComponent$RangeType.LINE),DealDamageComponent.getTooltip(25F)};
			SourceType[] lv3_5 = new SourceType[]{SourceType.PHYSICAL};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BrilliantPunkAbility::new)))).addDescriptionLine(lv3_3).addAdvancedDescriptionLine(lv3_4).setSourceType(lv3_5).setSourceHakiNature(SourceHakiNature.HARDENING).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("brilliant_punk","Brilliant Punk",var0));
	}
	private Result requiresDiamondBody(LivingEntity var1, IAbility var2) {
		Function<AbilityData, DiamondBodyAbility> var3 = (l0) -> {
			return ((DiamondBodyAbility)(l0.getEquippedAbility(((AbilityCore)(DiamondBodyAbility.INSTANCE.get())))));
		};
		Function<DiamondBodyAbility, Boolean> var4 = (l0) -> {
			return Boolean.valueOf(l0.isContinuous());
		};
		if (!(((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)))).map((Function)(var4)).orElse(Boolean.valueOf(false)))).booleanValue())){
			Object[] var5 = new Object[]{((AbilityCore)(DiamondBodyAbility.INSTANCE.get())).getUnlocalizedName()};
			return Result.fail((Component)(Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var5)));
		}else{
			return Result.success();
		}
	}
}