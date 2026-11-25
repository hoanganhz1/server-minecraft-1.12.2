package xyz.pixelatedw.mineminenomi.abilities.electro;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.DashAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.DealDamageComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RangeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.chat.WyComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ElectricalMissileAbility extends DashAbility {
	// Fields
	private static final float COOLDOWN_BONUS = 0.5F;
	private static final float DAMAGE_BONUS = 2.5F;
	private static final int COOLDOWN = 180;
	private static final float RANGE = 1.6F;
	private static final int DAMAGE = 20;
	private static final int ELECLAW_STACKS = 1;
	private static final int DORIKI = 800;
	private static final int MARTIAL_ARTS_POINTS = 5;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ElectricalMissileAbility>> INSTANCE;
	private float dashSpeed;

	public ElectricalMissileAbility(AbilityCore<ElectricalMissileAbility> var1) {
		super((AbilityCore)(var1));
		this.dashSpeed = 4F;
		this.addCanUseCheck(ElectroHelper.requireEleclaw(1));
		this.continuousComponent.addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
	}
	public void onTargetHit(LivingEntity var1, LivingEntity var2, float var3, DamageSource var4) {
		var2.addEffect(new MobEffectInstance((Holder)(ModEffects.PARALYSIS),40,0,false,false,true));
	}
	public float getDashCooldown() {
		return 180F;
	}
	public float getDamage() {
		return 20F;
	}
	public float getRange() {
		return 1.6F;
	}
	public float getSpeed() {
		return this.dashSpeed;
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(9F,8F));
		DorikiUnlockCondition var2 = DorikiUnlockCondition.atLeast(800D);
		RequiredRaceUnlockCondition var3 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.MINK.get())));
		NodeUnlockCondition var4 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).and(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(5F)))).or(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(7.5F))));
		NodeUnlockAction var5 = SubtractPointsAction.martialArts(5F).onlyIf(var3).andThen(SubtractPointsAction.martialArts(7.5F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).not())).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var6 = new AbilityNode[1];
		var6[0] = ((AbilityCore)(EleclawAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var6);
		var1.setUnlockRule(var4,var5);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv5_2 = new Pair[2];
			((java.lang.Object[]) (lv5_2))[0] = ImmutablePair.of((Object)("Powerful and fast forward dash that will stun enemies."),(Object)(null));
			Object[] lv5_3 = new Object[3];
			lv5_3[0] = WyComponent.reference((Holder)(SulongAbility.INSTANCE));
			int lv5_4 = Math.round(50F);
			String lv5_5 = "" + lv5_4 + "%";
			lv5_3[1] = MentionHelper.mentionText(lv5_5);
			int lv5_6 = Math.round((Math.abs(-1.5F)) * (100F));
			String lv5_7 = "" + lv5_6 + "%";
			lv5_3[2] = MentionHelper.mentionText(lv5_7);
			((java.lang.Object[]) (lv5_2))[1] = ImmutablePair.of((Object)("While %s is active the cooldown of this ability is reduced by %s and the damage is increased by %s."),lv5_3);
			Component[] lv5_8 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv5_2);
			Component[] lv5_9 = new Component[]{lv5_8[0]};
			AbilityDescriptionLine$IDescriptionLine[] lv5_10 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine$IDescriptionLine.of(lv5_8[1])};
			SourceType[] lv5_11 = new SourceType[]{SourceType.FIST};
			AbilityCore$INodeFactory[] lv5_12 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv5_13 = ElectricalMissileAbility::createNode;
			lv5_12[0] = lv5_13;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ElectricalMissileAbility::new)))).addDescriptionLine(lv5_9).addAdvancedDescriptionLine(lv5_10).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv5_11).setSourceElement(SourceElement.LIGHTNING).setNodeFactories(lv5_12).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("electrical_missile","Electrical Missile",var0));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		boolean var3 = ElectroHelper.hasSulongActive(var1);
		this.dashSpeed = (var3) ? (5F) : (4F);
		this.dealDamageComponent.getBonusManager().removeBonus(ElectroHelper.SULONG_DAMAGE_BONUS);
		this.cooldownComponent.getBonusManager().removeBonus(ElectroHelper.SULONG_COOLDOWN_BONUS);
		if (var3){
			this.dealDamageComponent.getBonusManager().addBonus(ElectroHelper.SULONG_DAMAGE_BONUS,"Sulong Damage Bonus",BonusOperation.MUL,2.5F);
			this.cooldownComponent.getBonusManager().addBonus(ElectroHelper.SULONG_COOLDOWN_BONUS,"Sulong Cooldown Bonus",BonusOperation.MUL,0.5F);
		}
		var1.swing(InteractionHand.MAIN_HAND,true);
		Function<AbilityData, EleclawAbility> var4 = (l0) -> {
			return ((EleclawAbility)(l0.getEquippedAbility(((AbilityCore)(EleclawAbility.INSTANCE.get())))));
		};
		EleclawAbility var5 = ((EleclawAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var4)).orElse(null)));
		if ((var5) != (null)){
			var5.reduceUsage(var1,1);
		}
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(180F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<RangeComponentTooltip> var4 = (l0) -> {
			l0.range(1.6F,RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var4);
		Consumer<DealDamageComponentTooltip> var5 = (l0) -> {
			l0.damage(20F);
		};
		DealDamageComponentTooltip.generate(var2,var5);
	}
}