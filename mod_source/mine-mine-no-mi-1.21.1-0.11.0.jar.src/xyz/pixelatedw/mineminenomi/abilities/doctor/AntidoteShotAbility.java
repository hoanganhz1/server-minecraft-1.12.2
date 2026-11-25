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

public class AntidoteShotAbility extends PunchAbility {
	// Fields
	private static final int COOLDOWN = 300;
	private static final int EFFECT_TIME = 200;
	private static final int NODE_POINTS_TE = 5;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<AntidoteShotAbility>> INSTANCE;

	public AntidoteShotAbility(AbilityCore<AntidoteShotAbility> var1) {
		super((AbilityCore)(var1));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresMedicBag))));
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.isCrouching()){
			this.applyAntidoteEffect(var1,var1);
			return;
		}else{
			return;
		}
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		this.applyAntidoteEffect(var1,var2);
		return true;
	}
	private void applyAntidoteEffect(LivingEntity var1, LivingEntity var2) {
		var2.addEffect(new MobEffectInstance((Holder)(ModEffects.ANTIDOTE),200,0));
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.FIRST_AID.get())),(Entity)(var1),var2.getX(),var2.getY(),var2.getZ());
		super.consumeUseAndStop();
	}
	public float getPunchCooldown() {
		return 300F;
	}
	public int getUseLimit() {
		return 1;
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-9F,-2F));
		boolean var2 = ((Boolean)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isDoctor)))).orElse(Boolean.valueOf(false)))).booleanValue();
		TrainingPointsUnlockCondition var3 = TrainingPointsUnlockCondition.technology(5F);
		NodeUnlockAction var4 = SubtractPointsAction.technology(5F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		if (!(var2)){
			AbilityNode[] var5 = new AbilityNode[1];
			var5[0] = ((AbilityCore)(FirstAidAbility.INSTANCE.get())).getNode(var0);
			var1.addPrerequisites(var5);
		}
		var1.setUnlockRule(var3,var4);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Injects the target with an antidote making them immune to certain negative effects."),(Object)(null)),ImmutablePair.of((Object)("§aSHIFT-USE§r: User injects themselves"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FRIENDLY};
			AbilityCore$INodeFactory[] lv1_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv1_6 = AntidoteShotAbility::createNode;
			lv1_5[0] = lv1_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(AntidoteShotAbility::new)))).addDescriptionLine(lv1_3).setSourceType(lv1_4).setNodeFactories(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("antidote_shot","Antidote Shot",var0));
	}
}