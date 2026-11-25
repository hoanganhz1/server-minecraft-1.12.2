package xyz.pixelatedw.mineminenomi.abilities.bara;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.BaraFestivalEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class BaraBaraFestivalAbility extends Ability {
	// Fields
	private static final Component ATTACK_NAME = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ABILITY,"bara_bara_festival_attack","Bara Bara Festival: Attack"));
	private static final Component SHIELD_NAME = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ABILITY,"bara_bara_festival_shield","Bara Bara Festival: Shield"));
	private static final ResourceLocation BARA_BARA_FESTIVAL_ATTACK_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/bara_bara_festival.png");
	private static final ResourceLocation BARA_BARA_FESTIVAL_SHIELD_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/bara_bara_festival_shield.png");
	private static final int COOLDOWN = 300;
	private static final int HOLD_TIME = 160;
	private static final int RANGE = 3;
	private static final int DAMAGE_ATTACK = 6;
	private static final int DAMAGE_SHIELD = 4;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BaraBaraFestivalAbility>> INSTANCE;
	private static final AttributeModifier STEP_HEIGHT;
	private final ContinuousComponent continuousComponent;
	private final AltModeComponent<BaraBaraFestivalAbility$Mode> altModeComponent;
	private final ChangeStatsComponent changeStatsComponent;
	private final GrabEntityComponent grabEntityComponent;
	private final DealDamageComponent dealDamageComponent;
	private final RangeComponent rangeComponent;
	private final MorphComponent morphComponent;
	private final PoolComponent poolComponent;
	private Interval hurtInterval;
	private BaraFestivalEntity baraFestivalEntity;

	public BaraBaraFestivalAbility(AbilityCore<BaraBaraFestivalAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::continuityStart)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::continuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::continuityEnd))));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),BaraBaraFestivalAbility$Mode.class,BaraBaraFestivalAbility$Mode.ATTACK).addChangeModeEvent((AltModeComponent$IChangeModeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent)(this::altModeChange)))));
		this.changeStatsComponent = new ChangeStatsComponent((IAbility)(this));
		this.grabEntityComponent = new GrabEntityComponent((IAbility)(this),false,false,0F);
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.morphComponent = new MorphComponent((IAbility)(this));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GRAB_ABILITY,new AbilityPool[0]);
		this.hurtInterval = new Interval(20);
		this.changeStatsComponent.addAttributeModifier(Attributes.STEP_HEIGHT,STEP_HEIGHT);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.altModeComponent,this.changeStatsComponent,this.grabEntityComponent,this.dealDamageComponent,this.rangeComponent,this.morphComponent,this.poolComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(BaraHelper::hasLimbs))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if (this.continuousComponent.isContinuous()){
			this.continuousComponent.stopContinuity(var1);
			return;
		}else{
			if ((this.altModeComponent.getCurrentMode()) == (BaraBaraFestivalAbility$Mode.ATTACK)){
				HitResult var3 = WyHelper.rayTraceBlocksAndEntities((Entity)(var1),12D);
				if (var3 instanceof EntityHitResult){
					EntityHitResult var4 = ((EntityHitResult)(var3));
					if (var4.getEntity() instanceof LivingEntity){
						LivingEntity var5 = ((LivingEntity)(var4.getEntity()));
						boolean var6 = ((Boolean)(DevilFruitData.get(var5).map((Function)(((Function<DevilFruitData, Boolean>)(DevilFruitData::isLogia)))).orElse(Boolean.valueOf(false)))).booleanValue();
						if (((var5.isAlive()) && (!((var6) && (!(HakiHelper.hasHardeningActive(var1)))))) && (!(CombatHelper.isTargetBlocking(var1,var5)))){
							this.grabEntityComponent.grabManually(var1,var5);
						}else{
							return;
						}
					}
				}else{
					WyHelper.sendMessage(var1,ModI18nAbilities.MESSAGE_NO_TARGET);
					return;
				}
			}else{
				if ((this.altModeComponent.getCurrentMode()) == (BaraBaraFestivalAbility$Mode.SHIELD)){
					this.grabEntityComponent.grabManually(var1,var1);
				}
			}
			if (!(this.grabEntityComponent.hasGrabbedEntity())){
				WyHelper.sendMessage(var1,ModI18nAbilities.MESSAGE_NO_TARGET);
				return;
			}else{
				this.continuousComponent.startContinuity(var1,160F);
				return;
			}
		}
	}
	private void continuityStart(LivingEntity var1, IAbility var2) {
		this.hurtInterval.restartIntervalToZero();
		if (this.grabEntityComponent.hasGrabbedEntity()){
			this.morphComponent.startMorph(var1,((MorphInfo)(ModMorphs.BARA_FESTIVAL.get())));
			this.changeStatsComponent.applyModifiers(var1);
			LivingEntity var3 = this.grabEntityComponent.getGrabbedEntity();
			this.baraFestivalEntity = new BaraFestivalEntity((Level)(var1.level()),(LivingEntity)(var1));
			this.baraFestivalEntity.moveTo(var3.getX(),(var3.getY()) + (1D),var3.getZ(),0F,0F);
			this.baraFestivalEntity.setTarget(var3);
			var1.level().addFreshEntity((Entity)(this.baraFestivalEntity));
		}
	}
	private void continuityTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((this.altModeComponent.getCurrentMode()) == (BaraBaraFestivalAbility$Mode.ATTACK)){
				LivingEntity var3 = this.grabEntityComponent.getGrabbedEntity();
				if (((var3) != (null)) && ((var3.isAlive()) && (!((var1.distanceToSqr((Entity)(var3))) > (1500D))))){
					if ((this.hurtInterval.canTick()) && (this.dealDamageComponent.hurtTarget(var1,var3,6F))){
						var3.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,10,1,true,true));
					}
				}else{
					this.continuousComponent.stopContinuity(var1);
					return;
				}
			}else{
				if ((this.altModeComponent.getCurrentMode()) == (BaraBaraFestivalAbility$Mode.SHIELD)){
					Iterator var3_1 = this.rangeComponent.getTargetsInArea(var1,3F).iterator();
					do{
						if (var3_1.hasNext()){
							LivingEntity var4 = ((LivingEntity)(var3_1.next()));
							if (this.dealDamageComponent.hurtTarget(var1,var4,4F)){
								Vec3 var5 = var4.position().subtract(var1.position()).add(0D,-1D,0D).normalize();
								double var6 = 2D;
								double var7 = (-(var5.x)) * (var6);
								AbilityHelper.setDeltaMovement((Entity)(var4),-(var7),0.20000000298023224D,-((-(var5.z)) * (var6)));
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
				}
			}
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.NO_HANDS),5,1));
			return;
		}
	}
	private void continuityEnd(LivingEntity var1, IAbility var2) {
		if ((this.baraFestivalEntity) != (null)){
			this.baraFestivalEntity.remove(Entity.RemovalReason.DISCARDED);
		}
		this.changeStatsComponent.removeModifiers(var1);
		this.grabEntityComponent.release(var1);
		this.morphComponent.stopMorph(var1);
		this.cooldownComponent.startCooldown(var1,300F);
	}
	private boolean altModeChange(LivingEntity var1, IAbility var2, BaraBaraFestivalAbility$Mode var3) {
		if ((var3) == (BaraBaraFestivalAbility$Mode.ATTACK)){
			this.setDisplayName(ATTACK_NAME);
			this.setDisplayIcon(BARA_BARA_FESTIVAL_ATTACK_ICON);
		}else{
			if ((var3) == (BaraBaraFestivalAbility$Mode.SHIELD)){
				this.setDisplayName(SHIELD_NAME);
				this.setDisplayIcon(BARA_BARA_FESTIVAL_SHIELD_ICON);
			}else{
				return true;
			}
		}
		return true;
	}
	public void switchModeToAttack(LivingEntity var1) {
		if (this.altModeComponent.isMode(BaraBaraFestivalAbility$Mode.ATTACK)){
			return;
		}else{
			this.altModeComponent.setMode(var1,(Enum)(BaraBaraFestivalAbility$Mode.ATTACK));
			return;
		}
	}
	public void switchModeToShield(LivingEntity var1) {
		if (this.altModeComponent.isMode(BaraBaraFestivalAbility$Mode.SHIELD)){
			return;
		}else{
			this.altModeComponent.setMode(var1,(Enum)(BaraBaraFestivalAbility$Mode.SHIELD));
			return;
		}
	}
	public boolean isAttackMode() {
		return this.altModeComponent.isMode(BaraBaraFestivalAbility$Mode.ATTACK);
	}
	public boolean isShieldMode() {
		return this.altModeComponent.isMode(BaraBaraFestivalAbility$Mode.SHIELD);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Splits the user's body in several small parts that constantly hurt and slow down the target."),(Object)(null)),ImmutablePair.of((Object)("Splits the user's body in several small parts that swarm around the user acting as a shield."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{CooldownComponent.getTooltip(300F),ContinuousComponent.getTooltip(160F)};
			AbilityDescriptionLine$IDescriptionLine[] lv1_5 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,AbilityDescriptionLine$IDescriptionLine.of((Component)(MentionHelper.mentionText(ATTACK_NAME))),AbilityDescriptionLine$IDescriptionLine.of(lv1_3[0]),DealDamageComponent.getTooltip(6F)};
			AbilityDescriptionLine$IDescriptionLine[] lv1_6 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,AbilityDescriptionLine$IDescriptionLine.of((Component)(MentionHelper.mentionText(SHIELD_NAME))),AbilityDescriptionLine$IDescriptionLine.of(lv1_3[1]),RangeComponent.getTooltip(3F,RangeComponent$RangeType.AOE),DealDamageComponent.getTooltip(4F)};
			SourceType[] lv1_7 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BaraBaraFestivalAbility::new)))).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_5).addAdvancedDescriptionLine(lv1_6).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_7).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("bara_bara_festival","Bara Bara Festival",var0));
		STEP_HEIGHT = new AttributeModifier(AttributeHelper.MORPH_STEP_HEIGHT_UUID,1D,AttributeModifier.Operation.ADD_VALUE);
	}
}