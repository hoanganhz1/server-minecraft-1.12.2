package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class AntiMannerKickCourseAbility extends PunchAbility {
	// Fields
	private static final float COOLDOWN = 240F;
	private static final int NODE_POINTS_MA = 3;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<AntiMannerKickCourseAbility>> INSTANCE;
	private final AnimationComponent animationComponent;

	public AntiMannerKickCourseAbility(AbilityCore<AntiMannerKickCourseAbility> var1) {
		super((AbilityCore)(var1));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.animationComponent};
		this.addComponents(var2);
		this.clearUseChecks();
	}
	public float getPunchDamage() {
		return 15F;
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		this.animationComponent.start(var1,ModAnimations.ANTI_MANNER,7);
		AbilityHelper.setDeltaMovement((Entity)(var2),var2.getDeltaMovement().add(0D,1.2000000000000002D,0D));
		var2.addEffect(new MobEffectInstance(MobEffects.CONFUSION,40,0,false,false));
		var2.addEffect(new MobEffectInstance((Holder)(ModEffects.DIZZY),40,0,false,false));
		return true;
	}
	public int getUseLimit() {
		return 1;
	}
	public float getPunchCooldown() {
		return 240F;
	}
	public boolean isParallel() {
		return true;
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-9F,9F));
		RequiredStyleUnlockCondition var2 = RequiredStyleUnlockCondition.requires(((FightingStyle)(ModFightingStyles.BLACK_LEG.get())));
		NodeUnlockCondition var3 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).or((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(3F)));
		var1.setUnlockRule(var3,SubtractPointsAction.martialArts(3F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).not()).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE))));
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Kicks an enemy and launches them vertically"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST};
			AbilityCore$INodeFactory[] lv1_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv1_6 = AntiMannerKickCourseAbility::createNode;
			lv1_5[0] = lv1_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(AntiMannerKickCourseAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_4).setNodeFactories(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("antimanner_kick_course","Anti-Manner Kick Course",var0));
	}
}