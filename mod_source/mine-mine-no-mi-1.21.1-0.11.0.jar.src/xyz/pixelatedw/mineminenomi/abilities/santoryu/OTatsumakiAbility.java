package xyz.pixelatedw.mineminenomi.abilities.santoryu;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class OTatsumakiAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 60;
	private static final float COOLDOWN = 240F;
	private static final float DAMAGE = 30F;
	private static final float RANGE = 5.5F;
	private static final int NODE_POINTS_WM = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<OTatsumakiAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final DealDamageComponent dealDamageComponent;
	private final RangeComponent rangeComponent;
	private final AnimationComponent animationComponent;
	private final Interval damageInterval;

	public OTatsumakiAbility(AbilityCore<OTatsumakiAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onStartContinuousEvent)))).addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onTickContinuousEvent)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onEndContinuousEvent))));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.damageInterval = new Interval(15);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.dealDamageComponent,this.rangeComponent,this.animationComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresSword))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,60F);
	}
	private void onStartContinuousEvent(LivingEntity var1, IAbility var2) {
		var1.getMainHandItem().hurtAndBreak(1,var1,EquipmentSlot.MAINHAND);
		this.damageInterval.restartIntervalToZero();
		this.animationComponent.start(var1,ModAnimations.BODY_ROTATION_WIDE_ARMS);
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.SPIN.get())),SoundSource.PLAYERS,2F,(0.75F) + ((var1.getRandom().nextFloat()) / (4F)));
	}
	private void onEndContinuousEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,240F);
	}
	private void onTickContinuousEvent(LivingEntity var1, IAbility var2) {
		if (this.damageInterval.canTick()){
			Iterator var3 = this.rangeComponent.getTargetsInArea(var1,5.5F).iterator();
			do{
				if (var3.hasNext()){
					LivingEntity var4 = ((LivingEntity)(var3.next()));
					this.dealDamageComponent.hurtTarget(var1,var4,30F);
					if (!(var1.level().isClientSide)){
						WyHelper.spawnParticles((ParticleOptions)(ParticleTypes.SWEEP_ATTACK),((ServerLevel)(var1.level())),var4.getX(),(var4.getY()) + ((double)(var4.getEyeHeight())),var4.getZ());
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		var1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,5,1,false,false));
		if (!(var1.level().isClientSide)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.TATSU_MAKI.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			if (((this.continuousComponent.getContinueTime()) % (5F)) == (0F)){
				var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.SPIN.get())),SoundSource.PLAYERS,2F,(0.75F) + ((var1.getRandom().nextFloat()) / (4F)));
			}else{
				return;
			}
		}
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-5F,-3F));
		TrainingPointsUnlockCondition var2 = TrainingPointsUnlockCondition.weaponMastery(10F);
		NodeUnlockAction var3 = SubtractPointsAction.weaponMastery(10F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(ShiShishiSonsonAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv5_2 = new Pair[]{ImmutablePair.of((Object)("By spinning, the user creates a small tornado, which slashes and weakens nearby opponents"),(Object)(null))};
			Component[] lv5_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv5_2);
			SourceType[] lv5_4 = new SourceType[]{SourceType.SLASH};
			AbilityCore$INodeFactory[] lv5_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv5_6 = OTatsumakiAbility::createNode;
			lv5_5[0] = lv5_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(OTatsumakiAbility::new)))).setIcon(ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/tatsu_maki.png")).addDescriptionLine(lv5_3).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv5_4).setNodeFactories(lv5_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("o_tatsumaki","O Tatsumaki",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(240F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ContinuousComponentTooltip> var4 = (l0) -> {
			l0.holdTime(60F);
		};
		ContinuousComponentTooltip.generate(var2,var4);
		Consumer<RangeComponentTooltip> var5 = (l0) -> {
			l0.range(5.5F,RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var5);
		Consumer<DealDamageComponentTooltip> var6 = (l0) -> {
			l0.damage(30F);
		};
		DealDamageComponentTooltip.generate(var2,var6);
	}
}