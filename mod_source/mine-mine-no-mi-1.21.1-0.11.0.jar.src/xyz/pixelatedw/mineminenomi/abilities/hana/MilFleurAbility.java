package xyz.pixelatedw.mineminenomi.abilities.hana;

import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class MilFleurAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 600;
	private static final int MIN_COOLDOWN = 100;
	private static final float MAX_COOLDOWN = 400F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<MilFleurAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;

	public MilFleurAbility(AbilityCore<MilFleurAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,600F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			DosFleurClutchAbility var4 = ((DosFleurClutchAbility)(var3.getEquippedAbility(((AbilityCore)(DosFleurClutchAbility.INSTANCE.get())))));
			if ((var4) != (null)){
				var4.switchMilMode(var1);
			}
			SeisFleurSlapAbility var5 = ((SeisFleurSlapAbility)(var3.getEquippedAbility(((AbilityCore)(SeisFleurSlapAbility.INSTANCE.get())))));
			if ((var5) != (null)){
				var5.switchMilMode(var1);
			}
			SeisFleurTwistAbility var6 = ((SeisFleurTwistAbility)(var3.getEquippedAbility(((AbilityCore)(SeisFleurTwistAbility.INSTANCE.get())))));
			if ((var6) != (null)){
				var6.switchMilMode(var1);
			}
			return;
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			DosFleurClutchAbility var4 = ((DosFleurClutchAbility)(var3.getEquippedAbility(((AbilityCore)(DosFleurClutchAbility.INSTANCE.get())))));
			if ((var4) != (null)){
				var4.switchNormalMode(var1);
			}
			SeisFleurSlapAbility var5 = ((SeisFleurSlapAbility)(var3.getEquippedAbility(((AbilityCore)(SeisFleurSlapAbility.INSTANCE.get())))));
			if ((var5) != (null)){
				var5.switchNormalMode(var1);
			}
			SeisFleurTwistAbility var6 = ((SeisFleurTwistAbility)(var3.getEquippedAbility(((AbilityCore)(SeisFleurTwistAbility.INSTANCE.get())))));
			if ((var6) != (null)){
				var6.switchNormalMode(var1);
			}
			float var7 = Math.max(100F,400F);
			this.cooldownComponent.startCooldown(var1,var7);
			return;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("While active all the other abilities of this fruit will transform, either allowing for area of effects or bigger and better versions of themselves."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(100F,400F),ContinuousComponent.getTooltip(600F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(MilFleurAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("mil_fleur","Mil Fleur",var0));
	}
}