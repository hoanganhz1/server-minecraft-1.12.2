package xyz.pixelatedw.mineminenomi.abilities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MobEffectComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MobEffectComponent$ICheckMobEffectEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public abstract class EffectImmunityAbility extends PassiveAbility {
	// Fields
	public static final EffectImmunityAbility$ImmunityInfo HEAT_RESISTANCES;
	protected final MobEffectComponent mobEffectComponent;
	private final EffectImmunityAbility$ImmunityInfo immunityInfo;

	public EffectImmunityAbility(AbilityCore<? extends EffectImmunityAbility> var1, EffectImmunityAbility$ImmunityInfo var2) {
		super((AbilityCore)(var1));
		this.mobEffectComponent = new MobEffectComponent((IAbility)(this));
		AbilityComponent[] var3 = new AbilityComponent[]{this.mobEffectComponent};
		this.addComponents(var3);
		this.immunityInfo = var2;
		this.mobEffectComponent.setEffectCheck((MobEffectComponent$ICheckMobEffectEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.MobEffectComponent$ICheckMobEffectEvent)(this::checkEffectEvent))));
	}
	private boolean checkEffectEvent(LivingEntity var1, MobEffectInstance var2) {
		if (AbilityHelper.isAffectedByWater(var1)){
			return true;
		}else{
			ArrayList var3 = this.immunityInfo.getImmunityEffects();
			ArrayList var4 = this.immunityInfo.getResistanceEffects();
			ArrayList var5 = this.immunityInfo.getResistanceEffectsReduction();
			if ((var4.size()) > (0)){
				int var6 = 0;
				do{
					if ((var6) < (var4.size())){
						if ((((Holder)(var4.get(var6))).equals(var2.getEffect())) && ((var2.getAmplifier()) < (1))){
							int var7 = (var2.getDuration()) / (((Integer)(var5.get(var6))).intValue());
							if ((var7) <= (20)){
								return false;
							}else{
								((IMobEffectInstanceMixin)(var2)).setDuration(var7);
							}
						}
						var6++;
						continue;
					}else{
						break;
					}
				} while (true);
			}
			return !(((List)(((java.util.stream.Stream)(var3.stream().map((Function)(((Function<Holder, MobEffect>)(Holder::value)))))).collect((Collector)(Collectors.toList())))).contains(var2.getEffect().value()));
		}
	}
	static  {
		Holder[] var0 = new Holder[]{ModEffects.FROSTBITE,ModEffects.CANDY_STUCK,ModEffects.CANDLE_LOCK};
		HEAT_RESISTANCES = new EffectImmunityAbility$ImmunityInfo().addImmunityEffects(var0).addResistanceEffect((Holder)(ModEffects.FROZEN),10).addResistanceEffect((Holder)(ModEffects.STICKY),6);
	}
}