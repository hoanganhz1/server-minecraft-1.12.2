package xyz.pixelatedw.mineminenomi.abilities.moku;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class WhitePullAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 200;
	private static final int RANGE = 100;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<WhitePullAbility>> INSTANCE;
	private final RangeComponent rangeComponent;
	private final ContinuousComponent continuousComponent;
	private ArrayList<LivingEntity> targetsToRemove;

	public WhitePullAbility(AbilityCore<WhitePullAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.targetsToRemove = (ArrayList) (new ArrayList());
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent,this.continuousComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	public void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,100F);
	}
	public void onContinuityTick(LivingEntity var1, IAbility var2) {
		Iterator var3 = this.rangeComponent.getTargetsInArea(var1,100F).iterator();
		do{
			if (var3.hasNext()){
				LivingEntity var4 = ((LivingEntity)(var3.next()));
				if (var4.hasEffect((Holder)(ModEffects.SMOKE))){
					AbilityHelper.setDeltaMovement((Entity)(var4),var1.position().subtract(var4.position()).normalize().scale(0.5D));
					if (!(this.targetsToRemove.contains(var4))){
						this.targetsToRemove.add(var4);
						continue;
					}else{
						continue;
					}
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public void onContinuityEnd(LivingEntity var1, IAbility var2) {
		Iterator var3 = this.targetsToRemove.iterator();
		do{
			if (var3.hasNext()){
				((LivingEntity)(var3.next())).removeEffect((Holder)(ModEffects.SMOKE));
				continue;
			}else{
				break;
			}
		} while (true);
		this.targetsToRemove.clear();
		this.cooldownComponent.startCooldown(var1,200F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Pulls all nearby entities surrounded by smoke towards the user."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),RangeComponent.getTooltip(100F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(WhitePullAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("white_pull","White Pull",var0));
	}
}