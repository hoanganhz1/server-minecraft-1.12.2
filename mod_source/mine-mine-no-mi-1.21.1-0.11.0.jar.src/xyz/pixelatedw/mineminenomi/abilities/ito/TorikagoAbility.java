package xyz.pixelatedw.mineminenomi.abilities.ito;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule$Builder;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TorikagoAbility extends Ability {
	// Fields
	public static final int MAX_CAGE_SIZE = 60;
	private static final int MIN_CAGE_SIZE = 8;
	private static final int CHARGE_TIME = 60;
	private static final int MAX_COOLDOWN = 200;
	private static final float MIN_COOLDOWN = 26.666668F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<TorikagoAbility>> INSTANCE;
	private static final BlockProtectionRule GRIEF_RULE;
	private static final BlockProtectionRule RESTORE_RULE;
	private final ChargeComponent chargeComponent;
	private final ContinuousComponent continuousComponent;
	private final AnimationComponent animationComponent;
	private final BlockTrackerComponent blockTrackerComponent;
	private SimpleBlockPlacer placer;
	public int cageSize;
	private BlockPos centerPos;
	private Interval checkPositionInterval;

	public TorikagoAbility(AbilityCore<TorikagoAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this),true).addStartEvent(100,(ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::startChargeEvent)))).addEndEvent(100,(ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuousEvent)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuousEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.blockTrackerComponent = new BlockTrackerComponent((IAbility)(this));
		this.cageSize = 0;
		this.checkPositionInterval = new Interval(20);
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.continuousComponent,this.animationComponent,this.blockTrackerComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if (this.isContinuous()){
			this.continuousComponent.stopContinuity(var1);
			return;
		}else{
			this.chargeComponent.startCharging(var1,60F);
			return;
		}
	}
	private void startChargeEvent(LivingEntity var1, IAbility var2) {
		this.checkPositionInterval.restartIntervalToZero();
		this.animationComponent.start(var1,ModAnimations.RAISE_RIGHT_ARM,60);
		this.placer = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.STRING_WALL.get())).defaultBlockState()).setRule(GRIEF_RULE).setHollow().setBlockQueue(this.blockTrackerComponent.getQueue());
		this.blockTrackerComponent.initQueue(var1.level(),500);
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			this.centerPos = var1.blockPosition();
			this.cageSize = Math.max(8,(int)((60F) * ((this.chargeComponent.getChargeTime()) / (this.chargeComponent.getMaxChargeTime()))));
			if ((this.placer) != (null)){
				this.placer.setSize(this.cageSize);
				this.placer.generate(var1.level(),this.centerPos,BlockGenerators.SPHERE);
			}
			this.continuousComponent.startContinuity(var1,-1F);
			this.animationComponent.stop(var1);
			this.blockTrackerComponent.getQueue().start();
			return;
		}
	}
	private void tickContinuousEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (((this.centerPos) != (null)) && ((this.checkPositionInterval.canTick()) && (!(this.isPositionInTorikago(var1.blockPosition()))))){
				this.continuousComponent.stopContinuity(var1);
			}
			this.blockTrackerComponent.tickBlockQueue(var1.level());
			return;
		}
	}
	private void endContinuousEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((this.placer) != (null)){
				Iterator var3 = this.placer.getPlacedPositions().iterator();
				do{
					if (var3.hasNext()){
						BlockPos var4 = ((BlockPos)(var3.next()));
						var1.level().setBlockAndUpdate(var4,Blocks.AIR.defaultBlockState());
						continue;
					}else{
						break;
					}
				} while (true);
			}
			this.centerPos = null;
			float var3_1 = (200F) * (((float)(this.cageSize)) / (60F));
			this.cooldownComponent.startCooldown(var1,var3_1);
			return;
		}
	}
	public boolean isPositionInTorikago(BlockPos var1) {
		if ((this.centerPos) == (null)){
			return false;
		}else{
			return var1.closerThan((Vec3i)(this.centerPos),(double)(this.cageSize));
		}
	}
	public BlockPos getCenter() {
		return this.centerPos;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Creates an indestructible dome made of strings, that damage anyone who touches them"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(26.666668F,200F),ChargeComponent.getTooltip(60F),ContinuousComponent.getTooltip()};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(TorikagoAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("torikago","Torikago",var0));
		GRIEF_RULE = new BlockProtectionRule$Builder(new BlockProtectionRule[]{DefaultProtectionRules.AIR_FOLIAGE_LIQUID}).setBypassGriefFlag().build();
		Supplier[] var1 = new Supplier[]{ModBlocks.STRING_WALL};
		RESTORE_RULE = new BlockProtectionRule$Builder(new BlockProtectionRule[0]).addApprovedBlocks(var1).setBypassGriefFlag().build();
	}
}