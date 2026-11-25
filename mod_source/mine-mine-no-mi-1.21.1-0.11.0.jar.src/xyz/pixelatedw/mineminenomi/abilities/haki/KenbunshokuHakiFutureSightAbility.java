package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.awt.Color;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent$IStacksChangeEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.StackComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KenbunshokuHakiFutureSightAbility extends Ability {
	// Fields
	public static final Supplier<AbilityCore<KenbunshokuHakiFutureSightAbility>> INSTANCE;
	private static final int MIN_COOLDOWN = 100;
	private static final float PROTECTION_TIME = 10F;
	public static final AbilityOverlay OVERLAY;
	private final ContinuousComponent continuousComponent;
	private final DamageTakenComponent damageTakenComponent;
	private final SkinOverlayComponent skinOverlayComponent;
	private final PoolComponent poolComponent;
	private final StackComponent stackComponent;
	private final AnimationComponent animationComponent;
	private boolean hasDodged;
	private float protTimer;

	public KenbunshokuHakiFutureSightAbility(AbilityCore<KenbunshokuHakiFutureSightAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::damageTakenEvent))));
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.DODGE_ABILITY,new AbilityPool[0]);
		this.stackComponent = new StackComponent((IAbility)(this)).addStackChangeEvent((StackComponent$IStacksChangeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent$IStacksChangeEvent)(this::changeStackEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.protTimer = 10F;
		this.setOGCD();
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.damageTakenComponent,this.skinOverlayComponent,this.poolComponent,this.stackComponent,this.animationComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canEnableHaki))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
		this.addTickEvent((Ability$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent)(this::tickEvent))));
	}
	public void tickEvent(LivingEntity var1, IAbility var2) {
		if (((this.getLastUseGametime()) <= (0L)) && ((this.stackComponent.getDefaultStacks()) <= (0))){
			this.stackComponent.setDefaultStacks(this.calculateMaxProtection(var1));
			this.stackComponent.revertStacksToDefault(var1,var2);
		}
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void changeStackEvent(LivingEntity var1, IAbility var2, int var3) {
		if ((var3) <= (0)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.HAKI_GUARD.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			this.continuousComponent.stopContinuity(var1);
		}
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.stackComponent.setDefaultStacks(this.calculateMaxProtection(var1));
		this.stackComponent.revertStacksToDefault(var1,var2);
		this.protTimer = 0F;
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.KENBUNSHOKU_HAKI_ON_SFX.get())),SoundSource.PLAYERS,2F,1F);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (HakiHelper.checkForHakiOveruse(var1,2)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.HAKI_GUARD.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			this.continuousComponent.stopContinuity(var1);
		}
		if ((this.protTimer) > (0F)){
			this.protTimer = (this.protTimer) - (1F);
		}else{
			if (((this.protTimer) <= (0F)) && (this.hasDodged)){
				this.skinOverlayComponent.hideAll(var1);
				this.hasDodged = false;
			}else{
				return;
			}
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.KENBUNSHOKU_HAKI_OFF.get())),SoundSource.PLAYERS,2F,1F);
		this.skinOverlayComponent.hideAll(var1);
		float var3 = (100F) + (WyHelper.secondsToTicks((float)(((this.stackComponent.getDefaultStacks()) - (this.stackComponent.getStacks())) * (6))));
		this.cooldownComponent.startCooldown(var1,var3);
		this.stackComponent.revertStacksToDefault(var1,this);
	}
	private int calculateMaxProtection(LivingEntity var1) {
		EntityStatsData var2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))));
		HakiData var3 = ((HakiData)(((java.util.Optional)(HakiData.get(var1))).orElse((Object)(null))));
		if (((var2) != (null)) && ((var3) != (null))){
			double var4 = (var2.getDoriki()) / (5000D);
			return (int)(Math.max(1L,Math.round((var4) + ((double)((var3.getKenbunshokuHakiExp()) / (12F))))));
		}else{
			return 0;
		}
	}
	public float damageTakenEvent(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if ((super.isContinuous()) && (!(AbilityUseConditions.canUseMomentumAbilities(var1,this).isFail()))){
			HakiData var5 = ((HakiData)(((java.util.Optional)(HakiData.get(var1))).orElse((Object)(null))));
			if ((var5) == (null)){
				return var4;
			}else{
				int var6 = (10) + ((var5.getMaxOveruse()) / (1180));
				IDamageSourceHandler var7 = IDamageSourceHandler.getHandler(var3);
				boolean var8 = var7.isUnavoidable();
				int var9 = ((var7.hasElement(SourceElement.EXPLOSION)) || (var7.hasElement(SourceElement.SHOCKWAVE))) ? (1) : (0);
				if ((!(var8)) && ((var9) == (0))){
					boolean var10 = true;
					if ((!(var3.is(DamageTypes.MOB_ATTACK))) && ((!(var3.is(DamageTypes.PLAYER_ATTACK))) && (!(var3.is(DamageTypeTags.IS_PROJECTILE))))){
						if ((!(var7.hasType(SourceType.BLUNT))) && ((!(var7.hasType(SourceType.SLASH))) && ((!(var7.hasType(SourceType.FIST))) && ((!(var7.hasType(SourceType.PHYSICAL))) && ((!(var7.hasType(SourceType.PROJECTILE))) && ((!(var7.hasType(SourceType.INDIRECT))) && (!(var7.hasType(SourceType.BULLET))))))))){

						}else{
							var10 = false;
						}
					}else{
						var10 = false;
					}
					if ((this.protTimer) <= (0F)){
						if (!(var10)){
							this.skinOverlayComponent.showAll(var1);
							this.animationComponent.start(var1,ModAnimations.DODGE,5);
							var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.FUTURE_SIGHT_HIT.get())),SoundSource.PLAYERS,2F,(0.75F) + ((var1.getRandom().nextFloat()) / (2F)));
							var5.alterHakiOveruse(((var6) * (4)) + ((int)((var4) * ((float)(var6)))));
							int var11 = Math.max(1,Math.round((var4) / (15F)));
							this.stackComponent.addStacks(var1,this,-(var11));
							this.protTimer = 10F;
							this.hasDodged = true;
						}
						return (var10) ? (var4) : (0F);
					}else{
						return 0F;
					}
				}else{
					return var4;
				}
			}
		}else{
			return var4;
		}
	}
	private static boolean canUnlock(LivingEntity var0) {
		AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		HakiData var2 = ((HakiData)(((java.util.Optional)(HakiData.get(var0))).orElse((Object)(null))));
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var0))).orElse((Object)(null))));
		if (((var1) != (null)) && (((var2) != (null)) && ((var3) != (null)))){
			return (var1.hasUnlockedAbility(((AbilityCore)(KenbunshokuHakiAuraAbility.INSTANCE.get())))) && (((var3.getDoriki()) > (6000D)) && (((double)(var2.getKenbunshokuHakiExp())) > (HakiHelper.getKenbunshokuFutureSightExpNeeded(var0))));
		}else{
			return false;
		}
	}
	public int getProtectionStacks() {
		return this.stackComponent.getStacks();
	}
	public int getMaxProtectionStacks() {
		return this.stackComponent.getDefaultStacks();
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("Using Observation Haki allows the user to see a short period into the future to avoid attacks."),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.HAKI,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KenbunshokuHakiFutureSightAbility::new)))).addDescriptionLine(lv4_3).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(KenbunshokuHakiFutureSightAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = ModRegistry.registerAbility("kenbunshoku_haki_future_sight","Kenbunshoku Haki: Future Sight",var0);
		OVERLAY = new AbilityOverlay$Builder().setTexture(ModResources.BUSOSHOKU_HAKI_ARM).setColor(new Color(255,100,200,100)).build();
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		float var3 = (100F) + (WyHelper.secondsToTicks((float)(((this.stackComponent.getDefaultStacks()) - (this.stackComponent.getStacks())) * (6))));
		float var4 = var3;
		final float var4_f1 = var4;
		Consumer<CooldownComponentTooltip> var5 = (l0) -> {
			l0.cooldown(100F,var4_f1);
		};
		CooldownComponentTooltip.generate(var2,var5);
		Consumer<ContinuousComponentTooltip> var6 = (l0) -> {
		};
		ContinuousComponentTooltip.generate(var2,var6);
		KenbunshokuHakiFutureSightAbility var7 = this;
		Player var8 = var1;
		final Player var8_f2 = var8;
		Consumer<StackComponentTooltip> var9 = (l0) -> {
			l0.stacks((float)(this.calculateMaxProtection((LivingEntity)(var8_f2))));
		};
		StackComponentTooltip.generate(var2,var9);
	}
}