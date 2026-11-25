package xyz.pixelatedw.mineminenomi.abilities.pika;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.TeleportComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class YataNoKagamiAbility extends Ability {
	// Fields
	private static final int CHARGE_DURATION = 30;
	private static final int DISABLE_DURATION = 40;
	private static final int COOLDOWN = 60;
	private static final float MAX_TELEPORT_DISTANCE = 200F;
	private static final float SAFE_TELEPORT_DISTANCE = 64F;
	private static final AbilityDescriptionLine$IDescriptionLine TELEPORT_TOOLTIP;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<YataNoKagamiAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final DamageTakenComponent damageTakenComponent;
	private final AnimationComponent animationComponent;
	private final TeleportComponent teleportComponent;
	private boolean hasFallDamage;
	private boolean isCancelled;

	public YataNoKagamiAbility(AbilityCore<YataNoKagamiAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::onChargeStart)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::onChargeTick)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onChargeEnd))));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.teleportComponent = new TeleportComponent((IAbility)(this));
		this.hasFallDamage = true;
		this.isCancelled = false;
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.damageTakenComponent,this.animationComponent,this.teleportComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if (!(this.chargeComponent.isCharging())){
			if (AbilityUseConditions.canUseMomentumAbilities(var1).isSuccess()){
				this.chargeComponent.startCharging(var1,30F);
			}else{
				return;
			}
		}else{
			this.chargeComponent.stopCharging(var1);
		}
	}
	private void onChargeStart(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			this.hasFallDamage = false;
			this.isCancelled = false;
			this.animationComponent.start(var1,ModAnimations.POINT_RIGHT_ARM);
			return;
		}
	}
	private void onChargeTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			float var3 = Math.max(50F,(200F) - (((200F) * ((1F) - ((var1.getHealth()) / (var1.getMaxHealth())))) * (2F)));
			BlockHitResult var4 = this.teleportComponent.getTeleportHit(var1,var3,false);
			if (var1 instanceof Player){
				Player var5 = ((Player)(var1));
				if (((this.chargeComponent.getChargeTime()) % (5F)) == (1F)){
					WyHelper.spawnParticleEffectForOwner(((ParticleEffect)(ModParticleEffects.YATA_NO_KAGAMI.get())),var5,var4.getLocation().x(),(var4.getLocation().y()) - (0.5D),var4.getLocation().z(),(ParticleEffect$Details)(null));
				}
			}
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,1,false,false));
			AbilityHelper.slowEntityFall(var1,2);
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.PIKA_CHARGING.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			return;
		}
	}
	private void onChargeEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (!(this.isCancelled)){
				float var3 = Math.max(50F,(200F) - (((200F) * ((1F) - ((var1.getHealth()) / (var1.getMaxHealth())))) * (2F)));
				float var4 = Math.max(16F,(64F) - (((64F) * ((1F) - ((var1.getHealth()) / (var1.getMaxHealth())))) * (2F)));
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.YATA_NO_KAGAMI.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.FLASH.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
				this.teleportComponent.teleport(var1,var3,var4);
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.YATA_NO_KAGAMI.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
				var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.PIKA_SFX.get())),SoundSource.PLAYERS,2F,1F);
				var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.TELEPORT_SFX.get())),SoundSource.PLAYERS,2F,1F);
			}
			this.animationComponent.stop(var1);
			this.cooldownComponent.startCooldown(var1,60F);
			return;
		}
	}
	private float onDamageTaken(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if ((!(this.hasFallDamage)) && (var3.is(DamageTypes.FALL))){
			this.hasFallDamage = true;
			return 0F;
		}else{
			if ((var4) >= (5F)){
				if (this.chargeComponent.isCharging()){
					this.isCancelled = true;
					this.chargeComponent.stopCharging(var1);
				}else{
					this.disableComponent.startDisable(var1,40F);
				}
			}
			return var4;
		}
	}
	public void saveAdditional(CompoundTag var1) {
		var1.putBoolean("hasFallDamage",this.hasFallDamage);
	}
	public void loadAdditional(CompoundTag var1) {
		this.hasFallDamage = var1.getBoolean("hasFallDamage");
	}
	static  {
		AbilityDescriptionLine$IDescriptionLine var0 = (l0, l1) -> {
			return new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_TELEPORT_DISTANCE),64F).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_BLOCKS).build().getStatDescription();
		};
		TELEPORT_TOOLTIP = var0;
		AbilityRegistryEntry var1 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("The user forms light between his hands, and reflects it off any surface he wishes, instantly teleporting it towards where the light hit."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,ChargeComponent.getTooltip(30F),CooldownComponent.getTooltip(60F),TELEPORT_TOOLTIP};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(YataNoKagamiAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("yata_no_kagami","Yata no Kagami",var1));
	}
}