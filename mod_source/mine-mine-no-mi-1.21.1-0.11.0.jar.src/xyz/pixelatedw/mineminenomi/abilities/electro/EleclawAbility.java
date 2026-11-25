package xyz.pixelatedw.mineminenomi.abilities.electro;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.StackComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class EleclawAbility extends PunchAbility {
	// Fields
	private static final int MAX_STACKS = 9;
	private static final int MIN_COOLDOWN = 50;
	private static final int COOLDOWN_PER_STACK = 20;
	private static final int MAX_COOLDOWN = 180;
	private static final int DORIKI = 500;
	private static final int MARTIAL_ARTS_POINTS = 3;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<EleclawAbility>> INSTANCE;
	private static final AttributeModifier ATTACK_SPEED_MODIFIER;
	private final StackComponent stackComponent;

	public EleclawAbility(AbilityCore<EleclawAbility> var1) {
		super((AbilityCore)(var1));
		this.stackComponent = new StackComponent((IAbility)(this),9);
		AbilityComponent[] var2 = new AbilityComponent[]{this.stackComponent};
		this.addComponents(var2);
		this.statsComponent.addAttributeModifier(Attributes.ATTACK_SPEED,ATTACK_SPEED_MODIFIER);
		this.continuousComponent.addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent))));
		this.continuousComponent.addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((!(var1.level().isClientSide)) && (!((!(var1.isEyeInFluidType(((FluidType)(NeoForgeMod.WATER_TYPE.value()))))) && ((this.stackComponent.getStacks()) != (0))))){
			this.continuousComponent.stopContinuity(var1);
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.consumeUseAndStop();
		this.stackComponent.revertStacksToDefault(var1,this);
	}
	public float getPunchDamage() {
		return 5F;
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		if ((var1.getRandom().nextInt(10)) < (1)){
			var2.addEffect(new MobEffectInstance((Holder)(ModEffects.PARALYSIS),10,0,false,false,true));
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GENERIC_LIGHTNING_USE.get())),(Entity)(var1),var2.getX(),var2.getY(),var2.getZ());
		}
		return true;
	}
	public int getUseLimit() {
		return -1;
	}
	public float getPunchCooldown() {
		return (float)(Math.max(50,((9) - (this.stackComponent.getStacks())) * (20)));
	}
	public boolean isParallel() {
		return true;
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(7F,8F));
		DorikiUnlockCondition var2 = DorikiUnlockCondition.atLeast(500D);
		RequiredRaceUnlockCondition var3 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.MINK.get())));
		NodeUnlockCondition var4 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).and(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(3F)))).or(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(15F))));
		var1.setUnlockRule(var4,SubtractPointsAction.martialArts(3F).onlyIf(var3).andThen(SubtractPointsAction.martialArts(15F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).not())).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE))));
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("The user coats their hands and weapons with lightning, enabling the use of other electric skills and giving the chance to stun foes."),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			SourceType[] lv4_4 = new SourceType[]{SourceType.PHYSICAL};
			AbilityCore$INodeFactory[] lv4_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv4_6 = EleclawAbility::createNode;
			lv4_5[0] = lv4_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(EleclawAbility::new)))).addDescriptionLine(lv4_3).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv4_4).setSourceElement(SourceElement.LIGHTNING).setNodeFactories(lv4_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("eleclaw","Eleclaw",var0));
		ATTACK_SPEED_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","eleclaw.attack_speed"),0.3499999940395355D,AttributeModifier.Operation.ADD_VALUE);
	}
	public void reduceUsage(LivingEntity var1, int var2) {
		if (!(var1.level().isClientSide)){
			Function<AbilityData, SulongAbility> var3 = (l0) -> {
			return ((SulongAbility)(l0.getEquippedAbility(((AbilityCore)(SulongAbility.INSTANCE.get())))));
		};
			SulongAbility var4 = ((SulongAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)).orElse(null)));
			if (((((var4) != (null)) && (var4.isContinuous())) ? (1) : (0)) == (0)){
				int var5 = (this.stackComponent.getStacks()) - (var2);
				this.stackComponent.setStacks(var1,this,var5);
			}else{
				return;
			}
		}
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(50F,180F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<StackComponentTooltip> var4 = (l0) -> {
			l0.stacks(9F);
		};
		StackComponentTooltip.generate(var2,var4);
	}
}