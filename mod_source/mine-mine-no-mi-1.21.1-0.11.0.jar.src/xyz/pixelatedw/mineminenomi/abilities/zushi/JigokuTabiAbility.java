package xyz.pixelatedw.mineminenomi.abilities.zushi;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class JigokuTabiAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 120;
	private static final int MIN_COOLDOWN = 100;
	private static final int MAX_COOLDOWN = 340;
	private static final int RANGE = 24;
	private static final int DEFAULT_FORCE = 4;
	private static final int FORCE_INCREASE = 60;
	private static final int MIN_DAMAGE = 8;
	private static final int MAX_DAMAGE = 12;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<JigokuTabiAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private int force;
	private Interval damageInterval;
	private Interval addForceInterval;

	public JigokuTabiAbility(AbilityCore<JigokuTabiAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.force = 4;
		this.damageInterval = new Interval(20);
		this.addForceInterval = new Interval(60);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.rangeComponent,this.dealDamageComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,120F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.force = 4;
		this.damageInterval.restartIntervalToZero();
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			List var3 = this.rangeComponent.getTargetsInArea(var1,24F);
			AbilityDamageSource var4 = this.dealDamageComponent.getDamageSource(var1);
			IDamageSourceHandler var5 = IDamageSourceHandler.getHandler(var4);
			var5.bypassLogia();
			var5.addAbilityPiercing(1F,this.getCore());
			var5.setUnavoidable();
			SimpleBlockPlacer var6 = new SimpleBlockPlacer().setBlock(Blocks.AIR.defaultBlockState()).setSize(this.force,2).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE);
			Iterator var7 = var3.iterator();
			do{
				if (var7.hasNext()){
					LivingEntity var8 = ((LivingEntity)(var7.next()));
					AbilityHelper.setDeltaMovement((Entity)(var8),0D,(var8.getDeltaMovement().y) - (4D),0D);
					if (this.damageInterval.canTick()){
						var8.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,25,5,false,false));
						this.dealDamageComponent.hurtTarget(var1,var8,(float)((this.force) * (2)),var4);
						GraviZoneAbility.gravityRing(var8,3,2,false);
						var6.generate(var1.level(),var8.blockPosition(),BlockGenerators.SPHERE);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			if (this.addForceInterval.canTick()){
				this.force = (this.force) + (1);
			}
			return;
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		float var3 = (100F) + ((this.continuousComponent.getContinueTime()) * (2F));
		this.cooldownComponent.startCooldown(var1,var3);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Causes a powerful downward force of gravity, sending the enemies down in a crater. The longer its used the more damage it'll deal and the larger the crater will become."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F,340F),ContinuousComponent.getTooltip(120F),RangeComponent.getTooltip(24F,RangeComponent$RangeType.AOE),DealDamageComponent.getTooltip(8F,12F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(JigokuTabiAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceElement(SourceElement.GRAVITY).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("jigoku_tabi","Jigoku Tabi",var0));
	}
}