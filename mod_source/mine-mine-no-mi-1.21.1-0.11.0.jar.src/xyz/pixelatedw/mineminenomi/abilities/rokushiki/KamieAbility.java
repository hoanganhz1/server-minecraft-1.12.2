package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
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
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class KamieAbility extends Ability {
	// Fields
	private static final float HOLD_TIME = 60F;
	private static final float MIN_COOLDOWN = 100F;
	private static final float MAX_COOLDOWN = 400F;
	private static final float PROTECTION_TIME = 10F;
	private static final int DORIKI = 3600;
	private static final int MARTIAL_ARTS_POINTS = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KamieAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final PoolComponent poolComponent;
	private final DamageTakenComponent damageTakenComponent;
	private final AnimationComponent animationComponent;
	private int hitsTaken;
	private float protTimer;

	public KamieAbility(AbilityCore<KamieAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onStartContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onEndContinuityEvent))));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.DODGE_ABILITY,new AbilityPool[0]);
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTakenEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.hitsTaken = 0;
		this.protTimer = 10F;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.poolComponent,this.damageTakenComponent,this.animationComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,60F);
	}
	private void onStartContinuityEvent(LivingEntity var1, IAbility var2) {
		this.hitsTaken = 0;
		this.protTimer = 0F;
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((this.protTimer) > (0F)){
			this.protTimer = (this.protTimer) - (1F);
		}
	}
	private void onEndContinuityEvent(LivingEntity var1, IAbility var2) {
		float var3 = Math.min(400F,((100F) + ((this.continuousComponent.getContinueTime()) * (5F))) + ((float)((this.hitsTaken) * (5))));
		this.cooldownComponent.startCooldown(var1,var3);
	}
	public float onDamageTakenEvent(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if ((super.isContinuous()) && (!(AbilityUseConditions.canUseMomentumAbilities(var1,this).isFail()))){
			IDamageSourceHandler var5 = IDamageSourceHandler.getHandler(var3);
			boolean var6 = var5.isUnavoidable();
			int var7 = ((var5.hasElement(SourceElement.EXPLOSION)) || (var5.hasElement(SourceElement.SHOCKWAVE))) ? (1) : (0);
			if ((!(var6)) && ((var7) == (0))){
				boolean var8 = true;
				if ((!(var3.is(DamageTypes.MOB_ATTACK))) && ((!(var3.is(DamageTypes.PLAYER_ATTACK))) && (!(var3.is(DamageTypeTags.IS_PROJECTILE))))){
					if ((!(var5.hasType(SourceType.BLUNT))) && ((!(var5.hasType(SourceType.SLASH))) && ((!(var5.hasType(SourceType.FIST))) && ((!(var5.hasType(SourceType.PHYSICAL))) && ((!(var5.hasType(SourceType.PROJECTILE))) && ((!(var5.hasType(SourceType.INDIRECT))) && (!(var5.hasType(SourceType.BULLET))))))))){

					}else{
						var8 = false;
					}
				}else{
					var8 = false;
				}
				if ((this.protTimer) <= (0F)){
					if (!(var8)){
						SoundEvent var9 = ((SoundEvent)(ModSounds.DODGE_1.get()));
						if (var1.getRandom().nextBoolean()){
							var9 = ((SoundEvent)(ModSounds.DODGE_2.get()));
						}
						this.animationComponent.start(var1,ModAnimations.DODGE,5);
						var1.level().playSound((Player)(null),var1.blockPosition(),var9,SoundSource.PLAYERS,1F,(0.75F) + ((var1.getRandom().nextFloat()) / (2F)));
						this.hitsTaken = (this.hitsTaken) + (1);
						this.protTimer = 10F;
					}
					return (var8) ? (var4) : (0F);
				}else{
					return 0F;
				}
			}else{
				return var4;
			}
		}else{
			return var4;
		}
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(8F,2F));
		DorikiUnlockCondition var2 = DorikiUnlockCondition.atLeast(3600D);
		RequiredRaceUnlockCondition var3 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.HUMAN.get())));
		NodeUnlockCondition var4 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).and(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(10F)))).or(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(15F))));
		var1.setUnlockRule(var4,SubtractPointsAction.martialArts(10F).onlyIf(var3).andThen(SubtractPointsAction.martialArts(15F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).not())).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE))));
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[]{ImmutablePair.of((Object)("Makes the user's body flexible like paper in order to avoid attacks"),(Object)(null))};
			Component[] lv3_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			AbilityCore$INodeFactory[] lv3_4 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv3_5 = KamieAbility::createNode;
			lv3_4[0] = lv3_5;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KamieAbility::new)))).addDescriptionLine(lv3_3).setNodeFactories(lv3_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kamie","Kami-E",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(100F,400F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ContinuousComponentTooltip> var4 = (l0) -> {
			l0.holdTime(60F);
		};
		ContinuousComponentTooltip.generate(var2,var4);
	}
}