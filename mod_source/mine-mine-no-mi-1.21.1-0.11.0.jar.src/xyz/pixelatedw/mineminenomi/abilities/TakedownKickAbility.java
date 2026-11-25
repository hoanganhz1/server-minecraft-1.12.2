package xyz.pixelatedw.mineminenomi.abilities;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.DropHitAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TakedownKickAbility extends DropHitAbility {
	// Fields
	private static final UUID COOLDOWN_BONUS_UUID = UUID.fromString("11164ab3-f50d-4f6e-b2a1-3a2ac0c1c895");
	private static final float COOLDOWN = 240F;
	private static final float RANGE = 2.5F;
	private static final float DAMAGE = 10F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<TakedownKickAbility>> INSTANCE;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final AnimationComponent animationComponent;
	private boolean isFalling;

	public TakedownKickAbility(AbilityCore<TakedownKickAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.dealDamageComponent,this.rangeComponent,this.animationComponent};
		this.addComponents(var2);
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
		this.continuousComponent.addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.continuousComponent.addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.hitTrackerComponent.clearHits();
		this.isFalling = false;
		Vec3 var3 = var1.getLookAngle().multiply(4D,0D,4D);
		if (var1 instanceof Mob){
			Mob var4 = ((Mob)(var1));
			if (((var4.getTarget()) != (null)) && (var4.getTarget().isAlive())){
				var3 = var4.getTarget().position().subtract(var1.position()).normalize().multiply(4D,0D,4D);
			}
		}
		Vec3 var4_1 = var3.add(0D,2.25D,0D);
		AbilityHelper.setDeltaMovement((Entity)(var1),var4_1.x,var4_1.y,var4_1.z);
	}
	private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((AbilityHelper.getDifferenceToFloor((Entity)(var1))) > (5D)){
			boolean var3 = false;
			Iterator var4 = this.rangeComponent.getTargetsInArea(var1,2.5F).iterator();
			do{
				if (var4.hasNext()){
					LivingEntity var5 = ((LivingEntity)(var4.next()));
					if ((this.hitTrackerComponent.canHit((Entity)(var5))) && (this.dealDamageComponent.hurtTarget(var1,var5,10F))){
						var5.addEffect(new MobEffectInstance((Holder)(ModEffects.DIZZY),20,0,false,false));
						AbilityHelper.setDeltaMovement((Entity)(var5),var1.getDeltaMovement().x,-5D,var1.getDeltaMovement().z);
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
				if (!(this.isFalling)){
					AbilityHelper.setDeltaMovement((Entity)(var1),0D,0D,0D);
					this.isFalling = true;
				}
				this.animationComponent.start(var1,ModAnimations.TAKEDOWN_KICK,8);
				if (!(var1.level().isClientSide)){
					((ServerLevel)(var1.level())).getChunkSource().broadcastAndSend((Entity)(var1),(Packet)(new ClientboundAnimatePacket((Entity)(var1),0)));
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		if (var1 instanceof Player){
			this.cooldownComponent.getBonusManager().addBonus(COOLDOWN_BONUS_UUID,"Cooldown Bonus",BonusOperation.ADD,360F);
		}
		this.cooldownComponent.startCooldown(var1,240F);
	}
	public void onLanding(LivingEntity var1) {
		this.continuousComponent.stopContinuity(var1);
	}
	private static boolean canUnlock(LivingEntity var0) {
		if (var0 instanceof Player){
			Player var1 = ((Player)(var0));
			if ((((QuestData)(((java.util.Optional)(QuestData.get(var1))).orElse((Object)(null))))) == (null)){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Jumps high in the sky and kicks down any enemies it reaches temporarily stunning them too."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(240F),DealDamageComponent.getTooltip(10F),RangeComponent.getTooltip(2.5F,RangeComponent$RangeType.AOE)};
			SourceType[] lv1_5 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(TakedownKickAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_5).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(TakedownKickAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("takedown_kick","Takedown Kick",var0));
	}
}