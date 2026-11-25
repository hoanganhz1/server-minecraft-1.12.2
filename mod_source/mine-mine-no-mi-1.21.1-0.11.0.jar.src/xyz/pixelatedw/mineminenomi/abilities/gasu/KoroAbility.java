package xyz.pixelatedw.mineminenomi.abilities.gasu;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.effects.BaseEffect;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.gasu.KoroParticleEffect$Details;

public class KoroAbility extends Ability {
	// Fields
	private static final TargetPredicate TARGETS_PREDICATE = new TargetPredicate().testFriendlyFaction();
	private static final int COOLDOWN = 400;
	private static final float RANGE = 30F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KoroAbility>> INSTANCE;
	private final RangeComponent rangeComponent;
	private KoroParticleEffect$Details particleDetails;

	public KoroAbility(AbilityCore<KoroAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.particleDetails = new KoroParticleEffect$Details();
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[2];
			((java.lang.Object[]) (lv3_2))[0] = ImmutablePair.of((Object)("Removes all poison from self and nearby allies."),(Object)(null));
			Object[] lv3_3 = new Object[]{ShinokuniAbility.INSTANCE};
			((java.lang.Object[]) (lv3_2))[1] = ImmutablePair.of((Object)("If used while %s is active, it'll remove all harmful effects for self and nearby allies."),lv3_3);
			Component[] lv3_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			AbilityDescriptionLine$IDescriptionLine[] lv3_5 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(400F),RangeComponent.getTooltip(30F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KoroAbility::new)))).addDescriptionLine(lv3_4).addAdvancedDescriptionLine(lv3_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("koro","Koro",var0));
	}
	public void useEvent(LivingEntity var1, IAbility var2) {
		List var3 = this.rangeComponent.getTargetsInArea(var1,30F,TARGETS_PREDICATE);
		var3.add(var1);
		Function<AbilityData, ShinokuniAbility> var4 = (l0) -> {
			return ((ShinokuniAbility)(l0.getEquippedAbility(((AbilityCore)(ShinokuniAbility.INSTANCE.get())))));
		};
		Function<ShinokuniAbility, Boolean> var5 = (l0) -> {
			return Boolean.valueOf(((l0) != (null)) && (l0.isContinuous()));
		};
		boolean var6 = ((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var4)))).map((Function)(var5)).orElse(Boolean.valueOf(false)))).booleanValue();
		Iterator var7 = var3.iterator();
		LOOP_1:
		do{
			if (var7.hasNext()){
				LivingEntity var8 = ((LivingEntity)(var7.next()));
				if (var6){
					Iterator var9 = var8.getActiveEffects().iterator();
					do{
						if (var9.hasNext()){
							MobEffectInstance var10 = ((MobEffectInstance)(var9.next()));
							Holder var11 = var10.getEffect();
							if (((var11 instanceof BaseEffect) && (!(((BaseEffect)(var11)).isRemoveable()))) || (!(((((MobEffect)(var10.getEffect().value())).getCategory()) == (MobEffectCategory.HARMFUL)) && (var8.removeEffect(var10.getEffect()))))){
								continue;
							}else{
								this.particleDetails.setEffect(var10);
								WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.KORO.get())),(Entity)(var1),var8.getX(),var8.getY(),var8.getZ(),(ParticleEffect$Details)(this.particleDetails));
								continue;
							}
						}else{
							continue LOOP_1;
						}
					} while (true);
				}else{
					if ((var8.hasEffect(MobEffects.POISON)) && (var8.removeEffect(MobEffects.POISON))){
						this.particleDetails.setEffect(var8.getEffect(MobEffects.POISON));
						WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.KORO.get())),(Entity)(var1),var8.getX(),var8.getY(),var8.getZ(),(ParticleEffect$Details)(this.particleDetails));
						continue;
					}else{
						continue;
					}
				}
			}else{
				break;
			}
		} while (true);
		this.cooldownComponent.startCooldown(var1,400F);
	}
}