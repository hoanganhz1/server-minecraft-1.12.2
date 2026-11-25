package xyz.pixelatedw.mineminenomi.abilities.ryupteranodon;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class TankyudonAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 200;
	private static final float RANGE = 1.2F;
	private static final double PUSH_FORCE = 2.2D;
	private static final float DAMAGE = 10F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<TankyudonAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final DealDamageComponent dealDamageComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private final RangeComponent rangeComponent;
	private Interval clearHitsInterval;

	public TankyudonAbility(AbilityCore<TankyudonAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.clearHitsInterval = new Interval(25);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.dealDamageComponent,this.hitTrackerComponent,this.rangeComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RyuPteraHelper::requiresFlyPoint))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RyuPteraHelper::requiresFlyPoint))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,WyHelper.secondsToTicks(10F));
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			this.hitTrackerComponent.clearHits();
			this.clearHitsInterval.restartIntervalToZero();
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.DASH_ABILITY_SWOOSH_SFX.get())),SoundSource.PLAYERS,2F,1F);
			return;
		}
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (super.canUse(var1).isFail()){
				this.continuousComponent.stopContinuity(var1);
				return;
			}else{
				List var3 = this.rangeComponent.getTargetsInLine(var1,1.2F,1.5F);
				if (this.clearHitsInterval.canTick()){
					this.hitTrackerComponent.clearHits();
				}
				Iterator var4 = var3.iterator();
				do{
					if (var4.hasNext()){
						LivingEntity var5 = ((LivingEntity)(var4.next()));
						if ((this.hitTrackerComponent.canHit((Entity)(var5))) && (this.dealDamageComponent.hurtTarget(var1,var5,10F))){
							AbilityHelper.setDeltaMovement((Entity)(var5),var1.getLookAngle().subtract(var5.getLookAngle()).normalize().scale(2.2D));
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
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			this.cooldownComponent.startCooldown(var1,200F);
			return;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Flying into enemies at great speeds deals damage and knocks them back."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[2];
			lv1_4[0] = AbilityDescriptionLine.NEW_LINE;
			Supplier[] lv1_5 = new Supplier[]{ModMorphs.PTERA_FLY};
			lv1_4[1] = AbilityTooltipsHelper.getRequiredMorphTooltip(lv1_5);
			AbilityDescriptionLine$IDescriptionLine[] lv1_6 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),ContinuousComponent.getTooltip(),DealDamageComponent.getTooltip(10F),RangeComponent.getTooltip(1.2F,RangeComponent$RangeType.LINE)};
			SourceType[] lv1_7 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(TankyudonAbility::new)))).addDescriptionLine(lv1_3).addDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_6).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_7).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("tankyudon","Tankyudon",var0));
	}
}