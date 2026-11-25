package xyz.pixelatedw.mineminenomi.abilities.kage;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.DoppelmanEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DoppelmanAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 12000;
	private static final int MIN_COOLDOWN = 40;
	private static final int MAX_COOLDOWN = 6000;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DoppelmanAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final StackComponent stackComponent;
	private DoppelmanEntity doppelman;
	private int shadowsUsed;
	private int prevShadowsUsed;

	public DoppelmanAbility(AbilityCore<DoppelmanAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onTickEvent)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::stopContinuityEvent))));
		this.stackComponent = new StackComponent((IAbility)(this));
		this.doppelman = null;
		this.shadowsUsed = 0;
		this.prevShadowsUsed = 0;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.stackComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.doppelman = new DoppelmanEntity((Level)(var1.level()),(LivingEntity)(var1));
		this.doppelman.moveTo(var1.getX(),var1.getY(),var1.getZ(),var1.getYRot(),var1.getXRot());
		if (!(var1.level().isClientSide)){
			this.doppelman.setShadow(this.shadowsUsed);
			this.stackComponent.setStacks(var1,this,this.shadowsUsed);
		}
		var1.level().addFreshEntity((Entity)(this.doppelman));
	}
	private void onTickEvent(LivingEntity var1, IAbility var2) {
		if (((this.doppelman) != (null)) && (this.doppelman.isAlive())){
			this.shadowsUsed = this.doppelman.getShadows();
			if ((this.shadowsUsed) != (this.prevShadowsUsed)){
				this.stackComponent.setStacks(var1,this,this.shadowsUsed);
				this.prevShadowsUsed = this.shadowsUsed;
			}
			return;
		}else{
			this.continuousComponent.stopContinuity(var1);
			return;
		}
	}
	private void stopContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((this.doppelman) != (null)){
			this.doppelman.discard();
		}
		this.prevShadowsUsed = 0;
		float var3 = Mth.clamp(this.continuousComponent.getContinueTime(),40F,6000F);
		this.cooldownComponent.startCooldown(var1,var3);
	}
	@Nullable()
	public DoppelmanEntity getDoppelman() {
		return this.doppelman;
	}
	public void doppelmanDeathTrigger(LivingEntity var1) {
		this.shadowsUsed = 0;
		this.stackComponent.setStacks(var1,this,0);
	}
	public void saveAdditional(CompoundTag var1) {
		var1.putInt("shadows",this.shadowsUsed);
	}
	public void loadAdditional(CompoundTag var1) {
		this.shadowsUsed = var1.getInt("shadows");
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Creates a living version of the user's shadow to help them fight"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(40F,6000F),ContinuousComponent.getTooltip()};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DoppelmanAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("doppelman","Doppelman",var0));
	}
}