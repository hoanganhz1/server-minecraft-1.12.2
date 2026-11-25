package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility$GuardValue;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SamehadaShoteiAbility extends GuardAbility {
	// Fields
	private static final float HOLD_TIME = 200F;
	private static final float INTRERUPT_COOLDOWN = 200F;
	private static final float MIN_COOLDOWN = 100F;
	private static final float MAX_COOLDOWN = 400F;
	private static final float DAMAGE = 15F;
	private static final int DORIKI = 3000;
	private static final int MARTIAL_ARTS_POINTS = 5;
	private static final GuardAbility$GuardValue GUARD_VALUE = GuardAbility$GuardValue.percentage(1F);
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SamehadaShoteiAbility>> INSTANCE;
	private final AnimationComponent animationComponent;
	private final DealDamageComponent dealDamageComponent;

	public SamehadaShoteiAbility(AbilityCore<SamehadaShoteiAbility> var1) {
		super((AbilityCore)(var1));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.animationComponent,this.dealDamageComponent};
		this.addComponents(var2);
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onStartContinuityEvent)))).addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onTickContinuityEvent)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onEndContinuityEvent))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
	}
	private void onStartContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.CROSSED_ARMS);
		this.blockMovement(var1);
	}
	private void onTickContinuityEvent(LivingEntity var1, IAbility var2) {
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.SAMEHADA.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
	}
	private void onEndContinuityEvent(LivingEntity var1, IAbility var2) {
		this.removeMovementBlock(var1);
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,(100F) + ((this.continuousComponent.getContinueTime()) * (2F)));
	}
	public float getHoldTime() {
		return 200F;
	}
	public void onGuard(LivingEntity var1, DamageSource var2, float var3, float var4) {
		Entity var5 = var2.getEntity();
		if ((var5) == (null)){
			return;
		}else{
			float var6 = Math.max(15F,(var3) / (2F));
			if (this.dealDamageComponent.hurtTarget(var1,((LivingEntity)(var5)),var6)){
				var5.setDeltaMovement(var1.position().subtract(var5.position()).normalize().scale(-2D));
				var5.hurtMarked = true;
			}
			this.continuousComponent.stopContinuity(var1);
			this.cooldownComponent.startCooldown(var1,200F);
			return;
		}
	}
	public GuardAbility$GuardValue getGuard(LivingEntity var1) {
		return GUARD_VALUE;
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(9F,-8F));
		DorikiUnlockCondition var2 = DorikiUnlockCondition.atLeast(3000D);
		RequiredRaceUnlockCondition var3 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.FISHMAN.get())));
		NodeUnlockCondition var4 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).and(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(5F)))).or(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(7.5F))));
		NodeUnlockAction var5 = SubtractPointsAction.martialArts(5F).onlyIf(var3).andThen(SubtractPointsAction.martialArts(7.5F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).not())).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var6 = new AbilityNode[1];
		var6[0] = ((AbilityCore)(KachiageHaisokuAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var6);
		var1.setUnlockRule(var4,var5);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("The user concentrates their power to their palms, sending melee damage back to its owner and pushing them a few blocks back"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityCore$INodeFactory[] lv2_4 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv2_5 = SamehadaShoteiAbility::createNode;
			lv2_4[0] = lv2_5;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SamehadaShoteiAbility::new)))).addDescriptionLine(lv2_3).setNodeFactories(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("samehada_shotei","Samehada Shotei",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(100F,400F);
		};
		CooldownComponentTooltip.generate(var2,var3);
	}
}