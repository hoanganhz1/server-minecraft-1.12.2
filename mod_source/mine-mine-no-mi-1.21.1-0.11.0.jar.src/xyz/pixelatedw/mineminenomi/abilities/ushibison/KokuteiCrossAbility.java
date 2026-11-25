package xyz.pixelatedw.mineminenomi.abilities.ushibison;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IAbilityGoalFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KokuteiCrossAbility extends PunchAbility {
	// Fields
	private static final int COOLDOWN = 140;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KokuteiCrossAbility>> INSTANCE;

	public KokuteiCrossAbility(AbilityCore<KokuteiCrossAbility> var1) {
		super((AbilityCore)(var1));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(UshiBisonHelper::requiresHeavyPoint))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(UshiBisonHelper::requiresHeavyPoint))));
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		Vec3 var4 = var1.getLookAngle().multiply(1.5D,0D,1.5D);
		AbilityHelper.setDeltaMovement((Entity)(var2),var4.x,var1.getDeltaMovement().y(),var4.z);
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.KOKUTEI_CROSS.get())),(Entity)(var1),var2.getX(),var2.getY(),var2.getZ());
		return true;
	}
	public float getPunchDamage() {
		return 20F;
	}
	public float getPunchCooldown() {
		return 140F;
	}
	public int getUseLimit() {
		return 1;
	}
	public boolean isParallel() {
		return true;
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		Supplier[] var3 = new Supplier[]{ModMorphs.BISON_HEAVY};
		AbilityTooltipsHelper.generateRequiredMorphTooltip(var2,var3);
		super.appendDescription(var1,var2);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The transformed user crosses their hooves to hit the opponent."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST};
			AbilityCore$IAbilityGoalFactory[] lv1_5 = new AbilityCore$IAbilityGoalFactory[1];
			AbilityCore$IAbilityGoalFactory lv1_6 = CloseMeleeAbilityWrapperGoal::new;
			lv1_5[0] = lv1_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KokuteiCrossAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_4).addGoalWrapper(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kokutei_cross","Kokutei Cross",var0));
	}
}