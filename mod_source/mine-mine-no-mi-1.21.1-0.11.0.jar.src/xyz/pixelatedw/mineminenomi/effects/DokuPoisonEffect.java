package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.abilities.PoisonImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.BaseDamageSource;
import xyz.pixelatedw.mineminenomi.api.effects.ITransmisibleByTouchEffect;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

public class DokuPoisonEffect extends DamageOverTimeEffect implements ITransmisibleByTouchEffect {
	public DokuPoisonEffect() {
		super(WyHelper.hexToRGB("#802460").getRGB(),(l0) -> {
			return ModDamageSources.getInstance((LevelAccessor)(l0.level())).poison();
		},0F,25);
		Function<Integer, Float> var1 = (Integer l0) -> {
			int lv2_1 = (25) >> (l0.intValue());
			if ((lv2_1) > (0)){
				return Float.valueOf((25F) / ((float)(lv2_1)));
			}else{
				return Float.valueOf(25F);
			}
		};
		this.setDamageScaling(var1);
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		Function<AbilityData, PoisonImmunityAbility> var3 = (l0) -> {
			return ((PoisonImmunityAbility)(l0.getEquippedAbility(((AbilityCore)(PoisonImmunityAbility.INSTANCE.get())))));
		};
		if (!(AbilityData.get(var1).map(var3).isPresent())){
			super.applyEffectTick(var1,var2);
		}
		return true;
	}
	public boolean isTransmisibleByTouch() {
		return true;
	}
	public boolean isLingering() {
		return true;
	}
}