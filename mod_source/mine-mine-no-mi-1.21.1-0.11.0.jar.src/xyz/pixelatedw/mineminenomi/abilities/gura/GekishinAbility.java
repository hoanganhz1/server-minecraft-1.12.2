package xyz.pixelatedw.mineminenomi.abilities.gura;

import java.util.Iterator;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
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
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gura.GekishinProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GekishinAbility extends Ability {
	// Fields
	private static final Component KAISHIN_NAME = Component.translatable(ModRegistry.registerAbilityName("kaishin","Kaishin"));
	private static final ResourceLocation GEKISHIN_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/gekishin.png");
	private static final ResourceLocation KAISHIN_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/kaishin.png");
	private static final float GEKISHIN_COOLDOWN = 240F;
	private static final float GEKISHIN_CHARGE_TIME = 20F;
	private static final float KAISHIN_COOLDOWN = 480F;
	private static final float KAISHIN_CHARGE_TIME = 40F;
	private static final int GEKISHIN_RANGE = 8;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GekishinAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final AltModeComponent<GekishinAbility$Mode> altModeComponent;
	private final AnimationComponent animationComponent;
	private final ProjectileComponent projectileComponent;
	private final RangeComponent rangeComponent;
	private final ExplosionComponent explosionComponent;

	public GekishinAbility(AbilityCore<GekishinAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::onChargeStart)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onChargeEnd))));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),GekishinAbility$Mode.class,GekishinAbility$Mode.GEKISHIN).addChangeModeEvent((AltModeComponent$IChangeModeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent)(this::onAltModeChange)))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<GekishinProjectile>) (this::createProjectile))))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.explosionComponent = new ExplosionComponent((IAbility)(this),12000);
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.altModeComponent,this.animationComponent,this.projectileComponent,this.rangeComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if ((this.altModeComponent.getCurrentMode()) == (GekishinAbility$Mode.KAISHIN)){
			this.chargeComponent.startCharging(var1,40F);
		}else{
			if ((this.altModeComponent.getCurrentMode()) == (GekishinAbility$Mode.GEKISHIN)){
				this.chargeComponent.startCharging(var1,20F);
			}else{
				return;
			}
		}
	}
	private void onChargeStart(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.KAISHIN);
	}
	private void onChargeEnd(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((this.altModeComponent.getCurrentMode()) == (GekishinAbility$Mode.GEKISHIN)){
				EntityHitResult var3 = WyHelper.rayTraceEntities((Entity)(var1),1.5D);
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.AIR_CRACKS.get())),(Entity)(var1),var3.getLocation().x(),var1.getEyeY(),var3.getLocation().z());
				var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.GURA_SFX.get())),SoundSource.PLAYERS,5F,1F);
				this.projectileComponent.shoot(var1,3F,1F);
			}else{
				if ((this.altModeComponent.getCurrentMode()) == (GekishinAbility$Mode.KAISHIN)){
					Vec3 var3_1 = var1.position().add(0D,(double)(var1.getEyeHeight()),0D).add(var1.getLookAngle().scale(2.5D).yRot(180F));
					Vec3 var4 = var1.position().add(0D,(double)(var1.getEyeHeight()),0D).add(var1.getLookAngle().scale(2.5D).yRot(-180F));
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.AIR_CRACKS.get())),(Entity)(var1),var3_1.x(),(var1.getY()) + (0.5D),var3_1.z());
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.AIR_CRACKS.get())),(Entity)(var1),var4.x(),(var1.getY()) + (0.5D),var4.z());
					Iterator var5 = this.rangeComponent.getTargetsInArea(var1,8F).iterator();
					do{
						if (var5.hasNext()){
							Entity var6 = ((Entity)(var5.next()));
							if (var6 instanceof NuProjectileEntity){
								NuProjectileEntity var7 = ((NuProjectileEntity)(var6));
								if (((var7.getDamage()) < (20F)) && (var7.isPhysical())){
									var6.discard();
								}
							}
							if (var6 instanceof LivingEntity){
								Vec3 var7_1 = var6.getLookAngle().multiply(3D,2D,3D);
								AbilityHelper.setDeltaMovement(var6,var7_1.x,var7_1.y,var7_1.z);
								var6.fallDistance = 0F;
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.GURA_SFX.get())),SoundSource.PLAYERS,0.5F,1F);
					GekishinProjectile var6_1 = ((GekishinProjectile)(this.projectileComponent.getNewProjectile(var1)));
					var6_1.setMaxLife((int)(((float)(var6_1.getMaxLife())) * (0.5F)));
					var6_1.shootFromRotation((Entity)(var1),var1.getXRot(),(var1.getYRot()) + (90F),0F,1.75F,1F);
					var1.level().addFreshEntity((Entity)(var6_1));
					GekishinProjectile var7_2 = ((GekishinProjectile)(this.projectileComponent.getNewProjectile(var1)));
					var7_2.setMaxLife((int)(((float)(var7_2.getMaxLife())) * (0.5F)));
					var7_2.shootFromRotation((Entity)(var1),var1.getXRot(),(var1.getYRot()) - (90F),0F,1.75F,1F);
					var1.level().addFreshEntity((Entity)(var7_2));
				}
			}
			this.animationComponent.stop(var1);
			if ((this.altModeComponent.getCurrentMode()) == (GekishinAbility$Mode.KAISHIN)){
				this.cooldownComponent.startCooldown(var1,480F);
			}else{
				if ((this.altModeComponent.getCurrentMode()) == (GekishinAbility$Mode.GEKISHIN)){
					this.cooldownComponent.startCooldown(var1,240F);
				}else{
					return;
				}
			}
			return;
		}
	}
	private boolean onAltModeChange(LivingEntity var1, IAbility var2, GekishinAbility$Mode var3) {
		if ((var3) == (GekishinAbility$Mode.KAISHIN)){
			super.setDisplayName(KAISHIN_NAME);
			super.setDisplayIcon(KAISHIN_ICON);
		}else{
			if ((var3) == (GekishinAbility$Mode.GEKISHIN)){
				super.setDisplayName(((AbilityCore)(INSTANCE.get())).getLocalizedName());
				super.setDisplayIcon(GEKISHIN_ICON);
			}else{
				return true;
			}
		}
		return true;
	}
	private GekishinProjectile createProjectile(LivingEntity var1) {
		return new GekishinProjectile(var1.level(),var1,(IAbility)(this));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv5_2 = new Pair[]{ImmutablePair.of((Object)("The user 'cracks' the air, launching a single concentrated shockwave forward which send blocks flying and deals damage to all enemies in its path"),(Object)(null)),ImmutablePair.of((Object)("The user 'cracks' the air on their sides, launching two smaller shockwaves which send blocks flying and deals damage to all enemies in their path"),(Object)(null))};
			Component[] lv5_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv5_2);
			AbilityDescriptionLine$IDescriptionLine[] lv5_4 = new AbilityDescriptionLine$IDescriptionLine[4];
			String lv5_5 = l1;
			final String lv5_5_f1 = lv5_5;
			AbilityDescriptionLine$IDescriptionLine lv5_6 = (l2_0, l2_1) -> {
				return Component.literal(lv5_5_f1).setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
			};
			lv5_4[0] = lv5_6;
			Component[] lv5_7 = lv5_3;
			final Component[] lv5_7_f2 = lv5_7;
			AbilityDescriptionLine$IDescriptionLine lv5_8 = (l2_0, l2_1) -> {
				return lv5_7_f2[0];
			};
			lv5_4[1] = lv5_8;
			lv5_4[2] = CooldownComponent.getTooltip(240F);
			lv5_4[3] = ChargeComponent.getTooltip(20F);
			AbilityDescriptionLine$IDescriptionLine[] lv5_9 = new AbilityDescriptionLine$IDescriptionLine[5];
			lv5_9[0] = AbilityDescriptionLine.NEW_LINE;
			AbilityDescriptionLine$IDescriptionLine lv5_10 = (l2_0, l2_1) -> {
				return KAISHIN_NAME.copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
			};
			lv5_9[1] = lv5_10;
			Component[] lv5_11 = lv5_3;
			final Component[] lv5_11_f3 = lv5_11;
			AbilityDescriptionLine$IDescriptionLine lv5_12 = (l2_0, l2_1) -> {
				return lv5_11_f3[1];
			};
			lv5_9[2] = lv5_12;
			lv5_9[3] = CooldownComponent.getTooltip(480F);
			lv5_9[4] = ChargeComponent.getTooltip(40F);
			AbilityDescriptionLine$IDescriptionLine[] lv5_13 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE};
			AbilityDescriptionLine$IDescriptionLine[] lv5_14 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GekishinAbility::new)))).addAdvancedDescriptionLine(lv5_4).addAdvancedDescriptionLine(lv5_9).addAdvancedDescriptionLine(lv5_13).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).addAdvancedDescriptionLine(lv5_14).setSourceElement(SourceElement.SHOCKWAVE).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gekishin","Gekishin",var0));
	}
}