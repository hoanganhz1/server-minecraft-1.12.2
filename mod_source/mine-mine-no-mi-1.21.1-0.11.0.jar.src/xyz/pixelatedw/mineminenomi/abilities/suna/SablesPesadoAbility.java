package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna.SablesPesadoProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SablesPesadoAbility extends Ability {
	// Fields
	private static final float COOLDOWN_BONUS = 0.8F;
	private static final float CHARGE_BONUS = 0.5F;
	private static final int COOLDOWN = 500;
	private static final int CHARGE_TIME = 100;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SablesPesadoAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final ProjectileComponent projectileComponent;
	private final ExplosionComponent explosionComponent;
	private Interval particleInterval;

	public SablesPesadoAbility(AbilityCore<SablesPesadoAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::duringChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<SablesPesadoProjectile>) (this::createProjectile))))));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		this.particleInterval = new Interval(5);
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.projectileComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresDryUser))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if (!(this.chargeComponent.isCharging())){
			this.chargeComponent.getMaxChargeBonusManager().removeBonus(SunaHelper.DESERT_CHARGE_BONUS);
			if (SunaHelper.isFruitBoosted(var1)){
				this.chargeComponent.getMaxChargeBonusManager().addBonus(SunaHelper.DESERT_CHARGE_BONUS,"Desert Charge Bonus",BonusOperation.MUL,0.5F);
			}
			this.particleInterval.restartIntervalToZero();
			this.chargeComponent.startCharging(var1,100F);
		}
	}
	private void duringChargeEvent(LivingEntity var1, IAbility var2) {
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),2,1,false,false));
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.REDUCED_FALL),2,1,false,false));
		if (this.particleInterval.canTick()){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.SABLES_PESADO.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		}
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.SABLES_PESADO_SFX.get())),SoundSource.PLAYERS,2F,1F);
		this.projectileComponent.shoot(var1,3.25F,4F);
		this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
		if (SunaHelper.isFruitBoosted(var1)){
			this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS,"Desert Cooldown Bonus",BonusOperation.MUL,0.8F);
		}
		this.cooldownComponent.startCooldown(var1,500F);
	}
	private SablesPesadoProjectile createProjectile(LivingEntity var1) {
		return new SablesPesadoProjectile(var1.level(),var1,(IAbility)(this));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[2];
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("The user compresses a sandstorm to its limits and shoots it at extreme speeds."),(Object)(null));
			Object[] lv1_3 = new Object[2];
			int lv1_4 = Math.round(19.999998F);
			String lv1_5 = "§a" + lv1_4 + "%§r";
			lv1_3[0] = lv1_5;
			int lv1_6 = Math.round(50F);
			String lv1_7 = "§a" + lv1_6 + "%§r";
			lv1_3[1] = lv1_7;
			((java.lang.Object[]) (lv1_2))[1] = ImmutablePair.of((Object)("While in a desert the cooldown of this ability is reduced by %s and charge time is reduced by %s."),lv1_3);
			Component[] lv1_8 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_9 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(500F),ChargeComponent.getTooltip(100F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SablesPesadoAbility::new)))).addDescriptionLine(lv1_8).addAdvancedDescriptionLine(lv1_9).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("sables_pesado","Sables: Pesado",var0));
	}
}