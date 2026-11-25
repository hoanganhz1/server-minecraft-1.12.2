package xyz.pixelatedw.mineminenomi.abilities.bara;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity$IOnHitEntityEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.DaiCircusProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KuchuKirimomiDaiCircusAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 300;
	private static final int HOLD_TIME = 60;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KuchuKirimomiDaiCircusAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final ProjectileComponent projectileComponent;
	private final GrabEntityComponent grabEntityComponent;
	private final MorphComponent morphComponent;
	private final PoolComponent poolComponent;

	public KuchuKirimomiDaiCircusAbility(AbilityCore<KuchuKirimomiDaiCircusAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<DaiCircusProjectile>) (this::createProjectile))))));
		this.grabEntityComponent = new GrabEntityComponent((IAbility)(this),true,true,7F);
		this.morphComponent = new MorphComponent((IAbility)(this));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.GRAB_ABILITY,new AbilityPool[0]);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.projectileComponent,this.grabEntityComponent,this.morphComponent,this.poolComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(BaraHelper::hasLimbs))));
		this.addTickEvent((Ability$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent)(this::tickEvent))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void tickEvent(LivingEntity var1, IAbility var2) {
		if ((!(var1.level().isClientSide)) && ((!(this.continuousComponent.isContinuous())) && (this.morphComponent.isMorphed()))){
			NuProjectileEntity var3 = this.projectileComponent.getShotProjectile();
			if (((var3) != (null)) && (var3.isAlive())){
				return;
			}else{
				this.morphComponent.stopMorph(var1);
			}
		}
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if (this.continuousComponent.isContinuous()){
			this.continuousComponent.stopContinuity(var1);
			return;
		}else{
			this.projectileComponent.shoot(var1,3F,0F);
			int var3 = this.projectileComponent.getShotProjectile().getLife();
			this.morphComponent.startMorph(var1,((MorphInfo)(ModMorphs.BARA_CIRCUS.get())));
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.NO_HANDS),var3,0));
			return;
		}
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.NO_HANDS),5,1));
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.morphComponent.stopMorph(var1);
		this.grabEntityComponent.release(var1);
		this.cooldownComponent.startCooldown(var1,300F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Fires both fists at an enemy and lifts them up, moving them around according to the user's movements."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(300F),ContinuousComponent.getTooltip(60F)};
			SourceType[] lv2_5 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KuchuKirimomiDaiCircusAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv2_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kuchu_kirimomi_dai_circus","Kuchu Kirimomi Dai Circus",var0));
	}

	private DaiCircusProjectile createProjectile(LivingEntity var1) {
		DaiCircusProjectile var2 = new DaiCircusProjectile(var1.level(),var1,(IAbility)(this));
		KuchuKirimomiDaiCircusAbility var3 = this;
		LivingEntity var4 = var1;
		final LivingEntity var4_f1 = var4;
		NuProjectileEntity$IOnHitEntityEvent var5 = (l0) -> {
			Entity lv1_3 = l0.getEntity();
			if (lv1_3 instanceof LivingEntity){
				LivingEntity lv1_4 = ((LivingEntity)(lv1_3));
				if (this.grabEntityComponent.grabManually(var4_f1,lv1_4)){
					this.continuousComponent.startContinuity(var4_f1,60F);
				}else{
					return;
				}
			}
		};
		var2.addEntityHitEvent(100,var5);
		return var2;
	}}