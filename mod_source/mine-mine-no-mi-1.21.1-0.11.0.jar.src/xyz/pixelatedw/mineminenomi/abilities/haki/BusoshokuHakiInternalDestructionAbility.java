package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.awt.Color;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$OverlayPart;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageSourceComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageSourceComponent$IUpdateDamageSource;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.HakiDamageTooltip;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class BusoshokuHakiInternalDestructionAbility extends PunchAbility {
	// Fields
	public static final Supplier<AbilityCore<BusoshokuHakiInternalDestructionAbility>> INSTANCE;
	private static final AbilityOverlay OVERLAY;
	private final PoolComponent poolComponent;
	private final SkinOverlayComponent skinOverlayComponent;
	private final DamageSourceComponent damageSourceComponent;

	public BusoshokuHakiInternalDestructionAbility(AbilityCore<BusoshokuHakiInternalDestructionAbility> var1) {
		super((AbilityCore)(var1));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.ADVANCED_BUSOSHOKU_HAKI,new AbilityPool[0]);
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		this.damageSourceComponent = new DamageSourceComponent((IAbility)(this)).addDamageSourceUpdateEvent(100,(DamageSourceComponent$IUpdateDamageSource)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageSourceComponent$IUpdateDamageSource)(this::onDamageSourceUpdate))));
		this.setOGCD();
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart)))).addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.poolComponent,this.skinOverlayComponent,this.damageSourceComponent};
		this.addComponents(var2);
		this.clearUseChecks();
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canEnableHaki))));
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		this.statsComponent.removeModifiers(var1);
		this.statsComponent.clearAttributeModifiers();
		this.statsComponent.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE,HakiHelper.getAdvancedBusoshokuHakiAttackReachBonus(((AbilityCore)(INSTANCE.get())),(double)(BusoshokuHakiEmissionAbility.getReachBonus(var1))));
		this.statsComponent.applyModifiers(var1);
		this.skinOverlayComponent.showAll(var1);
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.BUSOSHOKU_HAKI_SFX.get())),SoundSource.PLAYERS,2F,1F);
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		Level var3 = var1.level();
		if (((var3) != (null)) && (!(var3.isClientSide))){
			if (HakiHelper.checkForHakiOveruse(var1,2)){
				super.consumeUseAndStop();
			}
			return;
		}else{
			return;
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		this.skinOverlayComponent.hideAll(var1);
	}
	private static boolean canUnlock(LivingEntity var0) {
		AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		HakiData var2 = ((HakiData)(((java.util.Optional)(HakiData.get(var0))).orElse((Object)(null))));
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var0))).orElse((Object)(null))));
		if (((var1) != (null)) && (((var2) != (null)) && ((var3) != (null)))){
			boolean var4 = var1.hasUnlockedAbility(((AbilityCore)(BusoshokuHakiEmissionAbility.INSTANCE.get())));
			int var5 = (((double)(var2.getBusoshokuHakiExp())) > (HakiHelper.getBusoshokuInternalDestructionExpNeeded(var0))) ? (1) : (0);
			return (var4) && (((var3.getDoriki()) > (8000D)) && ((var5) != (0)));
		}else{
			return false;
		}
	}
	public void onDamageSourceUpdate(LivingEntity var1, LivingEntity var2, DamageSource var3, IAbility var4) {
		if (this.continuousComponent.isContinuous()){
			IDamageSourceHandler var5 = IDamageSourceHandler.getHandler(var3);
			var5.bypassLogia();
			var5.addGlobalPiercing(1F);
		}
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.INTERNAL_DESTRUCTION_BURST.get())),(Entity)(var1),var2.getX(),(var2.getY()) + ((double)(var1.getEyeHeight())),var2.getZ());
		AbilityExplosion var4 = new AbilityExplosion((Entity)(var1),(IAbility)(this),var2.getX(),var2.getY(),var2.getZ(),1F);
		var4.setDamageEntities(false);
		var4.setDestroyBlocks(false);
		var4.disableExplosionKnockback();
		var4.setFireAfterExplosion(false);
		var4.setExplosionSound(false);
		var4.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION1));
		var4.explode();
		return true;
	}
	public int getUseLimit() {
		return -1;
	}
	public boolean isParallel() {
		return true;
	}
	public float getPunchCooldown() {
		return 0F;
	}
	public Result canTriggerHit(LivingEntity var1) {
		return Result.success();
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to emit their haki through the target's body dealing great internal damage."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			SourceType[] lv2_4 = new SourceType[]{SourceType.FIST,SourceType.INTERNAL};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.HAKI,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BusoshokuHakiInternalDestructionAbility::new)))).addDescriptionLine(lv2_3).setSourceType(lv2_4).setUnlockCheck((AbilityCore$ICanUnlock)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$ICanUnlock)(BusoshokuHakiInternalDestructionAbility::canUnlock)))).build("mineminenomi");
		}));
		INSTANCE = ModRegistry.registerAbility("busoshoku_haki_internal_destruction","Busoshoku Haki: Internal Destruction",var0);
		OVERLAY = new AbilityOverlay$Builder().setOverlayPart(AbilityOverlay$OverlayPart.LIMB).setColor(new Color(226,129,25,70)).build();
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Player var3 = var1;
		final Player var3_f1 = var3;
		Consumer<HakiDamageTooltip> var4 = (l0) -> {
			l0.damage((float)(HakiHelper.getAdvancedBusoshokuHakiDamageBoost((LivingEntity)(var3_f1),1F))).setBaseDamage(2F);
		};
		HakiDamageTooltip.generate(var2,var4);
	}
}