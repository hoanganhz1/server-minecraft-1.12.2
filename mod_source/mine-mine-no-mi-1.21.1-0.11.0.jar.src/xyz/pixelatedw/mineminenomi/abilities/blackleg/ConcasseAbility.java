package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.DropHitAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.DealDamageComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RangeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ConcasseAbility extends DropHitAbility {
	// Fields
	private static final int COOLDOWN = 300;
	private static final float RANGE = 1.7F;
	private static final float DAMAGE = 15F;
	private static final int NODE_POINTS_MA = 5;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ConcasseAbility>> INSTANCE;
	private final DealDamageComponent dealDamageComponent;
	private final RangeComponent rangeComponent;
	private final AnimationComponent animationComponent;
	private Interval particleInterval;

	public ConcasseAbility(AbilityCore<ConcasseAbility> var1) {
		super((AbilityCore)(var1));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.particleInterval = new Interval(2);
		AbilityComponent[] var2 = new AbilityComponent[]{this.dealDamageComponent,this.rangeComponent,this.animationComponent};
		super.addComponents(var2);
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
		this.continuousComponent.addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.continuousComponent.addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.hitTrackerComponent.clearHits();
		this.particleInterval.restartIntervalToZero();
		Vec3 var3 = var1.getLookAngle();
		AbilityHelper.setDeltaMovement((Entity)(var1),var3.x,1.3D,var3.z);
		this.animationComponent.start(var1,ModAnimations.PITCH_SPIN);
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,300F);
	}
	public void onLanding(LivingEntity var1) {
	}
	
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-11F,10F));
		TrainingPointsUnlockCondition var2 = TrainingPointsUnlockCondition.martialArts(5F);
		NodeUnlockAction var3 = SubtractPointsAction.martialArts(5F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(AntiMannerKickCourseAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv5_2 = new Pair[]{ImmutablePair.of((Object)("Leaps forward kicking all nearby enemies for moderate damage and knocking them down"),(Object)(null))};
			Component[] lv5_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv5_2);
			SourceType[] lv5_4 = new SourceType[]{SourceType.FIST};
			AbilityCore$INodeFactory[] lv5_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv5_6 = ConcasseAbility::createNode;
			lv5_5[0] = lv5_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ConcasseAbility::new)))).addDescriptionLine(lv5_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv5_4).setNodeFactories(lv5_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("concasse","Concasse",var0));
	}

	private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((var1 instanceof Player) && (((Player)(var1)).getAbilities().flying)){
				this.continuousComponent.stopContinuity(var1);
				return;
			}else{
				boolean var3 = false;
				if ((var1.fallDistance) > (0F)){
					var3 = false;
					List var4 = this.rangeComponent.getTargetsInArea(var1,1.7F);
					var4.remove(var1);
					Iterator var5 = var4.iterator();
					do{
						if (var5.hasNext()){
							LivingEntity var6 = ((LivingEntity)(var5.next()));
							if ((this.hitTrackerComponent.canHit((Entity)(var6))) && (((var1.blockPosition().getY()) > (var6.blockPosition().getY())) && (this.dealDamageComponent.hurtTarget(var1,var6,15F)))){
								var6.addEffect(new MobEffectInstance((Holder)(ModEffects.UNCONSCIOUS),20,0,false,false));
								AbilityHelper.setDeltaMovement((Entity)(var6),var1.getDeltaMovement().x,-1.5D,var1.getDeltaMovement().z);
								var3 = true;
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					if (var3){
						if (!(var1.level().isClientSide)){
							((ServerLevel)(var1.level())).getChunkSource().broadcastAndSend((Entity)(var1),(Packet)(new ClientboundAnimatePacket((Entity)(var1),0)));
						}
						this.continuousComponent.stopContinuity(var1);
					}
				}
				if (this.particleInterval.canTick()){
					Function<AbilityData, DiableJambeAbility> var3_1 = (l0) -> {
			return ((DiableJambeAbility)(l0.getEquippedAbility(((AbilityCore)(DiableJambeAbility.INSTANCE.get())))));
		};
					if ((((Boolean)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3_1)).map((Function)(((Function<DiableJambeAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue()) && (!(var1.onGround()))){
						WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.CONCASSE_DIABLE.get())),(Entity)(var1),var1.getX(),(var1.getY()) + (1.5D),var1.getZ());
					}else{
						return;
					}
				}
				return;
			}
		}
	}public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(300F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<RangeComponentTooltip> var4 = (l0) -> {
			l0.range(1.7F,RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var4);
		Consumer<DealDamageComponentTooltip> var5 = (l0) -> {
			l0.damage(15F);
		};
		DealDamageComponentTooltip.generate(var2,var5);
	}}