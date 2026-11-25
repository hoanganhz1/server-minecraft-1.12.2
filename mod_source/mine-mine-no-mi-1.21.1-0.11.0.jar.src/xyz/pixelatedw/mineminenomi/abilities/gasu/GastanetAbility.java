package xyz.pixelatedw.mineminenomi.abilities.gasu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GastanetAbility extends Ability {
	// Fields
	private static final UUID SHINOKUNI_RANGE_BONUS = UUID.fromString("05f3fc04-68e5-4f7b-a513-7237ad6fd643");
	private static final TargetPredicate ALL_TARGETS = new TargetPredicate();
	private static final int COOLDOWN = 160;
	private static final int RANGE = 8;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GastanetAbility>> INSTANCE;
	private final RangeComponent rangeComponent;
	private final ExplosionComponent explosionComponent;

	public GastanetAbility(AbilityCore<GastanetAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[2];
			((java.lang.Object[]) (lv4_2))[0] = ImmutablePair.of((Object)("The user creates an explosion by detonating gas with their hands poisoning every enemy around the user."),(Object)(null));
			Object[] lv4_3 = new Object[]{ShinokuniAbility.INSTANCE};
			((java.lang.Object[]) (lv4_2))[1] = ImmutablePair.of((Object)("If %s is active the nearby entities will instead get affected by whatever effect the user has at the moment. Allies will receive benefic effects while enemies will receive negative effects."),lv4_3);
			Component[] lv4_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			AbilityDescriptionLine$IDescriptionLine[] lv4_5 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(160F),RangeComponent.getTooltip(8F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GastanetAbility::new)))).addDescriptionLine(lv4_4).addAdvancedDescriptionLine(lv4_5).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.EXPLOSION).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gastanet","Gastanet",var0));
	}

	private void useEvent(LivingEntity var1, IAbility var2) {
		HitResult var3 = WyHelper.rayTraceBlocksAndEntities((Entity)(var1),32D);
		AbilityExplosion var4 = this.explosionComponent.createExplosion((Entity)(var1),var3.getLocation().x,var3.getLocation().y,var3.getLocation().z,5F);
		var4.setStaticDamage(40F);
		var4.setDestroyBlocks(false);
		var4.explode();
		this.rangeComponent.getBonusManager().removeBonus(SHINOKUNI_RANGE_BONUS);
		if (((MorphInfo)(ModMorphs.SHINOKUNI.get())).isActive(var1)){
			this.rangeComponent.getBonusManager().addBonus(SHINOKUNI_RANGE_BONUS,"Shinokuni Range Bonus",BonusOperation.ADD,4F);
			List var5 = this.rangeComponent.getTargetsInArea(var1,8F,ALL_TARGETS);
			Function<AbilityData, ShinokuniAbility> var6 = (l0) -> {
			return ((ShinokuniAbility)(l0.getEquippedAbility(((AbilityCore)(ShinokuniAbility.INSTANCE.get())))));
		};
			ShinokuniAbility var7 = ((ShinokuniAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var6)).orElse(null)));
			ShinokuniAbility var8 = var7;
			LivingEntity var9 = var1;
			final ShinokuniAbility var8_f1 = var8;
			final LivingEntity var9_f2 = var9;
			Consumer<LivingEntity> var10 = (l0) -> {
			var8_f1.applyEffects(var9_f2,l0);
		};
			((java.lang.Iterable)(var5)).forEach((Consumer)(var10));
		}else{
			List var5_1 = this.rangeComponent.getTargetsInArea(var1,8F);
			Consumer<LivingEntity> var6_1 = (l0) -> {
			l0.addEffect(new MobEffectInstance(MobEffects.POISON,200,5));
		};
			((java.lang.Iterable)(var5_1)).forEach((Consumer)(var6_1));
		}
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GASTANET.get())),(Entity)(var1),var3.getLocation().x,var3.getLocation().y,var3.getLocation().z);
		this.cooldownComponent.startCooldown(var1,160F);
	}}