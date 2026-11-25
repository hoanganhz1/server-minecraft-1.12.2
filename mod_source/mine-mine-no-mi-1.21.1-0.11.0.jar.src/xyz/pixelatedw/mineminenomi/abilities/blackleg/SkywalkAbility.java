package xyz.pixelatedw.mineminenomi.abilities.blackleg;

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
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SkywalkAbility extends Ability {
	// Fields
	private static final int STACKS = 6;
	private static final float SHORT_COOLDOWN = 10F;
	private static final float LONG_COOLDOWN = 50F;
	private static final int NODE_POINTS_MA = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SkywalkAbility>> INSTANCE;
	private final PoolComponent poolComponent;
	private final DamageTakenComponent damageTakenComponent;
	private final StackComponent stackComponent;
	private final AnimationComponent animationComponent;
	private boolean hasFallDamage;
	private int ticksOnGround;
	private boolean hadGravity;
	private int noGravityTime;

	public SkywalkAbility(AbilityCore<SkywalkAbility> var1) {
		super((AbilityCore)(var1));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GEPPO_LIKE,new AbilityPool[0]);
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		this.stackComponent = new StackComponent((IAbility)(this),6);
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.hasFallDamage = true;
		this.hadGravity = false;
		this.noGravityTime = 8;
		this.setOGCD();
		AbilityComponent[] var2 = new AbilityComponent[]{this.poolComponent,this.damageTakenComponent,this.stackComponent,this.animationComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
		this.addTickEvent((Ability$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent)(this::tickEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		int var3 = 1;
		if ((this.noGravityTime) <= (0)){
			this.hadGravity = !(var1.isNoGravity());
		}
		Vec3 var4 = var1.getLookAngle().normalize();
		if (var1.isInWater()){
			var4 = var4.scale(2.5D);
			var3 = this.stackComponent.getStacks();
		}else{
			var4 = var4.scale((var1.onGround()) ? (2D) : (1.5D));
		}
		var1.setNoGravity(true);
		this.noGravityTime = 8;
		if ((var1.getXRot()) < (-40F)){
			var4 = var4.add(0D,-((var4.y) - ((var4.y) / (2D))),0D);
		}
		AbilityHelper.setDeltaMovement((Entity)(var1),var4.x,var4.y,var4.z);
		this.stackComponent.addStacks(var1,this,-(var3));
		this.hasFallDamage = false;
		this.ticksOnGround = 0;
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.GEPPO_SFX.get())),SoundSource.PLAYERS,2F,(0.75F) + ((this.random.nextFloat()) / (3F)));
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GEPPO.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		this.animationComponent.start(var1,ModAnimations.GEPPO,7);
		if ((this.stackComponent.getStacks()) <= (0)){
			this.cooldownComponent.startCooldown(var1,this.getCooldownTicks());
			this.stackComponent.setStacks(var1,this,6);
		}else{
			this.cooldownComponent.startCooldown(var1,10F);
		}
	}
	public void tickEvent(LivingEntity var1, IAbility var2) {
		if ((!(var1.level().isClientSide)) && ((!(this.hasFallDamage)) && (((this.stackComponent.getStacks()) < (this.stackComponent.getDefaultStacks())) && (var1.onGround())))){
			this.ticksOnGround = (this.ticksOnGround) + (1);
			if ((this.ticksOnGround) > (10)){
				this.resetStacks(var1);
			}
		}
		if ((!(var1.level().isClientSide)) && (((this.noGravityTime--) <= (0)) && (this.hadGravity))){
			var1.setNoGravity(false);
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
	private void resetStacks(LivingEntity var1) {
		if ((this.stackComponent.getStacks()) != (this.stackComponent.getDefaultStacks())){
			this.cooldownComponent.stopCooldown(var1);
			this.cooldownComponent.startCooldown(var1,this.getCooldownTicks());
		}
		this.stackComponent.setStacks(var1,this,6);
		this.hasFallDamage = true;
	}
	private float getCooldownTicks() {
		return ((float)((this.stackComponent.getDefaultStacks()) - (this.stackComponent.getStacks()))) * (50F);
	}
	public void saveAdditional(CompoundTag var1) {
		var1.putBoolean("hasFallDamage",this.hasFallDamage);
		var1.putBoolean("hadGravity",this.hadGravity);
	}
	public void loadAdditional(CompoundTag var1) {
		this.hasFallDamage = var1.getBoolean("hasFallDamage");
		this.hadGravity = var1.getBoolean("hadGravity");
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-12F,12F));
		TrainingPointsUnlockCondition var2 = TrainingPointsUnlockCondition.martialArts(10F);
		NodeUnlockAction var3 = SubtractPointsAction.martialArts(10F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(ConcasseAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to kick the air beneath them and launch themselves into the air"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityCore$INodeFactory[] lv2_4 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv2_5 = SkywalkAbility::createNode;
			lv2_4[0] = lv2_5;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SkywalkAbility::new)))).addDescriptionLine(lv2_3).setNodeFactories(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("skywalk","Skywalk",var0));
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