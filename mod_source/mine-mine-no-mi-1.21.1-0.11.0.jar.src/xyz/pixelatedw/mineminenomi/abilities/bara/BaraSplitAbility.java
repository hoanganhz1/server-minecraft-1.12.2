package xyz.pixelatedw.mineminenomi.abilities.bara;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.OutOfBodyAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.BottomHalfBodyEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BaraSplitAbility extends OutOfBodyAbility {
	// Fields
	private static final int HOLD_TIME = 1000;
	private static final int MIN_COOLDOWN = 100;
	private static final int MAX_COOLDOWN = 1000;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BaraSplitAbility>> INSTANCE;
	private final MorphComponent morphComponent;
	private final PoolComponent poolComponent;
	private BottomHalfBodyEntity legs;

	public BaraSplitAbility(AbilityCore<BaraSplitAbility> var1) {
		super((AbilityCore)(var1));
		this.morphComponent = new MorphComponent((IAbility)(this));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.BARA_ABILITY,new AbilityPool[0]);
		AbilityComponent[] var2 = new AbilityComponent[]{this.morphComponent,this.poolComponent};
		this.addComponents(var2);
		this.continuousComponent.addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
		this.continuousComponent.addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent))));
		this.continuousComponent.addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.morphComponent.startMorph(var1,((MorphInfo)(ModMorphs.BARA_SPLIT.get())));
		AbilityHelper.setDeltaMovement((Entity)(var1),0D,2D,0D);
		this.legs = new BottomHalfBodyEntity((Level)(var1.level()),(LivingEntity)(var1));
		this.legs.moveTo(var1.getX(),var1.getY(),var1.getZ(),var1.getYRot(),var1.getXRot());
		var1.level().addFreshEntity((Entity)(this.legs));
		this.legs.setParentAbility((Ability)(this));
		this.setOriginalBody((LivingEntity)(this.legs));
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			float var3 = this.continuousComponent.getContinueTime();
			if ((this.legs) != (null)){
				this.setPivotPoint(this.legs.blockPosition());
			}
			if (((var3) > (5F)) && (var1.onGround())){
				this.continuousComponent.stopContinuity(var1);
				return;
			}else{
				return;
			}
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.morphComponent.stopMorph(var1);
		float var3 = Math.max(100F,this.continuousComponent.getContinueTime());
		this.cooldownComponent.startCooldown(var1,var3);
	}
	public int getHoldTime() {
		return 1000;
	}
	public float getMaxRange() {
		return 40F;
	}
	public boolean isPhysical() {
		return true;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to split its upper part of the body from the lower."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BaraSplitAbility::new)))).addDescriptionLine(lv2_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("bara_split","Bara Split",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(100F,1000F);
		};
		CooldownComponentTooltip.generate(var2,var3);
	}
}