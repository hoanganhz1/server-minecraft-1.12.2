package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KamisoriAbility extends Ability {
	// Fields
	private static final float HOLD_TIME = 40F;
	private static final float COOLDOWN = 300F;
	private static final int DORIKI = 1750;
	private static final int MARTIAL_ARTS_POINTS = 3;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KamisoriAbility>> INSTANCE;
	private final PoolComponent poolComponent;
	private final DamageTakenComponent damageTakenComponent;
	private final ContinuousComponent continuousComponent;
	private final AnimationComponent animationComponent;
	private final Interval zigzagInterval;
	private boolean hasFallDamage;
	private boolean isZig;

	public KamisoriAbility(AbilityCore<KamisoriAbility> var1) {
		super((AbilityCore)(var1));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GEPPO_LIKE,new AbilityPool[0]);
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.zigzagInterval = Interval.startAtMax(4);
		this.hasFallDamage = true;
		this.isZig = true;
		AbilityComponent[] var2 = new AbilityComponent[]{this.poolComponent,this.damageTakenComponent,this.continuousComponent,this.animationComponent};
		super.addComponents(var2);
		super.setOGCD();
		super.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		super.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
		super.addTickEvent((Ability$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent)(this::onTickEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.hasFallDamage = false;
		this.isZig = true;
		this.zigzagInterval.restartIntervalToMax();
		this.continuousComponent.triggerContinuity(var1,40F);
	}
	public void onTickEvent(LivingEntity var1, IAbility var2) {
		Level var3 = var1.level();
		if ((!(var3.isClientSide)) && ((!(this.hasFallDamage)) && ((var1.onGround()) && ((!(this.continuousComponent.isContinuous())) && ((var3.getGameTime()) > ((super.getLastUseGametime()) + (10L))))))){
			this.hasFallDamage = true;
		}
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if (!(this.zigzagInterval.canTick())){
			return;
		}else{
			this.animationComponent.start(var1,ModAnimations.GEPPO,4);
			AbilityHelper.setDeltaMovement((Entity)(var1),VectorHelper.getRelativeOffset(Vec3.ZERO,var1.getYRot(),var1.getXRot(),new Vec3(0D,(this.isZig) ? (1D) : (-1D),1D)),true);
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.VANISH),this.zigzagInterval.getInterval(),0,false,false));
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.TELEPORT_SFX.get())),SoundSource.PLAYERS,2F,1F);
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.GEPPO_SFX.get())),SoundSource.PLAYERS,2F,(0.75F) + ((this.random.nextFloat()) / (3F)));
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GEPPO.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			this.isZig = !(this.isZig);
			return;
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,300F);
	}
	private float onDamageTaken(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if (((this.hasFallDamage) && (!(this.continuousComponent.isContinuous()))) || ((var3) != (var1.damageSources().fall()))){
			return var4;
		}else{
			this.continuousComponent.stopContinuity(var1);
			return 0F;
		}
	}
	public void saveAdditional(CompoundTag var1) {
		var1.putBoolean("hasFallDamage",this.hasFallDamage);
		var1.putBoolean("isZig",this.isZig);
	}
	public void loadAdditional(CompoundTag var1) {
		this.hasFallDamage = var1.getBoolean("hasFallDamage");
		this.isZig = var1.getBoolean("isZig");
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(10F,-2F));
		AbilityNode[] var2 = new AbilityNode[2];
		var2[0] = ((AbilityCore)(GeppoAbility.INSTANCE.get())).getNode(var0);
		var2[1] = ((AbilityCore)(SoruAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var2);
		DorikiUnlockCondition var3 = DorikiUnlockCondition.atLeast(1750D);
		RequiredRaceUnlockCondition var4 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.HUMAN.get())));
		NodeUnlockCondition var5 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var4)).and(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(3F)))).or(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(4.5F))));
		var1.setUnlockRule(var5,SubtractPointsAction.martialArts(3F).onlyIf(var4).andThen(SubtractPointsAction.martialArts(4.5F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var4)).not())));
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("A combination of Geppo and Soru, where the user uses Soru in a zigzag motion in midair, allowing extremely fast movements in three dimensions."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityCore$INodeFactory[] lv1_4 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv1_5 = KamisoriAbility::createNode;
			lv1_4[0] = lv1_5;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KamisoriAbility::new)))).addDescriptionLine(lv1_3).setNodeFactories(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kamisori","Kamisori",var0));
	}
}