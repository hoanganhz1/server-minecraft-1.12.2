package xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
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
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class AncientBiteAbility extends PunchAbility {
	// Fields
	private static final int COOLDOWN = 140;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<AncientBiteAbility>> INSTANCE;

	public AncientBiteAbility(AbilityCore<AncientBiteAbility> var1) {
		super((AbilityCore)(var1));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RyuAllosaurusHelper::requiresEitherPoint))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RyuAllosaurusHelper::requiresEitherPoint))));
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		double var4 = (WyHelper.randomDouble()) * (2D);
		double var5 = (WyHelper.randomDouble()) * (0.3D);
		double var6 = (WyHelper.randomDouble()) * (2D);
		var1.level().addParticle((ParticleOptions)(ParticleTypes.CRIT),var2.getX(),(var2.getY()) + (1D),var2.getZ(),var4,var5,var6);
		var2.addEffect(new MobEffectInstance((Holder)(ModEffects.BLEEDING),20,0));
		int var7 = 0;
		do{
			if ((var7) < (50)){
				Vec3 var8 = new Vec3((((double)(var1.getRandom().nextFloat())) - (0.5D)) * (0.1D),((Math.random()) * (0.1D)) + (0.1D),0D);
				Vec3 var9 = var8.xRot((-(var1.getXRot())) * (0.017453292F));
				Vec3 var10 = var9.yRot((-(var1.getYRot())) * (0.017453292F));
				((ServerLevel)(var1.level())).sendParticles((ParticleOptions)(ParticleTypes.CRIT),var2.getX(),(var2.getY()) + (1.5D),var2.getZ(),1,var10.x,var10.y,var10.z,0.8D);
				var7++;
				continue;
			}else{
				break;
			}
		} while (true);
		return true;
	}
	public int getUseLimit() {
		return 1;
	}
	public float getPunchDamage() {
		return 20F;
	}
	public float getPunchCooldown() {
		return 140F;
	}
	public boolean isParallel() {
		return true;
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		Supplier[] var3 = new Supplier[]{ModMorphs.ALLOSAURUS_HEAVY,ModMorphs.ALLOSAURUS_WALK};
		AbilityTooltipsHelper.generateRequiredMorphTooltip(var2,var3);
		super.appendDescription(var1,var2);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Bites the enemy dealing moderate damage."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(AncientBiteAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("ancient_bite","Ancient Bite",var0));
	}
}