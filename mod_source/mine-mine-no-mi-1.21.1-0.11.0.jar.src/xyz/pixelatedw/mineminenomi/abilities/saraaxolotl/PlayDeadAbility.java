package xyz.pixelatedw.mineminenomi.abilities.saraaxolotl;

import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PlayDeadAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 200;
	private static final int MIN_COOLDOWN = 200;
	private static final int MAX_COOLDOWN = 400;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<PlayDeadAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final AnimationComponent animationComponent;
	private final DamageTakenComponent damageTakenComponent;

	public PlayDeadAbility(AbilityCore<PlayDeadAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnHurtEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onHurtEvent))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.animationComponent,this.damageTakenComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(SaraAxolotlHelper::requiresWalkPoint))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(SaraAxolotlHelper::requiresWalkPoint))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,200F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.PLAY_DEAD,200);
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.PLAY_DEAD),200,0,false,false));
	}
	
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		var1.removeEffect((Holder)(ModEffects.PLAY_DEAD));
		this.cooldownComponent.startCooldown(var1,(200F) + (this.continuousComponent.getContinueTime()));
	}
	private float onHurtEvent(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if (this.continuousComponent.isContinuous()){
			return (var4) * (0.75F);
		}else{
			return var4;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("While playing dead the user focuses all of their power into regeneration."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[2];
			lv2_4[0] = AbilityDescriptionLine.NEW_LINE;
			Supplier[] lv2_5 = new Supplier[]{ModMorphs.AXOLOTL_WALK};
			lv2_4[1] = AbilityTooltipsHelper.getRequiredMorphTooltip(lv2_5);
			AbilityDescriptionLine$IDescriptionLine[] lv2_6 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F,400F),ContinuousComponent.getTooltip(200F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(PlayDeadAbility::new)))).addDescriptionLine(lv2_3).addDescriptionLine(lv2_4).addAdvancedDescriptionLine(lv2_6).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("play_dead","Play Dead",var0));
	}
private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),2,2,false,false));
		PlayDeadAbility var3 = this;
		Predicate<IAbility> var4 = (l0) -> {
			return (!(l0.equals(this))) && (!(l0.getCore().equals(AxolotlWalkPointAbility.INSTANCE.get())));
		};
		AbilityHelper.disableAbilities(var1,2,var4);
	}}