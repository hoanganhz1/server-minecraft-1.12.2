package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import java.util.function.Function;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.damagesources.BaseDamageSource;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

public class TokudaiCandlePoisonEffect extends DamageOverTimeEffect {
	public TokudaiCandlePoisonEffect() {
		super(WyHelper.hexToRGB("#bed4ce").getRGB(),(l0) -> {
			return ModDamageSources.getInstance((LevelAccessor)(l0.level())).poison();
		},10F,30);
		TokudaiCandlePoisonEffect var1 = this;
		Function<Integer, Float> var2 = (Integer l0) -> {
			if ((l0.intValue()) <= (0)){
				l0 = Integer.valueOf(1);
			}
			return Float.valueOf((this.getBaseDamage()) * ((float)(l0.intValue())));
		};
		this.setDamageScaling(var2);
	}
	public boolean isLingering() {
		return true;
	}
}