package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.data.entity.CameraLockData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.moku.WhiteOutProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class WhiteOutAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 240;
	private static final int HOLD_TIME = 60;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<WhiteOutAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final ProjectileComponent projectileComponent;
	private LivingEntity grabbedEntity;
	private WhiteOutProjectile proj;

	public WhiteOutAbility(AbilityCore<WhiteOutAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<WhiteOutProjectile>) (this::createProjectile))))));
		this.grabbedEntity = null;
		this.proj = null;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.projectileComponent};
		super.addComponents(var2);
		super.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,60F);
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((((this.proj) != (null)) && (this.proj.isAlive())) || ((this.grabbedEntity) != (null))){
				if (((this.grabbedEntity) != (null)) && (!((this.grabbedEntity.isAlive()) && (!(CombatHelper.isGuardBlocking(this.grabbedEntity)))))){
					this.continuousComponent.stopContinuity(var1);
				}else{
					if ((this.grabbedEntity) != (null)){
						this.grabbedEntity.setYRot(this.grabbedEntity.yRotO);
						this.grabbedEntity.setXRot(this.grabbedEntity.xRotO);
						this.grabbedEntity.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,1));
						this.grabbedEntity.addEffect(new MobEffectInstance((Holder)(ModEffects.SMOKE),60,0));
						double var3 = 7D;
						Vec3 var4 = var1.getLookAngle();
						Vec3 var5 = new Vec3((var4.x) * (var3),(((double)(var1.getEyeHeight())) / (2D)) + ((var4.y) * (var3)),(var4.z) * (var3));
						AbilityHelper.setDeltaMovement((Entity)(this.grabbedEntity),var1.position().add(var5).subtract(this.grabbedEntity.position()));
						LivingEntity var6 = this.grabbedEntity;
						if (var6 instanceof ServerPlayer){
							ServerPlayer var7 = ((ServerPlayer)(var6));
							if (!(CameraLockData.hasCameraLocked((Player)(var7)))){
								CameraLockData.pinFixed((Player)(var7));
							}
						}
						this.grabbedEntity.fallDistance = 0F;
					}else{
						return;
					}
				}
				return;
			}else{
				this.continuousComponent.stopContinuity(var1);
				return;
			}
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		this.proj = null;
		if (var1 instanceof ServerPlayer){
			CameraLockData.resetLock((Player)(((ServerPlayer)(var1))));
		}
		this.grabbedEntity = null;
		this.cooldownComponent.startCooldown(var1,240F);
	}
	private WhiteOutProjectile createProjectile(LivingEntity var1) {
		return new WhiteOutProjectile(var1.level(),var1,(IAbility)(this));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Fires both fists at an enemy and lifts them up, moving them around according to the user's movements"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(240F),ContinuousComponent.getTooltip(60F)};
			SourceType[] lv2_5 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(WhiteOutAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv2_5).setSourceElement(SourceElement.SMOKE).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("white_out","White Out",var0));
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		this.proj = ((WhiteOutProjectile)(this.projectileComponent.getNewProjectile(var1)));
		WhiteOutAbility var3 = this;
		NuProjectileEntity$IOnHitEntityEvent var4 = (l0) -> {
			Entity lv1_2 = l0.getEntity();
			if (lv1_2 instanceof LivingEntity){
				LivingEntity lv1_3 = ((LivingEntity)(lv1_2));
				this.grabbedEntity = lv1_3;
			}
		};
		this.proj.addEntityHitEvent(90,var4);
		var1.level().addFreshEntity((Entity)(this.proj));
		this.proj.shootFromRotation((Entity)(var1),var1.getXRot(),var1.getYRot(),0F,3F,0F);
	}
}