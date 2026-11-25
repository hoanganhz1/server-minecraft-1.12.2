package xyz.pixelatedw.mineminenomi.abilities.horu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.horu.WinkExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.horu.WinkExplosionParticleEffect$Details;

public class DeathWinkAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 120;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DeathWinkAbility>> INSTANCE;
	private ExplosionComponent explosionComponent;

	public DeathWinkAbility(AbilityCore<DeathWinkAbility> var1) {
		super((AbilityCore)(var1));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.explosionComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.DEATH_WINK_SFX.get())),SoundSource.PLAYERS,2F,1F);
		EntityHitResult var3 = WyHelper.rayTraceEntities((Entity)(var1),1D);
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.HORU_WINK.get())),(Entity)(var1),var3.getLocation().x(),var1.getY(),var3.getLocation().z());
		int var4 = (var1.hasEffect((Holder)(ModEffects.GANMEN_SEICHO_HORMONE))) ? (3) : (2);
		boolean var5 = true;
		HitResult var6 = WyHelper.rayTraceBlocksAndEntities((Entity)(var1),(double)((8) * (var4)));
		double var7 = var6.getLocation().x;
		double var8 = var6.getLocation().y;
		double var9 = var6.getLocation().z;
		if (var6 instanceof EntityHitResult){
			Entity var10 = ((EntityHitResult)(var6)).getEntity();
			if (var10 instanceof NuProjectileEntity){
				NuProjectileEntity var11 = ((NuProjectileEntity)(var10));
				if ((var11.getDamage()) < ((float)((var4) * (5)))){
					var5 = false;
					AbilityHelper.setDeltaMovement((Entity)(var11),-(var11.getDeltaMovement().x),var11.getDeltaMovement().y,-(var11.getDeltaMovement().x));
				}
			}
		}
		if (var5){
			AbilityExplosion var10_1 = this.explosionComponent.createExplosion((Entity)(var1),var1,var7,var8,var9,(float)((1) + (var4)));
			var10_1.setStaticDamage((float)((var4) * (10)));
			var10_1.setExplosionSound(false);
			var10_1.setSmokeParticles(((ParticleEffect)(ModParticleEffects.WINK_EXPLOSION.get())),(ParticleEffect$Details)(WinkExplosionParticleEffect.EXPLOSION2));
			var10_1.explode();
			if ((Math.sqrt(var1.distanceToSqr(var7,var8,var9))) < (0.5D)){
				AbilityHelper.setDeltaMovement((Entity)(var1),(((double)(WyHelper.randomWithRange(-1,1))) * (0.5D)) * ((double)(var4)),(1.5D) * ((double)(var4)),(((double)(WyHelper.randomWithRange(-1,1))) * (0.5D)) * ((double)(var4)));
			}
		}
		this.cooldownComponent.startCooldown(var1,120F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[1];
			Object[] lv1_3 = new Object[]{ModEffects.GANMEN_SEICHO_HORMONE.get()};
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("The user winks really hard creating a shockwave; %s boosts it's power."),lv1_3);
			Component[] lv1_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_5 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(120F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DeathWinkAbility::new)))).addDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_5).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("death_wink","Death Wink",var0));
	}
}