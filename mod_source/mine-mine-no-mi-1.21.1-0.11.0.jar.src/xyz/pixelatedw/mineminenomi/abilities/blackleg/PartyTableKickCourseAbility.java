package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
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
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class PartyTableKickCourseAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 240F;
	private static final float RANGE = 2.5F;
	private static final float DAMAGE = 25F;
	private static final int NODE_POINTS_MA = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<PartyTableKickCourseAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final AnimationComponent animationComponent;
	private final DealDamageComponent dealDamageComponent;
	private final RangeComponent rangeComponent;

	public PartyTableKickCourseAbility(AbilityCore<PartyTableKickCourseAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onStartContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onStopContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.animationComponent,this.dealDamageComponent,this.rangeComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,20F);
	}
	private boolean onStartContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.HAND_STAND_SPIN);
		return true;
	}
	private boolean onStopContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,240F);
		return true;
	}
	
	
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-7F,10F));
		TrainingPointsUnlockCondition var2 = TrainingPointsUnlockCondition.martialArts(10F);
		NodeUnlockAction var3 = SubtractPointsAction.martialArts(10F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(AntiMannerKickCourseAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv5_2 = new Pair[]{ImmutablePair.of((Object)("The user does a hand stand on the ground, legs spread out spinning and dealing damage to all nearby enemies"),(Object)(null))};
			Component[] lv5_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv5_2);
			SourceType[] lv5_4 = new SourceType[]{SourceType.FIST};
			AbilityCore$INodeFactory[] lv5_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv5_6 = PartyTableKickCourseAbility::createNode;
			lv5_5[0] = lv5_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(PartyTableKickCourseAbility::new)))).addDescriptionLine(lv5_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv5_4).setNodeFactories(lv5_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("party_table_kick_course","Party-Table Kick Course",var0));
	}
private boolean duringContinuityEvent(LivingEntity var1, IAbility var2) {
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,1));
		List var3 = this.rangeComponent.getTargetsInArea(var1,2.5F);
		Iterator var4 = var3.iterator();
		do{
			if (var4.hasNext()){
				LivingEntity var5 = ((LivingEntity)(var4.next()));
				if (this.dealDamageComponent.hurtTarget(var1,var5,25F)){
					Vec3 var6 = var1.getLookAngle().multiply(1.5D,1D,1.5D);
					AbilityHelper.setDeltaMovement((Entity)(var5),var6.x,1.5D,var6.z);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		if ((!(var1.level().isClientSide)) && ((var3.size()) > (0))){
			((ServerLevel)(var1.level())).getChunkSource().broadcastAndSend((Entity)(var1),(Packet)(new ClientboundAnimatePacket((Entity)(var1),0)));
		}
		Function<AbilityData, DiableJambeAbility> var5_1 = (l0) -> {
			return ((DiableJambeAbility)(l0.getEquippedAbility(((AbilityCore)(DiableJambeAbility.INSTANCE.get())))));
		};
		if (((Boolean)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var5_1)).map((Function)(((Function<DiableJambeAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue()){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.PARTY_TABLE_KICK.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		}
		return true;
	}public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(240F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<RangeComponentTooltip> var4 = (l0) -> {
			l0.range(2.5F,RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var4);
		Consumer<DealDamageComponentTooltip> var5 = (l0) -> {
			l0.damage(25F);
		};
		DealDamageComponentTooltip.generate(var2,var5);
	}}