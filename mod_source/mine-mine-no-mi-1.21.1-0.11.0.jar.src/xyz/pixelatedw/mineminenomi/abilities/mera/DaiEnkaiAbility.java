package xyz.pixelatedw.mineminenomi.abilities.mera;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Vector3d;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IAbilityGoalFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$AbilityStatType;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent$IOnSwingEvent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera.DaiEnkaiEnteiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera.DaiEnkaiOnibiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.mera.HibashiraParticleEffect$Details;

public class DaiEnkaiAbility extends Ability {
	// Fields
	private static final Component DAI_ENKAI_ONIBI_NAME = Component.translatable(ModRegistry.registerAbilityName("dai_enkai_onibi","Dai Enkai: Onibi"));
	private static final ResourceLocation DAI_ENKAI_ENTEI_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/dai_enkai_entei.png");
	private static final ResourceLocation DAI_ENKAI_ONIBI_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/dai_enkai_onibi.png");
	private static final float COOLDOWN = 500F;
	public static final float ENTEI_CHARGE_TIME = 160F;
	private static final float ENTEI_ON_HOLD_TIME = 160F;
	private static final float ONIBI_CHARGE_TIME = 100F;
	private static final double PILLAR_SIZE = 25D;
	private static final float ENTEI_MIN_DAMAGE = 24F;
	private static final float ENTEI_MAX_DAMAGE = 104F;
	private static final float ONIBI_PROJ_DAMAGE = 75F;
	private static final float ONIBI_PILLAR_DAMAGE = 5F;
	private Vec3 onibiStartPos;
	private static final AbilityDescriptionLine$IDescriptionLine ONIBI_DAMAGE_TOOLTIP;
	private static final AbilityDescriptionLine$IDescriptionLine ENTEI_DAMAGE_TOOLTIP;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DaiEnkaiAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final ContinuousComponent continuousComponent;
	private final SwingTriggerComponent swingTriggerComponent;
	private final AltModeComponent<DaiEnkaiAbility$Mode> altModeComponent;
	private final AnimationComponent animationComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private final DealDamageComponent dealDamageComponent;
	private final ProjectileComponent projectileComponent;
	private final ExplosionComponent explosionComponent;
	private final Interval particleInterval;
	private final Interval clearHitsInterval;
	private DaiEnkaiEnteiProjectile enteiProjectile;
	private DaiEnkaiOnibiProjectile[] onibiProjectiles;

