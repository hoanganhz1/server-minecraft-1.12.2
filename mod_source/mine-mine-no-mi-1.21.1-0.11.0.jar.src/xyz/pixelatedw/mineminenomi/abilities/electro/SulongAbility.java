package xyz.pixelatedw.mineminenomi.abilities.electro;

import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$OverlayPart;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ChangeStatsComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SulongAbility extends Ability {
	// Fields
	private static final int MIN_COOLDOWN = 100;
	private static final int MAX_COOLDOWN = 1200;
	private static final Color COLOR = WyHelper.hexToRGB("#B0E9F255");
	private static final int DORIKI = 1200;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SulongAbility>> INSTANCE;
	private static final AbilityOverlay OVERLAY;
	private static final AttributeModifier SPEED_MODIFIER;
	private static final AttributeModifier STRENGTH_MODIFIER;
	private static final AttributeModifier JUMP_MODIFIER;
	private static final AttributeModifier FALL_RESISTANCE;
	private static final AttributeModifier STEP_HEIGHT;
	private final ContinuousComponent continuousComponent;
	private final ChangeStatsComponent statsComponent;
	private final SkinOverlayComponent skinOverlayComponent;

	public SulongAbility(AbilityCore<SulongAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.statsComponent = new ChangeStatsComponent((IAbility)(this));
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.statsComponent,this.skinOverlayComponent};
		this.addComponents(var2);
		this.statsComponent.addAttributeModifier(Attributes.MOVEMENT_SPEED,SPEED_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE,STRENGTH_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT,JUMP_MODIFIER);
		this.statsComponent.addAttributeModifier(ModAttributes.FALL_RESISTANCE,FALL_RESISTANCE);
		this.statsComponent.addAttributeModifier(Attributes.STEP_HEIGHT,STEP_HEIGHT);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(ElectroHelper::canTransformInSulong))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.statsComponent.applyModifiers(var1);
		this.skinOverlayComponent.showAll(var1);
	}
	private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((!(var1.level().isClientSide)) && (ElectroHelper.canTransformInSulong(var1,var2).isFail())){
			this.continuousComponent.stopContinuity(var1);
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.statsComponent.removeModifiers(var1);
		this.skinOverlayComponent.hideAll(var1);
		float var3 = Math.max(100F,(this.continuousComponent.getContinueTime()) * (2F));
		this.cooldownComponent.startCooldown(var1,var3);
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(7F,6F));
		NodeUnlockCondition var2 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.MINK.get()))).and(DorikiUnlockCondition.atLeast(1200D));
		NodeUnlockAction var3 = UnlockAbilityAction.unlock((Supplier)(INSTANCE)).andThen(UnlockAbilityAction.unlock((Supplier)(SulongCheckAbility.INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(EleclawAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[1];
			Object[] lv4_3 = new Object[]{MentionHelper.tryMention(EleclawAbility.INSTANCE)};
			((java.lang.Object[]) (lv4_2))[0] = ImmutablePair.of((Object)("The user reveals their true power during the full moon, enhancing their physical and electrical power. While active %s stacks are not consumed."),lv4_3);
			Component[] lv4_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			AbilityCore$INodeFactory[] lv4_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv4_6 = SulongAbility::createNode;
			lv4_5[0] = lv4_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SulongAbility::new)))).addDescriptionLine(lv4_4).setNodeFactories(lv4_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("sulong","Sulong",var0));
		OVERLAY = new AbilityOverlay$Builder().setOverlayPart(AbilityOverlay$OverlayPart.BODY).setColor(COLOR).build();
		SPEED_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","sulong.speed"),1.5D,AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
		STRENGTH_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","sulong.damage"),10D,AttributeModifier.Operation.ADD_VALUE);
		JUMP_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","sulong.jump"),1.05D,AttributeModifier.Operation.ADD_VALUE);
		FALL_RESISTANCE = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","sulong.fall_resistance"),6.25D,AttributeModifier.Operation.ADD_VALUE);
		STEP_HEIGHT = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","sulong.step_height"),1D,AttributeModifier.Operation.ADD_VALUE);
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(100F,1200F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ContinuousComponentTooltip> var4 = (l0) -> {
		};
		ContinuousComponentTooltip.generate(var2,var4);
		Consumer<ChangeStatsComponentTooltip> var5 = (l0) -> {
		};
		ChangeStatsComponentTooltip.generate(var2,var5);
	}
}