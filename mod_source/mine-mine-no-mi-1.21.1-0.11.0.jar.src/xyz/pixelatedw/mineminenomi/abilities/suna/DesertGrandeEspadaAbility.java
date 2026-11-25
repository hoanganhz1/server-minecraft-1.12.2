package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Vector3d;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna.DesertGrandeEspadaProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DesertGrandeEspadaAbility extends Ability {
	// Fields
	private static final float COOLDOWN_BONUS = 0.8F;
	private static final float DAMAGE_BONUS = 1.15F;
	private static final int COOLDOWN = 400;
	private static final int CHARGE_TIME = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DesertGrandeEspadaAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final ProjectileComponent projectileComponent;
	private Vector3d targetPos;

	public DesertGrandeEspadaAbility(AbilityCore<DesertGrandeEspadaAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::duringChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<DesertGrandeEspadaProjectile>) (this::createProjectile))))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.projectileComponent,this.chargeComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresDryUser))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresOnGround))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,10F);
	}
	private void duringChargeEvent(LivingEntity var1, IAbility var2) {
		if ((this.targetPos) == (null)){
			HitResult var3 = WyHelper.rayTraceBlocksAndEntities((Entity)(var1),64D);
			double var4 = var3.getLocation().x;
			double var5 = var3.getLocation().z;
			int var6 = var1.level().getHeight(Heightmap.Types.WORLD_SURFACE,(int)(var4),(int)(var5));
			this.targetPos = new Vector3d(var4,(double)(var6),var5);
		}
		if (!(var1.level().isClientSide)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.SAND_BLADE_IDLE.get())),(Entity)(var1),this.targetPos.x,this.targetPos.y,this.targetPos.z);
		}
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),2,1,false,false));
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		if (!(var1.level().isClientSide)){
			DesertGrandeEspadaProjectile var3 = ((DesertGrandeEspadaProjectile)(this.projectileComponent.getNewProjectile(var1)));
			var3.moveTo(this.targetPos.x,(this.targetPos.y) - (6D),this.targetPos.z,0F,0F);
			var3.shoot(0D,0.7D,0D,1.4F,0F);
			var1.level().addFreshEntity((Entity)(var3));
		}
		this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
		if (SunaHelper.isFruitBoosted(var1)){
			this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS,"Desert Cooldown Bonus",BonusOperation.MUL,0.8F);
		}
		this.cooldownComponent.startCooldown(var1,400F);
		this.targetPos = null;
	}
	private DesertGrandeEspadaProjectile createProjectile(LivingEntity var1) {
		boolean var2 = SunaHelper.isFruitBoosted(var1);
		this.projectileComponent.getDamageBonusManager().removeBonus(SunaHelper.DESERT_DAMAGE_BONUS);
		if (var2){
			this.projectileComponent.getDamageBonusManager().addBonus(SunaHelper.DESERT_DAMAGE_BONUS,"Desert Damage Bonus",BonusOperation.MUL,1.15F);
		}
		return new DesertGrandeEspadaProjectile(var1.level(),var1,(IAbility)(this));
	}
	public void setTargetPos(Vector3d var1) {
		this.targetPos = var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[2];
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("Spawns a large sand blade cutting through multiple enemies wherever the user is looking."),(Object)(null));
			Object[] lv1_3 = new Object[2];
			int lv1_4 = Math.round(19.999998F);
			String lv1_5 = "§a" + lv1_4 + "%§r";
			lv1_3[0] = lv1_5;
			int lv1_6 = Math.round((Math.abs(-0.14999998F)) * (100F));
			String lv1_7 = "§a" + lv1_6 + "%§r";
			lv1_3[1] = lv1_7;
			((java.lang.Object[]) (lv1_2))[1] = ImmutablePair.of((Object)("While in a desert the cooldown of this ability is reduced by %s and the damage is increased by %s."),lv1_3);
			Component[] lv1_8 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_9 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(400F),ChargeComponent.getTooltip(10F)};
			SourceType[] lv1_10 = new SourceType[]{SourceType.SLASH};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DesertGrandeEspadaAbility::new)))).addDescriptionLine(lv1_8).addAdvancedDescriptionLine(lv1_9).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv1_10).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("desert_grande_espada","Desert Grande Espada",var0));
	}
}