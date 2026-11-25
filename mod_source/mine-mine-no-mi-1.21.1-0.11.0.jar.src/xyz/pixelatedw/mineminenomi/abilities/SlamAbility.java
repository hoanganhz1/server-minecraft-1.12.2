package xyz.pixelatedw.mineminenomi.abilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.DropHitAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class SlamAbility extends DropHitAbility {
	// Fields
	private static final float COOLDOWN = 240F;
	private static final float RANGE = 2F;
	private static final float DAMAGE = 20F;
	public static final TargetPredicate TARGET_TEST = new TargetPredicate().testEnemyFaction().testSimpleInView();
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SlamAbility>> INSTANCE;
	private final DealDamageComponent dealDamageComponent;
	private final RangeComponent rangeComponent;
	private final AnimationComponent animationComponent;
	private BreakingBlocksParticleEffect$Details details;

	public SlamAbility(AbilityCore<SlamAbility> var1) {
		super((AbilityCore)(var1));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.details = new BreakingBlocksParticleEffect$Details(40);
		AbilityComponent[] var2 = new AbilityComponent[]{this.dealDamageComponent,this.rangeComponent,this.animationComponent};
		this.addComponents(var2);
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
		this.continuousComponent.addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.continuousComponent.addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.hitTrackerComponent.clearHits();
		Vec3 var3 = var1.getLookAngle();
		AbilityHelper.setDeltaMovement((Entity)(var1),var3.x,1.5D,var3.z);
	}
	private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		if (((var1.fallDistance) > (0F)) && (!(this.hasLanded()))){
			this.animationComponent.start(var1,ModAnimations.WEAPON_SLAM);
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,240F);
	}
	public void onLanding(LivingEntity var1) {
		Iterator var2 = this.rangeComponent.getTargetsInArea(var1,2F,TARGET_TEST).iterator();
		do{
			if (var2.hasNext()){
				LivingEntity var3 = ((LivingEntity)(var2.next()));
				if ((this.hitTrackerComponent.canHit((Entity)(var3))) && (this.dealDamageComponent.hurtTarget(var1,var3,20F))){
					var3.addEffect(new MobEffectInstance((Holder)(ModEffects.DIZZY),40,0,false,false));
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		ArrayList var3_1 = new ArrayList();
		int var4 = (int)(Math.ceil((double)(this.rangeComponent.getRange())));
		int var5 = -(var4);
		LOOP_1:
		do{
			if ((var5) < (var4)){
				int var6 = -(var4);
				do{
					if ((var6) < (var4)){
						double var7 = (var1.getX()) + ((double)(var5));
						double var8 = (var1.getY()) - (1D);
						double var9 = (var1.getZ()) + ((double)(var6));
						var3_1.add(new Vec3(var7,var8,var9));
						var6++;
						continue;
					}else{
						break;
					}
				} while (true);
				var5++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
		if ((((java.util.List)(var3_1)).size()) > (0)){
			this.details.setVecPositions((List)(var3_1));
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BREAKING_BLOCKS.get())),(Entity)(var1),0D,0D,0D,(ParticleEffect$Details)(this.details));
		}
		this.continuousComponent.stopContinuity(var1);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user jumps in the air and uses their fists or weapon to smash the target and the ground around it."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(240F),RangeComponent.getTooltip(2F,RangeComponent$RangeType.AOE),DealDamageComponent.getTooltip(20F)};
			SourceType[] lv1_5 = new SourceType[]{SourceType.BLUNT};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SlamAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("slam","Slam",var0));
	}
}