package xyz.pixelatedw.mineminenomi.abilities.gomu;

import java.util.Optional;
import net.minecraft.network.chat.Component;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GearThirdAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 400;
	private static final int MIN_COOLDOWN = 60;
	private static final float MAX_COOLDOWN = 400F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GearThirdAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;

	public GearThirdAbility(AbilityCore<GearThirdAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.setOGCD();
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent};
		this.addComponents(var2);
		this.addCanUseCheck(GomuHelper.canUseGearCheck(((AbilityCore)(INSTANCE.get()))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,400F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			GomuGomuNoPistolAbility var4 = ((GomuGomuNoPistolAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoPistolAbility.INSTANCE.get())))));
			if ((var4) != (null)){
				var4.switchThirdGear(var1);
			}
			GomuGomuNoGatlingAbility var5 = ((GomuGomuNoGatlingAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoGatlingAbility.INSTANCE.get())))));
			if ((var5) != (null)){
				var5.switchThirdGear(var1);
			}
			GomuGomuNoBazookaAbility var6 = ((GomuGomuNoBazookaAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoBazookaAbility.INSTANCE.get())))));
			if ((var6) != (null)){
				var6.switchThirdGear(var1);
			}
			return;
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			GomuGomuNoPistolAbility var4 = ((GomuGomuNoPistolAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoPistolAbility.INSTANCE.get())))));
			if ((var4) != (null)){
				var4.switchNoGear(var1);
			}
			GomuGomuNoGatlingAbility var5 = ((GomuGomuNoGatlingAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoGatlingAbility.INSTANCE.get())))));
			if ((var5) != (null)){
				var5.switchNoGear(var1);
			}
			GomuGomuNoBazookaAbility var6 = ((GomuGomuNoBazookaAbility)(var3.getEquippedAbility(((AbilityCore)(GomuGomuNoBazookaAbility.INSTANCE.get())))));
			if ((var6) != (null)){
				var6.switchNoGear(var1);
			}
			float var7 = Math.max(60F,this.continuousComponent.getContinueTime());
			this.cooldownComponent.startCooldown(var1,var7);
			return;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("By blowing air and inflating their body, the user's attacks get bigger and gain incredible strength."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(60F,400F),ContinuousComponent.getTooltip(400F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GearThirdAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gear_third","Gear Third",var0));
	}
}