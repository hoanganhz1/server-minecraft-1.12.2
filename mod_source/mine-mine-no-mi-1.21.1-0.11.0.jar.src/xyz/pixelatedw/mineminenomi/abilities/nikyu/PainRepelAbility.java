package xyz.pixelatedw.mineminenomi.abilities.nikyu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent$IOnSwingEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.data.entity.CameraLockData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu.PainEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class PainRepelAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 1200F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<PainRepelAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final HitTriggerComponent hitTriggerComponent;
	private final SwingTriggerComponent swingTriggerComponent;
	private final ExplosionComponent explosionComponent;
	private Interval painAddInterval;
	private LivingEntity target;
	private PainRepelAbility$State state;
	private float pain;
	private float initialTargetHealthDiff;

	public PainRepelAbility(AbilityCore<PainRepelAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.hitTriggerComponent = new HitTriggerComponent((IAbility)(this)).addTryHitEvent(100,(HitTriggerComponent$ITryHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$ITryHitEvent)(this::tryHitEvent)))).addOnHitEvent(100,(HitTriggerComponent$IOnHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent$IOnHitEvent)(this::onHitEvent))));
		this.swingTriggerComponent = new SwingTriggerComponent((IAbility)(this)).addSwingEvent(0,(SwingTriggerComponent$IOnSwingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent$IOnSwingEvent)(this::onSwing))));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		this.painAddInterval = new Interval(10);
		this.state = PainRepelAbility$State.IDLE;
		this.pain = 0F;
		this.initialTargetHealthDiff = 0F;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.hitTriggerComponent,this.swingTriggerComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private HitTriggerComponent$HitResult tryHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if ((var2.getHealth()) >= (var2.getMaxHealth())){
			return HitTriggerComponent$HitResult.PASS;
		}else{
			if (CombatHelper.isTargetBlocking(var1,var2)){
				return HitTriggerComponent$HitResult.PASS;
			}else{
				if ((this.continuousComponent.isContinuous()) && ((var1.getMainHandItem().isEmpty()) && ((this.state) == (PainRepelAbility$State.IDLE)))){
					return HitTriggerComponent$HitResult.HIT;
				}else{
					return HitTriggerComponent$HitResult.PASS;
				}
			}
		}
	}
	private boolean onHitEvent(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		this.painAddInterval.restartIntervalToZero();
		this.state = PainRepelAbility$State.HEALING;
		this.target = var2;
		this.initialTargetHealthDiff = (var2.getMaxHealth()) - (var2.getHealth());
		return true;
	}
	private void onSwing(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((this.continuousComponent.isContinuous()) && ((var1.getMainHandItem().isEmpty()) && ((this.state) == (PainRepelAbility$State.READY)))){
				PainEntity var3 = new PainEntity(var1.level(),var1,(IAbility)(this));
				var3.setDamage(this.pain);
				var3.shootFromRotation((Entity)(var1),var1.getXRot(),var1.getYRot(),0F,2F,1F);
				var1.level().addFreshEntity((Entity)(var3));
				this.continuousComponent.stopContinuity(var1);
			}
			return;
		}
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if ((this.state) == (PainRepelAbility$State.IDLE)){
			return;
		}else{
			if (((this.target) != (null)) && ((this.state) == (PainRepelAbility$State.HEALING))){
				LivingEntity var3 = this.target;
				if (var3 instanceof ServerPlayer){
					ServerPlayer var4 = ((ServerPlayer)(var3));
					if (!(CameraLockData.hasCameraLocked((Player)(var4)))){
						CameraLockData.pinFixed((Player)(var4));
					}
				}
				this.target.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,1));
				var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,1));
				if (this.painAddInterval.canTick()){
					float var4_1 = (this.target.getMaxHealth()) / (10F);
					this.pain = (this.pain) + (var4_1);
					this.target.heal(var4_1);
					var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.PAD_HO_SFX.get())),SoundSource.PLAYERS,2F,1F);
				}
				float var4_1 = (this.target.getMaxHealth()) - (this.target.getHealth());
				if (((!((var4_1) <= (0F))) && (!((this.pain) >= (this.initialTargetHealthDiff)))) || (var1.swinging)){
					return;
				}else{
					this.state = PainRepelAbility$State.READY;
					LivingEntity var5 = this.target;
					if (var5 instanceof ServerPlayer){
						CameraLockData.resetLock((Player)(((ServerPlayer)(var5))));
					}else{
						return;
					}
				}
			}
			return;
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		this.target = null;
		this.state = PainRepelAbility$State.IDLE;
		this.pain = 0F;
		this.initialTargetHealthDiff = 0F;
		this.cooldownComponent.startCooldown(var1,1200F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Extracts all the damage their target has suffered condensing it into a small paw-shaped ball."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(1200F)};
			SourceType[] lv1_5 = new SourceType[]{SourceType.FRIENDLY};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(PainRepelAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceType(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("pain_repel","Pain Repel",var0));
	}
}