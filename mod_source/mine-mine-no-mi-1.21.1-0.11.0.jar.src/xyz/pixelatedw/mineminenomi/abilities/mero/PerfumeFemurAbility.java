package xyz.pixelatedw.mineminenomi.abilities.mero;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class PerfumeFemurAbility extends PunchAbility {
	// Fields
	private static final int COOLDOWN = 280;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<PerfumeFemurAbility>> INSTANCE;

	public PerfumeFemurAbility(AbilityCore<PerfumeFemurAbility> var1) {
		super((AbilityCore)(var1));
		this.clearUseChecks();
	}
	public float getPunchDamage() {
		return 10F;
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		var2.addEffect(new MobEffectInstance((Holder)(ModEffects.LOVESTRUCK),200,1));
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.PERFUME_FEMUR.get())),(Entity)(var1),var2.getX(),var2.getY(),var2.getZ());
		return true;
	}
	public int getUseLimit() {
		return 1;
	}
	public float getPunchCooldown() {
		return 280F;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Kicks the enemy and turns them into stone."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(PerfumeFemurAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("perfume_femur","Perfume Femur",var0));
	}
}