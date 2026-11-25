package xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ChangeStatsComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class HeatEggTempo extends ChargedTempoAbility {
	// Fields
	private static final int HOLD_TIME = 300;
	private static final int TECHNOLOGY_POINTS = 5;
	private static final int WEAPON_MASTERY_POINTS = 5;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<HeatEggTempo>> INSTANCE;
	private static final AttributeModifier DAMAGE_MODIFIER;
	private final ContinuousComponent continuousComponent;
	private final ChangeStatsComponent changeStatsComponent;
	private ItemStack climaTactRef;

	public HeatEggTempo(AbilityCore<HeatEggTempo> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuousEvent)))).addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuousEvent)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuousEvent))));
		this.changeStatsComponent = new ChangeStatsComponent((IAbility)(this));
		this.climaTactRef = ItemStack.EMPTY;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.changeStatsComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresClimaTact))));
	}
	public WeatherBallKind[] getTempoOrder() {
		WeatherBallKind[] var1 = new WeatherBallKind[]{WeatherBallKind.HEAT,WeatherBallKind.HEAT,WeatherBallKind.HEAT};
		return var1;
	}
	public void useTempo(LivingEntity var1) {
		this.continuousComponent.startContinuity(var1);
	}
	private void startContinuousEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			this.climaTactRef = var1.getMainHandItem();
			this.changeStatsComponent.addAttributeModifier(Attributes.ATTACK_DAMAGE,DAMAGE_MODIFIER);
			this.changeStatsComponent.applyModifiers(var1);
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.HEAT_EGG_TEMPO),300,0));
			return;
		}
	}
	private void tickContinuousEvent(LivingEntity var1, IAbility var2) {
		if ((this.continuousComponent.getContinueTime()) >= (300F)){
			ItemStack var3 = var1.getMainHandItem();
			if ((!(this.climaTactRef.isEmpty())) && (((java.lang.Object)(var3)).equals((Object)(this.climaTactRef)))){
				this.continuousComponent.stopContinuity(var1);
			}else{
				if (!(var3.isEmpty())){
					this.climaTactRef = var1.getMainHandItem();
				}else{
					return;
				}
			}
		}
	}
	private void endContinuousEvent(LivingEntity var1, IAbility var2) {
		this.changeStatsComponent.removeModifiers(var1);
		this.climaTactRef = ItemStack.EMPTY;
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-11F,8F));
		NodeUnlockCondition var2 = TrainingPointsUnlockCondition.technology(5F).and(TrainingPointsUnlockCondition.weaponMastery(5F));
		NodeUnlockAction var3 = SubtractPointsAction.technology(5F).andThen(SubtractPointsAction.weaponMastery(5F)).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(MirageTempo.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[]{ImmutablePair.of((Object)("Used to imbue the Clima Tact with Heat Balls, while active it doubles the physical damage dealt by the clima tact"),(Object)(null))};
			Component[] lv3_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			SourceType[] lv3_4 = new SourceType[]{SourceType.TECHNOLOGY};
			AbilityCore$INodeFactory[] lv3_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv3_6 = HeatEggTempo::createNode;
			lv3_5[0] = lv3_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HeatEggTempo::new)))).setIcon(ModResources.TEMPO_ICON).addDescriptionLine(lv3_3).setSourceType(lv3_4).setNodeFactories(lv3_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("heat_egg_tempo","Heat Egg Tempo",var0));
		DAMAGE_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","heat_egg.attack_damage"),2D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<ContinuousComponentTooltip> var3 = (l0) -> {
			l0.holdTime(300F);
		};
		ContinuousComponentTooltip.generate(var2,var3);
		Consumer<ChangeStatsComponentTooltip> var4 = (l0) -> {
		};
		ChangeStatsComponentTooltip.generate(var2,var4);
	}
}