package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
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
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GeppoAbility extends Ability {
	// Fields
	private static final int MIN_JUMPS = 3;
	private static final int MAX_JUMPS = 6;
	private static final float SHORT_COOLDOWN = 10F;
	private static final float LONG_COOLDOWN = 50F;
	private static final int STACKS = 6;
	private static final int DORIKI = 1250;
	private static final int MARTIAL_ARTS_POINTS = 5;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GeppoAbility>> INSTANCE;
	private final PoolComponent poolComponent;
	private final DamageTakenComponent damageTakenComponent;
	private final StackComponent stackComponent;
	private final AnimationComponent animationComponent;
	private boolean hasFallDamage;
	private int ticksOnGround;

	public GeppoAbility(AbilityCore<GeppoAbility> var1) {
		super((AbilityCore)(var1));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GEPPO_LIKE,new AbilityPool[0]);
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		this.stackComponent = new StackComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.hasFallDamage = true;
		AbilityComponent[] var2 = new AbilityComponent[]{this.poolComponent,this.damageTakenComponent,this.stackComponent,this.animationComponent};
		this.addComponents(var2);
		this.setOGCD();
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
		this.addTickEvent((Ability$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent)(this::tickEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.stackComponent.setDefaultStacks(this.getMaxJumps(var1));
		Vec3 var4 = var1.getLookAngle();
		Vec3 var5 = null;
		double var6 = 0.0;
		int var7 = 0;
		if (var1.isInWater()){
			var5 = var4.multiply(2D,2D,2D);
			var6 = var5.y;
			var7 = this.stackComponent.getStacks();
		}else{
			if (var1.onGround()){
				var5 = var4.multiply(1D,1D,1D);
				var6 = 1.86D;
			}else{
				var5 = var4.multiply(1.5D,1.5D,1.5D);
				var6 = 1.25D;
			}
			var7 = 1;
		}
		AbilityHelper.setDeltaMovement((Entity)(var1),var5.x,var6,var5.z);
		this.stackComponent.addStacks(var1,this,-(var7));
		this.hasFallDamage = false;
		this.ticksOnGround = 0;
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.GEPPO_SFX.get())),SoundSource.PLAYERS,2F,(0.75F) + ((this.random.nextFloat()) / (3F)));
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GEPPO.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		this.animationComponent.start(var1,ModAnimations.GEPPO,7);
		if ((this.stackComponent.getStacks()) <= (0)){
			this.cooldownComponent.startCooldown(var1,this.getCooldownTicks());
			this.stackComponent.setStacks(var1,this,this.getMaxJumps(var1));
		}else{
			this.cooldownComponent.startCooldown(var1,10F);
		}
	}
	private float onDamageTaken(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if ((!(this.hasFallDamage)) && (var3.is(DamageTypes.FALL))){
			this.resetStacks(var1);
			return 0F;
		}else{
			return var4;
		}
	}
	public void tickEvent(LivingEntity var1, IAbility var2) {
		if (((this.getLastUseGametime()) <= (0L)) && ((this.stackComponent.getDefaultStacks()) <= (0))){
			this.stackComponent.setDefaultStacks(this.getMaxJumps(var1));
			this.stackComponent.revertStacksToDefault(var1,this);
		}
		if ((!(var1.level().isClientSide)) && ((!(this.hasFallDamage)) && (((this.stackComponent.getStacks()) < (this.stackComponent.getDefaultStacks())) && (var1.onGround())))){
			this.ticksOnGround = (this.ticksOnGround) + (1);
			if ((this.ticksOnGround) > (10)){
				this.resetStacks(var1);
			}else{
				return;
			}
		}
	}
	private void resetStacks(LivingEntity var1) {
		if ((this.stackComponent.getStacks()) != (this.stackComponent.getDefaultStacks())){
			this.cooldownComponent.stopCooldown(var1);
			this.cooldownComponent.startCooldown(var1,this.getCooldownTicks());
		}
		this.stackComponent.setStacks(var1,this,this.getMaxJumps(var1));
		this.stackComponent.setDefaultStacks(this.getMaxJumps(var1));
		this.hasFallDamage = true;
	}
	private int getMaxJumps(LivingEntity var1) {
		return 6;
	}
	private float getCooldownTicks() {
		return ((float)((this.stackComponent.getDefaultStacks()) - (this.stackComponent.getStacks()))) * (50F);
	}
	public void saveAdditional(CompoundTag var1) {
		var1.putBoolean("hasFallDamage",this.hasFallDamage);
	}
	public void loadAdditional(CompoundTag var1) {
		this.hasFallDamage = var1.getBoolean("hasFallDamage");
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(8F,-2F));
		DorikiUnlockCondition var2 = DorikiUnlockCondition.atLeast(1250D);
		RequiredRaceUnlockCondition var3 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.HUMAN.get())));
		NodeUnlockCondition var4 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).and(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(5F)))).or(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(7.5F))));
		var1.setUnlockRule(var4,SubtractPointsAction.martialArts(5F).onlyIf(var3).andThen(SubtractPointsAction.martialArts(7.5F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).not())).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE))));
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("The user kicks the air beneath them to launch themselves into the air"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityCore$INodeFactory[] lv2_4 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv2_5 = GeppoAbility::createNode;
			lv2_4[0] = lv2_5;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GeppoAbility::new)))).addDescriptionLine(lv2_3).setNodeFactories(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("geppo","Geppo",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<StackComponentTooltip> var3 = (l0) -> {
			l0.stacks(6F);
		};
		StackComponentTooltip.generate(var2,var3);
		AbilityTooltipsHelper.generateShortLongCooldownStat(var2,this,10F,50F);
	}
}