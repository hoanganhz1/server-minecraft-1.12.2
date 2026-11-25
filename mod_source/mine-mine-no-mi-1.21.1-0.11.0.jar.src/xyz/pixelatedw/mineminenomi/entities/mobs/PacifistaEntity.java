package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.IThreatLevel;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.pacifista.PacifistaRadicalBeamGoal;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class PacifistaEntity extends OPEntity implements IThreatLevel {
	// Fields
	public static final byte START_CHARGE_LASER_EVENT = 100;
	public static final byte END_CHARGE_LASER_EVENT = 101;
	private boolean isChargingLaser;

	public PacifistaEntity(EntityType<? extends PacifistaEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
		this.isChargingLaser = false;
		if (((var2) != (null)) && (!(var2.isClientSide))){
			this.getEntityStats().setRace(((Race)(ModRaces.CYBORG.get())));
			this.getEntityStats().setFaction(((Faction)(ModFactions.MARINE.get())));
			this.getEntityStats().setFightingStyle(((FightingStyle)(ModFightingStyles.BRAWLER.get())));
			this.getEntityStats().setDoriki((2000D) + (this.getRandomDorikiBonus(0,2000)));
			this.getEntityStats().setBelly((50L) + ((long)(WyHelper.randomWithRange(0,20))));
			MobsHelper.addBasicNPCGoals(this);
			this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
			this.goalSelector.addGoal(2,(Goal)(new PacifistaRadicalBeamGoal((Mob)(this))));
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.25D).add(Attributes.ATTACK_DAMAGE,6D).add(Attributes.MAX_HEALTH,150D).add(Attributes.ARMOR,20D).add(Attributes.KNOCKBACK_RESISTANCE,0.8D).add(Attributes.ATTACK_KNOCKBACK,2D);
	}
	public float getThreatLevel() {
		return 0.4F;
	}
	public void die(DamageSource var1) {
		super.die(var1);
		if (!(this.level().isClientSide)){
			AbilityExplosion var2 = new AbilityExplosion((Entity)(this),(IAbility)(null),this.getX(),this.getY(),this.getZ(),5F);
			var2.setStaticDamage(25F);
			var2.setDestroyBlocks(false);
			var2.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION5));
			var2.explode();
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public void handleEntityEvent(byte var1) {
		switch (var1){
		case 100:
			this.isChargingLaser = true;
			break;
		case 101:
			this.isChargingLaser = false;
			break;
		}
		super.handleEntityEvent(var1);
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean isChargingLaser() {
		return this.isChargingLaser;
	}
	public static boolean checkSpawnRules(EntityType<? extends OPEntity> var0, ServerLevelAccessor var1, MobSpawnType var2, BlockPos var3, RandomSource var4) {
		if (((var4.nextDouble()) * (100D)) > (ServerConfig.getPacifistaSpawnChance())){
			return false;
		}else{
			return OPEntity.checkSpawnRules(var0,var1,var2,var3,var4);
		}
	}
}