	public DaiEnkaiAbility(AbilityCore<DaiEnkaiAbility> var1) {
		super((AbilityCore)(var1));
		this.onibiStartPos = Vec3.ZERO;
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::onChargeStart)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::onChargeTick)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onChargeEnd))));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.swingTriggerComponent = new SwingTriggerComponent((IAbility)(this)).addSwingEvent((SwingTriggerComponent$IOnSwingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent$IOnSwingEvent)(this::onSwing))));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),DaiEnkaiAbility$Mode.class,DaiEnkaiAbility$Mode.ENTEI).addChangeModeEvent((AltModeComponent$IChangeModeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent)(this::onAltModeChange)))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<NuProjectileEntity>) (this::createProjectile))))));
		this.explosionComponent = new ExplosionComponent((IAbility)(this),42875);
		this.particleInterval = new Interval(2);
		this.clearHitsInterval = new Interval(20);
		this.onibiProjectiles = new DaiEnkaiOnibiProjectile[3];
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.chargeComponent,this.swingTriggerComponent,this.altModeComponent,this.animationComponent,this.projectileComponent,this.hitTrackerComponent,this.dealDamageComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((this.altModeComponent.getCurrentMode()) == (DaiEnkaiAbility$Mode.ENTEI)){
				if ((!(this.chargeComponent.isCharging())) && (!(this.continuousComponent.isContinuous()))){
					this.chargeComponent.startCharging(var1,160F);
				}else{
					if (((this.enteiProjectile) != (null)) && (this.enteiProjectile.isAlive())){
						BlockHitResult var3 = new BlockHitResult(this.enteiProjectile.position(),this.enteiProjectile.getDirection(),this.enteiProjectile.blockPosition(),false);
						this.enteiProjectile.onHitBlock(var3);
						this.enteiProjectile.remove(Entity.RemovalReason.DISCARDED);
					}else{
						return;
					}
				}
			}else{
				if ((this.altModeComponent.getCurrentMode()) == (DaiEnkaiAbility$Mode.ONIBI)){
					if (!(var1.onGround())){
						WyHelper.sendMessage(var1,ModI18nAbilities.MESSAGE_ONLY_IN_GROUND);
						return;
					}else{
						this.chargeComponent.startCharging(var1,100F);
					}
				}else{
					return;
				}
			}
			return;
		}
	}
	private void onChargeStart(LivingEntity var1, IAbility var2) {
		this.particleInterval.restartIntervalToZero();
		if ((this.altModeComponent.getCurrentMode()) == (DaiEnkaiAbility$Mode.ENTEI)){
			this.animationComponent.start(var1,ModAnimations.RAISE_RIGHT_ARM);
			this.enteiProjectile = ((DaiEnkaiEnteiProjectile)(this.projectileComponent.getNewProjectile(var1)));
			this.enteiProjectile.setPos(var1.getX(),((var1.getY()) + ((double)(var1.getEyeHeight()))) + (7.5D),var1.getZ());
			var1.level().addFreshEntity((Entity)(this.enteiProjectile));
		}else{
			if ((this.altModeComponent.getCurrentMode()) == (DaiEnkaiAbility$Mode.ONIBI)){
				this.onibiStartPos = var1.position();
				this.animationComponent.start(var1,ModAnimations.RAISE_ARMS);
				float var3 = (-(var1.getYRot())) * (0.017453292F);
				float var4 = Mth.cos(var3);
				float var5 = Mth.sin(var3);
				Vector3d var6 = new Vector3d((double)(var5),0D,(double)(var4));
				double var7 = (var6.x) * (30D);
				double var8 = 20D;
				double var9 = (var6.z) * (30D);
				DaiEnkaiOnibiProjectile[] var10 = new DaiEnkaiOnibiProjectile[]{((DaiEnkaiOnibiProjectile)(this.projectileComponent.getNewProjectile(var1))),((DaiEnkaiOnibiProjectile)(this.projectileComponent.getNewProjectile(var1))),((DaiEnkaiOnibiProjectile)(this.projectileComponent.getNewProjectile(var1)))};
				this.onibiProjectiles = var10;
				this.onibiProjectiles[0].setPos((var1.getX()) + (var9),(var1.getY()) + (var8),(var1.getZ()) - (var7));
				this.onibiProjectiles[1].setPos((var1.getX()) - (var7),(var1.getY()) + (var8),(var1.getZ()) - (var9));
				this.onibiProjectiles[2].setPos((var1.getX()) - (var9),(var1.getY()) + (var8),(var1.getZ()) + (var7));
				DaiEnkaiOnibiProjectile[] var11 = this.onibiProjectiles;
				int var12 = var11.length;
				int var13 = 0;
				do{
					if ((var13) < (var12)){
						DaiEnkaiOnibiProjectile var14 = var11[var13];
						var1.level().addFreshEntity((Entity)(var14));
						var13++;
						continue;
					}else{
						break;
					}
				} while (true);
			}else{
				return;
			}
		}
	}
	private void onChargeTick(LivingEntity var1, IAbility var2) {
		if ((this.altModeComponent.getCurrentMode()) == (DaiEnkaiAbility$Mode.ENTEI)){
			if (((this.enteiProjectile) != (null)) && (this.enteiProjectile.isAlive())){
				this.enteiProjectile.setLife(this.enteiProjectile.getMaxLife());
				this.enteiProjectile.increaseSize();
				this.enteiProjectile.setPos(var1.getX(),((var1.getY()) + ((double)(var1.getEyeHeight()))) + (7.5D),var1.getZ());
				if ((!(var1.level().isClientSide)) && (this.particleInterval.canTick())){
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.DAI_ENKAI_2.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
				}else{
					return;
				}
			}else{
				this.chargeComponent.stopCharging(var1);
				return;
			}
		}else{
			if ((this.altModeComponent.getCurrentMode()) == (DaiEnkaiAbility$Mode.ONIBI)){
				double var3 = 37.5D;
				Vec3 var4 = var1.getLookAngle();
				Vec3 var5 = var1.getEyePosition(1F).add((var4.x) * (var3),(var4.y) * (var3),(var4.z) * (var3));
				DaiEnkaiOnibiProjectile[] var6 = this.onibiProjectiles;
				int var7 = var6.length;
				int var8 = 0;
				do{
					if ((var8) < (var7)){
						DaiEnkaiOnibiProjectile var9 = var6[var8];
						if (((var9) != (null)) && (var9.isAlive())){
							var9.setLife(var9.getMaxLife());
							Vec3 var10 = var9.position();
							double var11 = (var5.x) - (var10.x);
							double var12 = (var5.y) - (var10.y);
							double var13 = (var5.z) - (var10.z);
							double var14 = -(Math.atan2(var12,Math.sqrt(((var11) * (var11)) + ((var13) * (var13)))));
							double var15 = Math.atan2(-(var11),var13);
							var14 = Math.toDegrees(var14);
							var15 = Math.toDegrees(var15);
							var9.setYRot((float)(-(var15)));
							var9.setXRot((float)(-(var14)));
						}
						var8++;
						continue;
					}else{
						break;
					}
				} while (true);
				if ((!(var1.level().isClientSide)) && (this.particleInterval.canTick())){
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.HIBASHIRA.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ(),(ParticleEffect$Details)(new HibashiraParticleEffect$Details(10F,4F,4F)));
				}
				double var9_1 = 41.5D;
				Class[] var10_1 = new Class[]{LivingEntity.class};
				Iterator var11_1 = TargetHelper.getEntitiesAroundCircle(var1,var1.position(),var9_1,4D,TargetPredicate.DEFAULT_AREA_CHECK,var10_1).iterator();
				do{
					if (var11_1.hasNext()){
						LivingEntity var12_1 = ((LivingEntity)(var11_1.next()));
						if ((this.hitTrackerComponent.canHit((Entity)(var12_1))) && (this.dealDamageComponent.hurtTarget(var1,var12_1,5F))){
							var12_1.igniteForSeconds(4F);
						}
						AbilityHelper.setDeltaMovement((Entity)(var12_1),this.onibiStartPos.subtract(var12_1.position()).normalize());
						continue;
					}else{
						break;
					}
				} while (true);
				if (this.clearHitsInterval.canTick()){
					this.hitTrackerComponent.clearHits();
				}
				AbilityHelper.slowEntityFall(var1);
				var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,1,false,false));
			}else{
				return;
			}
		}
	}
	private void onChargeEnd(LivingEntity var1, IAbility var2) {
		if ((this.altModeComponent.getCurrentMode()) == (DaiEnkaiAbility$Mode.ENTEI)){
			this.continuousComponent.startContinuity(var1,160F);
		}else{
			if ((this.altModeComponent.getCurrentMode()) == (DaiEnkaiAbility$Mode.ONIBI)){
				DaiEnkaiOnibiProjectile[] var3 = this.onibiProjectiles;
				int var4 = var3.length;
				int var5 = 0;
				do{
					if ((var5) < (var4)){
						DaiEnkaiOnibiProjectile var6 = var3[var5];
						if (((var6) != (null)) && (var6.isAlive())){
							var6.shootFromRotation((Entity)(var1),-(var6.getXRot()),-(var6.getYRot()),0F,3F,1F);
						}
						var5++;
						continue;
					}else{
						break;
					}
				} while (true);
				this.animationComponent.stop(var1);
				this.cooldownComponent.startCooldown(var1,500F);
			}else{
				return;
			}
		}
	}
	private void onSwing(LivingEntity var1, IAbility var2) {
		if (this.continuousComponent.isContinuous()){
			this.enteiProjectile.shootFromRotation((Entity)(var1),(var1.getXRot()) + (10F),var1.getYRot(),0F,3F,1F);
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.MERA_SFX.get())),SoundSource.PLAYERS,5F,1F);
			if (!(var1.level().isClientSide)){
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.DAI_ENKAI_1.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			}
			this.continuousComponent.stopContinuity(var1);
		}
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if (((this.enteiProjectile) != (null)) && (this.enteiProjectile.isAlive())){
			this.enteiProjectile.setLife(this.enteiProjectile.getMaxLife());
			this.enteiProjectile.setPos(var1.getX(),((var1.getY()) + ((double)(var1.getEyeHeight()))) + (7.5D),var1.getZ());
			return;
		}else{
			this.continuousComponent.stopContinuity(var1);
			return;
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (((this.enteiProjectile) != (null)) && ((this.enteiProjectile.isAlive()) && ((this.enteiProjectile.getLife()) < (this.enteiProjectile.getMaxLife())))){
				BlockHitResult var3 = new BlockHitResult(this.enteiProjectile.position(),this.enteiProjectile.getDirection(),this.enteiProjectile.blockPosition(),false);
				this.enteiProjectile.onHit((HitResult)(var3));
				this.enteiProjectile.remove(Entity.RemovalReason.DISCARDED);
			}
			this.enteiProjectile = null;
			this.animationComponent.stop(var1);
			this.cooldownComponent.startCooldown(var1,500F);
			return;
		}
	}
	private boolean onAltModeChange(LivingEntity var1, IAbility var2, DaiEnkaiAbility$Mode var3) {
		if ((var3) == (DaiEnkaiAbility$Mode.ENTEI)){
			super.setDisplayName(((AbilityCore)(INSTANCE.get())).getLocalizedName());
			super.setDisplayIcon(DAI_ENKAI_ENTEI_ICON);
		}else{
			if ((var3) == (DaiEnkaiAbility$Mode.ONIBI)){
				super.setDisplayName(DAI_ENKAI_ONIBI_NAME);
				super.setDisplayIcon(DAI_ENKAI_ONIBI_ICON);
			}else{
				return true;
			}
		}
		return true;
	}
	private NuProjectileEntity createProjectile(LivingEntity var1) {
		if ((this.altModeComponent.getCurrentMode()) == (DaiEnkaiAbility$Mode.ONIBI)){
			return new DaiEnkaiOnibiProjectile(var1.level(),var1,(IAbility)(this));
		}else{
			return new DaiEnkaiEnteiProjectile(var1.level(),var1,(IAbility)(this));
		}
	}
	public static AbilityWrapperGoal<Mob, DaiEnkaiAbility> createWrapper(Mob var0, AbilityCore<DaiEnkaiAbility> var1) {
		return new ProjectileAbilityWrapperGoal(var0,var1).setMinDistance(50F);
	}
	static  {
		AbilityDescriptionLine$IDescriptionLine var0 = (l0, l1) -> {
			LivingEntity lv3_2 = l0;
			final LivingEntity lv3_2_f1 = lv3_2;
			Function<ProjectileComponent, NuProjectileEntity> lv3_3 = (l2_0) -> {
				return l2_0.getCachedProjectile(lv3_2_f1);
			};
			NuProjectileEntity lv3_4 = ((NuProjectileEntity)(((java.util.Optional)(l1.getComponent(((AbilityComponentKey)(ModAbilityComponents.PROJECTILE.get()))))).map((Function)(lv3_3)).orElse(null)));
			if (((lv3_4) != (null)) && ((lv3_4.getDamage()) > (0F))){
				NuProjectileEntity lv3_5 = lv3_4;
				final NuProjectileEntity lv3_5_f1 = lv3_5;
				Function<BonusManager, Float> lv3_6 = (l2_0) -> {
				return Float.valueOf((l2_0.applyBonus(lv3_5_f1.getDamage())) - (lv3_5_f1.getDamage()));
			};
				float lv3_7 = ((Float)(((java.util.Optional)(l1.getComponent(((AbilityComponentKey)(ModAbilityComponents.PROJECTILE.get()))).map((Function)(((Function<ProjectileComponent, BonusManager>)(ProjectileComponent::getDamageBonusManager)))))).map((Function)(lv3_6)).orElse(Float.valueOf(0F)))).floatValue();
				AbilityStat$AbilityStatType lv3_8 = ((lv3_7) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((lv3_7) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL));
				return new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE),75F).withBonus(lv3_7,lv3_8).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_X3).build().getStatDescription(2);
			}else{
				return null;
			}
		};
		ONIBI_DAMAGE_TOOLTIP = var0;
		AbilityDescriptionLine$IDescriptionLine var1 = (l0, l1) -> {
			LivingEntity lv6_2 = l0;
			final LivingEntity lv6_2_f1 = lv6_2;
			Function<ProjectileComponent, NuProjectileEntity> lv6_3 = (l2_0) -> {
				return l2_0.getCachedProjectile(lv6_2_f1);
			};
			NuProjectileEntity lv6_4 = ((NuProjectileEntity)(((java.util.Optional)(l1.getComponent(((AbilityComponentKey)(ModAbilityComponents.PROJECTILE.get()))))).map((Function)(lv6_3)).orElse(null)));
			if (((lv6_4) != (null)) && ((lv6_4.getDamage()) > (0F))){
				NuProjectileEntity lv6_5 = lv6_4;
				final NuProjectileEntity lv6_5_f1 = lv6_5;
				Function<BonusManager, Float> lv6_6 = (l2_0) -> {
				return Float.valueOf((l2_0.applyBonus(lv6_5_f1.getDamage())) - (lv6_5_f1.getDamage()));
			};
				float lv6_7 = ((Float)(((java.util.Optional)(l1.getComponent(((AbilityComponentKey)(ModAbilityComponents.PROJECTILE.get()))).map((Function)(((Function<ProjectileComponent, BonusManager>)(ProjectileComponent::getDamageBonusManager)))))).map((Function)(lv6_6)).orElse(Float.valueOf(0F)))).floatValue();
				AbilityStat$AbilityStatType lv6_8 = ((lv6_7) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((lv6_7) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL));
				return new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE),24F,104F).withBonus(lv6_7,lv6_8).build().getStatDescription(2);
			}else{
				return null;
			}
		};
		ENTEI_DAMAGE_TOOLTIP = var1;
		AbilityRegistryEntry var2 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv13_2 = new Pair[]{ImmutablePair.of((Object)("Amasses the user's flames into a gigantic fireball that the user hurls at the opponent"),(Object)(null)),ImmutablePair.of((Object)("Creates a giant fire vortex around the user, trapping enemies within it and creating three fire dragons that will be shot towards the enemy at the end."),(Object)(null))};
			Component[] lv13_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv13_2);
			AbilityDescriptionLine$IDescriptionLine[] lv13_4 = new AbilityDescriptionLine$IDescriptionLine[7];
			String lv13_5 = l1;
			final String lv13_5_f1 = lv13_5;
			AbilityDescriptionLine$IDescriptionLine lv13_6 = (l2_0, l2_1) -> {
				return Component.literal(lv13_5_f1).setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
			};
			lv13_4[0] = lv13_6;
			Component[] lv13_7 = lv13_3;
			final Component[] lv13_7_f2 = lv13_7;
			AbilityDescriptionLine$IDescriptionLine lv13_8 = (l2_0, l2_1) -> {
				return lv13_7_f2[0];
			};
			lv13_4[1] = lv13_8;
			lv13_4[2] = CooldownComponent.getTooltip(500F);
			lv13_4[3] = ChargeComponent.getTooltip(160F);
			lv13_4[4] = ContinuousComponent.getTooltip(160F);
			AbilityDescriptionLine$IDescriptionLine lv13_9 = (l2_0, l2_1) -> {
				String lv9_2 = ModI18nAbilities.DESCRIPTION_STAT_NAME_PROJECTILE.getString();
				String lv9_3 = "§a" + lv9_2 + "§r";
				return Component.literal(lv9_3);
			};
			lv13_4[5] = lv13_9;
			lv13_4[6] = ENTEI_DAMAGE_TOOLTIP;
			AbilityDescriptionLine$IDescriptionLine[] lv13_10 = new AbilityDescriptionLine$IDescriptionLine[7];
			lv13_10[0] = AbilityDescriptionLine.NEW_LINE;
			AbilityDescriptionLine$IDescriptionLine lv13_11 = (l2_0, l2_1) -> {
				return DAI_ENKAI_ONIBI_NAME.copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
			};
			lv13_10[1] = lv13_11;
			Component[] lv13_12 = lv13_3;
			final Component[] lv13_12_f3 = lv13_12;
			AbilityDescriptionLine$IDescriptionLine lv13_13 = (l2_0, l2_1) -> {
				return lv13_12_f3[1];
			};
			lv13_10[2] = lv13_13;
			lv13_10[3] = CooldownComponent.getTooltip(500F);
			lv13_10[4] = ChargeComponent.getTooltip(100F);
			AbilityDescriptionLine$IDescriptionLine lv13_14 = (l2_0, l2_1) -> {
				String lv12_2 = ModI18nAbilities.DESCRIPTION_STAT_NAME_PROJECTILE.getString();
				String lv12_3 = "§a" + lv12_2 + "§r";
				return Component.literal(lv12_3);
			};
			lv13_10[5] = lv13_14;
			lv13_10[6] = ONIBI_DAMAGE_TOOLTIP;
			AbilityCore$IAbilityGoalFactory[] lv13_15 = new AbilityCore$IAbilityGoalFactory[1];
			AbilityCore$IAbilityGoalFactory lv13_16 = DaiEnkaiAbility::createWrapper;
			lv13_15[0] = lv13_16;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DaiEnkaiAbility::new)))).addAdvancedDescriptionLine(lv13_4).addAdvancedDescriptionLine(lv13_10).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.FIRE).addGoalWrapper(lv13_15).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("dai_enkai_entei","Dai Enkai: Entei",var2));
	}
}