package xyz.pixelatedw.mineminenomi.abilities.ryupteranodon;

import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class BeakGrabAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 200;
	private static final int HOLD_TIME = 300;
	private static final float RANGE = 1.2F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BeakGrabAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final AnimationComponent animationComponent;
	private final RangeComponent rangeComponent;
	private final GrabEntityComponent grabEntityComponent;

	public BeakGrabAbility(AbilityCore<BeakGrabAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.grabEntityComponent = new GrabEntityComponent((IAbility)(this),false,true,2F);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.animationComponent,this.rangeComponent,this.grabEntityComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RyuPteraHelper::requiresFlyPoint))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RyuPteraHelper::requiresFlyPoint))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,300F);
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		Level var3 = var1.level();
		if (((var3) != (null)) && (!(var3.isClientSide))){
			this.animationComponent.start(var1,ModAnimations.PTERA_OPEN_MOUTH);
			var3.playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.DASH_ABILITY_SWOOSH_SFX.get())),SoundSource.PLAYERS,2F,1F);
			return;
		}else{
			return;
		}
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		Level var3 = var1.level();
		if (((var3) != (null)) && (!(var3.isClientSide))){
			LivingEntity var4 = this.grabEntityComponent.getGrabbedEntity();
			if ((!(this.canUse(var1).isFail())) && (!(((var4) != (null)) && (!(this.grabEntityComponent.canContinueGrab(var1)))))){
				if ((var4) == (null)){
					this.grabEntityComponent.grabNearest(var1,1.2F,1.4F,false);
				}
				return;
			}else{
				this.continuousComponent.stopContinuity(var1);
				return;
			}
		}else{
			return;
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		Level var3 = var1.level();
		if (((var3) != (null)) && (!(var3.isClientSide))){
			this.grabEntityComponent.release(var1);
			this.animationComponent.stop(var1);
			this.cooldownComponent.startCooldown(var1,200F);
			return;
		}else{
			return;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to carry their target."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[2];
			lv1_4[0] = AbilityDescriptionLine.NEW_LINE;
			Supplier[] lv1_5 = new Supplier[]{ModMorphs.PTERA_FLY};
			lv1_4[1] = AbilityTooltipsHelper.getRequiredMorphTooltip(lv1_5);
			AbilityDescriptionLine$IDescriptionLine[] lv1_6 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),ContinuousComponent.getTooltip(300F),RangeComponent.getTooltip(1.2F,RangeComponent$RangeType.LINE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BeakGrabAbility::new)))).addDescriptionLine(lv1_3).addDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_6).setSourceHakiNature(SourceHakiNature.HARDENING).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("beak_grab","Beak Grab",var0));
	}
}