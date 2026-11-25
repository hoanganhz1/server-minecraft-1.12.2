package xyz.pixelatedw.mineminenomi.abilities.doctor;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
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
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DopingAbility extends PunchAbility {
	// Fields
	private static final int COOLDOWN = 500;
	private static final int EFFECT_TIME = 200;
	private static final int NODE_POINTS_TE = 3;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DopingAbility>> INSTANCE;

	public DopingAbility(AbilityCore<DopingAbility> var1) {
		super((AbilityCore)(var1));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresMedicBag))));
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.isCrouching()){
			this.applyEffect(var1,var1);
			return;
		}else{
			return;
		}
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		this.applyEffect(var1,var2);
		return true;
	}
	private void applyEffect(LivingEntity var1, LivingEntity var2) {
		var2.addEffect(new MobEffectInstance((Holder)(ModEffects.TENSION_HORMONE),200,1));
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.FIRST_AID.get())),(Entity)(var1),var2.getX(),var2.getY(),var2.getZ());
		super.consumeUseAndStop();
	}
	public float getPunchCooldown() {
		return 500F;
	}
	public int getUseLimit() {
		return 1;
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-15F,-2F));
		boolean var2 = ((Boolean)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isDoctor)))).orElse(Boolean.valueOf(false)))).booleanValue();
		TrainingPointsUnlockCondition var3 = TrainingPointsUnlockCondition.technology(3F);
		NodeUnlockAction var4 = SubtractPointsAction.technology(3F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		if (!(var2)){
			AbilityNode[] var5 = new AbilityNode[1];
			var5[0] = ((AbilityCore)(AntidoteShotAbility.INSTANCE.get())).getNode(var0);
			var1.addPrerequisites(var5);
		}
		var1.setUnlockRule(var3,var4);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Injects the target with special medicine that boosts their physical powers."),(Object)(null)),ImmutablePair.of((Object)("§aSHIFT-USE§r: User injects themselves"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST,SourceType.FRIENDLY};
			AbilityCore$INodeFactory[] lv1_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv1_6 = DopingAbility::createNode;
			lv1_5[0] = lv1_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DopingAbility::new)))).addDescriptionLine(lv1_3).setSourceType(lv1_4).setNodeFactories(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("doping","Doping",var0));
	}
}